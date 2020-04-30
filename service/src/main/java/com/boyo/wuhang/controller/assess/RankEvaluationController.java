package com.boyo.wuhang.controller.assess;

import com.boyo.wuhang.component.interceptor.LoginInterceptor;
import com.boyo.wuhang.entity.EvaluationForm;
import com.boyo.wuhang.entity.assess.WhEvaluation;
import com.boyo.wuhang.entity.assess.WhRankEvaluation;
import com.boyo.wuhang.entity.assess.WhRankPrice;
import com.boyo.wuhang.entity.base.BaseDeparture;
import com.boyo.wuhang.entity.base.BaseUser;
import com.boyo.wuhang.service.assess.ExcelService;
import com.boyo.wuhang.service.assess.RankEvaluationService;
import com.boyo.wuhang.service.base.DepartureService;
import com.boyo.wuhang.ultity.JSONTool;
import com.boyo.wuhang.ultity.JsonBuilder;
import com.boyo.wuhang.ultity.Pager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/evaluation")
public class RankEvaluationController {
	@Autowired
	private RankEvaluationService rankEvaluationService;
	@Autowired
	private DepartureService departureService;
	@Autowired
	private ExcelService excelService;

	@RequestMapping(value = "list",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getEvaluationList(@RequestBody EvaluationForm evaluationForm){
		Pager page = new Pager();
		page.setPageNumber(evaluationForm.getPageNumber());
		page.setPageSize(evaluationForm.getPageSize());
		page = rankEvaluationService.getEvaluationList(page,evaluationForm);
		return JsonBuilder.build(0,"",page);
	}

	@RequestMapping(value = "get",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getOneEvaluation(@RequestBody JSONObject jsonObject){
		Long id = null;
		if (jsonObject.has("id")){
			id = Long.parseLong(jsonObject.getString("id"));
		}
		Map<String,Object> res = rankEvaluationService.getEvaluation(id);
		return JsonBuilder.build(0,"",res);
	}

	@RequestMapping(value = "export",method = RequestMethod.POST)
	@ResponseBody
	public String exportEvaluation(@RequestBody JSONObject jsonObject, HttpServletResponse response){
		Long id = jsonObject.getLong("id");
		WhEvaluation evaluation = rankEvaluationService.getEvaluationSimple(id);
		if (evaluation == null){
			return JsonBuilder.build(1,"导出的级别评价表不存在",null).toString();
		}
		try {
			XSSFWorkbook workbook = excelService.exportEvaluation(evaluation);
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			workbook.write(os);
			InputStream excelStream = new ByteArrayInputStream(os.toByteArray());

			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buff = new byte[1024];
			int len = 0 ;
			while ((len = excelStream.read(buff))!= -1){
				outStream.write(buff,0,len);
			}
			excelStream.close();
			byte[] buffer = outStream.toByteArray();
			//重置response 改变返回类型
			if (null != buffer && buffer.length > 0) {
				// 清空response
				response.reset();
				// 设置response的Header
				response.addHeader("Content-Disposition", "attachment;filename="
						+ new String(("evaluation.xlsx").getBytes("GBK"), "ISO8859_1"));
				response.addHeader("Content-Length", "" + buffer.length);
				response.addHeader("Access-Control-Allow-Origin", "*");
				OutputStream toClient = response.getOutputStream();
				response.setContentType("application/octet-stream");
				toClient.write(buffer);
				toClient.flush();
				toClient.close();
				os.close();
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			return JsonBuilder.build(1,"导出失败"+ex.getMessage(),null).toString();
		}
		return JsonBuilder.build(0,"导出成功",null).toString();
	}

	@RequestMapping(value = "add",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insertEvaluationTable(@RequestBody JSONObject jsonObject){
		JSONArray array = jsonObject.getJSONArray("rankList");
		List<WhRankEvaluation> rankEvaluationList = new ArrayList<>();
		for (int i = 0; i<array.size(); i++){
			JSONObject item = array.getJSONObject(i);
			List<WhRankPrice> priceList = null;
			if (item.get("priceList") != null){
				priceList = JSONTool.jsonToList(item.getString("priceList"), WhRankPrice.class);
				item.remove("priceList");
			}
			WhRankEvaluation rankEvaluation = JSONTool.getObject(item.toString(), WhRankEvaluation.class);
			if (rankEvaluation == null){
				return JsonBuilder.build(1,(i+1)+"行价格不存在",null);
			}
			if (StringUtils.isBlank(rankEvaluation.getwRank())){
				return JsonBuilder.build(1,(i+1)+"行级别为空",null);
			}
			if (StringUtils.isBlank(rankEvaluation.getGrade())){
				return JsonBuilder.build(1,(i+1)+"行品级为空",null);
			}
			if (rankEvaluation.getwLevel()==null){
				return JsonBuilder.build(1,(i+1)+"行层次为空",null);
			}
			rankEvaluation.setId(null);
			rankEvaluation.setPriceList(priceList);
			rankEvaluationList.add(rankEvaluation);
		}
		jsonObject.remove("rankList");
		WhEvaluation evaluation = JSONTool.getObject(jsonObject.toString(),WhEvaluation.class);
		if (evaluation == null){
			return JsonBuilder.build(1,"级别评价表主表不存在",null);
		}
		evaluation.setId(null);
//		if (!rankEvaluationService.checkNo(evaluation)){
//			return JsonBuilder.build(1,"编号重复",null);
//		}
		if (rankEvaluationService.insertEvaluation(evaluation,rankEvaluationList)){
			return JsonBuilder.build(0,"创建级别评价表成功",null);
		}else {
			return JsonBuilder.build(1,"创建级别评价表失败",null);
		}
	}

	@RequestMapping(value = "edit",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject editEvaluation(@RequestBody JSONObject jsonObject){
		BaseUser user = LoginInterceptor.user.get();
		JSONArray array = jsonObject.getJSONArray("rankList");
		List<WhRankEvaluation> rankEvaluationList = new ArrayList<>();
		for (int i = 0; i<array.size(); i++){
			JSONObject item = array.getJSONObject(i);
			List<WhRankPrice> priceList = null;
			if (item.get("priceList") != null){
				priceList = JSONTool.jsonToList(item.getString("priceList"), WhRankPrice.class);
				item.remove("priceList");
			}
			WhRankEvaluation rankEvaluation = JSONTool.getObject(item.toString(), WhRankEvaluation.class);
			if (rankEvaluation == null){
				return JsonBuilder.build(1,(i+1)+"行级别价格不存在",null);
			}
			if (rankEvaluation.getEvaluationId() == 0){
				if (StringUtils.isBlank(rankEvaluation.getwRank())){
					return JsonBuilder.build(1,(i+1)+"行级别为空",null);
				}
				if (StringUtils.isBlank(rankEvaluation.getGrade())){
					return JsonBuilder.build(1,(i+1)+"行品级为空",null);
				}
				if (rankEvaluation.getwLevel()==null){
					return JsonBuilder.build(1,(i+1)+"行层次为空",null);
				}
				rankEvaluation.setId(null);
			}else if (rankEvaluation.getEvaluationId() == 1 || rankEvaluation.getEvaluationId() ==2){
				WhRankEvaluation old_rankEvaluation =
						rankEvaluationService.getRankEvaluation(rankEvaluation.getId());
				if (old_rankEvaluation == null){
					return JsonBuilder.build(1,(i+1)+"行级别价格不存在",null);
				}
				if (!old_rankEvaluation.getEvaluationId().equals(jsonObject.getLong("id"))){
					return JsonBuilder.build(1,(i+1)+"行级别价格不属于本表",null);
				}
			}

			rankEvaluation.setPriceList(priceList);
			rankEvaluationList.add(rankEvaluation);
		}
		jsonObject.remove("rankList");
		WhEvaluation evaluation = JSONTool.getObject(jsonObject.toString(),WhEvaluation.class);
		if (evaluation == null){
			return JsonBuilder.build(1,"级别评价表主表不存在",null);
		}
		WhEvaluation t_evaluation = rankEvaluationService.getEvaluationSimple(evaluation.getId());
		if (t_evaluation == null){
			return JsonBuilder.build(1,"级别评价表主表不存在",null);
		}
		if (t_evaluation.getFlag() == 1){
			return JsonBuilder.build(1,"级别评价表已审核，无法修改",null);
		}
		evaluation.setEditDate(new Date());
		evaluation.setEditPerson(user.getUserName());

		if (rankEvaluationService.updateEvaluation(evaluation, rankEvaluationList)){
			return JsonBuilder.build(0,"修改级别评价表成功",null);
		}else {
			return JsonBuilder.build(1,"修改级别评价表失败",null);
		}
	}

	@RequestMapping(value = "delete",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteEvaluation(@RequestBody JSONObject jsonObject){
		Long id = Long.parseLong(jsonObject.getString("id"));
		WhEvaluation evaluation = rankEvaluationService.getEvaluationSimple(id);
		if (evaluation == null){
			return JsonBuilder.build(1,"级别评价表不存在",null);
		}
		if (evaluation.getFlag() == 1){
			return JsonBuilder.build(1,"要删除级别评价表已审核",null);
		}
		if (rankEvaluationService.deleteEvaluation(evaluation)){
			return JsonBuilder.build(0,"删除级别评价表成功",null);
		}else {
			return JsonBuilder.build(1,"删除级别评价表失败",null);
		}
	}

	@RequestMapping(value = "check",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject checkEvaluation(@RequestBody JSONObject jsonObject){
		BaseUser user = LoginInterceptor.user.get();
		Long id = jsonObject.getLong("id");
		WhEvaluation evaluation = rankEvaluationService.getEvaluationSimple(id);
		if (evaluation == null){
			return JsonBuilder.build(1,"级别评价表不存在",null);
		}
		if (evaluation.getFlag() == 1){
			return JsonBuilder.build(1,"级别评价表已审核",null);
		}
		evaluation.setCheckDate(new Date());
		evaluation.setCheckPerson(user.getUserName());
		StringBuilder msg = new StringBuilder();
		if (rankEvaluationService.checkEvaluation(evaluation,msg)){
			return JsonBuilder.build(0,"审核级别评价表成功",null);
		}else {
			return JsonBuilder.build(1,"审核级别评价表失败，"+msg.toString(),null);
		}
	}

	//	@RequestMapping(value = "add_rank",method = RequestMethod.POST)
//	@ResponseBody
//	public JSONObject addRankEvaluation(@RequestBody JSONObject jsonObject){
//		Long evaluationId = Long.parseLong(jsonObject.getString("evaluationId"));
//		WhEvaluation evaluation = rankEvaluationService.getEvaluationSimple(evaluationId);
//		if (evaluation == null){
//			return JsonBuilder.build(1,"修改的级别评价表不存在",null);
//		}
//		JSONObject rank = jsonObject.getJSONObject("rank");
//		List<WhRankPrice> priceList = null;
//		if (rank.get("priceList") != null){
//			priceList = JSONTool.jsonToList(rank.getString("priceList"), WhRankPrice.class);
//			rank.remove("priceList");
//		}
//		WhRankEvaluation rankEvaluation = JSONTool.getObject(rank.toString(), WhRankEvaluation.class);
//		if (rankEvaluation == null){
//			return JsonBuilder.build(1,"添加的级别不存在",null);
//		}
//		rankEvaluation.setPriceList(priceList);
//		if (StringUtils.isBlank(rankEvaluation.getwRank())){
//			return JsonBuilder.build(1,"级别为空",null);
//		}
//		if (StringUtils.isBlank(rankEvaluation.getGrade())){
//			return JsonBuilder.build(1,"品级为空",null);
//		}
//		if (rankEvaluation.getwLevel()==null){
//			return JsonBuilder.build(1,"层次为空",null);
//		}
//		rankEvaluation.setId(null);
//		if (rankEvaluationService.insertRankEvaluation(evaluationId,rankEvaluation)){
//			return JsonBuilder.build(0,"添加评价表级别成功",null);
//		}else {
//			return JsonBuilder.build(1,"添加评价表级别失败",null);
//		}
//	}
//
//	@RequestMapping(value = "edit_rank",method = RequestMethod.POST)
//	@ResponseBody
//	public JSONObject updateRankEvaluation(@RequestBody JSONObject jsonObject){
//		Long rankId = Long.parseLong(jsonObject.getString("id"));
//		WhRankEvaluation t_rankEvaluation = rankEvaluationService.getRankEvaluation(rankId);
//		if (t_rankEvaluation == null){
//			return JsonBuilder.build(1,"修改的级别行不存在",null);
//		}
//		WhEvaluation evaluation = rankEvaluationService.getEvaluationSimple(t_rankEvaluation.getEvaluationId());
//		//check evaluation
//		List<WhRankPrice> priceList = null;
//		if (jsonObject.get("priceList") != null){
//			priceList = JSONTool.jsonToList(jsonObject.getString("priceList"), WhRankPrice.class);
//			jsonObject.remove("priceList");
//			for (WhRankPrice price : priceList){
//				BaseDeparture departure = departureService.selectByPrimaryKey(price.getDepartureId());
//				if (departure == null){
//					return JsonBuilder.build(1,"启运地不存在",price.getDepartureId());
//				}
//			}
//		}
//		WhRankEvaluation rankEvaluation = JSONTool.getObject(jsonObject.toString(), WhRankEvaluation.class);
//		if (rankEvaluation == null){
//			return JsonBuilder.build(1,"修改的级别不存在",null);
//		}
//		rankEvaluation.setPriceList(priceList);
//		rankEvaluation.setEvaluationId(evaluation.getId());
//		if (rankEvaluationService.updateRankEvaluation(rankEvaluation)){
//			return JsonBuilder.build(0,"修改评价表级别成功",null);
//		}else {
//			return JsonBuilder.build(1,"修改评价表级别失败",null);
//		}
//	}
//
//	@RequestMapping(value = "delete_rank",method = RequestMethod.POST)
//	@ResponseBody
//	public JSONObject deleteRankEvaluation(@RequestBody JSONObject jsonObject){
//		Long rankId = Long.parseLong(jsonObject.getString("id"));
//		WhRankEvaluation rankEvaluation = rankEvaluationService.getRankEvaluation(rankId);
//		if (rankEvaluation == null){
//			return JsonBuilder.build(1,"删除的级别行不存在",null);
//		}
//		WhEvaluation evaluation = rankEvaluationService.getEvaluationSimple(rankEvaluation.getEvaluationId());
//		//check evaluation
//		if (rankEvaluationService.deleteRankEvaluation(rankEvaluation)){
//			return JsonBuilder.build(0,"删除评价表级别成功",null);
//		}else {
//			return JsonBuilder.build(1,"删除评价表级别失败",null);
//		}
//	}
}
