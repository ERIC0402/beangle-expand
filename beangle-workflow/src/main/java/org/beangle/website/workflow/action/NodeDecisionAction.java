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
import org.beangle.website.workflow.model.StepNode;
import org.beangle.website.workflow.model.StepNodeDecision;


public class NodeDecisionAction extends SecurityActionSupport {
	
	public String index(){
		Long ncid = getLong("stepNode.id");
		StepNode nc = (StepNode) entityDao.get(StepNode.class, ncid);
		System.out.println("**********************1:::::"+ncid);
		Map m = new HashMap();
		m.put("stepNode", nc);
		put("nodeDecisions",entityDao.get(StepNodeDecision.class, m));
		put("stepNode",nc);
		put("stepNodes",entityDao.getAll(StepNode.class));
		put("wid",nc.getWorkflow().getId());
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("stepNode", nc);
		return forward();
	}
	public String list(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		StepNode stepNode = (StepNode) session.getAttribute("stepNode");
		Map m = new HashMap();
		m.put("stepNode", stepNode);
		put("nodeDecisions",entityDao.get(StepNodeDecision.class, m));
		put("stepNode",stepNode);
		System.out.println("!::::::"+stepNode.getName());
		put("stepNodes",entityDao.getAll(StepNode.class));
		put("wid",stepNode.getWorkflow().getId());
		return forward();
	}
	public String save(){
		StepNodeDecision nodeDecision = (StepNodeDecision) populateEntity(StepNodeDecision.class,"nodeDecision");
		String gotoOrder = get("nodeDecision.gotoOrder");
		String isDecesion = get("nodeDecision.isDecesion");
		String value = get("nodeDecision.value");
		String stepNode = get("nodeDecision.stepNode.id");
		if(!gotoOrder.equals(nodeDecision.getGotoOrder())){
			nodeDecision.setGotoOrder(gotoOrder);
		}
		
		if(!value.equals(nodeDecision.getValue())){
			nodeDecision.setValue(value);
		}
		if(Long.valueOf(stepNode) != nodeDecision.getStepNode().getId()){
			nodeDecision.setStepNode((StepNode) entityDao.get(StepNode.class, Long.valueOf(stepNode)));
		}
		
		entityDao.saveOrUpdate(nodeDecision);
		return redirect("search","info.save.success");
	}
	public String edit(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		StepNode stepNode = (StepNode) session.getAttribute("stepNode");
		put("stepNode",stepNode);
		StepNodeDecision nodeDecision = (StepNodeDecision) getEntity(StepNodeDecision.class,"nodeDecision");
		put("nodeDecision",nodeDecision);
		put("stepNodes",entityDao.getAll(StepNode.class));
		return forward();
	}
	public String search(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		StepNode stepNode = (StepNode) session.getAttribute("stepNode");
		put("stepNode",stepNode);
		put("nodeDecisions",entityDao.search(buildQuery()));
		put("wid",stepNode.getWorkflow().getId());
		return forward();
	}
	protected EntityQuery buildQuery(){
		EntityQuery query = new EntityQuery(StepNodeDecision.class,"nodeDecision");
		QueryHelper.populateConditions(query);
		HttpSession session = ServletActionContext.getRequest().getSession();
		StepNode node = (StepNode) session.getAttribute("stepNode");
		Long wid = getLong("nodeDecision.stepNode.id");
		if(wid == null){
			query.add(new Condition("nodeDecision.stepNode=:worf",node));
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
			StepNodeDecision nodeDecision = (StepNodeDecision) entityDao.get(StepNodeDecision.class, Long.parseLong(ids[i]));
			entityDao.remove(nodeDecision);
		}
		return redirect("search", "info.remove.success");
	}
}
