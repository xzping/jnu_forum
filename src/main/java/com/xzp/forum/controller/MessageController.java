package com.xzp.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MessageController {
	@RequestMapping(path="/message")
	public String message() {
		return "message";
	}
}
