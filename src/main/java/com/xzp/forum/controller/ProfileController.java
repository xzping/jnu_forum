package com.xzp.forum.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.alibaba.fastjson.JSON;
import com.xzp.forum.dao.AnswerDao;
import com.xzp.forum.dao.ImageDao;
import com.xzp.forum.dao.MessageDao;
import com.xzp.forum.dao.TopicDao;
import com.xzp.forum.dao.UserDao;
import com.xzp.forum.model.Image;
import com.xzp.forum.model.Topic;
import com.xzp.forum.model.User;
import com.xzp.forum.service.FollowService;
import com.xzp.forum.service.QiniuService;
import com.xzp.forum.util.HostHolder;

/**
 * 个人简介接口
 * 
 * @author xiezhiping
 *
 */
@Controller
public class ProfileController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
	
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
	private FollowService followService;
	
	@Autowired
	QiniuService qiniuService;
	
	@RequestMapping(path = "/profile", method = RequestMethod.GET)
	public String displayMyProfile(Model model) {
		User user = hostHolder.getUser();
		logger.warn("[displayMyProfile] display profile, username:{}",user.getUsername());
		Long points = userDao.getPoints(user.getId());
//		jedisAdapter.zadd(rankKey, points, user.getUsername());
		
		Long numberOfTopics = topicDao.countTopicsByUser_Id(user.getId());
		Long numberOfAnswers = answerDao.countAnswersByUser_Id(user.getId());
		Long numberOfHelped = answerDao.countAnswersByUser_IdAndUseful(user.getId(), true);
		List<String> myImgs=imageDao.getImgByUserId(user.getId());
		List<String> myAllImgs=imageDao.getAllImgByUserId(user.getId());
		
		model.addAttribute("user", user);
		model.addAttribute("newMessage", messageDao.countMessageByToId(user.getId()));
		model.addAttribute("points", points);
		model.addAttribute("numberOfTopics", numberOfTopics);
		model.addAttribute("numberOfAnswers", numberOfAnswers);
		model.addAttribute("numberOfHelped", numberOfHelped);
		model.addAttribute("myImgs", myImgs);
		model.addAttribute("isHasMoreImg", myAllImgs.size()>myImgs.size());
		model.addAttribute("switch", true);
		return "profile";
	}

	@RequestMapping(path = "/profile/{id}", method = RequestMethod.GET)
	public String displayProfileById(@PathVariable Long id, Model model) {
		User user = userDao.getUserById(id);
		logger.warn("[displayMyProfile] display profile, username:{}",user.getUsername());
		Long points = userDao.getPoints(user.getId());
		Long numberOfTopics = topicDao.countTopicsByUser_Id(id);
		Long numberOfAnswers = answerDao.countAnswersByUser_Id(id);
		Long numberOfHelped = answerDao.countAnswersByUser_IdAndUseful(id, true);
		List<String> myImgs=imageDao.getImgByUserId(user.getId());
		List<String> myAllImgs=imageDao.getAllImgByUserId(user.getId());
		User otherUser=hostHolder.getUser();
		boolean isFollowed = followService.isFollowed(otherUser.getId(), id);
		
		model.addAttribute("user", otherUser);
		model.addAttribute("otherUser", user);
		model.addAttribute("newMessage", messageDao.countMessageByToId(hostHolder.getUser().getId()));
		model.addAttribute("points", points);
		model.addAttribute("numberOfTopics", numberOfTopics);
		model.addAttribute("numberOfAnswers", numberOfAnswers);
		model.addAttribute("numberOfHelped", numberOfHelped);
		model.addAttribute("myImgs", myImgs);
		model.addAttribute("isHasMoreImg", myAllImgs.size()>myImgs.size());
		model.addAttribute("switch", (user.getId()==otherUser.getId())?true:false);
		model.addAttribute("followNums", followService.getFollowNum(user.getUsername(), user.getId()));
		model.addAttribute("commonFansNum", followService.getCommonFans(user.getId(), otherUser.getId()).size());
		model.addAttribute("isFollowed", isFollowed);
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
	
	@RequestMapping(path="/imageWall/{id}",method=RequestMethod.GET)
	public String imageWall(@PathVariable Long id, Model model) {
		User user = userDao.getUserById(id);
		List<String> myAllImgs=imageDao.getAllImgByUserId(user.getId());
		
		model.addAttribute("user", user);
		model.addAttribute("myImgs", myAllImgs);
		model.addAttribute("newMessage", messageDao.countMessageByToId(user.getId()));
		return "imageWall";
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
			List<String> myAllImgs=imageDao.getAllImgByUserId(user.getId());
			model.addAttribute("user", user);
			model.addAttribute("otherUser", user);
			model.addAttribute("newMessage", messageDao.countMessageByToId(user.getId()));
			model.addAttribute("points", points);
			model.addAttribute("numberOfTopics", numberOfTopics);
			model.addAttribute("numberOfAnswers", numberOfAnswers);
			model.addAttribute("numberOfHelped", numberOfHelped);
			model.addAttribute("myImgs", myImgs);
			model.addAttribute("isHasMoreImg", myAllImgs.size()>myImgs.size());
			model.addAttribute("switch", true);
			return "profile";
		} catch (IOException e) {
			e.printStackTrace();
			return "profile";
		}
	}
	
	/**
	 * userId用户关注otherUserId用户
	 * @param userId
	 * @param otherUserId
	 * @return
	 */
	@RequestMapping(path="/follow/{userId}_{otherUserId}",method=RequestMethod.POST)
	@ResponseBody
	public String addFollow(@PathVariable Long userId, @PathVariable Long otherUserId) {
		User user = userDao.getUserById(userId);
		User otherUser = userDao.getUserById(otherUserId);
		logger.warn("[addFollow] addFollow . {} follows {}", user.getUsername(), otherUser.getUsername());
		followService.addFollow(otherUser.getUsername(), userId, otherUserId);
		return "follow success!";
	}
	
	@RequestMapping(path="/fans/{userId}",method=RequestMethod.GET)
	@ResponseBody
	public String displayFans(@PathVariable Long userId){
		Set<User> followFans = followService.getFollowUser(userId);
		Map<String, Long> followFansMap = new HashMap<String, Long>();
		for (User fan : followFans) {
			followFansMap.put(fan.getUsername(), fan.getId());
		}
		return JSON.toJSONString(followFansMap);
	}
	
	@RequestMapping(path="/commonfans/{userId}_{otherUserId}",method=RequestMethod.GET)
	@ResponseBody
	public String displayCommonFans(@PathVariable Long userId, @PathVariable Long otherUserId) {
		Set<User> commonFans = followService.getCommonFans(userId, otherUserId);
		logger.warn("[displayCommonFans] commonFans:{}",commonFans);
		Map<String, Long> commonFansMap = new HashMap<>();
		for (User commonFan : commonFans) {
			commonFansMap.put(commonFan.getUsername(), commonFan.getId());
		}
		return JSON.toJSONString(commonFansMap);
	}
	
	@RequestMapping(path = "/profile/message", method = RequestMethod.GET)
	public View topicsTransform(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		return new RedirectView(contextPath + "/message");
	}
	
	@RequestMapping(path = "/imageWall/message", method = RequestMethod.GET)
	public View messageTransform(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		return new RedirectView(contextPath + "/message");
	}
}
