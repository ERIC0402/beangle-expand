package org.beangle.website.cms.action;

import java.sql.Date;

import org.beangle.commons.collection.Order;
import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.EntityQuery;
import org.beangle.struts2.helper.QueryHelper;
import org.beangle.website.cms.action.ContentCommonAction;
import org.beangle.website.cms.model.Statistics;

import com.opensymphony.xwork2.ActionContext;

public class  ColumnStatisticsAction extends ContentCommonAction {

	/**
	 * 信息日志   
	 * 
	 * @param args
	 */
	public String index() {
		put("sites",getSitesByCurrentUser());//当前用户所能管理的站点
		return forward();
	}
	
	public String search(){
		EntityQuery query = new EntityQuery(Statistics.class,"statistics");
		QueryHelper.populateConditions(query);
		Date time= getDate("startime");
		Date time2= getDate("endtime");
		ActionContext.getContext().getSession().put("startDate", time);
		ActionContext.getContext().getSession().put("endDate", time2);
		String subEnabled=get("subColumn1.enabled");
		String siteName=get("subColumn.enabled");
		
		
		if(time2 != null && time != null){
			query.add(new Condition("statistics.viewDate >= :startime",time));
			System.out.println("===="+query.toQueryString());
			query.add(new Condition("statistics.viewDate <= :endtime",time2));
		}
		else if(time != null ){
			query.add(new Condition("statistics.viewDate >= :startime",time));		
		}
		else if(time2 != null ){
			query.add(new Condition("statistics.viewDate <= :endtime",time2));
		}
		if("0001".equals(siteName)){
			query.add(new Condition("statistics.columns.site.code ='0001'"));
		}
		else if("0002".equals(siteName)){
			query.add(new Condition("statistics.columns.site.code =''"));
		}
		else if ("0003".equals(siteName)){
			query.add(new Condition("statistics.columns.site.code ='0003'"));
		}
		else if("0101".equals(siteName)){
			query.add(new Condition("statistics.columns.site.code ='0101'"));
		}
		query.setOrders(Order.parse("statistics.columns.orders"));//get("orderBy")));
		query.setLimit(getPageLimit());
		query.setSelect(" statistics.columns.name,statistics.columns.site.name,statistics.columns.orders, sum(statistics.views)");
		query.groupBy("statistics.columns.site.name,statistics.columns.name,statistics.columns.orders");	
		put("columnsStatisticses", entityDao.search(query));
		return forward();
	}
	
	public String detail(){
		String columnOrders=get("orders");
		
		Date startDate=(Date)ActionContext.getContext().getSession().get("startDate");
		Date endDate=(Date)ActionContext.getContext().getSession().get("endDate");
		
		
		EntityQuery query = new EntityQuery(Statistics.class,"statistics");
		query.add(new Condition("statistics.columns.orders=:orders",columnOrders));
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
