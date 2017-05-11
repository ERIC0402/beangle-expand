/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.widgets.theme61.list;

import java.util.Map;

import org.beangle.website.cms.template.widgets.PageLimit;
import org.beangle.website.cms.template.widgets.WidgetSupport;

import net.sf.json.JSONObject;

import org.beangle.website.cms.model.Column;

/**
 * 
 * @author CHII
 */
public class ContentListWidget3 extends WidgetSupport {
	@Override
	protected void viewSetting(Map<String, Object> root, JSONObject json) {
		Column column = getColumn();
		PageLimit limit = getPageLimit();
		root.put("column", column);
		root.put("contents", findContent(column, limit, false));
		root.put("limit", limit);
	}

	@Override
	protected void configSetting(Map<String, Object> root, JSONObject json) {
	}
}
