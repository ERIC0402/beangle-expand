package org.beangle.website.cms.action;

import java.util.List;
import java.util.Set;

import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.cms.action.ContentCommonAction;
import org.beangle.website.cms.model.LinksType;
import org.beangle.website.cms.model.Site;

import org.beangle.website.common.util.SeqStringUtil;

/**
 * 链接类型管理
 * @author DONHKO
 *
 */
public class LinksTypeAction extends ContentCommonAction {
	
	@Override
	protected String getEntityName() {
		return LinksType.class.getName();
	}
   
    public String index() {
        put("sites", getSitesByCurrentUser());
        return forward();
    }
    
    @Override
    protected QueryBuilder<?> getQueryBuilder() {
    	OqlBuilder<LinksType> oql = OqlBuilder.from(LinksType.class,"linksType");
    	Set<Site> sites = getSitesByCurrentUser();
    	if(sites != null && sites.size() > 0){
    		oql.where("linksType.site in (:sites)",sites);
    	}else{
    		oql.where("1!=1");
    	}
		populateConditions(oql);
		oql.orderBy(getOrderString("linksType.typeCode"));
		oql.limit(getPageLimit()); 
		return oql;
    }

    public String edit() {
        LinksType sitetype = (LinksType) getEntity(LinksType.class, "linksType");
        put("sites", getSitesByCurrentUser());
        put("linksType", sitetype);
        return forward();
    }

    public String save() {
        LinksType site_type = (LinksType) populateEntity(LinksType.class, "linksType");
        entityDao.saveOrUpdate(site_type);
        return redirect("search", "info.save.success");
    }

    public String remove() {
        String sitetypeId = get("linksType.ids");
        Long[] ids = SeqStringUtil.transformToLong(sitetypeId);
        boolean flag = false;
        for(int i=0;i<ids.length;i++){
        	LinksType sitetype = (LinksType) entityDao.get(LinksType.class, ids[i]);
        	try {
				entityDao.remove(sitetype);
			} catch (Exception e) {
				flag = true;
			}
        }
        if (flag) {
            return redirect("search", "删除成功，但已使用的未做处理");
        }
        
        return redirect("search", "删除成功");
    }
}
