package com.biz.tour.controller;

import java.net.URISyntaxException;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.biz.tour.service.TourService;
import com.biz.tour.service.fileupload.FileUploadToServerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
	private final TourService tourService;
	private final FileUploadToServerService fService;
	
	//,produces = "text/json;charset=UTF-8"
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) throws URISyntaxException {
		
		 return "fishing/fishMain";
	}
	
	@ResponseBody
	@RequestMapping(value = "/image_up",method=RequestMethod.POST,produces = "text/html;charset=utf-8")
	public String fileUp(MultipartFile upFile) {
		log.debug("file up resp");
		String retFileName=fService.fileUp(upFile);
		
		if(retFileName==null) {
			return "FAIL";
		}
		return retFileName;
	}
	
}
