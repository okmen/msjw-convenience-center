package cn.convenience.dao.mapper;

import java.util.List;

import cn.convenience.bean.SzjjVote;

public interface SzjjVoteMapper {

    int updateById(int id) throws Exception;
    
    List<SzjjVote> getAllVote() throws Exception;
}