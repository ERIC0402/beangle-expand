package org.beangle.website.workflow.service;


import java.util.List;
import java.util.Set;

import org.beangle.ems.security.Group;
import org.beangle.ems.security.User;
import org.beangle.website.workflow.model.Task;
import org.beangle.website.workflow.model.TaskLog;
import org.beangle.website.workflow.model.TaskNode;
import org.beangle.website.workflow.model.TaskNodeConstraint;
import org.beangle.website.workflow.model.Workflow;


/**
 * 流程引擎服务类
 * @author DONHKO
 *
 */

public interface WorkflowService {

	/**
	 * 根据传入的流程ID把流程实例化为任务，并返回任务
	 */
	Task instance(Long workflowId, String operationName);
	
	/**
	 * 根据传入的任务ID获取任务的节点列表
	 */
	List<TaskNode> getTaskNodeByTaskId(Long taskId);
	
	/**
	 * 获取任务基本信息
	 */
	Task getTaskByTaskId(Long taskId);
	
	/**
	 * 获取任务的当前节点
	 */
	TaskNode getCurrentTaskNodeByTaskId(Long taskId);
	
	/**
	 * 根据传入的任务ID获取下一个节点
	 */
	TaskNode getNextTaskNode(Long taskId);
	
	/**
	 * 根据传入的任务ID和值获取下一个节点
	 */
	TaskNode getNextTaskNode(Long taskId,String value);
	
	/**
	 * 根据任务ID读取任务日志列表
	 */
	List<TaskLog> getTaskLogs(Long taskId);
	
	/**
	 * 根据任务读取任务日志列表
	 */
	List<TaskLog> getTaskLogs(Task task,String orderParam);
	
	/**
	 * 读取节点日志
	 */
	List<TaskLog> getTaskLogs(TaskNode taskNode,boolean completed);
	
	/**
	 * 根据节点ID修改任务节点操作人
	 */
	void changeTaskNodeOperator(Long taskNodeID,Set<Group> roles);
	
	/**
	 * 根据节点修改任务节点操作人
	 */
	void changeTaskNodeOperator(TaskNode taskNode,Set<Group> roles);
	
	/**
	 * 写日志
	 */
	TaskLog writeTaskLog(TaskNode taskNode,String remark,String ipAdd);
	
	/**
	 * 完成当前节点、开始下一节点、设置任务当前节点
	 */
	void changeTaskNode(Task task,String value);	
	
	/**
	 * 判断节点是否完成
	 */
	boolean isFinish(TaskNode taskNode);
	
	/**
	 * 获取上一个节点
	 */
	TaskNode getPreviousTaskNode(Task task);
	
	/**
	 * 判断当前节点是否有效
	 */
	boolean isEnableCurrentTaskNode(Task task);
	
	/**
	 * 获取所有日志
	 */
	List<TaskLog> getAllTaskLog();
	
	/**
	 * 根据用户有关的进行中的任务
	 */
	Set<Task> getOngoingTasks(User user);
	
	/**
	 * 根据用户有关的已完成的任务
	 */
	Set<Task> getFinishTasks(User user);
	
	/**
	 * 根据节点日志格式拼接日志
	 */
	List<String> spliceLogRemark(TaskNode taskNode,String[] logs);
	
	/**
	 * 设置当前节点
	 */
	void setCurrentNode(Task task,TaskNode taskNode);
	
	/**
	 * 结束任务
	 */
	void endTask(Task task);
	
	/**
	 * 和用户拥有的角色有联系的任务
	 */
	Set<Task> getAllTaskByRole(User user);
	
	/**
	 * 根据节点ID获得节点
	 */
	TaskNode getTaskNodeByTaskNodeId(Long taskNodeId);
	
	/**
	 * 更新节点限制(num限制人数，time 限制时间)
	 */
	void changeTaskNodeConstraint(TaskNode taskNode,String num,String time);
	
	/**
	 * 获取用户任务的操作日志
	 */
	List<TaskLog> getUserTaskLog(User user,Task task);
	
	/**
	 * 预写日志
	 */
	void writeUndoTaskLog(User user,TaskNode taskNode);
	
	/**
	 * 获取和用户有关的预写日志
	 */
	List<TaskLog> getUndoTaskLog(TaskNode taskNode,User user);
	
	/**
	 * 获取和用户有关预写日志的任务
	 */
	List<Task> getUserTask(User user);
	
	/**
	 * 跟新预写日志
	 */
	void updateUndoTaskLog(User user, Task task,String remark,String ipAddr);
	
	/**
	 * 获取节点的限制条件
	 */
	TaskNodeConstraint getTaskNodeConstraint(TaskNode taskNode);
	
	/**
	 * 查找所有流程
	 * @return
	 */
	public List<Workflow> findAllWorkflow();
}
