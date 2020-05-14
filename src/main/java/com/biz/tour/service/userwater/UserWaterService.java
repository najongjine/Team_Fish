package com.biz.tour.service.userwater;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.biz.tour.domain.userwater.FishUserWaterVO;

public interface UserWaterService {
	public int insert(FishUserWaterVO waterVO,HttpSession session);
	
	public long getMaxID();

	public FishUserWaterVO findById(Long uf_id);

	public int update(FishUserWaterVO userVO);

	public List<FishUserWaterVO> findByTitle(String inputStr, int pageno, int itemLimit);

	public List<FishUserWaterVO> findAll(int pageno, int itemLimit);

	public int delete(long uf_id, String loggedName);
	
	public int countFindAll();
	
	public int countFindByTitle(String uf_title);
}
