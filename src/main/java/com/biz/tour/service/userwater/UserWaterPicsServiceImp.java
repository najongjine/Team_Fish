package com.biz.tour.service.userwater;

import java.util.List;

import org.springframework.stereotype.Service;

import com.biz.tour.dao.userwater.FishUserWaterPicsDao;
import com.biz.tour.domain.userwater.FishUserWaterPicsVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserWaterPicsServiceImp implements UserWaterPicsService{
	private final FishUserWaterPicsDao waterpicsDao;
	
	public int insert(FishUserWaterPicsVO waterPicsVO) {
		int ret=waterpicsDao.insert(waterPicsVO);
		return ret;
	}

	public List<FishUserWaterPicsVO> findByFK(Long fk) {
		// TODO Auto-generated method stub
		return waterpicsDao.findByFk(fk);
	}

	public int deleteById(Long ufp_id) {
		// TODO Auto-generated method stub
		return waterpicsDao.deleteById(ufp_id);
	}
}
