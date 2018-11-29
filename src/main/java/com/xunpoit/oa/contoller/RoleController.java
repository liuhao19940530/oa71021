package com.xunpoit.oa.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xunpoit.oa.entity.Role;
import com.xunpoit.oa.manager.dao.RoleManagerDao;


/**
 * @describe:模块管理的控制器
 * @author:小豪
 * 2018年11月26日
 */
@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleManagerDao roleManager;
	
	@RequestMapping(value="/addUI",method=RequestMethod.GET)
	public String addUI() {
		
		return "role/role_add";//默认是请求转发
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ModelAndView add(Role role) {
		
		ModelAndView mv = new ModelAndView("common/pub_add_success");
		
		roleManager.addRole(role);
		
		return mv;
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String remove(int id) {
		
		roleManager.removeRoleById(id);
		
		return "common/pub_del_success";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String modify(Role role) {
		
		roleManager.modifyRole(role);
		
		return "update_success";
	}
	
	@RequestMapping(value="/findRole",method=RequestMethod.GET)
	public String findModuleById(int id,Model model) {
		
		Role role = roleManager.findRoleById(id);
		
		model.addAttribute("role",role);
		
		return "role_info";
	}
	
	@RequestMapping(value="/findAll",method=RequestMethod.GET)
	public ModelAndView findAll() {
		
		ModelAndView mv = new ModelAndView();
		
		List<Role> roleList = roleManager.findAll();
		
		mv.addObject("roleList",roleList);
		
		mv.setViewName("role/index");
		
		return mv;
	}
	
}
