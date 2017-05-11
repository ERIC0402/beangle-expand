package org.beangle.website.cms.action;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.EntityQuery;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.cms.action.ContentCommonAction;
import org.beangle.website.cms.model.Annex;
import org.beangle.website.cms.model.Column;
import org.beangle.website.cms.model.ColumnContent;
import org.beangle.website.cms.model.Content;
import org.beangle.website.cms.model.ContentExtProperty;
import org.beangle.website.cms.model.ContentOperationLog;
import org.beangle.website.cms.model.ExtProperty;
import org.beangle.website.cms.model.OperationLog;
import org.beangle.website.cms.model.Site;

import org.beangle.website.common.util.SeqStringUtil;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.model.DictDataUtils;
import org.beangle.website.system.model.DictTypeUtils;
import org.beangle.website.workflow.model.Task;
import org.beangle.website.workflow.model.TaskNode;
import org.beangle.website.workflow.model.Workflow;
import org.beangle.website.workflow.service.WorkflowService;

/**
 * 文档管理
 * 
 * @author DONHKO
 * 
 */
public class ContentManageAction extends ContentCommonAction {

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

	public String index() {
		List<Site> ss = new ArrayList<Site>();
		ss.addAll(getSitesByCurrentUser());
		if (ss != null && ss.size() > 0) {
			Site site = ss.iterator().next();
			put("columns", findColumnsByUser(getCurrentUser(), site.getId()));
		} else {
			put("columns", new ArrayList<Column>());
		}
		put("sites", ss);
		return forward("index");
	}

	/**
	 * 左侧根据站点编号查询栏目树
	 * 
	 * @return
	 */
	public String searchTree() {
		Long siteId = getLong("siteId");
		put("siteId", siteId);
		// 查询站点下的栏目
		put("columns", findColumnsByUser(getCurrentUser(), siteId));
		return forward("leftColumnTree");
	}

	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		Long columnId = getLong("column.id");
		Column column = entityDao.get(Column.class, columnId);
		put("columnId", columnId);
		put("states", getDictDataByDictType(DictTypeUtils.CONTENT_STATE));
		OqlBuilder<ColumnContent> oql = OqlBuilder.from(ColumnContent.class, "cc");
		Long typeId = column.getType().getId();
		populateConditions(oql);
//		oql.where("cc.doesMainColumn=1");
		if (typeId != null && typeId.equals(16L)) {
			oql.where("cc.column.id=:columnId", columnId);
			put("enabledAdd", 1);
		} else {
			oql.where("cc.column.orders like '" + column.getOrders() + "%'");
		}
		if (isGeneralUser()) {
			List<?> columnIds = getColumnsByGroup(getUserGroups(getCurrentUser()));
			if (columnIds != null && columnIds.size() > 0) {
				oql.where("cc.column.id in (:columnIds)", columnIds);
			} else {
				oql.where("1!=1");
			}
		} else if (isOneSiteAdmin()) {
			Set<Site> sites = getSitesByCurrentUser();
			if (sites != null && sites.size() > 0) {
				oql.where("cc.column.site in (:sites)", sites);
			} else {
				oql.where("1!=1");
			}
		}
		oql.orderBy(getOrderString("cc.content.draftDate desc"));
		oql.limit(getPageLimit());
		return oql;
	}

	public String edit() {
		getSession();
		put("now", new Date());
		// 访问权限
		put("accesss", getDictDataByDictType(7L));
		Integer isAdd = getInteger("isAdd");
		put("isAdd", isAdd);
		Integer isRedirect = getInteger("isRedirect");
		put("isRedirect", isRedirect);
		Long ccid = getLong("content.id");
		ColumnContent cc = null;
		Content content = new Content();
		Long cid = getLong("column.id");
		if (ccid != null) {
			cc = (ColumnContent) entityDao.get(ColumnContent.class, ccid);
			content = cc.getContent();
			put("cc", cc);
			cid = cc.getColumn().getId();
		}

		if (cid == null) {
			return redirect("search", "请选择信息栏目进行起草信息");
		}

		Column column = (Column) entityDao.get(Column.class, cid);

		Set<ExtProperty> sets = getExtProperties(column);
		// put扩展属性
		putExtProperties(sets);
		put("propertys", sets);

		if (sets != null && sets.size() > 0) {
			put("pros", 1);
		} else {
			put("pros", 0);
		}

		Date produceDateToPage = new Date();
		put("produceDateToPage", produceDateToPage);

		put("content", content);
		put("contentStyles", getDictDataByDictType(DictTypeUtils.CONTENT_STYLE));
		put("currentDate", new Date());
		put("user", getCurrentUser());
		put("column", column);
		return forward();
	}

	public String save() {
		Column column = (Column) entityDao.get(Column.class, getLong("column.id"));
		Set<ExtProperty> sets = getExtProperties(column);
		Long contentId = getLong("content.id");
		Content content = (Content) populateEntity(Content.class, "content");
		if(content.getId() == null){
			content.setIpAddr(getRemoteAddr());
			content.setDraftDate(new Date());
			content.setDrafter(getCurrentUser());
		}
		
		Long ccid = getLong("cc.id");
		ColumnContent cc = new ColumnContent();
		if(ccid != null){
			cc = entityDao.get(ColumnContent.class, ccid);
		}
		cc.setColumn(column);
		cc.setDoesMainColumn(true);
		cc.setContent(content);
		Date publishDate = getDateTime("cc.publishDate");
		if(publishDate != null){
			cc.setPublishDate(publishDate);
		}
		if(ccid == null){
			cc.setContentState((DictData) entityDao.get(DictData.class, DictDataUtils.CONTENT_STATE_DRAFT));
		}
		Set<ColumnContent> set = new HashSet<ColumnContent>();
		set.add(cc);
		content.setColumns(set);
		
		if ("61".equals(get("content.contentStyle.id"))) {
			content.setDetail(get("richText"));
		}
		
		List<Annex> annexs = entityDao.get(Annex.class, SeqStringUtil.transformToLong(get("annexIds")));
		content.getAnnexs().clear();
		content.getAnnexs().addAll(annexs);
		entityDao.saveOrUpdate(content);
		Set<DictData> inds = new HashSet<DictData>();
		DictData dict = (DictData) entityDao.get(DictData.class, DictDataUtils.CONTENT_PROPERTY_INDEX);
		inds.add(dict);
		cc.setContentProperties(inds);
		entityDao.saveOrUpdate(cc);
		//保存信息扩展属性
		saveExtProperties(content, column, sets,null);
		//保存操作日志
		if(contentId != null){
			saveOperationLog("内容管理-新建内容", "修改了信息" + content.getTitle(),content);
		}else{
			saveOperationLog("内容管理-新建内容", "起草一条了信息" + content.getTitle(),content);
		}
		
		Integer isAdd = getInteger("isAdd");
		
		if(isAdd != null && isAdd.intValue() == 1){
			String operationName = "[" + column.getName() + "]" + content.getTitle();
			Workflow wf = getWorkflowByColumn(column);
			if (wf == null) {
				return redirect("list", "workflow.not.exist");
			}
			Task task = ws.instance(wf.getId(), operationName);
			cc.setTask(task);
			if (!"publish".equals(task.getCurrentNode().getEntry())) {
				String[] logs = { getCurrentUser().getName(), content.getTitle() };
				String remark = ws.spliceLogRemark(task.getCurrentNode(), logs)
						.get(0).toString();
				ws.writeTaskLog(task.getCurrentNode(), remark, getRemoteAddr());
				ws.changeTaskNode(task, null);
				cc.setContentState((DictData) entityDao.get(DictData.class, DictDataUtils.CONTENT_STATE_WAITAUDIT));
			} else {
				Date pd = cc.getPublishDate();
				if (pd == null) {
					pd = new Date();
					cc.setPublishDate(pd);
					entityDao.saveOrUpdate(cc);
				}
				cc.setContentState((DictData) entityDao.get(DictData.class, DictDataUtils.CONTENT_STATE_PUBLISHED));
		
				String[] logs = { getCurrentUser().getName(),
						content.getDrafter().getName(), content.getTitle(), "" };
				String remark = ws.spliceLogRemark(task.getCurrentNode(), logs)
						.get(0).toString();
				ws.writeTaskLog(task.getCurrentNode(), remark, getRemoteAddr());
				ws.changeTaskNode(task, null);

	}
	//测试使用
//	cc.setPublishDate(new Date());
//	cc.setContentState((DictData) entityDao.get(DictData.class, DictDataUtils.CONTENT_STATE_PUBLISHED));
	entityDao.saveOrUpdate(cc);
		}
		
		return redirect("edit","","column.id="+column.getId()+"&isAdd="+isAdd+"&isRedirect=1");
	}

	public String remove() {
		String contentid = get("cc.ids");
		Long[] ids = SeqStringUtil.transformToLong(contentid);
		DictData state = entityDao.get(DictData.class, DictDataUtils.CONTENT_STATE_DELETE);
		Set<Content> contents = CollectUtils.newHashSet();
		for (int i = 0; i < ids.length; i++) {
			ColumnContent cc = (ColumnContent) entityDao.get(ColumnContent.class, ids[i]);
			cc.setContentState(state);
			entityDao.saveOrUpdate(cc);
			saveOperationLog("内容中心-内容管理", "把信息" + cc.getContent().getTitle() + "的状态设为删除并结束了任务");
			Task task = cc.getTask();
			if (task != null) {
				ws.endTask(task);
			} else {
				Content content = cc.getContent();
				OqlBuilder<ContentOperationLog> oql = OqlBuilder.from(ContentOperationLog.class, "log");
				oql.where("log.contentId.id=:cid", content.getId());
				entityDao.remove(entityDao.search(oql));
				OqlBuilder<ContentExtProperty> cep = OqlBuilder.from(ContentExtProperty.class, "cep");
				cep.where("cep.content.id=:cid", content.getId());
				entityDao.remove(entityDao.search(cep));
				contents.add(content);
			}
		}
		try {
			entityDao.remove(contents);
		} catch (Exception e) {
			return redirect("search", "删除失败");
		}
		return redirect("search", "info.delete.success");
	}

	public String change() {
		Long ccid = getLong("cc.id");
		ColumnContent c_c = (ColumnContent) entityDao.get(ColumnContent.class, ccid);
		Task task = c_c.getTask();
		if (task == null) {
			return redirect("search", "workflow.change.error");
		}
		put("task", task);
		put("cc", c_c);
		put("taskNodes", ws.getTaskNodeByTaskId(task.getId()));
		return forward();
	}

	public String saveChange() {
		Long tnid = getLong("task.currentNode.id");
		Long tid = getLong("task.id");
		Long cid = getLong("cc.id");
		Task task = ws.getTaskByTaskId(tid);
		TaskNode tn = ws.getTaskNodeByTaskNodeId(tnid);
		ColumnContent cc = (ColumnContent) entityDao.get(ColumnContent.class, cid);
		DictData state = null;
		if ("publish".equals(tn.getEntry())) {
			state = entityDao.get(DictData.class, DictDataUtils.CONTENT_STATE_WAITPUBLISH);
		} else if ("submit".equals(tn.getEntry())) {
			state = entityDao.get(DictData.class, DictDataUtils.CONTENT_STATE_DRAFT);
		} else {
			state = entityDao.get(DictData.class, DictDataUtils.CONTENT_STATE_WAITAUDIT);
		}
		cc.setContentState(state);
		entityDao.saveOrUpdate(cc);

		ws.setCurrentNode(task, tn);
		saveOperationLog("内容中心-内容管理", "干预了任务" + task.getName());
		return redirect("search", "workflow.change.success");
	}

	public String relColumn() {
		ColumnContent cc = getEntity(ColumnContent.class, "cc");
		if (cc.getId() == null) {
			Long contentId = getLong("content.id");
			Content content = entityDao.get(Content.class, contentId);
			cc.setContent(content);
		}

		put("cc", cc);
		put("purviews", getDictDataByDictType(DictTypeUtils.READ_PURVIEW));
		put("properties", getDictDataByDictType(DictTypeUtils.CONTENT_PROPERTY));
		return forward();
	}

	public String saveRelColumn() {
		ColumnContent cc = populateEntity(ColumnContent.class, "cc");
		List<DictData> propertys = entityDao.get(DictData.class, getAll("propertyIds", Long.class));
		cc.getContentProperties().clear();
		cc.getContentProperties().addAll(propertys);
		Date topStartDate = getDateTime("cc.topStartDate");
		if (topStartDate == null && getBool("cc.doesTop")) {
			cc.setTopStartDate(new Date());
		}
		entityDao.saveOrUpdate(cc);
		return redirect("search", "info.save.success");
	}

}
