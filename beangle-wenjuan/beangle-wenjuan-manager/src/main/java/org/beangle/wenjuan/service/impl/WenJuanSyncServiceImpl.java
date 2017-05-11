package org.beangle.wenjuan.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.system.model.DictTree;
import org.beangle.website.system.service.DictTreeService;
import org.beangle.wenjuan.model.TiKu;
import org.beangle.wenjuan.model.TiMu;
import org.beangle.wenjuan.service.WenJuanSyncService;

public class WenJuanSyncServiceImpl implements WenJuanSyncService {
	
	private EntityDao entityDao;
	private DictTreeService dictTreeService;
	
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
	
	public void setDictTreeService(DictTreeService dictTreeService) {
		this.dictTreeService = dictTreeService;
	}

	public TiKu getTiKu(String code) {
		OqlBuilder<TiKu> query = OqlBuilder.from(TiKu.class, "tiKu");
		query.where("tiKu.code = :code", code);
		query.orderBy("tiKu.id");
		List<TiKu> list = entityDao.search(query);
		if(list.isEmpty()){
			TiKu tiKu = new TiKu();
			tiKu.setCode(code);
			return tiKu;
		}else{
			return list.get(0);
		}
	}

	public DictTree getTmfl(TiKu tiKu) {
		if(tiKu.getTkfl() != null){
			return tiKu.getTkfl();
		}
		if(tiKu.getId() == null){
			entityDao.saveOrUpdate(tiKu);
		}
		String code = "TMFL_" + tiKu.getId();
		DictTree tmfl = getTmfl(code);
		if(tmfl == null){
			tmfl = new DictTree();
			tmfl.setName(tiKu.getTkmc() + "的题目分类");
			tmfl.setDm(code);
			DictTree top = getTmfl("TMFL");
			tmfl.setParent(top);
			dictTreeService.move(tmfl, top, Integer.MAX_VALUE);
			return tmfl;
		}else{
			tiKu.setTkfl(tmfl);
			return tmfl;
		}
	}

	public DictTree getTmfl(String code) {
		OqlBuilder<DictTree> query = OqlBuilder.from(DictTree.class, "dictTree");
		query.where("dictTree.dm = :dm",  code);
		query.orderBy("dictTree.id");
		List<DictTree> list = entityDao.search(query);
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	public void addTmfl(DictTree dictTree, DictTree parent, int indexno) {
		dictTreeService.move(dictTree, parent, indexno);
	}

	public Map<String, Long> getTmflCount(TiKu tiKu, String syfwCode) {
		OqlBuilder<TiMu> query = OqlBuilder.from(TiMu.class, "tiMu");
		query.where("tiMu.sstk.id = :sstkid", tiKu.getId());
		if(syfwCode != null){
			query.where("tiMu.syfw.code = :syfwcode", syfwCode);
		}
		query.where("tiMu.enabled = true");
		query.select("tiMu.tmfl.dm, count(*)");
		query.groupBy("tiMu.tmfl.id, tiMu.tmfl.dm");
		@SuppressWarnings("rawtypes")
		List list = entityDao.search(query);
		Map<String, Long> map = new HashMap<String, Long>();
		for(Object o : list){
			Object[] oo = (Object[]) o;
			map.put((String)oo[0], (Long)oo[1]);
		}
		return map;
	}

	public List<TiMu> findTiMu(DictTree tmfl, String syfwCode) {
		return null;
	}

}
