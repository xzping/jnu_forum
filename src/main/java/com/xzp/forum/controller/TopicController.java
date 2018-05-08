package com.xzp.forum.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.xzp.forum.dao.AnswerDao;
import com.xzp.forum.dao.MessageDao;
import com.xzp.forum.dao.TopicDao;
import com.xzp.forum.dao.UserDao;
import com.xzp.forum.model.Answer;
import com.xzp.forum.model.Topic;
import com.xzp.forum.model.User;
import com.xzp.forum.service.TopicsService;
import com.xzp.forum.util.HostHolder;

@Controller
public class TopicController {
	@Autowired
	private UserDao userDao;

	@Autowired
	private TopicDao topicDao;

	@Autowired
	private AnswerDao answerDao;
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	HostHolder hostHolder;

	@Autowired
	TopicsService topicsService;
	
	@RequestMapping(path = "/topic/{id}", method = RequestMethod.GET)
	public String displayTopic(@PathVariable String id, Model model) {
		
		User user = hostHolder.getUser();
		Long idUser = user.getId();
		
		Topic topic = topicDao.findTopicById(Long.valueOf(id));
		List<Answer> answers = answerDao.findAnswerByTopic_Id(Long.valueOf(id));
		
		model.addAttribute("user", user);
		model.addAttribute("newMessage", messageDao.countMessageByToId(user.getId()));
		model.addAttribute("topic", topic);
		model.addAttribute("answers", answers);
		model.addAttribute("idUser", idUser);
		model.addAttribute("userDao", userDao);
		return "topic";
	}

	/**
	 * 删除评论或置为有用/没用的评论
	 * 
	 * @param id_topic
	 * @param action
	 * @param id_answer
	 * @param state
	 * @param request
	 * @return
	 */
	@RequestMapping(path = "/topic/{id}", method = RequestMethod.POST)
	public View updateAnswer(@RequestParam String id_topic, @RequestParam String action, @RequestParam String id_answer,
			@RequestParam(required = false) String state, HttpServletRequest request) {
		switch (action) {
		case "useful":
			answerDao.setUsefulForAnswer(!Boolean.valueOf(state), Long.valueOf(id_answer));
			break;
		case "delete":
			answerDao.deleteAnswerById(Long.valueOf(id_answer));
			break;
		}
		String contextPath = request.getContextPath();
		return new RedirectView(contextPath + "/topic/" + id_topic);
	}

	/**
	 * 话题评论接口
	 * 
	 * @param content 评论的内容
	 * @param code 评论附加的代码
	 * @param id_topic 话题的id
	 * @param id_user 该话题的用户的userId
	 * @param request
	 * @return
	 */
	@RequestMapping(path = "/topic", method = RequestMethod.POST)
	public View addAnswer(@RequestParam("content") String content, @RequestParam("code") String code,
			@RequestParam("id_topic") String id_topic, @RequestParam("id_user") String id_user,
			HttpServletRequest request) {
		
		topicsService.addAnswer(content, code, id_topic, id_user);
		
		String contextPath = request.getContextPath();
		return new RedirectView(contextPath + "/topic/" + id_topic);
	}
	
	/**
	 * 解决按分话题时候再点击站内信无法跳转到站内信的页面，此方法主要实现一个跳转
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(path = "/topics/message", method = RequestMethod.GET)
	public View topicsTransform(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		return new RedirectView(contextPath + "/message");
	}
	
	@RequestMapping(path = "/topic/message", method = RequestMethod.GET)
	public View topicTransform(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		return new RedirectView(contextPath + "/message");
	}
}
