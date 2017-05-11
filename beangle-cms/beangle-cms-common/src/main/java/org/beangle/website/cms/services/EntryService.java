package org.beangle.website.cms.services;

import java.util.List;

import org.beangle.website.cms.model.Entry;

public interface EntryService {
	
	public List<Entry> find(Long siteId);

}
