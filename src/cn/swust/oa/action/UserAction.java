package cn.swust.oa.action;

import java.util.HashSet;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.swust.oa.base.ModelDrivenBaseAction;
import cn.swust.oa.domain.Department;
import cn.swust.oa.domain.Role;
import cn.swust.oa.domain.User;

@Controller
@Scope("prototype")
public class UserAction extends ModelDrivenBaseAction<User> {
	
	private Long departmentId;         // 接收请求中的部门id参数
	
	private Long[] roleIds;            // 接收请求中的岗位id参数
	
	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}
	
	
	public String list() throws Exception {
		List<User> userList = userService.findAll();  		   // 读数据库(userList)
		ActionContext.getContext().put("userList", userList);  // 写到ValueStack
		return "list";
	}
	
	public String delete() throws Exception {
		userService.deleteById(model.getId());       // 写数据库
		return "toList";
	}
	
	public String addUI() throws Exception {
		List<Department> departmentList = departmentService.findAll();    // 读数据库(部门列表)
		ActionContext.getContext().put("departmentList", departmentList); // 写到ValueStack
		
		List<Role> roleList = roleService.findAll();   					 // 读数据库(岗位列表)
		ActionContext.getContext().put("roleList", roleList); 			 // 写到ValueStack
		
		return "saveUI";
	}
	
	public String add() throws Exception {
		Department department = departmentService.findById(departmentId);   // 读数据库(要设置的属性department、roles)
		List<Role> roles = roleService.findByIds(roleIds); 				
		
		model.setDepartment(department);                                    // 封装设置后的model
		model.setRoles(new HashSet<Role>(roles));		
		String md5Digest = DigestUtils.md5Hex("1234");						   // 默认密码为1234
		model.setPassword(md5Digest);					 						
		
		userService.save(model); 											// 写数据库
		
		return "toList";
	}
	
	public String editUI() throws Exception {
		List<Department> departmentList = departmentService.findAll();    // 读数据库(部门列表)
		ActionContext.getContext().put("departmentList", departmentList); // 写到ValueStack
		
		List<Role> roleList = roleService.findAll();   					 // 读数据库(岗位列表)
		ActionContext.getContext().put("roleList", roleList); 			 // 写到ValueStack
		
		User user = userService.findById(model.getId());				 // 读数据库(拿到要修改的user)
		ActionContext.getContext().getValueStack().push(user); 			 // 写到ValueStack
		
		if(user.getDepartment() != null) {								 // 读数据库-级联(department)
			departmentId = user.getDepartment().getId();				 // 写到departmentId属性
		}																 // 自动写到ValueStack(action对象在栈中)	
		
		if(user.getRoles() != null) {								 	 // 读数据库-级联(roles)
			roleIds = new Long[user.getRoles().size()];					 // 写到roleIds属性
			int index = 0;												 // 自动写到ValueStack(action对象在栈中)	
			for(Role role : user.getRoles()) {							 
				roleIds[index++] = role.getId();
			}
		}
		
		return "saveUI";
	}
	
	public String edit() throws Exception {
		User user = userService.findById(model.getId());					 // 读数据库:拿到要修改的user
		
		Department department = departmentService.findById(departmentId);    // 读数据库(要修改的department属性、roles属性)
		List<Role> roles = roleService.findByIds(roleIds);				
		user.setDepartment(department);								    	 // 封装修改后的user
		user.setRoles(new HashSet<Role>(roles));
		user.setLoginName(model.getLoginName());
		user.setName(model.getName());
		user.setGender(model.getGender());
		user.setPhoneNumber(model.getPhoneNumber());
		user.setEmail(model.getEmail());
		user.setDescription(model.getDescription());
		
		userService.update(user);											 // 写数据库
		return "toList";
	}
	
	// 初始化密码，用于用户忘记密码时设置新密码
	public String initPassword() throws Exception {										
		User user = userService.findById(model.getId());					 // 读数据库：拿到要修改的user
		String md5Digest = DigestUtils.md5Hex("1234");					     // 封装修改后的user
		user.setPassword(md5Digest);											 
		userService.update(user);											 // 写数据库
		return "toList";
	}
	
	public String loginUI() throws Exception {
		return "loginUI";
	}
	
	public String login() throws Exception {
		User user = userService.findByNameAndPassword(model.getLoginName(), model.getPassword());  // 读数据库(登录用户)
		if(user == null) {
			addFieldError("loginError", "登录名或密码错误！");         // 登录失败，写到fieldError 
			return "loginUI";
		} else {
			ActionContext.getContext().getSession().put("user", user); // 登录成功，写到session 
			return "toIndex";
		}
	}
	
	public String logout() throws Exception {
		ActionContext.getContext().getSession().remove("user");       // 写session
		return "logout";
	}
	
	
	
	
	
	
}
