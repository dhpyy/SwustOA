package cn.swust.oa.util;

import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.swust.oa.domain.Privilege;
import cn.swust.oa.service.PrivilegeService;


public class InitialAppListener implements ServletContextListener {
	
	// 程序启动事件发生时执行此处理方法，action的拦截器对其不起作用	
	public void contextInitialized(ServletContextEvent sce) {
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext()); // 取得Spring与Struts2整合创建的Spring容器对象
		PrivilegeService privilegeService = (PrivilegeService) ac.getBean("privilegeServiceImpl");  // 取得privilegeService对象

		List<Privilege> topPrivilegeList = privilegeService.findTopList();					// 读数据库(顶层权限集合)
		sce.getServletContext().setAttribute("topPrivilegeList", topPrivilegeList);
		System.out.println("===============已准备好顶层权限数据=================");
		
		Collection<String> allPrivilegeUrls = privilegeService.findAllUrls();				// 读数据库(权限表所有url集合)
		sce.getServletContext().setAttribute("allPrivilegeUrls", allPrivilegeUrls);
		System.out.println("===============已准备好权限urls数据=================");

	}
	
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
	
}


