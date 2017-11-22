package cn.convenience.dao;

import java.util.List;

import cn.convenience.bean.ActivityVote;

public interface IActivityVoteDao {
	int deleteById(Integer id) throws Exception;

    int insert(ActivityVote record) throws Exception;

    ActivityVote selectById(Integer id) throws Exception;

    List<ActivityVote> getVoteByPage(Integer page ,Integer pageSize) throws Exception;

    int updateById(ActivityVote record) throws Exception;
    
    int queryCount() throws Exception;
    
    List<ActivityVote> getFrontVote(Integer total) throws Exception;
}
