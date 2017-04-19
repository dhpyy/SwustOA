package cn.swust.oa.base;

import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import cn.swust.oa.service.DepartmentServcie;
import cn.swust.oa.service.ForumService;
import cn.swust.oa.service.PrivilegeService;
import cn.swust.oa.service.RoleService;
import cn.swust.oa.service.UserService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

// 只需将子类交给容器管理
public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T> { // 抽象出action类继承ActionSupport、实现ModelDriven接口的共性
	                                                                            // 可以不继承ActionSupport类（但失去了它提供的方法）
	protected T model;
	
	@Resource
	protected  DepartmentServcie departmentService;           // 抽象出action类拥有Service的引用
	@Resource
	protected RoleService roleService;
	@Resource
	protected UserService userService;
	@Resource
	protected PrivilegeService privilegeService;
	@Resource
	protected ForumService forumService;
	
	// 通过反射得到泛型的对象
	public BaseAction() {
		try {
			ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass(); 
			Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];       // 通过反射拿到泛型的Class
			model = clazz.newInstance();                       //  通过反射拿到泛型的实例
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	public T getModel() {
		return model;
	}
	
}
