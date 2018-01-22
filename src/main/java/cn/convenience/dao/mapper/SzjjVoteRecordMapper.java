package cn.convenience.dao.mapper;

import org.springframework.stereotype.Repository;

import cn.convenience.bean.SzjjVoteRecord;

@Repository
public interface SzjjVoteRecordMapper {
	
	/**
	 * 写入投票记录
	 * @param record
	 * @return
	 */
	int addSzjjVoteRecord(SzjjVoteRecord record);
}
