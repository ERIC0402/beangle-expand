package org.beangle.website.workflow.action;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.beangle.commons.collection.Order;
import org.beangle.ems.security.Group;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.EntityQuery;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.helper.QueryHelper;
import org.beangle.website.workflow.model.StepNodeConstraint;
import org.beangle.website.workflow.model.StepNodeDecision;
import org.beangle.website.workflow.model.Task;
import org.beangle.website.workflow.model.TaskNode;
import org.beangle.website.workflow.model.TaskNodeConstraint;
import org.beangle.website.workflow.model.Workflow;


public class TaskNodeAction extends SecurityActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6379389771840148510L;
	
	
	public String index(){
		Long taskid = getLong("task.id");
		Task task = null;
		if(taskid == null){
			put("taskNodes",entityDao.getAll(TaskNode.class));
		}else{
			task = (Task) entityDao.get(Task.class, taskid);
			OqlBuilder<TaskNode> oql = OqlBuilder.from(TaskNode.class,"t");
			oql.where("t.task.id=:taskId",taskid);
			put("taskNodes",entityDao.search(oql));
		}
		
		put("tasks",entityDao.getAll(Task.class));
		put("task",task);
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("task",task);
		return forward();
	}
	
	public String save(){
		TaskNode taskNode = (TaskNode) populateEntity(TaskNode.class,"taskNode");
		System.out.println("&&::"+taskNode.getOrder());
		String roleId = get("roleIds");
		String order = get("taskNode.order");
		String entry = get("taskNode.entry");
		if(!entry.equals(taskNode.getEntry())){
			taskNode.setEntry(entry);
		}
		if(!order.equals(taskNode.getOrder())){
			taskNode.setOrder(order);
		}
		String name = get("taskNode.name");
		if(!name.equals(taskNode.getName())){
			taskNode.setName(name);
		}
		String p = get("taskNode.logFormat");
		if(!p.equals(taskNode.getLogFormat())){
			taskNode.setLogFormat(p);
		}
		/*String state = get("taskNode.state");
		if(Integer.parseInt(state) != taskNode.getState()){
			taskNode.setState(Integer.parseInt(state));
		}*/
		String type = get("taskNode.type");
		if(Integer.parseInt(type) != taskNode.getType()){
			taskNode.setType(Integer.parseInt(type));
		}
		String taskid = get("taskNode.task.id");
		if(Long.valueOf(taskid) != taskNode.getTask().getId()){
			taskNode.setTask((Task) entityDao.get(Task.class, Long.valueOf(taskid)));
		}
		String workflowid = get("taskNode.workflow.id");
		if(Long.valueOf(workflowid) != taskNode.getWorkflow().getId()){
			taskNode.setWorkflow((Workflow)entityDao.get(Workflow.class, Long.valueOf(workflowid)));
		}
		
		System.out.println(taskNode.getTask().getId());
		System.out.println(get("taskNode.task.id"));
		taskNode.getRoles().clear();
		if(StringUtils.isNotEmpty(roleId)){
			String[] roleIds = roleId.split(",");
			Set set = new HashSet();
			for(int i=0;i<roleIds.length;i++){
				Group role = (Group) entityDao.get(Group.class, Long.parseLong(roleIds[i]));
				set.add(role);
			}
			taskNode.setRoles(set);
		}
		List orders = entityDao.searchHQLQuery("select s from org.beangle.website.workflow.model.TaskNode s left join s.task t where s.order>='"+taskNode.getOrder()+"' and t.id="+taskNode.getTask().getId()+"");
		long y = entityDao.count(TaskNode.class, "task", taskNode.getTask());
		System.out.println("原长：："+y);
		entityDao.saveOrUpdate(taskNode);
		long c = entityDao.count(TaskNode.class, "task", taskNode.getTask());
		System.out.println("现长：："+c);
		if(orders != null && orders.size() > 0){
			for(Iterator it = orders.iterator();it.hasNext();){
				TaskNode tn = (TaskNode) it.next();
				System.out.println("order::"+taskNode.getOrder()+":"+tn.getOrder());
				System.out.println("id:::"+taskNode.getId()+":"+tn.getId());
				if(taskNode.getId() != null && taskNode.getId() != tn.getId()){
					if(y == c){
						if(taskNode.getOrder().equals(tn.getOrder())){
							tn.setOrder((Integer.parseInt(tn.getOrder()) - Integer.parseInt(taskNode.getOrder())+Integer.parseInt(taskNode.getOrder())+1)+"");
						}else{
							tn.setOrder((Integer.parseInt(tn.getOrder()) - Integer.parseInt(taskNode.getOrder())+Integer.parseInt(taskNode.getOrder()))+"");
						}
					}else{
						tn.setOrder((Integer.parseInt(tn.getOrder()) - Integer.parseInt(taskNode.getOrder())+Integer.parseInt(taskNode.getOrder())+1)+"");
					}
				}
				entityDao.saveOrUpdate(tn);
			}
		}
		Task task = taskNode.getTask();
		OqlBuilder<TaskNode> oql = OqlBuilder.from(TaskNode.class,"t");
		oql.where("t.task.id=:taskId",task.getId());
		
		List tns =  entityDao.search(oql);
		for(Iterator it = tns.iterator();it.hasNext();){
			TaskNode tn = (TaskNode) it.next();
			TaskNode current = task.getCurrentNode();
			if(current == null){
				if("1".equals(tn.getOrder())){
					task.setCurrentNode(tn);
					entityDao.saveOrUpdate(task);
					break;
				}
			}
			
		}
		
		return redirect("search","info.save.success");
	}
	public String edit(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Task task = (Task) session.getAttribute("task");
		String ad = get("ad");
		String ids = get("taskNode.id");
		String order = get("order");
		TaskNode tn = null;
		if(ids != null){
			tn = (TaskNode) entityDao.get(TaskNode.class, Long.valueOf(ids));
		}
		TaskNode taskNode = new TaskNode();
		if(StringUtils.isEmpty(ad)){
			taskNode = (TaskNode) getEntity(TaskNode.class,"taskNode");
		}
		if(ad != null && Integer.parseInt(ad) == 1){
			taskNode.setOrder(tn.getOrder());
		}
		if(ad != null && Integer.parseInt(ad) == 0){
			taskNode.setOrder(Integer.parseInt(tn.getOrder())+1+"");
		}
		if(ad == null && ids == null && order != null){
			taskNode.setOrder(order);
		}
		
		List roles = entityDao.getAll(Group.class);
		put("taskNode",taskNode);
		put("workflows",entityDao.getAll(Workflow.class));
		put("ts",entityDao.getAll(Task.class));
		put("nodeConstraints",entityDao.getAll(StepNodeConstraint.class));
		put("nodeDecisions",entityDao.getAll(StepNodeDecision.class));
		put("roles",roles);
		put("task",task);
		return forward();
	}
	public String search(){
		List list = entityDao.search(buildQuery());
		put("taskNodes",list);
		if(list != null ){
			put("size",list.size());
		}else{
			put("size",0);
		}
		put("tasks",entityDao.getAll(Task.class));
		HttpSession session = ServletActionContext.getRequest().getSession();
		Task task = (Task) session.getAttribute("task");
		put("task",task);
		//System.out.println("**********************4:::::"+task.getId());
		//Task task = (Task) entityDao.get(Task.class, taskid);
		//put("task",task);
		return forward();
	}
	protected EntityQuery buildQuery(){
		EntityQuery query = new EntityQuery(TaskNode.class,"taskNode");
		QueryHelper.populateConditions(query);
		HttpSession session = ServletActionContext.getRequest().getSession();
		Task node = (Task) session.getAttribute("task");
		Long wid = getLong("taskNode.task.id");
		if(wid == null){
			query.add(new Condition("taskNode.task=:worf",node));
		}
		query.setLimit(getPageLimit());
		String orderByPras = get("orderBy");
		if (StringUtils.isEmpty(orderByPras)) {
			orderByPras = "taskNode.order";
		}
		query.addOrder(Order.parse(orderByPras));
		return query;
	}
	
	public String info(){
		TaskNode taskNode = (TaskNode) entityDao.get(TaskNode.class, getLong("taskNode.id"));
		if(taskNode != null){
			put("taskNode",taskNode);
		}
		return forward();
	}
	public String remove(){
		String taskNodeId = get("taskNode.ids");
		String[] ids = taskNodeId.split(",");
		
			for(int i=0;i<ids.length;i++){
				TaskNode taskNode = (TaskNode) entityDao.get(TaskNode.class, Long.parseLong(ids[i]));
				OqlBuilder<TaskNodeConstraint> oql = OqlBuilder.from(TaskNodeConstraint.class,"t");
				oql.where("t.taskNode=:taskNode",taskNode);
				List cs = entityDao.search(oql);
				if(cs != null && cs.size() > 0){
					entityDao.remove(cs);
				}
				try {
				entityDao.remove(taskNode);
				} catch (NumberFormatException e) {
					return redirect("search", "info.delete.failure");
				}
			}
		
		return redirect("search", "info.remove.success");
	}
	public String up(){
		String taskNodeId = get("taskNode.id");
		TaskNode taskNode = (TaskNode) entityDao.get(TaskNode.class, Long.valueOf(taskNodeId));
		OqlBuilder<TaskNode> oql = OqlBuilder.from(TaskNode.class,"t");
		oql.where("t.task=:task",taskNode.getTask());
		oql.where("t.order=:orders",(Integer.parseInt(taskNode.getOrder())-1)+"");
		List list = entityDao.search(oql);
		TaskNode taskNode1 = null;
		if(list != null && list.size() > 0){
			taskNode1 = (TaskNode) list.get(0);
			taskNode.setOrder(Integer.parseInt(taskNode.getOrder())-1+"");
			taskNode1.setOrder(Integer.parseInt(taskNode1.getOrder())+1+"");
			entityDao.saveOrUpdate(taskNode);
			entityDao.saveOrUpdate(taskNode1);
		}else{
			if((Integer.parseInt(taskNode.getOrder())-1) != 0){
				taskNode.setOrder(Integer.parseInt(taskNode.getOrder())-1+"");
				entityDao.saveOrUpdate(taskNode);
			}
		}
		Task task = taskNode.getTask();
		OqlBuilder<TaskNode> oql1 = OqlBuilder.from(TaskNode.class,"t");
		oql1.where("task.id=:taskID", task.getId());
		
		List tns =  entityDao.search(oql1);
		for(Iterator it = tns.iterator();it.hasNext();){
			TaskNode tn = (TaskNode) it.next();
			if("1".equals(tn.getOrder())){
				task.setCurrentNode(tn);
				entityDao.saveOrUpdate(task);
				break;
			}
		}
		return redirect("search", "info.save.success");
	}
	public String down(){
		String taskNodeId = get("taskNode.id");
		TaskNode taskNode = (TaskNode) entityDao.get(TaskNode.class, Long.valueOf(taskNodeId));
		OqlBuilder<TaskNode> oql = OqlBuilder.from(TaskNode.class,"t");
		oql.where("t.task=:task",taskNode.getTask());
		oql.where("t.order=:orders",(Integer.parseInt(taskNode.getOrder())+1)+"");
		List list = entityDao.search(oql);
		TaskNode taskNode1 = null;
		if(list != null && list.size() > 0){
			taskNode1 = (TaskNode) list.get(0);
			taskNode.setOrder(Integer.parseInt(taskNode.getOrder())+1+"");
			taskNode1.setOrder(Integer.parseInt(taskNode1.getOrder())-1+"");
			entityDao.saveOrUpdate(taskNode);
			entityDao.saveOrUpdate(taskNode1);
		}else{
			OqlBuilder<TaskNode> oql1 = OqlBuilder.from(TaskNode.class,"t");
			oql1.where("task.id=:taskID", taskNode.getTask().getId());
			
			List ls =  entityDao.search(oql1);
			if((Integer.parseInt(taskNode.getOrder())+1) <= ls.size()){
				taskNode.setOrder(Integer.parseInt(taskNode.getOrder())+1+"");
				entityDao.saveOrUpdate(taskNode);
			}
		}
		Task task = taskNode.getTask();
		OqlBuilder<TaskNode> oql1 = OqlBuilder.from(TaskNode.class,"t");
		oql1.where("task.id=:taskID", task.getId());
		
		List tns =  entityDao.search(oql1);
		for(Iterator it = tns.iterator();it.hasNext();){
			TaskNode tn = (TaskNode) it.next();
			if("1".equals(tn.getOrder())){
				task.setCurrentNode(tn);
				entityDao.saveOrUpdate(task);
				break;
			}
		}
		return redirect("search", "info.save.success");
	}
}