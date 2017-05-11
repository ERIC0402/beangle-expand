package org.beangle.website.cms.template.widgets.theme41.contentTop;
import java.util.Date;
import java.util.Map;

import org.beangle.website.cms.template.widgets.WidgetSupport;

import net.sf.json.JSONObject;

/*
* 首页搜索
*/
public class SearchTopWidget extends WidgetSupport{
@Override
protected void configSetting(Map<String, Object> root, JSONObject json) {
}
@Override
protected void viewSetting(Map<String, Object> root, JSONObject json) {
put("now", new Date());
}
}
