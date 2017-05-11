/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.widgets.theme61.list;

import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 
 * @author CHII
 */
public class ContentListWidget extends org.beangle.website.cms.template.widgets.list.ContentListWidget {
	@Override
	protected void viewSetting(Map<String, Object> root, JSONObject json) {
		// Column column = getColumn();
		// PageLimit limit = getPageLimit();
		// root.put("column", column);
		// root.put("contents", findContent(column, limit,false));
		// root.put("limit", limit);
		super.viewSetting(root, json);
	}

	@Override
	protected void configSetting(Map<String, Object> root, JSONObject json) {
	}
}
