package pers.cgq.smbms.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import pers.cgq.smbms.pojo.Bill;
import pers.cgq.smbms.pojo.Provider;
import pers.cgq.smbms.pojo.SupportBill;
import pers.cgq.smbms.pojo.SupportUser;
import pers.cgq.smbms.pojo.User;
import pers.cgq.smbms.service.BillService;
import pers.cgq.smbms.service.ProviderService;
import pers.cgq.smbms.service.UserService;
import pers.cgq.smbms.tools.Constants;
import pers.cgq.smbms.tools.PageSupport;

/**
 * 订单控制层
 * @author 光奇
 *
 */
@Controller
@RequestMapping("/bill")
public class BillController extends BaseController {
	
	@Autowired
	private BillService billService;
	@Autowired
	private ProviderService providerService;
	@Autowired
	private UserService userService;
	
	/**
	 * bill分页
	 * @param productName 商品名
	 * @param provId 供应商id
	 * @param isPayment 是否付款
	 * @param pageIndex 当前页数
	 * @return
	 */
	@RequestMapping(value="/list")
	public ModelAndView getBillPage(@RequestParam(value="queryProductName",required=false) String productName,
							  @RequestParam(value="queryProviderId",required=false) String provId,
							  @RequestParam(value="queryIsPayment",required=false) String isPayment,
							  @RequestParam(value="pageIndex",required=false) String pageIndex){
		int currentPageNo=1;
		if(pageIndex==null){
			currentPageNo = 1;
		}else{
			currentPageNo = Integer.parseInt(pageIndex);
		}
		int totalCount = billService.getAllCount(productName,isPayment,provId); //总记录数
		
		PageSupport page = new PageSupport();
		page.setCurrentPageNo(currentPageNo);
		page.setPageSize(Constants.pageSize);
		page.setTotalCount(totalCount);

		int pageCount = page.getTotalPageCount();// 获得总页数
		if(currentPageNo<1){
			currentPageNo = 1;
		}else if(currentPageNo>pageCount){
			currentPageNo=pageCount;
		}
		List<SupportBill> info = billService.findBillInfoPage(page.getPageSize(), currentPageNo, isPayment, productName, provId);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("billList", info); //添加page页
		mav.addObject("providerList", providerService.getIdAndName()); //添加供应商下拉菜单
		mav.addObject("page", page);
		mav.addObject("queryProviderId", provId);
		mav.addObject("queryIsPayment", isPayment);
		mav.addObject("queryProductName", productName);
		mav.setViewName("billlist"); //设置视图名
		return mav;
	}
	
	/**
	 * ajax传递供应商id，name到添加bill页面
	 * @return
	 */
	@RequestMapping("/ajaxprovider")
	@ResponseBody
	public String ajaxProvider(){
		List<Provider> provider = providerService.getIdAndName();
		return JSON.toJSONString(provider);
	}
	
	/**
	 * 进入添加
	 * @return
	 */
	@RequestMapping("/add.html")
	public String goAdd(Model model){
		model.addAttribute("bill", new Bill());
		return "billadd";
	}
	
	/**
	 * 添加
	 * @param bill
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/addbill",method=RequestMethod.POST)
	public String add(@Valid Bill bill,
						BindingResult result,
						HttpSession session,
						Model model){
		User user = (User) session.getAttribute(Constants.USER_SESSION);
		Bill b = bill;
		//添加创建者，创建时间
		b.setCreatedby(Long.valueOf(userService.getIdByuserCode(user.getUsercode())));
		b.setCreationdate(new Date(new Date().getTime()));
		
		if(billService.addBill(b)){
			return "redirect:list";
		}else{
			model.addAttribute("bill", bill);
			return "billadd";
		}
		
	}
	
	/**
	 * 删除bill
	 * @param billId
	 * @return
	 */
	@RequestMapping("/delbill")
	@ResponseBody
	public String delBill(@RequestParam("billid") String billId){
		int count=billService.deleteBill(Integer.valueOf(billId));
		String returnn = "false";
		if(count==0){
			returnn = "notexist";
		}else if(count==1){
			returnn = "true";
		}else if(count==-1){
			returnn = "false";
		}
		return JSON.toJSONString(returnn);
		
	}
	/**
	 * 显示bill
	 * @param billId
	 * @param model
	 * @return
	 */
	@RequestMapping("/showbill")
	public String showBill(@RequestParam("billid")String billId,Model model){
		model.addAttribute("bill", billService.getBill(Integer.valueOf(billId)));
		return "billview";
	}
	
	/**
	 * 添加信息，前往修改页面
	 * @return
	 */
	@RequestMapping("/modify.html")
	public String gomodify(@RequestParam("billid") String billId,Model model){
		model.addAttribute("bill", billService.getBill(Integer.valueOf(billId)));
		return "billmodify";
	}
	
	/**
	 * 真正更改信息
	 * @return
	 */
	@RequestMapping(value="/update",method={RequestMethod.POST})
	public String updateBil(Bill bill,Model model,HttpSession session){
		User user = (User) session.getAttribute(Constants.USER_SESSION);
		Bill b = bill;
		//添加更新者，更新时间
		b.setModifyby(Long.valueOf(userService.getIdByuserCode(user.getUsercode())));
		b.setModifydate(new Date(new Date().getTime()));
		
		if(billService.updateBill(b)){
			return "redirect:list";
		}else{
			model.addAttribute("bill", bill);
			return "billmodify";
		}

	}
	
	
}
