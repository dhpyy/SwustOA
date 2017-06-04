package cn.swust.oa.action;

import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.swust.oa.base.ModelDrivenBaseAction;
import cn.swust.oa.domain.Privilege;
import cn.swust.oa.domain.Role;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")    // action必须设置为多例，每次请求对应的是新的action
public class RoleAction extends ModelDrivenBaseAction<Role> {     
	
	private Long[] privilegeIds;          // 接收请求中的权限id参数 
	                                      // 自身属性需要设置get、set方法才能被初始化
	public Long[] getPrivilegeIds() {
		return privilegeIds;
	}

	public void setPrivilegeIds(Long[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}
	
	public String list() throws Exception {                     // 方法抛出异常，按调用顺序向上寻找直至找到异常拦截器进行处理
		List<Role> roleList = roleService.findAll();            // 读数据库：使用List存放读到数据，这样页面每次读到的数据都相同
		ActionContext.getContext().put("roleList", roleList);   // 写到ValueStack
		return "list";
	}
	
	public String delete() throws Exception {
		roleService.deleteById(model.getId());                 // 写数据库
		return "toList";
	}
	
	public String addUI() throws Exception {
		return "saveUI";                       // model类在栈中 页面可以直接读到请求中的id参数 
	}
	
	public String add() throws Exception {
		roleService.save(model);			    // 写数据库
		return "toList";
	}
	
	public String editUI() throws Exception {
		Role role = roleService.findById(model.getId());        // 读数据库
		ActionContext.getContext().getValueStack().push(role);  //　写到ValueStack
		return "saveUI";
	}
	
	public String edit() throws Exception {
		Role role = roleService.findById(model.getId());        // 读数据库： 拿到修改对象
		
		role.setName(model.getName());						    // 封装修改后role （外键关系属性只要一方设置了关联即可）
		role.setDescription(model.getDescription());            	
		
		roleService.update(role);                               // 写数据库
		return "toList";
	}
	
	/**
	 * 设置role的权限， 相当于修改role
	 */
	
	public String setPrivilegeUI() throws Exception {
		List<Privilege> privilegeList = privilegeService.findAll();  	// 读数据库(回显数据)
		ActionContext.getContext().put("privilegeList", privilegeList);	// 写到ValueStack
		
		Role role = roleService.findById(model.getId());				// 读数据库(修改对象)
		ActionContext.getContext().getValueStack().push(role);			// 写到ValueStack
		
		if(role.getPrivileges() != null) {								// 读数据库(级联属性)
			privilegeIds = new Long[role.getPrivileges().size()];         // 先定义才能执行赋值操作
			int i=0;
			for(Privilege priv : role.getPrivileges()) {
				privilegeIds[i++] = priv.getId();					// 写到privilegeIds属性
			}
		}
		return "setPrivilegeUI";
	}
	
	public String setPrivilege() throws Exception {
		Role role = roleService.findById(model.getId());						// 读数据库(修改对象)
		List<Privilege> privileges = privilegeService.findByIds(privilegeIds);	// 读数据库(要修改的属性)
		role.setPrivileges(new HashSet<Privilege>(privileges));					// 写到修改对象
		roleService.update(role);												// 写数据库
		return "toList";
	}
}
