package com.boyo.wuhang.service.assess;

import com.boyo.wuhang.dao.assess.WhDeliverCarWeighRecordMapper;
import com.boyo.wuhang.dao.assess.WhWeighMapper;
import com.boyo.wuhang.entity.WhDeliveryDetailsForm;
import com.boyo.wuhang.entity.WhWeighForm;
import com.boyo.wuhang.entity.assess.WhDeliver;
import com.boyo.wuhang.entity.assess.WhDeliverCarWeighRecord;
import com.boyo.wuhang.entity.assess.WhDeliveryDetails;
import com.boyo.wuhang.entity.assess.WhWeigh;
import com.boyo.wuhang.ultity.Pager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class WeighService {
	@Autowired
	private WhWeighMapper weighMapper;
	@Autowired
	private DeliverService deliverService;
	@Autowired
	private WhDeliverCarWeighRecordMapper carWeighRecordMapper;

	public WhWeigh getWeighById(Long id) {
		return weighMapper.selectByPrimaryKey(id);
	}

	public WhWeigh getWeighByDeliverId(Long deliverId) {
		return weighMapper.getWeighByDeliverId(deliverId);
	}

	@Transactional
	public boolean arriveWeigh(WhDeliver deliver, WhWeigh weigh) {
		WhDeliver whDeliver = deliverService.getDeliverById(deliver.getId());
		if (whDeliver.getFlag() != 1) {
			return false;
		}
		if (!deliverService.updateDeliver(deliver)) {
			return false;
		}
		if (!this.insertWeigh(weigh)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}
	@Transactional
	public boolean arriveEditWeigh(WhDeliver deliver, WhWeigh weigh) {
//		WhDeliver whDeliver = deliverService.getDeliverById(deliver.getId());
//		if (whDeliver.getFlag() < 2) {
//			return false;
//		}
		if (!deliverService.updateDeliver(deliver)) {
			return false;
		}
		if (!this.updateWeigh(weigh)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}


	@Transactional
	public boolean leaveWeigh(WhWeigh weigh,WhDeliver deliver) {
		WhDeliver whDeliver = deliverService.getDeliverById(weigh.getDeliverId());
		if (whDeliver.getFlag() != 3) {
			return false;
		}
		deliver.setId(weigh.getDeliverId());
		deliver.setFlag(4);
		deliver.setWeigh(weigh.getNetWeigh());
		if (!deliverService.updateDeliver(deliver)) {
			return false;
		}
		if (weighMapper.updateByPrimaryKeySelective(weigh) == 0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	public boolean insertWeigh(WhWeigh weigh) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String no = sdf.format(new Date()) + String.format("%04d", weighMapper.getTodayCount() + 1);
		if (!this.checkNo(no)) {
			return false;
		}
		weigh.setWeighNo(no);
		return weighMapper.insertSelective(weigh) == 1;
	}

	public boolean updateWeigh(WhWeigh weigh) {
		weigh.setWeighNo(null);
		return weighMapper.updateByPrimaryKeySelective(weigh) == 1;
	}

	public boolean deleteWeigh(WhWeigh weigh) {
		return weighMapper.deleteByPrimaryKey(weigh.getId()) == 1;
	}

	boolean checkNo(String weighNo) {
		if (StringUtils.isBlank(weighNo)) {
			return false;
		}
		return weighMapper.checkNo(weighNo) == null;
	}

	public Pager getWeighList(Pager page, WhWeighForm weigh) {
		selectDate(weigh);
		page.setList(weighMapper.getWeighList(weigh, page));
		page.setTotalRow(weighMapper.getWeighListCount(weigh));
		return page;
	}

	//时间查询
	private void selectDate(WhWeighForm weigh) {
		if (StringUtils.isNotBlank(weigh.getEndDate())) {
			weigh.setStartDate(weigh.getStartDate() + " 00:00:00.000");
			weigh.setEndDate(weigh.getEndDate() + " 23:59:59.999");
		} else {
			weigh.setStartDate(null);
			weigh.setEndDate(null);
		}
		if (StringUtils.isNotBlank(weigh.getArriveWeighEndDate())) {
			weigh.setArriveWeighStartDate(weigh.getArriveWeighStartDate() + " 00:00:00.000");
			weigh.setArriveWeighEndDate(weigh.getArriveWeighEndDate() + " 23:59:59.999");
		} else {
			weigh.setArriveWeighStartDate(null);
			weigh.setArriveWeighEndDate(null);
		}
		if (StringUtils.isNotBlank(weigh.getLeaveWeighEndTime())) {
			weigh.setLeaveWeighStartTime(weigh.getLeaveWeighStartTime() + " 00:00:00.000");
			weigh.setLeaveWeighEndTime(weigh.getLeaveWeighEndTime() + " 23:59:59.999");
		} else {
			weigh.setLeaveWeighStartTime(null);
			weigh.setLeaveWeighEndTime(null);
		}
	}

	/**
	 * 船对应的卸货车进行入场称重
	 * @param details  实体类对象
	 * @return boolean
	 */
	public boolean addGrossWeight(WhDeliveryDetailsForm details) {
		int count = deliverService.countDetails(details.getDeliverId());
		if (count == 0){
			WhDeliver deliver = new WhDeliver();
			deliver.setId(details.getDeliverId());
			deliver.setArriveTime(details.getPlanStartDate());
//			deliver.setGoodsId(details.getGoodsId());
//			deliver.setGoodsName(details.getGoodsName());
			deliver.setFlag(2);
			deliverService.updateDeliver(deliver);
		}
		return deliverService.insertDeliverDetail(details);
	}

	/**
	 * 船对应的卸货车离场称重
	 * @param details 实体类对象
	 * @return boolean
	 */
	public boolean setTareWeighAndPlace(WhDeliveryDetails details) {
		return deliverService.setTareWeighAndPlace(details);
	}
}
