/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.widgets.contentTop;

import java.util.Map;

import org.beangle.website.cms.template.widgets.PageLimit;
import org.beangle.website.cms.template.widgets.WidgetSupport;

import net.sf.json.JSONObject;

import org.beangle.website.cms.model.Column;
import org.beangle.website.cms.model.Site;

/**
 * 
 * @author CHII
 */
public class ContentTopWidget5 extends WidgetSupport {
	@Override
	protected void configSetting(Map<String, Object> root, JSONObject json) {
		put("columnList", findColumn());
	}

	@Override
	protected void viewSetting(Map<String, Object> root, JSONObject json) {
		Long cid = getLongFromJSON(json, "columnId");
		Site site = getSite();
		if (cid != null) {
			Column column = (Column) entityDao.get(Column.class, cid);
			put("column", column);
		}
		Integer count = getIntFromJSON(json, "count");
		PageLimit pageLimit = new PageLimit();
		pageLimit.setPageNo(1);
		pageLimit.setPageSize(count == null ? 8 : count);
		put("contents", findHotContent(site, pageLimit, false));
		put("site", site);
	}
}
