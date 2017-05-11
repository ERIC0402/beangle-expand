/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.widgets;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.beangle.website.cms.model.Column;

/**
 * 
 * @author CHII
 */
public class ColumnWidgetSample extends WidgetSupport {
	@Override
	protected void configSetting(Map<String, Object> root, JSONObject json) {
	}

	@Override
	protected void viewSetting(Map<String, Object> root, JSONObject json) {
		Column column = getColumn();
		if (column != null) {
			Column topColumn = getTopColumn(column);
			root.put("topColumn", topColumn);
			if (topColumn != null) {
				List<Column> list = findColumn(root, topColumn, column);
				root.put("columns", list);
			}
		}
		root.put("column", column);
	}
}
