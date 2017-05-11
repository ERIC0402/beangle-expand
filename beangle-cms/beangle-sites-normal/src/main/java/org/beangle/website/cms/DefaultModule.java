package org.beangle.website.cms;

import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.spring.bind.Scope;
import org.beangle.website.cms.action.ColumnAction;
import org.beangle.website.cms.action.SiteAction;


/**
 * 
 * @author GOKU
 *
 */
public class DefaultModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		// TODO Auto-generated method stub
		bind(SiteAction.class,ColumnAction.class).in(Scope.PROTOTYPE);
	}

}
