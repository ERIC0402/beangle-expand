package org.beangle.course.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.course.service.CourseSectionService;
import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.util.HierarchyEntityUtil;
import org.beangle.website.common.action.BaseCommonAction;
import org.beangle.zyk.core.model.Resource;
import org.beangle.zyk.course.model.Course;
import org.beangle.zyk.course.model.CourseContent;
import org.beangle.zyk.course.model.CourseSection;

/**
 * 课程章节
 * @author dell
 *
 */
public class CourseSectionAction extends BaseCommonAction{
	private CourseSectionService courseSectionService;

	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		Long courseId = getLong("courseId");
		
		OqlBuilder<?> oql =  (OqlBuilder<?>) super.getQueryBuilder();
		if(null!=courseId){
			oql.where(getShortName()+".course.id =:courseId",courseId);
			Course course = entityDao.get(Course.class, courseId);
			put("course", course);
		}
		oql.orderBy(getShortName()+".code");
		return oql;
	}
	
	protected void editSetting(Entity<?> entity) {
	   Long courseId = getLong("courseId");
	   Course course =entityDao.get(Course.class, courseId);
	   put("course", course);
	   
	   CourseSection courseSection = getEntity(CourseSection.class, "courseSection");
       put("courseSection", courseSection);
       
       
       CourseSection tree = (CourseSection) entity;
		List<CourseSection> folders = CollectUtils.newArrayList();
		OqlBuilder<CourseSection> query = OqlBuilder.from(CourseSection.class, "t");
		query.where("t.course.id =:courseId",courseId);
		query.orderBy("code");
		folders = entityDao.search(query);
		folders.removeAll(HierarchyEntityUtil.getFamily(tree));
		put("parents", folders);
       
	}
	
	protected String saveAndForward(Entity<?> entity) {
	   Long courseId = getLong("courseId");
	   Course course =entityDao.get(Course.class, courseId);
       
       CourseSection courseSection = (CourseSection) entity;
       courseSection.setCourse(course);
		try {
			Long newParentId = getLong("parent.id");
			int indexno = getInteger("indexno");
			CourseSection parent = null;
			if (null != newParentId) {
				parent = entityDao.get(CourseSection.class, newParentId);
			}
			courseSectionService.move(courseSection, parent, indexno);
			entityDao.saveOrUpdate(courseSection);
			
			//保存课程资源
			 Long[] resourceNo = StrUtils.splitToLong(get("resourceIndex"));
			 List ccList = new ArrayList(courseSection.getCourseContentSet());
			 for(int i = 0;i<resourceNo.length;i++){
				   CourseContent courseContent = null;
				   Long courseContentId = getLong("courseContentId"+resourceNo[i]);
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
				   
				   if(ccList.contains(courseContent)){
					   ccList.remove(courseContent);
				   }
			 }
			 //需要处理掉不存在的资源
			 for(Iterator<CourseContent> it=ccList.iterator();it.hasNext();){
				 CourseContent courseContent = it.next();
				 if(null!=courseContent.getId())
					 entityDao.remove(CourseContent.class, "id", courseContent.getId());
			 }
		} catch (Exception e) {
			e.printStackTrace();
			return forward(ERROR);
		}
	   return redirect("search", "info.save.success","&courseId="+courseId);
	}
	
	@Override
	protected String getEntityName() {
		return CourseSection.class.getName();
	}

	public CourseSectionService getCourseSectionService() {
		return courseSectionService;
	}

	public void setCourseSectionService(CourseSectionService courseSectionService) {
		this.courseSectionService = courseSectionService;
	}
}
