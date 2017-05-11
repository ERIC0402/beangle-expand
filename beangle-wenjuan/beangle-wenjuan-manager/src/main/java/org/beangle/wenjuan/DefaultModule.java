package org.beangle.wenjuan;

import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.spring.bind.Scope;
import org.beangle.wenjuan.action.DcwjAction;
import org.beangle.wenjuan.action.KsapAction;
import org.beangle.wenjuan.action.PjglAction;
import org.beangle.wenjuan.action.SelectAction;
import org.beangle.wenjuan.action.SjglAction;
import org.beangle.wenjuan.action.TiKuAction;
import org.beangle.wenjuan.action.TiMuAction;
import org.beangle.wenjuan.action.TmflAction;
import org.beangle.wenjuan.action.WjtmglAction;
import org.beangle.wenjuan.action.XxmbAction;
import org.beangle.wenjuan.action.ZxksfxAction;
import org.beangle.wenjuan.action.ZxksglAction;
import org.beangle.wenjuan.service.impl.TiKuServiceImpl;
import org.beangle.wenjuan.service.impl.WbtmServiceImpl;
import org.beangle.wenjuan.service.impl.WenJuanServiceImpl;
import org.beangle.wenjuan.service.impl.WenJuanSyncServiceImpl;
import org.beangle.wenjuan.service.impl.WjjgServiceImpl;
import org.beangle.wenjuan.service.impl.ZxksServiceImpl;
import org.beangle.xuesheng.action.ZxksAction;
import org.beangle.xuesheng.action.ZxkscjAction;


/**
 * 
 * @author GOKU
 *
 */
public class DefaultModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		
		bind(SelectAction.class).in(Scope.PROTOTYPE);

		bind(TiKuAction.class,XxmbAction.class,TiMuAction.class).in(Scope.PROTOTYPE);
		
		bind(SjglAction.class, PjglAction.class, DcwjAction.class, WjtmglAction.class).in(Scope.PROTOTYPE);
		
		bind(KsapAction.class, ZxksAction.class, ZxkscjAction.class, ZxksglAction.class, ZxksfxAction.class).in(Scope.PROTOTYPE);
		
		bind(TmflAction.class).in(Scope.PROTOTYPE);

		//bind("tiMuAction2", TiMuAction.class);
		bind("tiKuService", TiKuServiceImpl.class);
		bind("wbtmUtil", WbtmServiceImpl.class);
		bind("wenJuanService", WenJuanServiceImpl.class);
		bind("wjjgService", WjjgServiceImpl.class);
		bind(ZxksServiceImpl.class).shortName();
		bind(WenJuanSyncServiceImpl.class).shortName();
	}

}
