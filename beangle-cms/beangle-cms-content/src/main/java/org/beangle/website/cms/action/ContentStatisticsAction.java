package org.beangle.website.cms.action;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.beangle.commons.collection.Order;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.EntityQuery;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.cms.action.ContentCommonAction;
import org.beangle.website.cms.model.Column;
import org.beangle.website.cms.model.Statistics;

import org.beangle.website.common.util.CommonUtils;
import com.opensymphony.xwork2.ActionContext;

public class ContentStatisticsAction extends ContentCommonAction {

	@Override
	protected String getEntityName() {
		return Statistics.class.getName();
	}
	/**
	 * 信息日志
	 * 
	 * @param args
	 */
	public String index() {
		put("columns", entityDao.getAll(Column.class));
		return forward();
	}
	
	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		OqlBuilder<Statistics> oql = OqlBuilder.from(Statistics.class,"statistics");
		populateConditions(oql);
		oql.orderBy(getOrderString("statistics.viewDate"));
		oql.limit(getPageLimit());
		return oql;
	}

	public String search() {
		Date time = getDate("startime");
		Date time2 = getDate("endtime");
		ActionContext.getContext().getSession().put("startDate", time);
		ActionContext.getContext().getSession().put("endDate", time2);
		Long columnid = getLong("statistics.columns.id");
		String title = get("statistics.content.title");
		String hql = "select cc.column.site.name, cc.column.orders, cc.column.name, s.content.title, sum(s.views), s.content.id from org.beangle.website.cms.model.ColumnContent cc,org.beangle.website.cms.model.Statistics s "
				+ "where s.content=cc.content ";
		if(time != null){
			hql += " and s.viewDate>=:start";
		}
		if(time2 != null){
			hql += " and s.viewDate<=:end  ";
		}
		String hql1 = "select cc.column.site.name, cc.column.orders, cc.column.name, s.content.title, sum(s.views), s.content.id from org.beangle.website.cms.model.ColumnContent cc,org.beangle.website.cms.model.Statistics s "
			+ "where s.content=cc.content ";
		if(columnid != null){
			hql += " and cc.column.id=" + columnid;
			hql1 += " and cc.column.id=" + columnid;
		}
		if(title != null){
			hql += " and cc.content.title like '%"+title+"%'";
			hql1 += " and cc.content.title like '%"+title+"%'";
		}
		hql += "group by cc.column.site.name,cc.column.name, cc.column.orders,s.content.id, s.content.title"
				+ " order by cc.column.orders asc";
		hql1 += "group by cc.column.site.name,cc.column.name, cc.column.orders,s.content.id, s.content.title"
			+ " order by cc.column.orders asc";
		EntityQuery query = new EntityQuery(hql);
		query.setLimit(getPageLimit());
		if (time != null && time2 != null) {
			Map params = new HashMap();
			if(time != null){
				params.put("start", CommonUtils.getStartTime(time));
			}
			if(time2 != null){
				params.put("end", CommonUtils.getStartTime(time2));
			}
			
			query.params(params);
			put("contentStatisticses", entityDao.search(query));
		} else {
			put("contentStatisticses", entityDao.search(query));
		}
		return forward();
	}
	
	public String detail(){
		Long contentId=getLong("contentId");
		Date startDate=(Date)ActionContext.getContext().getSession().get("startDate");
		Date endDate=(Date)ActionContext.getContext().getSession().get("endDate");

		
		EntityQuery query = new EntityQuery(Statistics.class,"statistics");
		query.add(new Condition("statistics.content.id=:contentId",contentId));
		if(startDate != null && endDate != null){
			query.add(new Condition("statistics.viewDate >= :startime",startDate));
			System.out.println("===="+query.toQueryString());
			query.add(new Condition("statistics.viewDate <= :endtime",endDate));
		}
		query.addOrder(Order.parse("statistics.viewDate"));
		put("columnsStatisticses", entityDao.search(query));
		
		return forward();
	}
}
