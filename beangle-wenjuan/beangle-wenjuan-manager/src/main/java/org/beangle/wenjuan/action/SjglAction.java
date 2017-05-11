package org.beangle.wenjuan.action;

import org.beangle.commons.exception.MyException;
import org.beangle.commons.lang.StrUtils;
import org.beangle.model.Entity;
import org.beangle.struts2.convention.route.Action;
import org.beangle.wenjuan.model.WenJuan;
import org.beangle.wenjuan.model.WenJuanSj;
import org.beangle.wenjuan.model.Wjsz;
import org.beangle.wenjuan.model.Wjtm;
import org.beangle.wenjuan.utils.DictDataWJUtil;
import org.beangle.wenjuan.utils.DictTypeWJUtil;

/**
 * 题库管理类
 * @author GZW
 *
 */
public class SjglAction extends ZuJuanAction {
	

	@Override
	protected String getWjfl() {
		return DictDataWJUtil.WEN_JUAN_FL_SJ;
	}

	@Override
	protected String getName() {
		return WenJuanSj.class.getName();
	}

	@Override
	protected void editSetting(Entity<?> entity) {
		put("wjlxs", findDictData(DictTypeWJUtil.WEN_JUAN_LX_CODE));
		super.editSetting(entity);
	}
	
	public String wjsz(){
		WenJuan wenJuan = (WenJuan) getEntity();
		//如果问卷已发布，那么无法设置随机题目
		if(wenJuan.isEnabled()){
			return redirect("search", "已发布的问卷无法设置！");
		}
		wenJuanService.findSjtm(wenJuan);
		put("wenJuan", wenJuan);
		put("wjszs", wenJuan.getWjszs());
		put("tikus", tiKuService.findYQXTiKu(isAdmin(), getUserGroups(), getUserId()));
		put("tmlxs", findDictData(DictTypeWJUtil.WJ_TI_MU_LX_CODE));
		put("tmnds", findDictData(DictTypeWJUtil.WJ_TI_MU_NYCD));
		put("tmfls", findChildDictTrees("TMFL"));
		return forward();
	}
	public String gdwj(){
		WenJuan wenJuan = (WenJuan) getEntity();
		//如果问卷已发布，那么无法设置随机题目
		if(wenJuan.isEnabled()){
			return redirect("search", "已发布的问卷无法设置！");
		}else{
			return redirect(new Action(WjtmglAction.class,"index","wenJuan.id="+wenJuan.getId()),"");
		}
		
	}
	public String saveWjsz(){
		WenJuan wenJuan = (WenJuan) getEntity();
		wenJuan.getWjszs().clear();
		entityDao.executeUpdateHql("delete " + Wjsz.class.getName() + " where sswj.id = ?", wenJuan.getId());
		Long[] wjszs = StrUtils.splitToLong(get("wjsz"));
		for(Long id : wjszs){
			Wjsz wjsz = populate(Wjsz.class, "wjsz" + id);
			wenJuan.getWjszs().add(wjsz);
		}
		entityDao.saveOrUpdate(wenJuan);
		return redirect("search", "info.save.success");
	}
	
	@Override
	protected boolean faBuEnable(WenJuan wj) {
		Float score = 0F;
		for(Wjsz sz : wj.getWjszs()){
			score += sz.getMtfs() * sz.getTmsl();
		}
		for(Wjtm tm : wj.getWjtms()){
			if(tm.getScore() == null){
				throw new MyException("题目：" + tm.getSstm().getTmmc() + " 分数不能为空！");
			}
			score += tm.getScore();
		}
		if(score.intValue() != wj.getWjzf()){
			throw new MyException("题目总分与问卷总分不相等，分别为：" + score + " / " + wj.getWjzf());
		}
		return super.faBuEnable(wj);
	}
}
