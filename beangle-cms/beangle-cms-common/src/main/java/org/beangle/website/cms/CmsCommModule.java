package org.beangle.website.cms;

import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.spring.bind.Scope;
import org.beangle.website.cms.action.AdSettingAction;
import org.beangle.website.cms.action.ColumnManagerAction;
import org.beangle.website.cms.action.FileUtilsAction;
import org.beangle.website.cms.services.impl.EntryServiceImpl;
import org.beangle.website.cms.services.impl.SiteServiceImpl;

/**
 * 
 * @author GOKU
 * 
 */
public class CmsCommModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		bind(FileUtilsAction.class, ColumnManagerAction.class, AdSettingAction.class).in(Scope.PROTOTYPE);

		bind("siteService", SiteServiceImpl.class);
		bind("entryService", EntryServiceImpl.class);
	}

}
