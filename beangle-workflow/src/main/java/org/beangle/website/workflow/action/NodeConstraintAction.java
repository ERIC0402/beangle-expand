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
import org.beangle.website.workflow.model.StepNodeConstraint;


public class NodeConstraintAction extends SecurityActionSupport {
	
	public String index(){
		Long ncid = getLong("stepNode.id");
		System.out.println("id::"+ncid);
		StepNode nc = (StepNode) entityDao.get(StepNode.class, ncid);
		System.out.println("**********************1:::::"+ncid);
		Map m = new HashMap();
		m.put("stepNode", nc);
		put("nodeConstraints",entityDao.get(StepNodeConstraint.class, m));
		put("stepNode",nc);
		put("stepNodes",entityDao.getAll(StepNode.class));
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("stepNode", nc);
		put("wid",nc.getWorkflow().getId());
		return forward();
	}
	public String list(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		StepNode stepNode = (StepNode) session.getAttribute("stepNode");
		Map m = new HashMap();
		m.put("stepNode", stepNode);
		put("nodeConstraints",entityDao.get(StepNodeConstraint.class, m));
		put("stepNodes",entityDao.getAll(StepNode.class));
		return forward();
	}
	public String save(){
		StepNodeConstraint nodeConstraint = (StepNodeConstraint) populateEntity(StepNodeConstraint.class,"nodeConstraint");
		String code = get("nodeConstraint.code");
		String passNum = get("nodeConstraint.passNum");
		String time = get("nodeConstraint.time");
		String stepNode = get("nodeConstraint.stepNode.id");
		if(!code.equals(nodeConstraint.getCode())){
			nodeConstraint.setCode(code);
		}
		if(Integer.parseInt(passNum) != nodeConstraint.getPassNum()){
			nodeConstraint.setPassNum(Integer.parseInt(passNum));
		}
		if(Integer.parseInt(time) != nodeConstraint.getTime()){
			nodeConstraint.setTime(Integer.parseInt(time));
		}
		if(Long.valueOf(stepNode) != nodeConstraint.getStepNode().getId()){
			nodeConstraint.setStepNode((StepNode) entityDao.get(StepNode.class, Long.valueOf(stepNode)));
		}
		entityDao.saveOrUpdate(nodeConstraint);
		return redirect("search","info.save.success");
	}
	public String edit(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		StepNode stepNode = (StepNode) session.getAttribute("stepNode");
		put("stepNode",stepNode);
		StepNodeConstraint nodeConstraint = (StepNodeConstraint) getEntity(StepNodeConstraint.class,"nodeConstraint");
		put("nodeConstraint",nodeConstraint);
		put("stepNodes",entityDao.getAll(StepNode.class));
		return forward();
	}
	public String search(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		StepNode stepNode = (StepNode) session.getAttribute("stepNode");
		put("stepNode",stepNode);
		put("nodeConstraints",entityDao.search(buildQuery()));
		put("wid",stepNode.getWorkflow().getId());
		return forward();
	}
	protected EntityQuery buildQuery(){
		EntityQuery query = new EntityQuery(StepNodeConstraint.class,"nodeConstraint");
		QueryHelper.populateConditions(query);
		HttpSession session = ServletActionContext.getRequest().getSession();
		StepNode node = (StepNode) session.getAttribute("stepNode");
		Long wid = getLong("nodeConstraint.stepNode.id");
		if(wid == null){
			query.add(new Condition("nodeConstraint.stepNode=:worf",node));
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
			StepNodeConstraint nodeConstraint = (StepNodeConstraint) entityDao.get(StepNodeConstraint.class, Long.parseLong(ids[i]));
			entityDao.remove(nodeConstraint);
		}
		return redirect("search", "info.remove.success");
	}
}
