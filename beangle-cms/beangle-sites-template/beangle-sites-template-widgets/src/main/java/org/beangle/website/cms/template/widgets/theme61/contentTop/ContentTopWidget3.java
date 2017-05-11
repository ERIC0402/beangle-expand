/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.widgets.theme61.contentTop;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.beangle.website.cms.template.widgets.PageLimit;
import org.beangle.website.cms.template.widgets.WidgetSupport;

import org.beangle.website.cms.model.Column;
import org.beangle.website.cms.model.ColumnContent;
import org.beangle.website.cms.model.Site;

/**
 * 
 * @author CHII
 */
public class ContentTopWidget3 extends WidgetSupport {
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
			if (StringUtils.isNotEmpty(hiddenPic)) {
				pageLimit.setPageSize(picNum == null ? 4 : picNum);
				put("picContents", findTopContent(column, pageLimit, true));
			}
			root.put("column", column);
			pageLimit.setPageSize(count == null ? 8 : count);
			List<ColumnContent> list = findTopContent(column, pageLimit, false);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (ColumnContent cc : list) {
				try {
					Date date = sdf.parse(cc.getContent().getSubTitle());
					cc.setPublishDate(date);
				} catch (Exception e) {
				}
			}
			root.put("contents", findTopContent(column, pageLimit, false));
			root.put("hiddenPic", hiddenPic);
			root.put("hiddenContent", hiddenContent);
			root.put("picNum", picNum);
			root.put("showDate", showDate);
		}
		root.put("site", site);
	}

	@Override
	protected void configSetting(Map<String, Object> root, JSONObject json) {
		root.put("columnList", findColumn());
	}
}