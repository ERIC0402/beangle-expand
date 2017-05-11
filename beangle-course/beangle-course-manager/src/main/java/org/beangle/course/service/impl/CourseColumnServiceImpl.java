package org.beangle.course.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.course.service.CourseColumnService;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.zyk.course.model.CourseColumn;

public class CourseColumnServiceImpl extends BaseServiceImpl implements CourseColumnService{

	public void move(CourseColumn tree, CourseColumn parent, int indexno) {
		if (ObjectUtils.equals(tree.getParent(), parent)) {
			if (NumberUtils.toInt(tree.getIndexno()) != indexno) {
				shiftCode(tree, parent, indexno);
			}
		} else {
			if (null != tree.getParent()) {
				tree.getParent().getChildren().remove(tree);
			}
			tree.setParent(parent);
			shiftCode(tree, parent, indexno);
		}
	}

	private void shiftCode(CourseColumn menu, CourseColumn newParent, int indexno) {
		List<CourseColumn> sibling = null;
		if (null != newParent) {
			sibling = newParent.getChildren();
		}else {
			sibling = CollectUtils.newArrayList();
			OqlBuilder<CourseColumn> query = OqlBuilder.from(CourseColumn.class);
			query.where("parent is null");
			sibling = entityDao.search(query);
		}
		Collections.sort(sibling);
		sibling.remove(menu);
		indexno--;
		if (indexno > sibling.size()) {
			indexno = sibling.size();
		}
		sibling.add(indexno, menu);
		int nolength = String.valueOf(sibling.size()).length();
		Set<CourseColumn> menus = CollectUtils.newHashSet();
		for (int seqno = 1; seqno <= sibling.size(); seqno++) {
			CourseColumn one = sibling.get(seqno - 1);
			generateCode(one, StringUtils.leftPad(String.valueOf(seqno), nolength, '0'), menus);
		}
		entityDao.saveOrUpdate(menus);
	}

	private void generateCode(CourseColumn menu, String indexno, Set<CourseColumn> menus) {
		menus.add(menu);
		if (null != indexno) {
			((CourseColumn) menu).generateCode(indexno);
		} else {
			((CourseColumn) menu).generateCode();
		}
		if (null != menu.getChildren()) {
			for (CourseColumn m : menu.getChildren()) {
				generateCode(m, null, menus);
			}
		}
	}
	
	/**
	 * 获取有效的课程栏目
	 * @return
	 */
	public List getCourseColumnList(){
		OqlBuilder<CourseColumn> query = OqlBuilder.from(CourseColumn.class, "courseColumn");
		query.where("courseColumn.courseField is not null");
		query.where("courseColumn.enabled is true");
		query.orderBy("courseColumn.code");
 		List ls =  entityDao.search(query);
		return ls;
	}
}
