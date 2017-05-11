package org.beangle.website.cms.template.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.helper.Params;
import org.beangle.website.cms.template.service.TemplateService;
import org.beangle.website.cms.template.widgets.WidgetUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import org.beangle.website.cms.action.CommonAction;
import org.beangle.website.cms.model.Site;
import org.beangle.website.cms.model.Template;
import org.beangle.website.cms.model.TemplateGroup;
import org.beangle.website.cms.model.TemplateLayout;
import org.beangle.website.cms.model.Widget;
import org.beangle.website.cms.model.WidgetConfig;
import org.beangle.website.common.util.FileUtil;
import org.beangle.website.system.model.DictTypeUtils;

/**
 * 模板管理
 * 
 * @author XMAN
 * 
 */
public class TemplateAction extends CommonAction {
	private WidgetUtils widgetUtils;
	private TemplateService templateService;

	public void setWidgetUtils(WidgetUtils widgetUtils) {
		this.widgetUtils = widgetUtils;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}
	
	@Override
	protected String getEntityName() {
		return Template.class.getName();
	}

	@Override
	protected void indexSetting() {
		super.indexSetting();
		put("groups", entityDao.searchHQLQuery("from " + TemplateGroup.class.getName() + " where enabled = true"));
		put("types", getDictDataByDictType(DictTypeUtils.TEMPLATE_TYPE));
	}

	@Override
	protected void editSetting(Entity<?> entity) {
		super.editSetting(entity);
		Template template = (Template) entity;
		put("groups", entityDao.searchHQLQuery("from " + TemplateGroup.class.getName() + " where enabled = true"));
		put("template", template);
		put("types", getDictDataByDictType(DictTypeUtils.TEMPLATE_TYPE));
	}

	/**
	 * 可视化编辑
	 * 
	 * @return
	 */
	public String editLayout() {
		Template template = (Template) getEntity();
		put("template", template);
		if (StringUtils.isEmpty(template.getLayout())) {
			put("layouts", entityDao.searchHQLQuery("from " + TemplateLayout.class.getName() + " where enabled = true order by id"));
		}
		return forward();
	}

	/**
	 * 保存布局
	 * 
	 * @return
	 */
	public String saveLayout() throws DocumentException {
		Template template = (Template) getEntity();
		Long templateLayoutId = getLong("templateLayoutId");
		if (templateLayoutId != null) {
			if (StringUtils.isEmpty(template.getLayout())) {
				TemplateLayout tl = (TemplateLayout) entityDao.get(TemplateLayout.class, templateLayoutId);
				template.setLayout(tl.getContent());
				entityDao.saveOrUpdate(template);
			}
		}
		return redirect("editLayout", null, "templateId=" + template.getId());
	}

	/**
	 * 更新布局设置 type:0HTML,1添加，2删除，3修改
	 * 
	 * @param template
	 */
	public String updateLayout() throws DocumentException, UnsupportedEncodingException, IOException {
		response.setCharacterEncoding("utf-8");
		try {
			Template template = (Template) getEntity();
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new ByteArrayInputStream(template.getLayout().getBytes()));
			Integer type = getInteger("type");
			if (type == null) {
				return null;
			}
			JSONObject data = (JSONObject) JSONObject.fromObject(get("data"));
			String parentId = get("parentId");
			switch (type) {
			case 0:// 编辑HTML
				String content = get("content");
				document = saxReader.read(new ByteArrayInputStream(content.getBytes()));
				break;
			case 1:// 添加
				addLayout(document, parentId, data);
				break;
			case 2:// 修改
				updateLayout(document, parentId);
				break;
			case 3:// 删除
				Element pe = (Element) document.getRootElement().selectSingleNode("//div[@id='" + parentId + "']");
				pe.getParent().remove(pe);
				break;
			}
			OutputFormat of = new OutputFormat();
			of.setExpandEmptyElements(true);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			XMLWriter xmlWriter = new XMLWriter(baos, of);
			xmlWriter.write(document.getRootElement());
			template.setLayout(baos.toString());
			entityDao.saveOrUpdate(template);
			response.getWriter().write(template.getLayout());
		} catch (Exception e) {
			response.getWriter().write("error");
		}
		response.getWriter().flush();
		// return redirect("editLayout", null, "templateId=" +
		// template.getId());
		return null;
	}

	private void updateLayout(Document document, String parentId) {
		String sizeType = get("sizeType");
		String size = get("size") + "";
		String style = get("style") + ",0";
		String clazz = get("clazz") + ",0";
		Element pe = (Element) document.getRootElement().selectSingleNode("//div[@id='" + parentId + "']");
		List<Element> list = null;
		String[] sizes = size.split(",");
		if (sizes.length < 2) {
			list = new ArrayList<Element>();
			list.add(pe);
		} else {
			list = pe.elements();
		}
		String[] styles = style.split(",");
		String[] clazzs = clazz.split(",");
		for (int i = 0; i < sizes.length; i++) {
			Element e = (Element) list.get(i);
			String width = styles[i];
			if (sizes.length == 1) {
				width = "margin:0 auto;";
			} else {
				width = "float:left;";
			}
			width += "width:" + sizes[i] + sizeType + ";";
			e.addAttribute("style", width);
			Element ee = e.element("div");
			ee.addAttribute("style", styles[i]);
			ee.addAttribute("class", clazzs[i]);
		}
	}

	private void addLayout(Document document, String parentId, JSONObject data) throws NumberFormatException {
		Element root = (Element) document.getRootElement();
		Attribute pea = root.attribute("childNum");
		if (pea == null) {
			root.addAttribute("childNum", "0");
			pea = root.attribute("childNum");
		}
		Element pe = root;
		if (!"layoutRoot".equals(parentId)) {
			pe = (Element) document.getRootElement().selectSingleNode("//div[@id='" + parentId + "']");
			pe = pe.element("div");
		}
		int childNum = Integer.parseInt(pea.getText());
		String rows = (String) data.get("rows");
		if (rows == null) {
			// 添加多列
			pe = pe.addElement("div");
			pe.addAttribute("style", "overflow:hidden");
			pe.addAttribute("id", "layout" + (++childNum));
			String cols = (String) data.get("cols");
			for (String col : cols.split(",")) {
				Element e = pe.addElement("div");
				StringBuilder sb = new StringBuilder();
				sb.append("float:left;");
				e.addAttribute("id", "layout" + (++childNum));
				sb.append("width:").append(col).append("%;");
				e.addAttribute("style", sb.toString());
				// 浮动层默认添加一个空的层，可以使用样式、边框
				e.addElement("div");
			}
			Element e = pe.addElement("div");
			e.addAttribute("class", "clearDiv");
		} else {
			// 添加多行
			for (int i = 0; i < Integer.parseInt(rows); i++) {
				Element e = pe.addElement("div");
				e.addAttribute("id", "layout" + (++childNum));
				e.addElement("div");
			}
		}
		pea.setValue(childNum + "");
	}

	/**
	 * 可视化编辑
	 * 
	 * @return
	 */
	public String editContent() throws DocumentException, UnsupportedEncodingException, IOException {
		Template template = (Template) getEntity();
		// 所有组件
		put("widgets", entityDao.searchHQLQuery("select w from " + Widget.class.getName() + " w join w.groups g where w.enabled = true and g.id = ? order by w.type.code", new Object[] { template.getGroup().getId() }));
		// 组件分类
		put("types", getDictDataByDictType(DictTypeUtils.WIDGET_TYPE));
		// 同模板组下使用的组件
		// put("widgetConfigs", getUsedConfig(template));
		// 该模板未设置位置组件
		put("template", template);
		return forward();
	}

	/**
	 * 显示模板内容
	 * 
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public String viewContent() throws DocumentException, IOException {
		Long templateId = getLong("template.id");
		Long siteId = getLong("siteId");
		String content = templateService.getContent(templateId, siteId);
		FileUtil.writeFile(request.getSession().getServletContext().getRealPath("/WEB-INF/classes/temp/template"), templateId + ".ftl", content);
		if (siteId != null) {
			put("site", entityDao.get(Site.class, siteId));
		}
		return forward("/temp/template/" + templateId.toString());
	}

	/**
	 * 根据模板ID查询配置
	 * 
	 * @return
	 */
	public String findWidgetConfig() {
		Long templateId = getLong("templateId");
		List<WidgetConfig> widgetConfigs = entityDao.searchHQLQuery("from " + WidgetConfig.class.getName() + " where template.id = ? order by no desc", new Object[] { templateId });
		put("widgetConfigs", widgetConfigs);
		return forward();
	}

	/**
	 * 显示组件内容
	 * 
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void viewWidget() throws DocumentException, IOException {
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
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(widgetUtils.getConfig(wc.getWidget(), wc.getConfig()));
	}
	
	@Override
	protected String saveAndForward(Entity<?> entity) {
		Template tg = (Template) entity;
		tg.setImg(moveAndRemoveAnnex(tg.getImg(), get("oimg")));
		return super.saveAndForward(entity);
	}

	/**
	 * 保存组件内容
	 * 
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public String saveConfig() throws DocumentException, IOException {
		try {
			Map<String, Object> map = Params.sub("config");
			WidgetConfig wc = getWidgetConfig();
			JSONObject data = new JSONObject();
			for (String key : map.keySet()) {
				data.put(key, map.get(key));
			}
			wc.setConfig(data.toString());
			entityDao.saveOrUpdate(wc);
			// 更新相同组件设置
			if (get("updateSameWidget") != null) {
				// 更新已有站点组件配置
				OqlBuilder<WidgetConfig> query = OqlBuilder.from(WidgetConfig.class, "o");
				query.where(Condition.eq("o.group.id", wc.getGroup().getId()));
				query.where(Condition.eq("o.widget.id", wc.getWidget().getId()));
				if (wc.getId() != null) {
					query.where(Condition.ne("o.id", wc.getId()));
				}
				List<WidgetConfig> list = entityDao.search(query);
				if (!list.isEmpty()) {
					for (WidgetConfig s : list) {
						s.setConfig(wc.getConfig());
					}
					entityDao.saveOrUpdate(list);
				}
			}
		} catch (Exception e) {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write("error");
		}
		return null;
	}

	/**
	 * 更新布局设置 type:0HTML,1添加，2删除，3修改
	 * 
	 * @param template
	 */
	public String updateContent() throws DocumentException, UnsupportedEncodingException, IOException {
		response.setCharacterEncoding("utf-8");
		try {
			Template template = (Template) getEntity();
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new ByteArrayInputStream(template.getLayout().getBytes()));
			Integer type = getInteger("type");
			if (type == null) {
				throw new RuntimeException("type is null");
			}
			JSONObject data = (JSONObject) JSONObject.fromObject(get("data"));
			String parentId = get("parentId");
			switch (type) {
			case 0:// 编辑HTML
				String content = get("content");
				document = saxReader.read(new ByteArrayInputStream(content.getBytes()));
				break;
			case 1:// 添加
				addContent(template, parentId, data);
				break;
			case 2:// 修改
				saveConfig();
				break;
			case 3:// 删除
				Long widgetConfigId = getLong("widgetConfigId");
				WidgetConfig wc = entityDao.get(WidgetConfig.class, widgetConfigId);
				wc.setTemplate(null);
				entityDao.saveOrUpdate(wc);
				// entityDao.remove(entityDao.load(WidgetConfig.class,
				// widgetConfigId));
				break;
			}
			OutputFormat of = new OutputFormat();
			of.setExpandEmptyElements(true);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			XMLWriter xmlWriter = new XMLWriter(baos, of);
			xmlWriter.write(document.getRootElement());
			template.setLayout(baos.toString());
			entityDao.saveOrUpdate(template);
			response.getWriter().write(template.getLayout());
		} catch (Exception e) {
			response.getWriter().write("error");
		}
		response.getWriter().flush();
		// return redirect("editLayout", null, "templateId=" +
		// template.getId());
		return null;
	}

	private void addContent(Template template, String parentId, JSONObject data) {
		Integer widgetId = (Integer) data.get("widgetId");
		WidgetConfig config = new WidgetConfig();
		config.setWidget((Widget) entityDao.get(Widget.class, widgetId.longValue()));
		config.setTemplate(template);
		config.setGroup(template.getGroup());
		config.setPosition(parentId);
		// 设置引用
		// Integer widgetConfigId = (Integer) data.get("widgetConfigId");
		// if (widgetConfigId != null) {
		// config.setQuote((WidgetConfig) entityDao.load(WidgetConfig.class,
		// widgetConfigId.longValue()));
		// }
		entityDao.saveOrUpdate(config);
		config.setNo(config.getId());
		entityDao.saveOrUpdate(config);
	}

	@Override
	protected String removeAndForward(Collection<?> templates) {
		for (Iterator<Template> iterator = (Iterator<Template>) templates.iterator(); iterator.hasNext();) {
			Template template = iterator.next();
			// File file = new File(FileUtil.getWebAppPath() + "/" +
			// template.getPath() + "/" + template.getName() + ".html");
			// file.delete();
		}
		return super.removeAndForward(templates);
	}

	private WidgetConfig getWidgetConfig() {
		Long widgetConfigId = getLong("widgetConfigId");
		return (WidgetConfig) entityDao.get(WidgetConfig.class, widgetConfigId);
	}
}
