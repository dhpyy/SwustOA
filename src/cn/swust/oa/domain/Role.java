package cn.swust.oa.domain;

import java.io.Serializable;
import java.util.Set;

public class Role implements Serializable{
	
	private Long id;
	private String name;
	private String description;
	private Set<User> users;  // :User 多对多
	private Set<Privilege> privileges;  // :Privilege 多对多

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public Set<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}
	
}
