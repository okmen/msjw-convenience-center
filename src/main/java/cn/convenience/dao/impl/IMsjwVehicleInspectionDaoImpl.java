package cn.convenience.dao.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.convenience.bean.MsjwVehicleInspectionVo;
import cn.convenience.dao.IMsjwVehicleInspectionDao;
import cn.convenience.dao.mapper.MsjwVehicleInspectionMapper;

@Repository
public class IMsjwVehicleInspectionDaoImpl implements IMsjwVehicleInspectionDao {

	@Autowired
	private MsjwVehicleInspectionMapper msjwVehicleInspectionMapper;
	
	@Override
	public int addMsjwVehicleInspection(MsjwVehicleInspectionVo msjwVehicleInspectionVo) {
		return msjwVehicleInspectionMapper.addMsjwVehicleInspection(msjwVehicleInspectionVo);
	}

	@Override
	public List<MsjwVehicleInspectionVo> selectMsjwVehicleInspectionStatusZero(Integer page, Integer pageSize) {
		return msjwVehicleInspectionMapper.selectMsjwVehicleInspectionStatusZero(page, pageSize);
	}

	@Override
	public int updateMsjwVehicleInspection(MsjwVehicleInspectionVo msjwVehicleInspectionVo) {
		return msjwVehicleInspectionMapper.updateMsjwVehicleInspection(msjwVehicleInspectionVo);
	}

	@Override
	public int deleteMsjwVehicleInspection(String tylsbh, String platNumber) {
		return msjwVehicleInspectionMapper.deleteMsjwVehicleInspection(tylsbh, platNumber);
	}

	
}
