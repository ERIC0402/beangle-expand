package org.beangle.website.cms;

import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.spring.bind.Scope;
import org.beangle.website.cms.action.AddContentAction;
import org.beangle.website.cms.action.AnnexAction;
import org.beangle.website.cms.action.ColumnStatisticsAction;
import org.beangle.website.cms.action.ContentManageAction;
import org.beangle.website.cms.action.ContentStatisticsAction;
import org.beangle.website.cms.action.GroupColumnAction;
import org.beangle.website.cms.action.LinkAction;
import org.beangle.website.cms.action.LinksTypeAction;
import org.beangle.website.cms.action.OnlineMessAction;
import org.beangle.website.cms.action.OnlineMessTypeAction;
import org.beangle.website.cms.action.ProcessContentAction;
import org.beangle.website.cms.action.SetRelColumnAction;
import org.beangle.website.cms.service.impl.LinkServiceImpl;


/**
 * 
 * @author GOKU
 *
 */
public class CmsContentModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		bind(AnnexAction.class,AddContentAction.class,ProcessContentAction.class,SetRelColumnAction.class,
				ContentManageAction.class,LinksTypeAction.class,LinkAction.class,GroupColumnAction.class,
				ColumnStatisticsAction.class,ContentStatisticsAction.class,OnlineMessAction.class,OnlineMessTypeAction.class
				).in(Scope.PROTOTYPE);
		
		bind(LinkServiceImpl.class).shortName();
	}

}
