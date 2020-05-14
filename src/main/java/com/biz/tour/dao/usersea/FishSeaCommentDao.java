package com.biz.tour.dao.usersea;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.biz.tour.domain.usersea.FishUserSeaCommentVO;

public interface FishSeaCommentDao {

	@Select("select * from tbl_userfish_sea_comment where ufc_fk=#{ufc_fk} and ufc_pid=0 order by ufc_date desc")
	public List<FishUserSeaCommentVO> findByFk(long ufc_fk);
	
	@Select("select count(*) from tbl_userfish_sea_comment where ufc_fk=#{ufc_fk}")
	public int countFindByFk(long ufc_fk);

	@Select("select * from tbl_userfish_sea_comment where ufc_pid=#{ufc_id}  order by ufc_date desc")
	public List<FishUserSeaCommentVO> findByPId(long ufc_id);

	int insert(FishUserSeaCommentVO commentVO);

}
