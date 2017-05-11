package org.beangle.website.cms.action;

import java.util.List;

import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.cms.model.Entry;
import org.beangle.website.cms.model.Site;
import org.beangle.website.cms.services.SiteService;

public class EntryAction extends CommonAction {
	
	private SiteService siteService;
	
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	@Override
	protected String getEntityName() {
		return Entry.class.getName();
	}
	
	@Override
	protected void indexSetting() {
		put("sites", siteService.findSite());
		super.indexSetting();
	}
	
	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		@SuppressWarnings("unchecked")
		OqlBuilder<Entry> oql = (OqlBuilder<Entry>) super.getQueryBuilder();
		Long siteId = getLong("siteId");
		if(siteId != null){
			oql.join("entry.sites", "site");
			oql.where("site.id = :siteId", siteId);
		}
		return oql;
	}
	
	@Override
	protected void editSetting(Entity<?> entity) {
		put("sites", siteService.findSite());
		super.editSetting(entity);
	}
	
	@Override
	protected String saveAndForward(Entity<?> entity) {
		Entry entry = (Entry)entity;
		// 扩展属性
		List<Site> sites = entityDao.get(Site.class,getAll("siteIds", Long.class));
		entry.getSites().clear();
		entry.getSites().addAll(sites);
		return super.saveAndForward(entity);
	}
}
