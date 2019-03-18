package cn.appsys.controller.developer;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import cn.appsys.pojo.Dev_user;
import cn.appsys.service.devUser.DevUserService;
import cn.appsys.tools.Constants;

@Controller("devLoginController")
@RequestMapping("/dev")
public class DevLoginController {
	@Resource(name="devUserService")
	private DevUserService devUserService;
	@RequestMapping(value="/login")
	public String login(){
		return "devlogin";
	}
	@RequestMapping(value="/dologin")
	public String dologin(@RequestParam()String devCode,@RequestParam()String devPassword,
			HttpSession session,Model model){
		Dev_user devU=devUserService.getDevUserId(devCode, devPassword);
		if(devU!=null){
			session.setAttribute(Constants.DEV_USER_SESSION, devU);
			return "developer/main";
		}else{
			session.setAttribute("error", "账号或密码不正确");
			return "devlogin";
		}
	}
	@RequestMapping(value="/logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "devlogin";
	}
}
