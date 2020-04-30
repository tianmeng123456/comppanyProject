package com.boyo.wuhang.controller.assess;

import com.boyo.wuhang.component.interceptor.LoginInterceptor;
import com.boyo.wuhang.entity.WhDeliverForm;
import com.boyo.wuhang.entity.assess.WhDeliver;
import com.boyo.wuhang.entity.assess.WhDeliveryDetails;
import com.boyo.wuhang.entity.assess.WhWeigh;
import com.boyo.wuhang.entity.base.BaseSupplier;
import com.boyo.wuhang.entity.base.BaseUser;
import com.boyo.wuhang.entity.base.WhCarRecord;
import com.boyo.wuhang.service.assess.AccessService;
import com.boyo.wuhang.service.assess.DeliverService;
import com.boyo.wuhang.service.assess.WeighService;
import com.boyo.wuhang.service.assess.WhDeliveryDetailsService;
import com.boyo.wuhang.service.base.BaseSupplierService;
import com.boyo.wuhang.service.base.DepartureService;
import com.boyo.wuhang.service.base.WhCarRecordService;
import com.boyo.wuhang.ultity.JSONTool;
import com.boyo.wuhang.ultity.JsonBuilder;
import com.boyo.wuhang.ultity.Pager;
import com.boyo.wuhang.ultity.Validator;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/deliver/")
public class DeliverController {
	@Autowired
	private DeliverService deliverService;
	@Autowired
	private BaseSupplierService supplierService;
	@Autowired
	private WhCarRecordService carRecordService;
	@Autowired
	private WhDeliveryDetailsService detailsService;
	@Autowired
	private AccessService accessService;
	@Autowired
	private DepartureService departureService;
	@Autowired
	private WeighService weighService;

	@RequestMapping(value = "list",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getDeliverList(@RequestBody WhDeliverForm deliverForm){
		Pager page = new Pager();
		page.setPageNumber(deliverForm.getPageNumber());
		page.setPageSize(deliverForm.getPageSize());
		page = deliverService.getDeliverList(page,deliverForm);
		return JsonBuilder.build(0,"",page);
	}

	//查询各个状态下的送货单，根据车牌选择送货单
	@RequestMapping(value = "search",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject searchDeliver(@RequestBody JSONObject jsonObject){
//		Integer flag = jsonObject.getInt("flag");
		WhDeliverForm deliver = JSONTool.getObject(jsonObject.toString(), WhDeliverForm.class);
		if (deliver.getFlag() > 3){
			return JsonBuilder.build(1,"参数不正确",null);
		}
		List<Map<String,Object>> list = deliverService.getDeliverByFlag(deliver);
		return JsonBuilder.build(0,"",list);
	}

	//查询入场称重后的船只,根据船号选择送货单
	@RequestMapping(value = "shipsearch",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getShipDeliverByFlag(@RequestBody WhDeliverForm deliverForm){
//		Integer flag = jsonObject.getInt("flag");
//		if (flag > 3){
//			return JsonBuilder.build(1,"参数不正确",null);
//		}
		List<Map<String,Object>> list = deliverService.getShipDeliverByFlag(deliverForm);
		return JsonBuilder.build(0,"",list);
	}

//	@RequestMapping(value = "arrive",method = RequestMethod.POST)
//	@ResponseBody
//	public JSONObject carArrive(@RequestBody JSONObject jsonObject){
//		List<Map<String,Object>> list = deliverService.getDeliverByFlag(0);
//		if (list.size() > 1){
//			return JsonBuilder.build(1,"存在车辆到场未打印送货单，请先打印之前车辆的送货单",null);
//		}
//		WhDeliver deliver = JSONTool.getObject(jsonObject.toString(),WhDeliver.class);
//		if (deliver == null){
//			return JsonBuilder.build(1,"参数不存在",null);
//		}
//		deliver.setArriveTime(new Date());
//		if (StringUtils.isBlank(deliver.getCarNumber())){
//			return JsonBuilder.build(1,"车牌号不存在",null);
//		}
//		deliver.setWeigh(null);
//		deliver.setFlag(0);
//		deliver.setMark(0);
//		if (deliverService.insertDeliver(deliver)){
//			//校验是否提交证件
//			WhCarRecord carRecord = carRecordService.getCarRecordByNo(deliver.getCarNumber());
//			String msg = deliver.getCarNumber()+ "车辆入场成功";
//			if (carRecord == null){
//				msg = msg + "，未上传证件";
//			}
//			return JsonBuilder.build(0, msg,null);
//		}else {
//			return JsonBuilder.build(1,deliver.getCarNumber()+"车辆入场失败",null);
//		}
//	}

	//司机扫描二维码提交供应商电话找到供应商，如果没有驾驶证行驶证，第一次上传一次
//	@RequestMapping(value = "write")
//	@ResponseBody
//	public JSONObject writeDeliver(@RequestBody JSONObject jsonObject){
//		WhDeliver deliver = JSONTool.getObject(jsonObject.toString(),WhDeliver.class);
//		WhDeliver t_deliver = deliverService.getDeliverById(deliver.getId());
//		if (t_deliver == null){
//			return JsonBuilder.build(1,"送货单不存在",null);
//		}
//		if (t_deliver.getFlag() > 0){
//			return JsonBuilder.build(1,"送货单已打印",null);
//		}
//		BaseSupplier supplier = supplierService.getSupplierById(deliver.getSupplierId());
//		if (supplier == null){
//			return JsonBuilder.build(1,"供应商不存在",null);
//		}
//		if (StringUtils.isBlank(deliver.getGoodsName())){
//			return JsonBuilder.build(1,"货物名不存在",null);
//		}
//		deliver.setWeigh(null);
//		deliver.setFlag(null);
//		deliver.setMark(null);
//		if (deliverService.updateDeliver(deliver)){
//			return JsonBuilder.build(0,"送货单填写资料成功",null);
//		}else {
//			return JsonBuilder.build(1,"送货单填写资料失败",null);
//		}
//	}

	@RequestMapping(value = "print",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject printDeliver(@RequestBody JSONObject jsonObject){
		Long id = jsonObject.getLong("id");
		WhDeliver deliver = deliverService.getDeliverById(id);
		if (deliver == null){
			return JsonBuilder.build(1,"打印的送货单不存在",null);
		}
		if (deliver.getFlag() != 0){
			return JsonBuilder.build(1,"已打印送货单",null);
		}
		WhDeliver updateDeliver = new WhDeliver();
		updateDeliver.setId(deliver.getId());
		updateDeliver.setFlag(1);
		if (deliverService.updateDeliver(updateDeliver)){
			return JsonBuilder.build(0,"打印送货单成功",deliver);
		}else {
			return JsonBuilder.build(1,"打印送货单失败",null);
		}
	}

	@RequestMapping(value = "edit",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateDeliver(@RequestBody WhDeliver deliver){
		WhDeliver t_deliver = deliverService.getDeliverById(deliver.getId());
		if (t_deliver == null){
			return JsonBuilder.build(1,"送货单不存在",null);
		}
		if (t_deliver.getFlag() == 6){
			return JsonBuilder.build(1,"已结算,无法修改",null);
		}
		if (t_deliver.getMark() == 0) {
//			if (t_deliver.getFlag() > 0){
//				return JsonBuilder.build(1,"送货单已打印",null);
//			}
			WhCarRecord carRecordByNo = carRecordService.getCarRecordByNo(deliver.getCarNumber());
			if (carRecordByNo == null) {
				return JsonBuilder.build(1, "该车牌没有证件,请上传证件", null);
			}
			if (!Validator.isCarNumber(deliver.getCarNumber())){
				return JsonBuilder.build(1,"车牌号不规范",null);
			}
			//修改时判断车辆是否在场
//			List<WhDeliver> carNumber = deliverService.getDeliverByCarNumber(deliver.getCarNumber());
//			if (carNumber != null){
//				return JsonBuilder.build(1,deliver.getCarNumber()+"已在场内,请更换或等待出场",null);
//			}
		}
        if (deliver.getSupplierId() != null) {
			BaseSupplier supplierById = supplierService.getSupplierById(deliver.getSupplierId());
			if (supplierById == null) {
				return JsonBuilder.build(1, "供应商不存在", null);
			}
		}

		if (deliverService.updateDeliver(deliver)){
			return JsonBuilder.build(0,"修改送货单成功",deliver);
		}else{
			return JsonBuilder.build(1,"修改送货单失败",null);
		}
	}

	/**
	 * 修改单价、金额
	 * @param deliverForm
	 * @return	修改后该送货单不在结算列表出现
	 */
	@RequestMapping(value = "edit_price",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject editPrice(@RequestBody WhDeliverForm deliverForm){
		BaseUser user = LoginInterceptor.user.get();
		WhDeliver deliver = deliverService.getDeliverById(deliverForm.getDeliverId());
		if (deliver.getFlag() > 5){
			return JsonBuilder.build(1,"已完成,无法修改",null);
		}
		if (deliver.getSettleFlag() > 0){
		    return JsonBuilder.build(1,"已结算,无法修改",null);
        }
		deliverForm.setContractPrice(deliverForm.getContractPrice());
		deliverForm.setContractAmount(deliverForm.getContractAmount());
		deliverForm.setEditFlag(1);
		deliverForm.setId(deliver.getId());
		deliverForm.setPriceEditId(user.getId());
		deliverForm.setPriceEditName(user.getUserName());
		deliverForm.setPriceEditTime(new Date());
		if (deliverService.updateDeliver(deliverForm)){
			return JsonBuilder.build(0,"修改成功",deliverForm);
		}else{
			return JsonBuilder.build(1,"修改失败",null);
		}
	}

	@RequestMapping(value = "add",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addDeliver(@RequestBody JSONObject jsonObject){
//		String tel = jsonObject.getString("tel");
		WhDeliver deliver = JSONTool.getObject(jsonObject.toString(),WhDeliver.class);
		if (deliver == null){
			return JsonBuilder.build(1,"参数不存在",null);
		}
		deliver.setArriveTime(new Date());
		BaseSupplier supplier = supplierService.getSupplierById(deliver.getSupplierId());
		if (supplier == null){
			return JsonBuilder.build(1,"供应商不存在",null);
		}
		deliver.setSupplierId(supplier.getId());
		//校验是否提交证件
		WhCarRecord carRecord = carRecordService.getCarRecordByNo(deliver.getCarNumber());
		if (carRecord == null){
			return JsonBuilder.build(1,"请上传证件",null);
		}
		List<WhDeliver> carNumberList = deliverService.getDeliverByCarNumber(deliver.getCarNumber());
		for (WhDeliver item : carNumberList){
			if (item.getFlag() < 4){
				return JsonBuilder.build(1,item.getCarNumber()+"已有未完成的送货单",item);
			}
		}
		//默认起运地
//		BaseDeparture departure = departureService.getDepartureByFlag(3);
//		deliver.setDepartureId(departure.getId());
//		deliver.setDepartureName(departure.getdName());

		deliver.setWeigh(null);
		deliver.setFlag(0);
		deliver.setMark(0);
		if (deliverService.insertDeliver(deliver)){
			return JsonBuilder.build(0, deliver.getCarNumber()+"车辆入场成功",deliver);
		}else {
			return JsonBuilder.build(1,deliver.getCarNumber()+"车辆入场失败",null);
		}
	}

	/**
	 * 船只入场
	 */
	@RequestMapping(value = "shipArrive", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject shipArrive(@RequestBody WhDeliver deliver){
		if (deliver == null){
			return JsonBuilder.build(1,"参数不存在",null);
		}
		BaseSupplier supplier = supplierService.getSupplierById(deliver.getSupplierId());
		if (supplier == null){
			return JsonBuilder.build(1,"供应商不存在",null);
		}

//		if (StringUtils.isBlank(deliver.getContractNo())) {
//			return JsonBuilder.build(1, "合同号必填", null);
//		}

		if (StringUtils.isBlank(deliver.getDepartureName()) || deliver.getDepartureId() == null) {
			 return JsonBuilder.build(1,"启运运地必填",null);
		}
//		deliver.setConsignorId(deliver.getConsignorId());
		deliver.setWeigh(null);
		deliver.setFlag(1);
		deliver.setMark(1);
		if (deliverService.insertDeliver(deliver)){
			String msg = deliver.getCarNumber() + "船只入场成功";
			return JsonBuilder.build(0, msg, deliver);
		} else {
			return JsonBuilder.build(1,"船只入场失败", null);
		}
	}

	/**
	 * 添加船对应卸货的车
	 */
	@RequestMapping(value = "addShipDetail", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addShipDetail(@RequestBody JSONObject jsonObject){
		WhDeliveryDetails deliveryDetails = JSONTool.getObject(jsonObject.toString(), WhDeliveryDetails.class);

		if (deliveryDetails == null){
			return JsonBuilder.build(1,"参数不存在",null);
		}

		WhDeliver deliver = deliverService.getDeliverById(deliveryDetails.getDeliverId());
		if (deliver == null){
			return JsonBuilder.build(1,"送货单号不存在", null);
		}

		int endFlag = 5;
		if (deliver.getFlag() >= endFlag) {
			return JsonBuilder.build(1,"流程结束不能添加卸货车", null);
		}

		deliveryDetails.setFlag(0);
		if (!deliverService.insertDeliverDetail(deliveryDetails)){
			return JsonBuilder.build(1, "添加卸货车失败",null);
		} else {
			return JsonBuilder.build(0, "添加卸货车成功",null);
		}
	}

	/**
	 * 获取未结算船对应的送货单
	 */
	@RequestMapping(value = "shipNonSettleList")
	@ResponseBody
	public JSONObject shipNonSettleList(@RequestBody JSONObject jsonObject) {
		WhDeliverForm object = JSONTool.getObject(jsonObject.toString(), WhDeliverForm.class);
		if (object == null) {
			return JsonBuilder.build(1, "参数错误",null);
		}
		object.setFlag(5);
		object.setMark(1);
		List<WhDeliverForm> data = deliverService.getShipNonSettleList(object);
		return JsonBuilder.build(0,"成功",data);
	}

//	@RequestMapping(value = "delShipDetail", method = RequestMethod.POST)
//	@ResponseBody
//	public JSONObject delShipDetail(@RequestBody JSONObject jsonObject){
//		String id = jsonObject.getString("id");
//		if (StringUtils.isEmpty(id)){
//			return JsonBuilder.build(1, "删除卸货车操作失败", null);
//		}
//
//		if (deliverService.delDeliverDetail(Long.parseLong(id))){
//			return JsonBuilder.build(0, "删除拉卸货车成功", null);
//		} else {
//			return JsonBuilder.build(1, "删除卸货车失败", null);
//		}
//	}

//	@RequestMapping(value = "updateShipDetail", method = RequestMethod.POST)
//	@ResponseBody
//	public JSONObject updateShipDetail(@RequestBody JSONObject jsonObject) {
//		WhDeliveryDetails deliveryDetails = JSONTool.getObject(jsonObject.toString(), WhDeliveryDetails.class);
//		if (deliveryDetails == null || deliveryDetails.getId() == null) {
//			return JsonBuilder.build(1, "参数不存在", null);
//		}
//
//		if (deliverService.updateDeliverDetail(deliveryDetails)){
//			return JsonBuilder.build(0, "修改卸货车成功", null);
//		} else {
//			return JsonBuilder.build(1, "修改卸货车失败", null);
//		}
//	}

	/**
	 * 获取船对应卸货车列表
	 */
	@RequestMapping(value = "shipDetailList", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject shipDetailList(@RequestBody JSONObject jsonObject){
		Pager page = new Pager();
		page.setPageNumber(Integer.parseInt(jsonObject.getString("pageNumber")));
		page.setPageSize(Integer.parseInt(jsonObject.getString("pageSize")));
		jsonObject.remove("pageNumber");
		jsonObject.remove("pageSize");
		WhDeliveryDetails deliveryDetails = JSONTool.getObject(jsonObject.toString(),WhDeliveryDetails.class);

		Pager pager = deliverService.getDeliverDetails(deliveryDetails, page);
		return JsonBuilder.build(0,"获取卸货车列表成功",pager);
	}

	/**
	 * 获取船对应卸货车
	 */
	@RequestMapping(value = "getNewShipDetail", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getShipDetail(@RequestBody WhDeliveryDetails details){
		WhDeliveryDetails deliveryDetails = deliverService.getNewDetail(details);
		if(deliveryDetails == null){
			return JsonBuilder.build(1,"获取失败",deliveryDetails);
		} else {
			return JsonBuilder.build(0,"获取成功",deliveryDetails);
		}
	}

	/**
	 * 船的结束(统计净重,计算价格)   重复操作会重复生成
	 */
	@RequestMapping(value = "shipfinish",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject finishDeliver(@RequestBody JSONObject jsonObject){
		WhDeliverForm deliver = JSONTool.getObject(jsonObject.toString(), WhDeliverForm.class);

		WhDeliver whDeliver = deliverService.getDeliverById(deliver.getDeliverId());
		if (whDeliver.getMark() == 0) {
			return JsonBuilder.build(1, "不能完成车的订单",null);
		}
		if (whDeliver.getFlag() > 4) {
			return JsonBuilder.build(1,"不能重复操作", null);
		}
		WhDeliveryDetails deliverDetail = detailsService.getStartDetail(deliver.getDeliverId());
		if (deliverDetail == null){
			return JsonBuilder.build(1,"没有称毛重,无法完成",null);
		}
		if (deliverDetail.getGrossWeight() == null){
		    return JsonBuilder.build(1,"没有称毛重,无法完成",null);
        }
		if (deliverDetail.getTareWeight() == null){
			return JsonBuilder.build(1,"皮重为空,无法完成",null);
		}
		if (deliverService.getNetWeight(deliver)){
			String msg = whDeliver.getDeliverNo()+" 完成";
			return JsonBuilder.build(0,msg,deliver);
		}else{
			return JsonBuilder.build(1,"失败",null);
		}
	}

	/**
	 * 现场车辆状态列表
	 * @param deliverForm
	 * @return
	 */
	@RequestMapping(value = "statuslist",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getDeliverStatusList(@RequestBody WhDeliverForm deliverForm){
		Pager page = new Pager();
		page.setPageNumber(deliverForm.getPageNumber());
		page.setPageSize(deliverForm.getPageSize());
		page = deliverService.getDeliverStatusList(deliverForm,page);
		return JsonBuilder.build(0,"",page);
	}

	/**
	 * 车辆结束
	 * 0 刚入场 1 打印 2入场称重 3 评铁单 4 离场称重 5 完成订单(计算平均单价)
	 * @param deliver
	 * @return
	 */
	@RequestMapping(value = "carfinish",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getCarSettle(@RequestBody WhDeliverForm deliver){
		WhDeliver deliverById = deliverService.getDeliverById(deliver.getDeliverId());
		if (deliverById == null){
			return JsonBuilder.build(1,"送货单不存在,无法完成",null);
		}
		if (deliverById.getMark() != 0){
			return JsonBuilder.build(1,"非车辆无法完成订单",null);
		}
		if (deliverById.getFlag() < 4){
			return JsonBuilder.build(1,"未离场称重,无法完成",null);
		}
		if (deliverById.getFlag() == 5){
			return JsonBuilder.build(1,"订单已完成",null);
		}
		if (deliverById.getDepartureId() == null){
			return JsonBuilder.build(1,"起运地为空,无法完成",null);
		}
		deliver.setId(deliver.getDeliverId());
		deliver.setFlag(5);
//		deliver.setEditFlag(1);
		if (deliverService.updateDeliver(deliver)){
			return JsonBuilder.build(0,"完成订单",deliver);
		}else{
			return JsonBuilder.build(1,"完成失败",null);
		}
	}

	/**
	 * 车辆计算单价
	 * @param deliverForm
	 * @return
	 */
	@RequestMapping(value = "countPrice",method = RequestMethod.POST)
    @ResponseBody
	public JSONObject countPrice(@RequestBody WhDeliverForm deliverForm){
            List<WhDeliverForm> carSettle = deliverService.getCarSettle(deliverForm.getDeliverId());
            if (carSettle.size() == 0){
			    deliverForm.setContractPrice(new BigDecimal(0));
    		}else{
            	BigDecimal sumPrice = new BigDecimal(0);
    			for (WhDeliverForm d : carSettle){
					BigDecimal contractPrice = d.getContractPrice();
					BigDecimal t_sumPrice = contractPrice.add(contractPrice);
					sumPrice = t_sumPrice;
				}
    			deliverForm.setContractPrice(sumPrice);
    		}
        WhDeliver deliver = deliverService.getDeliverById(deliverForm.getDeliverId());
            if (deliver == null){
                return JsonBuilder.build(1,"送货单不存在",null);
            }
            if (deliver.getSettleFlag() > 0){
                return JsonBuilder.build(1,"已结算",null);
            }
            if (deliver.getFlag() == 5){
            	return JsonBuilder.build(1,"已完成",null);
			}
            deliverForm.setId(deliver.getId());
        if (deliverService.updateDeliver(deliverForm)){
            return JsonBuilder.build(0,"计算单价成功",deliverForm);
        }else{
            return JsonBuilder.build(1,"计算单价失败",null);
        }
    }

    /**
     * 结算送货单(计算金额)
     * @param deliverForm
     * @return
     */
	@RequestMapping(value = "settle",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject settlement(@RequestBody WhDeliverForm deliverForm){
		BaseUser user = LoginInterceptor.user.get();
		WhDeliver deliverById = deliverService.getDeliverById(deliverForm.getDeliverId());
		if (deliverById.getSettleFlag() > 0){
			return JsonBuilder.build(1,"已结算",null);
		}
		if (deliverById.getEditFlag() > 1) {
			if (deliverById.getFlag() < 5) {
				return JsonBuilder.build(1, "订单未完成,无法结算", null);
			}
		}
		if (deliverById.getContractPrice() == null){
			return JsonBuilder.build(1,"请先计算单价",null);
		}
		List<WhDeliverForm> carSettle = deliverService.getCarSettle(deliverForm.getDeliverId());
		if (carSettle.size() == 0){
//			WhAccess access = accessService.getAccessByDeliverId(deliverForm.getDeliverId());
			WhWeigh weigh = weighService.getWeighByDeliverId(deliverForm.getDeliverId());
			//单价*(净重-扣杂)
			deliverForm.setContractAmount(deliverById.getContractPrice().multiply(deliverById.getWeigh().subtract(weigh.getImpurities())));
		}else{
			BigDecimal sumAmount = new BigDecimal(0);
			for (WhDeliverForm d : carSettle){
				BigDecimal contractAmount = d.getContractAmount();
				BigDecimal t_sumAmount = contractAmount.add(sumAmount);
				sumAmount = t_sumAmount;
			}
			deliverForm.setContractAmount(sumAmount);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String no = sdf.format(new Date()) + String.format("%03d",deliverService.getTodayCount()+1);
		//生成结算单
		deliverForm.setId(deliverById.getId());
		deliverForm.setSettleFlag(1);
		if(deliverById.getSettleNo() == null) {
//			List<WhDeliver> deliverList = deliverForm.getDeliverList();
//			for (WhDeliver ids : deliverList){
//				WhDeliver deliver = new WhDeliver();
//				deliver.setId(ids.getId());
				deliverForm.setSettleNo(no);
//				deliverList.add(deliver);
//			}
//			deliverService.updateBatch(deliverList);
		}
		deliverForm.setSettleId(user.getId());
		deliverForm.setSettleName(user.getUserName());
		deliverForm.setSettleTime(new Date());
		if (deliverService.updateDeliver(deliverForm)){
			return JsonBuilder.build(0,"结算完成",deliverForm);
		}else{
			return JsonBuilder.build(1,"结算失败",null);
		}
	}

	/**
	 * 反结算
	 * @param deliverForm
	 * @return
	 */
    @RequestMapping(value = "unsettle",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject unSettlement(@RequestBody WhDeliverForm deliverForm){
        WhDeliver deliverById = deliverService.getDeliverById(deliverForm.getDeliverId());
        if(deliverById.getSettleFlag() != 1){
            return JsonBuilder.build(1,"订单未结算,无需返结算",null);
        }
        deliverForm.setId(deliverById.getId());
        deliverForm.setContractAmount(new BigDecimal(0));
        deliverForm.setSettleFlag(0);
        if (deliverService.updateDeliver(deliverForm)){
            return JsonBuilder.build(0,"已返结算",deliverForm);
        }else{
            return JsonBuilder.build(1,"返算失败",null);
        }
    }

    /**
     * 修改起运地审核操作
     * @param deliverForm
     * @return
     */
    @RequestMapping(value = "checkflag",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject checkFlag(@RequestBody WhDeliverForm deliverForm){
        WhDeliver deliver = deliverService.getDeliverById(deliverForm.getDeliverId());
        if (deliver == null){
            return JsonBuilder.build(1,"送货单不存在",null);
        }
        if (deliver.getFlag() > 4){
            return JsonBuilder.build(1,"送货单已完成",null);
        }
        deliverForm.setId(deliver.getId());
        deliverForm.setCheckFlag(1);
        if (deliverService.updateDeliver(deliverForm)){
	        return JsonBuilder.build(0,"审核成功",null);
        }else{
	        return JsonBuilder.build(1,"审核失败",null);
        }
    }

	/**
	 * 修改起运地反审核
	 * @param deliverForm
	 * @return
	 */
    @RequestMapping(value = "uncheckflag",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject unCheckFlag(@RequestBody WhDeliverForm deliverForm){
        WhDeliver deliver = deliverService.getDeliverById(deliverForm.getDeliverId());
        if (deliver == null){
            return JsonBuilder.build(1,"送货单不存在",null);
        }
        if (deliver.getFlag() > 4){
            return JsonBuilder.build(1,"送货单已完成",null);
        }
        deliverForm.setId(deliver.getId());
        deliverForm.setCheckFlag(0);
        if (deliverService.updateDeliver(deliverForm)){
            return JsonBuilder.build(0,"返审核成功",null);
        }else{
            return JsonBuilder.build(1,"返审核失败",null);
        }
    }

	/**
	 * 结算单列表
	 * @param deliverForm
	 * @return
	 */
	@RequestMapping(value = "settlelist",method = RequestMethod.POST)
	@ResponseBody
    public JSONObject getSettleList(@RequestBody WhDeliverForm deliverForm){
		Pager page = new Pager();
		page.setPageNumber(deliverForm.getPageNumber());
		page.setPageSize(deliverForm.getPageSize());
		page = deliverService.getSettleList(deliverForm,page);
		return JsonBuilder.build(0,"",page);
	}


}
