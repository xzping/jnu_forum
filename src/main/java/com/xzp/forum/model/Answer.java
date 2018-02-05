package com.xzp.forum.model;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * answer的model实体层
 * 
 * @author xiezhiping
 *
 */
public class Answer {
	private Long id;
	private String content;
	private boolean useful;
	private Date createdDate;
	private String code;
	private Integer idTopic;
	private Integer idUser;

	private User user;
	private Topic topic;

	public Integer getIdTopic() {
		return idTopic;
	}

	public void setIdTopic(Integer idTopic) {
		this.idTopic = idTopic;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isUseful() {
		return useful;
	}

	public void setUseful(boolean useful) {
		this.useful = useful;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

}
