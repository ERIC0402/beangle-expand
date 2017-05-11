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
import org.beangle.website.workflow.model.TaskNodeDecision;


public class TaskNodeDecisionAction extends SecurityActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4137854169687886176L;

	public String index(){
		Long ncid = getLong("taskNode.id");
		TaskNode nc = (TaskNode) entityDao.get(TaskNode.class, ncid);
		System.out.println("**********************1:::::"+ncid);
		Map m = new HashMap();
		m.put("taskNode", nc);
		put("nodeDecisions",entityDao.get(TaskNodeDecision.class, m));
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
		put("nodeDecisions",entityDao.get(TaskNodeDecision.class, m));
		put("taskNodes",entityDao.getAll(TaskNode.class));
		return forward();
	}
	public String save(){
		TaskNodeDecision nodeDecision = (TaskNodeDecision) populateEntity(TaskNodeDecision.class,"nodeDecision");
		String gotoOrder = get("nodeDecision.gotoOrder");
		String isDecesion = get("nodeDecision.isDecesion");
		String value = get("nodeDecision.value");
		String taskNode = get("nodeDecision.taskNode.id");
		if(!gotoOrder.equals(nodeDecision.getGotoOrder())){
			nodeDecision.setGotoOrder(gotoOrder);
		}
		
		if(!value.equals(nodeDecision.getValue())){
			nodeDecision.setValue(value);
		}
		if(Long.valueOf(taskNode) != nodeDecision.getTaskNode().getId()){
			nodeDecision.setTaskNode((TaskNode) entityDao.get(TaskNode.class, Long.valueOf(taskNode)));
		}
		
		entityDao.saveOrUpdate(nodeDecision);
		return redirect("search","info.save.success");
	}
	public String edit(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		TaskNode taskNode = (TaskNode) session.getAttribute("taskNode");
		put("taskNode",taskNode);
		TaskNodeDecision nodeDecision = (TaskNodeDecision) getEntity(TaskNodeDecision.class,"nodeDecision");
		put("nodeDecision",nodeDecision);
		put("taskNodes",entityDao.getAll(TaskNode.class));
		return forward();
	}
	public String search(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		TaskNode taskNode = (TaskNode) session.getAttribute("taskNode");
		put("taskNode",taskNode);
		put("nodeDecisions",entityDao.search(buildQuery()));
		put("wid",taskNode.getWorkflow().getId());
		return forward();
	}
	protected EntityQuery buildQuery(){
		EntityQuery query = new EntityQuery(TaskNodeDecision.class,"nodeDecision");
		QueryHelper.populateConditions(query);
		HttpSession session = ServletActionContext.getRequest().getSession();
		TaskNode node = (TaskNode) session.getAttribute("taskNode");
		Long wid = getLong("nodeDecision.taskNode.id");
		if(wid == null){
			query.add(new Condition("nodeDecision.taskNode=:worf",node));
		}
		query.setLimit(getPageLimit());
		String orderByPras = get("orderBy");
		if (StringUtils.isEmpty(orderByPras)) {
			orderByPras = "nodeDecision.gotoOrder";
		}
		query.addOrder(Order.parse(orderByPras));
		return query;
	}
	
	public String remove(){
		String nodeDecisionId = get("nodeDecision.ids");
		String[] ids = nodeDecisionId.split(",");
		for(int i=0;i<ids.length;i++){
			TaskNodeDecision nodeDecision = (TaskNodeDecision) entityDao.get(TaskNodeDecision.class, Long.parseLong(ids[i]));
			entityDao.remove(nodeDecision);
		}
		return redirect("search", "info.remove.success");
	}
}
