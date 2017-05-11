package org.beangle.course.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.beangle.course.service.CourseHomeService;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.zyk.course.model.Course;
import org.beangle.zyk.course.model.CourseColumn;
import org.beangle.zyk.course.model.CourseContent;

public class CourseHomeServiceImpl  extends BaseServiceImpl implements CourseHomeService{

	/**
	 * 获取有效的一级课程栏目
	 * 课程栏目
	 * @return
	 */
	public List getFirstCourseColumnList(){
		OqlBuilder<CourseColumn> query = OqlBuilder.from(CourseColumn.class, "courseColumn");
		query.where("courseColumn.columnType.code =:code","COURSE_COLUMN_TYPE_02");//一级栏目
		query.where("courseColumn.enabled is true");
		query.orderBy("courseColumn.code");
 		List ls =  entityDao.search(query);
		return ls;
	}
	
	/**
	 * 获取课程简介
	 * @return
	 */
	public CourseColumn getCourseColumnIntroduction(String name){
		OqlBuilder<CourseColumn> query = OqlBuilder.from(CourseColumn.class, "courseColumn");
		query.where("courseColumn.name =:name",name);//一级栏目
		query.where("courseColumn.enabled is true");
		query.orderBy("courseColumn.code");
 		List ls =  entityDao.search(query);
		return ls.size()>0?(CourseColumn)ls.get(0):null;
	}
	
	/**
	 * 通过课程获取推荐资源
	 * @return
	 */
	public List getCourseResourceList(Course course){
		OqlBuilder<CourseContent> query = OqlBuilder.from(CourseContent.class, "courseContent");
		query.where("courseContent.courseSection.course =:course",course);//一级栏目
		query.where("courseContent.sftj is true");
		query.select("courseContent.courseResource");
 		List ls =  entityDao.search(query);
		return ls;
	}
	
	/**
	 * 通过课程获取热点资源（根据资源的访问人次排序取前面的）
	 * @return
	 */
	public List getHotResourceListByCourse(Course course){
		OqlBuilder<CourseContent> query = OqlBuilder.from(CourseContent.class, "courseContent");
		query.where("courseContent.courseSection.course =:course",course);
		query.select("courseContent.courseResource");
 		List ls =  entityDao.search(query);
		return ls;
	}
	
	
	/**
	 * 根据根据课程类型分类统计数量
	 * @param course
	 * @return
	 */
	public Map getKczyCountMap(Course course){
		Map kczyCountMap = new HashMap();
		OqlBuilder<CourseContent> query = OqlBuilder.from(CourseContent.class, "courseContent");
		query.groupBy("courseContent.kczylx.id");
		query.where("courseContent.courseSection.course.id =:courseId",course.getId());
		query.select("courseContent.kczylx.id,count(courseContent.kczylx.id)");
 		List ls =  entityDao.search(query);
 		for(Iterator it = ls.iterator();it.hasNext();){
 			Object[] obj = (Object[]) it.next();
 			kczyCountMap.put((obj[0].toString()), obj[1].toString());
 		}
		return kczyCountMap;
	}
}
