/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.widgets.link;

import java.util.List;
import java.util.Map;

import org.beangle.website.cms.template.widgets.WidgetSupport;

import net.sf.json.JSONObject;

import org.beangle.website.cms.model.Site;

/**
 * 
 * @author CHII
 */
public class LinkWidget4 extends WidgetSupport {
	@Override
	protected void viewSetting(Map<String, Object> root, JSONObject json) {
		Site site = getSite();
		String num = "";
		if (site.getId() != null) {
			String hql = "select sum(views) from Statistics where columns.site.id=" + site.getId();
			List list = null;
			list = entityDao.searchHQLQuery(hql);
			if (list.size() > 0) {
				num = list.get(0).toString();
			}
		}
		root.put("num", num);
	}

	@Override
	protected void configSetting(Map<String, Object> root, JSONObject json) {
	}
}
