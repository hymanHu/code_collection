package com.cpkf.notpad.controller.account;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cpkf.notpad.commons.constants.AccountConstants;
import com.cpkf.notpad.commons.constants.DateFormatConstants;
import com.cpkf.notpad.commons.constants.RoleConstants;
import com.cpkf.notpad.commons.utils.DateUtil;
import com.cpkf.notpad.commons.utils.MD5;
import com.cpkf.notpad.controller.account.model.AddUserModel;
import com.cpkf.notpad.controller.account.model.RegistAccountModel;
import com.cpkf.notpad.controller.account.validator.UserValidator;
import com.cpkf.notpad.entity.Account;
import com.cpkf.notpad.entity.Role;
import com.cpkf.notpad.entity.User;
import com.cpkf.notpad.security.MyUserDetailService;
import com.cpkf.notpad.server.IAccountService;
import com.cpkf.notpad.server.IMenuService;
import com.cpkf.notpad.server.IRoleService;
import com.cpkf.notpad.vo.AccountVo;
import com.cpkf.notpad.vo.Page;

/**  
 * Filename:    LoginController.java
 * Description: 登录控制层，以springMVC注解的方式实现
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   May 6, 2011 2:14:20 PM
 * modified:    
 */
@Controller
public class AccountController {
	private static Logger logger = Logger.getLogger(AccountController.class);
	@Autowired(required=true)
	private IAccountService accountService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IMenuService menuService;
	@Autowired
	private MyUserDetailService myUserDetailService;
	@Autowired
	private MessageSource messages;

	/* 
	 * method name   : redirectRegist
	 * description   : 跳转到注册页面
	 * @author       : Jiang.Hu
	 * @return       : String
	 * Create at     : Jun 8, 2011 9:10:03 AM
	 * modified      : 
	 */      
	@RequestMapping(value="/redirectRegist.do",method={RequestMethod.GET,RequestMethod.POST})
	public String redirectRegist(){
		return "account/regist";
	}
	@RequestMapping(value="redirectMyRiverAndLake.do",method={RequestMethod.POST,RequestMethod.GET})
	public String redirectMyRiverAndLake(){
		return "account/myRiverAndLake";
	}
	/* 
	 * method name   : securityLogin
	 * description   : spring security登陆
	 * @author       : Jiang.Hu
	 * @param        : @param request
	 * @param        : @param response
	 * @return       : String
	 * Create at     : Jun 8, 2011 9:10:22 AM
	 * modified      : 
	 */      
	@RequestMapping(value="/securityLogin.do",method={RequestMethod.GET,RequestMethod.POST})
	public String securityLogin(HttpServletRequest request,HttpServletResponse response){
		String email = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserDetails){
			email = ((UserDetails) principal).getUsername();
		} else {
			email = principal.toString();
		}
		if(StringUtils.isBlank(email)){
			logger.warn("email is empty.");
			return "forward:loginError.do";
		}
		AccountVo accountVo = accountService.getAccountByEmail(email);
		if(accountVo == null){
			logger.warn("No account for " + email);
			return "forward:loginError.do";
		}
		request.getSession().setAttribute(AccountConstants.SESSION_LOGIN_ACCOUNT, accountVo);
		request.getSession().setAttribute(AccountConstants.LOGIN_ACCOUNT_MENU, menuService.getShowMenus(email));
		return "index";
	}
	/* 
	 * method name   : loginError
	 * description   : 登陆错误
	 * @author       : Jiang.Hu
	 * @param        : @param request
	 * @param        : @param response
	 * @return       : String
	 * Create at     : Jun 8, 2011 9:10:42 AM
	 * modified      : 
	 */      
	@RequestMapping(value="loginError.do",method={RequestMethod.GET,RequestMethod.POST})
	public String loginError(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("errorMsg", "The user is diabled or username/password is incorrect.");
		return "../../login";
	}
	/* 
	 * method name   : logout
	 * description   : 退出
	 * @author       : Jiang.Hu
	 * @param        : @param request
	 * @param        : @param response
	 * @return       : String
	 * Create at     : Jun 8, 2011 9:10:59 AM
	 * modified      : 
	 */      
	@RequestMapping(value="logout.do",method={RequestMethod.GET,RequestMethod.POST})
	public String logout(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession(false);
		if(session != null){
			session.invalidate();
		}
		SecurityContextHolder.clearContext();
		return "../../login";
	}
	@RequestMapping(value="/sessionTimeout.do",method={RequestMethod.POST,RequestMethod.GET})
	public String sessionTimeout(){
		return "../../login";
	}
	/* 
	 * method name   : addAccount
	 * description   : 如果注册成功后直接返回home，虽然在session中已经保存有用户信息，
	 * 					但是在spring security(MyUserDetailServiceImpl类)中还没有该用户信息
	 * 					所以在home页面上操作安全框架无法判断用户角色，会跳转到登陆页面.
	 * 					注册成功后应该直接跳转登陆页面，让安全框架注册该用户，或者，
	 * 					在跳转之前用MyUserDetailServiceImpl再注册一次
	 * @author       : Jiang.Hu
	 * @param        : @param request
	 * @param        : @param response
	 * @param        : @param registAccountModel
	 * @param        : @param errors
	 * @param        : @param model
	 * @return       : String
	 * Create at     : May 31, 2011 2:53:39 PM
	 * modified      : 
	 */      
	@RequestMapping(value="/addAccount.do",method={RequestMethod.GET,RequestMethod.POST})
	public String addAccount(HttpServletRequest request,HttpServletResponse response,
			@Valid @ModelAttribute("registAccountModel") RegistAccountModel registAccountModel,
			BindingResult errors,Model model){
		UserValidator userValidator = new UserValidator();
		userValidator.registAccountValidator(request,registAccountModel,errors,accountService);
		model.addAttribute("registAccountModel",registAccountModel);
		if(errors.hasErrors()){
			logger.info("regist parameter is invalid.");
			return "account/regist";
		}
		Account account = new Account();
		account.setEmail(registAccountModel.getEmail());
		account.setPassWord(MD5.getMD5ofStr(registAccountModel.getPassword()));
		account.setStatus(true);
		Role role = roleService.getRoleByRoleName(RoleConstants.ROLE_GUEST);
		if(role == null){
			role = new Role();
			role.setDescription("guest layer");
			role.setRoleName(RoleConstants.ROLE_GUEST);
			role.setStatus(true);
			roleService.addRole(role);
		}
		account.getRoles().add(role);
		User user = new User();
		user.setRegistTime(new Date());
		account.setUser(user);
		accountService.addAccount(account);
//		myUserDetailService.loadUserByUsername(account.getEmail());
//		request.getSession().setAttribute(AccountConstants.SESSION_LOGIN_ACCOUNT, account);
//		request.getSession().setAttribute(AccountConstants.LOGIN_ACCOUNT_MENU, menuService.getShowMenus(account.getEmail()));
		return "../../login";
	}
	/* 
	 * method name   : showUserList
	 * description   : 分页显示用户列表
	 * @author       : Jiang.Hu
	 * @param        : @param request
	 * @param        : @param response
	 * @return       : String
	 * Create at     : Jun 8, 2011 9:11:22 AM
	 * modified      : 
	 */      
	@RequestMapping(value="/account/showUserList.do",method={RequestMethod.POST,RequestMethod.GET})
	public String showUserList(HttpServletRequest request,HttpServletResponse response){
		Page page  = accountService.getAllAccountVosForPage(Page.initPage(request));
		List<AccountVo> accountList = page.getList();
		if(accountList != null){
			request.setAttribute(AccountConstants.ACCOUNT_LIST, accountList);
		}
		Page.setPage(request, page);
		return "account/accountList";
	}
	/* 
	 * method name   : queryAccount
	 * description   : 分页显示查询用户列表
	 * @author       : Jiang.Hu
	 * @param        : @param request
	 * @param        : @param response
	 * @return       : String
	 * Create at     : Jun 8, 2011 9:11:54 AM
	 * modified      : 
	 */      
	@RequestMapping(value="/account/queryAccount.do",method={RequestMethod.POST,RequestMethod.GET})
	public String queryAccount(HttpServletRequest request,HttpServletResponse response){
		Page page = accountService.getQueryAccountVosForPage(Page.initPage(request));
		List<AccountVo> accountList = page.getList();
		if(accountList != null){
			request.setAttribute(AccountConstants.ACCOUNT_LIST, accountList);
		}
		Page.setPage(request, page);
		return "account/accountList";
	}
	/* 
	 * method name   : showAccountInfo
	 * description   : 展示个人资料
	 * @author       : Jiang.Hu
	 * @param        : @param request
	 * @return       : String
	 * Create at     : Jun 8, 2011 9:17:30 AM
	 * modified      : 
	 */      
	@RequestMapping(value="/account/accountInfo.do",method={RequestMethod.GET,RequestMethod.POST})
	public String showAccountInfo(HttpServletRequest request){
		Long accountId = Long.parseLong(request.getParameter("accountId"));
		AccountVo accountVo = accountService.getAccountById(accountId);
		if(accountVo != null){
			request.setAttribute("accountVo", accountVo);
		}
		return "account/accountInfo";
	}
	@RequestMapping(value="/account/modifyOwnerInfo.do",method={RequestMethod.POST,RequestMethod.GET})
	public String modifyOwnerInfo(){
		return "account/modifyOwnerInfo";
	}
	/*以下为实例内容*/
	
	@RequestMapping(value="/redirectAddUser.do",method={RequestMethod.GET,RequestMethod.POST})
	public String redirectAddUser(){
		return "account/addUser";
	}
	/* 
	 * method name   : addUser
	 * description   : 表单验证实例
	 * @author       : Jiang.Hu
	 * @param        : @param request
	 * @param        : @param response
	 * @param        : @param addUserModel
	 * @param        : @param errors
	 * @param        : @param model
	 * @return       : String
	 * Create at     : Jun 8, 2011 9:12:18 AM
	 * modified      : 
	 */      
	@RequestMapping(value="/addUser.do",method={RequestMethod.GET,RequestMethod.POST})
	public String addUser(HttpServletRequest request,HttpServletResponse response,
			@Valid @ModelAttribute("addUserModel") AddUserModel addUserModel,
			BindingResult errors,
			Model model){
		//使用注解方式局限大，故而自定义校验器，使用自定义国际化文件
		UserValidator userValidator = new UserValidator();
		userValidator.addUserValidator(addUserModel, errors);
		model.addAttribute("addUserModel", addUserModel);
		if(errors.hasErrors()){
			logger.info("The parameter is invalid");
			return "account/addUser";
		}
		return "index";
	}
	/* 
	 * 登陆实例
	 * @RequestMapping 方法的返回类型
	 * 		ModelAndView 
	 * 		Model
	 * 		A Map object for exposing a model
	 * 		View 
	 * 		String 指定view name
	 * 		void if the method handles the response itself
	 * 		If the method is annotated with @ResponseBody,the return type is written to the response HTTP body
	 * redirect, forward跳转
	 * 		使用"redirect:"前缀,如 "redirect:/view" 
	 * 		相应地使用"forward:"前缀表示servlet内部跳转
	 * Handling a file upload in a form
	 * 		使用@RequestParam("file") MultipartFile file获取上传的文件  
	 */      
	@RequestMapping(value="/login.do",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response){
		String email = request.getParameter("email");
		String passWord = request.getParameter("passWord");
		String nowTime = DateUtil.dateFormat(new Date(), DateFormatConstants.DAY_MONTH_YEAR_HOUR_MIN_SEC);
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("email", email);
		modelMap.put("passWord", passWord);
		modelMap.put("nowTime", nowTime);
		
		ModelMap modelMap2 = new ModelMap();
		modelMap2.addAttribute("email", email);
		modelMap2.addAttribute("passWord",passWord);
		modelMap2.addAttribute("nowTime", nowTime);
		
		logger.info("用户" + email + "登录。");
		
		/*
		 * 返回ModelAndView对象，常见的几种方式
		 * view-返回网页名称，在springMVC文件中已经配置了前缀和后缀
		 * model数据可以直接用键值对，也可用hashmap、modelmap以及addobject的方式
		 * */
//		return new ModelAndView("index","nowTime",nowTime);
//		return new ModelAndView("index",modelMap);
//		return new ModelAndView("index",modelMap2);
//		return new ModelAndView("index").addObject("email", email).addObject("passWord", passWord).addObject("nowTime", nowTime);
		
		System.out.println(messages.getMessage("message.account.wellcome", null, Locale.CHINA));
		System.out.println(messages.getMessage("message.account.wellcome", null, Locale.ENGLISH));
		
		Account account = accountService.getAccountByEmailAndPwd(email, MD5.getMD5ofStr(passWord));
		if(null == account){
			return new ModelAndView("common/error");
		}
		return new ModelAndView("index",modelMap); 
	}
}
