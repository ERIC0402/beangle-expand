package org.beangle.website.cms.template.widgets.list;

import java.util.Map;

import org.beangle.website.cms.template.widgets.WidgetSupport;

import net.sf.json.JSONObject;


public class ContentListWidgetTeacher extends WidgetSupport {
	@Override
	protected void viewSetting(Map<String, Object> root, JSONObject json) {
	/*	Column column = getColumn();
		if (column != null) {
			PageLimit limit = getPageLimit();
			limit.setPageSize(10);
			root.put("cc", column);
			EntityQuery query = new EntityQuery(Teachers.class, "teacher");
			query.add(new Condition("teacher.enabled =:enabled", true));
			query.setLimit(limit);
			List list = entityDao.search(query);
			setPage(limit, list);
			put("teacherList", list);
		}*/
	}

	@Override
	protected void configSetting(Map<String, Object> root, JSONObject json) {
	}
	/*
	 * public List orderBy(){ EntityQuery query = new
	 * EntityQuery(Teachers.class,"teacher"); query.add(new
	 * Condition("teacher.enabled =:enabled",true)); String orderByPras =
	 * get("orderBy"); if (StringUtils.isEmpty(orderByPras)) { orderByPras =
	 * "orders"; } query.setOrders(Order.parse(orderByPras)); List
	 * teacherList=entityDao.search(query); return teacherList; }
	 */
}
