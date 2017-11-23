package cn.convenience.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.convenience.bean.ActivityVote;
import cn.convenience.dao.IActivityVoteDao;
import cn.convenience.dao.mapper.ActivityVoteMapper;
@Repository
public class IActivityDaoImpl implements IActivityVoteDao{
	@Autowired
	private ActivityVoteMapper activityVoteMapper; 
	@Override
	public int deleteById(Integer id) throws Exception{
		return activityVoteMapper.deleteById(id);
	}

	@Override
	public int insert(ActivityVote record) throws Exception{
		return activityVoteMapper.insert(record);
	}

	@Override
	public ActivityVote selectById(Integer id) throws Exception{
		return activityVoteMapper.selectById(id);
	}

	@Override
	public List<ActivityVote> getVoteByPage(Integer page ,Integer pageSize) throws Exception{
		return activityVoteMapper.getVoteByPage(page ,pageSize);
	}

	@Override
	public int updateById(ActivityVote record) throws Exception{
		return activityVoteMapper.updateById(record);
	}

	@Override
	public int queryCount() throws Exception{
		return activityVoteMapper.queryCount();
	}

	@Override
	public List<ActivityVote> getFrontVote(Integer total) throws Exception {
		return activityVoteMapper.getFrontVote(total);
	}

	@Override
	public int queryCountSum() throws Exception {
		return activityVoteMapper.queryCountSum();
	}

	@Override
	public List<ActivityVote> selectByName(String name,int page ,int pageSize) throws Exception {
		return activityVoteMapper.selectByName(name,page ,pageSize);
	}
	
}
