package com.boyo.wuhang.controller.assess;

import com.boyo.wuhang.dao.base.BaseGoodsMapper;
import com.boyo.wuhang.entity.WhAccessForm;
import com.boyo.wuhang.entity.assess.*;
import com.boyo.wuhang.entity.base.BaseGoods;
import com.boyo.wuhang.service.assess.*;
import com.boyo.wuhang.ultity.JSONTool;
import com.boyo.wuhang.ultity.JsonBuilder;
import com.boyo.wuhang.ultity.Pager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/api/access")
public class AccessController {
	@Autowired
	private AccessService accessService;
	@Autowired
	private RankEvaluationService rankEvaluationService;
	@Autowired
	private DeliverService deliverService;
	@Autowired
	private WeighService weighService;
	@Autowired
	private BaseGoodsMapper goodsMapper;
	@Autowired
	private WhDeliveryDetailsService detailsService;

	@RequestMapping(value = "list",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getAccessList(@RequestBody WhAccessForm accessForm){
		Pager page = new Pager();
		page.setPageNumber(accessForm.getPageNumber());
		page.setPageSize(accessForm.getPageSize());
		page = accessService.getAccessList(page,accessForm);
		return JsonBuilder.build(0,"",page);
	}

	//根据送货单的入场称重时间从级别评价表拉取明细
	@RequestMapping(value = "get_rank",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getExecRankList(@RequestBody JSONObject jsonObject){
		Long deliverId = jsonObject.getLong("deliverId");
		WhDeliver deliver = deliverService.getDeliverById(deliverId);
		if (deliver == null){
			return JsonBuilder.build(1,"送货单不存在",null);
		}
		if (deliver.getFlag() < 2){
			return JsonBuilder.build(1,"该送货单未称重",null);
		}else if (deliver.getFlag() > 3){
			return JsonBuilder.build(1,"该送货单已完成评铁",null);
		}
		WhWeigh whWeigh = weighService.getWeighByDeliverId(deliverId);
		List<Map<String,Object>> list = rankEvaluationService.getExecRankList(whWeigh.getArriveWeighTime());
		if (list.size() == 0){
			return JsonBuilder.build(1,"该称重时刻没有有效的级别评价表",null);
		}
		return JsonBuilder.build(0,"",list);
	}

	@RequestMapping(value = "get_ship_rank",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getShipExecRankList(@RequestBody JSONObject jsonObject){
		Long deliverId = jsonObject.getLong("deliverId");
		WhDeliver deliver = deliverService.getDeliverById(deliverId);
		if (deliver == null){
			return JsonBuilder.build(1,"送货单不存在",null);
		}
		if (deliver.getArriveTime() == null){
			return JsonBuilder.build(1,"该送货单未称重",null);
		}else if (deliver.getFlag() > 3){
			return JsonBuilder.build(1,"该送货单已完成评铁",null);
		}
//		WhWeigh whWeigh = weighService.getWeighByDeliverId(deliverId);
		//通过送货单id查询多车明细列表 获取车辆入场时间查询有效评价表
		WhDeliveryDetails detail = accessService.getShipRank(deliverId);
		List<Map<String,Object>> list = rankEvaluationService.getExecRankList(detail.getPlanStartDate());
		if (list.size() == 0){
			return JsonBuilder.build(1,"该称重时刻没有有效的级别评价表",null);
		}
		return JsonBuilder.build(0,"",list);
	}


	@RequestMapping(value = "create",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insertAccess(@RequestBody JSONObject jsonObject){
		JSONArray jsonArray = jsonObject.getJSONArray("detailList");
		List<WhAccessDetail> detailList = new ArrayList<>();
		for (int i = 0; i<jsonArray.size(); i++){
			JSONObject item = jsonArray.getJSONObject(i);
			WhAccessDetail accessDetail = JSONTool.getObject(item.toString(), WhAccessDetail.class);
			WhRankEvaluation rankEvaluation = rankEvaluationService.getRankEvaluation(accessDetail.getRankId());
			if (rankEvaluation == null){
				return JsonBuilder.build(1,"评铁单对应的级别行不存在",null);
			}
			accessDetail.setwRank(rankEvaluation.getwRank());
			accessDetail.setGrade(rankEvaluation.getGrade());
			accessDetail.setwLevel(rankEvaluation.getwLevel().toString());
			accessDetail.setOrd(null);
			detailList.add(accessDetail);
		}
		jsonObject.remove("detailList");
		WhAccess access = JSONTool.getObject(jsonObject.toString(), WhAccess.class);
		if (access == null){
			return JsonBuilder.build(1,"评铁单参数不存在",null);
		}
		WhDeliver deliver = deliverService.getDeliverById(access.getDeliverId());
		if (deliver == null){
			return JsonBuilder.build(1,"评铁单对应的送货单不存在",null);
		}
		if (deliver.getFlag() < 2){
			return JsonBuilder.build(1,"该送货单未称重",null);
		}else if (deliver.getFlag() > 3){
			return JsonBuilder.build(1,"该送货单已完成评铁",null);
		}
		WhWeigh whWeigh = weighService.getWeighByDeliverId(access.getDeliverId());
		List<Map<String,Object>> list = rankEvaluationService.getExecRankList(whWeigh.getArriveWeighTime());
		if (list.size() == 0){
			return JsonBuilder.build(1,"该送货单没有有效的级别评价表",null);
		}
		for (WhAccessDetail detail : detailList){
			Iterator<Map<String,Object>> iterator = list.iterator();
			while (iterator.hasNext()){
				Long rankId = Long.parseLong(iterator.next().get("rankId").toString());
				if (rankId.equals(detail.getRankId())){
					iterator.remove();
					break;
				}
			}
		}
		if (list.size() != 0){
			return JsonBuilder.build(1,"评铁单中的级别与该送货单有效的级别评价表不符",null);
		}
		if (access.getImpurities() == null){
			access.setImpurities(new BigDecimal(0));
		}
		BaseGoods goods = goodsMapper.selectByPrimaryKey(deliver.getGoodsId());
		String operationFlag = "废钢";
		if (!goods.getGoodsName().equals(operationFlag)) {
			return JsonBuilder.build(1,goods.getGoodsName()+"不用评铁", null);
		}
		//评铁单扣杂写入称重单
		WhWeigh weigh = new WhWeigh();
		weigh.setId(whWeigh.getId());
		weigh.setImpurities(access.getImpurities());

		access.setId(null);
		access.setFlag(0);
		access.setMark(0);
		if (accessService.insertAccess(access,detailList,weigh)){
			return JsonBuilder.build(0,"创建评铁单成功",access);
		}else {
			return JsonBuilder.build(1,"创建评铁单失败",null);
		}
	}

	@RequestMapping(value = "edit",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject editAccess(@RequestBody JSONObject jsonObject){
		List<WhAccessDetail> detailList = null;
		if (jsonObject.has("detailList")){
			JSONArray jsonArray = jsonObject.getJSONArray("detailList");
			detailList = new ArrayList<>();
			for (int i = 0; i<jsonArray.size(); i++){
				JSONObject item = jsonArray.getJSONObject(i);
				WhAccessDetail accessDetail = JSONTool.getObject(item.toString(), WhAccessDetail.class);
				WhAccessDetail detail = accessService.getAccessDetailByOrd(accessDetail.getOrd());
				if (detail == null){
					return JsonBuilder.build(1,"修改的评铁单明细不存在",accessDetail.getOrd());
				}
				accessDetail.setAccessId(detail.getAccessId());
				detailList.add(accessDetail);
			}
			jsonObject.remove("detailList");
		}
		WhAccess access = JSONTool.getObject(jsonObject.toString(), WhAccess.class);
		if (access == null){
			return JsonBuilder.build(1,"修改的评铁单参数不存在",null);
		}
		WhAccess t_access = accessService.getAccessById(access.getId());
		if (t_access == null){
			return JsonBuilder.build(1,"修改的评铁单不存在",null);
		}
//		if (t_access.getMark() > 1){
//			return JsonBuilder.build(1,"该送货单已有评铁单被采纳，无法修改",null);
//		}
		if (detailList != null){
			for (WhAccessDetail item : detailList){
				if (!item.getAccessId().equals(access.getId())){
					return JsonBuilder.build(1,"修改明细不属于该评铁单",item.getOrd());
				}
				item.setRankId(null);
				item.setwRank(null);
				item.setGrade(null);
				item.setwLevel(null);
				item.setAccessId(access.getId());
			}
		}
		WhDeliver deliver = deliverService.getDeliverById(t_access.getDeliverId());
		if (deliver.getFlag() == 5){
			return JsonBuilder.build(1,"已完成",null);
		}

		access.setDeliverId(null);
//		access.setEditPerson(null);
		access.setManagerId(null);
		access.setFlag(0);
		access.setMark(null);
		if (accessService.updateAccess(access,detailList)){
			return JsonBuilder.build(0,"修改评铁单成功",access);
		}else {
			return JsonBuilder.build(1,"修改评铁单失败",null);
		}
	}

	@RequestMapping(value = "delete",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteAccess(@RequestBody JSONObject jsonObject){
		Long id = Long.parseLong(jsonObject.getString("id"));
		WhAccess access = accessService.getAccessById(id);
		if (access == null){
			return JsonBuilder.build(1,"删除的评铁单不存在",null);
		}
		if (accessService.deleteAccess(access)){
			return JsonBuilder.build(0,"删除评铁单成功",null);
		}else {
			return JsonBuilder.build(1,"删除评铁单失败",null);
		}
	}

	/**
	 * 按当前一车一单流程,暂时弃用该功能
	 * 2020-04-24 10:25:50
	 * @param jsonObject
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "submit",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject submitAccess(@RequestBody JSONObject jsonObject) throws ParseException {
		Long id = Long.parseLong(jsonObject.getString("id"));
		WhAccess access = accessService.getAccessById(id);
		if (access == null){
			return JsonBuilder.build(1,"评铁单不存在",null);
		}
		WhAccess updateAccess = new WhAccess();
		updateAccess.setId(id);
		updateAccess.setSubmitTime(new Date());
		updateAccess.setFlag(1);
		if (accessService.updateAccess(updateAccess,null)){
			return JsonBuilder.build(0,"提交评铁单成功",access);
		}else {
			return JsonBuilder.build(1,"提交评铁单失败",null);
		}
	}




	/**
	 * 一单一车一评铁,设置flag=1,弃用提交功能,采用功能代替提交功能
	 * 2020-04-24 10:25:55
	 * @param jsonObject
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "adopt",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject adoptAccess(@RequestBody JSONObject jsonObject) throws ParseException {
		Long id = Long.parseLong(jsonObject.getString("id"));
		WhAccess access = accessService.getAccessById(id);
		if (access == null){
			return JsonBuilder.build(1,"评铁单不存在",null);
		}
		WhAccess updateAccess = new WhAccess();
		updateAccess.setId(id);
		updateAccess.setDeliverId(access.getDeliverId());
		updateAccess.setAdoptTime(new Date());
		updateAccess.setFlag(1);
		updateAccess.setMark(1);
		if (accessService.adoptAccess(updateAccess)){
//			return JsonBuilder.build(0,"采纳评铁单成功",access);
			return JsonBuilder.build(0,"提交评铁单成功",access);
		}else {
//			return JsonBuilder.build(1,"采纳评铁单失败",null);
			return JsonBuilder.build(1,"提交评铁单失败",null);
		}
	}

	/**
	 * 添加船对应的评铁单
	 */
	@RequestMapping(value = "addShipAccess", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addShipDetails(@RequestBody JSONObject jsonObject) {
		JSONArray jsonArray = jsonObject.getJSONArray("detailList");

		//添加评铁单
		jsonObject.remove("detailList");
		WhAccess access = JSONTool.getObject(jsonObject.toString(), WhAccess.class);
		if (access == null){
			return JsonBuilder.build(1,"评铁单参数不存在",null);
		}

		WhDeliver deliver = deliverService.getDeliverById(access.getDeliverId());
		if (deliver == null){
			return JsonBuilder.build(1,"评铁单对应的送货单不存在",null);
		}

//		if (deliver.getFlag() >= 3){
//			return JsonBuilder.build(1,"该送货单已完成评铁",null);
//		}

		BaseGoods goods = goodsMapper.selectByPrimaryKey(deliver.getGoodsId());
		String operationFlag = "废钢";
		if (!goods.getGoodsName().equals(operationFlag)) {
			return JsonBuilder.build(1,goods.getGoodsName()+"不用评铁", null);
		}

		//添加评铁单明细
		List<WhAccessDetail> data = new ArrayList<>();
		for (Object item : jsonArray) {
			WhAccessDetail accessDetail = JSONTool.getObject(item.toString(), WhAccessDetail.class);
			WhRankEvaluation rankEvaluation = rankEvaluationService.getRankEvaluation(accessDetail.getRankId());
			if (rankEvaluation == null){
				return JsonBuilder.build(1,"评铁单对应的级别行不存在",null);
			}
			accessDetail.setwRank(rankEvaluation.getwRank());
			accessDetail.setGrade(rankEvaluation.getGrade());
			accessDetail.setwLevel(rankEvaluation.getwLevel().toString());
			accessDetail.setOrd(null);
			data.add(accessDetail);
		}
		WhDeliveryDetails details = JSONTool.getObject(jsonObject.toString(), WhDeliveryDetails.class);
		List<WhDeliveryDetails> detail = detailsService.getDetailById(details.getDeliverId());

//			WhDeliveryDetails detailByFlag = detailsService.selectDetailByFlag(details.getCarNo(), details.getDeliverId());
//			if (detailByFlag.getFlag() == 2){
//				return JsonBuilder.build(1,details.getCarNo() + "已评铁",null);
//			}
		access.setId(null);
		access.setFlag(0);
		access.setMark(0);
		if (accessService.insertShipAccess(access,data,deliver,detail)){
			return JsonBuilder.build(0,"创建评铁单成功",access);
		}else {
			return JsonBuilder.build(1,"创建评铁单失败",null);
		}
	}
}
