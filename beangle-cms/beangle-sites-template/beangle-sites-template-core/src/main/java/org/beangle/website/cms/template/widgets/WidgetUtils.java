/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.widgets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.struts2.ServletActionContext;
import org.beangle.model.persist.EntityDao;
import org.beangle.website.cms.template.service.ColumnService;
import org.beangle.website.cms.template.service.TemplateService;

import org.beangle.website.cms.model.Widget;

import freemarker.template.TemplateException;

/**
 * 
 * @author CHII
 */
public class WidgetUtils {
	private EntityDao entityDao;
	private TemplateService templateService;
	private ColumnService columnService;

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	public void setColumnService(ColumnService columnService) {
		this.columnService = columnService;
	}

	public WidgetSupport getWidget(String className) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
		@SuppressWarnings("unchecked")
		Class<WidgetSupport> clazz = (Class<WidgetSupport>) Class.forName(className);
		WidgetSupport ws = (WidgetSupport) clazz.newInstance();
		return ws;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	public String getDefaultImg(String className) throws ClassNotFoundException {
		StringBuilder sb = new StringBuilder();
		sb.append(getRootPath(className));
		sb.append("/img.jpg");
		return sb.toString();
	}

	public String getRootPath(String className) throws ClassNotFoundException {
		StringBuilder sb = new StringBuilder();
		@SuppressWarnings("rawtypes")
		Class clazz = Class.forName(className);
		String dir = clazz.getSimpleName();
		dir = dir.substring(0, 1).toLowerCase() + dir.substring(1);
		sb.append(className.substring(WidgetSupport.class.getName().lastIndexOf(".") + 1, className.lastIndexOf(".") + 1).replaceAll("\\.", "/"));
		sb.append(dir);
		return sb.toString();
	}

	public String getContent(String className, String config) {
		try {
			WidgetSupport ws = getWidgetSupport(className);
			return ws.getContent(config);
		} catch (IOException ex) {
			Logger.getLogger(WidgetUtils.class.getName()).log(Level.SEVERE, null, ex);
		} catch (TemplateException ex) {
			Logger.getLogger(WidgetUtils.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			Logger.getLogger(WidgetUtils.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(WidgetUtils.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(WidgetUtils.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "出错了";
	}

	public String getConfig(Widget widget, String config) {
		try {
			WidgetSupport ws = getWidgetSupport(widget);
			return ws.getConfig(config);
		} catch (IOException ex) {
			Logger.getLogger(WidgetUtils.class.getName()).log(Level.SEVERE, null, ex);
		} catch (TemplateException ex) {
			Logger.getLogger(WidgetUtils.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			Logger.getLogger(WidgetUtils.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(WidgetUtils.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(WidgetUtils.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "出错了";
	}

	private WidgetSupport getWidgetSupport(Widget widget) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
		WidgetSupport ws = getWidgetSupport(widget.getClassName());
		ws.setId(widget.getId());
		return ws;
	}

	private WidgetSupport getWidgetSupport(String className) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
		WidgetSupport ws = getWidget(className);
		ws.setEntityDao(entityDao);
		ws.setTemplateService(templateService);
		ws.setColumnService(columnService);
		ws.setServletRequest(ServletActionContext.getRequest());
		ws.setServletResponse(ServletActionContext.getResponse());
		return ws;
	}
}
