/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package org.beangle.website.cms.template.widgets.detail;

import java.util.Map;

import org.beangle.website.cms.template.widgets.WidgetSupport;

import net.sf.json.JSONObject;
/**
*
* @author CHII
*/
public class ContentDetailWidget extends WidgetSupport {
@Override
protected void viewSetting(Map<String, Object> root, JSONObject json) {
root.put("column", getColumn());
root.put("column_content", getColumnContent());
}
@Override
protected void configSetting(Map<String, Object> root, JSONObject json) {
}
}
