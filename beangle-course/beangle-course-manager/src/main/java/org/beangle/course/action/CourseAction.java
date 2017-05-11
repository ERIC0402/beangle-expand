package org.beangle.course.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.course.service.CourseColumnService;
import org.beangle.ems.security.Group;
import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.common.action.FileAction;
import org.beangle.website.common.util.CommonUtils;
import org.beangle.zyk.core.model.Department;
import org.beangle.zyk.core.model.Faculty;
import org.beangle.zyk.course.model.Course;
import org.beangle.zyk.course.model.CourseColumn;
import org.beangle.zyk.course.model.CourseInfo;
import org.beangle.zyk.course.model.Professional;

/**
 * 课程管理
 * @author dell
 *
 */
public class CourseAction extends FileAction{
	private CourseColumnService courseColumnService;
	
	protected void indexSetting() {
		put("professionals",entityDao.getAll(Professional.class));
		put("courseTypes",getDictDataByDictTypeCode("KECHENG_LX"));
	}
	
	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		Long professionalId = getLong("professionalId");
		OqlBuilder<?> oql =  (OqlBuilder<?>) super.getQueryBuilder();
		Date start = getDate("start");
		Date end = getDate("end");
		if(start != null){
			oql.where(getShortName() + ".publishAt >=:start",CommonUtils.getStartTime(start));
		}
		if(end != null){
			oql.where(getShortName() + ".publishAt <=:end",CommonUtils.getEndTime(end));
		}
		if(null!=professionalId){
			oql.join(getShortName()+".professionals", "professional");
			oql.where("professional.id =:professionalId",professionalId);
		}
		return oql;
	}
	
	protected void putEdit(){
		
		put("departments",entityDao.getAll(Department.class));//部门
		put("professionals",entityDao.getAll(Professional.class));
		put("faculties",entityDao.getAll(Faculty.class));//教职工
		put("groups",entityDao.getAll(Group.class));//传递角色
		put("courseTypes",getDictDataByDictTypeCode("KECHENG_LX"));
	}
	
	protected void editSetting(Entity<?> entity) {
		putEdit();
	}
	
	public String courseStandard(){
		Course course = getEntity(Course.class, "course");
	    put("course", course);
		return forward();
	}
	
	@Override
	protected String removeAndForward(Collection<?> entities) {
		return super.removeAndForward(entities);
	}

	protected String saveAndForward(Entity<?> entity) {
		Course course = (Course) entity;
		String picAdress = get("iconPath");
		List courseList = new ArrayList();
		try {
			//课程代码唯一
			if (entityDao.duplicate(Course.class, course.getId(), "code", course.getCode())) {
				put("course",course);
				putEdit();
				return forward("edit","该课程代码"+course.getCode()+"已存在！");
			}
			
			if(null==course.getId()){
				course.setCreateAt(new Date());
			}
			
			//获取专业ID，并组建Long类型数组
			Long[] professionalIds = StrUtils.splitToLong(get("professionalIds"));
			List<Professional> professionals = entityDao.get(Professional.class, professionalIds);
			course.getProfessionals().clear();
			course.getProfessionals().addAll(professionals);
			course.setUpdateAt(new Date());
			courseList.add(course);
			
			//截取图片文件名称（图片处理）
			String path = this.getCourseAbsolutePath(course)+"/";
			String tmpPicName =  picAdress;
			String picName = tmpPicName.substring(tmpPicName.lastIndexOf("/")+1, tmpPicName.length());
			String previewImage =moveAndRemoveAnnex(picAdress,path+picName);//移动图片
			if(StringUtils.isNotEmpty(previewImage)){
				course.setPreviewImage(previewImage);
			}
			
			CourseInfo courseInfo = null;
			if(null==course.getCourseInfo()){
				courseInfo= new CourseInfo();
				courseInfo.setCourse(course);
				courseList.add(courseInfo);
				course.setCourseInfo(courseInfo);
			}
			entityDao.saveOrUpdate(courseList);
			
		} catch (Exception e) {
			e.printStackTrace();
			return forward(ERROR);
		}
		return redirect("edit", "info.save.success","courseId="+course.getId());
	}
	
	/**
	 * 显示栏目列表（课程信息）
	 * @return
	 */
	public String courseColumnList(){
		Course course = getEntity(Course.class, "course");
	    put("course", course);
	    put("courseColumns", courseColumnService.getCourseColumnList());
		return forward();
	}
	
	public String courseColumnEdit(){
		Course course = getEntity(Course.class, "course");
	    put("course", course);
	    
	    Long courseColumnId = getLong("courseColumnId");
	    CourseColumn courseColumn = entityDao.get(CourseColumn.class, courseColumnId);
	    put("courseColumn",courseColumn);
	    put("courseContent",course.getCourseInfo().getCourseColumnInfo(courseColumn.getCourseField()));
		return forward();
	}
	
	/**
    * 课程信息保存
    * 
    * @return
    */
   public String saveCourseField() {
	   Course course  = populateEntity(Course.class, "course");
		try {
			course.setUpdateAt(new Date());
			entityDao.saveOrUpdate(course);
		} catch (Exception e) {
			e.printStackTrace();
			return forward(ERROR);
		}
		return redirect("courseColumnList", "info.save.success","courseId="+course.getId());
	}
	
	/**
	 * 获取课程预览图片的绝对路径
	 * 文件路径（年/月/资源的ID序号 ）
	 * @return
	 */
	public String getCourseAbsolutePath(Course course){
		Calendar ca = Calendar.getInstance();
	    int year = ca.get(Calendar.YEAR);//获取年份
	    int month=ca.get(Calendar.MONTH)+1;//获取月份
		String path = year+"/"+month+"/"+course.getId();
		return path;
	}
	
	@Override
	protected String getEntityName() {
		return Course.class.getName();
	}

	public CourseColumnService getCourseColumnService() {
		return courseColumnService;
	}

	public void setCourseColumnService(CourseColumnService courseColumnService) {
		this.courseColumnService = courseColumnService;
	}
}
