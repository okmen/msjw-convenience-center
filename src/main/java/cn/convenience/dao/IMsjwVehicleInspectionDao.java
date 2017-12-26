package cn.convenience.dao;
import java.util.List;

import cn.convenience.bean.MsjwVehicleInspectionVo;

public interface IMsjwVehicleInspectionDao {
	
	/**
	 * 新增记录
	 * @param msjwVehicleInspectionVo
	 * @return
	 */
	int addMsjwVehicleInspection(MsjwVehicleInspectionVo msjwVehicleInspectionVo);
	
	/**
	 * 查询审核状态为0-待审核记录
	 * @param page
	 * @param pageSize
	 * @return
	 */
	List<MsjwVehicleInspectionVo> selectMsjwVehicleInspectionStatusZero(Integer page, Integer pageSize);
	
	/**
	 * 根据tylsbh和platNumber更新数据库状态
	 * @param msjwVehicleInspectionVo
	 * @return
	 */
	int updateMsjwVehicleInspection(MsjwVehicleInspectionVo msjwVehicleInspectionVo);
	
	/**
	 * 根据tylsbh和platNumber删除记录
	 * @param tylsbh
	 * @param platNumber
	 * @return
	 */
	int deleteMsjwVehicleInspection(String tylsbh, String platNumber);
}
