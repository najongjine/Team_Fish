package com.biz.tour.domain.userwater;

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
public class FishUserWaterCommentVO {
	private long ufc_id ;// AUTO_INCREMENT	PRIMARY KEY,
    private long ufc_pid;// nVARCHAR(50),
    private long ufc_fk;// nVARCHAR(150),
    private String ufc_username;// nVARCHAR(50),
    private String ufc_title;// nVARCHAR(2000)
    private String ufc_date;
    private String ufc_text;
}
