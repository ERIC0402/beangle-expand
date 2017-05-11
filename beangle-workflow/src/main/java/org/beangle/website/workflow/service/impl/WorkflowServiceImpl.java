package org.beangle.website.workflow.service.impl;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.GroupMember;
import org.beangle.ems.security.SecurityUtils;
import org.beangle.ems.security.User;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.workflow.model.StepNode;
import org.beangle.website.workflow.model.StepNodeConstraint;
import org.beangle.website.workflow.model.StepNodeDecision;
import org.beangle.website.workflow.model.Task;
import org.beangle.website.workflow.model.TaskLog;
import org.beangle.website.workflow.model.TaskNode;
import org.beangle.website.workflow.model.TaskNodeConstraint;
import org.beangle.website.workflow.model.TaskNodeDecision;
import org.beangle.website.workflow.model.Workflow;
import org.beangle.website.workflow.service.WorkflowService;
import org.springframework.util.Assert;


//@SuppressWarnings("unchecked")
public class WorkflowServiceImpl implements WorkflowService {

	private EntityDao entityDao;

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	public Task instance(Long workflowId, String operationName) {
		Task task = new Task();
		Assert.notNull(workflowId);
		Workflow workflow = (Workflow) entityDao.get(Workflow.class,workflowId);
		task.setName(operationName + "任务");
		task.setCode(workflow.getCode());
		Set<Group> roles = workflow.getRoles();
		Set<Group> trs = CollectUtils.newHashSet();
		for (Iterator<Group> it = roles.iterator(); it.hasNext();) {
			Group g = it.next();
			Group tg = new org.beangle.ems.security.model.GroupBean();
			tg = g;
			trs.add(tg);
		}
		task.setRoles(trs);
		entityDao.saveOrUpdate(task);
		OqlBuilder<StepNode> stepNodeQuery = OqlBuilder.from(StepNode.class,"s");
		stepNodeQuery.where("s.workflow.id=:workflowid",workflowId);
		List<StepNode> stepNodes = entityDao.search(stepNodeQuery);
		for (Iterator<StepNode> it = stepNodes.iterator(); it.hasNext();) {
			StepNode stepNode = it.next();
			TaskNode taskNode = new TaskNode();
			taskNode.setName(stepNode.getName());
			taskNode.setOrder(stepNode.getOrder());
			taskNode.setEntry(stepNode.getEntry());
			taskNode.setLogFormat(stepNode.getLogFormat());
			taskNode.setUser(stepNode.getUser());
			Set<Group> sets = CollectUtils.newHashSet();
			Set<Group> set = stepNode.getRoles();
			for (Iterator<Group> rit = set.iterator(); rit.hasNext();) {
				Group group = (Group) rit.next();
				Group g = new org.beangle.ems.security.model.GroupBean();
				g = group;
				sets.add(g);
			}
			taskNode.setRoles(sets);
			taskNode.setWorkflow(stepNode.getWorkflow());
			taskNode.setType(stepNode.getType());
			taskNode.setTask(task);
			if ("1".equals(taskNode.getOrder())) {
				task.setCurrentNode(taskNode);
				taskNode.setState(TaskNode.DOING_STATE);
				taskNode = setConstraintTime(taskNode);
			} else {
				taskNode.setState(TaskNode.UNDO_STATE);
			}
			entityDao.saveOrUpdate(taskNode);
			OqlBuilder<StepNodeConstraint> stepNodeConstraintQuery = OqlBuilder.from(StepNodeConstraint.class,"s");
			stepNodeConstraintQuery.where("s.stepNode=:stepNode",stepNode);
			List<StepNodeConstraint> stepNodeConstraints = entityDao.search(stepNodeConstraintQuery);
			for (Iterator<StepNodeConstraint> sit = stepNodeConstraints.iterator(); sit.hasNext();) {
				StepNodeConstraint stepNodeConstraint = (StepNodeConstraint) sit.next();
				TaskNodeConstraint taskNodeConstraint = new TaskNodeConstraint();
				taskNodeConstraint.setCode(stepNodeConstraint.getCode());
				taskNodeConstraint.setTaskNode(taskNode);
				taskNodeConstraint.setPassNum(stepNodeConstraint.getPassNum());
				taskNodeConstraint.setTime(stepNodeConstraint.getTime());
				entityDao.saveOrUpdate(taskNodeConstraint);
			}
			OqlBuilder<StepNodeDecision> StepNodeDecisionQuery = OqlBuilder.from(StepNodeDecision.class,"s");
			stepNodeConstraintQuery.where("s.stepNode=:stepNode",stepNode);
			List<StepNodeDecision> stepNodeDecisions = entityDao.search(StepNodeDecisionQuery);
			for (Iterator<StepNodeDecision> dit = stepNodeDecisions.iterator(); dit.hasNext();) {
				StepNodeDecision stepNodeDecision = (StepNodeDecision) dit.next();
				TaskNodeDecision taskNodeDecision = new TaskNodeDecision();
				taskNodeDecision.setTaskNode(taskNode);
				taskNodeDecision.setGotoOrder(stepNodeDecision.getGotoOrder());
				taskNodeDecision.setValue(stepNodeDecision.getValue());
				entityDao.saveOrUpdate(taskNodeDecision);
			}
		}
		task.setCode(task.getId() + "");
		entityDao.saveOrUpdate(task);
		return task;
	}

	public List<TaskNode> getTaskNodeByTaskId(Long taskId) {
		Assert.notNull(taskId);
		OqlBuilder<TaskNode> taskNodeQuery = OqlBuilder.from(TaskNode.class,"t");
		taskNodeQuery.where("t.task.id=:taskId",taskId);
		return entityDao.search(taskNodeQuery);
	}

	public Task getTaskByTaskId(Long taskId) {
		Assert.notNull(taskId);
		Task task = (Task) entityDao.get(Task.class, taskId);
		if (task != null) {
			return task;
		} else {
			return null;
		}
	}

	public TaskNode getCurrentTaskNodeByTaskId(Long taskId) {
		Assert.notNull(taskId);
		Task task = (Task) entityDao.get(Task.class, taskId);
		if (task != null) {
			return task.getCurrentNode();
		} else {
			return null;
		}
	}

	public TaskNode getNextTaskNode(Long taskId) {
		TaskNode taskNode = getCurrentTaskNodeByTaskId(taskId);
		List<TaskNode> list = getTaskNodeByTaskId(taskId);
		if (taskNode != null) {
			for (Iterator<TaskNode> it = list.iterator(); it.hasNext();) {
				TaskNode node = it.next();
				if ((Integer.parseInt(taskNode.getOrder()) + 1) == Integer
						.parseInt(node.getOrder())) {
					return node;
				}
			}
		}
		return null;
	}

	public TaskNode getNextTaskNode(Long taskId, String value) {
		TaskNode taskNode = getCurrentTaskNodeByTaskId(taskId);
		if (taskNode != null) {
			if(value == null || value == ""){
				return getNextTaskNode(taskId);
			}else{
				OqlBuilder<TaskNodeDecision> taskNodeDecisionQuery = OqlBuilder.from(TaskNodeDecision.class,"t");
				taskNodeDecisionQuery.where("t.taskNode=:taskNode",taskNode);
				taskNodeDecisionQuery.where("t.value=:val",value);
				List<TaskNodeDecision> list = entityDao.search(taskNodeDecisionQuery);
				if (list != null && list.size() > 0) {
					TaskNodeDecision tnd = (TaskNodeDecision) list.get(0);
					String order = tnd.getGotoOrder();
					OqlBuilder<TaskNode> taskNodeQuery = OqlBuilder.from(TaskNode.class,"t");
					taskNodeQuery.where("t.task.id=:taskId",taskId);
					taskNodeQuery.where("t.order=:ord",order);
					List<TaskNode> ls = entityDao.search(taskNodeQuery);
					if (ls != null && ls.size() > 0) {
						TaskNode tn = (TaskNode) ls.get(0);
						return tn;
					}
				}
			}
		}
		return null;
	}

	public List<TaskLog> getTaskLogs(Long taskId) {
		Assert.notNull(taskId);
		OqlBuilder<TaskLog> taskLogQuery = OqlBuilder.from(TaskLog.class,"log");
		taskLogQuery.join("log.taskNode.task", "t");
		taskLogQuery.where("t.id=:tid",taskId);
		taskLogQuery.orderBy("log.time");
		List<TaskLog> list = entityDao.search(taskLogQuery);
		return list;
	}

	public List<TaskLog> getTaskLogs(Task task,String orderParam) {
		Assert.notNull(task);
		OqlBuilder<TaskLog> taskLogQuery = OqlBuilder.from(TaskLog.class,"log");
		taskLogQuery.join("log.taskNode.task", "t");
		taskLogQuery.where("t=:t",task);
		if(StringUtils.isNotEmpty(orderParam)){
			taskLogQuery.orderBy(orderParam);
		}
		List<TaskLog> list = entityDao.search(taskLogQuery);
		return list;
	}
	
	public void changeTaskNodeOperator(Long taskNodeID, Set<Group> roles) {
		Assert.notNull(taskNodeID);
		TaskNode taskNode = (TaskNode) entityDao.get(TaskNode.class,
				taskNodeID);
		if (roles != null && roles.size() > 0) {
			taskNode.setRoles(roles);
			entityDao.saveOrUpdate(taskNode);
		}
	}

	public void changeTaskNodeOperator(TaskNode taskNode, Set<Group> roles) {
		Assert.notNull(taskNode);
		if (roles != null && roles.size() > 0) {
			taskNode.setRoles(roles);
			entityDao.saveOrUpdate(taskNode);
		}
	}

	public List<TaskLog> getTaskLogs(TaskNode taskNode) {
		Assert.notNull(taskNode);
		OqlBuilder<TaskLog> taskLogQuery = OqlBuilder.from(TaskLog.class,"log");
		taskLogQuery.where("log.time is null");
		taskLogQuery.where("log.ipAddress is null");
		taskLogQuery.where("log.taskNode = :tasknode",taskNode);
		List<TaskLog> list = entityDao.search(taskLogQuery);
		return list;
	}
	
	public List<TaskLog> getTaskLogs(TaskNode taskNode,boolean completed) {
		Assert.notNull(taskNode);
		OqlBuilder<TaskLog> taskLogQuery = OqlBuilder.from(TaskLog.class,"log");
		taskLogQuery.where("log.taskNode = :tasknode",taskNode);
		if(completed){
			taskLogQuery.orderBy(Order.desc("log.time"));
		}else{
			taskLogQuery.where("log.time is null");
			taskLogQuery.where("log.ipAddress is null");
		}
		
		List<TaskLog> list = entityDao.search(taskLogQuery);
		return list;
	}

	public void changeTaskNode(Task task, String value) {
		Assert.notNull(task);

		TaskNode nextNode = null;
		if (value == null) {
			nextNode = getNextTaskNode(task.getId());
		} else {
			nextNode = getNextTaskNode(task.getId(), value);
		}
		if (nextNode != null) {
			nextNode = setConstraintTime(nextNode);
			nextNode.setState(TaskNode.DOING_STATE);
			setCurrentNode(task,nextNode);
		} else {
			task.setState(Task.FINISH_STATE);
			TaskNode node = task.getCurrentNode();
			node.setState(TaskNode.FINISH_STATE);
			entityDao.saveOrUpdate(node);
			entityDao.saveOrUpdate(task);
		}
	}

	public TaskLog writeTaskLog(TaskNode taskNode, String remark, String ipAdd) {
		Assert.notNull(taskNode);
		Long userId = SecurityUtils.getUserId();
		User user = (User) entityDao.get(User.class, userId);
		TaskLog log = new TaskLog();
		log.setTaskNode(taskNode);
		log.setTime(new Timestamp(System.currentTimeMillis()));
		log.setUser(user);
		log.setRemark(remark);
		log.setIpAddress(ipAdd);
		entityDao.saveOrUpdate(log);
		return log;
	}

	protected TaskNode setConstraintTime(TaskNode taskNode) {
		Assert.notNull(taskNode);
		TaskNodeConstraint tnc = getTaskNodeConstraint(taskNode);
		if (tnc != null) {
			String code = tnc.getCode();
			if (TaskNodeConstraint.CONSTRAINT_TIME.equals(code)
					|| TaskNodeConstraint.CONSTRAINT_PASSNUM_TIME.equals(code)) {
				taskNode.setStartTime(new Timestamp(System.currentTimeMillis()));
				taskNode.setEndTime(new Timestamp(
								(System.currentTimeMillis() + (tnc.getTime() * 3600 * 1000))));
				entityDao.saveOrUpdate(taskNode);
			}
		}
		return taskNode;
	}

	public boolean isFinish(TaskNode taskNode) {
		Assert.notNull(taskNode);
		TaskNodeConstraint tnc = getTaskNodeConstraint(taskNode);
		if (tnc != null) {
			String code = tnc.getCode();
			if (TaskNodeConstraint.CONSTRAINT_PASSNUM.equals(code)
					|| TaskNodeConstraint.CONSTRAINT_PASSNUM_TIME.equals(code)) {
				List<TaskLog> list = getTaskLogs(taskNode);
				if (list != null && list.size() > 0) {
					if (list.size() >= 0) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public TaskNodeConstraint getTaskNodeConstraint(TaskNode taskNode) {
		Assert.notNull(taskNode);
		int type = taskNode.getType();
		if (type == TaskNode.CONSTRAINT_TYPE
				|| type == TaskNode.DECISION_CONSTRAINT_TYPE) {
			OqlBuilder<TaskNodeConstraint> taskNodeConstraintQuery = OqlBuilder.from(TaskNodeConstraint.class,"t");
			taskNodeConstraintQuery.where("t.taskNode=:taskNode",taskNode);
			List<TaskNodeConstraint> tncs = entityDao.search(taskNodeConstraintQuery);
			if (tncs != null && tncs.size() > 0) {
				TaskNodeConstraint tnc = (TaskNodeConstraint) tncs.get(0);
				return tnc;
			}
		}
		return null;
	}

	public TaskNode getPreviousTaskNode(Task task) {
		Assert.notNull(task);
		TaskNode current = task.getCurrentNode();
		int order = Integer.parseInt(current.getOrder()) - 1;
		if (order >= 1) {
			OqlBuilder<TaskNode> taskNodeQuery = OqlBuilder.from(TaskNode.class,"t");
			taskNodeQuery.where("t.task=:task",task);
			taskNodeQuery.where("t.order=:order",order+"");
			List<TaskNode> list = entityDao.search(taskNodeQuery);
			if (list != null && list.size() > 0) {
				TaskNode taskNode = (TaskNode) list.get(0);
				return taskNode;
			}
		}
		return null;
	}

	public boolean isEnableCurrentTaskNode(Task task) {
		Assert.notNull(task);
		if (task.getState() == Task.FINISH_STATE) {
			return false;
		} else {
			TaskNode taskNode = task.getCurrentNode();
			if (taskNode.getState() == TaskNode.FINISH_STATE) {
				return false;
			} else {
				Timestamp start = taskNode.getStartTime();
				Timestamp end = taskNode.getEndTime();
				if (start != null && end != null) {
					Long current = System.currentTimeMillis();
					Long s = start.getTime();
					Long e = end.getTime();
					if (current > e || current < s) {
						return false;
					}
				}
			}

		}
		return true;
	}

	public List<TaskLog> getAllTaskLog() {
		return entityDao.getAll(TaskLog.class);
	}

	public Set<Task> getOngoingTasks(User user) {
		List<TaskLog> logs = getAllTaskLog();
		Set<Task> tasks = new HashSet<Task>();
		for (Iterator<TaskLog> it = logs.iterator(); it.hasNext();) {
			TaskLog log = it.next();
			User u = (User) log.getUser();
			Task task = log.getTaskNode().getTask();
			if (u.getId() == user.getId()
					&& task.getState() == Task.DOING_STATE) {
				tasks.add(task);
			}
		}
		return tasks;
	}

	public Set<Task> getFinishTasks(User user) {
		List<TaskLog> logs = getAllTaskLog();
		Set<Task> tds = new HashSet<Task>();
		for (Iterator<TaskLog> it = logs.iterator(); it.hasNext();) {
			TaskLog log = it.next();
			User u = (User) log.getUser();
			Task task = log.getTaskNode().getTask();
			if (u.getId() == user.getId()
					&& task.getState() == Task.FINISH_STATE) {
				tds.add(task);
			}
		}
		return tds;
	}

	public List<String> spliceLogRemark(TaskNode taskNode, String[] logs) {
		Assert.notNull(taskNode);
		Assert.notNull(logs);

		String logFormat = taskNode.getLogFormat();
		List<String> remarks = new ArrayList<String>();
		if(logFormat != null || logFormat != ""){
			if (logFormat.indexOf("<&>") >= 0) {
				String[] str = logFormat.split("<&>");
				for (int j = 0; j < str.length; j++) {
					remarks.add(spliceLog(str[j], logs));
				}
			}else if (logFormat.indexOf("&lt;&&gt;") >= 0) {
				String[] str = logFormat.split("&lt;&&gt;");
				for (int j = 0; j < str.length; j++) {
					remarks.add(spliceLog(str[j], logs));
				}
			} else {
				remarks.add(spliceLog(logFormat, logs));
			}
		}
		return remarks;
	}

	protected String spliceLog(String logFormat, String[] logs) {
		if (logs.length > 0) {
			for (int i = 0; i < logs.length; i++) {
				if(null == logs[i]){
					logs[i] = "无";
				}
				if (logFormat.indexOf("<#>") >= 0) {
					logFormat = logFormat.replaceFirst("<#>", logs[i]);
				}else if (logFormat.indexOf("&lt;#&gt;") >= 0) {
					logFormat = logFormat.replaceFirst("&lt;#&gt;", logs[i]);
				}
			}
		}
		return logFormat;
	}

	public void endTask(Task task) {
		TaskNode ctn = task.getCurrentNode();
		List<TaskNode> list = getBackTaskNode(task, ctn);
		for(Iterator<TaskNode> it = list.iterator();it.hasNext();){
			TaskNode tn = it.next();
			tn.setState(TaskNode.FINISH_STATE);
			entityDao.saveOrUpdate(tn);
		}
		ctn.setState(TaskNode.FINISH_STATE);
		entityDao.saveOrUpdate(ctn);
		task.setCurrentNode(getEndTaskNode(task));
		task.setState(Task.FINISH_STATE);
		entityDao.saveOrUpdate(task);
	}

	public void setCurrentNode(Task task,TaskNode taskNode) {
		int co = Integer.parseInt(task.getCurrentNode().getOrder());
		int to = Integer.parseInt(taskNode.getOrder());
		if(co > to){
			List<TaskNode> hs = getBackTaskNode(task, taskNode);
			for(Iterator<TaskNode> it = hs.iterator();it.hasNext();){
				TaskNode tn = it.next();
				if(tn.getState() != TaskNode.UNDO_STATE){
					tn.setState(TaskNode.UNDO_STATE);
					entityDao.saveOrUpdate(tn);
				}
			}
		}else{
			List<TaskNode> hs = getForeTaskNode(task, taskNode);
			for(Iterator<TaskNode> it = hs.iterator();it.hasNext();){
				TaskNode tn = it.next();
				if(tn.getState() != TaskNode.FINISH_STATE){
					tn.setState(TaskNode.FINISH_STATE);
					entityDao.saveOrUpdate(tn);
				}
			}
		}
		taskNode = setConstraintTime(taskNode);
		taskNode.setState(TaskNode.DOING_STATE);
		entityDao.saveOrUpdate(taskNode);
		task.setCurrentNode(taskNode);
		task.setState(Task.DOING_STATE);
		entityDao.saveOrUpdate(task);
	}

	protected List<TaskNode> getBackTaskNode(Task task,TaskNode taskNode){
		List<TaskNode> list = new ArrayList<TaskNode>();
		List<TaskNode> allNodes = getTaskNodeByTaskId(task.getId());
		for(Iterator<TaskNode> it = allNodes.iterator();it.hasNext();){
			TaskNode tn = it.next();
			if(Integer.parseInt(tn.getOrder()) > Integer.parseInt(taskNode.getOrder())){
				list.add(tn);
			}
		}
		return list;
	}
	
	protected List<TaskNode> getForeTaskNode(Task task,TaskNode taskNode){
		List<TaskNode> list = new ArrayList<TaskNode>();
		List<TaskNode> allNodes = getTaskNodeByTaskId(task.getId());
		for(Iterator<TaskNode> it = allNodes.iterator();it.hasNext();){
			TaskNode tn = it.next();
			if(Integer.parseInt(tn.getOrder()) < Integer.parseInt(taskNode.getOrder())){
				list.add(tn);
			}
		}
		return list;
	}
	
	protected TaskNode getEndTaskNode(Task task){
		TaskNode taskNode = task.getCurrentNode();
		List<TaskNode> list = getBackTaskNode(task, taskNode);
		for(Iterator<TaskNode> it = list.iterator();it.hasNext();){
			TaskNode tn = it.next();
			if(Integer.parseInt(tn.getOrder()) > Integer.parseInt(taskNode.getOrder())){
				taskNode = tn;
			}
		}
		return taskNode;
	}

	public Set<Task> getAllTaskByRole(User user) {
		Set<Group> roles = CollectUtils.newHashSet();
		for(Iterator<GroupMember> it = user.getGroups().iterator();it.hasNext();){
			GroupMember gm = it.next();
			roles.add(gm.getGroup());
		}
		OqlBuilder<TaskNode> taskNodeQuery = OqlBuilder.from(TaskNode.class,"tn");
		taskNodeQuery.join("tn.roles","r");
		taskNodeQuery.where("r in (:a)",roles);
		List<TaskNode> list = entityDao.search(taskNodeQuery);
		Set<Task> tasks = CollectUtils.newHashSet();
		for(Iterator<TaskNode> it = list.iterator();it.hasNext();){
			TaskNode tn = it.next();
			tasks.add(tn.getTask());
		}
		return tasks;
	}

	public TaskNode getTaskNodeByTaskNodeId(Long taskNodeId) {
		Assert.notNull(taskNodeId);
		TaskNode tn = (TaskNode) entityDao.get(TaskNode.class, taskNodeId);
		return tn;
	}

	public void changeTaskNodeConstraint(TaskNode taskNode, String num,
			String time) {
		OqlBuilder<TaskNodeConstraint> taskNodeConstraintQuery = OqlBuilder.from(TaskNodeConstraint.class,"tnc");
		taskNodeConstraintQuery.where(new Condition("tnc.taskNode =:tn",taskNode));
		taskNodeConstraintQuery.orderBy(Order.parse("tnc.code"));
		List<TaskNodeConstraint> constraints = entityDao.search(taskNodeConstraintQuery);
		
		if(constraints != null && constraints.size() > 0){
			if(time == null || time == ""){
				time = "0";
			}
			if(num == null || num == ""){
				num = "0";
			}
			TaskNodeConstraint constraint = (TaskNodeConstraint) constraints.get(0);
			if("0".equals(num)){
				constraint.setCode(TaskNodeConstraint.CONSTRAINT_TIME);
			}
			constraint.setPassNum(Integer.parseInt(num));
			if("0".equals(time)){
				constraint.setCode(TaskNodeConstraint.CONSTRAINT_PASSNUM);
			}
			constraint.setTime(Integer.parseInt(time));
			entityDao.saveOrUpdate(constraint);
		}else{
			String code = "";
			if(num != null && "0".equals(num)){
				num = null;
			}
			if(time != null && "0".equals(time)){
				time = null;
			}
			if(num != null && num != "" && time != null && time != ""){
				code = TaskNodeConstraint.CONSTRAINT_PASSNUM_TIME;
				saveConstraint(taskNode, num, time, code);
			}else if(num != null && num != "") {
				code = TaskNodeConstraint.CONSTRAINT_PASSNUM;
				saveConstraint(taskNode, num, "0", code);
			}else if(time != null && time != "") {
				code = TaskNodeConstraint.CONSTRAINT_TIME;
				saveConstraint(taskNode, "0", time, code);
			}
		}
	}
	
	protected void saveConstraint(TaskNode taskNode,String num ,String time,String code){
		TaskNodeConstraint constraint = new TaskNodeConstraint();
		constraint.setPassNum(Integer.parseInt(num));
		constraint.setTime(Integer.parseInt(time));
		
		constraint.setCode(code);
		constraint.setTaskNode(taskNode);
		entityDao.saveOrUpdate(constraint);
	}

	public List<TaskLog> getUserTaskLog(User user, Task task) {
		OqlBuilder<TaskLog> taskLogQuery = OqlBuilder.from(TaskLog.class,"log");
		taskLogQuery.where("log.taskNode.task = :task",task);
		taskLogQuery.where("log.user = :user",user);
		return entityDao.search(taskLogQuery);
	}

	public void writeUndoTaskLog(User user, TaskNode taskNode) {
		TaskLog log = new TaskLog();
		log.setUser(user);
		log.setTaskNode(taskNode);
		entityDao.saveOrUpdate(log);
	}

	public List<TaskLog> getUndoTaskLog(TaskNode taskNode,User user) {
		OqlBuilder<TaskLog> taskLogQuery = OqlBuilder.from(TaskLog.class,"log");
		taskLogQuery.where("log.user = :user",user);
		taskLogQuery.where("log.time is null");
		taskLogQuery.where("log.ipAddress is null");
		if(taskNode != null){
			taskLogQuery.where("log.taskNode =:tn",taskNode);
		}
		taskLogQuery.where("log.taskNode.state =:state",TaskNode.DOING_STATE);
		return entityDao.search(taskLogQuery);
	}
	
	public List<Task> getUserTask(User user) {
		List<TaskLog> logs = getUndoTaskLog(null,user);
		Set<Task> tasks = new HashSet<Task>();
		for(Iterator<TaskLog> it=logs.iterator();it.hasNext();){
			TaskLog log = it.next();
			tasks.add(log.getTaskNode().getTask());
		}
		return new ArrayList<Task>(tasks);
	}

	public void updateUndoTaskLog(User user, Task task, String remark,
			String ipAddr) {
		OqlBuilder<TaskLog> taskLogQuery = OqlBuilder.from(TaskLog.class,"log");
		taskLogQuery.where("log.user = :user",user);
		taskLogQuery.where("log.time is null");
		taskLogQuery.where("log.ipAddress is null");
		taskLogQuery.where("log.taskNode.task =:task",task);
		taskLogQuery.where("log.taskNode.state =:state",TaskNode.DOING_STATE);
		List<TaskLog> logs = entityDao.search(taskLogQuery);
		if(logs != null && logs.size() > 0){
			TaskLog log = (TaskLog) logs.get(0);
			log.setIpAddress(ipAddr);
			log.setTime(new Timestamp(System.currentTimeMillis()));
			log.setRemark(remark);
			entityDao.saveOrUpdate(log);
		}
	}
	
	public List<Workflow> findAllWorkflow(){
		OqlBuilder<Workflow> query = OqlBuilder.from(Workflow.class,"wf");
		query.orderBy("wf.name");
		return entityDao.search(query);
	}

}
