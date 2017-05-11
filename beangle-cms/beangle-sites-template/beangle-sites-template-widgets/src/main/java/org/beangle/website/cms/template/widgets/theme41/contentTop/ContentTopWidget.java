package org.beangle.website.cms.template.widgets.theme41.contentTop;
import org.beangle.website.cms.template.widgets.ContentTopWidgetSample;
/**
* 首页头部信息
* @author admin
*
*/
public class ContentTopWidget extends ContentTopWidgetSample {
//	@Override
//	protected void configSetting(Map<String, Object> root, JSONObject json) {
//    	root.put("columnList",findColumn());
//    }
//
//    @Override
//    protected void viewSetting(Map<String, Object> root, JSONObject json) {
//    	Long cid = getLongFromJSON(json,"columnId");
//    	Integer count = getIntFromJSON(json,"count");
//    	Integer picNum = getIntFromJSON(json, "picNum");
//    	String  hiddenPic = getStringFromJSON(json, "hiddenPic");
//    	String  hiddenContent = getStringFromJSON(json, "hiddenContent");
//    	String  showDate = getStringFromJSON(json, "showDate");
//    	Site site = getSite();
//    	if(cid != null){
//    		PageLimit pageLimit = new PageLimit();
//    		pageLimit.setPageNo(1);
//    		
//    		Column column = (Column) entityDao.get(Column.class, cid);
//    		if(StringUtils.isNotEmpty(hiddenPic)){
//    			pageLimit.setPageSize(picNum==null?4:picNum);
//    			put("picContents",findContent(column, pageLimit,true));
//    		}
//    		root.put("column", column);
//    		pageLimit.setPageSize(count==null?8:count);
//    		root.put("contents", findHotContent(column, pageLimit, false));
//    		root.put("hiddenPic", hiddenPic);
//    		root.put("hiddenContent", hiddenContent);
//    		root.put("picNum", picNum);
//    		root.put("showDate", showDate);
//    	}
//    	root.put("site", site);
//    }
}
