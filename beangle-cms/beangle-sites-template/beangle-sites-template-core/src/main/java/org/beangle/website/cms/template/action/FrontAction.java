package org.beangle.website.cms.template.action;

import java.util.Date;
import java.util.List;

import org.beangle.commons.collection.Order;
import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.cms.template.service.ColumnService;
import org.beangle.website.cms.template.service.TemplateService;
import org.beangle.website.cms.template.util.RequestIvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.beangle.website.cms.action.CommonAction;
import org.beangle.website.cms.model.Column;
import org.beangle.website.cms.model.ColumnContent;
import org.beangle.website.cms.model.Content;
import org.beangle.website.cms.model.Site;
import org.beangle.website.cms.model.Statistics;
import org.beangle.website.cms.model.Template;

public class FrontAction extends CommonAction {

	private Logger log = LoggerFactory.getLogger(FrontAction.class);
	private TemplateService templateService;
	protected ColumnService columnService;

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	public void setColumnService(ColumnService columnService) {
		this.columnService = columnService;
	}

	@Override
	public String index() throws Exception {
		Site site = getSite();
		if (site == null) {
			return redirect("allSite", null);
		}
		String path = templateService.createTemplate(site.getIndexTemplate(), site);
		viewColumn();
		return forward(path);
	}

	protected Column getColumn() {
		return getColumn(true);
	}

	protected Column getColumn(final boolean getChild) {
		Column column = getRequestAttribute("column", new RequestIvoker() {

			@SuppressWarnings("unchecked")
			public <T> T getObject() {
				// 根据栏目ID查询
				Long cid = getLong("cid");
				Column c = null;
				if (cid != null) {
					c = entityDao.get(Column.class, getLong("cid"));
				}
				// 根据文档查询
				if (c == null) {
					Long ccid = getLong("ccid");
					ColumnContent cc = null;
					if (ccid != null) {
						cc = entityDao.get(ColumnContent.class, ccid);
					}
					if (cc != null) {
						c = cc.getColumn();
					}
				}
				// 根据站点查询
				if (c == null) {
					c = columnService.getColumn(getSite());
				}
				if (c != null && getChild) {
					c = getChild(c);
				}
				return (T) c;
			}
		});
		return column;
	}

	protected Column getTopColumn(Column column) {
		if (column == null) {
			column = getColumn();
		}
		if (column == null) {
			return null;
		}
		Column topColumn = column;
		if (topColumn.getOrders().length() > 4) {
			String hql = "from " + Column.class.getName() + " where orders like ?";
			Object[] params = new Object[] { topColumn.getOrders().substring(0, 4) };
			List<Column> list = entityDao.searchHQLQuery(hql, params);
			if (list.isEmpty()) {
				topColumn = null;
			} else {
				topColumn = list.get(0);
			}
		} else {
			topColumn = getChild(column);
		}
		return topColumn;
	}

	private Column getChild(Column c) {
		if (c == null) {
			return null;
		}
		if (c.getType().getId() == 142 || c.getOrders().length() < 6) {
			OqlBuilder<Column> query = OqlBuilder.from(Column.class, "c");
			query.where(Condition.eq("c.columns.id", c.getId()));
			query.orderBy(new Order("c.orders"));
			List<Column> list = entityDao.search(query);
			if (!list.isEmpty()) {
				c = getChild(list.get(0));
			}
		}
		return c;
	}

	public String list() throws Exception {
		Site site = getSite();
		Column column = getColumn();
		Template template = null;
		if (column.getColumnTemplate() != null) {
			template = column.getColumnTemplate();
		} else {
			template = site.getListTemplate();
		}
		String path = templateService.createTemplate(template, site);
		viewColumn();
		return forward(path);
	}

	@Override
	public String info() throws Exception {
		Site site = getSite();
		Column column = getColumn();
		Template template = null;
		if (column.getContentTemplate() != null) {
			template = column.getContentTemplate();
		} else {
			template = site.getDetailTemplate();
		}
		String path = templateService.createTemplate(template, site);
		// viewContent();
		return forward(path);
	}

	public String detail() throws Exception {
		Site site = getSite();
		Column column = getColumn();
		String path = templateService.createTemplate(column.getContentTemplate(), site);
		return forward(path);
	}

	public String search() {
		Site site = getSite();
		String path = templateService.createTemplate(site.getSearchTemplate(), site);
		return forward(path);
	}

	protected Site getSite() {
		Site site = (Site) request.getAttribute("site");
		if (site != null) {
			return site;
		}
		Long siteId = getLong("siteId");
		if (siteId != null) {
			site = entityDao.get(Site.class, siteId);
		} else {
			String domain = request.getServerName();
			String hql = "from " + Site.class.getName() + " where domain = ? and enabled = true";
			Object[] params = new Object[] { domain };
			List<Site> list = entityDao.searchHQLQuery(hql, params);
			if (list.isEmpty()) {
				String basePath = get("site");
				String contextPath = request.getContextPath();
				if(contextPath.length() > 1){
					basePath = basePath.replace(contextPath.substring(1) +"/", "");
				}
				log.debug(basePath);
				hql = "from " + Site.class.getName() + " where basePath = ? and enabled = true";
				params = new Object[] { basePath };
				list = entityDao.searchHQLQuery(hql, params);
			}
			if (list.isEmpty()) {
				// String msg = "basePath: " + basePath + " not found";
				// log.error(msg);
				// throw new RuntimeException(msg);
				return null;
			}
			site = list.get(0);
		}
		if (site == null) {
			site = new Site();
		}
		request.setAttribute("site", site);
		return site;
	}

	public String allSite() {
		put("sites", entityDao.searchHQLQuery("from " + Site.class.getName() + " where enabled = true order by code"));
		return forward();
	}

	// private Column getColumn() {
	// Site site = (Site) request.getAttribute("site");
	// if (site != null) {
	// return site;
	// }
	// Column column = get()
	// }
	protected <T> T getRequestAttribute(String name, RequestIvoker ri) {
		@SuppressWarnings("unchecked")
		T t = (T) request.getAttribute(name);
		if (t == null) {
			t = ri.getObject();
			request.setAttribute(name, t);
		}
		return t;
	}

	private void viewColumn() {
		Column Column = getColumn(false);
		if (Column == null) {
			Site site = getSite();
			List<Column> list = entityDao.searchHQLQuery("from " + Column.class.getName() + " where type.id =141 and site.id = ?", new Object[] { site.getId() });
			if (!list.isEmpty()) {
				Column = list.get(0);
			}
		}
		if (Column != null) {
			String hql = "from " + Statistics.class.getName() + " where viewDate = ? and columns.id = ? and content.id is null";
			Date now = new Date();
			Object[] params = new Object[] { now, Column.getId() };
			List<Statistics> list = entityDao.searchHQLQuery(hql, params);
			Statistics statistics = null;
			if (list.isEmpty()) {
				statistics = new Statistics();
				statistics.setViewDate(now);
				statistics.setColumns(Column);
			} else {
				statistics = list.get(0);
			}
			if (statistics.getViews() == null) {
				statistics.setViews(0);
			}
			statistics.setViews(statistics.getViews() + 1);
			entityDao.saveOrUpdate(statistics);
		}
	}

	protected void viewContent() {
		viewContent(null);
	}

	/**
	 * 文档详细访问记录
	 * 
	 * @param cc
	 */
	protected void viewContent(ColumnContent cc) {
		if (cc == null && getLong("ccid") != null) {
			cc = entityDao.get(ColumnContent.class, getLong("ccid"));
		}
		if (cc != null) {
			Content c = cc.getContent();
			String hql = "from " + Statistics.class.getName() + " where viewDate = ? and content.id = ?";
			Date now = new Date();
			Object[] params = new Object[] { now, c.getId() };
			List<Statistics> list = entityDao.searchHQLQuery(hql, params);
			Statistics statistics = null;
			if (list.isEmpty()) {
				statistics = new Statistics();
				statistics.setViewDate(now);
				statistics.setColumns(cc.getColumn());
				statistics.setContent(c);
			} else {
				statistics = list.get(0);
			}
			if (statistics.getViews() == null) {
				statistics.setViews(0);
			}
			statistics.setViews(statistics.getViews() + 1);
			entityDao.saveOrUpdate(statistics);
			if (c.getReadTimes() == null) {
				c.setReadTimes(0L);
			}
			c.setReadTimes(c.getReadTimes() + 1);
			entityDao.saveOrUpdate(c);
		}
	}

	protected <T> T get(Class<T> clazz, Long id) {
		return entityDao.get(clazz, id);
	}

	public String module() throws Exception {
		Site site = getSite();
		Long templateId = getLong("tid");
		if (templateId == null) {
			return null;
		}
		Template template = get(Template.class, templateId);
		String path = templateService.createTemplate(template, site);
		return forward(path);
	}
}
