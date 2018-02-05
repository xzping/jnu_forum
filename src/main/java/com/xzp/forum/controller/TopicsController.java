package com.xzp.forum.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xzp.forum.dao.AnswerDao;
import com.xzp.forum.dao.TopicDao;
import com.xzp.forum.dao.UserDao;
import com.xzp.forum.model.Answer;
import com.xzp.forum.model.Topic;
import com.xzp.forum.model.User;
import com.xzp.forum.util.LocalHost;
import com.xzp.forum.util.ViewObject;

@Controller
public class TopicsController {
	@Autowired
	private UserDao userDao;

	@Autowired
	private TopicDao topicDao;

	@Autowired
	private AnswerDao answerDao;
	
	@Autowired
	private LocalHost localHost;

	@RequestMapping(path = "/topics", method = RequestMethod.GET)
	public String displayAllTopics(Model model,HttpServletRequest request) {
		List<Topic> topics = topicDao.findAll();
		String header = setHeader("all");

		// 修复getId()为null的bug
//		 List<ViewObject> vos = new ArrayList<>();
//		for (Topic topic : topics) {
//			System.out.println(topic.getUser().getId());
//			System.out.println(topic.getUser().getUsername());
//			 ViewObject vo = new ViewObject();
//			 vo.set("topicv", topic);
//			 vo.set("username", topic.getUser().getUsername());
//			 vo.set("userId", topic.getUser().getId());
//			 vos.add(vo);
//		}
//		 model.addAttribute("vos", vos);
		
		model.addAttribute("topics", topics);
		model.addAttribute("header", header);
		model.addAttribute("answerDao", answerDao);
		model.addAttribute("userDao", userDao);
		return "topics";
	}
	
	@RequestMapping(path = "/topics/{category}", method = RequestMethod.GET)
	public String displayTopicsByCategory(@PathVariable String category, Model model) {
		List<Topic> topics = topicDao.findTopicsByCategoryOrderByCreatedDateDesc(category);
		String header = setHeader(category);
		model.addAttribute("topics", topics);
		model.addAttribute("header", header);
		model.addAttribute("answerDao", answerDao);
		return "topics";
	}

	@RequestMapping(path = "/topics/user/{id}", method = RequestMethod.GET)
	public String displayTopicsByUser(@PathVariable String id, Model model) {
		List<Topic> topics = topicDao.findTopicsByUser_IdOrderByCreatedDateDesc(Long.valueOf(id));
		String header = setHeader("user");
		model.addAttribute("topics", topics);
		model.addAttribute("header", header);
		model.addAttribute("answerDao", answerDao);
		return "topics";
	}

	private String setHeader(String category) {
		switch (category) {
		case "se":
			return "Java Standard Edition";
		case "ee":
			return "Java Enterprise Edition";
		case "jpa":
			return "Java Persistence API and Hibernate";
		case "spring":
			return "Spring Framework";
		case "web":
			return "HTML/CSS/JavaScript";
		case "all":
			return "All topics";
		default:
			return "User's topics";
		}
	}
}
