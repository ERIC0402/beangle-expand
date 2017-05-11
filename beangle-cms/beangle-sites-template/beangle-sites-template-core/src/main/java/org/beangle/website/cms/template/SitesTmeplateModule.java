package org.beangle.website.cms.template;

import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.spring.bind.Scope;
import org.beangle.website.cms.template.action.FrontAction;
import org.beangle.website.cms.template.action.TemplateAction;
import org.beangle.website.cms.template.action.TemplateCommonAction;
import org.beangle.website.cms.template.action.TemplateGroupAction;
import org.beangle.website.cms.template.action.TemplateLayoutAction;
import org.beangle.website.cms.template.action.WidgetAction;
import org.beangle.website.cms.template.service.impl.ColumnServiceImpl;
import org.beangle.website.cms.template.service.impl.TemplateServiceImpl;
import org.beangle.website.cms.template.widgets.WidgetUtils;


/**
 * 
 * @author GOKU
 *
 */
public class SitesTmeplateModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		//模板布局、模板组、组件、模板
		bind(TemplateLayoutAction.class,TemplateGroupAction.class, WidgetAction.class, TemplateAction.class).in(Scope.PROTOTYPE);
		//栏目 使用struts-plugin.xml配置
//		bind(ColumnAction.class).in(Scope.PROTOTYPE);
		//站点管理 使用struts-plugin.xml配置
//		bind(SiteAction.class).in(Scope.PROTOTYPE);
		//前台显示
		bind(FrontAction.class).in(Scope.PROTOTYPE);
		//公共类
		bind(TemplateCommonAction.class);

		//组件工具
		bind("widgetUtils", WidgetUtils.class);
		//栏目服务
		bind("columnService", ColumnServiceImpl.class);
		//模板服务
		bind("templateService", TemplateServiceImpl.class);
	}

}
