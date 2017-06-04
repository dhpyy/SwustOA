package cn.swust.oa.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.swust.oa.base.BaseAction;
import cn.swust.oa.domain.Application;
import cn.swust.oa.domain.ApproveInfo;
import cn.swust.oa.domain.TaskView;
import cn.swust.oa.domain.Template;
import cn.swust.oa.util.QueryHelper;

import com.opensymphony.xwork2.ActionContext;

/**
 * 流转功能
 * 
 */
@Controller
@Scope("prototype")
public class FlowAction extends BaseAction {

	private Long templateId;
	private Long applicationId;
	private File upload; // 上传的文件
	private String status;
	private String taskId;

	private boolean approval;
	private String comment;
	private String outcome;
	
	private InputStream inputStream; // 下载用的

	// =========================== 申请人的功能 =================================

	/** 起草申请（申请模版列表） */
	public String templateList() throws Exception {
		List<Template> templateList = templateService.findAll();
		ActionContext.getContext().put("templateList", templateList);
		return "templateList";
	}

	/** 提交申请UI*/
	public String submitUI() throws Exception {
		return "submitUI";
	}

	/** 提交申请 */
	public String submit() throws Exception {
		// 封装对象
		Application application = new Application();
		// >> 1, 表单中的参数
		application.setTemplate(templateService.findById(templateId)); // 申请所属的模版
		application.setPath(saveUploadFile(upload)); // 申请所对应的文件
		// >> 2, 显示层的信息
		application.setApplicant(getCurrentUser()); // 当前登录的用户

		// 调用业务方法（保存申请信息，启动流程开始流转）
		flowService.submit(application);

		return "toMyApplicationList"; // 转到我的申请查询
	}

	/** 我的申请查询 */
	public String myApplicationList() throws Exception {
		// 准备分页的信息
		new QueryHelper(Application.class, "a")//
				.addWhereCondition("a.applicant=?", getCurrentUser())// 申请人是当前登录用户
				.addWhereCondition((templateId != null), "a.template.id=?", templateId)//
				.addWhereCondition((status != null && status.trim().length() > 0), "a.status=?", status)//
				.addOrderByProperty("a.applyTime", false)//
				.preparePageBean(userService, pageNum); // 注意service要是一个继承了DaoSupportImpl的类

		// 准备模版列表
		List<Template> templateList = templateService.findAll();
		ActionContext.getContext().put("templateList", templateList);

		return "myApplicationList";
	}

	// =========================== 审批人的功能 =================================

	/** 待我审批 */
	public String myTaskList() throws Exception {
		List<TaskView> taskViewList = flowService.findMyTaskViewList(getCurrentUser());
		ActionContext.getContext().put("taskViewList", taskViewList);
		return "myTaskList";
	}

	/** 审批处理页面 */
	public String approveUI() throws Exception {
		// 准备数据
		Collection<String> outcomes = flowService.getOutcomesByTaskId(taskId);
		System.out.println(outcomes);
		ActionContext.getContext().put("outcomes", outcomes);
		return "approveUI";
	}

	/** 审批处理 */
	public String approve() throws Exception {
		// 封装对象
		ApproveInfo approveInfo = new ApproveInfo();
		approveInfo.setApproval(approval); // 
		approveInfo.setComment(comment);
		approveInfo.setApplication(flowService.getApplicationById(applicationId)); // 所属的申请
		approveInfo.setApprover(getCurrentUser()); // 审批人，当前登录用户
		approveInfo.setApproveTime(new Date()); // 审批时间，当前时间

		// 调用业务方法（保存审批信息，完成当前任务，维护申请的状态）
		flowService.approve(approveInfo, taskId, outcome);

		return "toMyTaskList"; // 转到待我审批
	}

	/** 查看流转记录 */
	public String approvedHistory() throws Exception {
		List<ApproveInfo> approveInfoList = flowService.getApproveInfosByApplicationId(applicationId);
		ActionContext.getContext().put("approveInfoList", approveInfoList);
		return "approvedHistory";
	}
	
	/** 下载 */
	public String download() throws Exception {
		Application application = flowService.getApplicationById(applicationId);// 获取申请对象
		String path = application.getPath();

		// 准备默认的文件名
		String fileName = application.getTemplate().getName();
		fileName = URLEncoder.encode(fileName, "utf-8"); // 解决下载的文件名的乱码问题
		ActionContext.getContext().put("fileName", fileName);

		// 准备输出到前端的输入流
		inputStream = new FileInputStream(path);
		return "download";
	}
 
	// ---

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public boolean isApproval() {
		return approval;
	}

	public void setApproval(boolean approval) {
		this.approval = approval;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}


	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
