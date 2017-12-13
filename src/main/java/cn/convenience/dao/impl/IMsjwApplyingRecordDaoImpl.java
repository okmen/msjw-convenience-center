package cn.convenience.dao.impl;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.convenience.bean.MsjwApplyingRecordVo;
import cn.convenience.dao.IMsjwApplyingRecordDao;
import cn.convenience.dao.mapper.MsjwApplyingRecordMapper;

@Repository
public class IMsjwApplyingRecordDaoImpl implements IMsjwApplyingRecordDao {

	protected Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private MsjwApplyingRecordMapper msjwApplyingRecordMapper;
	
	@Override
	public int insertMsjwApplyingRecord(MsjwApplyingRecordVo msjwApplyingRecordVo) {
		return msjwApplyingRecordMapper.insertMsjwApplyingRecord(msjwApplyingRecordVo);
	}
	@Override
	public int updateMsjwApplyingRecordById(MsjwApplyingRecordVo msjwApplyingRecordVo) {
		return msjwApplyingRecordMapper.updateMsjwApplyingRecordById(msjwApplyingRecordVo);
	}
	@Override
	public int deleteMsjwApplyingRecordById(Integer id) {
		return msjwApplyingRecordMapper.deleteMsjwApplyingRecordById(id);
	}
	@Override
	public List<String> selectIdentityIdAll() {
		return msjwApplyingRecordMapper.selectIdentityIdAll();
	}
	@Override
	public MsjwApplyingRecordVo selectMsjwApplyingRecordByTylsbh(String tylsbh) {
		return msjwApplyingRecordMapper.selectMsjwApplyingRecordByTylsbh(tylsbh);
	}
	@Override
	public int addMsjwFinishedRecord(MsjwApplyingRecordVo msjwApplyingRecordVo) {
		return msjwApplyingRecordMapper.addMsjwFinishedRecord(msjwApplyingRecordVo);
	}
	
}
