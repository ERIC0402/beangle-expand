package org.beangle.wenjuan.action;

import java.util.List;

import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.system.action.DictTreeAction;
import org.beangle.website.system.model.DictTree;

public class TmflAction extends DictTreeAction {

	
	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		OqlBuilder<?> oql =  (OqlBuilder<?>) super.getQueryBuilder();
		List<DictTree> top = entityDao.get(DictTree.class, "dm", "TMFL");
		if(!top.isEmpty()){
			oql.where("code like :pcode", top.get(0).getCode() + ".%");
		}
		return oql;
	}
}
