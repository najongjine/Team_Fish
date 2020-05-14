package com.biz.tour.domain;

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
public class UserSearchVO {
	private String searchOption;
	private String inputStr;
	{
		searchOption="";
		inputStr="";
	}
}
