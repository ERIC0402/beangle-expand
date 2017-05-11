package org.beangle.course.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.beangle.commons.lang.StrUtils;
import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.common.action.BaseCommonAction;
import org.beangle.zyk.course.model.Course;
import org.beangle.zyk.course.model.CourseRelated;

/**
 * 相关课程
 * @author dell
 *
 */
public class CourseRelatedAction extends BaseCommonAction{
	
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
	   
	  List ls  = new ArrayList(course.getRelatedCourses());
	   
	   
       List courses = entityDao.getAll(Course.class);
       put("courses", courses);
	}
	
	protected String saveAndForward(Entity<?> entity) {
	   Long courseId = getLong("courseId");
	   Course course =entityDao.get(Course.class, courseId);
	   
		//获取选中课程ID，并组建Long类型数组
		Long[] courseIds = StrUtils.splitToLong(get("courseIds"));
		List<Course> courses = entityDao.get(Course.class, courseIds);
		
		for(Iterator<Course> it = courses.iterator();it.hasNext();){
			Course courseNext = it.next();
			CourseRelated courseRelated  = new CourseRelated();
			courseRelated.setCourse(course);
			courseRelated.setCourseRelated(courseNext);
		    entityDao.saveOrUpdate(courseRelated);
		}
	   return redirect("search", "info.save.success");
	}
	
	@Override
	protected String getEntityName() {
		return CourseRelated.class.getName();
	}
}
