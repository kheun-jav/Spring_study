package controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.User;
import service.UserService;

@Controller
@RequestMapping("admin")
public class AdminController {
	@Autowired
	UserService service;

	@RequestMapping("list")
	public ModelAndView adminCheckList(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User loginUser = (User)session.getAttribute("loginUser");
			List<User> userList = service.list();
			mav.addObject("list", userList);
		return mav;
	}
}
