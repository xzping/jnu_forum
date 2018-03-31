package com.xzp.forum.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.xzp.forum.async.EventModel;
import com.xzp.forum.async.EventProducer;
import com.xzp.forum.async.EventType;
import com.xzp.forum.dao.AnswerDao;
import com.xzp.forum.dao.TopicDao;
import com.xzp.forum.dao.UserDao;
import com.xzp.forum.model.Answer;
import com.xzp.forum.model.Topic;
import com.xzp.forum.model.User;
import com.xzp.forum.util.EntityType;
import com.xzp.forum.util.HostHolder;

@Service
public class TopicsService {

	@Autowired
	private TopicDao topicDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private AnswerDao answerDao;
	
	@Autowired
	private EventProducer eventProducer;
	
	@Autowired
	HostHolder hostHolder;

	public List<Topic> getTopicsByCategory(String category) {
		if (category.equals("all")) {
			return topicDao.findAll();
		} else {
			return topicDao.findTopicsByCategoryOrderByCreatedDateDesc(category);
		}
	}

	public List<Topic> getTopicsByUser(String userId) {
		return topicDao.findTopicsByUser_IdOrderByCreatedDateDesc(Long.valueOf(userId));
	}

	public void addAnswer(String content, String code, String id_topic, String id_user) {
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

		// 触发评论的异步队列
		User user = hostHolder.getUser();
		// 如果评论自己的话题不会触发站内信通知
		if (user.getId() != topicDao.getId_userById(Long.parseLong(id_topic))) {
			EventModel eventModel = new EventModel(EventType.COMMENT);
			eventModel.setCreatedDate(new Date());
			eventModel.setActorId(Integer.parseInt(String.valueOf(user.getId())));
			eventModel.setEntityId(Integer.valueOf(id_topic));
			eventModel.setEntityType(EntityType.ENTITY_COMMENT);
			eventModel.setEntityOwnerId(topicDao.getId_userById(Long.parseLong(id_topic)));
			eventProducer.fireEvent(eventModel);
		}
	}
}
