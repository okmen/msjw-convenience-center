package cn.convenience.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.convenience.bean.MsjwVehicleInspectionVo;

@Repository
public interface MsjwVehicleInspectionMapper {
	
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
	List<MsjwVehicleInspectionVo> selectMsjwVehicleInspectionStatusZero(@Param("page")Integer page, @Param("pageSize")Integer pageSize);
	
	/**
	 * 根据tylsbh和platNumber更新数据库状态
	 * @param msjwVehicleInspectionVo
	 * @return
	 */
	int updateMsjwVehicleInspection(MsjwVehicleInspectionVo msjwVehicleInspectionVo);
	
	/**
	 * 根据tylsbh和platNumber删除记录
	 * @param tylsbh 流水号
	 * @param platNumber 车牌号
	 * @return
	 */
	int deleteMsjwVehicleInspection(@Param("tylsbh")String tylsbh, @Param("platNumber")String platNumber);
}
