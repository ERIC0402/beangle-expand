/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.widgets.contentTop;

import java.util.List;
import java.util.Map;

import org.beangle.website.cms.template.widgets.PageLimit;
import org.beangle.website.cms.template.widgets.WidgetSupport;

import net.sf.json.JSONObject;

import org.beangle.website.cms.model.Column;
import org.beangle.website.cms.model.ColumnContent;
import org.beangle.website.cms.model.Site;

/**
 * 
 * @author CHII
 */
public class ContentTopWidget3 extends WidgetSupport {
	@Override
	protected void configSetting(Map<String, Object> root, JSONObject json) {
		root.put("columns", findColumn());
	}

	@Override
	protected void viewSetting(Map<String, Object> root, JSONObject json) {
		Integer count = getIntFromJSON(json, "count");
		Site site = getSite();
		if (site != null) {
			PageLimit pageLimit = new PageLimit();
			pageLimit.setPageNo(1);
			pageLimit.setPageSize(count == null ? 8 : count);
			List<ColumnContent> list = findNewContent(getSite(), pageLimit);
			root.put("contents", list);
		}
		Long cid = getLongFromJSON(json, "columnId");
		if (cid != null) {
			Column column = (Column) entityDao.get(Column.class, cid);
			root.put("column", column);
		}
	}
}
