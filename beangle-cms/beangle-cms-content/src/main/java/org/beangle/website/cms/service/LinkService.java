package org.beangle.website.cms.service;

import java.util.List;

import org.beangle.website.cms.model.Link;
import org.beangle.website.cms.model.LinksType;

public interface LinkService {

	public List<Link> findLink(String typeCode);

	public LinksType getLinkTypeByTypeCode(String typeCode);
}
