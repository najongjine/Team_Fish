package com.biz.tour.service.usersea;

import java.util.List;

import org.springframework.stereotype.Service;

import com.biz.tour.dao.usersea.FishUserSeaPicsDao;
import com.biz.tour.domain.usersea.FishUserSeaPicsVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSeaPicsServiceImp implements UserSeaPicsService{
	private final FishUserSeaPicsDao seapicsDao;
	
	public int insert(FishUserSeaPicsVO seaPicsVO) {
		int ret=seapicsDao.insert(seaPicsVO);
		return ret;
	}

	public List<FishUserSeaPicsVO> findByFK(Long fk) {
		// TODO Auto-generated method stub
		return seapicsDao.findByFk(fk);
	}

	public int deleteById(Long ufp_id) {
		// TODO Auto-generated method stub
		return seapicsDao.deleteById(ufp_id);
	}
}
