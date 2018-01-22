package cn.convenience.dao.impl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.convenience.bean.SzjjVoteRecord;
import cn.convenience.dao.ISzjjVoteRecordDao;
import cn.convenience.dao.mapper.SzjjVoteRecordMapper;

@Repository
public class ISzjjVoteRecordDaoImpl implements ISzjjVoteRecordDao {

	protected Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private SzjjVoteRecordMapper szjjVoteRecordMapper;
	
	@Override
	public int addSzjjVoteRecord(SzjjVoteRecord record) {
		// TODO Auto-generated method stub
		return szjjVoteRecordMapper.addSzjjVoteRecord(record);
	}

}
