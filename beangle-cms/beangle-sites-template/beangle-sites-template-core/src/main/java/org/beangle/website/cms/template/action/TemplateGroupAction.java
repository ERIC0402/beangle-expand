/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.action;

import java.util.Collection;

import org.beangle.model.Entity;

import org.beangle.website.cms.action.CommonAction;
import org.beangle.website.cms.model.TemplateGroup;

/**
 * 组件管理
 * 
 * @author CHII
 */
public class TemplateGroupAction extends CommonAction {
	
	@Override
	protected String getEntityName() {
		return TemplateGroup.class.getName();
	}

	@Override
	protected void indexSetting() {
		super.indexSetting();
	}

	@Override
	protected String saveAndForward(Entity<?> entity) {
		TemplateGroup tg = (TemplateGroup) entity;
		tg.setImg(moveAndRemoveAnnex(tg.getImg(), get("oimg")));
		return super.saveAndForward(entity);
	}
	
	@Override
	protected String removeAndForward(Collection<?> entities) {
		try {
			remove(entities);
			for(TemplateGroup tg : (Collection<TemplateGroup>)entities){
				delete(tg.getImg());
			}
		} catch (Exception e) {
			logger.info("removeAndForwad failure", e);
			return redirect("search", "info.delete.failure");
		}
		return redirect("search", "info.remove.success");
	}
}
