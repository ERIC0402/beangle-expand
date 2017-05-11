package org.beangle.course.action;

import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.common.action.BaseCommonAction;
import org.beangle.zyk.course.model.Course;
import org.beangle.zyk.course.model.CourseBook;

/**
 * 课程教材
 * @author dell
 *
 */
public class CourseBookAction extends BaseCommonAction{

	
	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		Long courseId = getLong("courseId");
		
		OqlBuilder<?> oql =  (OqlBuilder<?>) super.getQueryBuilder();
		if(null!=courseId){
			oql.where(getShortName()+".course.id =:courseId",courseId);
			Course course = entityDao.get(Course.class, courseId);
			put("course", course);
		}
		return oql;
	}
	
	protected void editSetting(Entity<?> entity) {
	   Long courseId = getLong("courseId");
	   Course course =entityDao.get(Course.class, courseId);
	   put("course", course);
	   
	   CourseBook courseBook = getEntity(CourseBook.class, "courseBook");
       put("courseBook", courseBook);
	}
	
	protected String saveAndForward(Entity<?> entity) {
	   Long courseId = getLong("courseId");
	   Course course =entityDao.get(Course.class, courseId);
	   CourseBook courseBook  = populateEntity(CourseBook.class, "courseBook");
	   courseBook.setCourse(course);
       entityDao.saveOrUpdate(courseBook);
	   return redirect("search", "info.save.success");
	}
	
	@Override
	protected String getEntityName() {
		return CourseBook.class.getName();
	}
}
