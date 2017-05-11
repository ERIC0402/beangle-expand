package org.beangle.website.cms;

import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.spring.bind.Scope;
import org.beangle.website.cms.action.FloatingBarAction;


/**
 * 
 * @author GOKU
 *
 */
public class DefaultModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		// TODO Auto-generated method stub
		bind(FloatingBarAction.class).in(Scope.PROTOTYPE);
	}

}
