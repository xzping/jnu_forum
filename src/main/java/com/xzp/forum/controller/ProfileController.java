package com.xzp.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.core.userdetails.UserDetails;

import com.xzp.forum.dao.AnswerDao;
import com.xzp.forum.dao.TopicDao;
import com.xzp.forum.dao.UserDao;
import com.xzp.forum.model.User;

@Controller
public class ProfileController {
	@Autowired
	private UserDao userDao;

	@Autowired
	private TopicDao topicDao;

	@Autowired
	private AnswerDao answerDao;

	@RequestMapping(path = "/profile", method = RequestMethod.GET)
	public String displayMyProfile(Model model) {
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username=((UserDetails) principal).getUsername();
		
		User user=userDao.getUserByUsername(username);
		Long points=userDao.getPoints(user.getId());
		
		Long numberOfTopics=topicDao.countTopicsByUser_Id(user.getId());
		Long numberOfAnswers=answerDao.countAnswersByUser_Id(user.getId());
		Long numberOfHelped=answerDao.countAnswersByUser_IdAndUseful(user.getId(), true);
		
		model.addAttribute("user",user);
		model.addAttribute("points", points);
		model.addAttribute("numberOfTopics", numberOfTopics);
        model.addAttribute("numberOfAnswers", numberOfAnswers);
        model.addAttribute("numberOfHelped", numberOfHelped);
		return "profile";
	}
}
