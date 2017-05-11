/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.widgets.list;

import java.util.Map;

import org.beangle.website.cms.template.widgets.PageLimit;

import net.sf.json.JSONObject;


/**
 * 横排图片列表
 * 
 * @author CHII
 */
public class ImgItemListWidget2 extends ContentListWidget {
	@Override
	protected void configSetting(Map<String, Object> root, JSONObject json) {
	}

	@Override
	protected void viewSetting(Map<String, Object> root, JSONObject json) {
		super.viewSetting(root, json);
	}

	@Override
	protected PageLimit getPageLimit() {
		PageLimit limit = super.getPageLimit();
		limit.setPageSize(18);
		return limit;
	}
}
