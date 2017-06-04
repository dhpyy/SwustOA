package cn.swust.oa.service;

import java.util.Collection;
import java.util.List;

import cn.swust.oa.domain.Application;
import cn.swust.oa.domain.ApproveInfo;
import cn.swust.oa.domain.TaskView;
import cn.swust.oa.domain.User;


public interface FlowService {

	/**
	 * C :  
	 * 
	 * 本系统：保存申请、提交申请(当前环节审批人为自己)
	 * 		
	 * JBPM: 创建执行对象、流转到并办理第一个任务
	 * 
	 */
	void submit(Application application);

	/**
	 * R :  
	 * 
	 * 本系统：查询审批
	 * 		
	 * JBPM: 查询任务
	 * 
	 */
	List<TaskView> findMyTaskViewList(User currentUser);

	/**
	 * 
	 * 本系统：审批申请       C :  申请信息
	 * 		
	 * 				  U :  申请状态
	 * 
	 * JBPM: 办理任务       U :  任务
	 * 
	 * 				  U :  执行对象
	 * 
	 * @param outcome  指定离开本任务离开要使用的Transition的名称，如果为null，就使用默认的Transition离开本活动
	 *            
	 */
	void approve(ApproveInfo approveInfo, String taskId, String outcome);

	/**
	 * R :  
	 * 
	 * JBPM：获取任务的所有流转路径
	 * 
	 */
	Collection<String> getOutcomesByTaskId(String taskId);

	/**
	 * R :  
	 * 
	 * 本系统：根据申请  查询所有审批信息
	 * 		
	 */
	List<ApproveInfo> getApproveInfosByApplicationId(Long applicationId);

	/**
	 * R :  
	 * 
	 * 本系统：根据id获取申请
	 * 
	 */
	Application getApplicationById(Long applicationId);
}
