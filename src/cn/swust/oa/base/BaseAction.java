package cn.swust.oa.base;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import cn.swust.oa.domain.User;
import cn.swust.oa.service.DepartmentService;
import cn.swust.oa.service.FlowService;
import cn.swust.oa.service.ForumService;
import cn.swust.oa.service.PrivilegeService;
import cn.swust.oa.service.ProcessDefinitionService;
import cn.swust.oa.service.RoleService;
import cn.swust.oa.service.TemplateService;
import cn.swust.oa.service.UserService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {

	// ===================== 声明Service ====================
		@Resource
		protected RoleService roleService;
		@Resource
		protected DepartmentService departmentService;
		@Resource
		protected UserService userService;
		@Resource
		protected PrivilegeService privilegeService;
		@Resource
		protected ForumService forumService;
		@Resource
		protected ProcessDefinitionService processDefinitionService;
		@Resource
		protected TemplateService templateService;
		@Resource
		protected FlowService flowService;
		
		protected int pageNum = 1; // 当前页，默认为第1页

		public int getPageNum() {
			return pageNum;
		}

		public void setPageNum(int pageNum) {
			this.pageNum = pageNum;
		}
		
		/**
		 * 获取当前登录的用户
		 */
		public User getCurrentUser() {
			return (User) ActionContext.getContext().getSession().get("user");
		}

		/**
		 * 保存上传的文件到服务器，并返回其服务器中的存储路径
		 * @param upload
		 * @return
		 */
		protected String saveUploadFile(File upload) {
			// >> 1, 得到服务器中保存文件的路径
			SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
			String basePath = ServletActionContext.getServletContext().getRealPath("/WEB-INF/upload_files");
			String datePath = sdf.format(new Date());
			
			// >> 2, 如果文件夹不存在，就创建
			File dir = new File(basePath +datePath);
			if(!dir.exists()){
				dir.mkdirs();  
			}
			String path = basePath+ datePath + UUID.randomUUID().toString(); // 注意同名的问题，可以使用uuid做为文件名
			File destFile = new File(path);
			
			// >> 3, 移动文件
			upload.renameTo(destFile);
			return path;
		}
}
