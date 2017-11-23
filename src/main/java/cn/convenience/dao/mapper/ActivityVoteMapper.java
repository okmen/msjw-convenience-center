package cn.convenience.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.convenience.bean.ActivityVote;

public interface ActivityVoteMapper {
    int deleteById(Integer id) throws Exception;

    int insert(ActivityVote record) throws Exception;

    ActivityVote selectById(Integer id) throws Exception;

    List<ActivityVote> getVoteByPage(@Param("page")Integer page ,@Param("pageSize")Integer pageSize) throws Exception;

    int updateById(ActivityVote record) throws Exception;
    
    int queryCount() throws Exception;
    
    List<ActivityVote> getFrontVote(Integer total) throws Exception;
    
    int queryCountSum() throws Exception;
}