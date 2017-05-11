package org.beangle.course.service;

import java.util.List;

import org.beangle.zyk.course.model.CourseColumn;

public interface CourseColumnService {

	/**
	 * 移动课程栏目到指定的位置
	 * 
	 * @param menu
	 * @param location
	 *            新的位置
	 * @param indexno
	 *            新位置的顺序号
	 */
	public void move(CourseColumn menu, CourseColumn parent, int indexno);
	
	/**
	 * 获取有效的课程栏目
	 * @return
	 */
	public List getCourseColumnList();
}
