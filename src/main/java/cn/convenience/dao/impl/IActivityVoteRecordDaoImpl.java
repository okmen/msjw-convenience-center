package cn.convenience.dao.impl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.convenience.bean.ActivityVoteRecord;
import cn.convenience.dao.IActivityVoteRecordDao;
import cn.convenience.dao.mapper.ActivityVoteRecordMapper;

@Repository
public class IActivityVoteRecordDaoImpl implements IActivityVoteRecordDao {

	protected Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private ActivityVoteRecordMapper activityVoteRecordMapper;
	
	@Override
	public int addVoteRecord(ActivityVoteRecord record) {
		return activityVoteRecordMapper.addVoteRecord(record);
	}
}
