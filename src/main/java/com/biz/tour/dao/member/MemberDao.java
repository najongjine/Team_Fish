package com.biz.tour.dao.member;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.biz.tour.domain.member.MemberVO;

public interface MemberDao {
	@Select("select * from tbl_members where u_name=#{u_name}")
	public MemberVO findByUName(String u_name);
	
	public int insert(MemberVO memberVO);
	
	@Delete("delete from tbl where u_name=#{u_name}")
	public int delete(String u_name);

	public int update(MemberVO userVO);
}
