package com.boyo.wuhang.dao.base;

import com.boyo.wuhang.entity.base.BaseChinaCities;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseChinaCitiesMapper {
    int insert(BaseChinaCities record);

    int insertSelective(BaseChinaCities record);

	List<String> getProvinceList();

	List<String> getCityList(@Param("province")String province);

	List<String> getDistrictList(@Param("province")String province, @Param("city")String city);

	List<BaseChinaCities> selectByDetailAddrList(@Param("province")String province, @Param("city")String city,@Param("district")String district);

	int updateByPrimaryKeySelective(BaseChinaCities record);

	int updateByPrimaryKey(BaseChinaCities record);

	List<BaseChinaCities> selectByDepartureId(@Param("departureId") Long departureId);
}