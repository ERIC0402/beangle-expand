package org.beangle.course.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.course.service.CourseSectionService;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.zyk.core.model.Department;
import org.beangle.zyk.course.model.CourseSection;

public class CourseSectionServiceImpl extends BaseServiceImpl implements CourseSectionService {

	public void move(CourseSection tree, CourseSection parent, int indexno) {
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

	private void shiftCode(CourseSection menu, CourseSection newParent, int indexno) {
		List<CourseSection> sibling = null;
		if (null != newParent) {
			sibling = newParent.getChildren();
		}else {
			sibling = CollectUtils.newArrayList();
			OqlBuilder<CourseSection> query = OqlBuilder.from(CourseSection.class);
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
		Set<CourseSection> menus = CollectUtils.newHashSet();
		for (int seqno = 1; seqno <= sibling.size(); seqno++) {
			CourseSection one = sibling.get(seqno - 1);
			generateCode(one, StringUtils.leftPad(String.valueOf(seqno), nolength, '0'), menus);
		}
		entityDao.saveOrUpdate(menus);
	}

	private void generateCode(CourseSection menu, String indexno, Set<CourseSection> menus) {
		menus.add(menu);
		if (null != indexno) {
			((CourseSection) menu).generateCode(indexno);
		} else {
			((CourseSection) menu).generateCode();
		}
		if (null != menu.getChildren()) {
			for (CourseSection m : menu.getChildren()) {
				generateCode(m, null, menus);
			}
		}
	}
	
	/**
	 * 根据代码获取字典树节点
	 * @param dm 代码
	 * @return
	 */
	protected Department getDepartmentByDM(String dm){
		OqlBuilder<Department> query = OqlBuilder.from(Department.class,"d");
		query.where("d.dm=:dm",dm);
		List<Department> dictTrees = entityDao.search(query);
		Department dictTree = null;
		if(dictTrees != null && dictTrees.size() > 0){
			dictTree = dictTrees.get(0);
		}
		return dictTree;
	}

	public List<Department> findDepartmentByCode(String code) {
		Department dictTree = getDepartmentByDM(code);
		if(dictTree != null){
			OqlBuilder<Department> query = OqlBuilder.from(Department.class,"d");
			query.where("d.code like '" + dictTree.getCode() + "%'");
			query.where("d.enable=1");
			query.orderBy("d.code");
			return entityDao.search(query);
		}else{
			return null;
		}
	}

	public Map<String, Department> getDepartmentMap(String code) {
		List<Department> list = findDepartmentByCode(code);
		Map<String, Department> map = new HashMap<String, Department>(list==null?0:list.size());
		for(Department dictTree : list){
			map.put(dictTree.getName(), dictTree);
		}
		return map;
	}
}
