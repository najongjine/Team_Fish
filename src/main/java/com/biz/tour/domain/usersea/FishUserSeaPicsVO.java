package com.biz.tour.domain.usersea;

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
public class FishUserSeaPicsVO {
	private long ufp_id;// bigint AUTO_INCREMENT	PRIMARY KEY,
	private long ufp_fk;// nVARCHAR(50),
	private String ufp_originalFName;// nVARCHAR(50),
	private String ufp_uploadedFName;// nVARCHAR(150)
}
