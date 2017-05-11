package org.beangle.wenjuan.action;

import java.util.List;

import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.wenjuan.model.TiMu;
import org.beangle.wenjuan.service.TiKuService;
import org.beangle.wenjuan.utils.DictTreeWJUtil;
import org.beangle.wenjuan.utils.DictTypeWJUtil;

public class SelectAction extends WenJuanCommonAction {

	private TiKuService tiKuService;
	
	public void setTiKuService(TiKuService tiKuService) {
		this.tiKuService = tiKuService;
	}
	
	public String tiMu(){
		//传递题库集合
		put("tiKuList",tiKuService.findTiKu());
		//传递题目类型集合
		put("tmlxs",findDictData(DictTypeWJUtil.WJ_TI_MU_LX_CODE));
		//传递题目分类集合
		put("tmfls",findChildDictTrees(DictTreeWJUtil.TMFL_CODE));
		return forward();
	}
	public String tiMuSearch(){
		OqlBuilder<TiMu> query = OqlBuilder.from(TiMu.class, "tiMu");
		populateConditions(query);
		query.where("tiMu.sstk in (:tiKus)", tiKuService.findYQXTiKu(isAdmin(), getUserGroups(), getUserId()));
		query.where("sstk is not null");
		query.where("enabled = 1");
		query.where("tiMu.sjtm.id is null");
		query.limit(getPageLimit());
		List<TiMu> tiMus = entityDao.search(query);
		put("tiMus", tiMus);
		return forward();
	}

}
