package org.beangle.website.cms.action;

import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.User;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.cms.model.Column;
import org.beangle.website.cms.model.Content;
import org.beangle.website.cms.model.ContentOperationLog;
import org.beangle.website.cms.model.GroupColumn;
import org.beangle.website.cms.model.OperationLog;
import org.beangle.website.cms.model.Site;

import org.beangle.website.common.action.FileAction;
import org.beangle.website.system.model.DictDataUtils;
import org.beangle.website.workflow.model.Workflow;

public class CommonAction extends FileAction implements ServletRequestAware, ServletResponseAware {
	
	protected HttpServletRequest request;
	
	protected HttpServletResponse response;

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 获取当前用户可管理的站点
	 * 
	 * @return
	 */
	protected Set<Site> getSitesByCurrentUser() {
		User user = getCurrentUser();
		Set<Group> groups = getUserGroups(user);
		OqlBuilder<Site> oql = OqlBuilder.from(Site.class, "s");
		if (!isAllSitesAdmin()) {
			oql.join("s.groups", "g");
			if (groups != null && groups.size() > 0) {
				oql.where("g in (:groups)", groups);
			} else {
				oql.where("1!=1");
			}
		}
		oql.where("s.enabled=1");
		oql.orderBy("s.code");
		Set<Site> sites = new LinkedHashSet<Site>();
		sites.addAll(entityDao.search(oql));
		return sites;
	}

	/**
	 * 根据站点获取该站点下所有栏目
	 * 
	 * @param siteId
	 *            站点ID
	 * @return
	 */
	protected List<Column> getColumnsBySiteId(Long siteId) {
		OqlBuilder<Column> oql = OqlBuilder.from(Column.class, "c");
		if (siteId != null) {
			oql.where("c.site.id=:siteId", siteId);
		} else {
			oql.where("1!=1");
		}
		oql.where("c.enabled = 1");
		oql.orderBy("c.orders");
		return entityDao.search(oql);
	}
	
	protected List<Column> findColumnsByUser(User user,Long siteId){
		OqlBuilder<Column> oql = OqlBuilder.from(Column.class, "c");
		Set<Group> groups = getUserGroups(user);
		if(!groups.isEmpty()&&!isAllSitesAdmin()){
			oql.join("c.groups", "cg");
			oql.where("cg.group in (:groups)",groups);
		}
		if (siteId != null) {
			oql.where("c.site.id=:siteId", siteId);
		} else {
			oql.where("1!=1");
		}
		oql.orderBy("c.orders");
		return entityDao.search(oql);
	}

	/**
	 * 获取所有有效的站点根目录类型的栏目
	 * 
	 * @return
	 */
	protected List<Column> getAllSiteRootColumns() {
		OqlBuilder<Column> oql = OqlBuilder.from(Column.class, "c");
		oql.where("c.type.id=:typeId", DictDataUtils.COLUMN_TYPE_SITE);
		oql.where("c.enabled=1");
		oql.orderBy("c.orders");
		Long siteId = getLong("siteId");
		if(siteId != null){
			oql.where("c.site.id = :siteId", siteId);
		}
		return entityDao.search(oql);
	}

	/**
	 * 保存操作日志
	 * 
	 * @param menu
	 *            菜单
	 * @param nr
	 *            内容
	 */
	protected void saveOperationLog(String menu, String nr) {
		saveOperationLog(menu, nr, null);
	}

	/**
	 * 保存操作日志（如果content属性不为空，则还要保存信息变更日志）
	 * 
	 * @param menu
	 *            菜单
	 * @param nr
	 *            内容
	 * @param content
	 *            信息
	 */
	protected void saveOperationLog(String menu, String nr, Content content) {
		OperationLog ol = new OperationLog(getCurrentUser(), menu, new Timestamp(System.currentTimeMillis()), getRemoteAddr(), nr);
		entityDao.saveOrUpdate(ol);
		if (content != null) {
			ContentOperationLog col = new ContentOperationLog(ol, content, get("oldDetail"));
			entityDao.saveOrUpdate(col);
		}
	}

	/**
	 * 根据栏目获取流程
	 * 
	 * @param column
	 *            栏目
	 * @return
	 */
	protected Workflow getWorkflowByColumn(Column column) {
		Workflow wf = null;
		if (column.isDoesImpl()) {
			if(column.getColumns() != null){
				wf = getWorkflowByColumn(column.getColumns());
			}else{
				wf = column.getWorkflow();
			}
		} else {
			wf = column.getWorkflow();
		}
		return wf;
	}

	/**
	 * 获取站点根栏目
	 * 
	 * @param column
	 *            栏目
	 * @return
	 */
	protected Column getSiteRootColumn(Column column) {
		Column parent = column.getColumns();
		if ("COLUMN_TYPE_SITE".equals(parent.getType().getCode())) {
			return parent;
		} else {
			return getSiteRootColumn(parent);
		}
	}

	/**
	 * 获取起草权限
	 * 
	 * @param groups
	 *            角色集合
	 * @return
	 */
	protected List<?> getColumnsByGroup(Set<Group> groups) {
		OqlBuilder<GroupColumn> oql = OqlBuilder.from(GroupColumn.class, "g");
		if (groups != null && groups.size() > 0) {
			oql.where("g.group in (:group)", groups);
		} else {
			oql.where("1!=1");
		}
		oql.select("g.column.id");
		return entityDao.search(oql);
	}

}
