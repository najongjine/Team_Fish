package com.biz.tour.service.usersea;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.biz.tour.domain.usersea.FishUserSeaCommentVO;

public interface UserSeaCommentService {

	public List<FishUserSeaCommentVO> findByFk(long ufc_fk, int pageno, int itemLimit);

	public int insert(FishUserSeaCommentVO commentVO, HttpSession session);
	
	public int countFindByFk(long ufc_fk);
}
