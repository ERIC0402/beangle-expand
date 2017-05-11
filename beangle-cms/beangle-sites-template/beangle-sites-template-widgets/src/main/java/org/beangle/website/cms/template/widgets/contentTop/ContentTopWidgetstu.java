/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.widgets.contentTop;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.EntityQuery;
import org.beangle.website.cms.template.widgets.WidgetSupport;

import org.beangle.website.cms.model.Link;
import org.beangle.website.cms.model.Site;
import org.beangle.website.cms.model.LinksType;

/**
 * 
 * @author CHII
 */
public class ContentTopWidgetstu extends WidgetSupport {
	@Override
	protected void viewSetting(Map<String, Object> root, JSONObject json) {
		Long siteTypeId = getLongFromJSON(json, "siteTypeId");
		if (siteTypeId != null) {
			LinksType st = (LinksType) entityDao.get(LinksType.class, siteTypeId);
			root.put("st", st);
			String hql = "from Link where enabled = true and site_type.id = ?";
			Object[] params = new Object[] { siteTypeId };
			List<Link> linkList = entityDao.searchHQLQuery(hql, params);
			root.put("links", linkList);
		}
	}

	@Override
	protected void configSetting(Map<String, Object> root, JSONObject json) {
		Site site = getSite();
		EntityQuery query = new EntityQuery(LinksType.class, "o");
		if (site != null) {
			query.add(Condition.eq("site.id", site.getId()));
		}
		root.put("siteTypes", entityDao.search(query));
	}
}
