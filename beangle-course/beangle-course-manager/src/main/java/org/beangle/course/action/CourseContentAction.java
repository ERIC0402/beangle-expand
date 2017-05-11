package org.beangle.course.action;

import java.util.List;

import org.beangle.commons.lang.StrUtils;
import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.common.action.BaseCommonAction;
import org.beangle.zyk.core.model.Resource;
import org.beangle.zyk.course.model.Course;
import org.beangle.zyk.course.model.CourseBook;
import org.beangle.zyk.course.model.CourseColumn;
import org.beangle.zyk.course.model.CourseContent;
import org.beangle.zyk.course.model.CourseSection;

/**
 * 课程内容
 * @author dell
 *
 */
public class CourseContentAction extends BaseCommonAction{

	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		Long courseId = getLong("courseId");
		OqlBuilder<?> oql =  (OqlBuilder<?>) super.getQueryBuilder();
		if(null!=courseId){
			oql.where(getShortName()+".courseSection.course.id =:courseId",courseId);
			Course course = entityDao.get(Course.class, courseId);
			put("course", course);
		}
		return oql;
	}
	
	protected void editSetting(Entity<?> entity) {
	   Long courseId = getLong("courseId");
	   Course course =entityDao.get(Course.class, courseId);
	   put("course", course);
	   
	   CourseContent courseContent = getEntity(CourseContent.class, "courseContent");
       put("courseContent", courseContent);
       
       if(null!=courseContent.getCourseSection()){
           OqlBuilder<CourseContent> oql = OqlBuilder.from(CourseContent.class, "courseContent");
           oql.where("courseContent.id =:courseContentId",courseContent.getId());
    	   List	courseContents = entityDao.search(oql);
    	   put("courseContents", courseContents);
    	   put("courseSection", courseContent.getCourseSection());
       }
       
       //章节
       OqlBuilder<CourseSection> query = OqlBuilder.from(CourseSection.class, "courseSection");
       query.where("courseSection.course.id =:courseId",courseId);
	   query.orderBy("code");
	   List	courseSections = entityDao.search(query);
	   put("courseSections", courseSections);
	}
	
	protected String saveAndForward(Entity<?> entity) {
	   Long[] resourceNo = StrUtils.splitToLong(get("resourceIndex"));
	   Long courseSectionId = getLong("courseSection.id");
	   CourseSection courseSection = entityDao.get(CourseSection.class, courseSectionId);
	   Long courseContentId =getLong("courseContentId");
	   if(null!=courseContentId){
		   CourseContent courseContent = entityDao.get(CourseContent.class, courseContentId);
		   courseContent.setCourseSection(courseSection);
		   entityDao.save(courseContent);
	   }else{
		   for(int i = 0;i<resourceNo.length;i++){
			   CourseContent courseContent = null;
			   courseContentId = getLong("courseContentId"+resourceNo[i]);
			   if(null!=courseContentId){
				   courseContent = entityDao.get(CourseContent.class, courseContentId);
			   }else{
				   courseContent = new CourseContent();
			   }
			   Long resourceId = getLong("resourceId"+resourceNo[i]);
			   Resource courseResource = entityDao.get(Resource.class, resourceId);
			   
			   courseContent.setCourseResource(courseResource);
			   courseContent.setCourseSection(courseSection);
			   entityDao.save(courseContent);
		   }
	   }
	   return redirect("search", "info.save.success","&courseId="+courseSection.getCourse().getId());
	}
	
	/**
	 * 资源推荐
	 * @return
	 */
	public String recommendCourseSection(){
		Boolean isRecommend = getBoolean("isRecommend");
		
		Long courseId = getLong("courseId");
	    Course course = getEntity(Course.class, "course");
	    put("course", course);
	    
	    Long courseContentId =getLong("courseContentId");
		CourseContent courseContent = entityDao.get(CourseContent.class, courseContentId);
		courseContent.setSftj(isRecommend);
		entityDao.saveOrUpdate(courseContent);
		return redirect("search", "info.save.success","&courseId="+courseId);
	}
	
	@Override
	protected String getEntityName() {
		return CourseContent.class.getName();
	}
}
