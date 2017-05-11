package org.beangle.website.expand;

import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.spring.bind.Scope;

import org.beangle.website.expand.action.ScheduleAction;

/**
 * 
 * @author GOKU
 *
 */
public class DefaultModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		bind(ScheduleAction.class).in(Scope.PROTOTYPE);
	}

}
