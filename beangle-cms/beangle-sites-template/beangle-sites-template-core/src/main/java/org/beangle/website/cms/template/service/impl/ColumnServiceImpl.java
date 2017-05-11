/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.service.impl;

import java.util.List;
import org.beangle.model.persist.EntityDao;
import org.beangle.website.cms.template.service.ColumnService;

import org.beangle.website.cms.model.Column;
import org.beangle.website.cms.model.Site;

/**
 * 
 * @author CHII
 */
public class ColumnServiceImpl implements ColumnService {
	private EntityDao entityDao;

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	public Column getColumn(Site site) {
		if (site == null) {
			return null;
		}
		List<Column> list = entityDao.searchHQLQuery("from org.beangle.website.cms.model.Column where type.code = 'COLUMN_TYPE_INDEX' and site.id = ?", new Object[] { site.getId() });
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}
}
