package com.boyo.wuhang.service.assess;

import com.boyo.wuhang.dao.assess.WhDeliveryDetailsMapper;
import com.boyo.wuhang.entity.assess.WhDeliveryDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class WhDeliveryDetailsService {
    @Autowired
    private WhDeliveryDetailsMapper detailsMapper;

    public Map<String, BigDecimal> getWeight(Long id){
        return detailsMapper.getWeight(id);
    }

    public WhDeliveryDetails getStartDetail(Long deliverId){
        return detailsMapper.getStartDetail(deliverId);
    }

    public List<WhDeliveryDetails> getDetailById(long deliverId){
        return detailsMapper.getDetailById(deliverId);
    }

    public WhDeliveryDetails selectDetailByDeliverId(String carNo, Long deliverId){
        return detailsMapper.selectDetailByDeliverId(carNo, deliverId);
    }

}
