/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package org.beangle.website.cms.template.widgets.theme61.contentTop;

import java.util.Date;
import java.util.Map;

import org.beangle.website.cms.template.widgets.ContentTopWidgetSample;

import net.sf.json.JSONObject;
/**
*
* @author CHII
*/
public class ContentTopWidget2 extends ContentTopWidgetSample {
@Override
protected void viewSetting(Map<String, Object> root, JSONObject json) {
super.viewSetting(root, json);
put("today", new Date());
}
}