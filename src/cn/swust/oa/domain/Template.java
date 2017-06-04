package cn.swust.oa.domain;

/**
 * 本系统 ： 申请类型
 * JBPM : 流程定义对象
 * 
 */
public class Template {
	private Long id;
	private String name;			
	private String processKey;		// 模板对应的流程定义对象的key（可为多个版本）
	private String path; 			// 服务器中的存储路径
	
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
	public String getProcessKey() {
		return processKey;
	}
	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
}
