package org.beangle.course.service.impl;

import java.util.List;

import org.beangle.commons.collection.page.PageLimit;
import org.beangle.course.service.ProfessionalService;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.zyk.core.model.Faculty;
import org.beangle.zyk.course.model.Professional;

/**
 * 专业处理接口
 * @author dell
 *
 */
public class ProfessionalServiceImpl extends BaseServiceImpl implements ProfessionalService{

	/**
	 * 过滤已选的专业教师
	 * @param professional
	 * @return
	 */
	public List getFacultyNotInProfessionalList(Professional professional){
		OqlBuilder<Faculty> query = OqlBuilder.from(Faculty.class, "faculty");
		query.where("faculty.enabled is true");
		if(professional.getZyjsSet().size()>0){
			query.where("faculty not in(:zyjss)",professional.getZyjsSet());
		}
		PageLimit limit = new PageLimit(10,1);
		query.limit(limit);
 		List ls =  entityDao.search(query);
		return ls;
	}
}
