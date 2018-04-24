package com.xzp.forum.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import com.xzp.forum.dao.AnswerDao;
import com.xzp.forum.dao.MessageDao;
import com.xzp.forum.dao.TopicDao;
import com.xzp.forum.dao.UserDao;
import com.xzp.forum.model.PageBean;
import com.xzp.forum.model.Topic;
import com.xzp.forum.model.User;
import com.xzp.forum.service.PageService;
import com.xzp.forum.service.TopicsService;
import com.xzp.forum.util.HostHolder;

@Controller
public class TopicsController {
	@Autowired
	private UserDao userDao;

	@Autowired
	private AnswerDao answerDao;
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private HostHolder localHost;
	
	@Autowired
	private TopicsService topicsService;
	
	@Autowired
	private PageService pageService;
	
	/**
	 * 分页处理
	 * @param category
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping(path="/topics/{category}/{currentPage}", method=RequestMethod.GET)
	public String displayTopicPage(@PathVariable String category, @PathVariable int currentPage, Model model) {
		PageBean<Topic> pageTopic=pageService.findItemByPage(category, currentPage, 10);
		List<Topic> pageList=pageTopic.getItems();
		String header = setHeader(category);
		int topicsTotalNum=topicsService.getTopicsByCategory(category).size();
		
		User user=localHost.getUser();
		model.addAttribute("user", user);
		model.addAttribute("newMessage", messageDao.countMessageByToId(user.getId()));
		model.addAttribute("topics", pageList);
		model.addAttribute("topicsTotalNum", topicsTotalNum);
		model.addAttribute("header", header);
		model.addAttribute("answerDao", answerDao);
		model.addAttribute("userDao", userDao);
		model.addAttribute("currentPage", pageTopic.getCurrentPage());
		model.addAttribute("totalPage", pageTopic.getTotalPage());
		model.addAttribute("hasNext", pageTopic.getIsMore());
		return "topics";
	}
	
//	@RequestMapping(path = "/topics/{category}", method = RequestMethod.GET)
//	public String displayTopicsByCategory(@PathVariable String category, Model model) {
//		List<Topic> topics = topicsService.getTopicsByCategory(category);
//		String header = setHeader(category);
//		
//		User user=localHost.getUser();
//		model.addAttribute("user", user);
//		model.addAttribute("newMessage", messageDao.countMessageByToId(user.getId()));
//		model.addAttribute("topics", topics);
//		model.addAttribute("header", header);
//		model.addAttribute("answerDao", answerDao);
//		model.addAttribute("userDao", userDao);
//		return "topics";
//	}

	@RequestMapping(path = "/topics/user/{id}", method = RequestMethod.GET)
	public String displayTopicsByUser(@PathVariable String id, Model model) {
		List<Topic> topics = topicsService.getTopicsByUser(id);
		String header = setHeader("user");
		
		User user=localHost.getUser();
		model.addAttribute("user", user);
		model.addAttribute("newMessage", messageDao.countMessageByToId(user.getId()));
		model.addAttribute("topics", topics);
		model.addAttribute("header", header);
		model.addAttribute("answerDao", answerDao);
		model.addAttribute("userDao", userDao);
		
		return "topics";
	}

	private String setHeader(String category) {
		switch (category) {
			case "se":
				return "Java Standard Edition";
			case "ee":
				return "Java Enterprise Edition";
			case "mbs":
				return "MyBatis";
			case "spring":
				return "Spring Framework";
			case "web":
				return "HTML/CSS/JavaScript";
			case "other":
				return "其他";
			case "all":
				return "All topics";
			default:
				return "User's topics";
		}
	}
	
	/**
	 * 页面跳转bug
	 * @param request
	 * @return
	 */
//	@RequestMapping(path = "/topics/user/message", method = RequestMethod.GET)
//	public View topicTransformUser(HttpServletRequest request) {
//		String contextPath = request.getContextPath();
//		return new RedirectView(contextPath + "/message");
//	}
//	
//	@RequestMapping(path = "/topics/other/message", method = RequestMethod.GET)
//	public View topicTransformOther(HttpServletRequest request) {
//		String contextPath = request.getContextPath();
//		return new RedirectView(contextPath + "/message");
//	}
//	
//	@RequestMapping(path = "/topics/web/message", method = RequestMethod.GET)
//	public View topicTransformWeb(HttpServletRequest request) {
//		String contextPath = request.getContextPath();
//		return new RedirectView(contextPath + "/message");
//	}
}
