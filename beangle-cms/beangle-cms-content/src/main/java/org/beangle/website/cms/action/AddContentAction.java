package org.beangle.website.cms.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.cms.action.ContentCommonAction;
import org.beangle.website.cms.model.Annex;
import org.beangle.website.cms.model.Column;
import org.beangle.website.cms.model.ColumnContent;
import org.beangle.website.cms.model.Content;
import org.beangle.website.cms.model.ExtProperty;
import org.beangle.website.cms.model.Site;

import org.beangle.website.common.util.SeqStringUtil;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.model.DictDataUtils;
import org.beangle.website.system.model.DictTypeUtils;
import org.beangle.website.workflow.model.Task;
import org.beangle.website.workflow.model.Workflow;
import org.beangle.website.workflow.service.WorkflowService;

/**
 * 起草信息
 * @author DONHKO
 * 
 */
public class AddContentAction extends ContentCommonAction {

	private WorkflowService ws;

	public WorkflowService getWs() {
		return ws;
	}

	public void setWs(WorkflowService ws) {
		this.ws = ws;
	}
	
	@Override
	protected String getEntityName() {
		return Content.class.getName();
	}

	public String index() {
		List<Site> ss = new ArrayList<Site>();
		ss.addAll(getSitesByCurrentUser());
		if(ss != null && ss.size() > 0){
			Site site = ss.iterator().next();
			put("columns",findColumnsByUser(getCurrentUser(),site.getId()));
		}else{
			put("columns",new ArrayList<Column>());
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
		//查询站点下的栏目
		put("columns",findColumnsByUser(getCurrentUser(),siteId));
		return forward("leftColumnTree");
	}
	
	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		Long columnId = getLong("column.id");
		Column column = entityDao.get(Column.class, columnId);
		put("columnId",columnId);
		put("states",getDictDataByDictType(DictTypeUtils.CONTENT_STATE));
		OqlBuilder<ColumnContent> oql = OqlBuilder.from(ColumnContent.class,"content");
		Long typeId = column.getType().getId();
		populateConditions(oql);
		if(typeId != null && typeId.equals(16L)){
			oql.where("content.column.id=:columnId",columnId);
			put("enabledAdd",1);
		}else{
			oql.where("content.column.orders like '"+column.getOrders()+"%'");
		}
		if(!isAllSitesAdmin()){
			oql.where("content.content.drafter.id=:drafterId",getUserId());
		}
		oql.orderBy(getOrderString("content.content.draftDate desc"));
		oql.limit(getPageLimit());
		return oql;
	}

	public String edit() {
		getSession();
		put("now", new Date());
		// 访问权限
		put("accesss", getDictDataByDictType(7L));
		Integer isAdd = getInteger("isAdd");
		put("isAdd",isAdd);
		Integer isRedirect = getInteger("isRedirect");
		put("isRedirect",isRedirect);
		Long ccid = getLong("content.id");
		ColumnContent cc = null;
		Content content = new Content();
		Long cid = getLong("column.id");
		if(ccid != null){
			cc = (ColumnContent) entityDao.get(ColumnContent.class, ccid);
			content = cc.getContent();
			put("cc",cc);
			cid = cc.getColumn().getId();
			DictData state = cc.getContentState(); //7：起草 11：退回
			if(state == null || !(state.getId().equals(7L) || state.getId().equals(11L))){
				return redirect("search","只有起草或退回状态的信息才能修改！","column.id="+cc.getColumn().getId());
			}
		}
		
		
		if(cid == null){
			return redirect("search","请选择信息栏目进行起草信息");
		}
		
		Column column = (Column) entityDao.get(Column.class, cid);

		Set<ExtProperty> sets = getExtProperties(column);
		//put扩展属性
		putExtProperties(sets);
		put("propertys", sets);
		
		if (sets != null && sets.size() > 0) {
			put("pros", 1);
		} else {
			put("pros", 0);
		}
	
		Date produceDateToPage = new Date();
		put("produceDateToPage",produceDateToPage);
		
		
		put("content", content);
		put("contentStyles", getDictDataByDictType(DictTypeUtils.CONTENT_STYLE));
		put("currentDate", new Date());
		put("user",getCurrentUser());
		put("column",column);
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
		
		String operationName = "[" + column.getName() + "]"
				+ content.getTitle();
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
//		cc.setPublishDate(new Date());
//		cc.setContentState((DictData) entityDao.get(DictData.class, DictDataUtils.CONTENT_STATE_PUBLISHED));
		entityDao.saveOrUpdate(cc);
		
		Integer isAdd = getInteger("isAdd");
		return redirect("edit","","column.id="+column.getId()+"&isAdd="+isAdd+"&isRedirect=1");
	}
	
}
