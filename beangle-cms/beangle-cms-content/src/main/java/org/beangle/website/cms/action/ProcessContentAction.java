package org.beangle.website.cms.action;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.Order;
import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.EntityQuery;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.convention.route.Action;
import org.beangle.website.cms.action.ContentCommonAction;
import org.beangle.website.cms.model.Column;
import org.beangle.website.cms.model.ColumnContent;
import org.beangle.website.cms.model.Content;
import org.beangle.website.cms.model.ContentExtProperty;
import org.beangle.website.cms.model.ContentOperationLog;
import org.beangle.website.cms.model.ExtProperty;
import org.beangle.website.cms.model.Site;

import org.beangle.website.common.util.SeqStringUtil;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.model.DictDataUtils;
import org.beangle.website.system.model.DictTypeUtils;
import org.beangle.website.workflow.model.Task;
import org.beangle.website.workflow.model.TaskLog;
import org.beangle.website.workflow.model.TaskNode;
import org.beangle.website.workflow.model.Workflow;
import org.beangle.website.workflow.service.WorkflowService;

/**
 * 信息处理
 * 
 * @author DONHKO
 * 
 */
public class ProcessContentAction extends ContentCommonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2252245977471504690L;

	private WorkflowService ws;

	public WorkflowService getWs() {
		return ws;
	}

	public void setWs(WorkflowService ws) {
		this.ws = ws;
	}
	
	@Override
	protected String getShortName() {
		return "cc";
	}
	
	@Override
	public String index(){
		return forward(new Action(this, "search"));
	}

	public String search() {
		OqlBuilder<ColumnContent> oql = OqlBuilder.from(ColumnContent.class,"cc");
		oql.join("cc.task.currentNode.roles", "r");
		oql.where("cc.task.state=:taskState",Task.DOING_STATE);
		oql.where("cc.contentState.code in (:waitProcess)",new String[] {"CONTENT_STATE_WAITAUDIT","CONTENT_STATE_WAITPUBLISH" });
		oql.where("r in (:roles)",getUserGroups(getCurrentUser()));
		oql.where("cc.doesMainColumn=1");
		
		if(isOneSiteAdmin()){
			Set<Site> sites = getSitesByCurrentUser();
			if(sites != null && sites.size() > 0){
				oql.where("cc.column.site in (:sites)",sites);
			}else{
				oql.where("1!=1");
			}
		}else if(isGeneralUser()){
			List<?> columnIds = getColumnsByGroup(getUserGroups(getCurrentUser()));
			if(columnIds != null && columnIds.size() > 0){
				oql.where("cc.column.id in (:columnIds)",columnIds);
			}else{
				oql.where("1!=1");
			}
		}
		
		oql.select("cc.id");
		List<?> ids = entityDao.search(oql);
		OqlBuilder<ColumnContent> query = OqlBuilder.from(ColumnContent.class,"cc");
		if(ids != null && ids.size() > 0){
			query.where("cc.id in (:ids)",ids);
		}else{
			query.where("1!=1");
		}
		
		populateConditions(query);
		query.orderBy(getOrderString("cc.content.draftDate desc"));
		query.limit(getPageLimit());
		put("ccs", entityDao.search(query));
		
		put("states",getDictDataByDictType(DictTypeUtils.CONTENT_STATE));
		return forward();
	}

	public String edit() {
		getSession();
		String forward = putEdit();
		if(StringUtils.isNotEmpty(forward)){
			return forward;
		}
		Integer isRedirect = getInteger("isRedirect");
		put("isRedirect",isRedirect);
		return forward();
	}
	
	protected String putEdit(){
		// 访问权限
		put("accesss", getDictDataByDictType(7L));
		Long contentid = getLong("cc.id");
		ColumnContent c_c = (ColumnContent) entityDao.get(
				ColumnContent.class, contentid);
		Task task = c_c.getTask();
		if (task != null) {
			TaskNode node = task.getCurrentNode();
			boolean bool = isTaskNodeRole(node);
			if (!bool) {
				put("message","您无此权限!");
				return forward("result");
			}
		}
		Content content = c_c.getContent();
		if (content.getAbstracts() != null) {
			int ci = content.getAbstracts().indexOf("&");
			if (ci != -1) {
				content.setAbstracts(content.getAbstracts().replaceAll("&",
						"&amp;"));
			}
		}
		if ("CONTENT_STYLE_TEXT".equals(content.getContentStyle().getCode())) {
			int ci = content.getDetail().indexOf("&");
			if (ci != -1) {
				content.setDetail(content.getDetail().replaceAll("&", "&amp;"));
			}
		}
		Column column = c_c.getColumn();
		Set<ExtProperty> sets = getExtProperties(column);
		putExtProperties(sets);
		
		put("propertys", sets);
		if (sets != null && sets.size() > 0) {
			put("pros", 1);
		} else {
			put("pros", 0);
		}
		put("content", content);
		put("contentStyles", getDictDataByDictType(DictTypeUtils.CONTENT_STYLE));
		
		put("cep", getContentExtProperty(content));
		put("currentDate", new Date());
		put("user",getCurrentUser());
		put("column",column);
		put("cc",c_c);
		return null;
	}

	public String save() {
		Column column = (Column) entityDao.get(Column.class, getLong("column.id"));
		Set<ExtProperty> sets = getExtProperties(column);

		Content content = (Content) populateEntity(Content.class, "content");
		// content.setDetail(get("content.detail"));
		Long ccid = getLong("cc.id");
		ColumnContent cc = new ColumnContent();
		if(ccid != null){
			cc = entityDao.get(ColumnContent.class, ccid);
		}
		if ("61".equals(get("content.contentStyle.id"))) {
			content.setDetail(get("richText"));
		}
		
		entityDao.saveOrUpdate(content);
		
		//保存信息扩展属性
		ContentExtProperty cep =getContentExtProperty(content);
		saveExtProperties(content, column, sets, cep);
		
		//保存操作日志
		saveOperationLog("内容管理-待处理", "修改了信息" + content.getTitle(), content);
		return redirect("edit","","cc.id="+ccid+"&isRedirect=1");
	}

	public String remove() {
		String contentid = get("content.id");
		Long[] ids = SeqStringUtil.transformToLong(contentid);
		Set set = new HashSet();
		for (int i = 0; i < ids.length; i++) {
			ColumnContent cc = (ColumnContent) entityDao.get(
					ColumnContent.class, ids[i]);
			Task task = cc.getTask();
			if (task != null) {
				cc.setContentState((DictData) entityDao.get(DictData.class, DictDataUtils.CONTENT_STATE_DELETE));
				entityDao.saveOrUpdate(cc);
				ws.endTask(task);
				saveOperationLog("内容管理-待处理", "把信息" + cc.getContent().getTitle()
								+ "的状态设为删除并结束了任务", null);
			} else {
				Content content = cc.getContent();
				EntityQuery query = new EntityQuery(ContentOperationLog.class,"log");
				query.add(new Condition("log.contentId.id=:cid",content.getId()));
				entityDao.remove(entityDao.search(query));
				set.add(content);
			}
		}
		entityDao.remove(set);
		return redirect("search", "info.delete.success");
	}

	public String handed(){
		Long contentid = getLong("cc.id");
		ColumnContent c_c = (ColumnContent) entityDao.get(
				ColumnContent.class, contentid);
		Content content = c_c.getContent();
		Column column = c_c.getColumn();
		if (!getUserId().equals(content.getDrafter().getId())) {
			return redirect("list", "info.handed.error");
		}

		String operationName = "[" + column.getName() + "]"
				+ content.getTitle();
		Workflow wf = getWorkflowByColumn(column);
		if (wf == null) {
			return redirect("list", "workflow.not.exist");
		}
		Task task = ws.instance(wf.getId(), operationName);
		c_c.setTask(task);
		if (!"publish".equals(task.getCurrentNode().getEntry())) {
			String[] logs = { getCurrentUser().getFullname(), content.getTitle() };
			String remark = ws.spliceLogRemark(task.getCurrentNode(), logs)
					.get(0).toString();
			ws.writeTaskLog(task.getCurrentNode(), remark, getRemoteAddr());
			ws.changeTaskNode(task, null);
			DictData dic = (DictData) entityDao.get(DictData.class, DictDataUtils.CONTENT_STATE_WAITAUDIT);
			c_c.setContentState(dic);
		} else {
			Date pd = c_c.getPublishDate();
			Date ed = c_c.getEndDate();
			if (pd == null) {
				pd = new Date();
				c_c.setPublishDate(pd);
				c_c.setEndDate(ed);
				entityDao.saveOrUpdate(c_c);
			}
			DictData dic = (DictData) entityDao.get(DictData.class, DictDataUtils.CONTENT_STATE_PUBLISHED);
			c_c.setContentState(dic);

			String[] logs = { getCurrentUser().getFullname(),
					content.getDrafter().getName(), content.getTitle(), "无" };
			String remark = ws.spliceLogRemark(task.getCurrentNode(), logs)
					.get(0).toString();
			ws.writeTaskLog(task.getCurrentNode(), remark, getRemoteAddr());
			ws.changeTaskNode(task, null);
		}
		entityDao.saveOrUpdate(c_c);
		if (!"publish".equals(task.getCurrentNode().getEntry())) {
			return redirect("list", "info.handed.success");
		} else {
			return redirect("list", "info.publish.complete");
		}

	}

	public String submit() {
		Long contentid = getLong("cc.id");
		ColumnContent c_c = (ColumnContent) entityDao.get(
				ColumnContent.class, contentid);
		Task task = c_c.getTask();
		String[] logs = { getCurrentUser().getFullname(), c_c.getContent().getTitle() };
		String remark = ws.spliceLogRemark(task.getCurrentNode(), logs).get(0)
				.toString();
		DictData dic = (DictData)entityDao.get(DictData.class, DictDataUtils.CONTENT_STATE_WAITAUDIT);
		c_c.setContentState(dic);
		entityDao.saveOrUpdate(c_c);
		ws.writeTaskLog(task.getCurrentNode(), remark, getRemoteAddr());
		ws.changeTaskNode(task, null);
		return redirect("list", "info.handed.success");
	}

	public String log() {
		Long id = getLong("cc.id");
		ColumnContent cc = (ColumnContent) entityDao.get(ColumnContent.class, id);
		Task task = cc.getTask();
		if (task == null) {
			return redirect("list", "task.not.exist");
		}
		List<TaskLog> logs = ws.getTaskLogs(task, get("orderBy"));
		put("cc", cc);
		EntityQuery query = new EntityQuery(ContentOperationLog.class, "col");
		query.add(new Condition("col.contentId=:con", cc.getContent()));
		query.addOrder(Order.asc("col.log.time"));
		put("clogs", entityDao.search(query));
		put("logs", logs);
		return forward();
	}

	public String audit() {
		Long id = getLong("cc.id");
		ColumnContent c_c = (ColumnContent) entityDao.get(
				ColumnContent.class, id);
		Task task = c_c.getTask();
		if (task != null) {
			TaskNode node = task.getCurrentNode();
			boolean bool = isTaskNodeRole(node);
			if (!bool) {
				return redirect("list", "no.competence");
			}
		}

		Content content = c_c.getContent();
		if (content.getAbstracts() != null) {
			int ci = content.getAbstracts().indexOf("&");
			if (ci != -1) {
				content.setAbstracts(content.getAbstracts().replaceAll("&",
						"&amp;"));
			}
		}
		Column column = c_c.getColumn();
		Set<ExtProperty> sets = getExtProperties(column);
		put("propertys", sets);
		if (sets != null && sets.size() > 0) {
			put("pros", 1);
		} else {
			put("pros", 0);
		}
		put("content", content);
		put("contentStyles", getDictDataByDictType(DictTypeUtils.CONTENT_STYLE));
		
		put("cep", getContentExtProperty(content));
		put("cc", c_c);
		return forward();
	}

	public String saveAudit() {
		Long id = getLong("cc.id");
		ColumnContent cc = (ColumnContent) entityDao.get(ColumnContent.class, id);
		Integer edit = getInteger("edit");
		if (edit != null && edit == 1) {
			Content content = (Content) populateEntity(Content.class, "content");
			if ("61".equals(get("content.contentStyle.id"))) {
				content.setDetail(get("richText"));
			}
			
			entityDao.saveOrUpdate(content);
			ContentExtProperty cep = getContentExtProperty(content);
			Column column = cc.getColumn();
			saveExtProperties(content, column, getExtProperties(column), cep);
			saveOperationLog("内容管理-待处理", "修改了信息" + content.getTitle(), content);
		}
		
		Content content = cc.getContent();
		int state = getInteger("audit.state");
		String abstracts = get("abstracts");
		if (abstracts == null || abstracts == "") {
			abstracts = "无";
		}
		Task task = cc.getTask();
		if (state == 0) {
			DictData dic = (DictData) entityDao.get(DictData.class, DictDataUtils.CONTENT_STATE_BACK);
			cc.setContentState(dic);

			String[] logs = { getCurrentUser().getFullname(),
					content.getDrafter().getName(), content.getTitle(),
					abstracts };
			String remark = ws.spliceLogRemark(task.getCurrentNode(), logs)
					.get(1).toString();
			ws.writeTaskLog(task.getCurrentNode(), remark, getRemoteAddr());
			ws.changeTaskNode(task, "back");
		} else if (state == 1) {
			DictData dic = (DictData) entityDao.get(DictData.class, DictDataUtils.CONTENT_STATE_WAITPUBLISH);
			cc.setContentState(dic);

			String[] logs = { getCurrentUser().getFullname(),
					content.getDrafter().getName(), content.getTitle(),
					abstracts };
			String remark = ws.spliceLogRemark(task.getCurrentNode(), logs)
					.get(0).toString();
			ws.writeTaskLog(task.getCurrentNode(), remark, getRemoteAddr());
			ws.changeTaskNode(task, null);
		} else {
			DictData dic = (DictData) entityDao.get(DictData.class, DictDataUtils.CONTENT_STATE_END);

			String[] logs = { getCurrentUser().getFullname(),
					content.getDrafter().getName(), content.getTitle(),
					abstracts };
			String remark = ws.spliceLogRemark(task.getCurrentNode(), logs)
					.get(2).toString();
			cc.setContentState(dic);
			ws.writeTaskLog(task.getCurrentNode(), remark, getRemoteAddr());
			ws.endTask(task);
		}
		entityDao.saveOrUpdate(cc);
		put("message","审核完成！");
		return forward("result", "info.audit.complete");
	}

	public String publish() {
		Long contentid = getLong("cc.id");
		ColumnContent c_c = (ColumnContent) entityDao.get(
				ColumnContent.class, contentid);
		Task task = c_c.getTask();
		if (task != null) {
			TaskNode node = task.getCurrentNode();
			boolean bool = isTaskNodeRole(node);
			if (!bool) {
				return redirect("list", "no.competence");
			}
		}

		Content content = c_c.getContent();
		if (content.getAbstracts() != null) {
			int ci = content.getAbstracts().indexOf("&");
			if (ci != -1) {
				content.setAbstracts(content.getAbstracts().replaceAll("&",
						"&amp;"));
			}
		}
		Column column = c_c.getColumn();
		Set<ExtProperty> sets = getExtProperties(column);
		put("propertys", sets);
		if (sets != null && sets.size() > 0) {
			put("pros", 1);
		} else {
			put("pros", 0);
		}
		put("content", content);
		put("contentStyles", getDictDataByDictType(DictTypeUtils.CONTENT_STYLE));
		put("cep", getContentExtProperty(content));
		put("cc", c_c);
		return forward();
	}

	public String savePublish() throws UnsupportedEncodingException,
			FileNotFoundException {
		Long id = getLong("cc.id");
		ColumnContent cc = (ColumnContent) entityDao.get(
				ColumnContent.class, id);
		Integer edit = getInteger("edit");
		if (edit != null && edit == 1) {
			Content content = (Content) populateEntity(Content.class, "content");
			if ("61".equals(get("content.contentStyle.id"))) {
				content.setDetail(get("richText"));
			}
			
			entityDao.saveOrUpdate(content);
			ContentExtProperty cep = getContentExtProperty(content);
			Column column = cc.getColumn();
			saveExtProperties(content, column, getExtProperties(column), cep);
			saveOperationLog("内容管理-待处理", "修改了信息" + content.getTitle(), content);
		}
		
		Date pd = cc.getPublishDate();
		Date ed = cc.getEndDate();
		if (pd == null) {
			pd = new Date();
			cc.setPublishDate(pd);
			cc.setEndDate(ed);
			entityDao.saveOrUpdate(cc);

		}
		Content content = cc.getContent();
		int state = getInteger("publish.state");
		String abstracts = get("abstracts");
		if (abstracts == null || abstracts == "") {
			abstracts = "无";
		}
		Task task = cc.getTask();
		if (state == 0) {
			DictData dic = (DictData) entityDao.get(DictData.class, DictDataUtils.CONTENT_STATE_WAITAUDIT);
			cc.setContentState(dic);

			String[] logs = { getCurrentUser().getFullname(),
					content.getDrafter().getName(), content.getTitle(),
					abstracts };
			String remark = ws.spliceLogRemark(task.getCurrentNode(), logs)
					.get(1).toString();
			ws.writeTaskLog(task.getCurrentNode(), remark, getRemoteAddr());
			ws.changeTaskNode(task, "back");
		} else if (state == 1) {
			DictData dic = (DictData) entityDao.get(DictData.class, DictDataUtils.CONTENT_STATE_PUBLISHED);
			cc.setContentState(dic);

			String[] logs = { getCurrentUser().getFullname(),
					content.getDrafter().getName(), content.getTitle(),
					abstracts };
			String remark = ws.spliceLogRemark(task.getCurrentNode(), logs)
					.get(0).toString();
			ws.writeTaskLog(task.getCurrentNode(), remark, getRemoteAddr());
			ws.changeTaskNode(task, null);
		} else {
			DictData dic = (DictData) entityDao.get(DictData.class, DictDataUtils.CONTENT_STATE_END);
			String[] logs = { getCurrentUser().getFullname(),
					content.getDrafter().getName(), content.getTitle(),
					abstracts };
			String remark = ws.spliceLogRemark(task.getCurrentNode(), logs)
					.get(2).toString();
			cc.setContentState(dic);
			ws.writeTaskLog(task.getCurrentNode(), remark, getRemoteAddr());
			ws.endTask(task);
		}
//		Column xxgk = (Column) entityDao.get(Column.class, 8L);
//		Column lm = cc.getColumn();
//		String orders = xxgk.getOrders();
//		String lmorders = lm.getOrders();
//		if(StringUtils.isNotEmpty(orders) && StringUtils.isNotEmpty(lmorders)){
//			if(lmorders.indexOf(orders) == 0){
//				cc.setSqh(getIndexNo(orders, lm));
//			}
//		}
		entityDao.saveOrUpdate(cc);
		put("message","操作成功！");
		return forward("result", "info.audit.complete");
	}
	
	protected String getIndexNo(String parent,Column column){
		StringBuffer indent = new StringBuffer();        
		indent.append("G022-");
		//学校代码+栏目编号+
		if(column.getOrders().length() == 10){
			indent.append(column.getOrders().substring(6, 10));
			indent.append("000");
		}else if(column.getOrders().length() == 12){
			indent.append(column.getOrders().substring(6, 10));
			indent.append("0");
			indent.append(column.getOrders().substring(10, 12));
		}
		indent.append("-");
		Date indentDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		String year = format.format(indentDate);
		indent.append(year);
		indent.append("-");
		String hql2 = "select count (c) from ColumnContent c where c.columns.orders like '"+parent+"%'";
		List<Object> num = entityDao.searchHQLQuery(hql2);
		//System.out.println("num==="+num.get(0));
		//lsh = 流水号
		StringBuffer lsh = new StringBuffer();        
		 String prefix[]={"","0","00","000"};
		 String str=String.valueOf(num.get(0));
         str=prefix[4-str.length()];
         lsh.append(str);
         lsh.append(String.valueOf(num.get(0)));
         indent.append(lsh.toString());
         return indent.toString();
	}

	public String departmentAudit() {
		Long id = getLong("cc.id");
		ColumnContent c_c = (ColumnContent) entityDao.get(
				ColumnContent.class, id);
		Content content = c_c.getContent();
//		User du = content.getDrafter();
//		User u = getCurrentUser();
//		Department dud = du.getDepartment();
//		Department ud = u.getDepartment();
//		if (!dud.getId().equals(ud.getId())) {
//			return redirect("list", "部门不同");
//		}
		Task task = c_c.getTask();
		if (task != null) {
			TaskNode node = task.getCurrentNode();
			boolean bool = isTaskNodeRole(node);
			if (!bool) {
				return redirect("list", "no.competence");
			}
		}

		if (content.getAbstracts() != null) {
			int ci = content.getAbstracts().indexOf("&");
			if (ci != -1) {
				content.setAbstracts(content.getAbstracts().replaceAll("&",
						"&amp;"));
			}
		}
		Column column = c_c.getColumn();
		Set<ExtProperty> sets = getExtProperties(column);
		
		put("propertys", sets);
		if (sets != null && sets.size() > 0) {
			put("pros", 1);
		} else {
			put("pros", 0);
		}
		put("content", content);
		put("contentStyles", getDictDataByDictType(DictTypeUtils.CONTENT_STYLE));
		
		put("cep", getContentExtProperty(content));
		put("cc", c_c);
		return forward();
	}

	public String saveDepartmentAudit() {
		Long id = getLong("cc.id");
		ColumnContent cc = (ColumnContent) entityDao.get(ColumnContent.class, id);
		Integer edit = getInteger("edit");
		if (edit != null && edit == 1) {
			Content content = (Content) populateEntity(Content.class, "content");
			if ("61".equals(get("content.contentStyle.id"))) {
				content.setDetail(get("richText"));
			}
			
			entityDao.saveOrUpdate(content);
			ContentExtProperty cep = getContentExtProperty(content);
			Column column = cc.getColumn();
			saveExtProperties(content, column, getExtProperties(column), cep);
			saveOperationLog("内容管理-待处理", "修改了信息" + content.getTitle(), content);
		}
		
		Content content = cc.getContent();
		int state = getInteger("audit.state");
		String abstracts = get("abstracts");
		if (abstracts == null || abstracts == "") {
			abstracts = "无";
		}
		Task task = cc.getTask();
		if (state == 0) {
			DictData dic = (DictData) entityDao.get(DictData.class, DictDataUtils.CONTENT_STATE_BACK);
			cc.setContentState(dic);

			String[] logs = { getCurrentUser().getFullname(),
					content.getDrafter().getName(), content.getTitle(),
					abstracts };
			String remark = ws.spliceLogRemark(task.getCurrentNode(), logs)
					.get(1).toString();
			ws.writeTaskLog(task.getCurrentNode(), remark, getRemoteAddr());
			ws.changeTaskNode(task, "back");
		} else if (state == 1) {
			DictData dic = (DictData) entityDao.get(DictData.class, DictDataUtils.CONTENT_STATE_WAITPUBLISH);
			cc.setContentState(dic);

			String[] logs = { getCurrentUser().getFullname(),
					content.getDrafter().getName(), content.getTitle(),
					abstracts };
			String remark = ws.spliceLogRemark(task.getCurrentNode(), logs)
					.get(0).toString();
			ws.writeTaskLog(task.getCurrentNode(), remark, getRemoteAddr());
			ws.changeTaskNode(task, null);
		} else {
			DictData dic = (DictData) entityDao.get(DictData.class, DictDataUtils.CONTENT_STATE_END);
			String[] logs = { getCurrentUser().getFullname(),
					content.getDrafter().getName(), content.getTitle(),
					abstracts };
			String remark = ws.spliceLogRemark(task.getCurrentNode(), logs)
					.get(2).toString();
			cc.setContentState(dic);
			ws.writeTaskLog(task.getCurrentNode(), remark, getRemoteAddr());
			ws.endTask(task);
		}
		entityDao.saveOrUpdate(cc);
		put("message","审核完成！");
		return forward("result", "info.audit.complete");
	}

	public String detail() {
		Long contentoperationLogId = getLong("contentoperationLog.id");
		ContentOperationLog contentoperationLog = (ContentOperationLog) entityDao
				.get(ContentOperationLog.class, contentoperationLogId);
		if (null != contentoperationLog) {
			put("contentoperationLog", contentoperationLog);
		}
		return forward();
	}

	public String auditAndEditContent() {
		String forward = putEdit();
		if(StringUtils.isNotEmpty(forward)){
			return forward;
		}
		return forward();
	}

	public String publishAndEditContent() {
		String forward = putEdit();
		if(StringUtils.isNotEmpty(forward)){
			return forward;
		}
		return forward();
	}

	public String departAuditEditContent() {
		String forward = putEdit();
		if(StringUtils.isNotEmpty(forward)){
			return forward;
		}
		return forward();
	}
}
