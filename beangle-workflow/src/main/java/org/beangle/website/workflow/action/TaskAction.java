package org.beangle.website.workflow.action;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.Order;
import org.beangle.ems.security.Group;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.query.builder.EntityQuery;
import org.beangle.struts2.helper.QueryHelper;
import org.beangle.website.workflow.model.Task;
import org.beangle.website.workflow.model.TaskNode;
import org.beangle.website.workflow.model.TaskNodeConstraint;
import org.beangle.website.workflow.model.TaskNodeDecision;



public class TaskAction extends SecurityActionSupport {
	
	public String index(){
		//List workflows = entityDao.get(entity, parameterMap)
		List tasks = entityDao.getAll(Task.class);
		put("tasks",tasks);
		return forward();
	}
	public String list(){
		List tasks = entityDao.getAll(Task.class);
		put("tasks",tasks);
		return forward();
	}
	public String save(){
		Task task = (Task) populateEntity(Task.class,"task");
		if(task.getCurrentNode() == null || task.getCurrentNode().getId() == 0){
			task.setCurrentNode(null);
		}
		String current = get("task.currentNode.id");
		if(Long.valueOf(current) != 0 ){
			task.setCurrentNode((TaskNode) entityDao.get(TaskNode.class, Long.valueOf(current)));
		}else{
			task.setCurrentNode(null);
		}
		String state = get("task.state");
		if(Integer.parseInt(state) != task.getState()){
			task.setState(Integer.parseInt(state));
		}
		String code = get("task.code");
		if(!code.equals(task.getCode())){
			task.setCode(code);
		}
		String name = get("task.name");
		if(!name.equals(task.getName())){
			task.setName(name);
		}
		List<Group> groups = entityDao.get(Group.class,
				getAll("roleIds", Long.class));
		task.getRoles().clear();
		task.getRoles().addAll(groups);
		entityDao.saveOrUpdate(task);
		return redirect("search","info.save.success");
	}
	public String edit(){
		Task task = (Task) getEntity(Task.class,"task");
		List roles = entityDao.getAll(Group.class);
		List nodes = null;
		if(task == null || task.getId() == null ){
			nodes = entityDao.getAll(TaskNode.class);
		}else{
			Map m = new HashMap();
			m.put("task.id", task.getId());
			nodes = entityDao.get(TaskNode.class, m);
		}
		
		put("task",task);
		put("roles",roles);
		put("cns",nodes);
		return forward();
	}
	public String search(){
		put("tasks",entityDao.search(buildQuery()));
		return forward();
	}
	protected EntityQuery buildQuery(){
		EntityQuery query = new EntityQuery(Task.class,"task");
		QueryHelper.populateConditions(query);
		query.setLimit(getPageLimit());
		String orderByPras = get("orderBy");
		if (StringUtils.isEmpty(orderByPras)) {
			orderByPras = "task.code";
		}
		query.addOrder(Order.parse(orderByPras));
		return query;
	}
	
	public String info(){
		Task task = (Task) entityDao.get(Task.class, getLong("task.id"));
		if(task != null){
			put("task",task);
		}
		return forward();
	}
	public String remove(){
		String TaskId = get("task.ids");
		String[] ids = TaskId.split(",");
		for(int i=0;i<ids.length;i++){
			Task task = (Task) entityDao.get(Task.class, Long.parseLong(ids[i]));
			Map m = new HashMap();
			m.put("task.id", task.getId());
			List cs = null;
			List list = entityDao.get(TaskNode.class, m);
			for(Iterator it = list.iterator();it.hasNext();){
				TaskNode node = (TaskNode) it.next();
				m.clear();
				m.put("taskNode.id", node.getId());
				if(node.getType() == TaskNode.CONSTRAINT_TYPE || node.getType() == TaskNode.DECISION_CONSTRAINT_TYPE){
					cs.add(entityDao.get(TaskNodeConstraint.class, m));
				}
				if(node.getType() == TaskNode.DECISION_TYPE || node.getType() == TaskNode.DECISION_CONSTRAINT_TYPE){
					cs.add(entityDao.get(TaskNodeDecision.class, m));
				}				
			}
			if(cs != null && cs.size() > 0){
				entityDao.remove(cs);
			}
			if(list != null && list.size() > 0){
				entityDao.remove(list);
			}
			entityDao.remove(task);
		}
		return redirect("search", "info.remove.success");
	}
		
}