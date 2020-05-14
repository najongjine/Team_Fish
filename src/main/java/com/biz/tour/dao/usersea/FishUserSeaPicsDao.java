package com.biz.tour.dao.usersea;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.biz.tour.domain.usersea.FishUserSeaPicsVO;

public interface FishUserSeaPicsDao {
	@Select("select * from tbl_userfish_sea_pics where ufp_fk=#{ufp_fk}")
	public List<FishUserSeaPicsVO> findByFk(Long ufp_fk);

	int insert(FishUserSeaPicsVO seaPicsVO);

	@Delete("delete from tbl_userfish_sea_pics where ufp_id=#{ufp_id}")
	public int deleteById(Long ufp_id);
}
