package com.biz.tour.service.usersea;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.biz.tour.domain.usersea.FishUserSeaVO;

public interface UserSeaService {
	public int insert(FishUserSeaVO seaVO,HttpSession session);
	
	public long getMaxID();

	public FishUserSeaVO findById(Long uf_id);

	public int update(FishUserSeaVO userVO);

	public List<FishUserSeaVO> findByTitle(String inputStr, int pageno, int itemLimit);

	public List<FishUserSeaVO> findAll(int pageno, int itemLimit);
	
	public int delete(long uf_id, String loggedName);
	
	public int countFindAll();
	
	public int countFindByTitle(String uf_title);
}
