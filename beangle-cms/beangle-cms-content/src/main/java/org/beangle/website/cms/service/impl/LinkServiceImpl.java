package org.beangle.website.cms.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.cms.model.Link;
import org.beangle.website.cms.model.LinksType;
import org.beangle.website.cms.service.LinkService;

public class LinkServiceImpl implements LinkService {
	
	private EntityDao entityDao;
	
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	public List<Link> findLink(String typeCode) {
		OqlBuilder<Link> query = OqlBuilder.from(Link.class, "link");
		query.where("link.enabled = true");
		query.where("link.linkType.typeCode = :typeCode", typeCode);
		query.orderBy("link.orders");
		query.cacheable();
		return entityDao.search(query);
	}

	public LinksType getLinkTypeByTypeCode(String typeCode) {
		OqlBuilder<LinksType> query = OqlBuilder.from(LinksType.class,"lt");
		query.where("lt.typeCode=:typeCode",typeCode);
		query.cacheable();
		List<LinksType> lts = entityDao.search(query);
		if(CollectionUtils.isNotEmpty(lts)){
			return lts.get(0);
		}
		return null;
	}

}
