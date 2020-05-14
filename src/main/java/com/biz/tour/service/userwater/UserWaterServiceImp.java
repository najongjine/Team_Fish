package com.biz.tour.service.userwater;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.biz.tour.dao.userwater.FishUserWaterDao;
import com.biz.tour.domain.userwater.FishUserWaterVO;
import com.biz.tour.service.util.GetCurrentDateServiceImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserWaterServiceImp implements UserWaterService{
	private final FishUserWaterDao waterDao;
	private final GetCurrentDateServiceImp curTimeService;
	
	public int insert(FishUserWaterVO waterVO,HttpSession session) {
		String loggedName=(String)session.getAttribute("U_NAME");
		String curTime=curTimeService.getCurDate();
		waterVO.setUf_date(curTime);
		waterVO.setUf_username(loggedName);
		int ret=waterDao.insert(waterVO);
		return ret;
	}
	
	public long getMaxID() {
		return waterDao.getMaxID();
	}

	public FishUserWaterVO findById(Long uf_id) {
		// TODO Auto-generated method stub
		return waterDao.findById(uf_id);
	}

	public int update(FishUserWaterVO userVO) {
		return waterDao.update(userVO);
	}

	public List<FishUserWaterVO> findByTitle(String inputStr, int pageno, int itemLimit) {
		// TODO Auto-generated method stub
		return waterDao.findByTitle(inputStr,pageno,itemLimit);
	}

	public List<FishUserWaterVO> findAll(int pageno, int itemLimit) {
		// TODO Auto-generated method stub
		return waterDao.findAll(pageno,itemLimit);
	}

	public int delete(long uf_id, String loggedName) {
		// TODO Auto-generated method stub
		FishUserWaterVO userVO=waterDao.findById(uf_id);
		if(!userVO.getUf_username().equals(loggedName)) {
			return -1;
		}
		return waterDao.delete(uf_id);
	}
	
	public int countFindAll() {
		return waterDao.countFindAll();
	}
	
	public int countFindByTitle(String uf_title) {
		return waterDao.countFindByTitle(uf_title);
	}
}
