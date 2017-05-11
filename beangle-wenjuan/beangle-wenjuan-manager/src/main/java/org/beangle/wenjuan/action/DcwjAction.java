package org.beangle.wenjuan.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.beangle.model.Entity;
import org.beangle.wenjuan.model.TiMu;
import org.beangle.wenjuan.model.Tmxx;
import org.beangle.wenjuan.model.WenJuan;
import org.beangle.wenjuan.model.WenJuanDc;
import org.beangle.wenjuan.model.Wjtm;
import org.beangle.wenjuan.service.WbtmService;
import org.beangle.wenjuan.utils.DictDataWJUtil;
import org.beangle.wenjuan.utils.DictTypeWJUtil;


/**
 * 题库管理类
 * @author GZW
 *
 */
public class DcwjAction extends ZuJuanAction {
	
	private WbtmService wbtmService;
	
	public void setWbtmService(WbtmService wbtmService) {
		this.wbtmService = wbtmService;
	}
	
	@Override
	protected String getWjfl() {
		return DictDataWJUtil.WEN_JUAN_FL_DC;
	}

	@Override
	protected String getName() {
		return WenJuanDc.class.getName();
	}
	
//	@Override
//	protected String getShortName() {
//		return "wenJuanDc";
//	}
	
	@Override
	protected void editSetting(Entity<?> entity) {
		super.editSetting(entity);
		if(entity.getIdentifier() == null){
			WenJuanDc dc = (WenJuanDc)entity;
			if(StringUtils.isEmpty(dc.getWjmc())){
				dc.setWjmc("请输入问卷的标题");
			}
			entityDao.saveOrUpdate(entity);
		}
		wjtm();
		put("tmlxs", findDictData(DictTypeWJUtil.WJ_TI_MU_LX_CODE));
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
		return forward();
	}
	
	@Override
	protected String saveAndForward(Entity<?> entity) {
		return super.saveAndForward(entity);
	}
	
	public String editWjtm(){
		Wjtm wjtm = getEntity(Wjtm.class, "wjtm");
		wjtm.setSswj((WenJuan) getEntity());
		if(wjtm.getSstm() == null){
			wjtm.setSstm(new TiMu());
		}
		TiMu timu = wjtm.getSstm();
		if(StringUtils.isNotEmpty(timu.getTmnr())){
			//替换文本域换行
			timu.setTmnr(timu.getTmnr().replaceAll("<br/>", "\n"));
		}
		String code = get("code");
		if(StringUtils.isNotEmpty(code)){
			if(wjtm.getSstm() == null){
				wjtm.setSstm(new TiMu());
			}
			wjtm.getSstm().setTmlx(getDictData(code));
		}
		put("tmlxs", findDictData(DictTypeWJUtil.WJ_TI_MU_LX_CODE));
		put("wjtm", wjtm);
		put("timu", timu);
		Set<Wjtm> wjtms = wjtm.getSswj().getWjtms();
		List<TiMu> timus = new ArrayList<TiMu>(wjtms.size());
		for(Wjtm w : wjtms){
			if(w.getPx() < wjtm.getPx()){
				timus.add(w.getSstm());
			}
		}
		put("timus", timus);
		return forward();
	}
	
	public String wjtm(){
		List<Wjtm> wjtms = findWjtm();
		put("wjtms", wjtms);
		return forward();
	}

	private List<Wjtm> findWjtm() {
		WenJuan wenJuan = (WenJuan) getEntity();
		List<Wjtm> wjtms = new ArrayList<Wjtm>(wenJuan.getWjtms());
		Collections.sort(wjtms);
		return wjtms;
	}
	
	public String saveWjtm(){
		Wjtm wjtm = populateEntity(Wjtm.class, "wjtm");
		TiMu timu = populateEntity(TiMu.class, "timu");
		wjtm.setSswj(entityDao.get(WenJuan.class, wjtm.getSswj().getId()));
		if(wjtm.getId() == null){
			//添加默认排序
			wjtm.setPx(wjtm.getSswj().getWjtms().size() + 1);
		}
		if(StringUtils.isNotEmpty(timu.getTmnr())){
			//替换文本域换行
			timu.setTmnr(timu.getTmnr().replaceAll("\\n", "<br/>"));
		}
		String[] xxxhs = getAll("xxxh", String.class);
		if(xxxhs != null && xxxhs.length > 0){
			List<Tmxx> tmxxs = new ArrayList<Tmxx>(xxxhs.length);
			for(String xh : xxxhs){
				Tmxx xx = populateEntity(Tmxx.class, "xx" + xh);
				xx.setSstm(timu);
				tmxxs.add(xx);
			}
			for(Iterator<Tmxx> itor = timu.getTmxxs().iterator(); itor.hasNext();){
				Tmxx xx = itor.next();
				if(!tmxxs.contains(xx)){
					xx.setSstm(null);
				}
			}
			timu.getTmxxs().clear();
			timu.getTmxxs().addAll(tmxxs);
		}
		wjtm.setSstm(timu);
		entityDao.saveOrUpdate(timu);
		entityDao.saveOrUpdate(wjtm);
		return forward();
	}
	
	public void delWjtm(){
		Wjtm wjtm = entityDao.get(Wjtm.class, getLong("wjtm.id"));
		entityDao.remove(wjtm);
		resetPx();
	
	}
	
	public void resetPx(){
		List<Wjtm> wjtms = findWjtm();
		for(Wjtm wjtm : wjtms){
			Integer px = getInteger("px" + wjtm.getId());
			wjtm.setPx(px);
		}
		entityDao.saveOrUpdate(wjtms);
	}
}
