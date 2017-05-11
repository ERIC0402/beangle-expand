/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.widgets.others;

import java.util.Map;

import org.beangle.website.cms.template.widgets.WidgetSupport;

import net.sf.json.JSONObject;


/**
 * 
 * @author CHII
 */
public class TeacherInroduceWidget extends WidgetSupport {
	@Override
	protected void configSetting(Map<String, Object> root, JSONObject json) {
	/*	put("columns", findColumn());
		put("teachers", findTeachers(json));*/
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
			@Override
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
		put("teachers", findTeachers(json));
		Long columnId = getLongFromJSON(json, "columnId");
		if (columnId != null) {
			Column column = get(Column.class, columnId);
			put("column", column);
		}*/
	}

	/*private List findTeachers(JSONObject json) {
		String ids = getStringFromJSON(json, "ids");
		List<Teachers> list = null;
		if (StringUtils.isNotEmpty(ids)) {
			String hql = "from Teachers t where t.id in (" + ids + ")";
			list = entityDao.searchHQLQuery(hql);
		}
		return list;
	}*/
}
