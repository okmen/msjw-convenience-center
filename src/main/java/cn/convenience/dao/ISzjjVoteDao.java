package cn.convenience.dao;

import java.util.List;

import cn.convenience.bean.SzjjVote;

public interface ISzjjVoteDao {
	
    int updateById(String [] voteIds) throws Exception;
    
    List<SzjjVote> getAllVote() throws Exception;

}
