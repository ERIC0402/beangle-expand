package org.beangle.website.workflow.action;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.Order;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.query.builder.EntityQuery;
import org.beangle.struts2.helper.QueryHelper;
import org.beangle.website.workflow.model.Module;
import org.beangle.website.workflow.model.Workflow;


public class ModuleAction extends SecurityActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5143377181359037895L;
	public String index(){
		List modules = entityDao.getAll(Module.class);
		put("modules",modules);
		put("workflows",entityDao.getAll(Workflow.class));
		return forward();
	}
	public String list(){
		List modules = entityDao.getAll(Module.class);
		put("modules",modules);
		return forward();
	}
	public String save(){
		Module module = (Module) populateEntity(Module.class,"module");
		String code = get("module.code");
		String name = get("module.name");
		if(!code.equals(module.getCode())){
			module.setCode(code);
		}
		if(!name.equals(module.getName())){
			module.setName(name);
		}
		String workflowId = get("workflowIds");
		if(workflowId != ""){
			String[] workflowIds = workflowId.split(",");
			Set set = new HashSet();
			for(int i=0;i<workflowIds.length;i++){
				Workflow workflow = (Workflow) entityDao.get(Workflow.class, Long.parseLong(workflowIds[i]));
				set.add(workflow);
			}
			module.setWorkflows(set);
		}
		entityDao.saveOrUpdate(module);
		return redirect("search","info.save.success");
	}
	public String edit(){
		Module module = (Module) getEntity(Module.class,"module");
		put("module",module);
		put("workflows",entityDao.getAll(Workflow.class));
		return forward();
	}
	public String search(){
		put("modules",entityDao.search(buildQuery()));
		return forward();
	}
	protected EntityQuery buildQuery(){
		EntityQuery query = new EntityQuery(Module.class,"module");
		QueryHelper.populateConditions(query);
		query.setLimit(getPageLimit());
		String orderByPras = get("orderBy");
		if (StringUtils.isEmpty(orderByPras)) {
			orderByPras = "module.code";
		}
		query.addOrder(Order.parse(orderByPras));
		return query;
	}
	
	public String info(){
		Module module = (Module) entityDao.get(Module.class, getLong("module.id"));
		if(module != null){
			put("module",module);
		}
		return forward();
	}
	public String delete(){
		String moduleId = get("module.id");
		String[] ids = moduleId.split(",");
		for(int i=0;i<ids.length;i++){
			Module module = (Module) entityDao.get(Module.class, Long.parseLong(ids[i]));
			entityDao.remove(module);
		}
		return redirect("search", "info.delete.success");
	}
}
