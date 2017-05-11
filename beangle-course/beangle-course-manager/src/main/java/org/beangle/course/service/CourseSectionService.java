package org.beangle.course.service;

import org.beangle.zyk.course.model.CourseSection;

/**
 * 课程章节处理接口
 * @author dell
 *
 */
public interface CourseSectionService {

	/**
	 * 移动课程章节到指定的位置
	 * 
	 * @param menu
	 * @param location
	 *            新的位置
	 * @param indexno
	 *            新位置的顺序号
	 */
	public void move(CourseSection menu, CourseSection parent, int indexno);
}
