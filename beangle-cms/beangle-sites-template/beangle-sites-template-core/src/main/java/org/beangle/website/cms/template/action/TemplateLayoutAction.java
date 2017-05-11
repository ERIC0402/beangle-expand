/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.action;

import org.beangle.model.Entity;

import org.beangle.website.cms.action.CommonAction;
import org.beangle.website.cms.model.TemplateGroup;
import org.beangle.website.cms.model.TemplateLayout;

/**
 * 模板布局管理
 * 
 * @author CHII
 */
public class TemplateLayoutAction extends CommonAction {

	@Override
	protected String getEntityName() {
		return TemplateLayout.class.getName();
	}

	@Override
	protected String saveAndForward(Entity<?> entity) {
		TemplateLayout tg = (TemplateLayout) entity;
		tg.setImg(moveAndRemoveAnnex(tg.getImg(), get("oimg")));
		return super.saveAndForward(entity);
	}

}
