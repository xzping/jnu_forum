package com.xzp.forum.controller;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.xzp.forum.async.EventModel;
import com.xzp.forum.async.EventProducer;
import com.xzp.forum.async.EventType;
import com.xzp.forum.dao.AnswerDao;
import com.xzp.forum.dao.MessageDao;
import com.xzp.forum.dao.TopicDao;
import com.xzp.forum.dao.UserDao;
import com.xzp.forum.model.Answer;
import com.xzp.forum.model.Topic;
import com.xzp.forum.model.User;
import com.xzp.forum.util.EntityType;
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
	private EventProducer eventProducer;
	
	@Autowired
	HostHolder hostHolder;

	@RequestMapping(path = "/topic/{id}", method = RequestMethod.GET)
	public String displayTopic(@PathVariable String id, Model model) {
		
		//通过UserDetails可以得到username等登录信息！！！
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();
		Long idUser = userDao.getUserByUsername(username).getId();
		
		Topic topic = topicDao.findTopicById(Long.valueOf(id));
		List<Answer> answers = answerDao.findAnswerByTopic_Id(Long.valueOf(id));

		model.addAttribute("user", hostHolder.getUser());
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
	 * 删除话题
	 * @param id_topic
	 * @param action
	 * @param request
	 * @return
	 */
	@RequestMapping(path = "/topicC/{id}", method = RequestMethod.POST)
	public View updateTopic(@RequestParam String id_topic, @RequestParam String action,
			HttpServletRequest request) {
		
		List<Answer> answers = answerDao.findAnswerByTopic_Id(Long.valueOf(id_topic));
		switch (action) {
		case "delete":
			//如果当前话题的评论数不为0时，删除当前话题会出现数据库的主外键关联的删除问题，即无法删除当前字段，因此必须先把当前话题的评论删除才可以删除话题
			if(answers.size()!=0) {
				answerDao.deleteAnswerByTopic_id(Long.parseLong(id_topic));
			}
			topicDao.deleteTopicById(Long.parseLong(id_topic));
			//把该话题的评论时候引起的站内信通知也对应删除掉，避免出现话题的查找的bug
			messageDao.deleteMessageByTopicId(Long.parseLong(id_topic));
			break;
		}
		String contextPath = request.getContextPath();
		return new RedirectView(contextPath + "/topics");
	}

	@RequestMapping(path = "/topic", method = RequestMethod.POST)
	public View addAnswer(@RequestParam("content") String content, @RequestParam("code") String code,
			@RequestParam("id_topic") String id_topic, @RequestParam("id_user") String id_user,
			HttpServletRequest request) {
		Answer answer = new Answer();
		answer.setContent(content);
		if (Objects.equals(code, "")) {
			answer.setCode(null);
		} else {
			answer.setCode(code);
		}
		answer.setCreatedDate(new Date());
		answer.setUseful(false);
		answer.setTopic(topicDao.findTopicById(Long.valueOf(id_topic)));
		answer.setUser(userDao.getUserById(Long.parseLong(id_user)));
		answer.setIdTopic(Integer.parseInt(id_topic));
		answer.setIdUser(Integer.parseInt(id_user));

		answerDao.addAnswer(answer);
		
		//触发评论的异步队列
		User user=hostHolder.getUser();
		EventModel eventModel=new EventModel(EventType.COMMENT);
		eventModel.setCreatedDate(new Date());
		eventModel.setActorId(Integer.parseInt(String.valueOf(user.getId())));
		eventModel.setEntityId(Integer.valueOf(id_topic));
		eventModel.setEntityType(EntityType.ENTITY_COMMENT);
		eventModel.setEntityOwnerId(topicDao.getId_userById(Long.parseLong(id_topic)));
		eventProducer.fireEvent(eventModel);
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
