package org.beangle.wenjuan.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.beangle.commons.exception.MyException;
import org.beangle.commons.lang.StrUtils;
import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.common.action.BaseCommonAction;
import org.beangle.wenjuan.model.Ksap;
import org.beangle.wenjuan.model.KsapParent;
import org.beangle.wenjuan.model.TiMu;
import org.beangle.wenjuan.model.WenJuan;
import org.beangle.wenjuan.model.Wjsz;
import org.beangle.wenjuan.model.Wjtm;
import org.beangle.wenjuan.service.TiKuService;
import org.beangle.wenjuan.service.WenJuanService;
import org.beangle.wenjuan.utils.DictTypeWJUtil;

public abstract class ZuJuanAction extends BaseCommonAction {
	
	protected TiKuService tiKuService;
	protected WenJuanService wenJuanService;
	
	public void setTiKuService(TiKuService tiKuService) {
		this.tiKuService = tiKuService;
	}
	
	public void setWenJuanService(WenJuanService wenJuanService) {
		this.wenJuanService = wenJuanService;
	}
	
	protected abstract String getWjfl();
	
	protected abstract String getName();

	@Override
	protected String getEntityName() {
		return getName();
	}
	
	@Override
	protected String getShortName() {
		return "wenJuan";
	}
	
	@Override
	protected void indexSetting() {
		put("wjlxs", findDictData(DictTypeWJUtil.WEN_JUAN_LX_CODE));
		super.indexSetting();
	}
	
	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		@SuppressWarnings("unchecked")
		OqlBuilder<WenJuan> query = (OqlBuilder<WenJuan>) super.getQueryBuilder();
//		query.where("wjfl.code = :wjfl", getWjfl());
		if(!isAdmin()){
			query.where("tjr.id = :tjrid", getUserId());
		}
		query.orderBy(getOrderString("id desc"));
		return query;
	}
	
	@Override
	protected void editSetting(Entity<?> entity) {
		WenJuan wenJuan = (WenJuan)entity;
		wenJuan.setWjfl(getDictData(getWjfl()));
		super.editSetting(entity);
	}
	
	@Override
	protected String saveAndForward(Entity<?> entity) {
		WenJuan wenjuan = (WenJuan)entity;
		if(wenjuan.getTjr() == null){
			wenjuan.setTjr(getCurrentUser());
		}
		if(wenjuan.getTjsj() == null){
			wenjuan.setTjsj(new Date());
		}
		if(wenjuan.getIpAddr() == null){
			wenjuan.setIpAddr(getRemoteAddr());
		}
		if(getWjfl() != null){
			wenjuan.setWjfl(getDictData(getWjfl()));
		}
		return super.saveAndForward(entity);
	}
	
	public String yuLan(){
		put("wjlx", getWjfl());
		WenJuan wenJuan = (WenJuan) getModel(getEntityName(), getEntityId(getShortName()));
		List<TiMu> tiMus = wenJuanService.getTiMu(wenJuan);
		put("wenJuan", wenJuan);
		put("tiMus", tiMus);
		return forward();
	}
	
	public String guDingTiMu(){
		WenJuan wenJuan = (WenJuan) getModel(getEntityName(), getEntityId(getShortName()));
		try {
			Map<Long, Wjsz> szmap = new HashMap<Long, Wjsz>();
			for(Wjsz wjsz : wenJuan.getWjszs()){
				wjsz.setSswj(null);
				szmap.put(wjsz.getId(), wjsz);
			}
			Long[] tmids = StrUtils.splitToLong(get("tmid"));
			List<Wjtm> wjtms = new ArrayList<Wjtm>();
			int px = wenJuan.getWjtms().size();
			for(Long tmid : tmids){
				Wjtm wjtm = new Wjtm();
				Wjsz wjsz = szmap.get(getLong("wjsz" + tmid));
				wjtm.setSswj(wenJuan);
				wjtm.setSstm(new TiMu(tmid));
				wjtm.setScore(wjsz.getMtfs());
				wjtm.setPx(++px);
				wjtms.add(wjtm);
			}
			wenJuan.getWjszs().clear();
			entityDao.remove(szmap.values());
			entityDao.saveOrUpdate(wjtms);
		} catch (Exception e) {
			logger.error("saveAndForwad failure", e);
		}
		
		return redirect("yuLan", "info.action.success", "wenJuan.id=" + wenJuan.getId());
	}
	
	public String faBu(){
		@SuppressWarnings("unchecked")
		Collection<WenJuan> wenJuans = getModels(getEntityName(), getEntityIds(getShortName()));
		try {
			int yfb = 0;
			for(WenJuan wj : wenJuans){
				if(wj.isEnabled()){
					yfb++;
					continue;
				}
				if(faBuEnable(wj)){
					wj.setEnabled(true);
					entityDao.saveOrUpdate(wj);
				}
			}
			String msg = "发布成功";
			if(yfb > 0){
				msg += "，其中" + yfb + "个问卷已发布，不能重复发布。";
			}
			return redirect("search", msg);
		} catch (MyException e) {
			logger.error("saveAndForwad failure", e);
			return redirect("search", e.getMessage());
		}catch (Exception e) {
			logger.error("saveAndForwad failure", e);
			return redirect("search", "发布失败");
		}
	}
	
	@Override
	protected String removeAndForward(Collection<?> entities) {
		@SuppressWarnings("unchecked")
		Collection<WenJuan> wenJuans = (Collection<WenJuan>) entities;
		List<WenJuan> removes = new ArrayList<WenJuan>();
		try {
			int yfb = 0;
			for(WenJuan wj : wenJuans){
				if(wj.isEnabled()){
					yfb++;
					continue;
				}
				removes.add(wj);
			}
			remove(removes);
			String msg = "删除成功";
			if(yfb > 0){
				msg += "，其中" + yfb + "个问卷已发布，不能删除。";
			}
			return redirect("search", msg);
		} catch (MyException e) {
			logger.error("removeAndForward failure", e);
			return redirect("search", e.getMessage());
		}catch (Exception e) {
			logger.error("removeAndForward failure", e);
			return redirect("search", "删除失败");
		}
	}
	
	protected boolean faBuEnable(WenJuan wj) {
		wenJuanService.findSjtm(wj);
		for(Wjsz sz : wj.getWjszs()){
			if(sz.getTiMus().size() < sz.getTmsl()){
				throw new MyException("随机题目数量不足，请进入随机问题设置查看题目数量。");
			}
		}
		if(wj.getWjszs().size() == 0 && wj.getWjtms().size() == 0){
			throw new MyException(" 该问卷没有题目。");
		}
		return true;
	}

	public String shouHui(){
		@SuppressWarnings("unchecked")
		Collection<WenJuan> wenJuans = getModels(getEntityName(), getEntityIds(getShortName()));
		//收回之前先判断有否问卷已出现在在线考试中，有则无法收回！tdf 20140620
		String ids="";
		Iterator<WenJuan> it = wenJuans.iterator();  
		while (it.hasNext()) { 
			ids+=it.next().getId();
		}
		OqlBuilder<KsapParent> query = OqlBuilder.from(KsapParent.class, "ksap");
		query.where("ksap.wenJuan.id in("+ids+")");
		query.where("ksap.startTime<=:kssj",new Date());
		query.where("ksap.endTime>=:jssj",new Date());
		populateConditions(query);
		List<KsapParent> ksaps=entityDao.search(query);
		if(ksaps!=null&&ksaps.size()>0){
			return redirect("search", "您选择的试卷中已经存在开始考试的试卷了，无法收回。");
		}
		try {
			int ysh = 0;
			for(WenJuan wj : wenJuans){
				if(!wj.isEnabled()){
					ysh++;
					continue;
				}
				wj.setEnabled(false);
			}
			String msg = "收回成功";
			if(ysh > 0){
				msg += "，其中" + ysh + "个问卷已收回，不能重复收回。";
			}
			entityDao.saveOrUpdate(wenJuans);
			return redirect("search", msg);
		} catch (Exception e) {
			logger.info("saveAndForwad failure", e);
			return redirect("search", "收回失败");
		}
	}
	
	public String fuZhi(){
		return forward();
	}
}
