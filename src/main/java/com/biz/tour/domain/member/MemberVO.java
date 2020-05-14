package com.biz.tour.domain.member;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MemberVO {
	private long u_id;
	@NotBlank(message = "* 아이디를 입력해 주세요")
	private String u_name;
	
	@NotBlank(message = "* 비번을 입력해 주세요")
	private String u_password;
	private String u_repassword;
	private String u_role;
	
	private boolean enabled;
	
	private String email;
	private String phone;
	private String address;
	private int point;
	
	private String profile_pic;
}
