package com.xzp.forum.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xzp.forum.dao.MessageDao;
import com.xzp.forum.dao.UserDao;
import com.xzp.forum.model.Message;
import com.xzp.forum.model.User;
import com.xzp.forum.util.HostHolder;

/**
 * 站内信的实现：通过异步队列来实现，当发起话题的发起者认为你的评论有用时、和评论你的帖子时会触发异步队列
 * 站内信的内容主要为：XXX评论了你的话题、XXX认为你的评论很有用！...
 * 
 * @author xiezhiping
 *
 */
@Controller
public class MessageController {
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private HostHolder hostHolder;
	
	@RequestMapping(path="/message",method= RequestMethod.GET)
	public String message(Model model) {
		User user=hostHolder.getUser();
		Long toId=user.getId();
		List<Message> messages=messageDao.getMessageByToId(toId);
//		System.out.println(messages.size());
		model.addAttribute("messages", messages);
		model.addAttribute("userDao", userDao);
		return "message";
	}
}
