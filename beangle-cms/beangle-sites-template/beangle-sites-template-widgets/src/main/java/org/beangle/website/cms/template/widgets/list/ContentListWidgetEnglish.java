package org.beangle.website.cms.template.widgets.list;

import java.util.Map;

import net.sf.json.JSONObject;

public class ContentListWidgetEnglish extends ContentListWidget {
	@Override
	protected void viewSetting(Map<String, Object> root, JSONObject json) {
		/*Column column = getColumn();
		if (column != null) {
			PageLimit limit = getPageLimit();
			limit.setPageSize(10);
			root.put("cc", column);
			
			 * root.put("contents", findContent(column, limit,false));
			 * root.put("limit", limit);
			 
			EntityQuery query = new EntityQuery(English.class, "english");
			query.add(new Condition("english.department =:department", column.getName()));
			query.add(new Condition("english.enabled =:enabled", true));
			query.addOrder(new Order("name"));
			// EntityQuery query = getContentQuery(limit, isImage, doesHot);
			// query.add(new Condition("));
			query.setLimit(limit);
			List list = entityDao.search(query);
			setPage(limit, list);
			// String hql="from English e where e.department=\'"++"\'";
			// List list=entityDao.searchHQLQuery(hql);
			put("englishList", list);
		}*/
	}

	@Override
	protected void configSetting(Map<String, Object> root, JSONObject json) {
	}
	/*
	 * public List orderBy(){ EntityQuery query = new
	 * EntityQuery(English.class,"english"); query.add(new
	 * Condition("english.enabled =:enabled",true)); String orderByPras =
	 * get("orderBy"); if (StringUtils.isEmpty(orderByPras)) { orderByPras =
	 * "orders"; } query.setOrders(Order.parse(orderByPras)); List
	 * teacherList=entityDao.search(query); return teacherList; }
	 */
}
