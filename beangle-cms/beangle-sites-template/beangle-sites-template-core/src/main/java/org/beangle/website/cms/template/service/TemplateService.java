/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.service;

import java.util.List;
import org.dom4j.Document;
import org.beangle.website.cms.model.Site;
import org.beangle.website.cms.model.SiteWidgetConfig;
import org.beangle.website.cms.model.Template;
import org.beangle.website.cms.model.TemplateGroup;

/**
 * 
 * @author CHII
 */
public interface TemplateService {
	
	public String getContent(Long templateId, Long siteId);

	public String getContent(Template template, Site site);

	public String getStyle(Site site);

	public Document getColorConfig(TemplateGroup group);

	public String createTemplate(Template indexTemplate, Site site);

	public String createTemplate(Template indexTemplate, Site site, boolean recreate);

	public List<SiteWidgetConfig> findSiteWidgetConfig(Long siteId, Long templateId);

	public List<Template> findTemplate(TemplateGroup templateGroup);

	public void createTempStyle(Site site);
	public void createStyle(Site site);
}
