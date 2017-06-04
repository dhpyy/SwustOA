package cn.swust.oa.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.swust.oa.base.ModelDrivenBaseAction;
import cn.swust.oa.domain.Department;
import cn.swust.oa.util.DepartmentUtils;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class DepartmentAction extends ModelDrivenBaseAction<Department> {
	
	private Long parentId;       // 接收请求中的上级部门Id参数
	
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String list() throws Exception {
		List<Department> departmentList;
		if(parentId == null) {
			departmentList = departmentService.findTopList();				// 读数据库(顶级部门列表)
		} else{
			departmentList = departmentService.findChildrenList(parentId);  	// 读数据库(该parentId下的子部门列表)
			Department parent = departmentService.findById(parentId);			// 读数据库(分层页面对应的上级部门parent)
			ActionContext.getContext().put("parent", parent);   				// 写到ValueStack
		}
		ActionContext.getContext().put("departmentList", departmentList);   // 写到ValueStack 
		return "list";
	}
	
	public String delete() throws Exception {
		departmentService.deleteById(model.getId());    // 写数据库
		return "toList";
	}
	
	public String addUI() throws Exception {
		List<Department> topList = departmentService.findTopList();            // 读数据库(树状显示的department集合)
		List<Department> departmentTreeList = DepartmentUtils.getTreeList(topList);
		ActionContext.getContext().put("departmentList", departmentTreeList);  // 写到ValueStack
		return "saveUI";
	}
	
	public String add() throws Exception {
		Department parent = departmentService.findById(parentId);          // 读数据库(要设置的属性parent)
		model.setParent(parent);						              	   // 封装设置后的model
		departmentService.save(model);                                     // 写数据库 
		return "toList";
	}
	
	public String editUI() throws Exception {
		List<Department> topList = departmentService.findTopList();            // 读数据库(树状显示的department集合)
		List<Department> departmentTreeList = DepartmentUtils.getTreeList(topList);
		ActionContext.getContext().put("departmentList", departmentTreeList);  // 写到ValueStack
		
		Department department = departmentService.findById(model.getId());  // 读数据库(拿到要修改的departement) 
		ActionContext.getContext().getValueStack().push(department);        // 写到ValueStack
		
		if(department.getParent() != null) {								// 读数据库-级联
			parentId = department.getParent().getId();             		    // 写到ValueStack(Action对象在对象栈中)	
		}						
		
		return "saveUI";
	}
	
	public String edit() throws Exception {
		Department department = departmentService.findById(model.getId());	 // 读数据库：拿到要修改的department
		
		Department parent = departmentService.findById(parentId);			 // 读数据库(要修改的parent属性)
																				
																			 // 事务管理发生在service层，在调用service的方法完成后commit
																			 // model变为detached状态
		
		department.setParent(parent);										 // 封装修改后的department
		department.setName(model.getName());
		department.setDescription(model.getDescription());
		
		departmentService.update(department);								 // 写数据库
		
		return "toList";
	}
	
	
}
