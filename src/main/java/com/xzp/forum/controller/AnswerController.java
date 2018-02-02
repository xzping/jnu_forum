package com.xzp.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xzp.forum.dao.AnswerDao;
import com.xzp.forum.model.Answer;

@Controller
public class AnswerController {
	@Autowired
	private AnswerDao answerDao;
	
	@RequestMapping(path="/answers/{id}",method=RequestMethod.GET)
	public String displayAnswersByUser(@PathVariable String id, Model model) {
		List<Answer> answers=answerDao.findAnswerByUser_IdOrderByCreatedDateDesc(Long.parseLong(id));
		model.addAttribute("answers", answers);
		return "answers";
	}
	
	@RequestMapping(path="/answers/useful/{id}",method=RequestMethod.GET)
	public String displayUsefulAnswersByUser(@PathVariable String id, Model model) {
		List<Answer> answers=answerDao.findAnswerByUser_IdAndUsefulOrderByCreatedDateDesc(Long.parseLong(id), true);
		model.addAttribute("answers", answers);
		return "answers";
	}
}
