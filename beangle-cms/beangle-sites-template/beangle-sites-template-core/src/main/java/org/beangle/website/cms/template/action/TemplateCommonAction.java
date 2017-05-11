package org.beangle.website.cms.template.action;

import org.beangle.website.cms.template.widgets.WidgetSupport;

import org.beangle.website.common.action.FileAction;

public class TemplateCommonAction extends FileAction {
	public void showWidgetImg() {
		String img = get("img");
		String contentType = "image/jpeg";
		outPutFile(WidgetSupport.class.getResourceAsStream(img), "img.jpg", contentType);
	}
}
