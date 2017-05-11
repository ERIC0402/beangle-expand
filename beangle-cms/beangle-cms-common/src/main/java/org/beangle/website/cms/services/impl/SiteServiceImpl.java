package org.beangle.website.cms.services.impl;

import java.util.List;

import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.cms.model.Site;
import org.beangle.website.cms.services.SiteService;

public class SiteServiceImpl implements SiteService {
	
	private EntityDao entityDao;
	
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	public List<Site> findSite() {
		OqlBuilder<Site> oql = OqlBuilder.from(Site.class, "site");
		oql.where("site.enabled = 1");
		return entityDao.search(oql);
	}

}
