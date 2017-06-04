package cn.swust.oa.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.swust.oa.base.ModelDrivenBaseAction;
import cn.swust.oa.domain.Forum;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ForumManageAction extends ModelDrivenBaseAction<Forum> {
	
	public String list() throws Exception {
		List<Forum> forumList = forumService.findAll();		// 读数据库（回显数据）
		ActionContext.getContext().put("forumList", forumList);
		return "list"; 
	}
	
	public String delete() throws Exception {
		forumService.deleteById(model.getId());				// 写数据库
		return "toList";
	}
	
	public String addUI() throws Exception {
		return "saveUI";
	}
	
	public String add() throws Exception {
		forumService.save(model);							// 写数据库
		return "toList";
	}
	
	public String editUI() throws Exception {
		Forum forum = forumService.findById(model.getId());		// 读数据库（修改对象）
		ActionContext.getContext().getValueStack().push(forum); // 写到ValueStack
		return "saveUI";
	}
	
	public String edit() throws Exception {
		Forum forum = forumService.findById(model.getId());		// 读数据库（修改对象）
		
		forum.setName(model.getName());							// 写修改对象
		forum.setDescription(model.getDescription());
		
		forumService.update(forum);								// 写数据库
		return "toList";
	}
	
	public String moveUp() throws Exception {
		forumService.moveUp(model.getId());							// 写数据库
		return "toList";
	}
	
	public String moveDown() throws Exception {
		forumService.moveDown(model.getId());					   // 写数据库
		return "toList";
	}
}
