/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.widgets.theme61.contentTop;

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
public class ContentTopWidget1 extends WidgetSupport {
	@Override
	protected void viewSetting(Map<String, Object> root, JSONObject json) {
		Long cid = getLongFromJSON(json, "columnId");
		Site site = getSite();
		if (cid != null) {
			PageLimit pageLimit = new PageLimit();
			pageLimit.setPageNo(1);
			pageLimit.setPageSize(1);
			Column column = (Column) entityDao.get(Column.class, cid);
			root.put("column", column);
			root.put("contents", findTopContent(column, pageLimit, false));
		}
		root.put("site", site);
	}

	@Override
	protected void configSetting(Map<String, Object> root, JSONObject json) {
		root.put("columnList", findColumn());
	}
}