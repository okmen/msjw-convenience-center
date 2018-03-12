package cn.convenience.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.convenience.bean.SzjjToken;
import cn.convenience.dao.ISzjjTokenDao;
import cn.convenience.dao.mapper.SzjjTokenMapper;
@Repository
public class ISzjjTokenDaoImpl implements ISzjjTokenDao{
	@Autowired
	private SzjjTokenMapper szjjTokenMapper;

	@Override
	public int insert(SzjjToken record) {
		return szjjTokenMapper.insert(record);
	}

	@Override
	public SzjjToken selectByIdentityCard(String identityCard) {
		return szjjTokenMapper.selectByIdentityCard(identityCard);
	}
	
}
