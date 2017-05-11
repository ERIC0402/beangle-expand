package org.beangle.website.cms.action;

import java.util.Set;

import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.cms.action.ContentCommonAction;
import org.beangle.website.cms.model.OnlineMessType;
import org.beangle.website.cms.model.Site;

import org.beangle.website.common.util.SeqStringUtil;

public class OnlineMessTypeAction extends ContentCommonAction{
	
	@Override
	protected String getEntityName() {
		return OnlineMessType.class.getName();
	}
	
	public String index(){
		put("sites",getSitesByCurrentUser());
		return forward();
	}
	
	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		OqlBuilder<OnlineMessType> oql = OqlBuilder.from(OnlineMessType.class,"onlineMessType");
		populateConditions(oql);
		Set<Site> sites = getSitesByCurrentUser();
    	if(sites != null && sites.size() > 0){
    		oql.where("onlineMessType.site in (:sites)",sites);
    	}else{
    		oql.where("1!=1");
    	}
		oql.orderBy(getOrderString("onlineMessType.typeCode"));
		oql.limit(getPageLimit()); 
		return oql;
	}
	
	public String edit() {
		OnlineMessType onlineMessType = (OnlineMessType) getEntity(OnlineMessType.class,"onlineMessType");
		put("sites",getSitesByCurrentUser());
		put("onlineMessType", onlineMessType);
		return forward();
	}

	public String save() {
		OnlineMessType onlineMessType = (OnlineMessType) populateEntity(OnlineMessType.class, "onlineMessType");
		entityDao.saveOrUpdate(onlineMessType);
		return redirect("search", "info.save.success");
	}
	
	public String remove(){
		String onlineMessTypeId = get("onlineMessType.ids");
        Long[] ids = SeqStringUtil.transformToLong(onlineMessTypeId);
        boolean flag = false;
        boolean success = false;
        for(int i=0;i<ids.length;i++){
        	OnlineMessType onlineMessType = (OnlineMessType) entityDao.get(OnlineMessType.class, ids[i]);
        	try {
				entityDao.remove(onlineMessType);
				success = true;
			} catch (Exception e) {
				flag = true;
			}
        }
        if (flag && !success) {
            return redirect("search", "已使用的未做处理");
        }
        if (success && flag) {
            return redirect("search", "删除成功，但已使用的未做处理");
        }
        
        return redirect("search", "删除成功");
	}
}
