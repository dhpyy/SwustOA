package cn.swust.oa.util;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.swust.oa.domain.Privilege;
import cn.swust.oa.domain.User;

@Component
public class Installer {
	
	@Resource
	private SessionFactory sessionFactory;
	
	/**
	 * 执行初始化安装
	 */
	@Transactional
	public void install() {
		Session session = sessionFactory.getCurrentSession();
		
		User user = new User();              // 初始化超级管理员
		user.setLoginName("admin");
		String md5Digest = DigestUtils.md5Hex("admin");
		user.setPassword(md5Digest);
		user.setName("超级管理员");
		session.save(user);
		
		Privilege menu, menu1, menu2, menu3;      // 初始化系统管理模块权限
		menu = new Privilege("系统管理", null, null);
		
		
		menu1 = new Privilege("部门管理", "/department_list", menu);     // url只需保存namespace + action_name即可
		menu2 = new Privilege("岗位管理", "/role_list", menu);
		menu3 = new Privilege("用户管理", "/user_list", menu);
		session.save(menu);
		session.save(menu1);
		session.save(menu2);
		session.save(menu3);

		
		session.save(new Privilege("部门列表", "/department_list", menu1));
		session.save(new Privilege("部门删除", "/department_delete", menu1));
		session.save(new Privilege("部门添加", "/department_add", menu1));
		session.save(new Privilege("部门修改", "/department_edit", menu1));
		
		session.save(new Privilege("岗位列表", "/role_list", menu2));
		session.save(new Privilege("岗位删除", "/role_delete", menu2));
		session.save(new Privilege("岗位添加", "/role_add", menu2));
		session.save(new Privilege("岗位修改", "/role_edit", menu2));
		session.save(new Privilege("设置权限", "/role_setPrivilege", menu2));
		
		session.save(new Privilege("用户列表", "/user_list", menu3));
		session.save(new Privilege("用户删除", "/user_delete", menu3));
		session.save(new Privilege("用户添加", "/user_add", menu3));
		session.save(new Privilege("用户修改", "/user_edit", menu3));
		session.save(new Privilege("初始密码", "/user_initPassword", menu3));
		
		
	}
	
	public static void main(String[] args) {          //运行该工具类不经过监听器 需手动创建Spring容器对象
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		Installer installer = (Installer) ac.getBean("installer");
		installer.install();
	}
}

