package com.biz.tour.dao.marinelife;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.biz.tour.domain.marinelife.NaverAPIVO;

public interface NaverAPIDao {

	@Select("SELECT * FROM tbl_naverapi WHERE title = #{queryName}")
	NaverAPIVO selectByTitle(String queryName);

	
	void insertFull(List<NaverAPIVO> naverAPIResultList);

	@Insert("INSERT INTO tbl_naverapi(title,isfullinfo) VALUES(#{title},#{isFullInfo})")
	void insertTitle(@Param("title") String title, @Param("isFullInfo")  boolean isFullInfo);

	
	
	
}
