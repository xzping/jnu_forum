package com.xzp.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xzp.forum.model.PageBean;
import com.xzp.forum.model.Topic;
import com.xzp.forum.service.PageService;
import com.xzp.forum.util.ForumUtil;

@Controller
public class PageHelperController {
	
	@Autowired
	public PageService pageService;
	
//	@RequestMapping(path="/topics/{category}/{currentPage}",method=RequestMethod.GET)
//	@ResponseBody
	public String pageHelper(@PathVariable String category, @PathVariable int currentPage) {
		PageBean<Topic> pageTopic=pageService.findItemByPage(category, currentPage, 5);
		List<Topic> topics=pageTopic.getItems();
		return ForumUtil.getJSONString(1, topics);
	}
}
