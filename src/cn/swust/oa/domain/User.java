package cn.swust.oa.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;

public class User implements Serializable{
	private Long id;
	private String loginName;
	private String password;
	private String name;
	private String gender;
	private String phoneNumber;
	private String email;
	private String description;
	private Department department;   // :Department 多对一
	private Set<Role> roles;         // :Role 多对多

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public Boolean isAdmin() {
		return "admin".equals(loginName);
	}

    /**
     * 根据名字判断权限
     * @param name
     * @return
     */
	public Boolean hasPrivilgeByName(String name) {
		//超级管理员默认拥有所有权限		
		if(isAdmin()) {
			return true;
		}
	    //用户拥有的权限为其所有角色权限的并集			
		for(Role role : roles) {
			for(Privilege priv : role.getPrivileges()) {
				if(priv.getName().equals(name)) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 根据URL判断权限
	 * @param priURL
	 * @return
	 */
	public Boolean hasPrivilgeByUrl(String url) {
		//超级管理员默认拥有所有权限		
		if(isAdmin()) {
			return true;
		}
		
		//得到url对应的privUrl
		// 去除url后面的参数
		int pos = url.indexOf("?");
		if(pos > -1) {
			url = url.substring(0, pos);
		}
		// 去除url的UI后缀
		if(url.endsWith("UI")) {
			url = url.substring(0, url.length()-2);
		}
		
	    //判读用户是否拥有该权限		
		Collection<String> allPrivilegeUrls = (Collection<String>) ActionContext.getContext().getApplication().get("allPrivilegeUrls");	 
		if(!allPrivilegeUrls.contains(url)) {            //如果是基本请求，不用进行权限判断
			return true;
		} else{											 //否则进行权限判断
			for(Role role : roles) {
				for(Privilege priv : role.getPrivileges()) {
					if(url.equals(priv.getUrl())) {       	 // 顶层权限privUrl为空，避免空指针异常放在equals后面
						return true;
					}
				}
			}
			return false;
		}
	}
	
}
