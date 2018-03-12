package cn.convenience.dao.mapper;

import java.util.List;

import cn.convenience.bean.SzjjToken;

public interface SzjjTokenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SzjjToken record);

    SzjjToken selectByPrimaryKey(Integer id);

    List<SzjjToken> selectAll();

    int updateByPrimaryKey(SzjjToken record);
    
    SzjjToken selectByIdentityCard(String identityCard);
}