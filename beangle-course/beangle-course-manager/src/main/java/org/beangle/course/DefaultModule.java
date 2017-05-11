package org.beangle.course;

import org.beangle.course.action.CourseAction;
import org.beangle.course.action.CourseBookAction;
import org.beangle.course.action.CourseColumnAction;
import org.beangle.course.action.CourseRelatedAction;
import org.beangle.course.action.CourseResponsibleAction;
import org.beangle.course.action.CourseSectionAction;
import org.beangle.course.action.ProfessionalAction;
import org.beangle.course.service.impl.CourseColumnServiceImpl;
import org.beangle.course.service.impl.CourseSectionServiceImpl;
import org.beangle.course.service.impl.ProfessionalServiceImpl;
import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.spring.bind.Scope;

/**
 * 课程部分默认
 * @author flysky
 *
 */
public class DefaultModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		bind(ProfessionalAction.class,CourseAction.class,
				CourseBookAction.class,CourseSectionAction.class,
				CourseColumnAction.class,CourseResponsibleAction.class,
				CourseRelatedAction.class).in(Scope.PROTOTYPE);
		bind("courseSectionService",CourseSectionServiceImpl.class);
		bind("professionalService",ProfessionalServiceImpl.class);
		bind("courseColumnService",CourseColumnServiceImpl.class);
	}
}
