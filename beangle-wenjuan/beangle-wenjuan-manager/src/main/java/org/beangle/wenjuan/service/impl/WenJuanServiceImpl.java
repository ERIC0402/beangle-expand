package org.beangle.wenjuan.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.helper.Params;
import org.beangle.website.common.action.FileAction;
import org.beangle.website.common.util.CommonUtils;
import org.beangle.website.system.model.DictTree;
import org.beangle.wenjuan.model.TiKu;
import org.beangle.wenjuan.model.TiMu;
import org.beangle.wenjuan.model.Tmxx;
import org.beangle.wenjuan.model.WenJuan;
import org.beangle.wenjuan.model.Wjsz;
import org.beangle.wenjuan.model.Wjtm;
import org.beangle.wenjuan.model.Xxmb;
import org.beangle.wenjuan.service.WenJuanService;
import org.beangle.wenjuan.utils.DictDataWJUtil;

public class WenJuanServiceImpl extends BaseServiceImpl implements WenJuanService {

	/**
	 * 查询问卷题目
	 * 随机题目保存在设置里
	 * @param wenJuan
	 * @return 固定问卷题目
	 */
	public List<TiMu> getTiMu(WenJuan wenJuan) {
		List<Wjtm> wjtms = findWjtm(wenJuan);
		Map<Long, Wjtm> tmmap = new HashMap<Long, Wjtm>();
		List<TiMu> tiMus = new ArrayList<TiMu>();
		for(Wjtm wjtm : wjtms){
			if(tmmap.get(wjtm.getSstm().getId()) != null){
				entityDao.remove(wjtm);
			}else{
				tmmap.put(wjtm.getSstm().getId(), wjtm);
				tiMus.add(wjtm.getSstm());
			}
		}
		return tiMus;
	}
	
	public void findSjtm(WenJuan wenJuan){
		for(Wjsz wjsz : wenJuan.getWjszs()){
			OqlBuilder<TiMu> query = OqlBuilder.from(TiMu.class, "tm");
			query.where("tm.enabled = 1");
			if(wjsz.getTk() != null){
				query.where("tm.sstk.id = :tkid", wjsz.getTk().getId());
			}
			if(wjsz.getTmfl() != null){
				query.where("tm.tmfl.code like :tmflid", wjsz.getTmfl().getCode() + "%");
			}
			if(wjsz.getTmlx() != null){
				query.where("tm.tmlx.id = :tmlxid", wjsz.getTmlx().getId());
			}
			//新增 问卷难度的抽取。 tdf 20140620
			if(wjsz.getTmnd() != null){
				query.where("tm.tmnd.id = :tmndid", wjsz.getTmnd().getId());
			}
			query.limit(new PageLimit(1, wjsz.getTmsl()));
			query.orderBy("rand()");
			wjsz.setTiMus(entityDao.search(query));
		}
	}

	public List<Wjtm> findWjtm(WenJuan wenJuan) {
		return getWjtm(wenJuan);
	}
	/**
	 * 查询问卷题目
	 * 随机题目保存在设置里
	 * @param wenJuan
	 * @return 固定问卷题目
	 */
	public List<Wjtm> getWjtm(WenJuan wenJuan) {
		this.findSjtm(wenJuan);
		List<Wjtm> tiMus = entityDao.searchHQLQuery("select wjtm from " + Wjtm.class.getName() + " wjtm where wjtm.sswj.id = ? order by wjtm.px", wenJuan.getId());
		return tiMus;
	}

	public void updateTikuTimuNum() {
		entityDao.executeUpdateHql("update " + TiKu.class.getName() + " tk set tk.tmsl = (select count(tm.id) from " + TiMu.class.getName() + " tm where tm.sstk.id = tk.id)");
	}

	public void updateTikuTimuNum(Long tiKuId) {
		entityDao.executeUpdateHql("update " + TiKu.class.getName() + " tk set tk.tmsl = (select count(tm.id) from " + TiMu.class.getName() + " tm where tm.sstk.id = tk.id) where id = " + tiKuId);
	}

	public List<Xxmb> findXxmb() {
		OqlBuilder<Xxmb> query = OqlBuilder.from(Xxmb.class,"xxmb");
		query.where("xxmb.enabled=1");
		return entityDao.search(query);
	}

	protected String get(String paramName) {
		return Params.get(paramName);
	}

	public String saveTimu(TiMu tiMu, FileAction action) {
		// 删除就文件、保存新文件
		String oldFileName = get("oldTMPIC");
		if(StringUtils.isEmpty(oldFileName)){
			oldFileName = get("oldImg");
		}
		String tempFileName = tiMu.getPic();
		tiMu.setPic(action.moveAndRemoveAnnex(tempFileName, oldFileName));

		//更新题库题目数量
		if(tiMu.getSstk() != null){
			updateTikuTimuNum(tiMu.getSstk().getId());
		}
		String msg = "";
		String tmlx = tiMu.getTmlx().getCode();
		// 如果题目类型为“普通节点”则不保存选项
		Set<TiMu> ztms = CollectUtils.newHashSet();
		tiMu.setWgztms(ztms);
		if (StringUtils.isNotEmpty(tmlx) && !(DictDataWJUtil.WJ_TI_MU_LX_JI_DIAN.equals(tmlx))) {
			// 如果题目类型为网格题，则需要保存子题目
			if (DictDataWJUtil.WJ_TI_MU_LX_WANG_GE.equals(tmlx)) {
//				String wgztm = get("wgztmnr");
				String wgztm = tiMu.getWgztm();
				tiMu.setWgztm(wgztm);
				if (StringUtils.isNotEmpty(wgztm)) {
					String[] wgztms = wgztm.split("\n");
					// 查询旧的子题目
					OqlBuilder<TiMu> query = OqlBuilder.from(TiMu.class, "t");
					query.where("t.sjtm.id=:sjtmId", tiMu.getId());
					query.orderBy("t.px");
					List<TiMu> ztmOlds = entityDao.search(query);
					int count = ztmOlds == null ? 0 : ztmOlds.size();
					// int tmNum = 0;
					for (int i = 0; i < wgztms.length; i++) {
						TiMu ztm = null;
						if (i < count) {
							ztm = ztmOlds.get(0);
							ztmOlds.remove(0);
						} else {
							ztm = new TiMu();
						}
						// 如果子题目存在则保存，如果为空或空字符串则不予处理
						if (StringUtils.isNotEmpty(wgztms[i])) {
							ztm.setSstk(null);
							ztm.setEnabled(true);
							ztm.setSjtm(tiMu);
							ztm.setTmmc(wgztms[i]);
							ztm.setPx(i + 1);
							ztms.add(ztm);
						}
					}
					for (Iterator<TiMu> iterator = ztmOlds.iterator(); iterator.hasNext();) {
						TiMu tm = iterator.next();
						tm.setEnabled(false);
						ztms.add(tm);
					}
				}
			}
		}
		String xxnr = tiMu.getXxnr();
		Set<Tmxx> saveTmxxList = new HashSet<Tmxx>();
		tiMu.setTmxxs(saveTmxxList);
		// 处理选项内容
		if (StringUtils.isNotEmpty(xxnr)) {
			// 以换行符拆分选项内容
			String[] tmxxs = xxnr.split("\n");
			// 查询所有本题库的旧选项
			OqlBuilder<Tmxx> query = OqlBuilder.from(Tmxx.class, "tmxx");
			query.where("tmxx.sstm.id=:tmid", tiMu.getId());
			query.orderBy("tmxx.px");
			List<Tmxx> tmxxList = entityDao.search(query);
			int count = tmxxList == null ? 0 : tmxxList.size();
			int tmNum = 0;
			for (int i = 0; i < tmxxs.length; i++) {
				Tmxx tmxx = null;
				if (i < count) {
					// 获取第一个对象
					tmxx = tmxxList.get(0);
					// 从list中移除第一个对象
					tmxxList.remove(0);
				} else {
					tmxx = new Tmxx();
				}
				if (StringUtils.isNotEmpty(tmxxs[i])) {
					// 设置题目选项
					tmxx.setSstm(tiMu);
					tmxx.setXxnr(tmxxs[i]);
					tmxx.setPx(CommonUtils.getIdentString(tmNum));
					tmNum += 1;
					// 把需要保存的题目选项添加到一个集合中
					saveTmxxList.add(tmxx);
				}
			}
			try {
				// 删除多余选项
				entityDao.remove(tmxxList);
			} catch (Exception e) {
				throw new RuntimeException("已使用的选项不可删除");
			}
		}
		if(sfcz(tiMu.getSstk().getId(),tiMu.getTmmc(), tiMu.getTmfl(),tiMu.getTmlx().getId(), tiMu.getId())){
			throw new RuntimeException("该题目已存在");
		}
		entityDao.saveOrUpdate(tiMu);
		// 保存选项
		entityDao.saveOrUpdate(saveTmxxList);
		// 保存子题目
		entityDao.saveOrUpdate(ztms);
		HttpSession session=ServletActionContext.getRequest().getSession();
		session.setAttribute("TMID", tiMu.getId());
		return msg;
	}
	
	private Boolean sfcz(Long tiku,String timumc,DictTree tmfl,Long timulx, Long id){
		OqlBuilder<TiMu> oql=OqlBuilder.from(TiMu.class, "timu");
		oql.where("timu.sstk.id=:tiku",tiku);
		oql.where("timu.tmmc=:tmmc",timumc);
		if(tmfl != null){
			oql.where("timu.tmfl.id=:timufl", tmfl.getId());
		}
		oql.where("timu.tmlx.id=:timulx",timulx);
		if(id != null){
			oql.where("timu.id != :timuid", id);
		}
		List<TiMu> ls=entityDao.search(oql);
		return ls.size()>0?true:false;
		
	}
}
