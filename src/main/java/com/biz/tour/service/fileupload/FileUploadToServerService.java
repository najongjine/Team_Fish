package com.biz.tour.service.fileupload;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface FileUploadToServerService {

	/*
	 * 원래 파일이름을 UUID 부착된 파일이름으로 변경하고 변경된 이름으로 서버의 filePath에 저장하고 변경된 파일이름을 return
	 */
	public String filesUp(MultipartHttpServletRequest uploaded_files, String whichTable,long fk);

	public String fileUp(MultipartFile uploadedFile, String whichTable,long fk);

	public String fileUp(MultipartFile uploadedFile);
}
