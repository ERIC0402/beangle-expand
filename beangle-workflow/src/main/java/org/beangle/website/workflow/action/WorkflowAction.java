package org.beangle.website.workflow.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.ems.security.Category;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.GroupMember;
import org.beangle.ems.security.User;
import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.EntityQuery;
import org.beangle.struts2.helper.QueryHelper;
import org.beangle.website.common.action.BaseCommonAction;
import org.beangle.website.workflow.model.StepNode;
import org.beangle.website.workflow.model.StepNodeConstraint;
import org.beangle.website.workflow.model.StepNodeDecision;
import org.beangle.website.workflow.model.Workflow;


public class WorkflowAction extends BaseCommonAction {

	@Override
	protected String getEntityName() {
		return Workflow.class.getName();
	}

	public String index() {
		// List workflows = entityDao.get(entity, parameterMap)
//		List workflows = entityDao.getAll(Workflow.class);
//		put("workflows", workflows);
		return forward();
	}

//	public String list() {
//		List workflows = entityDao.getAll(Workflow.class);
//		put("workflows", workflows);
//		return forward();
//	}

	public String save() {
		Workflow workflow = (Workflow) populateEntity(Workflow.class, "workflow");
		String id = get("workflow.id");
		List<Group> groups = entityDao.get(Group.class,
				getAll("roleIds", Long.class));
		workflow.getRoles().clear();
		workflow.getRoles().addAll(groups);
		String code = workflow.getCode();
		List list = entityDao
				.searchHQLQuery("from org.beangle.website.workflow.model.Workflow w where w.code='"
						+ code + "'");
		if (list != null && list.size() > 0 && (id == null || id == "")) {
			// return redirect("list","info.code.exist");
			put("workflow", workflow);
			put("roles", entityDao.getAll(Group.class));
			return forward("form", "info.code.exist");
		} else {
			entityDao.saveOrUpdate(workflow);
			return redirect("search", "info.save.success");
		}
	}

	public String edit() {
		Workflow workflow = (Workflow) getEntity(Workflow.class, "workflow");
		put("workflow", workflow);
		put("roles", entityDao.getAll(Group.class));
		return forward();
	}

	public String search() {
		put("workflows", entityDao.search(buildQuery()));
		return forward();
	}

	protected EntityQuery buildQuery() {
		User user = entityDao.get(User.class, getUserId());
		Set<Group> gs = CollectUtils.newHashSet();
		for (Iterator<GroupMember> it = user.getGroups().iterator(); it
				.hasNext();) {
			GroupMember gm = it.next();
			gs.add(gm.getGroup());
		}
		EntityQuery query = new EntityQuery(Workflow.class, "workflow");
		QueryHelper.populateConditions(query);
		Category category = (Category) entityDao.get(Category.class, 3L);
		if (!user.getCategories().contains(category) && gs != null
				&& gs.size() > 0) {
			query.join("workflow.roles", "ro");
			query.add(new Condition("ro in (:roles)", gs));
		}
		query.setLimit(getPageLimit());
		String orderByPras = get("orderBy");
		if (StringUtils.isEmpty(orderByPras)) {
			orderByPras = "workflow.code";
		}
		query.addOrder(Order.parse(orderByPras));
		return query;
	}

	public String info() {
		Workflow workflow = (Workflow) entityDao.get(Workflow.class,
				getLong("workflow.id"));
		if (workflow != null) {
			put("workflow", workflow);
		}
		return forward();
	}

	public String remove() {
		String workflowId = get("workflow.ids");
		String[] ids = workflowId.split(",");
		for (int i = 0; i < ids.length; i++) {
			Workflow workflow = (Workflow) entityDao.get(Workflow.class,
					Long.parseLong(ids[i]));
			Map m = new HashMap();
			m.put("workflow.id", workflow.getId());
			List cs = null;
			List list = entityDao.get(StepNode.class, m);
			for (Iterator it = list.iterator(); it.hasNext();) {
				StepNode node = (StepNode) it.next();
				m.clear();
				m.put("stepNode.id", node.getId());
				if (node.getType() == StepNode.CONSTRAINT_TYPE
						|| node.getType() == StepNode.DECISION_CONSTRAINT_TYPE) {
					cs.add(entityDao.get(StepNodeConstraint.class, m));
				}
				if (node.getType() == StepNode.DECISION_TYPE
						|| node.getType() == StepNode.DECISION_CONSTRAINT_TYPE) {
					cs.add(entityDao.get(StepNodeDecision.class, m));
				}
			}
			if (cs != null && cs.size() > 0) {
				entityDao.remove(cs);
			}
			if (list != null && list.size() > 0) {
				entityDao.remove(list);
			}
			entityDao.remove(workflow);
		}
		return redirect("search", "info.remove.success");
	}
	
	/**
	 * 一键设置流程
	 * @return
	 */
	public String addWorkflowAtOne(){
		put("users",findAllActivationUser());
		return forward();
	}
	
	/**
	 * 一键设置流程保存
	 * @return
	 */
	public String saveWorkflowAtOne(){
		//保存流程
		final Workflow workflow = populateEntity(Workflow.class,getShortName());
		entityDao.saveOrUpdate(workflow);
		
		//保存流程步骤
		final List<Integer> orders = CollectUtils.newArrayList();
		orders.add(1);
		List<StepNode> stepNodes = getAllEntity(StepNode.class, "stepNode", new GetAllEntityInvoker<StepNode>() {
			public void doSth(StepNode stepNode) {
				stepNode.setWorkflow(workflow);
				stepNode.setType(StepNode.NOMAL_TYPE);
				stepNode.setOrder(orders.get(0) + "");
				stepNode.setLogFormat("<#>");
				orders.set(0, orders.get(0) + 1);
			}
		});
		entityDao.saveOrUpdate(stepNodes);
		return redirect("search","保存成功");
	}

}
