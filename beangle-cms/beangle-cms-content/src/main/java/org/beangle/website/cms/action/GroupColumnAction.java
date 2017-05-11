package org.beangle.website.cms.action;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.ems.security.Authority;
import org.beangle.ems.security.Group;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.convention.route.Action;
import org.beangle.website.cms.action.CommonAction;
import org.beangle.website.cms.model.Column;
import org.beangle.website.cms.model.GroupColumn;
import org.beangle.website.cms.model.Site;


public class GroupColumnAction extends CommonAction {

	/**
	 * 根据站点角色权限
	 */
	public String edit() {
		Set<Site> sites = getSitesByCurrentUser();
		Site site = null;
		Long siteId = getLong("site.id");
		if (null == siteId) {
			if(!sites.isEmpty()){
				site = sites.iterator().next();
			}
		}else{
			site = entityDao.get(Site.class, siteId);
		}
		put("sites",sites);
		put("site",site);
		Long groupId = getLong("group.id");
		Group group = null;
		if(null == groupId){
			group = (!site.getGroups().isEmpty())?site.getGroups().iterator().next():null;
		}else{
			group = entityDao.get(Group.class, groupId);
		}
		
		OqlBuilder<Column> columnQuery = OqlBuilder.from(Column.class,"c");
		columnQuery.where("c.site.id=:siteId",site.getId());
		put("columns",entityDao.search(columnQuery));
		
		Set<Object> selectedColumnIds = CollectUtils.newHashSet();
		if(group != null){
			selectedColumnIds.addAll(findColumnIdsByGroup(group,site));
		}
		put("selectedColumnIds",selectedColumnIds);
		put("group",group);
		return forward();
	}

	/**
	 * 显示权限操作提示界面
	 */
	public String prompt() {
		return forward();
	}
	
	protected List<?> findColumnIdsByGroup(Group group,Site site){
		OqlBuilder<GroupColumn> gcQuery = OqlBuilder.from(GroupColumn.class,"g");
		gcQuery.where("g.group.id=:groupId",group.getId());
		gcQuery.where("g.column.site.id=:siteId",site.getId());
		gcQuery.select("g.column.id");
		return entityDao.search(gcQuery);
	}
	
	protected List<GroupColumn> findGcByGroup(Group group,Site site){
		OqlBuilder<GroupColumn> gcQuery = OqlBuilder.from(GroupColumn.class,"g");
		gcQuery.where("g.group.id=:groupId",group.getId());
		gcQuery.where("g.column.site.id=:siteId",site.getId());
		return entityDao.search(gcQuery);
	}

	/**
	 * 保存模块级权限
	 */
	public String save() {
		Site site = entityDao.get(Site.class, getLong("site.id"));
		Group group = entityDao.get(Group.class, getLong("group.id"));
		Set<Column> columns = CollectUtils.newHashSet(entityDao.get(Column.class, StrUtils.splitToLong(get("columnId"))));
		List<GroupColumn> selectedColumns = findGcByGroup(group, site);
		Set<GroupColumn> newGcs = CollectUtils.newHashSet();
		Set<GroupColumn> removed = CollectUtils.newHashSet();
		for (GroupColumn gc : selectedColumns) {
			if (!columns.contains(gc.getColumn())) {
				removed.add(gc);
			} else {
				columns.remove(gc.getColumn());
			}
		}
		entityDao.remove(removed);
		for(Iterator<Column> it = columns.iterator();it.hasNext();){
			Column column = it.next();
			GroupColumn gc = new GroupColumn();
			gc.setColumn(column);
			gc.setGroup(group);
			newGcs.add(gc);
		}
		entityDao.saveOrUpdate(newGcs);

		Action redirect = Action.to(this).method("edit");
		redirect.param("site.id", site.getId()).param("group.id", group.getId());
		return redirect(redirect, "info.save.success");
	}
}
