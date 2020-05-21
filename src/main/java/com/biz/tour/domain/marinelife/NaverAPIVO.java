package com.biz.tour.domain.marinelife;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class NaverAPIVO {

	private long id;
	private String title;
	private String link;
	private String description;
	private String thumbnail;
	private boolean isFullInfo;
	
	
	
}
