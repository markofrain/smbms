package pers.cgq.smbms.controller;

import java.io.File;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * 用户管理
 * @author 光奇
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

import pers.cgq.smbms.pojo.Role;
import pers.cgq.smbms.pojo.SupportUser;
import pers.cgq.smbms.pojo.User;
import pers.cgq.smbms.service.RoleService;
import pers.cgq.smbms.service.UserService;
import pers.cgq.smbms.tools.Constants;
import pers.cgq.smbms.tools.PageSupport;
@RequestMapping("/user")
@Controller
public class UserController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	/**
	 * 
	 * @param currentPageNo 当前页码
	 * @param totalPageCount 总页数
	 * @param totalCount 总记录数
	 * @param queryname 查询的名称,可为null
	 * @param queryUserRole 查询的用户角色编号，可为null
	 * @param model 返回到视图的模型数据
	 * @return 视图页面
	 */
	@RequestMapping("/list")
	public String findUserList(@RequestParam(value="pageIndex",required=false)String currentPageNo,
								String totalPageCount,
								String totalCount,
								@RequestParam(value="queryname",required=false)String queryname,
								@RequestParam(value="queryUserRole",required=false)String queryUserRole,
								Model model){
		int pageNo=1;
		int total=0;
		//进入列表页面
		//1.分页显示
		//判断当前页码是否为空
		if(null!=currentPageNo){
			if(Integer.valueOf(currentPageNo)<=0){//小于
				pageNo=1;
			}else{
				pageNo=Integer.valueOf(currentPageNo);
			}
			
		}
		//获得总记录数，查询条件，用户名，用户角色编号
		if(null==queryUserRole){
			total=userService.getTotalCount(queryname,0);//总记录数
		}else{
			total=userService.getTotalCount(queryname,Integer.valueOf(queryUserRole));//总记录数
		}
		
		
		PageSupport ps=new PageSupport();
		ps.setPageSize(Constants.pageSize);//设置页面大小
		ps.setCurrentPageNo(pageNo);//设置当前页
		ps.setTotalCount(total);//设置总记录数
		int totalpage=ps.getTotalPageCount();//获得总页数
		if(pageNo>=totalpage){//当前页数>=总页数
			pageNo=totalpage;
		}else if(pageNo<=0){//当前页数<=0
			pageNo=1;
		}
		//定义角色编号，不为空则转换为int
		int userRole=0;
		if(null!=queryUserRole){
			userRole=Integer.valueOf(queryUserRole);
		}
		List<SupportUser> userList=userService.getPage(pageNo, ps.getPageSize(),queryname,userRole,0);
		
		//存储每页的信息集合，当前请求的分页参数,角色集合
		model.addAttribute("userList", userList);
		model.addAttribute("page", ps);
		model.addAttribute("roleList", roleService.findAllRole());
		model.addAttribute("queryname",queryname);
		model.addAttribute("queryUserRole", queryUserRole);
		return "userlist";
	}
	/**
	 * 转到添加用户页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/adduser.html")
	public String UserAddPage(Model model){
		model.addAttribute("user", new User());
		return "useradd";
	}
	
	/**
	 * 获得角色列表返回到用户添加页面
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getrolelist")
	public Object getRoleListGoUserAddPage(){
		List<Role> roleList=roleService.findAllRole();
		
		return JSON.toJSON(roleList);
	}
	
	/**
	 * 注册时失焦，ajax检查用户名是否重复
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkusercode")
	public Object checkUserCode(@RequestParam(value="userCode")String userCode){
		User user=new User();
		if(userCode==""){//如果是“”值，直接返回错误
			user.setUsercode("exist");
			return JSON.toJSON(user);
		}
		int count=userService.checkUserCode(userCode);
		
		if(count==0){//不重复,可注册
			user.setUsercode("ok");
		}else{
			user.setUsercode("exist");
		}
		
		return JSON.toJSON(user);
	}
	/**
	 * 执行添加操作后
	 * 要转向那个页面
	 * @return
	 */
	@RequestMapping("/goadduser.html")
	public String goAddUser(@Valid User user,BindingResult result,
							@RequestParam("userRole")String userRole,
							Model model,
							HttpServletRequest request,
							@RequestParam(value="attachs",required=false)MultipartFile[] files){
		User addU=user;
		String[] paths=null;
		System.out.println(files.length);
	//	if(files.length!=0){//文件不为空才添加图片
			try {
				paths=addPicture(request,files);//调用方法，获得两个路径
				if(null!=paths){//不为空，执行
					
						if(paths[0]!=null&&!paths[0].equals("")){//如果不为空，不为""则传到user,否则，传数据库里就是空
							addU.setIdpicpath(paths[0]);
						}
						if(paths[1]!=null&&!paths[1].equals("")){
							addU.setWorkpicpath(paths[1]);
						}
					
				}else{
					model.addAttribute("user", user);//回显
					return "useradd";//返回添加用户页面
				}
				
				System.out.println("-----------------------------"+paths[0]);
				System.out.println("-----------------------------"+paths[1]);
			} catch (Exception e) {//出现错误，添加图片失败
				System.out.println("=*=*=*=*=*=*=**=*=*=*=*=*=*=*==*保存图片出现异常");
				File file=null;
				//删除文件
				if(paths[0]!=null&&!paths[0].equals("")){//不为空，有值
					file=new File(paths[0]);
					file.delete();
				}else if(paths[1]!=null&&!paths[1].equals("")){
					file=new File(paths[0]);
					file.delete();
				}
				model.addAttribute("user", user);//回显
				return "useradd";//返回添加用户页面
			}
	//	}
		/*//如果文件不为空，
		if(!file.isEmpty()){
			if(picPath.equals("")){//但是文件路径依然是""那么依然失败
				model.addAttribute("user", user);//回显
				return "useradd";//返回添加用户页面
			}else{//文件不为空，路径也为不为"",则有路径
				//输出文件路径，将路径添加到user中
				System.out.println("文件路径"+picPath);
				addU.setIdpicpath(picPath);
			}
		}*/
		if(null!=userRole){//有值，转换为Integer
			addU.setUserrole(Integer.valueOf(userRole));
		}
		boolean flag=userService.addUser(addU,paths);
		if(flag){//成功
			return "redirect:list";
		}else{//失败，添加信息失败，那么之前的文件也应不存在
			//在业务层操作删除
			model.addAttribute("user", user);//回显
			return "useradd";
		}
		
	}
	
	/**
	 * 保存图片
	 * @return 图片名
	 * @throws Exception 
	 */
	public String[] addPicture(HttpServletRequest request,MultipartFile[] files) throws Exception{
		String idpicPath="";//id文件路径
		String workpicPath="";//工作证
		String errorInfo="";//错误
		String[] str=new String[10];//返回数组，装着两个路径
		for (int i = 0; i < files.length; i++) {//循环遍历
			MultipartFile file=files[i];
			if(!file.isEmpty()){
				if(i==0){
					errorInfo="uploadFileError";
				}else if(i==1){
					errorInfo="uploadWpError";
				}
				//要上传的位置,统一是这个位置
				String path=request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
				//获得文件名
				String oldFileName=file.getOriginalFilename();
				//获得后缀名
				String subbix=FilenameUtils.getExtension(oldFileName);
				int fileSize=512000;//设置上传大小
				if(file.getSize()>fileSize){
					request.setAttribute(errorInfo, "上传文件不得超过500KB");
					return null;
					//throw new Exception("上传文件超过500KB");
				}else if(subbix.equalsIgnoreCase("jpg")||
						subbix.equalsIgnoreCase("png")||
						subbix.equalsIgnoreCase("jpeg")||
						subbix.equalsIgnoreCase("pneg")){
					/*开始重命名图片*/
					//获得一个新的文件名
					String fileName=System.currentTimeMillis()+RandomUtils.nextInt(10, 9999)+"_person.jpg";
					File targetFile=new File(path,fileName);
					if(!targetFile.exists()){
						targetFile.mkdirs();
					}
					//保存
					file.transferTo(targetFile);
					//获得新文件名
					if(i==0){
						idpicPath=fileName;
						str[0]=idpicPath;
					}else if(i==1){
						workpicPath=fileName;
						str[1]=workpicPath;
					}
				}else{
					request.setAttribute(errorInfo, "上传图片格式不正确");
					return null;
					//throw new Exception("上传图片格式不正确");
				}
			}
		}

		return str;
	}
	/**
	 * 删除用户
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public String deleteUser(@RequestParam("uid")String userId){
		//先删除照片附件，在删除用户信息
		User user=userService.getUserById(Integer.valueOf(userId));
		
		if(user==null){//不存在
			return "notexist";
		}else{ //用户存在，判断文件存不存在。进行操作
			return userService.delUserAgoDelFile(user.getIdpicpath(), user.getWorkpicpath(), Integer.parseInt(userId));
		}
		
	}
	
	
	/**
	 * 去更改页面，将当前角色信息传递到页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/update/{uid}")
	public String goupdatePage(Model model,@PathVariable("uid")String id){
		List<SupportUser> user=userService.getPage(1, 5, null, 0, Integer.valueOf(id));
		model.addAttribute("user", user.get(0));
		return "usermodify";
	}
	
	
	/**
	 * 执行修改操作
	 * @return
	 */
	@RequestMapping("/update.html")
	public String update(User user,Model model){
		boolean flag=userService.updateUser(user);
		if(flag){
			return "redirect:list";//成功，显示列表
		}else{
			model.addAttribute("user", user);//失败重新回到修改页
			return "usermodify";
		}
	}
	/**
	 * json检查旧密码是否正确
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkoldpwd")
	public String checkOldPwd(@RequestParam("oldpassword")String oldPwd,HttpSession session){
		if(null!=oldPwd&&!oldPwd.equals("")){//旧密码不为空
			User user=(User)session.getAttribute(Constants.USER_SESSION);
			if(null==user){//得不到session用户，登录过期
				return "sessionerror";
			}else{
				String sessionPwd=user.getUserpassword();//从session中获得密码
				if(oldPwd.equals(sessionPwd)){
					return "true";
				}else{
					return "false";
				}
			}
		}else{//旧密码为空，返回错误
			return "error";
		}
	}
	
	@RequestMapping("/goUpdatePwd")
	public String goUpdatePwd(){return "pwdmodify";}
	/**
	 * 更改密码
	 * @return
	 */
	@RequestMapping("updpwd.html")
	public String updatePassWord(@RequestParam("newpassword")String newPwd,HttpSession session){
		System.out.println(newPwd);
		//获得作用域内用户的id
		Long id=((User)session.getAttribute(Constants.USER_SESSION)).getId().longValue();
		//设置id和新密码
		User user=new User();
		user.setUserpassword(newPwd);
		user.setId(id);
		//执行
		boolean flag=userService.updateUser(user);

		if(flag){
			return "redirect:login.html";//修改成功，去登录页
		}else{
			return "pwdmodify";
		}
	}
	
	/**
	 * 查看执行用户明细
	 * @return
	 */
	@RequestMapping("/userview/{id}")
	public String showUserView(@PathVariable("id")String uid,Model model){
		if(null!=uid&&!uid.equals("")){
			List<SupportUser> user=userService.getPage(1, 5, null, 0, Integer.valueOf(uid));
			model.addAttribute("user", user.get(0));
		}
		return "userview";
	}
	
}
