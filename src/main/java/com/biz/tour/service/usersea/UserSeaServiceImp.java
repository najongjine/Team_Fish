package com.biz.tour.service.usersea;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.biz.tour.dao.usersea.FishUserSeaDao;
import com.biz.tour.domain.usersea.FishUserSeaVO;
import com.biz.tour.service.util.GetCurrentDateServiceImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSeaServiceImp implements UserSeaService{
	private final FishUserSeaDao seaDao;
	private final GetCurrentDateServiceImp curTimeService;
	
	public int insert(FishUserSeaVO seaVO,HttpSession session) {
		String loggedName=(String)session.getAttribute("U_NAME");
		String curTime=curTimeService.getCurDate();
		seaVO.setUf_date(curTime);
		seaVO.setUf_username(loggedName);
		int ret=seaDao.insert(seaVO);
		return ret;
	}
	
	public long getMaxID() {
		return seaDao.getMaxID();
	}

	public FishUserSeaVO findById(Long uf_id) {
		// TODO Auto-generated method stub
		return seaDao.findById(uf_id);
	}

	public int update(FishUserSeaVO userVO) {
		return seaDao.update(userVO);
	}

	public List<FishUserSeaVO> findByTitle(String inputStr, int pageno, int itemLimit) {
		// TODO Auto-generated method stub
		return seaDao.findByTitle(inputStr,pageno,itemLimit);
	}

	public List<FishUserSeaVO> findAll(int pageno, int itemLimit) {
		// TODO Auto-generated method stub
		return seaDao.findAll(pageno,itemLimit);
	}
	
	public int delete(long uf_id, String loggedName) {
		// TODO Auto-generated method stub
		FishUserSeaVO userVO=seaDao.findById(uf_id);
		if(!userVO.getUf_username().equals(loggedName)) {
			return -1;
		}
		return seaDao.delete(uf_id);
	}
	
	public int countFindAll() {
		return seaDao.countFindAll();
	}
	
	public int countFindByTitle(String uf_title) {
		return seaDao.countFindByTitle(uf_title);
	}
}
