package org.beangle.wenjuan.action;

import java.util.List;

import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.common.action.BaseCommonAction;
import org.beangle.wenjuan.model.Ksap;
import org.beangle.wenjuan.model.KsapParent;
import org.beangle.wenjuan.model.Zxks;
import org.beangle.wenjuan.model.ZxksParent;

/**
 * 在线考试分析
 * @author CHII
 *
 */
public class ZxksfxAction extends BaseCommonAction {
	
	@Override
	protected String getEntityName() {
		return Ksap.class.getName();
	}
	
	protected Class<? extends ZxksParent> getZxksClass(){
		return Zxks.class;
	}

	/**
	 * 查看结果
	 * 
	 * @return
	 */
	public String info() {
		KsapParent ksap = (KsapParent) getEntity();
		@SuppressWarnings("unchecked")
		OqlBuilder<ZxksParent> query = (OqlBuilder<ZxksParent>) OqlBuilder.from(getZxksClass(), "zxks");
		populateConditions(query);
		query.join("zxks.wjjg.wjjgtms", "tm");
		query.join("tm.tmxxs", "tmxx");
		query.groupBy("tmxx.id");
		query.select("tmxx.id, count(*)");
		@SuppressWarnings("unchecked")
		List<ZxksParent> tjlist = search(query);
		put("tjlist", tjlist);
		put("wenjuan", ksap.getWenJuan());
		return forward();
	}
}
