package org.beangle.website.cms.template.widgets.detail;

import java.util.Map;

import org.beangle.website.cms.template.widgets.WidgetSupport;

import net.sf.json.JSONObject;


public class ContentDetailWidgetEnglish extends WidgetSupport {
	@Override
	protected void configSetting(Map<String, Object> root, JSONObject json) {
	}

	@Override
	protected void viewSetting(Map<String, Object> root, JSONObject json) {
/*		Long cid = getLong("ccid");
		if (cid != null) {
			English e = (English) entityDao.load(English.class, cid);
			put("english", e);
		}*/
	}
}
