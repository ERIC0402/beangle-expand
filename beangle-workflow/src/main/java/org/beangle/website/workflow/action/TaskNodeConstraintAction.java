package org.beangle.website.workflow.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.beangle.commons.collection.Order;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.EntityQuery;
import org.beangle.struts2.helper.QueryHelper;
import org.beangle.website.workflow.model.TaskNode;
import org.beangle.website.workflow.model.TaskNodeConstraint;


public class TaskNodeConstraintAction extends SecurityActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 895111880031339731L;

	public String index(){
		Long ncid = getLong("taskNode.id");
		System.out.println("id::"+ncid);
		TaskNode nc = (TaskNode) entityDao.get(TaskNode.class, ncid);
		System.out.println("**********************1:::::"+nc.getId());
		Map m = new HashMap();
		m.put("taskNode", nc);
		put("nodeConstraints",entityDao.get(TaskNodeConstraint.class, m));
		put("taskNode",nc);
		put("taskNodes",entityDao.getAll(TaskNode.class));
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("taskNode", nc);
		put("wid",nc.getWorkflow().getId());
		return forward();
	}
	public String list(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		TaskNode taskNode = (TaskNode) session.getAttribute("taskNode");
		Map m = new HashMap();
		m.put("taskNode", taskNode);
		put("nodeConstraints",entityDao.get(TaskNodeConstraint.class, m));
		put("taskNodes",entityDao.getAll(TaskNode.class));
		return forward();
	}
	public String save(){
		TaskNodeConstraint nodeConstraint = (TaskNodeConstraint) populateEntity(TaskNodeConstraint.class,"nodeConstraint");
		String code = get("nodeConstraint.code");
		String passNum = get("nodeConstraint.passNum");
		String time = get("nodeConstraint.time");
		String taskNode = get("nodeConstraint.taskNode.id");
		if(!code.equals(nodeConstraint.getCode())){
			nodeConstraint.setCode(code);
		}
		if(Integer.parseInt(passNum) != nodeConstraint.getPassNum()){
			nodeConstraint.setPassNum(Integer.parseInt(passNum));
		}
		if(Integer.parseInt(time) != nodeConstraint.getTime()){
			nodeConstraint.setTime(Integer.parseInt(time));
		}
		if(Long.valueOf(taskNode) != nodeConstraint.getTaskNode().getId()){
			nodeConstraint.setTaskNode((TaskNode) entityDao.get(TaskNode.class, Long.valueOf(taskNode)));
		}
		entityDao.saveOrUpdate(nodeConstraint);
		return redirect("search","info.save.success");
	}
	public String edit(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		TaskNode taskNode = (TaskNode) session.getAttribute("taskNode");
		put("taskNode",taskNode);
		TaskNodeConstraint nodeConstraint = (TaskNodeConstraint) getEntity(TaskNodeConstraint.class,"nodeConstraint");
		put("nodeConstraint",nodeConstraint);
		put("taskNodes",entityDao.getAll(TaskNode.class));
		return forward();
	}
	public String search(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		TaskNode taskNode = (TaskNode) session.getAttribute("taskNode");
		put("taskNode",taskNode);
		put("nodeConstraints",entityDao.search(buildQuery()));
		put("wid",taskNode.getWorkflow().getId());
		return forward();
	}
	protected EntityQuery buildQuery(){
		EntityQuery query = new EntityQuery(TaskNodeConstraint.class,"nodeConstraint");
		QueryHelper.populateConditions(query);
		HttpSession session = ServletActionContext.getRequest().getSession();
		TaskNode node = (TaskNode) session.getAttribute("taskNode");
		Long wid = getLong("nodeConstraint.taskNode.id");
		if(wid == null){
			query.add(new Condition("nodeConstraint.taskNode=:worf",node));
		}
		query.setLimit(getPageLimit());
		String orderByPras = get("orderBy");
		if (StringUtils.isEmpty(orderByPras)) {
			orderByPras = "nodeConstraint.code";
		}
		query.addOrder(Order.parse(orderByPras));
		return query;
	}
	
	public String remove(){
		String nodeConstraintId = get("nodeConstraint.ids");
		String[] ids = nodeConstraintId.split(",");
		for(int i=0;i<ids.length;i++){
			TaskNodeConstraint nodeConstraint = (TaskNodeConstraint) entityDao.get(TaskNodeConstraint.class, Long.parseLong(ids[i]));
			entityDao.remove(nodeConstraint);
		}
		return redirect("search", "info.remove.success");
	}
}
