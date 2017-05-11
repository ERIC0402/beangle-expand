/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.widgets.theme61.others;

import java.util.List;
import java.util.Map;

import org.beangle.website.cms.template.widgets.WidgetSupport;

import net.sf.json.JSONObject;

import org.beangle.website.cms.model.Column;

/**
 * 
 * @author CHII
 */
public class TeacherInroduceWidget extends WidgetSupport {
	@Override
	protected void configSetting(Map<String, Object> root, JSONObject json) {
	}

	@Override
	protected void viewSetting(Map<String, Object> root, JSONObject json) {
		Column column = getColumn();
		if (column != null) {
			Column topColumn = getTopColumn(column);
			if (topColumn != null) {
				root.put("topColumn", topColumn);
				root.put("column", column);
				String hql = "from Column where orders like ? order by orders";
				Object[] params = new Object[] { topColumn.getOrders().substring(0, 6) + "%" };
				List<Column> list = entityDao.searchHQLQuery(hql, params);
				root.put("columns", list);
			}
		}
	}
}