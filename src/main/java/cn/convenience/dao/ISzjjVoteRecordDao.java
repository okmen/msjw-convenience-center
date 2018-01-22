package cn.convenience.dao;
import cn.convenience.bean.SzjjVoteRecord;

public interface ISzjjVoteRecordDao {
	
	/**
	 * 写入投票记录
	 * @param record
	 * @return
	 */
	int addSzjjVoteRecord(SzjjVoteRecord record);
}
