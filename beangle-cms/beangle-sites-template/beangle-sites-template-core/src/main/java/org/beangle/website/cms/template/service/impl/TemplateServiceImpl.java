/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.service.impl;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.helper.ContextHelper;
import org.beangle.website.cms.template.action.FrontAction;
import org.beangle.website.cms.template.service.TemplateService;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.beangle.website.cms.model.Site;
import org.beangle.website.cms.model.SiteWidgetConfig;
import org.beangle.website.cms.model.Template;
import org.beangle.website.cms.model.TemplateGroup;
import org.beangle.website.cms.model.WidgetConfig;
import org.beangle.website.common.util.FileUtil;

/**
 * 
 * @author CHII
 */
public class TemplateServiceImpl implements TemplateService {
	private Logger log = LoggerFactory.getLogger(TemplateServiceImpl.class);
	private EntityDao entityDao;

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	public String getContent(Long templateId, Long siteId) {
		Site site = null;
		if (siteId != null) {
			site = (Site) entityDao.get(Site.class, siteId);
		}
		return getContent((Template) entityDao.get(Template.class, templateId), site);
	}

	public String getContent(Template template, Site site) {
		Long siteId = null;
		if (site != null) {
			siteId = site.getId();
		}
		List<SiteWidgetConfig> list = findSiteWidgetConfig(siteId, template.getId());
		StringBuilder content = new StringBuilder();
		content.append("<#assign templateGroupId=").append(template.getGroup().getId()).append("/>");
		content.append("<#include '/").append(FileUtil.getClassDir(FrontAction.class)).append("/front/comm.ftl'><@html>");
		if (!StringUtils.isEmpty(template.getLayout())) {
			content.append(template.getLayout());
		}
		Map<Long, String> existmap = new HashMap<Long, String>();
		for (SiteWidgetConfig swc : list) {
			Long key = swc.getWidgetConfig().getId();
			if (swc.getId() != null && existmap.get(key) != null) {
				entityDao.remove(swc);
				continue;
			}
			existmap.put(key, "true");
			int index = content.indexOf(swc.getWidgetConfig().getPosition() + "\"");
			if (index < 0) {
				continue;
			}
			StringBuilder sb = new StringBuilder();
			sb.append("<#assign config>").append(swc.getConfig()).append("</#assign>");
			sb.append("<@widget className='").append(swc.getWidgetConfig().getWidget().getClassName()).append("' config=config/>");
			content.insert(content.indexOf("</div>", index), sb);
		}
		content.append("</@html>");
		return content.toString();
	}

	public String createTemplate(Template template, Site site) {
		return createTemplate(template, site, false);
	}

	public String createTemplate(Template template, Site site, boolean recreate) {
		if (template == null) {
			ContextHelper.put("msg", "没有适合的模板");
			return "errors";
		}
		String path = site.getBasePath() + "/" + template.getId();
		String root = FrontAction.class.getClassLoader().getResource("").getPath() + FileUtil.getActionDir(FrontAction.class);
		File file = new File(root + "/" + path + ".ftl");
		log.debug(file.getAbsolutePath());
		if (!recreate && file.exists()) {
			return path;
		}
		String content = getContent(template.getId(), site.getId());
		try {
			FileUtil.writeFile(file, content);
		} catch (IOException e) {
			log.error("模板文件写入失败：" + file.getAbsolutePath());
			throw new RuntimeException("error");
		}
		// if (StringUtils.isNotEmpty(site.getColors())) {
		try {
			String style = getStyle(site);
			String cssDir = getRootPath() + "/css/front/theme" + site.getTemplateGroup().getId() + "/style_01";
			FileUtil.writeFile(cssDir, site.getId() + ".css", style);
		} catch (IOException ex) {
			java.util.logging.Logger.getLogger(TemplateServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
		}
		// }
		return path;
	}

	public List<SiteWidgetConfig> findSiteWidgetConfig(Long siteId, Long templateId) {
		List<SiteWidgetConfig> list = null;
		if (siteId == null) {
			list = new ArrayList<SiteWidgetConfig>();
		} else {
			OqlBuilder<SiteWidgetConfig> query = OqlBuilder.from(SiteWidgetConfig.class, "o");
			query.where(Condition.eq("site.id", siteId));
			query.where(Condition.eq("widgetConfig.template.id", templateId));
			list = entityDao.search(query);
		}
		String hql = "from " + WidgetConfig.class.getName() + " where template.id = ?";
		if (!list.isEmpty()) {
			StringBuilder wcids = new StringBuilder();
			for (SiteWidgetConfig swc : list) {
				if (wcids.length() > 0) {
					wcids.append(",");
				}
				wcids.append(swc.getWidgetConfig().getId());
			}
			hql += " and id not in (" + wcids + ")";
		}
		Object[] param = new Object[] { templateId };
		List<WidgetConfig> wclist = entityDao.searchHQLQuery(hql, param);
		for (WidgetConfig wc : wclist) {
			SiteWidgetConfig swc = new SiteWidgetConfig();
			swc.setConfig(wc.getConfig());
			swc.setWidgetConfig(wc);
			list.add(swc);
		}
		Collections.sort(list, new Comparator<SiteWidgetConfig>() {
			public int compare(SiteWidgetConfig o1, SiteWidgetConfig o2) {
				if (o1.getWidgetConfig().getNo() > o2.getWidgetConfig().getNo()) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		return list;
	}

	public List<Template> findTemplate(TemplateGroup templateGroup) {
		OqlBuilder<Template> query = OqlBuilder.from(Template.class, "t");
		query.where(new Condition("t.enabled = true"));
		query.orderBy("t.type.code");
		query.where(Condition.eq("t.group.id", templateGroup.getId()));
		return entityDao.search(query);
	}

	public String getStyle(Site site) {
		String css = null;
		if (StringUtils.isNotEmpty(site.getColors())) {
			Document doc = getColorConfig(site.getTemplateGroup());
			css = getStyle(site.getTemplateGroup());
			if (doc != null && StringUtils.isNotEmpty(css)) {
				Map<String, String> map = new HashMap<String, String>();
				for (String s : site.getColors().split(";")) {
					String[] ss = s.split(":");
					if (ss.length > 1 && StringUtils.isNotEmpty(ss[1])) {
						map.put(ss[0], ss[1]);
					}
				}
				List<Element> list = doc.selectNodes("/root/color");
				for (Element e : list) {
					String value = e.elementText("value");
					String color = map.get(value);
					if (StringUtils.isEmpty(color) || color.length() != 6) {
						color = value;
					}
					css = css.replaceAll("#" + value, "#" + color);
					List<Element> childs = e.selectNodes("child/color");
					if (!childs.isEmpty()) {
						Color parentColor0 = getHtmlColor(value);
						Color parentColor1 = getHtmlColor(color);
						for (Element c : childs) {
							String cv = c.elementText("value");
							Color childColor0 = getHtmlColor(cv);
							Color childColor1 = getChildColor(parentColor0, childColor0, parentColor1);
							String nv = Integer.toHexString(childColor1.getRGB());
							css = css.replaceAll("#" + cv, "#" + nv.substring(2, 8));
						}
					}
				}
			}
		} else {
			String defaultCssName = getThemeClasspath(site.getTemplateGroup()) + "/style_01/style_comm.css";
			css = FileUtil.readFile(this.getClass().getClassLoader().getResourceAsStream(defaultCssName));
		}
		return css;
	}

	public Document getColorConfig(TemplateGroup group) {
		StringBuilder sb = new StringBuilder(getThemeClasspath(group));
		sb.append("/color_mb.xml");
		SAXReader saxReader = new SAXReader();
		Document doc = null;
		try {
			doc = saxReader.read(this.getClass().getClassLoader().getResourceAsStream(sb.toString()));
		} catch (DocumentException ex) {
			log.error(ex.getMessage(), ex);
		}
		return doc;
	}

	private String getThemeClasspath(TemplateGroup group) {
		return "static/site-template/css/front/theme" + group.getId();
	}

	private String getThemeDir(TemplateGroup group) {
		return this.getClass().getClassLoader().getResource("").getPath() + getThemeClasspath(group);
	}

	private String getStyle(TemplateGroup group) {
		StringBuilder sb = new StringBuilder();
		sb.append(getRootPath());
		sb.append("/css/front");
		sb.append("/theme").append(group.getId());
		log.debug(sb.toString());
		File colorTemplate = new File(sb.toString() + "/style_01/style_comm.css");
		String css = FileUtil.readFile(colorTemplate);
		return css;
	}

	private String getRootPath() {
		return this.getClass().getClassLoader().getResource("").getPath() + "static/site-template";
	}

	private Color getChildColor(Color parentColor0, Color childColor0, Color parentColor1) {
		float[] hp0 = Color.RGBtoHSB(parentColor0.getRed(), parentColor0.getGreen(), parentColor0.getBlue(), null);
		float[] hp1 = Color.RGBtoHSB(parentColor1.getRed(), parentColor1.getGreen(), parentColor1.getBlue(), null);
		float[] hcc = Color.RGBtoHSB(childColor0.getRed(), childColor0.getGreen(), childColor0.getBlue(), null);
		for (int i = 0; i < 3; i++) {
			hcc[i] *= hp1[i] / hp0[i];
			if (i > 0 && hcc[i] > 1) {
				hcc[i] = 1;
			}
		}
		return new Color(Color.HSBtoRGB(hp1[0], hcc[1], hcc[2]));
	}

	private Color getHtmlColor(String value) {
		return new Color(Integer.parseInt(value.substring(0, 2), 16), Integer.parseInt(value.substring(2, 4), 16), Integer.parseInt(value.substring(4, 6), 16));
	}

	public void createTempStyle(Site site) {
		createStyle(site, true);
	}

	public void createStyle(Site site) {
		createStyle(site, false);
	}

	private void createStyle(Site site, boolean temp) {
		String style = getStyle(site);
		String path = getThemeDir(site.getTemplateGroup());
		if(temp){
			path += "/temp_style/" + site.getId() + ".css";
		}else{
			path += site.getId() + ".css";
		}
		try {
			FileUtil.writeFile(path, style);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
