package com.xzp.forum.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
	private Long id; //站内信的id
	private Integer fromId;//站内信的发送方
	private Integer toId;//站内容的接收方
	private String content;//站内信的内容
	private Date createdDate;//站内信触发的时间
	private Integer idTopic;//站内信对应的话题
	private int hasRead;//站内信是否已读

	public Integer getIdTopic() {
		return idTopic;
	}

	public void setIdTopic(Integer idTopic) {
		this.idTopic = idTopic;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getFromId() {
		return fromId;
	}

	public void setFromId(Integer fromId) {
		this.fromId = fromId;
	}

	public Integer getToId() {
		return toId;
	}

	public void setToId(Integer toId) {
		this.toId = toId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public int getHasRead() {
		return hasRead;
	}

	public void setHasRead(int hasRead) {
		this.hasRead = hasRead;
	}
	
	public String displayParsedCreatedDate(Date date) {
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return formatter.format(date);
    }
}
