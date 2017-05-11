package org.beangle.course.action;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.course.service.CourseColumnService;
import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.util.HierarchyEntityUtil;
import org.beangle.website.common.action.BaseCommonAction;
import org.beangle.zyk.course.model.CourseColumn;

/**
 * 课程栏目
 * @author dell
 *
 */
public class CourseColumnAction extends BaseCommonAction{

	private CourseColumnService courseColumnService;

	protected void indexSetting() {
		putParameters();
	}
	
	protected void putParameters(){
		put("columnTypes",getDictDataByDictTypeCode("COURSE_COLUMN_TYPE"));
	}
	
	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		OqlBuilder<?> oql =  (OqlBuilder<?>) super.getQueryBuilder();
		oql.orderBy(getShortName()+".code");
		return oql;
	}
	
	protected void editSetting(Entity<?> entity) {
	   CourseColumn courseColumn = getEntity(CourseColumn.class, "courseColumn");
       put("courseColumn", courseColumn);
       
       CourseColumn tree = (CourseColumn) entity;
	   List<CourseColumn> folders = CollectUtils.newArrayList();
	   OqlBuilder<CourseColumn> query = OqlBuilder.from(CourseColumn.class, "t");
	   query.orderBy("code");
	   folders = entityDao.search(query);
	   folders.removeAll(HierarchyEntityUtil.getFamily(tree));
	   put("parents", folders);
     
	   putParameters();
	}
	
	protected String saveAndForward(Entity<?> entity) {
	   CourseColumn courseColumn = (CourseColumn) entity;
		try {
			Long newParentId = getLong("parent.id");
			int indexno = getInteger("indexno");
			CourseColumn parent = null;
			if (null != newParentId) {
				parent = entityDao.get(CourseColumn.class, newParentId);
			}
			courseColumnService.move(courseColumn, parent, indexno);
			entityDao.saveOrUpdate(courseColumn);
			
		} catch (Exception e){
			e.printStackTrace();
			return forward(ERROR);
		}
	   return redirect("search", "info.save.success");
	}
	
	@Override
	protected String getEntityName() {
		return CourseColumn.class.getName();
	}

	public CourseColumnService getCourseColumnService() {
		return courseColumnService;
	}

	public void setCourseColumnService(CourseColumnService courseColumnService) {
		this.courseColumnService = courseColumnService;
	}
}
