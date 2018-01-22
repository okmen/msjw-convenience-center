package cn.convenience.dao.mapper;

import java.util.List;

import cn.convenience.bean.SzjjVote;

public interface SzjjVoteMapper {

    int updateById(List<String> voteIds) throws Exception;
    
    List<SzjjVote> getAllVote() throws Exception;
}