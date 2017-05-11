/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.widgets.theme2.contentTop;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.Order;
import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.EntityQuery;
import org.beangle.website.cms.template.widgets.PageLimit;
import org.beangle.website.cms.template.widgets.WidgetSupport;

import org.beangle.website.cms.model.Column;
import org.beangle.website.cms.model.Site;

/**
 * 
 * @author CHII
 */
public class ContentTopWidget extends WidgetSupport {
	@Override
	protected void configSetting(Map<String, Object> root, JSONObject json) {
		EntityQuery query = new EntityQuery(Column.class, "c");
		Site site = getSite();
		if (site != null) {
			query.add(new Condition("c.site.id=:sid", site.getId()));
		} else {
			query.add(new Condition("1!=1"));
		}
		query.add(new Condition("c.enabled=1"));
		query.add(new Condition("c.type.id=:tid", 16L));
		query.addOrder(Order.parse("c.orders"));
		root.put("columnList", entityDao.search(query));
	}

	@Override
	protected void viewSetting(Map<String, Object> root, JSONObject json) {
		Long cid = getLongFromJSON(json, "columnId");
		Integer count = getIntFromJSON(json, "count");
		Integer picNum = getIntFromJSON(json, "picNum");
		String hiddenPic = getStringFromJSON(json, "hiddenPic");
		String hiddenContent = getStringFromJSON(json, "hiddenContent");
		String showDate = getStringFromJSON(json, "showDate");
		Site site = getSite();
		if (cid != null) {
			PageLimit pageLimit = new PageLimit();
			pageLimit.setPageNo(1);
			Column column = (Column) entityDao.get(Column.class, cid);
			if (StringUtils.isEmpty(hiddenPic)) {
				pageLimit.setPageSize(picNum == null ? 4 : picNum);
				put("picContents", findContent(column, pageLimit, true));
			}
			root.put("column", column);
			pageLimit.setPageSize(count == null ? 8 : count);
			root.put("contents", findContent(column, pageLimit, false));
			root.put("hiddenPic", hiddenPic);
			root.put("hiddenContent", hiddenContent);
			root.put("picNum", picNum);
			root.put("showDate", showDate);
		}
		root.put("site", site);
	}
}
