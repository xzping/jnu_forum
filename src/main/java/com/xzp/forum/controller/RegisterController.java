package com.xzp.forum.controller;

import java.util.Date;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.xzp.forum.dao.UserDao;
import com.xzp.forum.model.User;

/**
 * 注册接口
 * 
 * @author xiezhiping
 *
 */
@Controller
public class RegisterController {

	@Autowired
	UserDao userDao;

	@Autowired
	PasswordEncoder passwordEncoder;

	@RequestMapping(path = "/register", method = RequestMethod.GET)
	public String register() {
		return "register";
	}

	@RequestMapping(path = "/register", method = RequestMethod.POST)
	public View register(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("introduction") String introduction, HttpServletRequest request) {
		String contextPath = request.getContextPath();
		User user = new User();
		if (userDao.getUserByUsername(username) == null) {
			user.setUsername(username);
			user.setPassword(password);
			user.setPassword(passwordEncoder.encode(password));
			if (Objects.equals(introduction, "")) {
				user.setIntroduction(null);
			} else {
				user.setIntroduction(introduction);
			}
			user.setCreatedDate(new Date());
			userDao.addUser(user);
			return new RedirectView(contextPath + "/login");
		} else {
			return new RedirectView(contextPath + "/register");
		}
	}
}
