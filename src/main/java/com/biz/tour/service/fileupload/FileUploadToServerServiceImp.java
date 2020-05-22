package com.biz.tour.service.fileupload;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.biz.tour.domain.member.MemberVO;
import com.biz.tour.domain.usersea.FishUserSeaPicsVO;
import com.biz.tour.domain.userwater.FishUserWaterPicsVO;
import com.biz.tour.service.member.MemberService;
import com.biz.tour.service.usersea.UserSeaPicsService;
import com.biz.tour.service.usersea.UserSeaService;
import com.biz.tour.service.userwater.UserWaterPicsService;
import com.biz.tour.service.userwater.UserWaterService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileUploadToServerServiceImp implements FileUploadToServerService{
	private final UserWaterPicsService uwPicsService;
	private final UserSeaPicsService usPicsService;
	private final UserWaterService uwService;
	private final UserSeaService usService;
	private final MemberService memberService;
	// 바다낚시 사진 테이블 선언해 줘야함

	// servlet-context.xml에 설정된 파일 저장 경로 정보를 가져와서 사용하기
	private final String filePath;

	/*
	 * 원래 파일이름을 UUID 부착된 파일이름으로 변경하고 변경된 이름으로 서버의 filePath에 저장하고 변경된 파일이름을 return
	 */
	public String filesUp(MultipartHttpServletRequest uploaded_files, String whichTable,long fk) {
		// uploaded_files.getFiles("uploaded_files") 이부분은 jsp form input 에서 지정한 name과
		// 동일해야함
		for (MultipartFile file : uploaded_files.getFiles("uploaded_files")) {
			if (file.isEmpty())
				return null;
			fileUp(file, whichTable,fk);
		}
		return null;
	}

	public String fileUp(MultipartFile uploadedFile, String whichTable,long fk) {
		if (uploadedFile.isEmpty())
			return null;

		log.debug("## uploadedFile: "+uploadedFile.getOriginalFilename().toString());
		// upload할 filePath가 있는지 확인을 하고
		// 없으면 폴더를 생성
		File dir = new File(filePath);
		if (dir.exists()) {
			dir.mkdirs();
		}

		// 파일이름을 추출(그림.jpg)
		String originalFileName = uploadedFile.getOriginalFilename();

		// UUID가 부착된 새로운 이름을 생성(UUID그림.jpg)
		String strUUID = UUID.randomUUID().toString();
		String UploadedFName = strUUID + originalFileName;

		// filePath와 변경된 파일이름을 결합하여 empty 파일 객체를 생성
		File serverFile = new File(filePath, UploadedFName);

		// upFile을 serverFile 이름으로 복사 수행
		try {
			uploadedFile.transferTo(serverFile);

			// water 이면 wtarePics 테이블에 저장
			if (whichTable.equalsIgnoreCase("water")) {
				FishUserWaterPicsVO uwPicsVO = new FishUserWaterPicsVO();
				//long fk = uwService.getMaxID();
				uwPicsVO.setUfp_fk(fk);
				uwPicsVO.setUfp_originalFName(originalFileName);
				uwPicsVO.setUfp_uploadedFName(UploadedFName);
				int ret = uwPicsService.insert(uwPicsVO);
				log.debug("!!! pic upload ret :" + ret);
			}
			else if (whichTable.equalsIgnoreCase("sea")) {
				FishUserSeaPicsVO usPicsVO = new FishUserSeaPicsVO();
				//long fk = usService.getMaxID();
				usPicsVO.setUfp_fk(fk);
				usPicsVO.setUfp_originalFName(originalFileName);
				usPicsVO.setUfp_uploadedFName(UploadedFName);
				int ret = usPicsService.insert(usPicsVO);
				log.debug("!!! pic upload ret :" + ret);
			}
			else if (whichTable.equalsIgnoreCase("tbl_members")) {
				//여기서 fk는 외래키 아님... 진짜 member 테이블의 Id임
				log.debug("## tbl member pic upload 진입");
				MemberVO memberVO=memberService.findById(fk);

				memberVO.setProfile_pic(UploadedFName);
				log.debug("## pic upload memberVO: "+memberVO.toString());

				int ret=memberService.update(memberVO);
				log.debug("## pic upload ret: "+ret);
			}

			return UploadedFName;
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	} // 테이블에 이미지 저장

	public String fileUp(MultipartFile uploadedFile) {
		if (uploadedFile.isEmpty())
			return null;

		// upload할 filePath가 있는지 확인을 하고
		// 없으면 폴더를 생성
		File dir = new File(filePath);
		if (dir.exists()) {
			dir.mkdirs();
		}

		// 파일이름을 추출(그림.jpg)
		String originalFileName = uploadedFile.getOriginalFilename();

		// UUID가 부착된 새로운 이름을 생성(UUID그림.jpg)
		String strUUID = UUID.randomUUID().toString();
		String UploadedFName = strUUID + originalFileName;

		// filePath와 변경된 파일이름을 결합하여 empty 파일 객체를 생성
		File serverFile = new File(filePath, UploadedFName);

		// upFile을 serverFile 이름으로 복사 수행
		try {
			uploadedFile.transferTo(serverFile);

			return UploadedFName;
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
