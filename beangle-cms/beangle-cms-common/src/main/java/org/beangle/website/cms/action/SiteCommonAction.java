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

public class SiteCommonAction extends CommonAction {

	@Override
	protected String getEntityName() {
		return Site.class.getName();
	}

	
	/**
	 * 获取所有子栏目
	 * 
	 * @param column
	 *            栏目
	 * @return
	 */
	protected List<Column> getAllChildColumns(Column column) {
		OqlBuilder<Column> oql = OqlBuilder.from(Column.class, "c");
		if (column == null) {
			oql.where("c.columns is null");
		} else {
			oql.where("c.columns = (:columns)", column);
		}
		oql.orderBy("c.orders desc");
		return entityDao.search(oql);
	}

	/**
	 * 根据父栏目获取子栏目的下一个序号
	 * 
	 * @param column
	 *            父栏目
	 * @return
	 */
	public String getNextOrders(Column column) {
		List<Column> cls = getAllChildColumns(column);
		String parentOrders = column == null ? "" : column.getOrders();
		String orders = "";
		if (cls != null && cls.size() > 0) {
			Column c = (Column) cls.get(0);
			String corders = c.getOrders();
			String end = corders.substring((corders.length() - 2), corders.length());
			int max = Integer.parseInt(end) + 1;
			if (max < 10) {
				orders = parentOrders + "0" + max;
			} else {
				orders = parentOrders + max;
			}
		} else {
			orders = parentOrders + "01";
		}
		return orders;
	}

	@Override
	protected void editSetting(Entity<?> entity) {
		Site site = (Site) entity;
		if(site.getWorkflow() == null){
			put("workFlowId", 21L);
		}
		put("site", site);
		put("workflows", entityDao.getAll(Workflow.class));
		put("groups", getUserGroups(getCurrentUser()));
	}

	@Override
	protected String saveAndForward(Entity<?> entity) {
		Site site = (Site) populateEntity(Site.class, "site");
		List<Group> groups = entityDao.get(Group.class, getAll("roleIds", Long.class));
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
			OqlBuilder<Column> oql = OqlBuilder.from(Column.class, "c");
			oql.where("site.id = :siteId", site.getId());
			oql.where("type.id = :typeId", DictDataUtils.COLUMN_TYPE_SITE);
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
