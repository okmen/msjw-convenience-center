package cn.convenience.dao;
import cn.convenience.bean.ActivityVoteRecord;

public interface IActivityVoteRecordDao {
	
	/**
	 * 写入投票记录
	 * @param record
	 * @return
	 */
	int addVoteRecord(ActivityVoteRecord record);
}
