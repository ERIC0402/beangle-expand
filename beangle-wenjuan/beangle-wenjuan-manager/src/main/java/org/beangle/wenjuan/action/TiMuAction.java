package org.beangle.wenjuan.action;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts2.ServletActionContext;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.exception.MyException;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.User;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.TransferResult;
import org.beangle.model.transfer.importer.EntityImporter;
import org.beangle.website.system.model.DictTree;
import org.beangle.website.system.service.DictTreeService;
import org.beangle.wenjuan.importer.TiMuImportListener;
import org.beangle.wenjuan.model.TiKu;
import org.beangle.wenjuan.model.TiMu;
import org.beangle.wenjuan.model.Tmxx;
import org.beangle.wenjuan.service.TiKuService;
import org.beangle.wenjuan.service.WbtmService;
import org.beangle.wenjuan.service.WenJuanService;
import org.beangle.wenjuan.utils.DictDataWJUtil;
import org.beangle.wenjuan.utils.DictTreeWJUtil;
import org.beangle.wenjuan.utils.DictTypeWJUtil;

/**
 * 题目管理
 * 
 * @author GZW
 * 
 */
public class TiMuAction extends WenJuanCommonAction {

	private TiKuService tiKuService;

	private DictTreeService dictTreeService;

	private WbtmService wbtmService;
	
	private WenJuanService wenJuanService;
	
	public void setWenJuanService(WenJuanService wenJuanService) {
		this.wenJuanService = wenJuanService;
	}

	public void setWbtmService(WbtmService wbtmService) {
		this.wbtmService = wbtmService;
	}

	public void setDictTreeService(DictTreeService dictTreeService) {
		this.dictTreeService = dictTreeService;
	}

	public void setTiKuService(TiKuService tiKuService) {
		this.tiKuService = tiKuService;
	}

	@Override
	protected String getEntityName() {
		return TiMu.class.getName();
	}

	@Override
	protected void indexSetting() {
		Long tiKuId = getLong("tiKu.id");
		put("tiKuId", tiKuId);
		// 传递数据
		putDatas();
		super.indexSetting();
	}

	/**
	 * 传递数据
	 */
	protected void putDatas() {
		// 传递题库集合
		put("tiKuList", tiKuService.findYQXTiKu(isAdmin(), getUserGroups(getCurrentUser()), getUserId()));
		// 传递题目类型集合
		put("tmlxs", findDictData(DictTypeWJUtil.WJ_TI_MU_LX_CODE));
		// 传递题目难易程度
		put("tmnds", findDictData(DictTypeWJUtil.WJ_TI_MU_NYCD));
		// 传递题目适用范围
		put("syfws", findDictData(DictTypeWJUtil.WJ_TI_MU_SYFW));
		// 传递题目分类集合
		put("tmfls", findChildDictTrees(DictTreeWJUtil.TMFL_CODE));
	}

	@Override
	public String search() {
		Long tiKuId = getLong("tiKuId");
		put("tiKuId", tiKuId);
		put("userId",getUserId());
		OqlBuilder<?> idquery = OqlBuilder.from(TiMu.class, getShortName());
		// 判断是否是管理员，如果是管理员则不判断角色权限，否侧进行角色判断
		if (!isAdmin()) {
			Set<Group> groups = getUserGroups(getCurrentUser());
			if (groups != null && groups.size() > 0) {
				idquery.join("left outer", getShortName() + ".sstk.groups", "g");
				idquery.join("left outer", getShortName() + ".sstk.users", "u");
				idquery.where("(g in (:gs) or tiMu.sstk.createUser.id = :userid or u.id=:userid)", groups, getUserId());
			}
		}
//		if (tiKuId != null) {
//			idquery.where(getShortName() + ".sstk.id=:tiKuId", tiKuId);
//		}
		populateConditions(idquery);
		idquery.where(getShortName() + ".sstk is not null");
		idquery.where(getShortName() + ".sjtm is null");
		idquery.orderBy(getOrderString(getShortName() + ".sstk.id"));
		idquery.limit(getPageLimit());
		idquery.select("select " + getShortName() + ".id");
		List list = entityDao.search(idquery);
		
		OqlBuilder<TiMu> query = OqlBuilder.from(TiMu.class);
		if (CollectionUtils.isNotEmpty(list)){
			query.where(getShortName() + ".id in (:ids)", list);
		}else{
			query.where("1!=1");
		}
		List list2 = entityDao.search(query);
		
		list.clear();
		list.addAll(list2);
		
		put(getShortName() + "s", list);
		return forward();
	}

	@Override
	protected void editSetting(Entity<?> entity) {
		putDatas();
		TiMu tm = (TiMu)entity;
		if(tm == null || tm.getId() == null){
		HttpSession session=ServletActionContext.getRequest().getSession();
		Object obj=session.getAttribute("TMID");
		if(obj != null ){
			tm = entityDao.get(TiMu.class, Long.parseLong(obj.toString()));
			if(tm==null){
				tm.setId(null);
				tm.setTmmc(null);
				tm.setTmnr(null);
				tm.setZqda(null);
				tm.setStjx(null);
				tm.setTmxx(null);
				Set<Tmxx> tmxxs = CollectUtils.newHashSet();
				tm.setTmxxs(tmxxs);
		
			}
			
		}
		}
		put(getShortName(),tm);
		// 选项模板
		put("xxmbs", wenJuanService.findXxmb());
		// 选项排列方式
		put("xxplfses", getDictDataByDictType(DictTypeWJUtil.WJ_XXPLFS));
		// 上级题目
		put("sjtms", findJdlxTM());

		put("currentDate", new Date());

		super.editSetting(entity);
	}

	@Override
	protected String saveAndForward(Entity<?> entity) {
		TiMu tiMu=(TiMu)entity;
		tiMu.setTjr(getCurrentUser());
		if(tiMu.getId() == null){
			tiMu.setCreatedAt(new Date());
		}
		tiMu.setUpdatedAt(new Date());
		try {
			String msg = wenJuanService.saveTimu(tiMu, this);
		} catch (Exception e) {
			editSetting(entity);
			return forward("edit", e.getMessage());
		}
		
		return redirect("search", "保存成功");
	}
	/**
	 * 根据题库，题目名称，题目类型，题目分类
	 * 判断是否存在该题目
	 * @param id 
	 */
	public Boolean sfcz(Long tiku,String timumc,Long timufl,Long timulx, Long id){
		OqlBuilder<TiMu> oql=OqlBuilder.from(TiMu.class, "timu");
		oql.where("timu.sstk.id=:tiku",tiku);
		oql.where("timu.tmmc=:tmmc",timumc);
		oql.where("timu.tmfl.id=:timufl",timufl);
		oql.where("timu.tmlx.id=:timulx",timulx);
		if(id != null){
			oql.where("timu.id != :timuid", id);
		}
		List<TiMu> ls=entityDao.search(oql);
		return ls.size()>0?true:false;
		
	}

	/*
	public String saveTimu(Entity<?> entity) {
		TiMu tm = (TiMu) entity;
		// 删除就文件、保存新文件
		String oldFileName = get("oldTMPIC");
		if(StringUtils.isEmpty(oldFileName)){
			oldFileName = get("oldImg");
		}
		String tempFileName = tm.getPic();
		tm.setPic(moveAndRemoveAnnex(tempFileName, oldFileName));

		entityDao.saveOrUpdate(tm);
		//更新题库题目数量
		if(tm.getSstk() != null){
			wenJuanService.updateTikuTimuNum(tm.getSstk().getId());
		}
		String msg = "";
		String tmlx = tm.getTmlx().getCode();
		// 如果题目类型为“普通节点”则不保存选项
		if (StringUtils.isNotEmpty(tmlx) && !(DictDataWJUtil.WJ_TI_MU_LX_JI_DIAN.equals(tmlx))) {
			// 如果题目类型为网格题，则需要保存子题目
			Set<TiMu> ztms = CollectUtils.newHashSet();
			if (DictDataWJUtil.WJ_TI_MU_LX_WANG_GE.equals(tmlx)) {
				String wgztm = get("wgztmnr");
				if (StringUtils.isNotEmpty(wgztm)) {
					String[] wgztms = wgztm.split("\n");
					// 查询旧的子题目
					OqlBuilder<TiMu> query = OqlBuilder.from(TiMu.class, "t");
					query.where("t.sjtm.id=:sjtmId", tm.getId());
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
							ztm.setSjtm(tm);
							ztm.setTmmc(wgztms[i]);
							ztm.setPx(i + 1);
							ztms.add(ztm);
						}
					}
					for (Iterator<TiMu> iterator = ztmOlds.iterator(); iterator.hasNext();) {
						TiMu tiMu = iterator.next();
						tiMu.setEnabled(false);
						ztms.add(tiMu);
					}
				}
			}
			// 保存子题目
			entityDao.saveOrUpdate(ztms);
		}
		String xxnr = get("xxnr");
		List<Tmxx> saveTmxxList = new ArrayList<Tmxx>();
		// 处理选项内容
		if (StringUtils.isNotEmpty(xxnr)) {
			// 以换行符拆分选项内容
			String[] tmxxs = xxnr.split("\n");
			// 查询所有本题库的旧选项
			OqlBuilder<Tmxx> query = OqlBuilder.from(Tmxx.class, "tmxx");
			query.where("tmxx.sstm.id=:tmid", tm.getId());
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
					tmxx.setSstm(tm);
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
				msg += "，已使用的选项不可删除";
				e.printStackTrace();
			}
		}
		// 保存选项
		entityDao.saveOrUpdate(saveTmxxList);
		return msg;
	}
	*/

	/**
	 * 查找节点类型的题目
	 * 
	 * @return
	 */
	protected List<TiMu> findJdlxTM() {
		OqlBuilder<TiMu> query = OqlBuilder.from(TiMu.class, getShortName());
		// 判断题目类型为节点类型
		query.where("(" + getShortName() + ".tmlx.code='" + DictDataWJUtil.WJ_TI_MU_LX_JI_DIAN + "' or " + getShortName() + ".tmlx.code='" + DictDataWJUtil.WJ_TI_MU_LX_WANG_GE + "')");
		// 按照题库、题目排序字段排序
		query.orderBy(getShortName() + ".sstk.id");
		query.orderBy(getShortName() + ".px");
		return entityDao.search(query);
	}

	/**
	 * 转发到导入页面(importForm.ftl)
	 */
	public String importForm() {
		return forward("importForm");
	}

	/**
	 * 配置导入器
	 */
	protected void configImporter(EntityImporter importer) {
		HttpSession session = ServletActionContext.getRequest().getSession();
		// 创建导入器
		TiMuImportListener l = new TiMuImportListener(entityDao, tiKuService, dictTreeService, dictDataService,getCurrentUser());
		l.setSession(session);
		// 把新建的导入器添加到importer中
		importer.addListener(l);
	}

	/**
	 * 导入完成方法
	 */
	public String importData() {
		TransferResult tr = new TransferResult();
		EntityImporter importer = buildEntityImporter(TiMu.class);
		if (null == importer) {
			return redirect("importForm", "请上传一个正确的XLS文件!");
		}
		configImporter(importer);
		try {
			importer.transfer(tr);
		} catch (RuntimeException e) {
			// 模板错误
			tr.addFailure("<font style='color:red;font-size:14px'>您使用的模版不正确!</font>", "");
			e.printStackTrace();
		} catch (Exception e) {
			// 数据错误
			e.printStackTrace();
		}
		put("importResult", tr);
		if (tr.hasErrors()) {
			return forward("result");
		} else {
			int count = Integer.parseInt(ServletActionContext.getRequest().getAttribute("count") == null ? "0" : ServletActionContext.getRequest().getAttribute("count").toString());
			int ucount = Integer.parseInt(ServletActionContext.getRequest().getAttribute("ucount") == null ? "0" : ServletActionContext.getRequest().getAttribute("ucount").toString());
			put("count", count);
			put("ucount", ucount);
			return forward("result");
		}
	}

	@Override
	protected String removeAndForward(Collection<?> entities) {
		// 有删除成功的记录
		boolean hasSuccess = false;
		// 有删除失败的记录
		boolean hasFailure = false;
		@SuppressWarnings("unchecked")
		List<TiMu> tms = (List<TiMu>) entities;
//		Set<TiKu> tks = CollectUtils.newHashSet();
		for (Iterator<TiMu> iterator = tms.iterator(); iterator.hasNext();) {
			TiMu tiMu = iterator.next();
//			tks.add(tiMu.getSstk());
			try {
				entityDao.remove(tiMu);
				hasSuccess = true;
			} catch (Exception e) {
				hasFailure = true;
				e.printStackTrace();
			}
		}
		// 重新计算题库题目数量
		wenJuanService.updateTikuTimuNum();
		// 如果有删除成功的记录则判断是否有删除失败的记录，如果有表示两种情况同时存在则提示“删除成功，但已被使用的题目未作处理”，否则没有删除失败的记录则提示“删除成功”
		if (hasSuccess) {
			if (hasFailure) {
				return redirect("search", "删除成功，但已被使用的题目未作处理");
			} else {
				return redirect("search", "删除成功");
			}
		} else {// 没有删除成功的记录表示全部删除失败，则提示，“删除失败，已被使用的题目不可删除”
			return redirect("search", "删除失败，已被使用的题目不可删除");
		}
	}

	public String wbtj() {
		Long tiKuId = getLong("tiMu.sstk.id");
		put("tiKuId", tiKuId);
		// 传递题库集合
		put("tikus", tiKuService.findYQXTiKu(isAdmin(), getUserGroups(getCurrentUser()), getUserId()));
		// 传递题目分类集合
		put("tmfls", findChildDictTrees(DictTreeWJUtil.TMFL_CODE));
		return forward();
	}

	public String saveWbtj() {
		String textInput = get("textInput");
		List<TiMu> tms = wbtmService.getTiMus(textInput);
		if (!tms.isEmpty()) {
			try {
				TiKu tiKu = new TiKu(getLong("sstk.id"));
				DictTree tmfl = new DictTree(getLong("tmfl.id"));
				if (tiKu.getId() == null || tmfl.getId() == null) {
					throw new MyException("题库或题目分类不能为空！");
				}
				for (TiMu tm : tms) {
					if (tm.getSjtm() == null) {
						tm.setTjr(getCurrentUser());
						tm.setCreatedAt(new Date());
						tm.setUpdatedAt(new Date());
						tm.setSstk(tiKu);
						tm.setTmfl(tmfl);
					}
				}
				entityDao.saveOrUpdate(tms);
				tiKuService.updateTmsl();
			} catch (Exception e) {
				put("textInput", textInput);
				return forward("wbtj", e.getMessage());
			}
		}
		return redirect("search", "info.save.success");
	}
	
	public String refreshDepartment(){
		OqlBuilder<TiMu> query = OqlBuilder.from(TiMu.class,"tm");
		query.where("tm.tjr is null");
		List<TiMu> tms = entityDao.search(query);
		for (TiMu tiMu : tms) {
			TiKu tk = tiMu.getSstk();
			if(tk != null && CollectionUtils.isNotEmpty(tk.getUsers())){
				tiMu.setTjr(tk.getUsers().iterator().next());
				tiMu.setCreatedAt(new Date());
				tiMu.setUpdatedAt(new Date());
			}
		}
		entityDao.saveOrUpdate(tms);
		return redirect("search","设置成功");
	}
}
