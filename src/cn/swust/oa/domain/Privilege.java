package cn.swust.oa.domain;

import java.io.Serializable;
import java.util.Set;

public class Privilege implements Serializable{

	private Long id;
	private String url;
	private String name;
	private Set<Role> roles;   // ：Role 多对多
	private Privilege parent;         // ：Privilege(上级)  多对一
	private Set<Privilege> children;  // ：Privilege(下级)  一对多
	
	public Privilege() {        // 重写构造方法后必须保留空的构造方法
								      
	}
	 
	public Privilege(String name, String url, Privilege parent) {   // 外键关系只要一方设置了关联即可
		this.name = name;
		this.url = url;
		this.parent = parent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Privilege getParent() {
		return parent;
	}

	public void setParent(Privilege parent) {
		this.parent = parent;
	}

	public Set<Privilege> getChildren() {
		return children;
	}

	public void setChildren(Set<Privilege> children) {
		this.children = children;
	}
}
