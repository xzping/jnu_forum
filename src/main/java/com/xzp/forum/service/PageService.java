package com.xzp.forum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.xzp.forum.dao.TopicDao;
import com.xzp.forum.model.PageBean;
import com.xzp.forum.model.Topic;

@Service
public class PageService {
	
	@Autowired
	private TopicDao topicDao;
	
	public PageBean<Topic> findItemByPage(int currentPage, int pageSize) {
		int countNums = topicDao.findAll().size(); // 总记录数
		// 设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
		PageHelper.startPage(currentPage, pageSize);
		List<Topic> allTopic = topicDao.findAll(); // 全部商品
		PageBean<Topic> pageData = new PageBean<>(currentPage, pageSize, countNums);
		pageData.setItems(allTopic);
		pageData.setCurrentPage(currentPage);
		pageData.setPageSize(pageSize);
		pageData.setTotalPage((countNums/pageSize));
		pageData.setIsMore(currentPage<(countNums/pageSize)?1:0);//1表示有下一页，0表示没有下一页
		return pageData;
	}
	
	public PageBean<Topic> findItemByPage(String category, int currentPage, int pageSize) {
		int countNums = topicDao.findTopicsByCategoryOrderByCreatedDateDesc(category).size(); // 总记录数
		// 设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
		PageHelper.startPage(currentPage, pageSize);
		List<Topic> allTopic=null;
		if(category.equals("all")) {
			allTopic=topicDao.findAll();
		}else{
			allTopic = topicDao.findTopicsByCategoryOrderByCreatedDateDesc(category); // 全部商品
		}
		PageBean<Topic> pageData = new PageBean<>(currentPage, pageSize, countNums);
		pageData.setItems(allTopic);
		pageData.setCurrentPage(currentPage);
		pageData.setPageSize(pageSize);
		pageData.setTotalPage((countNums/pageSize));
		pageData.setIsMore(currentPage<(countNums/pageSize)?1:0);//1表示有下一页，0表示没有下一页
		return pageData;
	}
}
