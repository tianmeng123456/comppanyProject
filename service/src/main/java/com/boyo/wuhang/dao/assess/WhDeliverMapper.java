package com.boyo.wuhang.dao.assess;

import com.boyo.wuhang.entity.WhDeliverForm;
import com.boyo.wuhang.entity.WhWeighForm;
import com.boyo.wuhang.entity.assess.WhAccess;
import com.boyo.wuhang.entity.assess.WhDeliver;
import com.boyo.wuhang.ultity.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Repository
public interface WhDeliverMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WhDeliver record);

    int insertSelective(WhDeliver record);

    WhDeliver selectByPrimaryKey(Long id);

    List<WhDeliver> selectBySupplierId(Long supplerId);

    int updateByPrimaryKeySelective(WhDeliver record);

    int updateByPrimaryKey(WhDeliver record);

    WhDeliver checkNo(@Param("deliverNo")String deliverNo);

    int getTodayCount();

	List<Map<String,Object>> getDeliverList(@Param("record")WhDeliverForm record,
	                                        @Param("page")Pager page);

	int getDeliverListCount(@Param("record")WhDeliverForm record);

	List<Map<String,Object>> getDeliverByFlag(@Param("deliver")WhDeliverForm deliver);

	List<Map<String,Object>> getShipDeliverByFlag(@Param("record")WhDeliverForm record);

	List<Map<String,Object>> getConsignorSchedule(@Param("supplierId")Long supplierId);

	List<Map<String,Object>> getConsignorYearExclude(@Param("supplierId")Long supplierId,
	                                                 @Param("consignorId")Long consignorId);

	List<Map<String,Object>> getConsignorMonthExclude(@Param("supplierId")Long supplierId,
	                                                  @Param("consignorId")Long consignorId);

	List<WhDeliverForm> getDeliverReportList(@Param("deliverForm") WhDeliverForm deliverForm,
                                                   @Param("page") Pager page);

	int getDeliverReportListCount(@Param("deliverForm") WhDeliverForm deliverForm);

	List<WhDeliverForm> getPriceStatementList(@Param("deliverForm") WhDeliverForm deliverForm,
                                              @Param("page") Pager page);

	int getPriceStatementListCount(@Param("deliverForm") WhDeliverForm deliverForm);

	List<WhDeliverForm> getDeliverByDayList(@Param("createMonth") String createMonth,
											@Param("deliverForm") WhDeliverForm deliverForm,
											@Param("page") Pager page);

	int getDeliverByDayListCount(@Param("createMonth") String createMonth,
								 @Param("deliverForm") WhDeliverForm deliverForm);

	List<WhDeliverForm> getPaymentSummaryList(@Param("deliverForm") WhDeliverForm deliverForm,

											  @Param("page") Pager page);

	int getPaymentSummaryListCount(@Param("deliverForm") WhDeliverForm deliverForm);


	List<WhDeliverForm> getDeliverStatusList(@Param("record") WhDeliverForm record,
											 @Param("page") Pager page);

	int getDeliverStatusListCount(@Param("record") WhDeliverForm record);


	List<WhDeliverForm> getCarSettle(@Param("deliverId") Long deliverId);

	/**
	 * 获取未结算的船对应的送货单
	 */
	List<WhDeliverForm> selectShipNonSettleList(@Param("record")WhDeliverForm deliver);

	/**
	 * 根据车牌查询送货单
	 * @param carNumber
	 * @return
	 */
	List<WhDeliver> getDeliverByCarNumber(String carNumber);

	/**
	 * 结算单列表
	 * @param enty
	 * @param page
	 * @return
	 */
	List<WhDeliverForm> getSettleList(@Param("enty") WhDeliverForm enty,
								  @Param("page") Pager page);

	int getSettleListCount(@Param("enty") WhDeliverForm enty);

	/**
	 * 单价结算列表
	 * @param enty
	 * @param page
	 * @return
	 */
	List<WhDeliverForm> getPriceSettleList(@Param("enty") WhDeliverForm enty,
										   @Param("page") Pager page);

	int getPriceSettleListCount(@Param("enty") WhDeliverForm enty);

	int updateBatch(@Param("list") List<WhDeliver> deliverList);
}