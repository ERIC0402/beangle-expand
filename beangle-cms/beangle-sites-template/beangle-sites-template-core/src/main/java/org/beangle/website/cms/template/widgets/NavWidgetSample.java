/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.widgets;

import java.util.Map;

import net.sf.json.JSONObject;

import org.beangle.commons.collection.Order;
import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.EntityQuery;

import org.beangle.website.cms.model.Column;
import org.beangle.website.cms.model.Site;

/**
 * 
 * @author CHII
 */
public class NavWidgetSample extends WidgetSupport {
	@Override
	protected void viewSetting(Map<String, Object> root, JSONObject json) {
		Site site = getSite();
		if (site != null) {
			EntityQuery query = new EntityQuery(Column.class, "c");
			query.add(Condition.eq("site.id", site.getId()));
			query.add(new Condition("c.columns is not null"));
			query.add(new Condition("c.enabled = true"));
			query.add(new Condition("c.visible = true"));
			query.addOrder(new Order("c.orders"));
			root.put("columns", entityDao.search(query));
		}
		put("topColumn", getTopColumn(null));
		put("site", site);
	}

	@Override
	protected void configSetting(Map<String, Object> root, JSONObject json) {
	}
}
