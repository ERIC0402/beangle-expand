package org.beangle.wenjuan.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.lang.StrUtils;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.wenjuan.model.TiMu;
import org.beangle.wenjuan.model.WenJuan;
import org.beangle.wenjuan.model.Wjtm;
import org.beangle.wenjuan.service.WbtmService;


/**
 * 题库管理类
 * @author GZW
 *
 */
public class WjtmglAction extends WenJuanCommonAction {
	
	private WbtmService wbtmService;
	
	public void setWbtmService(WbtmService wbtmService) {
		this.wbtmService = wbtmService;
	}
	
	@Override
	protected String getEntityName() {
		return Wjtm.class.getName();
	}
	
	@Override
	protected void indexSetting() {
		put("wenJuanId", getLong("wenJuan.id"));
		super.indexSetting();
	}

	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		OqlBuilder<Wjtm> query = OqlBuilder.from(Wjtm.class, "wjtm");
		query.where("sswj.id = :sswjId", getLong("wenJuanId"));
		query.orderBy("wjtm.px");
//		query.limit(new PageLimit(1, Integer.MAX_VALUE));
		return query;
	}
	
	@Override
	public String search() {
		WenJuan wenJuan = entityDao.get(WenJuan.class, getLong("wenJuanId"));
		put("wenJuan", wenJuan);
		return super.search();
	}
	
	public String wbtj(){
		put("wenJuanId", getLong("wenJuanId"));
		return forward();
	}
	
	public String saveWbtj(){
		String textInput = get("textInput");
		List<TiMu> tmlist = wbtmService.getTiMus(textInput);
		if(!tmlist.isEmpty()){
			WenJuan wenJuan = entityDao.get(WenJuan.class, getLong("wenJuanId"));
			wbtmService.addTiMu(tmlist, wenJuan);
		}
		return redirect("search", "info.save.success");
	}

	/**
	 * 跳转到从题库添加题目页面
	 * @return
	 */
	public String tktj(){
		Long wenJuanId = getLong("wenJuanId");
		put("wenJuanId", wenJuanId);
		WenJuan wenJuan = entityDao.get(WenJuan.class, wenJuanId);
		put("wjfl", wenJuan.getWjfl().getCode());
		return forward();
	}
	
	/**
	 * 保存从题库添加题目
	 * @return
	 */
	public String saveTktj(){
		Long[] wjtmis = StrUtils.splitToLong(get("timu"));
		if(wjtmis.length > 0){
			Long wenJuanId = getLong("wenJuanId");
			OqlBuilder<Wjtm> query = OqlBuilder.from(Wjtm.class, "tm");
			query.where("tm.sswj.id = :wjid", wenJuanId);
			query.orderBy("px");
			List<Wjtm> owjtms = entityDao.search(query);
			Map<Long, Wjtm> wjtmmap = new HashMap<Long, Wjtm>();
			for(Wjtm wjtm : owjtms){
				wjtmmap.put(wjtm.getSstm().getId(), wjtm);
			}
			
			List<Wjtm> wjtms = new ArrayList<Wjtm>();
			for(Long i : wjtmis){
				Wjtm wjtm = populate(Wjtm.class, "timu" + i);
				//过滤重复题目
				if(wjtmmap.get(wjtm.getSstm().getId()) != null){
					continue;
				}else{
					wjtmmap.put(wjtm.getSstm().getId(), wjtm);
				}
				wjtms.add(wjtm);
			}
			entityDao.saveOrUpdate(wjtms);
			//重新计算排序
			Map<Integer, Wjtm> map = new HashMap<Integer, Wjtm>();
			for(Wjtm tm : wjtms){
				while(map.get(tm.getPx()) != null){
					tm.setPx(tm.getPx()+1);
				}
				map.put(tm.getPx(), tm);
			}
			if(!owjtms.isEmpty()){
				int px = 1;
				for(Wjtm tm : owjtms){
					while(map.get(px) != null){
						px++;
					}
					tm.setPx(px++);
				}
				entityDao.saveOrUpdate(owjtms);
			}
		}
		return redirect("search", "info.save.success");
	}
	
	/**
	 *  保存分数
	 * @return
	 */
	public String saveSzfs(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", StrUtils.splitToLong(get("ids")));
		if("tm".equals(get("type"))){
			params.put("score", getFloat("score"));
			entityDao.executeUpdateHql("update " + Wjtm.class.getName() + " set score = :score where id in (:ids)", params);
		}else{
			String xxScore = get("score");
			xxScore = xxScore.replaceAll(",", "，");
			params.put("xxScore", xxScore);
			entityDao.executeUpdateHql("update " + Wjtm.class.getName() + " set xxScore = :xxScore where id in (:ids)", params);
		}
		return forward();
	}
	
	/**
	 * 删除问卷题目时，也删除没有题库的题目
	 * 并自动排序
	 */
	@Override
	protected String removeAndForward(Collection<?> entities) {
		List<TiMu> tms = new ArrayList<TiMu>();
		for(Object o : entities){
			Wjtm wjtm = (Wjtm)o;
			TiMu tm = wjtm.getSstm();
			if(tm.getSstk() == null){
				tms.add(tm);
			}
		}
		String result = null;
		try {
			result =  super.removeAndForward(entities);
			remove(tms);
			autopx();
		} catch (Exception e) {
			logger.info("removeAndForwad failure", e);
			return redirect("search", "info.delete.failure");
		}
		if(result != null){
			return result;
		}else{
			return redirect("search", "info.remove.success");
		}
	}

	/**
	 * 自动排序
	 */
	private void autopx() {
		@SuppressWarnings("unchecked")
		List<Wjtm> wjtms = (List<Wjtm>) entityDao.search(getQueryBuilder());
		if(!wjtms.isEmpty()){
			for(int i = 0; i < wjtms.size(); i++){
				Wjtm tm = wjtms.get(i);
				tm.setPx(i+1);
			}
			entityDao.saveOrUpdate(wjtms);
		}
	}
	
	public String changePx(){
		Long[] ids = StrUtils.splitToLong(get("ids"));
		OqlBuilder<Wjtm> query = OqlBuilder.from(Wjtm.class, "tm");
		query.where("id in (:ids)", ids);
		List<Wjtm> wjtms = entityDao.search(query);
		if(wjtms.size() == 2){
			int px = wjtms.get(0).getPx();
			wjtms.get(0).setPx(wjtms.get(1).getPx());
			wjtms.get(1).setPx(px);
			entityDao.saveOrUpdate(wjtms);
		}
		return forward();
	}
}
