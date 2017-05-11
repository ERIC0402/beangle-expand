/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.widgets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.collection.page.Page;
import org.beangle.commons.collection.page.SinglePage;
import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.helper.QueryHelper;
import org.beangle.website.cms.template.action.FrontAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.beangle.website.cms.model.Column;
import org.beangle.website.cms.model.ColumnContent;
import org.beangle.website.cms.model.Site;
import org.beangle.website.system.model.DictDataUtils;
import com.opensymphony.xwork2.ActionContext;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * @author CHII
 */
public abstract class WidgetSupport extends FrontAction {
	private static final Logger log = LoggerFactory.getLogger(WidgetSupport.class);
	private static final Configuration cfg;
	private Long id;

	public static Configuration getCfg() {
		return cfg;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public JSONObject getJSONConfig(String config) {
		JSONObject configData = null;
		try {
			configData = JSONObject.fromObject(config);
		} catch (Exception e) {
		}
		if (configData == null || configData.isNullObject()) {
			configData = new JSONObject();
		}
		return configData;
	}

	static {
		cfg = new Configuration();
		try {
			// 指定模板文件从何处加载的数据源，这里设置成一个文件目录。
			ClassTemplateLoader ctl = new ClassTemplateLoader(WidgetSupport.class, "");
			TemplateLoader[] loaders = new TemplateLoader[] { ctl };
			MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
			cfg.setTemplateLoader(mtl);
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			cfg.setDefaultEncoding("UTF-8");
		} catch (Exception ex) {
		}
	}

	public Map<String, Object> getRoot(JSONObject configData) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("Session", ActionContext.getContext().getSession());
		root.put("Parameter", request.getParameterMap());
		root.put("base", request.getContextPath());
		root.put("site", getSite());
		root.put("config", configData);
		request.setAttribute("root", root);
		return root;
	}

	protected String getString(String fileName, Map<String, Object> root) throws IOException, TemplateException {
		Template temp = cfg.getTemplate(getPerfix() + fileName);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		temp.process(root, new OutputStreamWriter(baos));
		return baos.toString();
	}

	@Override
	protected void put(String key, Object value) {
		@SuppressWarnings("unchecked")
		Map<String, Object> root = (Map<String, Object>) request.getAttribute("root");
		root.put(key, value);
	}

	/**
	 * 获取显示内容
	 * 
	 * @param config
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	public String getContent(String config) throws IOException, TemplateException {
		JSONObject configData = getJSONConfig(config);
		Map<String, Object> root = getRoot(configData);
		viewSetting(root, configData);
		String fileName = "/view.ftl";
		return getString(fileName, root);
	}

	/**
	 * 设置页面
	 * 
	 * @return
	 */
	public String getConfig(String config) throws IOException, TemplateException {
		JSONObject configData = getJSONConfig(config);
		Map<String, Object> root = getRoot(configData);
		configSetting(root, configData);
		String fileName = "/config.ftl";
		return getString(fileName, root);
	}

	/**
	 * 读取内容
	 * 
	 * @param root
	 * @param json
	 */
	protected abstract void configSetting(Map<String, Object> root, JSONObject json);

	/**
	 * 读取内容
	 * 
	 * @param root
	 * @param json
	 */
	protected abstract void viewSetting(Map<String, Object> root, JSONObject json);

	public String getPerfix() {
		StringBuilder sb = new StringBuilder();
		String str = getClass().getPackage().getName().substring(WidgetSupport.class.getName().lastIndexOf(".") + 1);
		str = str.replaceAll("\\.", "/");
		sb.append(str);
		sb.append("/");
		sb.append(getClass().getSimpleName().substring(0, 1).toLowerCase());
		sb.append(getClass().getSimpleName().substring(1));
		return sb.toString();
	}

	protected List<ColumnContent> findContent() {
		Column columns = null;
		Long cid = getLong("cid");
		if (cid != null) {
			columns = get(Column.class, cid);
		}
		PageLimit limit = new PageLimit(1, 20);
		Integer pageNo = getInteger("pageNo");
		if (pageNo != null) {
			limit.setPageNo(pageNo);
		}
		Integer pageSize = getInteger("pageNo");
		if (pageSize != null) {
			limit.setPageSize(pageSize);
		}
		return findContent(columns, limit, false);
	}

	// protected Column getColumn() {
	// Column columns = null;
	// Long cid = getLong("cid");
	// if(cid != null){
	// columns = get(Column.class, cid);
	// }
	// return columns;
	// }
	/**
	 * 根据栏目和数量获取信息
	 * 
	 * @param column
	 *            栏目
	 * @param limit
	 *            数量
	 * @return
	 */
	protected List<ColumnContent> findTopContent(Column column, PageLimit limit, boolean isImage) {
		return findContent(column, limit, isImage, false);
	}

	protected List<ColumnContent> findContent(Column column, PageLimit limit, boolean isImage) {
		return findContent(column, limit, isImage, false);
	}

	protected List<ColumnContent> findHotContent(Column column, PageLimit limit, boolean isImage) {
		return findContent(column, limit, isImage, true);
	}

	protected List<ColumnContent> findContent(Column column, PageLimit limit, boolean isImage, boolean doesHot) {
		if (column == null) {
			return null;
		}
		OqlBuilder<ColumnContent> query = getContentQuery(limit, isImage, doesHot);
//		if (column.getContentSource() == null || "CONTENT_SOURCE_SELF".equals(column.getContentSource().getCode())) {
//			query.where(new Condition("cc.column.orders like (:d) ", column.getOrders() + "%"));
//		} else {
//		}
		Set<Column> s = column.getRelationColumns();
		s.add(column);
		query.where(new Condition("cc.column.orders like (:orders)  or  cc.column in (:relationColumns)", column.getOrders(), s));
		query.limit(limit);
		List<ColumnContent> list = entityDao.search(query);
		setPage(limit, list);
		return list;
		// Set set = new LinkedHashSet();
		// if (column.getContent_source() == null ||
		// "CONTENT_SOURCE_SELF".equals(column.getContent_source().getCode())) {
		// EntityQuery query = getContentQuery(limit, isImage, doesHot);
		// query.where(new Condition("cc.columns.orders like (:d)",
		// column.getOrders() + "%"));
		// query.limit(limit);
		// List list = entityDao.search(query);
		// setPage(limit, list);
		// set.addAll(list);
		// } else {
		// Set s = column.getRelation_columns();
		// if (limit == null) {
		// EntityQuery query = new EntityQuery(ColumnContent.class, "cc");
		// if (s != null) {
		// if (s.size() > 0) {
		// query.where(new Condition("cc.columns in(:d)", s));
		// query.where(new Condition("cc.publishDate <=(:pt)",
		// new Date()));
		// query.where(new Condition("cc.doesTop = 1"));
		// query.where(new Condition("cc.topStartDate <=(:tsd)",
		// new Date()));
		// query.where(new Condition("cc.topEndDate >=(:ted)",
		// new Date()));
		// query.where(new Condition("cc.contentState.code=:cscode",
		// DictUtil.CONTENT_STATE_PUBLISHED));
		//
		// List orders = new ArrayList();
		// orders.add(Order.desc("cc.doesTop"));
		// orders.add(Order.desc("cc.publishDate"));
		// query.setOrders(orders);
		// set.addAll(entityDao.search(query));
		// }
		// EntityQuery query1 = new EntityQuery(ColumnContent.class,
		// "cc");
		// if (s.size() > 0) {
		// query1.add(new Condition("cc.columns in (:d)", s));
		// query1.add(new Condition("cc.publishDate <=(:pt)",
		// new Date()));
		// query1.add(new Condition(
		// "cc.contentState.code=:cscode",
		// DictUtil.CONTENT_STATE_PUBLISHED));
		//
		// List orders1 = new ArrayList();
		// orders1.add(Order.desc("cc.doesTop"));
		// orders1.add(Order.desc("cc.publishDate"));
		// query1.setOrders(orders1);
		// set.addAll(entityDao.search(query1));
		// }
		// }
		// } else {
		// if (s != null && s.size() > 0) {
		// String hql =
		// "from ColumnContent cc where cc.columns in (:d) and cc.contentState.code=:cscode and cc.publishDate <=(:pt) and "
		// + " ((cc.endDate is null or cc.endDate >=(:et))"
		// +
		// " or (cc.doesTop = 1 and cc.topStartDate <=(:tsd) and (cc.topEndDate is null or cc.topEndDate >=(:ted)) )) "
		// + " order by cc.doesTop ,cc.publishDate desc";
		// Map params = new HashMap();
		// params.put("d", s);
		// params.put("cscode", DictUtil.CONTENT_STATE_PUBLISHED);
		// params.put("pt", new Date());
		// params.put("et", new Date());
		// params.put("tsd", new Date());
		// params.put("ted", new Date());
		// set.addAll(entityDao.paginateHQLQuery(hql, params,
		// limit).getItems());
		// }
		// }
		//
		// }
		// return new ArrayList(set);
	}

	protected List<ColumnContent> searchContent() {
		List<ColumnContent> list = null;
		String keyWord = get("keyWord");
		String allSite = get("allSite");
		put("keyWord", keyWord);
		if (StringUtils.isNotEmpty(keyWord)) {
			keyWord = keyWord.trim();
			OqlBuilder<ColumnContent> query = OqlBuilder.from(ColumnContent.class, "cc");
			query.where(new Condition("cc.contentState.id = :scid", DictDataUtils.CONTENT_STATE_PUBLISHED));
			query.where(new Condition("cc.content.title like :keyWord or cc.content.keyword like :keyWord", "%" + keyWord + "%"));
			query.orderBy(new Order("cc.publishDate desc"));
			if (StringUtils.isEmpty(allSite)) {
				Site site = getSite();
				query.where(Condition.eq("cc.column.site.id", site.getId()));
			}
			PageLimit limit = getPageLimit();
			query.limit(limit);
			SinglePage<ColumnContent> page = (SinglePage<ColumnContent>) entityDao.search(query);
			list = page.getItems();
			limit.setTotal(page.getTotal());
			put("page", limit);
		}
		return list;
	}

	protected PageLimit getPageLimit() {
		PageLimit limit = new PageLimit();
		limit.setPageNo(QueryHelper.getPageNo());
		limit.setPageSize(QueryHelper.getPageSize());
		return limit;
	}

	protected Long getLongFromJSON(JSONObject json, String key) {
		Long l = null;
		if (StringUtils.isNotEmpty(key)) {
			try {
				l = json.getLong(key);
			} catch (Exception e) {
			}
		}
		return l;
	}

	protected Integer getIntFromJSON(JSONObject json, String key) {
		Integer l = null;
		if (StringUtils.isNotEmpty(key)) {
			try {
				l = json.getInt(key);
			} catch (Exception e) {
			}
		}
		return l;
	}

	protected String getStringFromJSON(JSONObject json, String key) {
		String l = null;
		if (StringUtils.isNotEmpty(key)) {
			try {
				l = json.getString(key);
			} catch (Exception e) {
			}
		}
		return l;
	}

	protected Boolean getBooleanFromJSON(JSONObject json, String key) {
		Boolean l = null;
		if (StringUtils.isNotEmpty(key)) {
			try {
				l = json.getBoolean(key);
			} catch (Exception e) {
			}
		}
		return l;
	}

	protected List<Column> findColumn() {
		OqlBuilder<Column> query = OqlBuilder.from(Column.class, "c");
		Site site = getSite();
		if (site != null) {
			query.where(new Condition("c.site.id=:sid", site.getId()));
		}
		query.where(new Condition("c.columns is not null"));
		query.where(new Condition("c.site.enabled=1"));
		query.where(new Condition("c.enabled=1"));
		query.where(new Condition("c.type.id=16 or c.type.id = 17 or c.type.id = 142"));
		query.orderBy(Order.parse("c.orders"));
		List<Column> list = entityDao.search(query);
		return list;
	}

	/**
	 * 详细信息
	 * 
	 * @return
	 */
	protected ColumnContent getColumnContent() {
		Long ccid = getLong("ccid");
		ColumnContent cc = null;
		Column column = getColumn();
		if (ccid != null) {
			cc = get(ColumnContent.class, ccid);
		} else if (column != null) {
			// 单页信息
			PageLimit limit = new PageLimit(1, 1);
			List<ColumnContent> list = findContent(column, limit, false);
			if (!list.isEmpty()) {
				cc = list.get(0);
			}
		}
		viewContent(cc);
		checkCode(cc);
		return cc;
	}

	/**
	 * 检查授权码
	 * 
	 * @param cc
	 */
	private void checkCode(ColumnContent cc) {
		if (cc == null) {
			return;
		}
		/*
		 * Content c = cc.getContent(); if (StringUtils.isNotEmpty(c.getCode()))
		 * { Object o =
		 * request.getAttribute("javax.servlet.forward.query_string"); String
		 * url = o == null ? "" : o.toString(); String code = get("code"); if
		 * (code != null && code.contains(",")) { code = code.split(",")[0]; url
		 * = ""; } if (code == null || url.contains(code) ||
		 * !c.getCode().equals(code)) { // 防止保存文档 c.setId(null); c.setDetail(
		 * "<form method='post'><p>该文档内容已被隐藏，需要授权码才能访问。</p><p>请输入授权码：<input name=\"code\"/>&nbsp;&nbsp;<input type=\"submit\" value='确定'/></p></form>"
		 * ); } }
		 */
	}

	protected List<Column> findColumn(Map<String, Object> root, Column topColumn, Column column) {
		String hql = "from "+Column.class.getName()+" where enabled = true and orders like ? order by orders";
		String orders = topColumn.getOrders();
		if (orders.length() > 6) {
			orders = topColumn.getOrders().substring(0, 6);
		}
		Object[] params = new Object[] { orders + "%" };
		List<Column> list = entityDao.searchHQLQuery(hql, params);
		return list;
	}

	protected List<ColumnContent> findNewContent(Site site, PageLimit limit) {
		if (site == null) {
			return null;
		}
		OqlBuilder<ColumnContent> query = getContentQuery(limit, false, false);
		query.where(Condition.eq("cc.columns.site.id", site.getId()));
		List<ColumnContent> list = entityDao.search(query);
		setPage(limit, list);
		return list;
	}

	/**
	 * 查询热点新闻
	 * 
	 * @param site
	 * @param limit
	 * @param isImage
	 * @return
	 */
	protected List<ColumnContent> findHotContent(Site site, PageLimit limit, boolean isImage) {
		OqlBuilder<ColumnContent> query = getContentQuery(limit, isImage, true);
		query.where(Condition.eq("cc.columns.site.id", site.getId()));
		List<ColumnContent> list = entityDao.search(query);
		setPage(limit, list);
		return list;
	}

	/**
	 * 查询文档
	 * 
	 * @param limit
	 * @param isImage
	 * @param doesHot
	 * @return
	 */
	private OqlBuilder<ColumnContent> getContentQuery(PageLimit limit, boolean isImage, boolean doesHot) {
		OqlBuilder<ColumnContent> query = OqlBuilder.from(ColumnContent.class, "cc");
		if (isImage) {
			query.where(new Condition("cc.content.titleImage is not null"));
		}
		if (doesHot) {
			query.where(new Condition("cc.doesHot = true"));
		}
		query.where(new Condition("cc.column.enabled = true"));
		query.where(new Condition("cc.contentState.id=:csid", DictDataUtils.CONTENT_STATE_PUBLISHED));
		Date now = new Date();
		query.where(new Condition("cc.publishDate <=(:pt)", now));
		// query.where(new Condition("cc.endDate is null or cc.endDate >=(:et)",
		// now));
		query.limit(limit);
		if (limit == null) {
			query.where(new Condition("cc.doesTop = 1"));
			query.where(new Condition("cc.topStartDate <= :tsd", now));
			query.where(new Condition("cc.topEndDate is null or cc.topEndDate >=(:ted)", now));
		}
		List<Order> orders = new ArrayList<Order>();
		orders.add(Order.desc("cc.doesTop"));
//		orders.add(Order.desc("cc.weight"));
		orders.add(Order.desc("cc.publishDate"));
		query.orderBy(orders);
		return query;
	}

	protected void setPage(PageLimit limit, List<ColumnContent> list) {
		if (limit != null) {
			Page<ColumnContent> page = (Page<ColumnContent>) list;
			limit.setTotal(page.getTotal());
			put("page", limit);
		}
	}
}
