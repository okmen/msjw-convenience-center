package cn.convenience.dao.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.convenience.bean.SzjjVote;
import cn.convenience.dao.ISzjjVoteDao;
import cn.convenience.dao.mapper.SzjjVoteMapper;
@Repository
public class ISzjjVoteDaoImpl implements ISzjjVoteDao{
	@Autowired
	private SzjjVoteMapper szjjVoteMapper;
	
	@Override
	public int updateById(String [] voteIds) throws Exception {
		List<String> asList = Arrays.asList(voteIds);
		return szjjVoteMapper.updateById(asList);
	}

	@Override
	public List<SzjjVote> getAllVote() throws Exception {
		return szjjVoteMapper.getAllVote();
	}

	
}
