package org.beangle.wenjuan.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.wenjuan.utils.DictDataWJUtil;
import org.codehaus.plexus.util.StringUtils;

/**
 * 题目
 * @author Donhko
 *
 */
@Entity(name="org.beangle.wenjuan.model.TiMu")
public class TiMu extends TmObject{
	
	public static final String TIAN_KON_DA_AN_SPLIT = "，";
	
	/**
	 * 所属题库
	 */
	private TiKu sstk;

	/**
	 * 上级题目
	 */
	private TiMu sjtm;
	
	/**
	 * 题目选项（仅导入用）
	 */
	@Transient
	private String tmxx;
	
	/**
	 * 题目选项（仅编辑方法用）
	 */
	@Transient
	private String xxnr;
	
	/**
	 *网格子题目（仅编辑方法用）
	 */
	@Transient
	private String wgztm;

	/**
	 * 试题解析
	 */
	@Column(length = 1500)
	private String stjx;
	
	/**
	 * 包含所有选项
	 */
	@OneToMany(mappedBy = "sstm", cascade = CascadeType.ALL)
	private Set<Tmxx> tmxxs = CollectUtils.newHashSet();
	
	/**
	 * 包含所有子题目
	 */
	@OneToMany(mappedBy = "sjtm", cascade = CascadeType.ALL)
	private Set<TiMu> wgztms = CollectUtils.newHashSet();
	/**
	 * 标记此题目是否是公有。  ‘1’ 私有   null or ‘0’ 公有
	 */
	private Boolean isqj;
	
	public Boolean getIsqj() {
		return isqj;
	}

	public void setIsqj(Boolean isqj) {
		this.isqj = isqj;
	}

	public TiMu() {
		super();
	}

	public TiMu(Long id) {
		super(id);
	}

	public TiKu getSstk() {
		return sstk;
	}

	public void setSstk(TiKu sstk) {
		this.sstk = sstk;
	}

	public TiMu getSjtm() {
		return sjtm;
	}

	public void setSjtm(TiMu sjtm) {
		this.sjtm = sjtm;
	}

	public Set<Tmxx> getTmxxs() {
		return tmxxs;
	}

	public void setTmxxs(Set<Tmxx> tmxxs) {
		this.tmxxs = tmxxs;
	}

	public String getTmxx() {
		return tmxx;
	}

	public void setTmxx(String tmxx) {
		this.tmxx = tmxx;
	}

	public String getXxnr() {
		if(StringUtils.isNotEmpty(xxnr)){
			return xxnr;
		}
		xxnr = "";
		List<Tmxx> tmxxList = new ArrayList<Tmxx>(tmxxs);
		Collections.sort(tmxxList,new Comparator<Tmxx>(){
			public int compare(Tmxx o1, Tmxx o2) {
				if(StringUtils.isEmpty(o1.getPx())){
					return 1;
				}
				return o1.getPx().compareTo(o2.getPx());
			}
			
		});
		for (Iterator<Tmxx> iterator = tmxxList.iterator(); iterator.hasNext();) {
			Tmxx tmxx = iterator.next();
			xxnr += tmxx.getXxnr() + "\n";
		}
		if(StringUtils.isNotEmpty(xxnr)){
			if(xxnr.endsWith("\n")){
				xxnr = xxnr.substring(0,xxnr.lastIndexOf("\n"));
			}
		}
		return xxnr;
	}

	public void setXxnr(String xxnr) {
		this.xxnr = xxnr;
	}

	public Set<TiMu> getWgztms() {
		return wgztms;
	}

	public void setWgztms(Set<TiMu> wgztms) {
		this.wgztms = wgztms;
	}

	public String getWgztm() {
		if(StringUtils.isNotEmpty(wgztm)){
			return wgztm;
		}
		wgztm = "";
		List<TiMu> tmList = new ArrayList<TiMu>(wgztms);
		Collections.sort(tmList,new Comparator<TiMu>(){
			public int compare(TiMu o1, TiMu o2) {
				if(o1.getPx() > o2.getPx()){
					return 1;
				}else{
					return -1;
				}
			}
			
		});
		for (Iterator<TiMu> iterator = tmList.iterator(); iterator.hasNext();) {
			TiMu tm = iterator.next();
			if(tm.isEnabled()){
				wgztm += tm.getTmmc() + "\n";
			}
		}
		if(StringUtils.isNotEmpty(wgztm)){
			if(wgztm.endsWith("\n")){
				wgztm = wgztm.substring(0,wgztm.lastIndexOf("\n"));
			}
		}
		return wgztm;
	}

	public void setWgztm(String wgztm) {
		this.wgztm = wgztm;
	}
	
	public String getStjx() {
		return stjx;
	}

	public void setStjx(String stjx) {
		this.stjx = stjx;
	}

	/**
	 * 题目是否包含选项
	 * @return
	 */
	public boolean getHasxx(){
		if(getTmlx() == null){
			return false;
		}
		String[] xxlxs = {DictDataWJUtil.WJ_TI_MU_LX_DAN_XUAN, DictDataWJUtil.WJ_TI_MU_LX_DUO_XUAN, DictDataWJUtil.WJ_TI_MU_LX_PAN_DUAN, DictDataWJUtil.WJ_TI_MU_LX_PAI_XU, DictDataWJUtil.WJ_TI_MU_LX_WANG_GE};
		for(String s : xxlxs){
			if(s.equals(getTmlx().getCode())){
				return true;
			}
		}
		return false;
	}
	
	public String getTmOption(){
		String option = "";
		List<Tmxx> tmxxList = new ArrayList<Tmxx>(tmxxs);
		Collections.sort(tmxxList,new Comparator<Tmxx>(){
			public int compare(Tmxx o1, Tmxx o2) {
				if(StringUtils.isEmpty(o1.getPx())){
					return 1;
				}
				return o1.getPx().compareTo(o2.getPx());
			}
			
		});
		
		for (int i=0;i<tmxxList.size();i++) {
			Tmxx tmxx = tmxxList.get(i);
			int icon = 65 + i;
			char optionIcon = (char) icon;
			option += optionIcon + "：" + tmxx.getXxnr() + "。";
		}
		return option;
	}
}
