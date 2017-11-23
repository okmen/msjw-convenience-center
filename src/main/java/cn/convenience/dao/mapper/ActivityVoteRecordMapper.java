package cn.convenience.dao.mapper;

import org.springframework.stereotype.Repository;

import cn.convenience.bean.ActivityVoteRecord;

@Repository
public interface ActivityVoteRecordMapper {
	
	/**
	 * 写入投票记录
	 * @param record
	 * @return
	 */
	int addVoteRecord(ActivityVoteRecord record);
}
