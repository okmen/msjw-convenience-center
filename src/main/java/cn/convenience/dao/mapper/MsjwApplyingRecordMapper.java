package cn.convenience.dao.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.convenience.bean.MsjwApplyingRecordVo;

@Repository
public interface MsjwApplyingRecordMapper {
	
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
