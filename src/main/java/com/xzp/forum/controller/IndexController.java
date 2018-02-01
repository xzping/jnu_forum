package com.xzp.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页接口
 * 
 * @author xiezhiping
 *
 */
@Controller
public class IndexController {
	@RequestMapping(path = "/")
	public String index() {
		return "index";
	}
}
