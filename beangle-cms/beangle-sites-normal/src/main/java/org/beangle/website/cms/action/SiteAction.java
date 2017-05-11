package org.beangle.website.cms.action;

import java.util.List;

import org.beangle.ems.security.Group;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.cms.model.Column;
import org.beangle.website.cms.model.Site;

import org.beangle.website.system.model.DictData;
import org.beangle.website.system.model.DictDataUtils;
import org.beangle.website.workflow.model.Workflow;

/**
 * 站点管理
 * 
 * @author CHII
 * 
 */
public class SiteAction extends SiteCommonAction {

	@Override
	protected String getEntityName() {
		return Site.class.getName();
	}
	
	@Override
	protected void editSetting(Entity<?> entity) {
		Site site = (Site) entity;
		put("site",site);
		put("workflows", entityDao.getAll(Workflow.class));
		put("groups",getUserGroups(getCurrentUser()));
	}

	@Override
	protected String saveAndForward(Entity<?> entity) {
		Site site = (Site) populateEntity(Site.class, "site");
		List<Group> groups = entityDao.get(Group.class,
				getAll("roleIds", Long.class));
		site.getGroups().clear();
		site.getGroups().addAll(groups);
		// 断送根路径是否存在、站点名是否存在
		if (exist(site, "name", site.getName())) {
			put("site", site);
			return forward("edit", "站点名重复");
		}
		if (exist(site, "basePath", site.getBasePath())) {
			put("site", site);
			return forward("edit", "根路径重复");
		}
		boolean newSite = site.getId() == null ? true : false;
		String message = (newSite ? "新建" : "修改") + "了一个站点" + site.getName();
		saveOperationLog("站点管理", message);

		entityDao.saveOrUpdate(site);
		Column column = null;
		if (!newSite) {
			OqlBuilder<Column> oql = OqlBuilder.from(Column.class,"c");
			oql.where("site.id = :siteId", site.getId());
			oql.where("type.id = :typeId",DictDataUtils.COLUMN_TYPE_SITE);
			List<Column> list = entityDao.search(oql);
			if (!list.isEmpty()) {
				column = list.get(0);
			}
		}
		if (newSite || column == null) {
			column = new Column();
			column.setType(entityDao.get(DictData.class, DictDataUtils.COLUMN_TYPE_SITE));
			column.setSite(site);
			column.setOrders(getNextOrders(null));
		}
		column.setEnabled(Boolean.TRUE);
		column.setNode(true);
		column.setWorkflow(site.getWorkflow());
		column.setDoesImpl(false);
		column.setName(site.getName());
		entityDao.saveOrUpdate(column);
		return redirect("search", "info.save.success");
	}
}
