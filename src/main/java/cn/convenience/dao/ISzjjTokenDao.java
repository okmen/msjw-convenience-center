package cn.convenience.dao;

import cn.convenience.bean.SzjjToken;

public interface ISzjjTokenDao {
	
	  int insert(SzjjToken record);
	  
	  SzjjToken selectByIdentityCard(String identityCard);
}
