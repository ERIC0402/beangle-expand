/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.widgets.theme61.link;

import java.util.Map;

import org.beangle.website.cms.template.widgets.WidgetSupport;

import net.sf.json.JSONObject;


/**
 * 
 * @author CHII
 */
public class LinkWidget1 extends WidgetSupport {
	@Override
	protected void configSetting(Map<String, Object> root, JSONObject json) {
	/*	put("englishs", findEnglish(json));
		root.put("columnList", findColumn());*/
	}

	@Override
	protected void viewSetting(Map<String, Object> root, JSONObject json) {
		/*Set<String> keys = json.keySet();
		List<Object[]> list = new ArrayList();
		String perfix = "fileds.";
		String suffix = ".no";
		for (String key : keys) {
			if (key.indexOf(perfix) == 0 && key.indexOf(suffix) < 0) {
				Object[] oo = new Object[2];
				oo[0] = key.substring(perfix.length());
				oo[1] = Integer.parseInt(json.getString(key + suffix));
				list.add(oo);
			}
		}
		Collections.sort(list, new Comparator<Object[]>() {
			public int compare(Object[] o1, Object[] o2) {
				Integer i1 = (Integer) o1[1];
				Integer i2 = (Integer) o2[1];
				if (i1 > i2) {
					return 1;
				}
				return 0;
			}
		});
		put("fileds", list);
		put("englishs", findEnglish(json));
		Long columnId = getLongFromJSON(json, "columnId");
		if (columnId != null) {
			Column column = get(Column.class, columnId);
			put("column", column);
		}*/
	}

	/*private List findEnglish(JSONObject json) {
		String ids = getStringFromJSON(json, "ids");
		Long cid = getLongFromJSON(json, "columnId");
		List<English> list = null;
		if (StringUtils.isNotEmpty(ids)) {
			String hql = "from English e where e.id in (" + ids + ")";
			list = entityDao.searchHQLQuery(hql);
			if (!list.isEmpty()) {
				// String[] ss = new String[list.size()];
				// for (int i = 0; i < list.size(); i++) {
				// ss[i] = list.get(i).getDepartment();
				// }
				EntityQuery query = new EntityQuery(Column.class, "c");
				// query.add(new Condition("c.name in (:names)", ss));
				query.add(Condition.eq("c.columns.id", cid));
				List<Column> columns = entityDao.search(query);
				put("columns", columns);
			}
		}
		return list;
	}*/
}
