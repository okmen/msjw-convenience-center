package cn.convenience.dao;
import java.util.List;

import cn.convenience.bean.MsjwApplyingRecordVo;

public interface IMsjwApplyingRecordDao {
	
	/**
	 * 写入到已完结表中
	 * @param msjwApplyingRecordVo
	 * @return
	 */
	int addMsjwFinishedRecord(MsjwApplyingRecordVo msjwApplyingRecordVo);
	
	int insertMsjwApplyingRecord(MsjwApplyingRecordVo msjwApplyingRecordVo);
	
	int updateMsjwApplyingRecordById(MsjwApplyingRecordVo msjwApplyingRecordVo);
	
	int deleteMsjwApplyingRecordById(Integer id);
	
	List<String> selectIdentityIdAll();
	
	MsjwApplyingRecordVo selectMsjwApplyingRecordByTylsbh(String tylsbh);
}
