package pers.cgq.smbms.controller;

import java.net.Authenticator.RequestorType;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.swing.text.html.FormSubmitEvent.MethodType;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import pers.cgq.smbms.pojo.Provider;
import pers.cgq.smbms.pojo.User;
import pers.cgq.smbms.service.ProviderService;
import pers.cgq.smbms.service.UserService;
import pers.cgq.smbms.tools.Constants;
import pers.cgq.smbms.tools.PageSupport;
/**
 * 供应商，控制
 * @author 光奇
 *
 */
@Controller
@RequestMapping("/prov")
public class ProviderController extends BaseController {
	
	@Autowired
	private ProviderService providerService;
	@Autowired
	private UserService userService;

	/**
	 * 显示供应商列表
	 * @param provCode 供应商编码，可选
	 * @param provName 供应商名称，可选
	 * @param model 返回域对象
	 * @return
	 */
	@RequestMapping("/list")
	public String ProviderList(@RequestParam(value="pageIndex",required=false)String currentPageNo,
								String totalCount,
								@RequestParam(value="totalPageCount",required=false)String totalPageCount,
								@RequestParam(value="queryProCode",required=false)String provCode,
								@RequestParam(value="queryProName",required=false)String provName,
								Model model){
		int pageNo=1;
		int totalCounts=0;
		if(null==currentPageNo){
			pageNo=1;
		}else{
			pageNo=Integer.valueOf(currentPageNo);
		}
		//获得总记录数，供应商编码，名称作为查询条件
		totalCounts=providerService.getAllCount(provCode,provName);
		
		PageSupport page=new PageSupport();
		page.setCurrentPageNo(pageNo);
		page.setPageSize(Constants.pageSize);
		page.setTotalCount(totalCounts);
		int totalPageCounts=page.getTotalPageCount();
		if(pageNo>totalPageCounts){
			pageNo=totalPageCounts;
		}else if(pageNo<=0){
			pageNo=1;
		}
		
		List<Provider> providerList=providerService.findProviderInfoPage(page.getPageSize(),pageNo, provCode, provName);
		model.addAttribute("providerList", providerList);
		model.addAttribute("page", page);
		model.addAttribute("queryProCode", provCode);
		model.addAttribute("queryProName", provName);
		return "providerlist";
	}

	/**
	 * 数据校验，将空的对象发送到供应商添加页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/add.html")
	public String goProviderAdd(Model model){
		model.addAttribute("provider", new Provider());
		return "provideradd";
	}
	
	/**
	 * 添加供应商
	 * @param provider 要添加的用户信息
	 * @param result 数据校验对象
	 * @param session HttpSession对象
	 * @return 显示页面
	 */
	@RequestMapping("/addprovider")
	public String addProvider(@Valid Provider provider
								,BindingResult result
								,HttpSession session
								,Model model){
		User user = (User) session.getAttribute(Constants.USER_SESSION);
		Provider prov = provider;
		//创建时，添加创建者编号和日期
		prov.setCreatedby(Long.valueOf(userService.getIdByuserCode(user.getUsercode())));
		prov.setCreationdate(new Date(new Date().getTime()));
		//添加成功
		if(providerService.addProvider(prov)){
			return "redirect:list";
		}else{
			model.addAttribute("provider",provider);
			return "provideradd";
		}
	}
	
	/**
	 * ajax删除供应商
	 * @param provId
	 * @return
	 */
	@RequestMapping("/delprov")
	@ResponseBody
	public String delProvider(@RequestParam("proid")String provId){
		int count = providerService.delProviderById(Integer.parseInt(provId));
		String returnn ="false";
		if(count == -2){
			returnn = "notexist";//不存在
		}else if(count == -1){
			returnn = "false";//删除失败
		}else if(count>1){
			returnn =  ""+count;
		}else if(count==0){
			returnn = "true";
		}
		return JSON.toJSONString(returnn);
	}
	
	/**
	 * 显示供应商信息
	 * @param provId 供应商编号
	 * @return
	 */
	@RequestMapping("/view.html")
	public String showProvider(@RequestParam("proid")String provId,Model model){
		Provider provider = providerService.getProviderById(Integer.parseInt(provId));
		model.addAttribute("provider", provider);
		return "providerview";
	}
	
	/**
	 * 修改供应商信息所需，源信息
	 * @return
	 */
	@RequestMapping("/modify.html")
	public String goProvidermodify(@RequestParam("proid")String provId,Model model){
		model.addAttribute("provider", providerService.getProviderById(Integer.parseInt(provId)));
		return "providermodify";
	}
	
	/**
	 * 真正执行修改的
	 * @param prov
	 * @return
	 */
	@RequestMapping(value="/modifyprov.html",method={RequestMethod.POST})
	public String modifyProvider(Provider prov,Model model,HttpSession session){
		User user = (User) session.getAttribute(Constants.USER_SESSION);
		//添加修改人和修改时间
		Provider provider = prov;
		provider.setModifyby(Long.valueOf(userService.getIdByuserCode(user.getUsercode())));
		provider.setModifydate(new Date(new Date().getTime()));
		if(providerService.updateProvider(prov)){
			//修改成功
			return "redirect:list";
		}else{
			model.addAttribute("provider", prov);
			return "providermodify";
		}
		
	}
	
}
