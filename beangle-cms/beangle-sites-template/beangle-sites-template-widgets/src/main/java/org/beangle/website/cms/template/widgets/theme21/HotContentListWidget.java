/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.widgets.theme21;

import java.util.Map;

import org.beangle.website.cms.template.widgets.PageLimit;
import org.beangle.website.cms.template.widgets.WidgetSupport;

import net.sf.json.JSONObject;

import org.beangle.website.cms.model.Column;

/**
 * 
 * @author CHII
 */
public class HotContentListWidget extends WidgetSupport {
	@Override
	protected void viewSetting(Map<String, Object> root, JSONObject json) {
		Column column = getColumn();
		root.put("column", column);
		PageLimit limit = getPageLimit();
		root.put("contents", findHotContent(getSite(), limit, false));
	}

	@Override
	protected void configSetting(Map<String, Object> root, JSONObject json) {
	}
}