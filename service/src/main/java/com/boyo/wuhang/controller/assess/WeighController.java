package com.boyo.wuhang.controller.assess;

import com.boyo.wuhang.component.interceptor.LoginInterceptor;
import com.boyo.wuhang.entity.WhDeliverForm;
import com.boyo.wuhang.entity.WhDeliveryDetailsForm;
import com.boyo.wuhang.entity.WhWeighForm;
import com.boyo.wuhang.entity.assess.WhDeliver;
import com.boyo.wuhang.entity.assess.WhDeliveryDetails;
import com.boyo.wuhang.entity.assess.WhWeigh;
import com.boyo.wuhang.entity.base.BaseConsignor;
import com.boyo.wuhang.entity.base.BaseDeparture;
import com.boyo.wuhang.entity.base.BaseEmployee;
import com.boyo.wuhang.entity.base.BaseUser;
import com.boyo.wuhang.service.assess.DeliverService;
import com.boyo.wuhang.service.assess.WeighService;
import com.boyo.wuhang.service.assess.WhDeliveryDetailsService;
import com.boyo.wuhang.service.base.BaseConsignorService;
import com.boyo.wuhang.service.base.DepartureService;
import com.boyo.wuhang.service.base.EmployeeService;
import com.boyo.wuhang.ultity.JSONTool;
import com.boyo.wuhang.ultity.JsonBuilder;
import com.boyo.wuhang.ultity.Pager;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/weigh")
public class WeighController {
	@Autowired
	private WeighService weighService;
	@Autowired
	private DeliverService deliverService;
	@Autowired
	private DepartureService departureService;
	@Autowired
	private BaseConsignorService consignorService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private WhDeliveryDetailsService detailsService;

	@RequestMapping(value = "list",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getWeighList(@RequestBody WhWeighForm whWeighForm){
		Pager page = new Pager();
		page.setPageNumber(whWeighForm.getPageNumber());
		page.setPageSize(whWeighForm.getPageSize());
		page = weighService.getWeighList(page, whWeighForm);
		return JsonBuilder.build(0,"",page);
	}

	//获取当前时间有效的启运地
	@RequestMapping(value = "select_departure",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject selectDeparture(){
		List<BaseDeparture> list = departureService.getNewestDeparture();
		return JsonBuilder.build(0,"",list);
	}

	//获取供应商的货主排班
	@RequestMapping(value = "select_consignor",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject selectConsignor(@RequestBody JSONObject jsonObject){
		Long supplierId = jsonObject.getLong("supplierId");
		List<Map<String,Object>> list = deliverService.getActiveConsignorSchedule(supplierId);
		return JsonBuilder.build(0,"",list);
	}

	//入场称重根据送货单上的车牌号，填写送货单信息，货主和启运地
	//之后称重记毛重
	@RequestMapping(value = "arrive_weigh",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject arriveWeigh(@RequestBody JSONObject jsonObject){
		BaseUser user = LoginInterceptor.user.get();
		Long deliverId = jsonObject.getLong("deliverId");
		String weigh = jsonObject.getString("weigh");
		//默认起运地,先获取评价表有效起运地
		Long departureId = jsonObject.getLong("departureId");
		//入场称重时选择商品名称
		long goodsId = Long.parseLong(jsonObject.getString("goodsId"));
		String goodsName = jsonObject.getString("goodsName");
		Long consignorId = jsonObject.getLong("consignorId");
		String gpsPicture = null;
		if (jsonObject.get("gpsPicture") != null){
			gpsPicture = jsonObject.getString("gpsPicture");
		}
		WhDeliver deliver = deliverService.getDeliverById(deliverId);
		if (deliver == null){
			return JsonBuilder.build(1,"送货单不存在",null);
		}
		if (deliver.getFlag() == 0){
			return JsonBuilder.build(1,"未打印送货单",null);
		}else if (deliver.getFlag() >= 2){
			return JsonBuilder.build(1,"该送货单已称毛重",null);
		}


		//校验起运地是否存在
		BaseDeparture departure = departureService.selectByPrimaryKey(departureId);
		if (departure == null){
			return JsonBuilder.build(1,"启运地不存在",null);
		}

		//校验有效起运地
		List<BaseDeparture> list = departureService.getNewestDeparture();
		boolean flag = false;
		for (BaseDeparture item : list){
			if (item.getId().equals(departureId)){
				flag = true;
				break;
			}
		}
		if (!flag){
			return JsonBuilder.build(1,"启运地在当前级别评价表中不存在",null);
		}

		BaseConsignor consignor = consignorService.selectById(consignorId);
		if (consignor == null){
			return JsonBuilder.build(1,"货主不存在",null);
		}
		//一个货主一月3次一年24次限制
		StringBuilder msg = new StringBuilder();
		if (!deliverService.checkConsignorActive(deliver.getSupplierId(),consignorId,msg)){
			return JsonBuilder.build(1,msg.toString(),null);
		}
		WhWeigh whWeigh = new WhWeigh();
		whWeigh.setDeliverId(deliverId);
		whWeigh.setArriveWeigh(new BigDecimal(weigh));
		whWeigh.setArriveWeighTime(new Date());
		whWeigh.setGpsPicture(gpsPicture);
		BaseEmployee employee = employeeService.getEmployeeByUserId(user.getId());
		if (employee != null){
			whWeigh.setEmployeeId(employee.getId());
		}else {
			whWeigh.setEmployeeId(null);
		}
		whWeigh.setWeighPerson(user.getUserName());

		WhDeliver whDeliver = new WhDeliver();
		whDeliver.setId(deliverId);
		whDeliver.setConsignorId(consignorId);
		whDeliver.setConsignorName(consignor.getConsignorName());
		whDeliver.setDepartureId(departureId);
		whDeliver.setDepartureName(departure.getdName());
		whDeliver.setGoodsId(goodsId);
		whDeliver.setGoodsName(goodsName);
		if (!goodsName.equals("废钢")) {
			whDeliver.setFlag(3);
		}else {
			whDeliver.setFlag(2);
		}
		if (weighService.arriveWeigh(whDeliver, whWeigh)){
			return JsonBuilder.build(0,deliver.getCarNumber()+"称毛重成功",whWeigh);
		}else {
			return JsonBuilder.build(1,deliver.getCarNumber()+"称毛重失败",null);
		}
	}

	/**
	 * 修改启运地
	 * @param deliverForm
	 * @return
	 */
	@RequestMapping(value = "editdepart",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject editWeigh(@RequestBody WhDeliverForm deliverForm){
		WhDeliver deliver = deliverService.getDeliverById(deliverForm.getDeliverId());
		if (deliver == null){
			return JsonBuilder.build(1,"送货单不存在",null);
		}
		if (deliver.getFlag() == 0){
			return JsonBuilder.build(1,"未打印送货单",null);
		}else if (deliver.getFlag() < 2){
			return JsonBuilder.build(1,"未入场称重",null);
		}else if (deliver.getFlag() == 5){
			return JsonBuilder.build(1,"已结算不能修改",null);
		}
		if (deliver.getCheckFlag() == 1){
			return JsonBuilder.build(1,"已审核",null);
		}
		//校验起运地是否存在
		BaseDeparture departure = departureService.selectByPrimaryKey(deliverForm.getDepartureId());
		if (departure == null){
			return JsonBuilder.build(1,"启运地不存在",null);
		}

		//校验有效起运地
		List<BaseDeparture> list = departureService.getNewestDeparture();
		boolean flag = false;
		for (BaseDeparture item : list){
			if (item.getId().equals(deliverForm.getDepartureId())){
				flag = true;
				break;
			}
		}
		if (!flag){
			return JsonBuilder.build(1,"启运地在当前级别评价表中不存在",null);
		}
		WhWeigh t_weigh = weighService.getWeighByDeliverId(deliver.getId());
		if (t_weigh == null){
			return JsonBuilder.build(1,"没有入场称重无法修改GPS",null);
		}
		WhWeigh weigh = new WhWeigh();
		weigh.setGpsPicture(deliverForm.getGpsPicture());
//		weigh.setDeliverId(deliver.getId());
		weigh.setId(t_weigh.getId());
		deliverForm.setId(deliverForm.getDeliverId());
		deliverForm.setDepartureId(deliverForm.getDepartureId());
		deliverForm.setDepartureName(deliverForm.getDepartureName());
		if (weighService.arriveEditWeigh(deliverForm,weigh)){
			return JsonBuilder.build(0,deliver.getDeliverNo()+" 起运地修改成功",deliver);
		}else {
			return JsonBuilder.build(1,deliver.getDeliverNo()+" 起运地修改失败",null);
		}
	}


	@RequestMapping(value = "leave_weigh",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject leaveWeigh(@RequestBody JSONObject jsonObject){
		Long deliverId = jsonObject.getLong("deliverId");
		String weigh = jsonObject.getString("weigh");
		long placeId = jsonObject.getLong("placeId");
		String placeName = jsonObject.getString("placeName");
		WhDeliver deliver = deliverService.getDeliverById(deliverId);
		if (deliver == null){
			return JsonBuilder.build(1,"送货单不存在",null);
		}
		if (!deliver.getGoodsName().equals("废钢")) {
			if (deliver.getFlag() != 3) {
				return JsonBuilder.build(1, "评铁未完成", null);
			}
		}
		//计算单价
//		WhDeliver carSettle = deliverService.getCarSettle(deliverId);
//		if (carSettle == null){
//			deliver.setContractPrice(new BigDecimal(0));
//		}else{
//			deliver.setContractPrice(carSettle.getContractPrice());
//		}
		WhWeigh oldWeigh = weighService.getWeighByDeliverId(deliverId);
		WhWeigh whWeigh = new WhWeigh();
		whWeigh.setId(oldWeigh.getId());
		whWeigh.setLeaveWeigh(new BigDecimal(weigh));
		whWeigh.setLeaveWeighTime(new Date());
		whWeigh.setNetWeigh(oldWeigh.getArriveWeigh().subtract(whWeigh.getLeaveWeigh()));
		whWeigh.setDeliverId(deliverId);

		deliver.setPersonId(placeId);
		deliver.setPersonName(placeName);
		if (weighService.leaveWeigh(whWeigh,deliver)){
			return JsonBuilder.build(0,deliver.getCarNumber()+"称皮重成功",whWeigh);
		}else {
			return JsonBuilder.build(1,deliver.getCarNumber()+"称皮重失败",null);
		}
	}

	/**
	 * 船对应的卸货车入场称重
	 */
	@RequestMapping(value = "inShipWeight", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject inShipWeight(@RequestBody WhDeliveryDetailsForm details){
		if (StringUtils.isBlank(details.getCarNo())) {
			return JsonBuilder.build(1, "参数不存在",null);
		}
		WhDeliver deliver = deliverService.getDeliverById(details.getDeliverId());
		if (deliver == null) {
			return JsonBuilder.build(1, "送货单不存在",null);
		}
//		if (deliver.getFlag() < 1) {
//			 return JsonBuilder.build(1, "送货单未打印", null);
//		} else
		if (deliver.getFlag() > 4){
			return JsonBuilder.build(1,deliver.getDeliverNo()+"已结算",null);
		}
		WhDeliveryDetails t_detail = detailsService.selectDetailByDeliverId(details.getCarNo(), details.getDeliverId());
		if (t_detail != null) {
			if (t_detail.getFlag() < 3 ) {
				return JsonBuilder.build(1, t_detail.getCarNo()+"已入场称重", null);
			}
		}
		details.setPlanStartDate(new Date());
		details.setFlag(1);
		details.setMark(0);
		if (weighService.addGrossWeight(details)) {
			return JsonBuilder.build(0, "入场称重成功",details.getGrossWeight());
		} else {
			return JsonBuilder.build(1, "入场称重失败",null);
		}
	}

	/**
	 * 船对应的卸货车:出场设置皮重和卸货地
	 */
	@RequestMapping(value = "outShipWeight", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject outShipWeight(@RequestBody JSONObject jsonObject){
		WhDeliveryDetails details = JSONTool.getObject(jsonObject.toString(),WhDeliveryDetails.class);
		boolean flag = !StringUtils.isBlank(details.getCarNo()) && details.getDeliverId() != null && details.getPlanStartDate() != null;
		if (!flag) {
			return JsonBuilder.build(1, "参数错误,车编号/送货单/入场称重时间必填",null);
		}

		if (StringUtils.isBlank(details.getPlaceName()) && details.getPlaceId() == null) {
			return JsonBuilder.build(1, "装卸地必填",null);
		}

		WhDeliver deliver = deliverService.getDeliverById(details.getDeliverId());
		if (deliver == null) {
			return JsonBuilder.build(1, "送货单不存在",null);
		}
		if (deliver.getFlag() >4){
			return JsonBuilder.build(1,deliver.getDeliverNo()+"已结算",null);
		}
//		if (deliver.getFlag() < 1) {
//			return JsonBuilder.build(1, "送货单未打印", null);
//		}
		if (details.getTareWeight() == null){
			return JsonBuilder.build(1,"皮重为空",null);
		}
//		WhDeliveryDetails t_detail = detailsService.selectDetailByFlag(details.getCarNo(), details.getDeliverId());
//		if (t_detail != null) {
//			if (t_detail.getFlag() == 3) {
//				return JsonBuilder.build(1, "已离场称重", null);
//			}
//		}
//		if (deliver.getValueMethod() == 1) {
//			WhDeliveryDetails detailById = detailsService.getDetailById(details.getDeliverId());
//			if (detailById.getMark() == 1){
//				return JsonBuilder.build(1,"已称毛重",null);
//			}
//			details.setMark(1);
//		}
		details.setPlanEndDate(new Date());
		details.setFlag(3);
		if (weighService.setTareWeighAndPlace(details)) {
			return JsonBuilder.build(0, "离场称重成功",details);
		} else {
			return JsonBuilder.build(1, "离场称重失败",null);
		}
	}



}
