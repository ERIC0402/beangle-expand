package org.beangle.website.cms.template.action;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.beangle.ems.security.Group;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.helper.Params;
import org.beangle.website.cms.template.service.TemplateService;
import org.beangle.website.cms.template.widgets.WidgetUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.slf4j.LoggerFactory;

import org.beangle.website.cms.action.SiteCommonAction;
import org.beangle.website.cms.model.Column;
import org.beangle.website.cms.model.Site;
import org.beangle.website.cms.model.SiteWidgetConfig;
import org.beangle.website.cms.model.Template;
import org.beangle.website.cms.model.TemplateGroup;
import org.beangle.website.cms.model.WidgetConfig;
import org.beangle.website.common.util.FileUtil;
import org.beangle.website.system.model.DictData;

/**
 * 站点管理
 * 
 * @author CHII
 * 
 */
public class SiteAction extends SiteCommonAction {
	private ColumnAction columnAction;
	private WidgetUtils widgetUtils;
	private TemplateService templateService;

	public void setWidgetUtils(WidgetUtils widgetUtils) {
		this.widgetUtils = widgetUtils;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	public void setColumnAction(ColumnAction columnAction) {
		this.columnAction = columnAction;
	}

	@Override
	protected String saveAndForward(Entity<?> entity) {
		try {
			saveSite();
		} catch (Exception e) {
			return forward("edit", e.getMessage());
		}
		return redirect("search", "info.save.success");
	}

	private Site saveSite() {
		Site site = (Site) populateEntity(Site.class, "site");
		if (get("setgroup") != null) {
			List<Group> groups = entityDao.get(Group.class, getAll("roleIds", Long.class));
			site.getGroups().clear();
			site.getGroups().addAll(groups);
		}
		// 断送根路径是否存在、站点名是否存在
		if (exist(site, "name", site.getName())) {
			put("site", site);
			throw new RuntimeException("站点名重复");
		}
		if (exist(site, "basePath", site.getBasePath())) {
			put("site", site);
			throw new RuntimeException("根路径重复");
		}
		boolean newSite = site.getId() == null ? true : false;
		String message = (newSite ? "新建" : "修改") + "了一个站点" + site.getName();
		saveOperationLog("站点管理", message);
		entityDao.saveOrUpdate(site);
		Column column = null;
		if (!newSite) {
			OqlBuilder<Column> oql = OqlBuilder.from(Column.class, "c");
			oql.where("site.id = :siteId and type.id = 141", site.getId());
			List<Column> list = entityDao.search(oql);
			if (!list.isEmpty()) {
				column = list.get(0);
			}
			entityDao.executeUpdateHql("update " + Column.class.getName() + " set enabled = ? where site.id = ?", new Object[] { site.isEnabled(), site.getId() });
		}
		if (newSite || column == null) {
			column = new Column();
			column.setType(entityDao.get(DictData.class, 141L));
			column.setSite(site);
			column.setEnabled(Boolean.TRUE);
			column.setNode(true);
			column.setOrders(columnAction.getNextOrders(null));
		}
		column.setWorkflow(site.getWorkflow());
		column.setDoesImpl(false);
		column.setName(site.getName());
		entityDao.saveOrUpdate(column);
		return site;
	}

	@Override
	public String index() throws Exception {
		super.index();
		put("userCategoryId", getUserCategoryId());
		return forward();
	}

	@Override
	public String search() {
		put("sites", getSitesByCurrentUser());
		return forward();
	}

	public String stepAddSite() {
		super.edit();
//		Site site = entityDao.get(Site.class, 66L);
		Site site = new Site();
		put("site", site);
		return forward();
	}

	public String stepSetTemplateGroup() {
		Site site = null;
		try {
			site = saveSite();
			put("site", site);
		} catch (Exception e) {
			e.printStackTrace();
			return forward("stepAddSite", e.getMessage());
		}
		editTemplate();
		put("site", site);
		return forward();
	}

	public String stepSetColumn() {
		try {
			Site site = saveSite();
			put("site", site);
		} catch (Exception e) {
			e.printStackTrace();
			return forward("stepSetTemplateGroup", e.getMessage());
		}
		return forward();
	}

	public String stepSetPage() {
		super.edit();
		return forward();
	}

	// 检查排序和名称是否唯一
	public void checkOrderandName() {
		try {
			String order = get("order");
			String name = get("name");
			String name2 = URLDecoder.decode(name, "utf-8");
			List<Site> orderList = entityDao.searchHQLQuery("from " + Site.class.getName() + " where code='" + order + "'");
			List<Site> nameList = entityDao.searchHQLQuery("from " + Site.class.getName() + " where name='" + name2 + "'");
			response.setContentType("text/plain;charset=utf-8");
			String ss = "";
			if (!orderList.isEmpty()) {
				ss += "order";
			}
			if (!nameList.isEmpty()) {
				ss += "name";
			}
			if (orderList.isEmpty() && nameList.isEmpty()) {
				ss = "OK";
			}
			response.getWriter().print(ss);
		} catch (Exception e) {
		}
	}

	public String editTemplate() {
		put("site", getEntity());
		List<TemplateGroup> list = entityDao.searchHQLQuery("from " + TemplateGroup.class.getName() + " where enabled = true");
		put("templateGroups", list);
		return forward();
	}

	public String saveTemplate() {
		return forward();
	}

	public String findTemplate() {
		Long templateGroupId = getLong("templateGroupId");
		put("templates", entityDao.searchHQLQuery("from " + Template.class.getName() + " t where group.id = ? and enabled = true", new Object[] { templateGroupId }));
		return forward();
	}

	/**
	 * 模板列表
	 * 
	 * @return
	 */
	public String templateList() {
		Site site = (Site) getEntity();
		if (site.getTemplateGroup() != null) {
			OqlBuilder<?> oql = OqlBuilder.from(Template.class.getName(), "v");
			oql.where("v.enabled = true");
			oql.where(Condition.eq("v.group.id", site.getTemplateGroup().getId()));
			oql.orderBy("v.type.code");
			put("templates", entityDao.search(oql));
		}
		put("site", site);
		put("step", get("step"));
		return forward();
	}

	/**
	 * 配置模板
	 * 
	 * @return
	 */
	public String configTemplate() {
		Long templateId = getLong("templateId");
		Template template = (Template) entityDao.get(Template.class, templateId);
		Site site = (Site) getEntity();
		put("template", template);
		put("site", site);
		createCss(site);
		return forward();
	}

	/**
	 * 查询配置
	 * 
	 * @return
	 */
	public String findWidgetConfig() {
		put("widgetConfigs", templateService.findSiteWidgetConfig(getLong("siteId"), getLong("templateId")));
		return forward();
	}

	/**
	 * 显示组件
	 * 
	 * @return
	 */
	public void viewWidget() throws IOException {
		String clazzName = get("clazzName");
		String config = get("config");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(widgetUtils.getContent(clazzName, config));
		response.flushBuffer();
	}

	/**
	 * 显示组件配置
	 * 
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void editConfig() throws DocumentException, IOException {
		Long widgetConfigId = getLong("widgetConfigId");
		WidgetConfig wc = (WidgetConfig) entityDao.get(WidgetConfig.class, widgetConfigId);
		Long swidgetConfigId = getLong("swidgetConfigId");
		String config = null;
		if (swidgetConfigId != null) {
			SiteWidgetConfig swc = (SiteWidgetConfig) entityDao.get(SiteWidgetConfig.class, swidgetConfigId);
			config = swc.getConfig();
		} else {
			config = wc.getConfig();
		}
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(widgetUtils.getConfig(wc.getWidget(), config));
	}

	/**
	 * 保存组件配置
	 * 
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void saveConfig() throws DocumentException, IOException {
		try {
			Map<String, Object> map = Params.sub("config");
			SiteWidgetConfig swc = null;
			Long swidgetConfigId = getLong("swidgetConfigId");
			if (swidgetConfigId != null) {
				swc = (SiteWidgetConfig) entityDao.get(SiteWidgetConfig.class, swidgetConfigId);
			} else {
				Long widgetConfigId = getLong("widgetConfigId");
				WidgetConfig wc = (WidgetConfig) entityDao.get(WidgetConfig.class, widgetConfigId);
				swc = new SiteWidgetConfig();
				swc.setSite((Site) entityDao.get(Site.class, getLong("siteId")));
				swc.setWidgetConfig(wc);
			}
			JSONObject data = new JSONObject();
			for (String key : map.keySet()) {
				data.put(key, map.get(key));
			}
			swc.setConfig(data.toString());
			// 更新相同组件设置
			if (get("updateSameWidget") != null) {
				// 更新已有站点组件配置
				OqlBuilder<SiteWidgetConfig> query = OqlBuilder.from(SiteWidgetConfig.class, "o");
				query.where(Condition.eq("o.site.id", swc.getSite().getId()));
				query.where(Condition.eq("o.widgetConfig.widget.id", swc.getWidgetConfig().getWidget().getId()));
				if (swc.getId() != null) {
					query.where(Condition.ne("o.id", swc.getId()));
				}
				List<SiteWidgetConfig> list = entityDao.search(query);
				List<Long> ids = new ArrayList<Long>(list.size());
				if (!list.isEmpty()) {
					for (SiteWidgetConfig s : list) {
						s.setConfig(swc.getConfig());
						ids.add(s.getWidgetConfig().getId());
					}
					entityDao.saveOrUpdate(list);
				}
				// 创建站点组件配置
				OqlBuilder<WidgetConfig> query2 = OqlBuilder.from(WidgetConfig.class, "o");
				query.where(Condition.eq("o.widget.id", swc.getWidgetConfig().getWidget().getId()));
				if (!ids.isEmpty()) {
					query.where(new Condition("o.id not in (:ids)", ids));
				}
				List<WidgetConfig> wclist = entityDao.search(query2);
				if (!wclist.isEmpty()) {
					for (WidgetConfig wc : wclist) {
						SiteWidgetConfig s = new SiteWidgetConfig();
						s.setSite(swc.getSite());
						s.setWidgetConfig(wc);
						s.setConfig(swc.getConfig());
						entityDao.saveOrUpdate(s);
					}
				}
			}
			entityDao.saveOrUpdate(swc);
		} catch (Exception e) {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write("error");
		}
	}

	/**
	 * 发布
	 * 
	 * @return
	 */
	public String publish() {
		Template template = (Template) entityDao.get(Template.class, getLong("templateId"));
		Site site = (Site) getEntity();
		templateService.createTemplate(template, site, true);
		return forward();
	}

	/**
	 * 发布
	 * 
	 * @return
	 */
	public String publishAll() {
		String hql = "from " + Site.class.getName() + " where enabled = true and templateGroup.id is not null and id in (" + get("site.ids") + ")";
		List<Site> siteList = entityDao.searchHQLQuery(hql);
		for (Site s : siteList) {
			if (!s.isEnabled()) {
				continue;
			}
			List<Template> list = templateService.findTemplate(s.getTemplateGroup());
			for (Template template : list) {
				templateService.createTemplate(template, s, true);
			}
		}
		if (get("templateList") == null) {
			return redirect("search", "info.save.success");
		} else {
			return redirect("templateList", "info.save.success", "siteId=" + siteList.get(0).getId());
		}
	}

	/**
	 * 显示模板内容
	 * 
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public String viewContent() throws DocumentException, IOException {
		Template template = entityDao.get(Template.class, getLong("templateId"));
		Site site = entityDao.get(Site.class, getLong("siteId"));
		String content = templateService.getContent(template, site);
		String fileName = "temp/" + site.getId() + "_" + template.getId();
		FileUtil.writeFile(getFrontDir(), fileName + ".ftl", content);

		templateService.createTempStyle(site);
		put("perviewcss", "perviewcss");
		put("site", site);
		return forward(fileName);
	}
	
	private String getFrontDir(){
		return this.getClass().getClassLoader().getResource("").getPath() + FileUtil.getActionDir(this.getClass());
	}

	/**
	 * 根据站点的模板组读取换色配置文件
	 * 
	 * @return
	 */
	public String configColor() {
		Site site = (Site) getEntity();
		put("site", site);
		Document doc = templateService.getColorConfig(site.getTemplateGroup());
		if (doc != null) {
			List<Element> list = doc.selectNodes("/root/color");
			Map<String, String> colorMap = new HashMap<String, String>();
			if (StringUtils.isNotEmpty(site.getColors())) {
				String[] cs = site.getColors().split(";");
				for (String color : cs) {
					String[] ss = color.split(":");
					if (ss.length > 1) {
						colorMap.put(ss[0], ss[1]);
					}
				}
			}
			List<Object[]> colors = new ArrayList<Object[]>();
			for (Element e : list) {
				String[] oo = new String[4];
				oo[0] = e.elementText("title");
				oo[1] = e.elementText("value");
				oo[2] = colorMap.get(oo[1]);
				oo[3] = e.elementText("remark");
				if (oo[2] == null) {
					oo[2] = oo[1];
				}
				colors.add(oo);
			}
			if (!colors.isEmpty()) {
				put("colors", colors);
			}
		}
		return forward();
	}

	public String saveColor() {
		Site site = (Site) getEntity();
		String type = get("type");
		StringBuilder sb = new StringBuilder();
		if ("reset".equals(type)) {
		} else {
			Map<String, Object> map = Params.sub("color");
			for (String name : map.keySet()) {
				if (sb.length() > 0) {
					sb.append(";");
				}
				sb.append(name);
				sb.append(":");
				sb.append(map.get(name));
			}
		}
		site.setColors(sb.toString());
		entityDao.saveOrUpdate(site);
		createCss(site);
		return redirect("configColor", "info.save.success", "siteId=" + site.getId());
	}

	private void createCss(Site site) {
		String style = templateService.getStyle(site);
		try {
			String path = this.getClass().getClassLoader().getResource("").getFile() + "static/site-template/css/front/theme" + site.getTemplateGroup().getId() + "/temp_style";
			FileUtil.writeFile(path, site.getId() + ".css", style);
		} catch (IOException ex) {
			LoggerFactory.getLogger(SiteAction.class).error(ex.getMessage());
		}
	}
	
	public String cleanCache(){
		String hql = "from " + Site.class.getName() + " where enabled = true and templateGroup.id is not null and id in (" + get("site.ids") + ")";
		List<Site> siteList = entityDao.searchHQLQuery(hql);
		String[] keepDir = new String[]{"WEB-INF", "META-INF"};
		for (Site s : siteList) {
			File dir = new File(request.getSession().getServletContext().getRealPath(s.getBasePath()));
			if(dir.exists() && dir.isDirectory()){
				boolean canDel = true;
				for(String d : keepDir){
					if(d.equals(dir.getName())){
						canDel = false;
					}
				}
				if(canDel){
					try {
						FileUtils.deleteDirectory(dir);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return redirect("search", "info.save.success");
	}
}
