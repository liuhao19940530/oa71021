package com.xunpoit.oa.contoller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xunpoit.oa.entity.Org;
import com.xunpoit.oa.entity.Person;
import com.xunpoit.oa.manager.dao.OrgManagerDao;
import com.xunpoit.oa.manager.dao.PersonManagerDao;
import com.xunpoit.web.PageModel;
import com.xunpoit.web.Pager;

@Controller
@RequestMapping("/person")
public class PersonController {

	//调用的是PersonManagerDao
	@Autowired
	private PersonManagerDao personManager;
	
	@Resource//byType类型装配
	private OrgManagerDao orgManager;
	
	//跳转页面
	@RequestMapping(value="/addUI",method=RequestMethod.GET)
	public String addUI() {
		
		return "person/person_add";
	}
	
	//添加人员之前，首先选择所属机构
	@RequestMapping(value="/selectOrg",method=RequestMethod.GET)
	public String selectOrg(int pid,Pager pager,Model model) {
		
		PageModel<Org> pm= orgManager.queryAll(pid,pager==null?0:pager.getOffset(),5);
		
		model.addAttribute("pm",pm);
		
		model.addAttribute("pid",pid);
		
		model.addAttribute("ppid",0);
		
		if(pid > 0) {
			
		    Org parent = orgManager.queryOrgById(pid);
		    
		    if(parent!=null&&parent.getParent()!=null) {
		    	
		    	model.addAttribute("ppid",parent.getParent().getId());
		    }
		}
		
		return "person/select_org";
	}
	
	//添加人员
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ModelAndView add(Person person,int orgId) {
		
		ModelAndView mv = new ModelAndView();
		
	    mv.setViewName("common/pub_add_success");
	    
	    personManager.addPerson(person, orgId);
		
		return mv;
	}
	
	//删除人员
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String remove(int id) {
	
		personManager.removePersonById(id);
		
		return "common/pub_del_success";
	}
	
	//修改人员
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String modify(Person person) {
		
		personManager.modifyPerson(person);
		
		return "common/pub_update_success";
	}
	
	//根据id查询
	@RequestMapping(value="/findPerson",method=RequestMethod.GET)
	public String selectById(int id,Model model) {
		
		Person person = personManager.findPersonById(id);
		
		//相当于session.setAttribute()
		model.addAttribute("person",person);
		
		return "person_info";
	}
	
	//分页查询
	@RequestMapping(value="/findAll",method=RequestMethod.GET)
	public ModelAndView selectAll(Pager pager,Model model) {
		
		ModelAndView mv = new ModelAndView("person/index");
		
		PageModel<Person> pm = personManager.findAll(pager==null?0:pager.getOffset(),3);
		
		model.addAttribute("pm",pm);
		
		return mv;
	}
	
	//绑定参数
	@InitBinder("pager")
	public void initBinder(WebDataBinder binder) {
		
		binder.setFieldDefaultPrefix("pager.");
	}
	
}
