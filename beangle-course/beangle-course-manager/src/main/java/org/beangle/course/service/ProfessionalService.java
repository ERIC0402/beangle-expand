package org.beangle.course.service;

import java.util.List;

import org.beangle.zyk.course.model.Professional;

public interface ProfessionalService {

	/**
	 * 过滤已选的专业教师
	 * @param professional
	 * @return
	 */
	public List getFacultyNotInProfessionalList(Professional professional);
}
