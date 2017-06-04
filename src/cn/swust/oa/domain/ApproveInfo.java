package cn.swust.oa.domain;

import java.util.Date;

/**
 * 本系统 ： 审批信息
 * JBPM : 任务对象
 * 
 */
public class ApproveInfo {
	private Long id;
	private Application application; // 审批所属的申请
	private User approver;// 审批人

	private Date approveTime;// 审批时间
	private boolean approval; // 是否通过
	private String comment; // 审批意见

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getApprover() {
		return approver;
	}

	public void setApprover(User approver) {
		this.approver = approver;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
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

}
