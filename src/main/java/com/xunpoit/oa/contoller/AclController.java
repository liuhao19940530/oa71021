package com.xunpoit.oa.contoller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xunpoit.oa.entity.Acl;
import com.xunpoit.oa.entity.Module;
import com.xunpoit.oa.entity.Role;
import com.xunpoit.oa.entity.User;
import com.xunpoit.oa.manager.dao.ModuleManagerDao;
import com.xunpoit.oa.manager.dao.RoleManagerDao;
import com.xunpoit.oa.manager.dao.UserManagerDao;
import com.xunpoit.web.PageModel;

@Controller
@RequestMapping("/acl")
public class AclController {

	@Autowired
	private ModuleManagerDao moduleManager;
	
	@Resource
	private UserManagerDao userManager;
	
	@Resource
	private RoleManagerDao roleManager;
	
	@RequestMapping(value="/initPage",method=RequestMethod.GET)
	public ModelAndView initPage(String mainType,int mainId) {
		
		ModelAndView mv = new ModelAndView("acl/index");
		
		PageModel<Module> pm = moduleManager.findAll(0,0,Integer.MAX_VALUE);
		
		mv.addObject("pm",pm);
		
		mv.addObject("mainType",mainType);
		
		mv.addObject("mainId",mainId);
		
		if(Acl.TYPE_ROLE.equals(mainType)) {//如果是角色
			
			Role role = roleManager.findRoleById(mainId);
			
			mv.addObject("role",role);
			
		}
		
		if(Acl.TYPE_USER.equals(mainType)) {//如果是用户
			
			User user = userManager.findUserById(mainId);
			
			mv.addObject("user",user);
			
	    }
		
		return mv;
	}
}
