package org.beangle.xuesheng.action;

import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.common.action.BaseCommonAction;
import org.beangle.wenjuan.model.Zxks;

/**
 * 考试成绩
 * 
 * @author CHII
 * 
 */
public class ZxkscjAction extends BaseCommonAction {

	@Override
	protected String getEntityName() {
		return Zxks.class.getName();
	}

	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		@SuppressWarnings("unchecked")
		OqlBuilder<Zxks> query = (OqlBuilder<Zxks>) super.getQueryBuilder();
		query.where("user.id = :userId", getUserId());
		return query;
	}
}
