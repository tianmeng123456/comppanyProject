package com.boyo.wuhang.service.assess;

import com.boyo.wuhang.component.interceptor.LoginInterceptor;
import com.boyo.wuhang.dao.assess.*;
import com.boyo.wuhang.dao.base.BaseConsignorMapper;
import com.boyo.wuhang.entity.WhDeliverForm;
import com.boyo.wuhang.entity.assess.WhDeliver;
import com.boyo.wuhang.entity.assess.WhDeliveryDetails;
import com.boyo.wuhang.entity.assess.WhWeigh;
import com.boyo.wuhang.entity.base.BaseConsignor;
import com.boyo.wuhang.entity.base.BaseUser;
import com.boyo.wuhang.ultity.Pager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class DeliverService {
	@Autowired
	private WhDeliverMapper deliverMapper;
	@Autowired
	private BaseConsignorMapper consignorMapper;
	@Autowired
	private WhDeliveryDetailsMapper detailsMapper;
	@Autowired
	private WeighService weighService;
	@Autowired
	private WhAccessMapper accessMapper;

	public WhDeliver getDeliverById(Long id){
		return deliverMapper.selectByPrimaryKey(id);
	}

	public boolean insertDeliver(WhDeliver deliver){
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String no = sdf.format(new Date()) + String.format("%04d", deliverMapper.getTodayCount()+1);
		if (!this.checkNo(no)){
			return false;
		}
		deliver.setDeliverNo(no);
		return deliverMapper.insertSelective(deliver)==1;
	}

	@Transactional
	public boolean updateDeliver(WhDeliver deliver){
		deliver.setDeliverNo(null);
		return deliverMapper.updateByPrimaryKeySelective(deliver)==1;
	}

	public boolean deleteDeliver(WhDeliver deliver){
		return deliverMapper.deleteByPrimaryKey(deliver.getId())==1;
	}

	public boolean checkNo(String deliverNo){
		if (StringUtils.isBlank(deliverNo)){
			return false;
		}
		return deliverMapper.checkNo(deliverNo) == null;
	}

	public Pager getDeliverList(Pager page, WhDeliverForm deliver){
		if (StringUtils.isNotBlank(deliver.getEndDate())){
			deliver.setStartDate(deliver.getStartDate()+" 00:00:00.000");
			deliver.setEndDate(deliver.getEndDate()+" 23:59:59.999");
		}else{
			deliver.setStartDate(null);
			deliver.setEndDate(null);
		}
		page.setList(deliverMapper.getDeliverList(deliver,page));
		page.setTotalRow(deliverMapper.getDeliverListCount(deliver));
		return page;
	}

	public List<Map<String,Object>> getDeliverByFlag(WhDeliverForm deliver){
		return deliverMapper.getDeliverByFlag(deliver);
	}

	public List<Map<String, Object>> getShipDeliverByFlag(WhDeliverForm deliverForm){
		return deliverMapper.getShipDeliverByFlag(deliverForm);
	}

	//返回供应商的货主排班
	public List<Map<String,Object>> getActiveConsignorSchedule(Long supplierId){
		List<Map<String,Object>> list = deliverMapper.getConsignorSchedule(supplierId);
		List<Map<String,Object>> excludeList = deliverMapper.getConsignorYearExclude(supplierId,null);
		if (excludeList == null || excludeList.size() == 0){
			return list;
		}else {
			Iterator<Map<String,Object>> iterator = list.iterator();
			while (iterator.hasNext()){
				Map<String,Object> item = iterator.next();
				for (int i=0; i<excludeList.size(); i++){

					if (excludeList.get(i).get("consignorId").toString()
							.equals(item.get("consignorId").toString())){
						iterator.remove();
						break;
					}
				}
			}
			return list;
		}
	}

	//校验是货主有效
	public boolean checkConsignorActive(Long supplierId, Long consignorId, StringBuilder msg){
		BaseConsignor consignor = consignorMapper.selectByPrimaryKey(consignorId);
		if (!consignor.getSupplierId().equals(supplierId)){
			msg.append("货主不属于该供应商");
			return false;
		}
		List<Map<String,Object>> monthExclude = deliverMapper.getConsignorMonthExclude(supplierId, consignorId);
		if (monthExclude != null && monthExclude.size() > 0){
			msg.append("货主:");
			msg.append(consignor.getConsignorName());
			msg.append(",本月已送货3次");
			return false;
		}
		List<Map<String,Object>> yearExclude = deliverMapper.getConsignorYearExclude(supplierId, consignorId);
		if (yearExclude != null && yearExclude.size() > 0){
			msg.append("货主:");
			msg.append(consignor.getConsignorName());
			msg.append(",本年已送货24次");
			return false;
		}
		return true;
	}

	public List<WhDeliver> selectBySupplierId(Long id){
		return deliverMapper.selectBySupplierId(id);
	}

	//插入与船只关联的拉车明细
	@Transactional
	public boolean insertDeliverDetail(WhDeliveryDetails details) {
		return detailsMapper.insertSelective(details) == 1;
	}

	public int getTodayCount(){
		return deliverMapper.getTodayCount();
	}

	public Pager getDeliverDetails(WhDeliveryDetails details,Pager page){
		page.setList(detailsMapper.selectByDeliverPage(details,page));
		page.setTotalRow(detailsMapper.selectCountDetails(details));
		return page;
	}


	@Transactional
	public boolean delDeliverDetail(Long id) {
		return detailsMapper.deleteByPrimaryKey(id) == 1;
	}

	@Transactional
	public boolean updateDeliverDetail(WhDeliveryDetails details) {
		return detailsMapper.updateByPrimaryKeySelective(details) == 1;
	}

	public WhDeliveryDetails getDetailById(Long id) {
		return detailsMapper.selectByPrimaryKey(id);
	}

	/**
	 * 船的结束(统计净重,计算价格)
	 */
	@Transactional
	public boolean getNetWeight(WhDeliverForm deliver) {
		BaseUser user = LoginInterceptor.user.get();

		Map<String,BigDecimal> weightMap = detailsMapper.getWeight(deliver.getDeliverId());
		Map<String,BigDecimal> access = accessMapper.getPriceByDeliverId(deliver.getDeliverId());
		BigDecimal sumPrice = access == null ? new BigDecimal(0) : access.get("sumPrice");
	    BigDecimal impurities = access == null ? new BigDecimal(0) : access.get("impurities");
//		if (access == null){
//			deliver.setContractPrice(new BigDecimal(0));
//		}else {
//			deliver.setContractPrice(deliver.getContractPrice());
//		}
		deliver.setContractPrice(sumPrice);
		deliver.setId(deliver.getDeliverId());
		deliver.setWeigh(weightMap.get("netWeight"));
		deliver.setFlag(5);
//		WhWeigh weigh = weighService.getWeighByDeliverId(deliver.getId());
		WhWeigh whWeigh = new WhWeigh();
		whWeigh.setDeliverId(deliver.getDeliverId());
		whWeigh.setArriveWeighTime(deliver.getArriveTime());
		whWeigh.setArriveWeigh(weightMap.get("grossWeight"));
		whWeigh.setLeaveWeigh(weightMap.get("tareWeight"));
		//离场称重时间???应为最后一辆车离场时间
		whWeigh.setLeaveWeighTime(new Date());
		whWeigh.setImpurities(impurities);
		whWeigh.setNetWeigh(weightMap.get("grossWeight").subtract(weightMap.get("tareWeight")));
		whWeigh.setWeighPerson(user.getUserName());
		whWeigh.setEmployeeId(user.getId());
		weighService.insertWeigh(whWeigh);
		return this.updateDeliver(deliver);
	}


	public WhDeliveryDetails getDetail(WhDeliveryDetails details) {
		return detailsMapper.selectByDetail(details);
	}

	/**
	 * 依据 WhDeliveryDetails 实体类修改数据
	 * @param details 条件对象
	 * @return boolean
	 */
	public boolean setTareWeighAndPlace(WhDeliveryDetails details) {
		return detailsMapper.updateTareWeighAndPlace(details) == 1;
	}

	public Pager getDeliverStatusList(WhDeliverForm record , Pager page){
		if (StringUtils.isNotBlank(record.getEndDate())){
			record.setStartDate(record.getStartDate()+" 00:00:00.000");
			record.setEndDate(record.getEndDate()+" 23:59:59.999");
		}else{
			record.setStartDate(null);
			record.setEndDate(null);
		}
		page.setList(deliverMapper.getDeliverStatusList(record, page));
		page.setTotalRow(deliverMapper.getDeliverStatusListCount(record));
		return page;
	}

	/**
	 * 获取指定送货单号和车编号的最新入场称重车辆
	 * @param details 实体类对象
	 * @return 实体类对象
	 */
	public WhDeliveryDetails getNewDetail(WhDeliveryDetails details) {
		return detailsMapper.getNewDetail(details);
	}

	/**
	 * 统计 DeliverDetail 的次数
	 * @param deliverId 送货单号
	 */
	public int countDetails(Long deliverId) {
		return detailsMapper.countDetailByDeliverId(deliverId);
	}

	public List<WhDeliverForm> getCarSettle(Long deliverId){
		return deliverMapper.getCarSettle(deliverId);
	}

	/**
	 * 获取未结算的船列表
	 */
	public List<WhDeliverForm> getShipNonSettleList(WhDeliverForm obj) {
		return deliverMapper.selectShipNonSettleList(obj);
	}


	/**
	 * 根据车牌查询送货单
	 * @param carNumber
	 * @return
	 */
	public List<WhDeliver> getDeliverByCarNumber(String carNumber){
		return deliverMapper.getDeliverByCarNumber(carNumber);
	}

	/**
	 * 结算单列表
	 * @param deliverForm
	 * @param page
	 * @return
	 */
	public Pager getSettleList(WhDeliverForm deliverForm, Pager page){
		page.setList(deliverMapper.getSettleList(deliverForm, page));
		page.setTotalRow(deliverMapper.getSettleListCount(deliverForm));
		return page;
	}


	/**
	 * 单价结算审核列表
	 * @param deliverForm
	 * @param page
	 * @return
	 */
	public Pager getPriceSettleList(WhDeliverForm deliverForm, Pager page){
		page.setList(deliverMapper.getPriceSettleList(deliverForm, page));
		page.setTotalRow(deliverMapper.getPriceSettleListCount(deliverForm));
		return page;
	}

	/**
	 * 批量生成结算单
	 * @param deliverList
	 * @return
	 */
	public int updateBatch(List<WhDeliver> deliverList){
		return deliverMapper.updateBatch(deliverList);
	}
}
