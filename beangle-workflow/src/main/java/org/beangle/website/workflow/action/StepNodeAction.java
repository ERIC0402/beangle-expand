package org.beangle.website.workflow.action;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.beangle.commons.collection.Order;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.User;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.EntityQuery;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.helper.QueryHelper;
import org.beangle.website.workflow.model.StepNode;
import org.beangle.website.workflow.model.StepNodeConstraint;
import org.beangle.website.workflow.model.StepNodeDecision;
import org.beangle.website.workflow.model.Workflow;


public class StepNodeAction extends SecurityActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6144899297557371480L;
	
	public String index(){
		Long workflowid = getLong("workflow.id");
		Workflow workflow = null;
		if(workflowid == null){
			put("stepNodes",entityDao.getAll(StepNode.class));
			put("size",0);
		}else{
			workflow = (Workflow) entityDao.get(Workflow.class, workflowid);
			OqlBuilder<StepNode> oql = OqlBuilder.from(StepNode.class,"s");
			oql.where("s.workflow.id=:wid",workflowid);
			oql.orderBy("s.order");
			List list = entityDao.search(oql);
			put("stepNodes",list);
			if(list != null ){
				put("size",list.size());
			}else{
				put("size",0);
			}
		}
		put("workflows",entityDao.getAll(Workflow.class));
		put("workflow",workflow);
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("workflow", workflow);
		//System.out.println("*********::::"+workflow.getName());
		return forward();
	}
	public String list(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		Workflow workflow = (Workflow) session.getAttribute("workflow");
		//System.out.println("**********************2:::::"+workflow.getId());
		EntityQuery query = new EntityQuery(StepNode.class,"stepNode");
		query.setLimit(getPageLimit());
		String orderByPras = "stepNode.order";
		query.addOrder(Order.parse(orderByPras));
		query.add(new Condition("stepNode.workflow=(:wf)",workflow));
		List list = entityDao.search(query);
		/*map.clear();
		map.put("workflow", workflow);
		List list = entityDao.get(StepNode.class, map);*/
		put("stepNodes",list);
		if(list != null){
			put("size",list.size());
		}
		put("workflows",entityDao.getAll(Workflow.class));
		put("workflow", workflow);
		return forward();
	}
	public String save(){
		StepNode stepNode = (StepNode) populateEntity(StepNode.class,"stepNode");

		String state = get("stepNode.state");
		String order = get("stepNode.order");
		String entry = get("stepNode.entry");
		String logFormat = get("stepNode.logFormat");
		if(!logFormat.equals(stepNode.getLogFormat())){
			stepNode.setLogFormat(logFormat);
		}
		if(!entry.equals(stepNode.getEntry())){
			stepNode.setEntry(entry);
		}
		if(!order.equals(stepNode.getOrder())){
			stepNode.setOrder(order);
		}
		String name = get("stepNode.name");
		if(!name.equals(stepNode.getName())){
			stepNode.setName(name);
		}
		String type = get("stepNode.type");
		if(Integer.parseInt(type) != stepNode.getType()){
			stepNode.setType(Integer.parseInt(type));
		}
		String workflowid = get("stepNode.workflow.id");
		Workflow workflow = null;
		if(Long.valueOf(workflowid) != stepNode.getWorkflow().getId()){
			workflow = (Workflow) entityDao.get(Workflow.class, Long.valueOf(workflowid));
			stepNode.setWorkflow(workflow);
		}
		
		System.out.println(stepNode.getWorkflow().getId());
		System.out.println(get("stepNode.workflow.id"));
		stepNode.getRoles().clear();
		List<Group> groups = entityDao.get(Group.class,getAll("roleIds", Long.class));
		stepNode.getRoles().addAll(groups);
		List orders = entityDao.searchHQLQuery("select s from org.beangle.website.workflow.model.StepNode s left join s.workflow w where s.order>='"+stepNode.getOrder()+"' and w.id="+stepNode.getWorkflow().getId());
		long y = entityDao.count(StepNode.class, "workflow", stepNode.getWorkflow());
		entityDao.saveOrUpdate(stepNode);
		long c = entityDao.count(StepNode.class, "workflow", stepNode.getWorkflow());
		if(orders != null && orders.size() > 0){
			for(Iterator it = orders.iterator();it.hasNext();){
				StepNode tn = (StepNode) it.next();
				if(stepNode.getId() != null && tn.getId() != stepNode.getId()){
					if(y == c){
						if(tn.getOrder().equals(stepNode.getOrder())){
							tn.setOrder((Integer.parseInt(tn.getOrder()) - Integer.parseInt(stepNode.getOrder())+Integer.parseInt(stepNode.getOrder())+1)+"");
						}else{
							tn.setOrder((Integer.parseInt(tn.getOrder()) - Integer.parseInt(stepNode.getOrder())+Integer.parseInt(stepNode.getOrder()))+"");
						}
					}else{
						tn.setOrder((Integer.parseInt(tn.getOrder()) - Integer.parseInt(stepNode.getOrder())+Integer.parseInt(stepNode.getOrder())+1)+"");		
					}
				}
				entityDao.saveOrUpdate(tn);
			}
		}
		put("workflow",workflow);
		return redirect("search","info.save.success");
	}
	public String edit(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		Workflow workflow = (Workflow) session.getAttribute("workflow");
		System.out.println("*********::::"+workflow.getName());
		put("workflow",workflow);
		String ad = get("ad");
		String ids = get("stepNode.id");
		String order = get("order");
		StepNode sn = null;
		if(ids != null){
			sn = (StepNode) entityDao.get(StepNode.class, Long.valueOf(ids));
		}
		StepNode stepNode = new StepNode();
		if(StringUtils.isEmpty(ad)){
			stepNode = (StepNode) getEntity(StepNode.class,"stepNode");
		}
		if(ad != null && Integer.parseInt(ad) == 1){
			stepNode.setOrder(sn.getOrder());
		}
		if(ad != null && Integer.parseInt(ad) == 0){
			stepNode.setOrder(Integer.parseInt(sn.getOrder())+1+"");
		}
		if(ad == null && ids == null && order != null){
			stepNode.setOrder(order);
		}
		List roles = entityDao.getAll(Group.class);
		put("stepNode",stepNode);
		put("workflows",entityDao.getAll(Workflow.class));
		put("nodeConstraints",entityDao.getAll(StepNodeConstraint.class));
		put("nodeDecisions",entityDao.getAll(StepNodeDecision.class));
		put("roles",roles);
		put("users",entityDao.getAll(User.class));
		return forward();
	}
	public String search(){
		
		List list = entityDao.search(buildQuery());
		put("stepNodes",list);
		if(list != null && list.size() > 0 ){
			put("size",list.size());
		}else{
			put("size",0);
		}
		put("workflows",entityDao.getAll(Workflow.class));
		return forward();
	}
	protected EntityQuery buildQuery(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Workflow node = (Workflow) session.getAttribute("workflow");
		EntityQuery query = new EntityQuery(StepNode.class,"stepNode");
		Long wid = getLong("stepNode.workflow.id");
		if(wid == null){
			query.add(new Condition("stepNode.workflow=:worf",node));
		}
		
		QueryHelper.populateConditions(query);
		query.setLimit(getPageLimit());
		String orderByPras = get("orderBy");
		if (StringUtils.isEmpty(orderByPras)) {
			orderByPras = "stepNode.order";
		}
		query.addOrder(Order.parse(orderByPras));
		return query;
	}
	
	public String info(){
		StepNode stepNode = (StepNode) entityDao.get(StepNode.class, getLong("stepNode.id"));
		if(stepNode != null){
			put("stepNode",stepNode);
		}
		return forward();
	}
	public String remove(){
		String StepNodeId = get("stepNode.ids");
		String[] ids = StepNodeId.split(",");
		try {
			for(int i=0;i<ids.length;i++){
				StepNode stepNode = (StepNode) entityDao.get(StepNode.class, Long.parseLong(ids[i]));
				OqlBuilder<StepNodeConstraint> oql = OqlBuilder.from(StepNodeConstraint.class,"s");
				oql.where("s.stepNode.id=:sid",stepNode.getId());
				List cs = entityDao.search(oql);
				if(cs != null && cs.size() > 0){
					entityDao.remove(cs);
				}
				entityDao.remove(stepNode);
			}
		} catch (NumberFormatException e) {
			return redirect("search", "info.delete.failure");
		}
		return redirect("search", "info.remove.success");
	}
	
	public String up(){
		String stepNodeid = get("stepNode.id");
		StepNode stepNode = (StepNode) entityDao.get(StepNode.class, Long.valueOf(stepNodeid));
		OqlBuilder<StepNode> oql = OqlBuilder.from(StepNode.class,"s");
		oql.where("s.order=:sorder",(Integer.parseInt(stepNode.getOrder())-1)+"");
		oql.where("s.workflow=:workf",stepNode.getWorkflow());
		List list = entityDao.search(oql);
		StepNode stepNode1 = null;
		if(list != null && list.size() > 0){
			stepNode1 = (StepNode) list.get(0);
			stepNode.setOrder(Integer.parseInt(stepNode.getOrder())-1+"");
			stepNode1.setOrder(Integer.parseInt(stepNode1.getOrder())+1+"");
			entityDao.saveOrUpdate(stepNode);
			entityDao.saveOrUpdate(stepNode1);
		}else{
			if((Integer.parseInt(stepNode.getOrder())-1) != 0){
				stepNode.setOrder(Integer.parseInt(stepNode.getOrder())-1+"");
				entityDao.saveOrUpdate(stepNode);
			}
		}
		
		return redirect("search", "info.save.success");
	}
	public String down(){
		String stepNodeId = get("stepNode.id");
		StepNode stepNode = (StepNode) entityDao.get(StepNode.class, Long.valueOf(stepNodeId));
		OqlBuilder<StepNode> oql = OqlBuilder.from(StepNode.class,"s");
		oql.where("s.order=:sorder",(Integer.parseInt(stepNode.getOrder())+1)+"");
		oql.where("s.workflow=:workf",stepNode.getWorkflow());
		List list = entityDao.search(oql);
		StepNode stepNode1 = null;
		if(list != null && list.size() > 0){
			stepNode1 = (StepNode) list.get(0);
			stepNode.setOrder(Integer.parseInt(stepNode.getOrder())+1+"");
			stepNode1.setOrder(Integer.parseInt(stepNode1.getOrder())-1+"");
			entityDao.saveOrUpdate(stepNode);
			entityDao.saveOrUpdate(stepNode1);
		}else{
			OqlBuilder<StepNode> oql2 = OqlBuilder.from(StepNode.class,"s");
			oql2.where("s.workflow=:workf",stepNode.getWorkflow());
			List ls = entityDao.search(oql2);
			if((Integer.parseInt(stepNode.getOrder())+1) <= ls.size()){
				stepNode.setOrder(Integer.parseInt(stepNode.getOrder())+1+"");
				entityDao.saveOrUpdate(stepNode);
			}
		}
		
		return redirect("search", "info.save.success");
	}
}