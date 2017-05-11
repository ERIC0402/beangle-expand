package org.beangle.website.cms.services.impl;

import java.util.List;

import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.cms.model.Entry;
import org.beangle.website.cms.services.EntryService;

public class EntryServiceImpl implements EntryService {
	
	private EntityDao entityDao;
	
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	public List<Entry> find(Long siteId) {
		OqlBuilder<Entry> oql = OqlBuilder.from(Entry.class, "entry");
		oql.where("entry.enabled = true");
		if(siteId != null){
			oql.join("entry.sites", "site");
			oql.where("site.id = :siteId", siteId);
		}
		return entityDao.search(oql);
	}

}
