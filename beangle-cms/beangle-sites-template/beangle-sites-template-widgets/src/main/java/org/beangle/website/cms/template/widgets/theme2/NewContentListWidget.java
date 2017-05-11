/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.widgets.theme2;

import java.util.Map;

import org.beangle.website.cms.template.widgets.PageLimit;
import org.beangle.website.cms.template.widgets.WidgetSupport;

import net.sf.json.JSONObject;

import org.beangle.website.cms.model.Column;

/**
 * 
 * @author CHII
 */
public class NewContentListWidget extends WidgetSupport {
	@Override
	protected void viewSetting(Map<String, Object> root, JSONObject json) {
		Column column = getColumn();
		PageLimit limit = getPageLimit();
		root.put("column", column);
		root.put("contents", findNewContent(getSite(), limit));
		root.put("limit", limit);
	}

	@Override
	protected void configSetting(Map<String, Object> root, JSONObject json) {
	}
}
