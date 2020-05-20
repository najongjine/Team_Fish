package com.biz.tour.dao.member;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.biz.tour.domain.member.MemberVO;

public interface MemberDao {
	@Select("select * from tbl_members where u_id=#{id}")
	public MemberVO findById(long id);
	
	@Select("select * from tbl_members where u_name=#{u_name}")
	public MemberVO findByUName(String u_name);
	
	public int insert(MemberVO memberVO);
	
	@Delete("delete from tbl where u_name=#{u_name}")
	public int delete(String u_name);

	public int update(MemberVO userVO);

	public int pwupdate(MemberVO memberVO);

	public MemberVO findByUserEmail(String email);

	public int re_update(MemberVO memberVO);

	public int date_update(@Valid MemberVO memberVO);

}
