package org.beangle.wenjuan.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.service.DictDataService;
import org.beangle.wenjuan.model.TiMu;
import org.beangle.wenjuan.model.Tmxx;
import org.beangle.wenjuan.model.WenJuan;
import org.beangle.wenjuan.model.Wjtm;
import org.beangle.wenjuan.service.WbtmService;
import org.beangle.wenjuan.utils.DictTypeWJUtil;

public class WbtmServiceImpl extends BaseServiceImpl implements WbtmService{
	
	private Map<String, DictData> tmlxMap;
	private DictDataService dictDataService;
	
	public void setDictDataService(DictDataService dictDataService) {
		this.dictDataService = dictDataService;
	}
	
	/**
	 * 获得题目分类Map
	 * @return
	 */
	public Map<String, DictData> getTmlxMap() {
		if(tmlxMap == null){
			tmlxMap = new HashMap<String, DictData>();
			List<DictData> list = dictDataService.findDictData(DictTypeWJUtil.WJ_TI_MU_LX_CODE);
			for(DictData dd : list){
				tmlxMap.put(dd.getName(), dd);
			}
		}
		return tmlxMap;
	}

	/**
	 * 解析文本获得题目
	 * @param text
	 * @return
	 */
	public List<TiMu> getTiMus(String text) {
		List<TiMu> tmlist = new ArrayList<TiMu>();
		if(StringUtils.isEmpty(text)){
			return tmlist;
		}
		text +="\n#end";
		List<String> lines = format(text);
		List<String> tmlines = new ArrayList<String>();
		for(String line : lines){
			if("#end".equals(line)){
				break;
			}
			line = line.trim();
			if(StringUtils.isEmpty(line)){
				addTimu(tmlist, tmlines);
				tmlines.clear();
			}else{
				tmlines.add(line);
			}
		}
		return tmlist;
	}

	private List<String> format(String text) {
		text = text.replaceAll("\r", "");
		String[] lines = text.split("\n");
		List<String> data = new ArrayList<String>();
		for(String line : lines){
			data.add(line);
		}
		return data;
	}

	/**
	 * 添加题目
	 * @param tmlist
	 * @param tmlines
	 */
	private void addTimu(List<TiMu> tmlist, List<String> tmlines) {
		TiMu timu = getTiMu(tmlines);
		if(timu != null){
			tmlist.add(timu);
			if(!timu.getWgztms().isEmpty()){
				tmlist.addAll(timu.getWgztms());
			}
		}
	}

	/**
	 * 获得一个题目
	 * @param tmlines
	 * @return
	 */
	private TiMu getTiMu(List<String> tmlines) {
		if(tmlines.isEmpty()){
			return null;
		}
		
		TiMu timu = new TiMu();
		String config = getConfig(tmlines);
		String tmlx = getConfig("题目类型", config, "单选题");
		timu.setTmmc(tmlines.get(0));
		timu.setTmlx(getTmlxMap().get(tmlx));
		if("网格题".equals(tmlx)){
			setWanGeTiMu(timu, config, tmlines);
		}else{
			timu.setZqda(getConfig("正确答案", config));
			if(timu.getZqda() == null){
				setZqda(timu);
			}
			addxx(timu, tmlines);		
		}
		if(timu.getTmxxs().size() == 2){
			timu.setTmlx(getTmlxMap().get("判断题"));
		}
		return timu;
	}

	private void setWanGeTiMu(TiMu timu, String config, List<String> tmlines) {
		String tmxxstr = getConfig("题目选项", config);
		if(StringUtils.isNotEmpty(tmxxstr)){
			if(timu.getTmxxs() == null){
				timu.setTmxxs(new HashSet<Tmxx>());
			}
			tmxxstr = tmxxstr.replaceAll(",", "，");
			tmxxstr = tmxxstr.replaceAll("、", "，");
			String[] tmxxs = tmxxstr.split("，");
			char no = 'A';
			for(int i = 0; i < tmxxs.length; i++){
				Tmxx tmxx = getTmxx(tmxxs[i]);
				if(tmxx != null){
					tmxx.setPx(no++ + "");
					tmxx.setSstm(timu);
					timu.getTmxxs().add(tmxx);
				}
			}
		}
		
		if(timu.getWgztms() == null){
			timu.setWgztms(new HashSet<TiMu>());
		}
		for(int i = 2; i < tmlines.size(); i++){
			String tmmc = tmlines.get(i);
			TiMu ztm = new TiMu();
			ztm.setSjtm(timu);
			ztm.setTmmc(tmmc);
			ztm.setSstk(null);
			ztm.setEnabled(true);
			ztm.setPx(i);
			timu.getWgztms().add(ztm);	
		}
	}


	/**
	 * 根据题目名称获得答案
	 * 题目名称以“。”或“.”结尾，后面是答案
	 * @param tmmc
	 * @return
	 */
	private void setZqda(TiMu tm) {
		String tmmc = tm.getTmmc();
		String[] ends = {"。", "."};
		int index = -1;
		for(String end : ends){
			if(tmmc.lastIndexOf(end) < tmmc.length() - 1){
				index = Math.max(index, tmmc.lastIndexOf(end));
			}
		}
		if(index > 0){
			String zqda = tmmc.substring(index + 1);
			tm.setZqda(zqda);
			tm.setTmmc(tmmc.substring(0, index));
		}
	}

	/**
	 * 添加选项
	 * @param timu
	 * @param tmlines
	 */
	private void addxx(TiMu timu, List<String> tmlines) {
		if(timu.getTmxxs() == null){
			timu.setTmxxs(new HashSet<Tmxx>());
		}
		char no = 'A';
		for(int i = 1; i < tmlines.size(); i++){
			Tmxx tmxx = getTmxx(tmlines.get(i));
			if(tmxx != null){
				tmxx.setPx(no++ + "");
				tmxx.setSstm(timu);
				timu.getTmxxs().add(tmxx);
			}
		}
	}

	/**
	 * 获得选项
	 * @param string
	 * @return
	 */
	private Tmxx getTmxx(String xx) {
		if(xx.indexOf("[") == 0 && xx.lastIndexOf("]") == xx.length() -1){
			return null;
		}
		Tmxx tmxx = new Tmxx();
		String[] subs = {".", "。", "、", "．"};
		for(String sub : subs){
			xx = subTmxx(xx, sub);
		}
		tmxx.setXxnr(xx);
		return tmxx;
	}
	
	private String subTmxx(String xx, String sub){
		if(xx.indexOf(sub) < 2){
			xx = xx.substring(xx.indexOf(sub)+1);
		}
		return xx;
	}

	/**
	 * 根据类型获得配置
	 * @param type
	 * @param config
	 * @param dvalue
	 * @return
	 */
	private String getConfig(String type, String config) {
		return getConfig(type, config, null);
	}
	private String getConfig(String type, String config, String dvalue) {
		if(config == null){
			return dvalue;
		}
		Pattern pattern = Pattern.compile("\\["+type+"：[^\\]]*\\]");
		Matcher matcher = pattern.matcher(config);
		if(matcher.find()){
			String value = matcher.group();
			String[] values = value.split("：");
			if(values.length != 2){
				return null;
			}
			value = values[1];
			value = value.substring(0, value.length() -1);
			return value;
		}
		return null;
	}

	/**
	 * 解析文本获得配置
	 * @param tmlines
	 * @return
	 */
	private String getConfig(List<String> tmlines) {
		String config = null;
		for(String line : tmlines){
			if(line.indexOf("[") == 0 && line.lastIndexOf("]") == line.length() -1){
				config = line;
				break;
			}
		}
		return config;
	}
	
	private List<Wjtm> findWjtm(WenJuan wenJuan){
		OqlBuilder<Wjtm> query = OqlBuilder.from(Wjtm.class, "wjtm");
		query.where("sswj.id = :sswjId", wenJuan.getId());
		query.orderBy("wjtm.px");
		query.limit(new PageLimit(1, Integer.MAX_VALUE));
		return entityDao.search(query);
	}

	public void addTiMu(List<TiMu> tmlist, WenJuan wenJuan) {
		List<Wjtm> wjtmList = findWjtm(wenJuan);
		int px = 1;
		if(!wjtmList.isEmpty()){
			for(Wjtm wjtm : wjtmList){
				wjtm.setPx(px++);
			}
			entityDao.saveOrUpdate(wjtmList);
		}
		List<Wjtm> nwjtmList = new ArrayList<Wjtm>();
		for(TiMu tm : tmlist){
			if(tm.getSjtm() != null){
				continue;
			}
			Wjtm wjtm = new Wjtm();
			wjtm.setSswj(wenJuan);
			wjtm.setSstm(tm);
			wjtm.setPx(px++);
			nwjtmList.add(wjtm);
		}
		entityDao.saveOrUpdate(tmlist);
		entityDao.saveOrUpdate(nwjtmList);
	}
	
}
