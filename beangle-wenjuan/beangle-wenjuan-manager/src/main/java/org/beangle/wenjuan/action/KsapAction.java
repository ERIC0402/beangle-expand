package org.beangle.wenjuan.action;

import java.util.List;

import org.beangle.ems.security.User;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.common.action.BaseCommonAction;
import org.beangle.website.common.util.DateUtil;
import org.beangle.wenjuan.model.Ksap;
import org.beangle.wenjuan.model.KsapParent;
import org.beangle.wenjuan.model.WenJuan;
import org.beangle.wenjuan.utils.DictTypeWJUtil;

public class KsapAction extends BaseCommonAction {

	@Override
	protected String getEntityName() {
		return Ksap.class.getName();
	}

	public String index() {
		putDatas();
		return forward();
	}

	@Override
	protected String getDefaultOrderString() {
		return "id";
	}

	/**
	 * 编辑
	 */
	protected void editSetting(Entity<?> entity) {
		putDatas();
	}

	/**
	 * 保存
	 */
	protected String saveAndForward(Entity<?> entity) {
		KsapParent ksap = (KsapParent) entity;
		ksap.setStartTime(DateUtil.clearSecond(ksap.getStartTime()));
		ksap.setEndTime(DateUtil.clearSecond(ksap.getEndTime()));
		ksap.getUsers().clear();
		ksap.getUsers().addAll(findEntityByIds(User.class, "userIds"));
		ksap.getRgyjrys().clear();
		ksap.getRgyjrys().addAll(findEntityByIds(User.class, "rgyjIds"));
		return super.saveAndForward(entity);
	}

	/**
	 * 传递数据
	 */
	protected void putDatas() {
		OqlBuilder<WenJuan> query = OqlBuilder.from(WenJuan.class, "wenJuan");
		query.where("wenJuan.enabled = true");
		List<WenJuan> wenJuans = entityDao.search(query);
		put("wenJuans", wenJuans);
		put("categorys", findDictData(DictTypeWJUtil.ZXKS_CATEGORY)); // 学习过程类别
		put("types", findDictData(DictTypeWJUtil.ZXKS_TYPE)); // 学习过程类型
	}
	
}
