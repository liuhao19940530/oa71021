package com.xunpoit.oa.contoller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xunpoit.oa.entity.Module;
import com.xunpoit.oa.entity.Person;
import com.xunpoit.oa.entity.Role;
import com.xunpoit.oa.entity.User;
import com.xunpoit.oa.entity.UsersRoles;
import com.xunpoit.oa.manager.dao.AclManagerDao;
import com.xunpoit.oa.manager.dao.RoleManagerDao;
import com.xunpoit.oa.manager.dao.UserManagerDao;
import com.xunpoit.web.PageModel;
import com.xunpoit.web.Pager;

/**
 * @describe:用户管理的控制器
 * @author:小豪
 * 2018年11月26日
 */

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private UserManagerDao userManager;
	
	@Autowired
	private RoleManagerDao roleManager;
	
	@Autowired
	private AclManagerDao aclManager;
	
	@RequestMapping(value="/addUI",method=RequestMethod.GET)
	public String addUI(int personId,Model model) {
		
		model.addAttribute("personId",personId);
		
		return "user/user_add";
	}
	
	//给某个人员添加用户
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ModelAndView add(User user,String personId) {
		
		ModelAndView mv = new ModelAndView("common/pub_add_success");
		
		userManager.addUser(user,Integer.valueOf(personId));
		
		return mv;
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String remove(int id) {
		
		userManager.removeUserById(id);
	
		return "common/pub_del_success";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String modify(User user) {
		
		userManager.modifyUser(user);;
		
		return "update_success";
	}
	
	@RequestMapping(value="/findModule",method=RequestMethod.GET)
	public String findModuleById(int id,Model model) {
		
		User user = userManager.findUserById(id);
		
		model.addAttribute("user",user);
		
		return "user_info";
	}
	
	@RequestMapping(value="/findAll",method=RequestMethod.GET)
	public ModelAndView findAll(Pager pager) {
		
		ModelAndView mv = new ModelAndView();
		
		PageModel<Person> pm = userManager.findAll(pager==null?0:pager.getOffset(),3);
		
		mv.addObject("pm",pm);
		
		mv.setViewName("user/index");
		
		return mv;
	}
	
	//给用户分配角色:1.查询用户已经被分配的角色
	@RequestMapping(value="/findRoleList",method=RequestMethod.GET)
	public String findRoleList(int userId,Model model) {
		
		//已经定义的所有角色
		List<UsersRoles> ursList = userManager.findUrsListByUser(userId);
		
		//得到对应的用户名
		User user = userManager.findUserById(userId);
		
		model.addAttribute("user",user);
		
		model.addAttribute("ursList",ursList);
		
		return "user/role_list";
	}
	
	//给用户分配角色：2.查询所有角色，跳转到分配角色信息的页面
	@RequestMapping(value="/addUrsUI",method=RequestMethod.GET)
	public String addUrs(int userId,Model model) {
		
		List<Role> roleList = roleManager.findAll();
		
		model.addAttribute("roleList",roleList);
		
		model.addAttribute("userId",userId);
		
		return "user/user_addui";
	}
	
	//给用户分配角色:3.保存Urs
	@RequestMapping(value="/addUrs",method=RequestMethod.POST)
	public String addUrs(UsersRoles urs) {
		
		userManager.addUsersRoles(urs);
		
		return "common/pub_add_success";
	}
	
	//删除角色
	@RequestMapping(value="/delUrs",method=RequestMethod.GET)
    public String removeUrl(int ursId) {
		
		userManager.removeUsersRolesById(ursId);
		
		return "common/pub_del_success";
	}
	
	//登录的controller
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(User user,HttpSession session,HttpServletRequest request) {
		
		User loginUser = userManager.login(user);
		
		if(loginUser!=null) {
			
			session.setAttribute("loginUser",loginUser);
			
			//获取该用户所具有的读取权限的模块信息的集合
			List<Module> moduleList = aclManager.findAllModuleByUserId(loginUser.getId());
			
			session.setAttribute("moduleList",moduleList);
			
			//System.out.println("模块个数："+moduleList.size());
			
			return "index";
		}
		
		//登录错误信息
		request.setAttribute("msg","您输入的账号和密码不存在！请重新输入！");
		
		return "login";
	}
			
	@InitBinder("pager")
	public void initBinder(WebDataBinder binder) {
		
		binder.setFieldDefaultPrefix("pager.");
	}
}
