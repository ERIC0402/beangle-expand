package org.beangle.xuesheng.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.exception.MyException;
import org.beangle.commons.property.PropertyConfig;
import org.beangle.model.Entity;
import org.beangle.website.common.action.BaseCommonAction;
import org.beangle.website.common.util.IPUtil;
import org.beangle.wenjuan.model.Ksap;
import org.beangle.wenjuan.model.KsapParent;
import org.beangle.wenjuan.model.Wjjg;
import org.beangle.wenjuan.model.Wjjgtm;
import org.beangle.wenjuan.model.Zxks;
import org.beangle.wenjuan.model.ZxksParent;
import org.beangle.wenjuan.service.WenJuanService;
import org.beangle.wenjuan.service.WjjgService;
import org.beangle.wenjuan.service.ZxksService;
import org.beangle.wenjuan.utils.DictDataWJUtil;

/**
 * 在线考试
 * 
 * @author CHII
 * 
 */
public class ZxksAction extends BaseCommonAction {

	public static final String WYBS = "ZXKS_";

	protected WenJuanService wenJuanService;
	protected WjjgService wjjgService;
	private ZxksService zxksService;

	public void setZxksService(ZxksService zxksService) {
		this.zxksService = zxksService;
	}

	public void setWenJuanService(WenJuanService wenJuanService) {
		this.wenJuanService = wenJuanService;
	}

	public void setWjjgService(WjjgService wjjgService) {
		this.wjjgService = wjjgService;
	}

	@Override
	protected String getEntityName() {
		return Zxks.class.getName();
	}

	protected Class<?> getKsapClass() {
		return Ksap.class;
	}

	protected Class<?> getZxksClass() {
		return Zxks.class;
	}

	@Override
	public String search() {
		put("ksaps", zxksService.findKsap(getKsapClass(), getZxksClass(), getUserId()));
		return forward();
	}

	@Override
	protected void editSetting(Entity<?> entity) {
		try {
			KsapParent ksap = getKsap();
			checkKsap(ksap);
			Wjjg wjjg = null;
			if (ksap.getType() != null && DictDataWJUtil.ZXKS_TYPE_MNKS.equals(ksap.getType().getCode())) {
				// 练习模式
				wjjg = wjjgService.getWjjg(null, ksap.getWenJuan(), getCurrentUser());
				put("lxms", true);
			} else {
				ZxksParent zxks = zxksService.createZxks(getZxksClass(), ksap, getCurrentUser());
				settingZxks(zxks, ksap);
				if (zxks.getFinished()) {
					throw new MyException("问卷已提交，不能再继续答卷。");
				}
				wjjg = wjjgService.getWjjg(getWybs(ksap.getId()), ksap.getWenJuan(), getCurrentUser());
				zxks.setWjjg(wjjg);
				entityDao.saveOrUpdate(zxks);
			}
			put("wjjg", wjjg);
			List<Wjjgtm> wjjgtms = new ArrayList<Wjjgtm>(wjjg.getWjjgtms());
			Collections.sort(wjjgtms, new Comparator<Wjjgtm>() {
				public int compare(Wjjgtm o1, Wjjgtm o2) {
					if(o1.getSjtm()!=null){
						return 1;
					}
					return o1.getTm().getTmlx().getId().compareTo(o2.getTm().getTmlx().getId());
				}
			});
			put("wjjgtms", wjjgtms);
			put("ksap", ksap);
			put("now", new Date());
			put("startTime", wjjg.getStartTime());
			Date endTime = ksap.getEndTime();
			if (ksap.getTime() != null) {
				endTime = new Date(wjjg.getStartTime().getTime() + ksap.getTime() * 60000);
				if (endTime.after(ksap.getEndTime())) {
					endTime = ksap.getEndTime();
				}
			}
			put("endTime", endTime);
			super.editSetting(entity);
		} catch (Exception e) {
			logger.error("在线考试答卷错误", e);
			put("errormsg", e.getMessage() == null ? "系统错误" : e.getMessage());
		}
	}

	protected void settingZxks(ZxksParent zxks, KsapParent ksap) {
	}

	@Override
	protected String saveAndForward(Entity<?> entity) {
		try {
			KsapParent ksap = getKsap();
			HttpServletRequest request = getRequest();
			checkKsap(ksap);
			Wjjg wjjg = wjjgService.getWjjg(getWybs(ksap.getId()), ksap.getWenJuan(), getCurrentUser());
			wjjgService.putDa(request, wjjg);
			ZxksParent zxks = zxksService.createZxks(getZxksClass(), ksap, getCurrentUser());
			PropertyConfig config = getSystemConfig();
			if (StringUtils.isNotEmpty(get("completedj"))) {
				zxks.setFinished(true);
				entityDao.saveOrUpdate(zxks);
				// 根据开关判断是否立即计算成绩
				if ("1".equals(config.get(PropertyConfig.KEY_ZXKS_LJJSCJ))) {
					wjjgService.calScore(wjjg);
				}
			}
			entityDao.saveOrUpdate(wjjg);
			if (StringUtils.isNotEmpty(get("completedj")) && "1".equals(config.get(PropertyConfig.KEY_ZXKS_LJJSCJ))) {
				return redirect("search", "答卷已提交");
			}
			if (StringUtils.isNotEmpty(get("savedj"))) {
				return redirect("search", "保存成功");
			}
			return null;
		} catch (MyException e) {
			logger.error("saveAndForwad failure", e);
			return redirect("search", e.getMessage());
		} catch (Exception e) {
			logger.error("saveAndForwad failure", e);
			return redirect("search", "info.save.failure");
		}
	}

	private KsapParent getKsap() {
		Long ksapid = getLong("ksap.id");
		if (ksapid == null) {
			return null;
		}
		KsapParent ksap = (KsapParent) entityDao.get(getKsapClass(), ksapid);
		return ksap;
	}

	public String result() {
		ZxksParent zxks = (ZxksParent) getEntity(getZxksClass(), "zxks");
		put("zxks", zxks);
		return forward();
	}

	private void checkKsap(KsapParent ksap) {
		HttpServletRequest request = getRequest();
		if (ksap == null || ksap.getId() == null) {
			throw new MyException("试卷不存在！");
		}
		Date now = new Date();
		if (now.before(ksap.getStartTime()) || now.after(new Date(ksap.getEndTime().getTime() + 5 * 60 * 1000))) {
			throw new MyException("不在考试时间范围之内！");
		}
		// 限制IP
		if (StringUtils.isNotEmpty(ksap.getIpLimit())) {
			if (!IPUtil.isIpValid(request.getRemoteAddr(), ksap.getIpLimit())) {
				throw new MyException("您没有权限访问该页面，IP地址无效！");
			}
		}
	}

	protected String getWybs(Long ksapid) {
		return WYBS + ksapid;
	}
}
