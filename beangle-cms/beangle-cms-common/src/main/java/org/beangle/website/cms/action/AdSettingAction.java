package org.beangle.website.cms.action;

import org.beangle.model.Entity;
import org.beangle.website.cms.model.AdSetting;
import org.beangle.website.cms.utils.CmsDictTypeUtils;

public class AdSettingAction extends CommonAction {

	@Override
	protected String getEntityName() {
		return AdSetting.class.getName();
	}

	@Override
	protected void editSetting(Entity<?> entity) {
		put("positions", dictDataService.findDictData(CmsDictTypeUtils.ADWZ));
		super.editSetting(entity);
	}

}
