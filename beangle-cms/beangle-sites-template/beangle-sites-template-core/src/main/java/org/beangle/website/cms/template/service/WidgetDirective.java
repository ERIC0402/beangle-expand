/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.service;

import java.io.IOException;
import java.util.Map;

import org.beangle.website.cms.template.widgets.WidgetUtils;


import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 *
 * @author CHII
 */
public class WidgetDirective implements TemplateDirectiveModel {

    private static WidgetUtils widgetUtils;

    public void setWidgetUtils(WidgetUtils widgetUtils) {
        WidgetDirective.widgetUtils = widgetUtils;
    }

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String className = params.get("className").toString();
        String config = params.get("config").toString();
        env.getOut().write(widgetUtils.getContent(className, config));
    }
}
