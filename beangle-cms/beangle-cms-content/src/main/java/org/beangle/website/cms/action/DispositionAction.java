package org.beangle.website.cms.action;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.convention.route.Action;
import org.beangle.website.cms.action.ContentCommonAction;
import org.beangle.website.cms.model.Disposition;

/**
 * 留言处理
 * @author GOKU
 *
 */
public class DispositionAction extends ContentCommonAction {

	@Override
	protected String getEntityName() {
		return Disposition.class.getName();
	}
	
	public String index() {
    	return forward(new Action(this, "search"));
    }
	
	protected OqlBuilder<?> getQueryBuilder(){
		OqlBuilder<Disposition> query = OqlBuilder.from(Disposition.class,"disposition");
		query.where("disposition.onlineMess.back=0");
		query.where("disposition.state=0");
		query.where("disposition.user.id=:uid",getCurrentUser().getId());
		populateConditions(query);
		query.limit(getPageLimit());
		query.orderBy(getOrderString("disposition.onlineMess.time desc"));
		return query;
	}
	
	public String edit(){
		Disposition disposition = (Disposition) getEntity(Disposition.class,"disposition");
		put("disposition",disposition);
		OqlBuilder<Disposition> dquery = OqlBuilder.from(Disposition.class,"d");
		dquery.where("d.onlineMess.id=:mid",disposition.getOnlineMess().getId());
		dquery.orderBy("d.time");
		put("clryjs",entityDao.search(dquery));
		return forward();
	}
	
	public String save(){
		Disposition d = (Disposition) populateEntity(Disposition.class,"disposition");
		d.setState(true);
		d.setIpAddr(getRemoteAddr());
		d.setTime(new Timestamp(System.currentTimeMillis()));
		entityDao.saveOrUpdate(d);
		return redirect("search","处理成功");
	}
}
