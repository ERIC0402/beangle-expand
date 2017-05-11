/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;

import org.beangle.model.pojo.LongIdObject;


/**
 *
 * @author CHII
 */
@Entity(name="org.beangle.website.cms.model.SiteWidgetConfig")
public class SiteWidgetConfig extends LongIdObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//站点
    private Site site;
    //模板配置
    private WidgetConfig widgetConfig;
    //站点配置
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(columnDefinition="CLOB")
    private String config;

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public WidgetConfig getWidgetConfig() {
        return widgetConfig;
    }

    public void setWidgetConfig(WidgetConfig widgetConfig) {
        this.widgetConfig = widgetConfig;
    }
}
