package org.beangle.course.action;

import java.util.List;

import org.beangle.course.service.CourseHomeService;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.common.action.BaseCommonAction;
import org.beangle.zyk.core.utils.DictTypeUtils;
import org.beangle.zyk.course.model.Course;
import org.beangle.zyk.course.model.CourseBook;
import org.beangle.zyk.course.model.CourseColumn;
import org.beangle.zyk.course.model.CourseContent;
import org.beangle.zyk.course.model.CourseSection;

public class CourseHomeAction extends BaseCommonAction{
	private CourseHomeService courseHomeService;
	
	protected void indexSetting() {
		Long courseId = getLong("courseId");
		Course course = entityDao.get(Course.class, courseId);
		put("course",course);
		//首页栏目
		CourseColumn homeColumn=courseHomeService.getCourseColumnIntroduction("课程首页");
		put("homeColumn",homeColumn);
		putDatas(course);
	}
	
	/**
	 * 显示栏目信息
	 * @return
	 */
	public String search(){
	    Course course = getEntity(Course.class, "course");
		put("course",course);
//		put("courseColumns",courseHomeService.getFirstCourseColumnList());
	
		putDatas(course);
		return forward();
	}
	
	protected void putDatas(Course course){
		if(null!=course.getCourseLmzh()){
			put("courseColumns",course.getCourseLmzh().getFirstCourseColumnList());
		}else{
			put("courseColumns",courseHomeService.getFirstCourseColumnList());
		}
		
		//课程简介栏目
		CourseColumn introductionColumn=courseHomeService.getCourseColumnIntroduction("课程简介");
		put("introductionColumn",introductionColumn);
		String introductionContent = course.getCourseInfo().getCourseColumnInfo(introductionColumn.getCourseField());
		introductionContent = splitAndFilterString(introductionContent,280);
	    put("introductionContent",introductionContent);
	    
		//推荐资源
		List courseResourceList = courseHomeService.getCourseResourceList(course);
		put("courseResourceList",courseResourceList);
		List hotResourceList = courseHomeService.getHotResourceListByCourse(course);
		put("kczylxs",getDictDataByDictTypeCode(DictTypeUtils.KECHENG_ZYLX));
		put("kczyCountMap",courseHomeService.getKczyCountMap(course));
		
	}
	

	/**
	 * 课程信息内容
	 * @return
	 */
	public String courseInfoContent(){
	    Course course = getEntity(Course.class, "course");
	    put("course", course);
	    
	    Long courseColumnId = getLong("courseColumnId");
	    CourseColumn courseColumn = entityDao.get(CourseColumn.class, courseColumnId);
	    put("courseColumn",courseColumn);
	    String courseContent = course.getCourseInfo().getCourseColumnInfo(courseColumn.getCourseField());
	    put("courseContent",courseContent);
		return forward();
	}
	
	/**
	 * 课程章节
	 * @return
	 */
	public String courseSection(){
		Long courseId = getLong("courseId");
	    Course course = getEntity(Course.class, "course");
	    put("course", course);
	    
	    Long courseColumnId = getLong("courseColumnId");
	    CourseColumn courseColumn = entityDao.get(CourseColumn.class, courseColumnId);
	    put("courseColumn",courseColumn);
	    
	    OqlBuilder<CourseSection> query = OqlBuilder.from(CourseSection.class, "courseSection");
		query.where("courseSection.course.id =:courseId",courseId);
		query.orderBy("courseSection.code");
 		List courseSections =  entityDao.search(query);
 		put("courseSections",courseSections);
	    
		return forward();
	}
	
	/**
	 * 显示课程内容
	 * @return
	 */
	public String courseContent(){
		Long courseId = getLong("courseId");
		Course course = getEntity(Course.class, "course");
	    put("course", course);
	    
	    Long courseColumnId = getLong("courseColumnId");
	    CourseColumn courseColumn = entityDao.get(CourseColumn.class, courseColumnId);
	    put("courseColumn",courseColumn);
	    
	    Long kczylxId = getLong("kczylx.id");
	    
	    OqlBuilder<CourseContent> query = OqlBuilder.from(CourseContent.class, "courseContent");
		query.where("courseContent.courseSection.course.id =:courseId",courseId);
		if(null!=kczylxId){
			query.where("courseContent.kczylx.id =:kczylxId",kczylxId);
		}
 		List courseContents =  entityDao.search(query);
 		put("courseContents",courseContents);
		return forward();
	}
	
	/**
	 * 参考教材
	 * @return
	 */
	public String courseBook(){
		Long courseId = getLong("courseId");
	    Course course = getEntity(Course.class, "course");
	    put("course", course);
	    
	    Long courseColumnId = getLong("courseColumnId");
	    CourseColumn courseColumn = entityDao.get(CourseColumn.class, courseColumnId);
	    put("courseColumn",courseColumn);
	    
	    OqlBuilder<CourseBook> query = OqlBuilder.from(CourseBook.class, "courseBook");
		query.where("courseBook.course.id =:courseId",courseId);
 		List courseBooks =  entityDao.search(query);
 		put("courseBooks",courseBooks);
		return forward();
	}
	
	
	/** 
     * 删除input字符串中的html格式 
     *  
     * @param input 
     * @param length 
     * @return 
     */  
    public static String splitAndFilterString(String input, int length) {  
        if (input == null || input.trim().equals("")) {  
            return "";  
        }  
        // 去掉所有html元素,  
        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(  
                "<[^>]*>", "");  
        str = str.replaceAll("[(/>)<]", "");  
        int len = str.length();  
        if (len <= length) {  
            return str;  
        } else {  
            str = str.substring(0, length);  
            str += "......";  
        }  
        return str;  
    }  
	public CourseHomeService getCourseHomeService() {
		return courseHomeService;
	}
	
	public void setCourseHomeService(CourseHomeService courseHomeService) {
		this.courseHomeService = courseHomeService;
	}
}
