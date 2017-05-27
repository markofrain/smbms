package pers.cgq.smbms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pers.cgq.smbms.pojo.User;
import pers.cgq.smbms.service.UserService;
import pers.cgq.smbms.tools.Constants;
/**
 * 登录登出信息
 * @author 光奇
 *
 */
@Controller
public class LoginController {
	

	@Autowired
	private UserService userService;
	
	
	
	/**
	 * 跳转到系统登录页
	 * 
	 * @return
	 */
	@RequestMapping("/login.html")
	public String login(Model model) {
		System.out.println("UserController Welcome ===========>SMBMS");
		return "login";
	}

	/**
	 * 实现登录的方法
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doLogin.html", method = RequestMethod.POST)
	public String doLogin(@RequestParam("usercode") String usercode,
							@RequestParam("userpassword") String userpassword,
							HttpServletRequest request) {
		
		//验证登陆
		User user=userService.login(usercode, userpassword);
		
		if(null!=user){//验证登陆成功
			request.getSession().setAttribute(Constants.USER_SESSION, user);//为session中的user设置值
			//重定向到用户的主页
			return "redirect:/sys/main.html";//sys/main.html，用于拦截
		}else{
			request.setAttribute("error", "用户名或密码不正确");
			return "login";//返回到登录页面
		}
	}
	
	/**
	 * 登出
	 * @return
	 */
	@RequestMapping("/logout.html")
	public String loginOut(HttpSession session){
		session.removeAttribute(Constants.USER_SESSION);//用户登出，删除在session中的用户登录信息
		return "redirect:login.html";
	}
	/**
	 * 进入主页
	 * @return
	 */
	@RequestMapping("/sys/main.html")
	public String main(){
		return "frame";
	}
	
	


}
