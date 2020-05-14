package com.biz.tour.service.usersea;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.biz.tour.dao.usersea.FishSeaCommentDao;
import com.biz.tour.domain.usersea.FishUserSeaCommentVO;
import com.biz.tour.service.util.GetCurrentDateServiceImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSeaCommentServiceImp implements UserSeaCommentService{
	private final FishSeaCommentDao scDao;
	private final GetCurrentDateServiceImp dateService;

	public List<FishUserSeaCommentVO> findByFk(long ufc_fk, int pageno, int itemLimit) {
		// 댓글중 타 댓글에 의존이 없는 (pid=0)인 댓글들 뽑음
		List<FishUserSeaCommentVO> commentList=scDao.findByFk(ufc_fk);
		
		//댓글 계층구조가 다 완성된 list를 담을 객체
		List<FishUserSeaCommentVO> sortedCommentList=new ArrayList<FishUserSeaCommentVO>();
		
		//재귀 함수를 통해서 pid 가 없는 답글 기준으로...
		for(FishUserSeaCommentVO vo:commentList) {
			sortedCommentList.addAll(sortReplyHeriarchy(vo, 0));
		}
		List<FishUserSeaCommentVO> pgStedCmtList=new ArrayList<FishUserSeaCommentVO>();
		for(int i=(pageno*itemLimit);i<(pageno+1)*itemLimit;i++) {
			try {
				pgStedCmtList.add(sortedCommentList.get(i));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		return pgStedCmtList;
	}

	/*
	 * 게시판 코멘트 답변 리스트 가져오기
	 */
	private List<FishUserSeaCommentVO> sortReplyHeriarchy(FishUserSeaCommentVO cmtVO, int depth){
		// pid 0 인 댓글 하나 기준으로 child 댓글 heriarchy 를 만들 list
		List<FishUserSeaCommentVO> retList=new ArrayList<FishUserSeaCommentVO>();
		if(depth>0) {
			String c_header="&nbsp;";
			for(int i=0;i<depth;i++) {
				c_header+=" re: ";
			}
			String moddedText=c_header+cmtVO.getUfc_text();
			cmtVO.setUfc_text(moddedText);
		}
		// 매개 변수로 받은 댓글vo(부모)vo를 list에 먼저 추가 시켜줌
		retList.add(cmtVO);
		
		//부모 id와 같은 pid를 가진 child 댓글들 뽑음.
		List<FishUserSeaCommentVO> tempList=scDao.findByPId(cmtVO.getUfc_id());
		// child 댓글이 더이상 없으면 재귀함수 exit
		if(tempList.size()<1) return retList;
		
		for(FishUserSeaCommentVO vo:tempList) {
			// 각 child 댓글들을 기준으로 또 child-child 댓글이 있나 재귀함수로 확인.
			retList.addAll(sortReplyHeriarchy(vo, depth+1));
		}
		return retList;
	}

	public int insert(FishUserSeaCommentVO commentVO, HttpSession session) {
		// TODO Auto-generated method stub
		String username=(String) session.getAttribute("U_NAME");
		String curDate=dateService.getCurDate();
		commentVO.setUfc_date(curDate);
		commentVO.setUfc_username(username);
		return scDao.insert(commentVO);
	}
	
	public int countFindByFk(long ufc_fk) {
		return scDao.countFindByFk(ufc_fk);
	}
}
