package com.biz.tour.service.usersea;

import java.util.List;

import com.biz.tour.domain.usersea.FishUserSeaPicsVO;

public interface UserSeaPicsService {
	public int insert(FishUserSeaPicsVO seaPicsVO);

	public List<FishUserSeaPicsVO> findByFK(Long fk);

	public int deleteById(Long ufp_id);
}
