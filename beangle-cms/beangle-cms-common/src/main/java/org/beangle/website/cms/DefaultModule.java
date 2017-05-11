package org.beangle.website.cms;

import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.spring.bind.Scope;
import org.beangle.website.cms.action.ColumnManagerAction;
import org.beangle.website.cms.action.FileUtilsAction;


/**
 * 
 * @author GOKU
 *
 */
public class DefaultModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		// TODO Auto-generated method stub
		bind(FileUtilsAction.class,ColumnManagerAction.class).in(Scope.PROTOTYPE);
	}

}
