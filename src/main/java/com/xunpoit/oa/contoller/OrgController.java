package com.xunpoit.oa.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xunpoit.oa.entity.Org;
import com.xunpoit.oa.manager.dao.OrgManagerDao;
import com.xunpoit.web.PageModel;
import com.xunpoit.web.Pager;

@Controller
@RequestMapping("/org")
public class OrgController {

	@Autowired//byType的装配方式
	private OrgManagerDao orgManager;
	
	//对应添加机构中的方法，a标签都是get请求的方式
	@RequestMapping(value="/addUI",method=RequestMethod.GET)
	public ModelAndView addUI(int pid) {
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("pid",pid);
		
		mv.setViewName("org/org_add");
		
		return mv;
	}
	
	//添加机构，pid是隐藏域中提交，表示添加在哪一个的父机构下
	@RequestMapping(value="/add",method=RequestMethod.POST)//post的请求提交方式
	public ModelAndView add(Org org,int pid){
		
		//返回到org/index.jsp页面
		ModelAndView mv = new ModelAndView("common/pub_add_success");
		
		orgManager.addOrg(org, pid);
		
		return mv;
		
	}
	
	//根据id删除机构
	@RequestMapping(value="/del",method=RequestMethod.GET)
	public String removeOrg(int id) {
		
		orgManager.removeOrgById(id);

		return "common/pub_del_success";
	}
	
	//根据id修改机构
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public ModelAndView modifyOrg(Org org) {
		
		ModelAndView mv = new ModelAndView("update_success");
		
		orgManager.modifyOrg(org);
		
		return mv;
	}
	
	//查询一个的方法
	@RequestMapping(value="/findOrg",method=RequestMethod.GET)
	public String queryOneOrg(int id,Model model) {
		
		Org org = orgManager.queryOrgById(id);
		
		model.addAttribute("org",org);//只保存数据也可以，相当于session.setAttribute()
		
		return "org_info";
	}
	
	//查询所有的方法
	@RequestMapping(value="/findAll",method=RequestMethod.GET)
	public String queryAllOrgs(int pid,Pager pager,Model model) {
		
		//传递的参数是pager.offset，需要包装类，才能使用.符号
	    //如果传递的pager为null，就从0开始查询，否则就根据传递的参数查询
		PageModel<Org> pm = orgManager.queryAll(pid,pager==null?0:pager.getOffset(),3);
		
		model.addAttribute("pm",pm);//分页模型
		
		//在添加机构的时候，需要用到当前页面层级的父机构id
		model.addAttribute("pid",pid);
		
		//返回的时候，将隐藏的ppid保存在范围之中，默认是0（顶级机构）
		model.addAttribute("ppid",0);
		
		//如果传递的pid不是顶级机构，那么当你返回的时候，需要传递的是此子机构的父机构的父机构
		if(pid>0) {
			
			Org parent = orgManager.queryOrgById(pid); 
			
			if(parent!=null&&parent.getParent()!=null){//父机构和爷爷机构都存在的话
				
				model.addAttribute("ppid",parent.getParent().getId());
			}
		}
		
		return "org/index";
	}
	
	//初始化绑定，获取pager对象，方便使用pager.offset
	@InitBinder("pager")
	public void initBinder(WebDataBinder binder) {
		
		binder.setFieldDefaultPrefix("pager.");
	}
}
