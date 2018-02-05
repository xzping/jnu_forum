package com.xzp.forum.controller;

import java.util.Date;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.security.core.userdetails.UserDetails;

import com.xzp.forum.dao.AnswerDao;
import com.xzp.forum.dao.TopicDao;
import com.xzp.forum.dao.UserDao;
import com.xzp.forum.model.Topic;
import com.xzp.forum.model.User;

/**
 * 个人简介接口
 * 
 * @author xiezhiping
 *
 */
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
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();

		User user = userDao.getUserByUsername(username);
		Long points = userDao.getPoints(user.getId());

		Long numberOfTopics = topicDao.countTopicsByUser_Id(user.getId());
		Long numberOfAnswers = answerDao.countAnswersByUser_Id(user.getId());
		Long numberOfHelped = answerDao.countAnswersByUser_IdAndUseful(user.getId(), true);

		model.addAttribute("user", user);
		model.addAttribute("points", points);
		model.addAttribute("numberOfTopics", numberOfTopics);
		model.addAttribute("numberOfAnswers", numberOfAnswers);
		model.addAttribute("numberOfHelped", numberOfHelped);
		return "profile";
	}

	@RequestMapping(path = "/profile/{id}", method = RequestMethod.GET)
	public String displayProfileById(@PathVariable Long id, Model model) {
		User user = userDao.getUserById(id);
		Long points = userDao.getPoints(user.getId());

		Long numberOfTopics = topicDao.countTopicsByUser_Id(id);
		Long numberOfAnswers = answerDao.countAnswersByUser_Id(id);
		Long numberOfHelped = answerDao.countAnswersByUser_IdAndUseful(id, true);

		model.addAttribute("user", user);
		model.addAttribute("points", points);
		model.addAttribute("numberOfTopics", numberOfTopics);
		model.addAttribute("numberOfAnswers", numberOfAnswers);
		model.addAttribute("numberOfHelped", numberOfHelped);

		return "profile";
	}

	@RequestMapping(path = "/profile", method = RequestMethod.POST)
	public View addTask(@RequestParam("category") String category, @RequestParam("title") String title,
			@RequestParam("content") String content, @RequestParam("code") String code,
			@RequestParam("id_user") String id_user, HttpServletRequest request) {
		Topic topic = new Topic();
		topic.setCategory(category);
		if (Objects.equals(code, "")) {
			topic.setCode(null);
		} else {
			topic.setCode(code);
		}
		topic.setContent(content);
		topic.setTitle(title);
		topic.setCreatedDate(new Date());
		topic.setIdUser(Integer.parseInt(id_user));
		topic.setUser(userDao.getUserById(Long.parseLong(id_user)));

		topicDao.addTopic(topic);
		String contextPath = request.getContextPath();
		return new RedirectView(contextPath + "/profile");
	}
}