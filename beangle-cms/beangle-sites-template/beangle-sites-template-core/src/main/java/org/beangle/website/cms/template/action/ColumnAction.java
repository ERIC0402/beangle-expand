package org.beangle.website.cms.template.action;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.beangle.model.query.builder.OqlBuilder;

import org.beangle.website.cms.action.SiteCommonAction;
import org.beangle.website.cms.model.Column;
import org.beangle.website.cms.model.ExtProperty;
import org.beangle.website.cms.model.OperationLog;
import org.beangle.website.cms.model.Site;
import org.beangle.website.workflow.model.Workflow;

/**
 * 栏目管理
 * 
 * @author DONHKO
 * 
 */
public class ColumnAction extends SiteCommonAction {

	public String index() {
		Set<Site> sites = new LinkedHashSet<Site>();
		put("cid", getCurrentCategoryId());
		Long siteId = getLong("siteId");
		if(siteId != null){
			//创建站点向导时添加栏目
			Site site = entityDao.get(Site.class, siteId);
			sites.add(site);
			put("hideSites", true);
			put("site", site);
		}else{
			sites.addAll(getSitesByCurrentUser());
		}
		if (sites != null && sites.size() > 0) {
			Site site = sites.iterator().next();
			put("columns", getColumnsBySiteId(site.getId()));
		} else {
			put("columns", new ArrayList<Column>());
		}
		put("sites", sites);
		return forward();
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

		put("columns", getColumnsBySiteId(siteId));
		return forward("leftColumnTree");
	}

	/**
	 * 判断显示集合或修改页面
	 * 
	 * @return
	 */
	public String listandform() {
		Integer enabledRefresh = getInteger("enabledRefresh");
		put("enabledRefresh", enabledRefresh);
		Long id = getLong("column.id");
		if(id == null){
			id = getLong("nodeid");
		}
		if (id == null) {
			List<Column> srcs = getAllSiteRootColumns();
			if ((srcs != null && srcs.size() > 0)) {
				id = srcs.get(0).getId();
			} else {
				id = 1L;
			}
		}
		Long siteId = getLong("siteId");
		Integer all = getInteger("all");
		// 获取该栏目下的子栏目
		OqlBuilder<Column> oql = OqlBuilder.from(Column.class, "c");
		oql.where("c.columns.id=:columnsId", id);
		if (all == null || all == 0) {
			oql.where("c.enabled=1");
		}
		oql.orderBy("enabled desc");
		oql.orderBy("c.orders");
		oql.limit(getPageLimit());
		List<Column> list = entityDao.search(oql);

		put("columns", list);
		Column column = (Column) entityDao.get(Column.class, id);
		put("column", column);

		String self = get("self");

		if (siteId == null || siteId == 0) {
			if (column.getSite() != null) {
				siteId = column.getSite().getId();
			}
		}
		put("siteId", siteId);
		if (column.getColumns() == null) {
			return forward("list");
		} else {
			if (list.size() > 0 && !"yes".equals(self)) {
				return forward("list");
			} else {
				// 网页面上传送信息
				putEditDatas(siteId);
				put("siteId", siteId);
				Long nodeid = getLong("nodeid");
				if (column.getId() != null && column.getColumns() != null) {
					nodeid = column.getColumns().getId();
				}
				put("nodeid", nodeid);// 默认上级栏目
				return forward("form");
			}
		}
	}

	private void putEditDatas(Long siteId) {
		// 访问权限
		put("accesss", getDictDataByDictType(7L));

		// 栏目类型
		put("types", getDictDataByDictType(5L));

		// 内容来源
		put("contents", getDictDataByDictType(6L));

		// 扩展属性来源
		put("extpropertys", getDictDataByDictType(9L));

		// 新闻类型
		put("newsTypes", getDictDataByDictType(182L));

		// 上级栏目
		OqlBuilder<Column> queryColumn = OqlBuilder.from(Column.class, "c");
		if (siteId == null) {
			queryColumn.where("c.type.id=141");
		} else {
			queryColumn.where("c.site.id=:siteId", siteId);
		}
		queryColumn.orderBy("c.orders");
		put("columns", entityDao.search(queryColumn));
		// 关联流程
		put("workflows", entityDao.getAll(Workflow.class));

		Site site = entityDao.get(Site.class, siteId);
		if(site.getTemplateGroup() != null){
			// 显示模板
			put("templates", entityDao.searchHQLQuery("from org.beangle.website.cms.model.Template t where t.group.id = ?", site.getTemplateGroup().getId()));
		}
	}

	public String edit() {
		Long nodeid = getLong("nodeid");
		Long siteId = getLong("siteId");
		Column column = (Column) getEntity(Column.class, "column");

		Set<Site> sites = new LinkedHashSet<Site>();
		if (column.getId() != null) {
			nodeid = column.getColumns().getId();
		}
		if (siteId == null || siteId == 0) {
			if (column.getSite() != null) {
				siteId = column.getSite().getId();
			}
		}
		// 网页面上传送信息
		putEditDatas(siteId);
		put("sites", sites);
		put("nodeid", nodeid);// 默认上级栏目
		put("siteId", siteId);
		put("column", column);
		return forward();
	}

	public String save() {
		Column column = (Column) populateEntity(Column.class, "column");
		// 关联栏目
		List<Column> columns = entityDao.get(Column.class, getAll("columnIds", Long.class));
		column.getRelationColumns().clear();
		column.getRelationColumns().addAll(columns);
		// 扩展属性
		List<ExtProperty> propertys = entityDao.get(ExtProperty.class, getAll("propertyIds", Long.class));
		column.getExtpropertys().clear();
		column.getExtpropertys().addAll(propertys);
		if ("0".equals(get("column.site.id"))) {
			Site site = (Site) entityDao.searchHQLQuery("from Site where name ='" + column.getName() + "'").get(0);
			column.setSite(site);
			column.setNode(true);
		} else {
			Site site = (Site) entityDao.get(Site.class, getLong("column.site.id"));
			column.setSite(site);
			// column.setNode(false);
		}
		Long parentid = getLong("column.columns.id");
		if (parentid != null) {
			Column parent = (Column) entityDao.get(Column.class, parentid);
			boolean child = isChildColumn(parent, column);
			if (column.getOrders() == null || column.getOrders() == "" || !child) {
				column.setOrders(getNextOrders(parent));
			}
		}

		entityDao.saveOrUpdate(column);

		Long id = column.getColumns().getId();
		Column parent = (Column) entityDao.get(Column.class, id);
		parent.setNode(true);
		entityDao.saveOrUpdate(parent);
		// this.id = id;
		OperationLog ol = new OperationLog(getCurrentUser(), "栏目管理-新建栏目", new Timestamp(System.currentTimeMillis()), getRemoteAddr(), "新建了一个栏目" + column.getName());
		entityDao.saveOrUpdate(ol);
		return redirect("listandform", "info.save.success", "siteId=" + column.getSite().getId());
	}

	private boolean isChildColumn(Column parent, Column column) {
		String po = parent.getOrders();
		String co = column.getOrders();
		if (StringUtils.isNotEmpty(po) && StringUtils.isNotEmpty(co)) {
			if (co.length() - po.length() == 2 && po.equals(co.substring(0, po.length()))) {
				return true;
			}
		}
		return false;
	}

	public void checkOrder() {
		String order = get("order");
		List<Column> list = entityDao.searchHQLQuery("from " + Column.class + " where orders='" + order + "'");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=utf-8");
		try {
			if (list.size() == 0) {
				response.getWriter().print("OK");
			} else {
				response.getWriter().print("NO");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String Column() {
		Long myid = null;
		if (getLong("id") != null) {
			System.out.println("id");
			myid = getLong("id");
		}
		if (getLong("nodeid") != null) {
			System.out.println("nodeid");
			myid = getLong("nodeid");
		}

		Column column = (Column) entityDao.get(Column.class, myid);
		if (myid.equals(1L)) {
			put("defaultId", 0);// 默认站点
		} else {
			put("defaultId", column.getSite().getId());// 默认站点
		}

		Set<Site> sites = new LinkedHashSet<Site>();
		put("sites", sites);// 当前用户管理的站点

		// List Column=entityDao.getAll(Column.class);
		// put("Column",Column);
		return forward("Columnindex");
	}

	// private List<Column> getColumn(Long pid) {
	// OqlBuilder<Column> oql = OqlBuilder.from(Column.class,"c");
	// oql.where("c.columns.id = (:cid)",pid);
	// oql.where("c.enabled=1");
	// oql.orderBy("c.orders");
	// return entityDao.search(oql);
	// }

	/**
	 * 栏目删除
	 * 
	 * @return
	 */
	public String remove() {
		Long columnId = getLong("column.id");
		Column cols = (Column) entityDao.get(Column.class, columnId);
		Column parent = cols.getColumns();
		cols.setSite(null);
		try {
			entityDao.remove(cols);
			OqlBuilder<Column> oql = OqlBuilder.from(Column.class, "c");
			oql.where("c.columns.id=:parentId", parent.getId());
			List<Column> list = entityDao.search(oql);
			if (list == null || list.size() < 1) {
				parent.setNode(false);
				entityDao.saveOrUpdate(parent);
			}
		} catch (Exception e) {
			return redirect("listandform", "info.delete.failure");
		}
		OperationLog ol = new OperationLog(getCurrentUser(), "栏目管理-删除栏目", new Timestamp(System.currentTimeMillis()), getRemoteAddr(), "删除了一个栏目" + cols.getName());
		entityDao.saveOrUpdate(ol);
		return redirect("listandform", "info.delete.success");
	}

	/**
	 * 向上移动栏目
	 * 
	 * @return
	 */
	public String up() {
		Long cid = getLong("column.id");
		Column column = (Column) entityDao.get(Column.class, cid);
		List<Column> cols = getAllChildColumns(column.getColumns());
		if (cols != null && cols.size() > 1) {
			for (int i = 0; i < cols.size(); i++) {
				Column col = (Column) cols.get(i);
				if (column.getId().equals(col.getId())) {
					if (i != (cols.size() - 1)) {
						String orders = column.getOrders();
						Column up = (Column) cols.get(i + 1);

						List<Column> ups = entityDao.searchHQLQuery("from org.beangle.website.cms.model.Column c where c.orders like '" + up.getOrders() + "%' and c.orders !='" + up.getOrders()
								+ "'");
						List<Column> toUps = entityDao.searchHQLQuery("from org.beangle.website.cms.model.Column c where c.orders like '" + column.getOrders() + "%' and c.orders !='"
								+ column.getOrders() + "'");
						setOrdersByColumn(ups, orders);
						setOrdersByColumn(toUps, up.getOrders());
						column.setOrders(up.getOrders());
						up.setOrders(orders);
						entityDao.saveOrUpdate(up);
						entityDao.saveOrUpdate(column);
						return redirect("listandform", "move.up.success");
					}
				}
			}
		}
		return redirect("listandform", "can.not.move");
	}

	/**
	 * 为栏目设置排序
	 * 
	 * @param Column
	 *            要设置排序的栏目集合
	 * @param order
	 *            要设置的排序
	 */
	private void setOrdersByColumn(List<Column> columns, String order) {
		if (columns != null && columns.size() > 0) {
			for (Iterator<Column> it = columns.iterator(); it.hasNext();) {
				Column column = it.next();
				String end = column.getOrders().substring((order.length()), column.getOrders().length());
				column.setOrders(order + end);
				entityDao.saveOrUpdate(column);
			}
		}
	}

	/**
	 * 向下移动栏目
	 * 
	 * @return
	 */
	public String down() {
		Long cid = getLong("column.id");
		Column column = (Column) entityDao.get(Column.class, cid);
		List<Column> cols = getAllChildColumns(column.getColumns());
		if (cols != null && cols.size() > 1) {
			for (int i = 0; i < cols.size(); i++) {
				Column col = (Column) cols.get(i);
				if (column.getId().equals(col.getId())) {
					if (i != 0) {
						String orders = column.getOrders();
						Column down = (Column) cols.get(i - 1);
						List<Column> downs = entityDao.searchHQLQuery("from org.beangle.website.cms.model.Column c where c.orders like '" + down.getOrders() + "%' and c.orders !='"
								+ down.getOrders() + "'");
						List<Column> toDowns = entityDao.searchHQLQuery("from org.beangle.website.cms.model.Column c where c.orders like '" + column.getOrders() + "%' and c.orders !='"
								+ column.getOrders() + "'");
						setOrdersByColumn(downs, orders);
						setOrdersByColumn(toDowns, down.getOrders());
						column.setOrders(down.getOrders());
						down.setOrders(orders);
						entityDao.saveOrUpdate(down);
						entityDao.saveOrUpdate(column);
						return redirect("listandform", "move.down.success");
					}
				}
			}
		}
		return redirect("listandform", "can.not.move");
	}

}
