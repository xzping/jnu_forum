package com.xzp.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * footer部分的接口
 * 
 * @author xiezhiping
 *
 */
@Controller
public class FooterController {
	
	@RequestMapping(path = "/aboutUs")
	public String aboutUs() {
		return "aboutUs";
	}
	
	@RequestMapping(path = "/title")
	public String title() {
		return "title";
	}
}
