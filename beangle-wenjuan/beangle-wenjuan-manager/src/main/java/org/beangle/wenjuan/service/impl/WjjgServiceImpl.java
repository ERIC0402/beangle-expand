package org.beangle.wenjuan.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.beangle.ems.security.User;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.wenjuan.model.TiMu;
import org.beangle.wenjuan.model.Tmxx;
import org.beangle.wenjuan.model.WenJuan;
import org.beangle.wenjuan.model.Wjjg;
import org.beangle.wenjuan.model.Wjjgtm;
import org.beangle.wenjuan.model.Wjsz;
import org.beangle.wenjuan.model.Wjtm;
import org.beangle.wenjuan.service.WenJuanService;
import org.beangle.wenjuan.service.WjjgService;
import org.beangle.wenjuan.utils.DictDataWJUtil;

public class WjjgServiceImpl extends BaseServiceImpl implements WjjgService {
	
	private WenJuanService wenJuanService;
	
	public void setWenJuanService(WenJuanService wenJuanService) {
		this.wenJuanService = wenJuanService;
	}

	/**
	 * 根据唯一标识、问卷、用户、获得问卷结果
	 * @param wybs
	 * @param wenJuan
	 * @param currentUser
	 * @return
	 */
	public Wjjg getWjjg(String wybs, WenJuan wenJuan, User user) {
		OqlBuilder<Wjjg> query = OqlBuilder.from(Wjjg.class, "wjjg");
		query.where("wybs = :wybs", wybs);
		query.where("wj = :wj", wenJuan);
		query.where("user = :user", user);
		List<Wjjg> wjjgs = entityDao.search(query);
		Wjjg wjjg = null;
		if(wjjgs.isEmpty()){
			wjjg = new Wjjg();
			wjjg.setWybs(wybs);
			wjjg.setWj(wenJuan);
			wjjg.setUser(user);
			wjjg.setTotalScore(wenJuan.getWjzf());
			wjjg.setStartTime(new Date());
			List<Wjtm> tiMus = wenJuanService.getWjtm(wenJuan);
			List<Wjjgtm> wjjgtms = new ArrayList<Wjjgtm>();
			List<Wjjgtm> wjjgztms = new ArrayList<Wjjgtm>();
			for(Wjsz wjsz : wenJuan.getWjszs()){
				for(TiMu tm : wjsz.getTiMus()){
					Wjjgtm wjjgtm = getWjjgtm(wjjg, tm, wjsz);
					wjjgtms.add(wjjgtm);
				}
			}
			for(Wjtm tm : tiMus){
				Wjjgtm wjjgtm = getWjjgtm(wjjg, tm);
				wjjgtms.add(wjjgtm);
				wjjgztms.addAll(wjjgtm.getZtms());
			}
			//模拟考试没有唯一标识，不需要保存
			if(wybs != null){
				entityDao.saveOrUpdate(wjjg);
				if(!wjjgtms.isEmpty()){
					entityDao.saveOrUpdate(wjjgtms);
				}
				if(!wjjgztms.isEmpty()){
					//可以通过级联自动保存
					//entityDao.saveOrUpdate(wjjgztms);
				}
			}
			wjjg.getWjjgtms().addAll(wjjgtms);
		}else{
			wjjg = wjjgs.get(0);
		}
		return wjjg;
	}

	public Wjjgtm getWjjgtm(Wjjg wjjg, Wjtm tm) {
		return getWjjgtm(wjjg, tm, null, tm.getSstm());
	}

	private Wjjgtm getWjjgtm(Wjjg wjjg, TiMu tm, Wjsz wjsz) {
		return getWjjgtm(wjjg, null, wjsz, tm);
	}

	/**
	 * 从问卷题目中复制
	 * @param wjjg
	 * @param tm
	 * @param wjsz
	 * @return
	 */
	private Wjjgtm getWjjgtm(Wjjg wjjg, Wjtm wjtm, Wjsz wjsz, TiMu tm) {
		Wjjgtm wjjgtm = new Wjjgtm();
		wjjgtm.setWjjg(wjjg);
		wjjgtm.setTm(tm);
		if(tm.getTmlx() != null && DictDataWJUtil.WJ_TI_MU_LX_WANG_GE.equals(tm.getTmlx().getCode())){
			for(TiMu ztm : tm.getWgztms()){
				Wjjgtm wjjgztm = getWjjgtm(wjjg, wjtm, wjsz, ztm);
				wjjgztm.setSjtm(wjjgtm);
				wjjgtm.getZtms().add(wjjgztm);
			}
		}
		if(wjsz != null){
			wjjgtm.setTmScore(wjsz.getMtfs());
		}else{
			wjjgtm.setTmScore(wjtm.getScore());
			wjjgtm.setXxScore(wjtm.getXxScore());
		}
		return wjjgtm;
	}
	
	public Wjjg getWjjgByUserAndWjAndWybs(Long userId, Long wjId, String wybs) {
		List<Wjjg> wjjgs = findWjjgsByUserAndWjAndWybs(userId, wjId, wybs,null,false);
		if(wjjgs != null && wjjgs.size() > 0){
			return wjjgs.get(0);
		}
		return null;
	}

	public List<?> findWjjgsByUser(Long userId) {
		return findWjjgsByUserAndWjAndWybs(userId, null, null, "wjjg.wybs",true);
	}
	
	protected List<Wjjg> findWjjgsByUserAndWjAndWybs(Long userId, Long wjId, String wybs,String select, boolean addScore) {
		OqlBuilder<Wjjg> query = OqlBuilder.from(Wjjg.class,"wjjg");
		if(userId != null){
			query.where("wjjg.user.id=:userId",userId);
		}
		if(wjId != null){
			query.where("wjjg.wj.id=:wjId",wjId);
		}
		if(StringUtils.isNotEmpty(wybs)){
			query.where("wjjg.wybs=:wybs",wybs);
		}
		if(StringUtils.isNotEmpty(select)){
			query.select(select);
		}
		if(addScore){
			query.join("wjjg.wjjgtms", "wjjgtm");
			query.where("wjjgtm.score is not null");
		}
		return entityDao.search(query);
	}

	public void putDa(HttpServletRequest request, Wjjg wjjg) {
		wjjg.setEndTime(new Date());
		wjjg.setIpAddr(request.getRemoteAddr());
		for (Wjjgtm tm : wjjg.getWjjgtms()) {
			String tmlx = "";
			if(tm.getSjtm()!=null){
				 tmlx = "";
			}else{
				tmlx=tm.getTm().getTmlx().getCode();
			}
			String name = "xx" + tm.getTm().getId();
			String value  = request.getParameter(name);
			if (DictDataWJUtil.WJ_TI_MU_LX_PAN_DUAN.equals(tmlx)
					|| DictDataWJUtil.WJ_TI_MU_LX_DAN_XUAN.equals(tmlx)
					|| DictDataWJUtil.WJ_TI_MU_LX_DUO_XUAN.equals(tmlx)) {
				String[] tks = request.getParameterValues(name);
				if(tks != null){
					tm.getTmxxs().clear();
					for(String xxid : tks){
						try {
							Long id = Long.parseLong(xxid);
							tm.getTmxxs().add(new Tmxx(id));
						} catch (Exception e) {
						}
					}
				}
			}else if (DictDataWJUtil.WJ_TI_MU_LX_TIAN_KON.equals(tmlx)){
				String[] tks = request.getParameterValues(name);
				if(tks != null){
					String tkda = "";
					for(String s : tks){
						if(tkda != ""){
							tkda += TiMu.TIAN_KON_DA_AN_SPLIT;
						}
						tkda += s;
					}
					tm.setNr(tkda);
				}
			}else{
				tm.setNr(value);
			}
		}
	}

	public void calScore(Wjjg wjjg) {
		Float score = 0F;
		for(Wjjgtm tm : wjjg.getWjjgtms()){
			calScore(tm);
			score += tm.getScore();
		}
		wjjg.setScore(score);
		entityDao.saveOrUpdate(wjjg);
	}

	private void calScore(Wjjgtm tm) {
		Float score = 0F;
		String tmlx="";
		if(tm.getSjtm()!=null){
			tmlx="";
		}else{
			tmlx = tm.getTm().getTmlx().getCode();
		}
		if (DictDataWJUtil.WJ_TI_MU_LX_PAN_DUAN.equals(tmlx)
				|| DictDataWJUtil.WJ_TI_MU_LX_DAN_XUAN.equals(tmlx)
				|| DictDataWJUtil.WJ_TI_MU_LX_DUO_XUAN.equals(tmlx)) {
			List<Tmxx> tmxxs = new ArrayList<Tmxx>(tm.getTmxxs());
			for(int i = 0; i < tmxxs.size(); i++){
				Tmxx xx = tmxxs.get(i);
				if(StringUtils.isEmpty(xx.getPx())){
					xx = entityDao.get(Tmxx.class, xx.getId());
				}
				tmxxs.set(i, xx);
			}
			Collections.sort(tmxxs);
			StringBuilder sb = new StringBuilder();
			try {
				for(Tmxx xx : tmxxs){
					String px = xx.getPx().replaceAll("\\s", "");;
					sb.append(px);
				}
				String zqda = tm.getTm().getZqda().replaceAll("\\s*[　]*", "");
				if(sb.toString().equals(zqda)){
					score = tm.getTmScore();
				}
				if(tm.getRgyjfs()!=null){
					score=tm.getRgyjfs();
				}
			} catch (Exception e) {
			}
		}else if (DictDataWJUtil.WJ_TI_MU_LX_WANG_GE.equals(tmlx)){
			if(!tm.getTmxxs().isEmpty()){
				String px = tm.getTmxxs().iterator().next().getPx();
				
					score = tm.getXxScore(px);
					
			}
			if(tm.getRgyjfs()!=null){
				score=tm.getRgyjfs();
				}
		}else if (DictDataWJUtil.WJ_TI_MU_LX_TIAN_KON.equals(tmlx)){
			if(StringUtils.isNotEmpty(tm.getNr()) && StringUtils.isNotEmpty(tm.getTm().getZqda())){
				String[] nrs = tm.getNr().split(TiMu.TIAN_KON_DA_AN_SPLIT);
				String[] zqdas = tm.getTm().getZqda().split(TiMu.TIAN_KON_DA_AN_SPLIT);
				for(int i = 0; i < zqdas.length; i++){
					if(nrs.length < i+1){
						break;
					}
					if(zqdas[i].equals(nrs[i])){
						score += tm.getTmScore() / zqdas.length;
					}
					
				}
				if(tm.getRgyjfs()!=null){
					score=tm.getRgyjfs();
					}
			}
		}else{
			if(StringUtils.equals(tm.getNr(), tm.getTm().getZqda())){
				score = tm.getTmScore();
			}
			if(tm.getRgyjfs()!=null){
				score=tm.getRgyjfs();
				}
		}
		if(score == null){
			score = 0F;
		}
		tm.setScore(score);
	}

	public List<Wjjg> findWjjgsByWybs(String wybs) {
		return findWjjgsByUserAndWjAndWybs(null, null, wybs, null,false);
	}
}
