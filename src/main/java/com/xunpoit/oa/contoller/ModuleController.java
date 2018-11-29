package com.xunpoit.oa.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xunpoit.oa.entity.Module;
import com.xunpoit.oa.exception.MyException;
import com.xunpoit.oa.manager.dao.ModuleManagerDao;
import com.xunpoit.web.PageModel;
import com.xunpoit.web.Pager;

/**
 * @describe:模块管理的控制器
 * @author:小豪
 * 2018年11月26日
 */
@Controller
@RequestMapping("/module")
public class ModuleController {

	@Autowired
	private ModuleManagerDao moduleManager;
	
	@RequestMapping(value="/addUI",method=RequestMethod.GET)
	public String addUI(int pid,Model model) {
		
		model.addAttribute("pid",pid);
		
		return "module/module_add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ModelAndView add(Module module,int pid) {
		
		ModelAndView mv = new ModelAndView("common/pub_add_success");
		
		moduleManager.addModule(module, pid);
		
		return mv;
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String remove(int id) {
		
		List<Module> childList= moduleManager.selectChildListByParent(id);
		
		if(childList.size() > 0) {

		    throw new MyException("请先删除此父模块下面的子模块！");	
		    
		}else {		
		
		   moduleManager.removeModuleById(id);
		
		}
		return "common/pub_del_success";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String modify(Module module) {
		
		moduleManager.modifyModule(module);
		
		return "update_success";
	}
	
	@RequestMapping(value="/findModule",method=RequestMethod.GET)
	public String findModuleById(int id,Model model) {
		
		Module module = moduleManager.findModuleById(id);
		
		model.addAttribute("module",module);
		
		return "module_info";
	}
	
	@RequestMapping(value="/findAll",method=RequestMethod.GET)
	public ModelAndView findAll(int pid,Pager pager) {
		
		ModelAndView mv = new ModelAndView();
		
		PageModel<Module> pm = moduleManager.findAll(pid,pager==null?0:pager.getOffset(),3);
		
		mv.addObject("pm",pm);
		
		mv.addObject("pid",pid);
		
		mv.setViewName("module/index");
		
		return mv;
	}
	
	@InitBinder("pager")
	public void initBinder(WebDataBinder binder) {
		
		binder.setFieldDefaultPrefix("pager.");
	}
}
