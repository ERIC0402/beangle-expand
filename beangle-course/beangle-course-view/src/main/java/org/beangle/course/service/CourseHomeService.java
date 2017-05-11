package org.beangle.course.service;

import java.util.List;
import java.util.Map;

import org.beangle.zyk.course.model.Course;
import org.beangle.zyk.course.model.CourseColumn;

public interface CourseHomeService {

	/**
	 * 获取课程简介
	 * @return
	 */
	public CourseColumn getCourseColumnIntroduction(String name);
	
	/**
	 * 获取有效的一级课程栏目
	 * 课程栏目
	 * @return
	 */
	public List getFirstCourseColumnList();
	
	/**
	 * 通过课程获取推荐资源
	 * @return
	 */
	public List getCourseResourceList(Course course);
	
	/**
	 * 通过课程获取热点资源（根据资源的访问人次排序取前面的）
	 * @return
	 */
	public List getHotResourceListByCourse(Course course);
	
	/**
	 * 根据根据课程类型分类统计数量
	 * @param course
	 * @return
	 */
	public Map getKczyCountMap(Course course);
}
