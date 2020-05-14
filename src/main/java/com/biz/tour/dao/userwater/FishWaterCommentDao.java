package com.biz.tour.dao.userwater;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.biz.tour.domain.userwater.FishUserWaterCommentVO;

public interface FishWaterCommentDao {

	@Select("select * from tbl_userfish_water_comment where ufc_fk=#{ufc_fk} and ufc_pid=0 order by ufc_date desc")
	List<FishUserWaterCommentVO> findByFk(long ufc_fk);
	
	@Select("select count(*) from tbl_userfish_water_comment where ufc_fk=#{ufc_fk}")
	public int countFindByFk(long ufc_fk);

	@Select("select * from tbl_userfish_water_comment where ufc_pid=#{ufc_id}  order by ufc_date desc")
	List<FishUserWaterCommentVO> findByPId(long ufc_id);

	int insert(FishUserWaterCommentVO commentVO);

}
