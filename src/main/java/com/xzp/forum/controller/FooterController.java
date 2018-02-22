package com.xzp.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xzp.forum.util.HostHolder;

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
