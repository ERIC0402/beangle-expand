package org.beangle.wenjuan.importer;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.beangle.ems.security.User;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.TransferResult;
import org.beangle.website.common.util.CommonUtils;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.model.DictTree;
import org.beangle.website.system.service.DictDataService;
import org.beangle.website.system.service.DictTreeService;
import org.beangle.wenjuan.model.TiKu;
import org.beangle.wenjuan.model.TiMu;
import org.beangle.wenjuan.model.Tmxx;
import org.beangle.wenjuan.service.TiKuService;
import org.beangle.wenjuan.utils.DictTreeWJUtil;
import org.beangle.wenjuan.utils.DictTypeWJUtil;
import org.codehaus.plexus.util.StringUtils;

/**
 * 题库导入管理
 * 
 * @author GZW
 * 
 */
public class TiMuImportListener extends WenJuanImportListener {

	// 题库
	private Map<String, TiKu> tkmap;
	// 题目分类
	private Map<String, DictTree> tmflmap;
	// 题目类型
	private Map<String, DictData> tmlxmap;

	public TiMuImportListener(EntityDao entityDao, TiKuService tiKuService,
			DictTreeService dictTreeService, DictDataService dictDataService, User tjr) {
		super();
		this.entityDao = entityDao;
		this.tiKuService = tiKuService;
		this.dictTreeService = dictTreeService;
		this.dictDataService = dictDataService;
		this.tjr = tjr;
		// 查询所有题库
		tkmap = tiKuService.getTiKuMap();
		// 查询题目分类字典树
		tmflmap = dictTreeService.getDictTreeMap(DictTreeWJUtil.TMFL_CODE);
		// 查询问卷题目类型字典数据
		tmlxmap = dictDataService
				.getDictDataMapByCode(DictTypeWJUtil.WJ_TI_MU_LX_CODE);
	}

	public void onItemFinish(TransferResult tr) {
		TiMu tiMu = (TiMu) importer.getCurrent();
		try {
			//查找题库
			TiKu tiKu = tkmap.get(tiMu.getSstk().getTkmc());
			//查找题目分类
			DictTree tmfl = tmflmap.get(tiMu.getTmfl().getName());
			//查找题目类型
			DictData tmlx = tmlxmap.get(tiMu.getTmlx().getName());
			String tmmc = tiMu.getTmmc();
			// 如果题库不存在则添加错误信息，提示“题库不存在，需先创建题库”
			if (tiKu == null || tiKu.getId() == null) {
				// 添加出错信息
				tr.addFailure(tiMu.getSstk().getTkmc(), "题库不存在，请先创建题库");
			} else if (tmfl == null) {
				// 添加出错信息
				tr.addFailure(tiMu.getTmfl().getName(), "题目分类不存在");
			} else if (tmlx == null) {
				// 添加出错信息
				tr.addFailure(tiMu.getTmfl().getName(), "题目类型不存在");
			} else if(StringUtils.isEmpty(tmmc) || tmmc.length()>100){
				// 添加出错信息
				tr.addFailure(tmmc, "题目名称为空或长度超过100个字符");
			} else {
				Integer tmsl = tiKu.getTmsl();
				if (tmsl == null) {
					tmsl = 0;
				}
				// 题库题目数量+1
				
				TiMu tm=sfcz(tiMu.getSstk().getTkmc(), tiMu.getTmmc(), tiMu.getTmfl().getName(), tiMu.getTmlx().getName());
				if(tm==null){
					tm=new TiMu();
					tm.setCreatedAt(new Date());
					count++;
					tiKu.setTmsl(tmsl + 1);
				}else{
					ucount++;
					
				}
				tm.setUpdatedAt(new Date());
				tm.setTjr(tjr);
				// 设置所属题库
				tm.setSstk(tiKu);
				// 设置题目分类
				tm.setTmfl(tmfl);
				// 设置题目类型
				tm.setTmlx(tmlx);
				tm.setTmmc(tiMu.getTmmc());
				// 题目数量
				
				// 设置题目排序
				tm.setPx(tmsl + 1);
				// 设置题目为有效
				tm.setEnabled(true);
				// 先保存题目
				entityDao.saveOrUpdate(tm);
				
				
				// 保存题库
				entityDao.saveOrUpdate(tiKu);
				// 获取题目选项的string
				String xxString = tiMu.getTmxx();
				// 保存题目选项
				saveTmxxs(xxString, tm);
				ServletActionContext.getRequest().setAttribute("count", count);
				ServletActionContext.getRequest().setAttribute("ucount", ucount);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 添加出错信息
			tr.addFailure(tiMu.getTmmc(), e.getMessage());
		}
	}

	/**
	 * 保存题目选项
	 * 
	 * @param xxString
	 * @param tiMu
	 */
	protected void saveTmxxs(String xxString, TiMu tiMu) {
		// 判断xxString是否为空，如果为空不做任何操作
		if (StringUtils.isNotEmpty(xxString)) {
			// 用“；”拆分xxString字符串
			String[] tmxxStr = xxString.split("；");
			// 循环拆分后的题目选项
			for (int i = 0; i < tmxxStr.length; i++) {
				// 用“：”查分一个选项可获得“A”和具体选项内容
				String[] tmxxs = tmxxStr[i].split("：");
				// 判断拆分后的数组是否为空和长度是否大于等于2，否则不进行草错
				if (tmxxs != null && tmxxs.length >= 2) {
					// 新建题目选项
					Tmxx tmxx = new Tmxx();
					// 设置排序
					tmxx.setPx(CommonUtils.getIdentString(i));
					// 设置所属题目
					tmxx.setSstm(tiMu);
					// 设置选项内容
					tmxx.setXxnr(tmxxs[1]);
					// 保存选项
					entityDao.saveOrUpdate(tmxx);
				}
			}

		}
	}
	/**
	 * 根据题库，题目名称，题目类型，题目分类
	 * 判断是否存在该题目
	 */
	public TiMu sfcz(String tikumc,String timumc,String timuflmc,String timulxmc){
		OqlBuilder<TiMu> oql=OqlBuilder.from(TiMu.class, "timu");
		oql.where("timu.sstk.tkmc=:tkmc",tikumc);
		oql.where("timu.tmmc=:tmmc",timumc);
		oql.where("timu.tmfl.name=:timufl",timuflmc);
		oql.where("timu.tmlx.name=:timulx",timulxmc);
		List<TiMu> ls=entityDao.search(oql);
		return ls.size()>0?ls.get(0):null;
		
	}
}
