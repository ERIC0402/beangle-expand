/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.widgets;

import java.util.Map;

import net.sf.json.JSONObject;

import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.EntityQuery;

import org.beangle.website.cms.model.Link;
import org.beangle.website.cms.model.LinksType;
import org.beangle.website.cms.model.Site;

/**
 * 
 * @author CHII
 */
public class LinkWidgetSample extends WidgetSupport {
	@Override
	protected void viewSetting(Map<String, Object> root, JSONObject json) {
		Long siteTypeId = getLongFromJSON(json, "siteTypeId");
		if (siteTypeId != null) {
			LinksType siteType = get(LinksType.class, siteTypeId);
			put("siteType", siteType);
			String hql = "from "+Link.class.getName()+" where enabled = true and linkType.id = ? order by orders";
			Object[] params = new Object[] { siteTypeId };
			root.put("links", entityDao.searchHQLQuery(hql, params));
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
