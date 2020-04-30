package com.boyo.wuhang.service.assess;

import com.boyo.wuhang.dao.assess.WhAccessPairMapper;
import com.boyo.wuhang.entity.assess.WhAccessPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessPairService {
	@Autowired
	private WhAccessPairMapper pairMapper;

	public WhAccessPair getAccessPairById(Long id){
		return pairMapper.selectByPrimaryKey(id);
	}

	public boolean insertAccessPair(WhAccessPair accessPair){
		return pairMapper.insertSelective(accessPair)==1;
	}

	public boolean updateAccessPair(WhAccessPair accessPair){
		return pairMapper.updateByPrimaryKey(accessPair)==1;
	}

	public boolean deleteAccessPair(WhAccessPair accessPair){
		return pairMapper.deleteByPrimaryKey(accessPair.getId())==1;
	}

	public WhAccessPair getPairByTwoId(WhAccessPair accessPair){
		return pairMapper.getPairByTwoId(accessPair);
	}

	public WhAccessPair getPairByEmployeeId(Long employeeId){
		return pairMapper.getPairByEmployeeId(employeeId);
	}

	public List<WhAccessPair> getPairList(){
		return pairMapper.getPairList();
	}
}
