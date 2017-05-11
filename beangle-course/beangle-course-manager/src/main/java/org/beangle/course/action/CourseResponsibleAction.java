package org.beangle.course.action;

import java.util.Date;

import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.common.util.CommonUtils;


/**
 * 课程负责（课程负责人管理课程）
 * @author dell
 *
 */
public class CourseResponsibleAction extends CourseAction{

	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		Long professionalId = getLong("professionalId");
		OqlBuilder<?> oql =  (OqlBuilder<?>) super.getQueryBuilder();
		Date start = getDate("start");
		Date end = getDate("end");
		if(start != null){
			oql.where(getShortName() + ".publishAt >=:start",CommonUtils.getStartTime(start));
		}
		if(end != null){
			oql.where(getShortName() + ".publishAt <=:end",CommonUtils.getEndTime(end));
		}
		if(null!=professionalId){
			oql.join(getShortName()+".professionals", "professional");
			oql.where("professional.id =:professionalId",professionalId);
		}
		return oql;
	}
}
