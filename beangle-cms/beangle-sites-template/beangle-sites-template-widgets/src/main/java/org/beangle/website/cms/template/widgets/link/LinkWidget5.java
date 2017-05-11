/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.widgets.link;

import java.util.HashMap;
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
public class LinkWidget5 extends WidgetSupport {
	@Override
	protected void viewSetting(Map<String, Object> root, JSONObject json) {
		Site site = getSite();
		Map columnMap = new HashMap();
		Long cid = null;
		for (int i = 1; i <= 5; i++) {
			cid = getLongFromJSON(json, "columnId" + i);
			if (cid != null) {
				PageLimit pageLimit = new PageLimit();
				pageLimit.setPageNo(1);
				Column column1 = (Column) entityDao.get(Column.class, cid);
				root.put("column" + i, column1);
				columnMap.put("column" + i, column1);
				Integer count = getIntFromJSON(json, "num" + i);
				pageLimit.setPageSize(count == null ? 8 : count);
				root.put("contents" + i, findTopContent(column1, pageLimit, false));
			}
		}
		root.put("site", site);
		root.put("columnMap", columnMap);
	}

	@Override
	protected void configSetting(Map<String, Object> root, JSONObject json) {
		root.put("columnList", findColumn());
	}
}
