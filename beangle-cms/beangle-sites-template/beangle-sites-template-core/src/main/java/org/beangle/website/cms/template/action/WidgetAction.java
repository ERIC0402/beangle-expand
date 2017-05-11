/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.action;

import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.cms.template.widgets.WidgetUtils;

import org.beangle.website.cms.action.CommonAction;
import org.beangle.website.cms.model.TemplateGroup;
import org.beangle.website.cms.model.Widget;
import org.beangle.website.system.model.DictTypeUtils;

/**
 * 组件管理
 * 
 * @author CHII
 */
public class WidgetAction extends CommonAction {

	private WidgetUtils widgetUtils;

	public void setWidgetUtils(WidgetUtils widgetUtils) {
		this.widgetUtils = widgetUtils;
	}

	@Override
	protected String getEntityName() {
		return Widget.class.getName();
	}

	@Override
	protected void indexSetting() {
		super.indexSetting();
		put("types", getDictDataByDictType(DictTypeUtils.WIDGET_TYPE));
		put("groups", getGroups());
	}

	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		OqlBuilder<Widget> query = (OqlBuilder<Widget>) super.getQueryBuilder();
		Long groupId = getLong("widget.groups.id");
		if (groupId != null) {
			query.join("widget.groups", "g");
			query.where(new Condition("g.id = :groupId", groupId));
		}
		query.getLimit().setPageSize(8);
		return query;
	}

	@Override
	protected void editSetting(Entity<?> entity) {
		super.editSetting(entity);
		put("types", getDictDataByDictType(DictTypeUtils.WIDGET_TYPE));
		put("groups", getGroups());
	}

	@Override
	protected String saveAndForward(Entity<?> entity) {
		Widget widget = (Widget) entity;
		if (widget.getId() == null) {
			try {
				Class.forName(widget.getClassName());
			} catch (ClassNotFoundException ex) {
			}
		}

		if (widget.getGroups() == null) {
			widget.setGroups(new HashSet<TemplateGroup>());
		}

		List<TemplateGroup> groups = entityDao.get(TemplateGroup.class, getAll("groupIds", Long.class));
		widget.getGroups().clear();
		widget.getGroups().addAll(groups);

		try {
			widget.setImg(widgetUtils.getDefaultImg(widget.getClassName()));
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(WidgetAction.class.getName()).log(Level.SEVERE, null, ex);
		}
		return super.saveAndForward(entity);
	}

	private List<TemplateGroup> getGroups() {
		OqlBuilder<TemplateGroup> query = OqlBuilder.from(TemplateGroup.class, "o");
		query.where(new Condition("enabled = true"));
		return entityDao.search(query);
	}
}
