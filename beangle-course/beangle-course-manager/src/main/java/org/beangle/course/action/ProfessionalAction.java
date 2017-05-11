package org.beangle.course.action;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.course.service.ProfessionalService;
import org.beangle.ems.security.Group;
import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.common.action.BaseCommonAction;
import org.beangle.zyk.core.model.Department;
import org.beangle.zyk.core.model.Faculty;
import org.beangle.zyk.course.model.Professional;

/**
 * 专业管理
 * @author dell
 *
 */
public class ProfessionalAction extends BaseCommonAction{

	private ProfessionalService professionalService;
	protected void indexSetting() {
		put("bmlxs",getDictDataByDictTypeCode("DEPARTMENT_TYPE"));
	}
	
	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		OqlBuilder<?> oql =  (OqlBuilder<?>) super.getQueryBuilder();
		return oql;
	}
	
	protected void editSetting(Entity<?> entity) {
		//部门
		put("departments",entityDao.getAll(Department.class));
		//教职工
		put("faculties",entityDao.getAll(Faculty.class));
		//传递角色
		put("groups",entityDao.getAll(Group.class));
	}
	
	@Override
	protected String removeAndForward(Collection<?> entities) {
		return super.removeAndForward(entities);
	}

	protected String saveAndForward(Entity<?> entity) {
		Professional professional = (Professional) entity;
		try {
			//获取选中角色ID，并组建Long类型数组
			Long[] groupIds = StrUtils.splitToLong(get("groupIds"));
			//查询角色
			List<Group> groups = entityDao.get(Group.class, groupIds);
			//清理原有角色
			professional.getRoles().clear();
			//设置角色
			professional.getRoles().addAll(groups);
			
			
			if(null==professional.getId()){
				professional.setCreateAt(new Date());
			}
			professional.setUpdateAt(new Date());
			entityDao.saveOrUpdate(professional);
		} catch (Exception e) {
			e.printStackTrace();
			return forward(ERROR);
		}
		
		return redirect("search", "info.save.success");
	}
	
  /**
    * 专业教师设置
    * 
    * @return
    */
   public String addPTeacherList() {
	   Long professionalId = getLong("professional.id");
	   Professional professional = entityDao.get(Professional.class, professionalId);
	   put("faculties", professional.getZyjsSet());
       return forward();
   }
	
   /**
    * 专业教师添加
    * 
    * @return
    */
   public String addPTeacher() {
	   Long professionalId = getLong("professional.id");
	   Professional professional = entityDao.get(Professional.class, professionalId);
	   put("professional", professional);
	   
	   put("faculties",professionalService.getFacultyNotInProfessionalList(professional));
       return forward();
   }
   
   /**
    * 专业教师保存
    * 
    * @return
    */
   public String pTeacherSave() {
	   Long professionalId = getLong("professional.id");
	   Professional professional = entityDao.get(Professional.class, professionalId);
	   Long[] zyjsIds = StrUtils.splitToLong(get("zyjsId"));
	   List facultyList = entityDao.get(Faculty.class, "id",zyjsIds);
	   
	   professional.getZyjsSet().addAll(facultyList);
	   entityDao.save(professional);
		
		return redirect("addPTeacherList", "info.save.success","&professional.id="+professionalId);
	}
   
   /**
    * 删除
    * 
    * @return
    */
   public String removePTeacher() {
	   Long professionalId = getLong("professional.id");
	   Professional professional = entityDao.get(Professional.class, professionalId);
	   
	   Long[] facultyIds = getEntityIds("faculty");
	   try {
		   List facultyList = entityDao.get(Faculty.class, "id",facultyIds);
		   professional.getZyjsSet().removeAll(facultyList);
		   entityDao.save(professional);
		} catch (Exception e) {
			e.printStackTrace();
			return forward(ERROR);
		}
	   return redirect("addPTeacherList", "info.save.success","&professional.id="+professionalId);
   }
   
   public String searchFaculty() {
 		String name = get("term");
 		OqlBuilder<Faculty> query = OqlBuilder.from(Faculty.class, "faculty");
 		query.where("faculty.enabled is true");
 		if(StringUtils.isNotEmpty(name)){
 	    	  query.where("faculty.fullname like(:name)", "%" + name + "%");
	    }
 		query.limit(getPageLimit());
 		List ls =  entityDao.search(query);
 		put("faculties", entityDao.search(query));
 		return forward("facultyJSON");
 	}
   
	@Override
	protected String getEntityName() {
		return Professional.class.getName();
	}

	public ProfessionalService getProfessionalService() {
		return professionalService;
	}

	public void setProfessionalService(ProfessionalService professionalService) {
		this.professionalService = professionalService;
	}
}
