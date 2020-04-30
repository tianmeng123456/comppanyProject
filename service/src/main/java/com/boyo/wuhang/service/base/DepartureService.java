package com.boyo.wuhang.service.base;

import com.boyo.wuhang.dao.base.BaseDepartureMapper;
import com.boyo.wuhang.entity.base.BaseDeparture;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartureService {
    @Autowired
    private BaseDepartureMapper departureMapper;
//    @Autowired
//    private BaseDataService dataService;

	public List<BaseDeparture> getNewestDeparture(){
		return departureMapper.getNewestDeparture();
	}

    public List<BaseDeparture> getDepartureList(BaseDeparture departure){
        return departureMapper.getDepartureList(departure);
    }

    public BaseDeparture selectByPrimaryKey(Long id){
        return departureMapper.selectByPrimaryKey(id);
    }

    public boolean insertDeparture(BaseDeparture departure){
        if (!this.checkNo(departure)){
            return false;
        }
        if (!this.checkName(departure)){
            return false;
        }
        return departureMapper.insertSelective(departure) == 1;
    }


    public boolean updateDeparture(BaseDeparture record){
//        String province, String city, String district,
//        List<BaseChinaCities> addrList = dataService.selectByDetailAddrList(province, city, district);
//        if (addrList.size() > 0) {
//            for (BaseChinaCities chinaCities : addrList) {
//                chinaCities.setId(chinaCities.getId());
//                chinaCities.setDepartureId(null);
//                dataService.updateByPrimaryKey(chinaCities);
//                if (dataService.updateChinaCities(chinaCities) == 0) {
//                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                    return false;
//                }
//            }
//        }
        if (record.getFlag() != null){
            BaseDeparture departure = this.getDepartureByFlag(record.getFlag());
            if (departure.getFlag() == 3){
                departure.setId(departure.getId());
                departure.setFlag(0);
                departureMapper.updateByPrimaryKeySelective(departure);
            }
        }
        record.setFlag(record.getFlag());
	    record.setMark(null);
        return departureMapper.updateByPrimaryKeySelective(record)==1;
    }

    public boolean delDeparture(Long id){
//        String province, String city, String district
//        List<BaseChinaCities> addrList = dataService.selectByDetailAddrList(province, city, district);
//        if (addrList.size() > 0) {
//            for (BaseChinaCities chinaCities : addrList) {
//                chinaCities.setId(chinaCities.getId());
////                    System.out.println(chinaCities.getId());
//                chinaCities.setDepartureId(null);
//                dataService.updateByPrimaryKey(chinaCities);
//                if (dataService.updateChinaCities(chinaCities) == 0) {
//                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                    return false;
//                }
//            }
//        }
	    BaseDeparture departure = new BaseDeparture();
	    departure.setId(id);
	    departure.setMark(1);
        return departureMapper.updateByPrimaryKeySelective(departure)==1;
    }

//    public boolean binDing(String province,String city,String district, BaseDeparture departure){
//            List<BaseChinaCities> addrList = dataService.selectByDetailAddrList(province, city, district);
//            if (addrList.size() > 0) {
//                for (BaseChinaCities chinaCities : addrList) {
//                    chinaCities.setId(chinaCities.getId());
////                    System.out.println(chinaCities.getId());
//                    chinaCities.setDepartureId(departure.getId());
//                    dataService.updateChinaCities(chinaCities);
//                    if (dataService.updateChinaCities(chinaCities) == 0) {
//                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                        return false;
//                    }
//                }
//            }
//        return true;
//    }
//
//    public boolean unBinDing(String province,String city,String district){
//            List<BaseChinaCities> addrList = dataService.selectByDetailAddrList(province, city, district);
//            if (addrList.size() > 0) {
//                for (BaseChinaCities chinaCities : addrList) {
//                    chinaCities.setId(chinaCities.getId());
////                    System.out.println(chinaCities.getId());
//                    chinaCities.setDepartureId(null);
//                    dataService.updateByPrimaryKey(chinaCities);
//                    if (dataService.updateChinaCities(chinaCities) == 0) {
//                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                        return false;
//                    }
//                }
//            }
//        return true;
//    }

    public BaseDeparture getDepartureByFlag(Integer flag){
	    return departureMapper.getDepartureByFlag(flag);
    }

    public boolean checkNo(BaseDeparture record){
        if (StringUtils.isBlank(record.getdCode()) ){
            return false;
        }
        return departureMapper.checkNo(record) == null;
    }

    public boolean checkName(BaseDeparture record){
        if (StringUtils.isBlank(record.getdName())){
            return false;
        }
        return departureMapper.checkName(record) == null;
    }

    public int getDepartureListCount(BaseDeparture departure){
	    return departureMapper.getDepartureListCount(departure);
    }
}
