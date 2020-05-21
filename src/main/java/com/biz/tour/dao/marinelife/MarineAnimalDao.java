package com.biz.tour.dao.marinelife;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.biz.tour.domain.marinelife.MarineLifeAPIVO;

public interface MarineAnimalDao {
	
	
	void insertAnimal(List<MarineLifeAPIVO> marinList);

	
	List<MarineLifeAPIVO> getAllMarine();

	@Select("SELECT DISTINCT(sciKr) FROM tbl_marineanimal WHERE latD = #{mapX} AND lonD = #{mapY}")
	List<MarineLifeAPIVO> getXYMarine(@Param("mapX") int mapX, @Param("mapY") int mapY);

	@Select("SELECT DISTINCT(sciKr) FROM tbl_marineanimal ")
	List<String> selectAllTitle();

}
