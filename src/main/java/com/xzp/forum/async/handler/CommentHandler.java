package com.xzp.forum.async.handler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xzp.forum.async.EventHandler;
import com.xzp.forum.async.EventModel;
import com.xzp.forum.async.EventType;
import com.xzp.forum.dao.AnswerDao;
import com.xzp.forum.dao.MessageDao;
import com.xzp.forum.dao.UserDao;
import com.xzp.forum.model.Message;
import com.xzp.forum.model.User;
import com.xzp.forum.util.HostHolder;

/**
 * 评论的处理器
 * 
 * @author xiezhiping
 *
 */
@Component
public class CommentHandler implements EventHandler{

	@Autowired
	UserDao userDao;
	
	@Autowired
	MessageDao messageDao;
	
	@Autowired
	HostHolder hostHolder;
	
	@Autowired
	AnswerDao answerDao;
	
	@Override
	public void doHandle(EventModel model) {
		Message message=new Message();
		message.setFromId(model.getActorId());
		message.setToId(model.getEntityOwnerId());
		User user=userDao.getUserById((long) model.getActorId());
		message.setContent("用户"+user.getUsername()+"评论你的话题！");
		message.setCreatedDate(model.getCreatedDate());
		message.setIdTopic(model.getEntityId());
		message.setHasRead(0);
		messageDao.addMessage(message);
	}

	@Override
	public List<EventType> getSupportEventTypes() {
		return Arrays.asList(EventType.COMMENT);
	}

}
