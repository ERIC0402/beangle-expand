package org.beangle.website.cms.template.widgets.detail;

import java.util.Map;

import org.beangle.website.cms.template.widgets.WidgetSupport;

import net.sf.json.JSONObject;


public class InfoContentListWidget extends WidgetSupport {
	@Override
	protected void viewSetting(Map<String, Object> root, JSONObject json) {
		/*
		 * root.put("column", getColumn()); root.put("column_content",
		 * getColumnContent()); Long cid = getLong("ccid"); if (cid != null) {
		 * Teachers t = (Teachers) entityDao.load(Teachers.class, cid);
		 * put("teacher", t); }
		 */
	}

	@Override
	protected void configSetting(Map<String, Object> root, JSONObject json) {
	}
}
