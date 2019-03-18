package cn.appsys.controller.backend;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.Backend_user;
import cn.appsys.service.Backend_user.BackendUserService;
import cn.appsys.tools.Constants;
@Controller("loginUserBack")
@RequestMapping("/manager")
public class LoginUserBack {
	@Resource(name="backendUserService")
	private BackendUserService backendUserService;
	@RequestMapping(value="/BackendUser",method=RequestMethod.POST)
	public String BackendUser(@RequestParam(required=false) String userCode,
			@RequestParam(required=false)String userPassword,HttpSession session){
		Backend_user bu= backendUserService.getBackendUser(userCode, userPassword);
		if(null!=bu){
			session.setAttribute(Constants.USER_SESSION, bu);
			return "backend/main";
		}else{
			session.setAttribute("error", "账号或密码不正确");
			return "backendlogin";
		}
	}
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(){
		return "backendlogin";
	}
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(HttpSession session){
		session.invalidate();
		return "backendlogin";
	}
}
