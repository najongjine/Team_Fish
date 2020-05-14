package com.biz.tour.service.userwater;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.biz.tour.domain.userwater.FishUserWaterCommentVO;

public interface UserWaterCommentService {
	public List<FishUserWaterCommentVO> findByFk(long ufc_fk, int pageno, int itemLimit);

	public int insert(FishUserWaterCommentVO commentVO, HttpSession session);
	
	public int countFindByFk(long ufc_fk);
}
