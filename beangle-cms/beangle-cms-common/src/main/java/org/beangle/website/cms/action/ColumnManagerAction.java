package org.beangle.website.cms.action;

import java.util.Set;

import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.cms.model.Column;
import org.beangle.website.cms.model.Site;


public class ColumnManagerAction extends CommonAction {

	@Override
	protected String getEntityName() {
		return Column.class.getName();
	}
	
	@Override
	protected void indexSetting() {
		put("idValues",get("idValues"));
		put("nameValues",get("nameValues"));
		put("isSingle",get("isSingle"));
		Set<Site> sites = getSitesByCurrentUser();
		if(sites != null && sites.size() > 0){
			put("site",sites.iterator().next());
		}else{
			put("site",new Site());
		}
		put("sites",getSitesByCurrentUser());
		
	}
	
	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		put("idValues",get("idValues"));
		put("nameValues",get("nameValues"));
		put("isSingle",get("isSingle"));
		OqlBuilder<Column> oql = OqlBuilder.from(Column.class,"column");
		populateConditions(oql);
		oql.orderBy(getOrderString("column.orders"));
		return oql;
	}
}
