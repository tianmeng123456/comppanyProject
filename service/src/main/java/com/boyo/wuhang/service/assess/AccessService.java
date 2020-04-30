package com.boyo.wuhang.service.assess;

import com.boyo.wuhang.component.interceptor.LoginInterceptor;
import com.boyo.wuhang.dao.assess.WhAccessDetailMapper;
import com.boyo.wuhang.dao.assess.WhAccessMapper;
import com.boyo.wuhang.dao.assess.WhDeliverMapper;
import com.boyo.wuhang.dao.assess.WhDeliveryDetailsMapper;
import com.boyo.wuhang.entity.WhAccessForm;
import com.boyo.wuhang.entity.assess.*;
import com.boyo.wuhang.entity.base.BaseUser;
import com.boyo.wuhang.service.editLog.AccessEditLogService;
import com.boyo.wuhang.ultity.Pager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AccessService {
	@Autowired
	private WhAccessMapper accessMapper;
	@Autowired
	private WhAccessDetailMapper accessDetailMapper;
	@Autowired
	private DeliverService deliverService;
	@Autowired
	private WeighService weighService;
	@Autowired
	private WhDeliveryDetailsMapper deliveryDetailsMapper;
	@Autowired
	private WhDeliverMapper deliverMapper;
	@Autowired
	private AccessEditLogService editLogService;

	public WhAccess getAccessById(Long id){
		return accessMapper.selectByPrimaryKey(id);
	}

	public WhAccessDetail getAccessDetailByOrd(Long ord){
		return accessDetailMapper.selectByPrimaryKey(ord);
	}

	@Transactional
	public boolean insertAccess(WhAccess access, List<WhAccessDetail> list,WhWeigh weigh){
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String no = sdf.format(new Date()) + String.format("%04d", accessMapper.getTodayCount()+1);
		if (!this.checkNo(no)){
			return false;
		}
		access.setAccessNo(no);
		if (accessMapper.insertSelective(access)==0){
			return false;
		}
		if (list != null){
			for (WhAccessDetail item : list){
				item.setAccessId(access.getId());
				if (accessDetailMapper.insertSelective(item)==0){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return false;
				}
			}
		}
		if (!weighService.updateWeigh(weigh)){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Transactional
	public boolean updateAccess(WhAccess access, List<WhAccessDetail> list){
		BaseUser user = LoginInterceptor.user.get();
		access.setAccessNo(null);
		if (accessMapper.updateByPrimaryKeySelective(access)==0){
			return false;
		}
		if (list != null){
			for (WhAccessDetail item : list){
				if (accessDetailMapper.updateByPrimaryKeySelective(item)==0){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return false;
				}
			}
		}

//		if (editLogService.insertAccessData(access.getId())==0){
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//			return false;
//		}
//		List<WhAccessEditLog> editLogList = editLogService.selectById(access.getId());
//		if (editLogList != null) {
//			for (WhAccessEditLog item : editLogList) {
//				item.setD_accessId_log(access.getId());
//				item.setCreateId(user.getId());
//				item.setCreateName(user.getUserName());
//				if (editLogService.updateByAccessId(item)==0){
//					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//					return false;
//				}
//			}
//		}
		return true;
	}

	@Transactional
	public boolean deleteAccess(WhAccess access){
		accessDetailMapper.deleteAccessDetailById(access.getId());
		if (accessMapper.deleteByPrimaryKey(access.getId())==0){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Transactional
	public boolean adoptAccess(WhAccess access){
		WhDeliver whDeliver = deliverService.getDeliverById(access.getDeliverId());
		if (whDeliver.getMark() == 0) {
			if (whDeliver.getFlag() != 2) {
				return false;
			}
		}
		WhDeliver deliver = new WhDeliver();
		deliver.setId(access.getDeliverId());
		deliver.setFlag(3);
		if (!deliverService.updateDeliver(deliver)){
			return false;
		}
		if (whDeliver.getMark() == 0) {
			WhWeigh whWeigh = weighService.getWeighByDeliverId(access.getDeliverId());
			if (whWeigh == null) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return false;
			}

			WhWeigh weigh = new WhWeigh();
			weigh.setId(whWeigh.getId());
			if (access.getImpurities() == null) {
				weigh.setImpurities(new BigDecimal(0));
			} else {
				weigh.setImpurities(access.getImpurities());
			}

			if (!weighService.updateWeigh(weigh)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return false;
			}
		}
		if (accessMapper.updateByPrimaryKeySelective(access)==0){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}

		return true;
	}

	boolean checkNo(String accessNo){
		if (StringUtils.isBlank(accessNo)){
			return false;
		}
		return accessMapper.checkNo(accessNo)==null;
	}

	public Pager getAccessList(Pager page, WhAccessForm access){
		if (StringUtils.isNotBlank(access.getEndDate())){
			access.setStartDate(access.getStartDate()+" 00:00:00.000");
			access.setEndDate(access.getEndDate()+" 23:59:59.999");
		}else{
			access.setStartDate(null);
			access.setEndDate(null);
		}
		List<Map<String,Object>> list = accessMapper.getAccessList(access, page);
		for (Map<String,Object> item : list){
			List<WhAccessDetail> detailList =
					accessDetailMapper.getAccessDetailById(Long.parseLong(item.get("id").toString()));
			item.put("detailList",detailList);
		}
		page.setList(list);
		page.setTotalRow(accessMapper.getAccessListCount(access));
		return page;
	}

	/**
	 * 创建船对应的车的评铁单
	 * @param access 评铁单
	 * @param data   评铁单明细
	 */
	@Transactional
	public boolean insertShipAccess(WhAccess access, List<WhAccessDetail> data, WhDeliver whDeliver,List<WhDeliveryDetails> details) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String no = sdf.format(new Date()) + String.format("%04d", accessMapper.getTodayCount()+1);
		if (!this.checkNo(no)){
			return false;
		}

		/*
		 * 整船评铁(flag=1): 最后插入此数据
		 * 每车评铁(flag=2): 每车都插入一条评铁数据
		 */
		access.setAccessNo(no);
		if (whDeliver.getValueMethod() == 1){
			insertAccessDetails(access, data);
			WhDeliver update = new WhDeliver();
			update.setId(whDeliver.getId());
//			update.setFlag(3);
			deliverService.updateDeliver(update);
		} else if (whDeliver.getValueMethod() == 2){
			insertAccessDetails(access,data);
		}
		if (details != null) {
			for (WhDeliveryDetails d : details) {
				d.setDeliverId(d.getDeliverId());
				d.setCarNo(d.getCarNo());
				d.setAccessId(access.getId());
				d.setFlag(2);
				if (deliveryDetailsMapper.updateByDeliverIdSelective(d) == 0) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 插入船的评铁表,评铁明细表
	 */
	private void insertAccessDetails(WhAccess access, List<WhAccessDetail> data) {
		if (accessMapper.insertSelective(access)==0){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		for (WhAccessDetail datum : data) {
			datum.setAccessId(access.getId());
			if(accessDetailMapper.insertSelective(datum) == 0) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
		}
	}

	public WhDeliveryDetails getStartDetail(Long deliverId){
		return deliveryDetailsMapper.getStartDetail(deliverId);
	}

	public WhDeliveryDetails getShipRank(Long deliverId){
		return deliveryDetailsMapper.getShipRank(deliverId);
	}
	public WhAccessDetail getAccessDetailByRank(Long rankId,Long accessId){
		return accessDetailMapper.getAccessDetailByRank(rankId,accessId);
	}

	public WhAccess getAccessByDeliverId(Long deliverId){
		return accessMapper.getAccessByDeliverId(deliverId);
	}
}
