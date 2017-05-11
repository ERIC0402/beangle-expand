package org.beangle.website.cms;

import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.spring.bind.Scope;
import org.beangle.website.cms.action.ColumnAction;
import org.beangle.website.cms.action.EntryAction;
import org.beangle.website.cms.action.SiteAction;


/**
 * 
 * @author GOKU
 *
 */
public class CmsNormalModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		bind(SiteAction.class,ColumnAction.class).in(Scope.PROTOTYPE);
		bind(EntryAction.class).in(Scope.PROTOTYPE);
	}

}
