package org.beangle.course.view;

import org.beangle.course.action.CourseHomeAction;
import org.beangle.course.service.impl.CourseHomeServiceImpl;
import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.spring.bind.Scope;

/**
 * 课程展示
 * @author dell
 *
 */
public class DefaultModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		bind(CourseHomeAction.class).in(Scope.PROTOTYPE);
		bind("courseHomeService",CourseHomeServiceImpl.class);
	}
}
