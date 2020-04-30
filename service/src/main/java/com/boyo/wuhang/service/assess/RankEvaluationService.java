package com.boyo.wuhang.service.assess;

import com.boyo.wuhang.dao.assess.WhDeliverMapper;
import com.boyo.wuhang.dao.assess.WhEvaluationMapper;
import com.boyo.wuhang.dao.assess.WhRankEvaluationMapper;
import com.boyo.wuhang.dao.assess.WhRankPriceMapper;
import com.boyo.wuhang.dao.base.BaseDepartureMapper;
import com.boyo.wuhang.entity.EvaluationForm;
import com.boyo.wuhang.entity.assess.WhEvaluation;
import com.boyo.wuhang.entity.assess.WhRankEvaluation;
import com.boyo.wuhang.entity.assess.WhRankPrice;
import com.boyo.wuhang.entity.base.BaseDeparture;
import com.boyo.wuhang.ultity.JsonBuilder;
import com.boyo.wuhang.ultity.Pager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RankEvaluationService {
	@Autowired
	private WhEvaluationMapper evaluationMapper;
	@Autowired
	private WhRankEvaluationMapper rankEvaluationMapper;
	@Autowired
	private WhRankPriceMapper rankPriceMapper;
	@Autowired
	private BaseDepartureMapper departureMapper;
	@Autowired
	private WhDeliverMapper deliverMapper;

	public Pager getEvaluationList(Pager page, EvaluationForm record){
		if (StringUtils.isNotBlank(record.getCreateEndDate())){
			record.setCreateStartDate(record.getCreateStartDate() + " 00:00:00.000");
			record.setCreateEndDate(record.getCreateEndDate() + " 23:59:59.999");
		}else{
			record.setCreateStartDate(null);
			record.setCreateEndDate(null);
		}
		page.setList(evaluationMapper.getEvaluationList(record, page));
		page.setTotalRow(evaluationMapper.getEvaluationCount(record));
		return page;
	}

	public Map<String,Object> getEvaluation(Long id){
		WhEvaluation evaluation;
		if (id == null){
			evaluation = evaluationMapper.getNewestEvaluation();
		}else {
			evaluation = evaluationMapper.selectByPrimaryKey(id);
		}
		try {
			Map<String,Object> res = (Map)JsonBuilder.BeanToMap(evaluation);
			List<WhRankEvaluation> rankList = rankEvaluationMapper.getByEvaluationId(evaluation.getId());
			for (WhRankEvaluation item : rankList){
				item.setPriceList(rankPriceMapper.getPriceByRankId(item.getId()));
			}
			res.put("rankList",rankList);
			res.put("departureList",departureMapper.getAllDeparture(evaluation.getId()));
			return res;

		}catch (Exception e ){
			System.err.println("类型转换错误");
			return null;
		}
	}

	public WhRankEvaluation getRankEvaluation(Long rankId){
		return rankEvaluationMapper.selectByPrimaryKey(rankId);
	}

	public WhEvaluation getEvaluationSimple(Long id){
		return evaluationMapper.selectByPrimaryKey(id);
	}

	@Transactional
	public boolean insertEvaluation(WhEvaluation evaluation, List<WhRankEvaluation> list){
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String no = sdf.format(new Date()) + String.format("%04d", deliverMapper.getTodayCount()+1);
		if (!this.checkNo(no)){
			return false;
		}
		evaluation.setEvaluationNo(no);
		if (evaluationMapper.insertSelective(evaluation)==0){
			return false;
		}
		for (WhRankEvaluation rankItem : list){
			rankItem.setEvaluationId(evaluation.getId());
			if (rankEvaluationMapper.insertSelective(rankItem)==0){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return false;
			}
			List<BaseDeparture> allDeparture = departureMapper.getEffectDeparture();
			if (rankItem.getPriceList() == null){
				//该行默认价格为0
				for (BaseDeparture departure : allDeparture){
					WhRankPrice rankPrice = new WhRankPrice();
					rankPrice.setDepartureId(departure.getId());
					rankPrice.setRankId(rankItem.getId());
					rankPrice.setPrice(new BigDecimal(0));
					if (rankPriceMapper.insertSelective(rankPrice)==0){
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return false;
					}
				}
			}else {
				List<WhRankPrice> priceList = rankItem.getPriceList();
				for (BaseDeparture departure : allDeparture){
					boolean existed = false;
					WhRankPrice existPrice = null;
					for (WhRankPrice price : priceList){
						if (price.getDepartureId().equals(departure.getId())){
							existed = true;
							existPrice = price;
							break;
						}
					}
					WhRankPrice rankPrice = new WhRankPrice();
					if (existed){
						rankPrice.setDepartureId(existPrice.getDepartureId());
						rankPrice.setRankId(rankItem.getId());
						rankPrice.setPrice(existPrice.getPrice());
						if (rankPriceMapper.insertSelective(rankPrice)==0){
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							return false;
						}
					}else {
						rankPrice.setDepartureId(departure.getId());
						rankPrice.setRankId(rankItem.getId());
						rankPrice.setPrice(new BigDecimal(0));
						if (rankPriceMapper.insertSelective(rankPrice)==0){
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	@Transactional
	public boolean updateEvaluation(WhEvaluation evaluation, List<WhRankEvaluation> list){
		if (list != null && list.size()>0){
			//根据EvaluationId排序，删除在最后
			list.sort(new Comparator<WhRankEvaluation>() {
				@Override
				public int compare(WhRankEvaluation o1, WhRankEvaluation o2) {
					if (o1.getEvaluationId() > o2.getEvaluationId()){
						return 1;
					}
					return -1;
				}
			});
			for (WhRankEvaluation item : list){
				switch (item.getEvaluationId().toString()){
					case "0":{
						//添加明细
						item.setEvaluationId(evaluation.getId());
						if (!this.insertRankEvaluation(evaluation.getId(),item)){
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							return false;
						}
						break;
					}
					case "1":{
						//修改明细
						item.setEvaluationId(null);
						if (!this.updateRankEvaluation(item)){
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							return false;
						}
						break;
					}
					case "2":{
						//删除明细
						if (!this.deleteRankEvaluation(item)){
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							return false;
						}
						break;
					}
					default:{
						continue;
					}
				}
			}
		}
		if (evaluationMapper.updateByPrimaryKeySelective(evaluation)==0){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	boolean insertRankEvaluation(Long evaluationId, WhRankEvaluation rankEvaluation){
		rankEvaluation.setEvaluationId(evaluationId);
		if (rankEvaluationMapper.insertSelective(rankEvaluation)==0){
			return false;
		}
		//当时添加时的启运地
		List<BaseDeparture> allDeparture = departureMapper.getAllDeparture(evaluationId);
		if (rankEvaluation.getPriceList() == null){
			//该行默认价格为0
			for (BaseDeparture departure : allDeparture){
				WhRankPrice rankPrice = new WhRankPrice();
				rankPrice.setDepartureId(departure.getId());
				rankPrice.setRankId(rankEvaluation.getId());
				rankPrice.setPrice(new BigDecimal(0));
				if (rankPriceMapper.insertSelective(rankPrice)==0){
					return false;
				}
			}
		}else {
			List<WhRankPrice> priceList = rankEvaluation.getPriceList();
			for (BaseDeparture departure : allDeparture){
				boolean existed = false;
				WhRankPrice existPrice = null;
				for (WhRankPrice price : priceList){
					if (price.getDepartureId().equals(departure.getId())){
						existed = true;
						existPrice = price;
						break;
					}
				}
				WhRankPrice rankPrice = new WhRankPrice();
				if (existed){
					rankPrice.setDepartureId(existPrice.getDepartureId());
					rankPrice.setRankId(rankEvaluation.getId());
					rankPrice.setPrice(existPrice.getPrice());
					if (rankPriceMapper.insertSelective(rankPrice)==0){
						return false;
					}
				}else {
					rankPrice.setDepartureId(departure.getId());
					rankPrice.setRankId(rankEvaluation.getId());
					rankPrice.setPrice(new BigDecimal(0));
					if (rankPriceMapper.insertSelective(rankPrice)==0){
						return false;
					}
				}
			}
		}
		return true;
	}

	boolean updateRankEvaluation(WhRankEvaluation rankEvaluation){
		List<WhRankPrice> priceList = rankEvaluation.getPriceList();
		if (priceList != null){
			for (WhRankPrice price : priceList){
				if (price.getPrice() == null){
					continue;
				}
				WhRankPrice t_price = rankPriceMapper.getPriceByRankAndDeparture(
						rankEvaluation.getId(),price.getDepartureId());
				if (t_price == null){
					continue;
				}
				WhRankPrice updatePrice = new WhRankPrice();
				updatePrice.setId(t_price.getId());
				updatePrice.setPrice(price.getPrice());
				if (rankPriceMapper.updateByPrimaryKeySelective(updatePrice)==0){
					return false;
				}
			}
		}
		if (rankEvaluationMapper.updateByPrimaryKeySelective(rankEvaluation)==0){
			return false;
		}
		return true;
	}

	boolean deleteRankEvaluation(WhRankEvaluation rankEvaluation){
		rankPriceMapper.deleteByRankId(rankEvaluation.getId());
		if (rankEvaluationMapper.deleteByPrimaryKey(rankEvaluation.getId())==0){
			return false;
		}
		return true;
	}

	@Transactional
	public boolean deleteEvaluation(WhEvaluation evaluation){
		List<WhRankEvaluation> rankEvaluationList
				= rankEvaluationMapper.getByEvaluationId(evaluation.getId());
		for (WhRankEvaluation rankEvaluation : rankEvaluationList){
			rankPriceMapper.deleteByRankId(rankEvaluation.getId());
		}
		rankEvaluationMapper.deleteByEvaluationId(evaluation.getId());
		if (evaluationMapper.deleteByPrimaryKey(evaluation.getId())==0){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	public boolean checkNo(String no){
		if (StringUtils.isBlank(no)){
			return false;
		}
		return evaluationMapper.checkNo(no)==null;
	}

	@Transactional
	public boolean checkEvaluation(WhEvaluation evaluation, StringBuilder msg){
		//找到以前最新的修改状态
		WhEvaluation newest = evaluationMapper.getNewestEvaluation();
		if (newest != null){
			//新修改的时间在当前时间之后
			if ( (new Date()).after(evaluation.getExecDate())){
				msg.append("执行时间早于当前时间");
				return false;
			}
			newest.setFlag(2);
			newest.setIsArchived(1);
			newest.setEndDate(evaluation.getExecDate());
			if (evaluationMapper.updateByPrimaryKeySelective(newest)==0){
				return false;
			}
		}
		evaluation.setFlag(1);
		if (evaluationMapper.updateByPrimaryKeySelective(evaluation)==0){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	public List<Map<String,Object>> getExecRankList(Date arriveWeighTime){
		return evaluationMapper.getExecRankList(arriveWeighTime);
	}

}
