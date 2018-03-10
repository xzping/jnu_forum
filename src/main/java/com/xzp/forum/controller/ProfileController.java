package com.xzp.forum.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.security.core.userdetails.UserDetails;

import com.xzp.forum.dao.AnswerDao;
import com.xzp.forum.dao.ImageDao;
import com.xzp.forum.dao.MessageDao;
import com.xzp.forum.dao.TopicDao;
import com.xzp.forum.dao.UserDao;
import com.xzp.forum.model.Image;
import com.xzp.forum.model.Topic;
import com.xzp.forum.model.User;
import com.xzp.forum.service.QiniuService;
import com.xzp.forum.util.FileUtil;
import com.xzp.forum.util.ForumUtil;
import com.xzp.forum.util.HostHolder;

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
	private MessageDao messageDao;

	@Autowired
	private AnswerDao answerDao;
	
	@Autowired
	private ImageDao imageDao;
	
	@Autowired
	private HostHolder hostHolder;
	
	@Autowired
	QiniuService qiniuService;

	@RequestMapping(path = "/profile", method = RequestMethod.GET)
	public String displayMyProfile(Model model) {
		User user = hostHolder.getUser();
		Long points = userDao.getPoints(user.getId());

		Long numberOfTopics = topicDao.countTopicsByUser_Id(user.getId());
		Long numberOfAnswers = answerDao.countAnswersByUser_Id(user.getId());
		Long numberOfHelped = answerDao.countAnswersByUser_IdAndUseful(user.getId(), true);
		List<String> myImgs=imageDao.getImgByUserId(user.getId());

		model.addAttribute("localHost", hostHolder.getUser().getUsername());
		model.addAttribute("user", user);
		model.addAttribute("newMessage", messageDao.countMessageByToId(user.getId()));
		model.addAttribute("points", points);
		model.addAttribute("numberOfTopics", numberOfTopics);
		model.addAttribute("numberOfAnswers", numberOfAnswers);
		model.addAttribute("numberOfHelped", numberOfHelped);
		model.addAttribute("myImgs", myImgs);
		model.addAttribute("switch", true);
		return "profile";
	}

	@RequestMapping(path = "/profile/{id}", method = RequestMethod.GET)
	public String displayProfileById(@PathVariable Long id, Model model) {
		User user = userDao.getUserById(id);
		Long points = userDao.getPoints(user.getId());

		Long numberOfTopics = topicDao.countTopicsByUser_Id(id);
		Long numberOfAnswers = answerDao.countAnswersByUser_Id(id);
		Long numberOfHelped = answerDao.countAnswersByUser_IdAndUseful(id, true);
		List<String> myImgs=imageDao.getImgByUserId(user.getId());

		model.addAttribute("localHost", hostHolder.getUser().getUsername());
		model.addAttribute("user", user);
		model.addAttribute("newMessage", messageDao.countMessageByToId(hostHolder.getUser().getId()));
		model.addAttribute("points", points);
		model.addAttribute("numberOfTopics", numberOfTopics);
		model.addAttribute("numberOfAnswers", numberOfAnswers);
		model.addAttribute("numberOfHelped", numberOfHelped);
		model.addAttribute("myImgs", myImgs);
		model.addAttribute("switch", false);

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
	
	@RequestMapping(path="/upload",method=RequestMethod.POST)
	public String uploadImage(@RequestParam("file") MultipartFile file,HttpServletRequest request, Model model) {
		try {
			String fileUrl=qiniuService.saveImage(file);
//			if(fileUrl == null) {
//				return ForumUtil.getJSONString(1, "上传图片失败");
//			}
			Image image=new Image();
			image.setImgUrl(fileUrl);
			image.setIdUser(hostHolder.getUser().getId());
			imageDao.addImg(image);
			
			User user = hostHolder.getUser();
			Long points = userDao.getPoints(user.getId());
			Long numberOfTopics = topicDao.countTopicsByUser_Id(user.getId());
			Long numberOfAnswers = answerDao.countAnswersByUser_Id(user.getId());
			Long numberOfHelped = answerDao.countAnswersByUser_IdAndUseful(user.getId(), true);
			List<String> myImgs=imageDao.getImgByUserId(user.getId());
			model.addAttribute("localHost", user.getUsername());
			model.addAttribute("user", user);
			model.addAttribute("newMessage", messageDao.countMessageByToId(user.getId()));
			model.addAttribute("points", points);
			model.addAttribute("numberOfTopics", numberOfTopics);
			model.addAttribute("numberOfAnswers", numberOfAnswers);
			model.addAttribute("numberOfHelped", numberOfHelped);
			model.addAttribute("myImgs", myImgs);
			model.addAttribute("switch", true);
			return "profile";
		} catch (IOException e) {
			e.printStackTrace();
			return "profile";
		}
	}
	
	@RequestMapping(path = "/profile/message", method = RequestMethod.GET)
	public View topicsTransform(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		return new RedirectView(contextPath + "/message");
	}
}
