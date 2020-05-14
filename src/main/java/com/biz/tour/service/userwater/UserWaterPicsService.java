package com.biz.tour.service.userwater;

import java.util.List;

import com.biz.tour.domain.userwater.FishUserWaterPicsVO;

public interface UserWaterPicsService {
	public int insert(FishUserWaterPicsVO waterPicsVO);

	public List<FishUserWaterPicsVO> findByFK(Long fk);

	public int deleteById(Long ufp_id);
}
