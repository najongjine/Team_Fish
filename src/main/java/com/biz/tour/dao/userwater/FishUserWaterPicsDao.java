package com.biz.tour.dao.userwater;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.biz.tour.domain.userwater.FishUserWaterPicsVO;

public interface FishUserWaterPicsDao {
	@Select("select * from tbl_userfish_water_pics where ufp_fk=#{ufp_fk}")
	public List<FishUserWaterPicsVO> findByFk(Long ufp_fk);

	int insert(FishUserWaterPicsVO waterPicsVO);

	@Delete("delete from tbl_userfish_water_pics where ufp_id=#{ufp_id}")
	public int deleteById(Long ufp_id);
}
