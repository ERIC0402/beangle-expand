package org.beangle.wenjuan.model;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.pojo.LongIdObject;

/**
 * 问卷结果选项
 * 
 * @author Donhko
 * 
 */
@Entity(name = "org.beangle.wenjuan.model.Wjjgtm")
public class Wjjgtm extends LongIdObject {

	/**
	 * 问卷结果 
	 */
	private Wjjg wjjg;

	/**
	 * 题目
	 */
	private TiMu tm;

	/**
	 * 所有选项分数（如100,80）注：如果选项最大分大于题目分数则使用百分制、否则为实际分数
	 */
	@Size(max = 300)
	@Column(length = 300)
	private String xxScore;

	/**
	 * 题目分数
	 */
	private Float tmScore;

	/**
	 * 选择选项
	 */
	@ManyToMany
	private Set<Tmxx> tmxxs = CollectUtils.newHashSet();

	/**
	 * 答案
	 */
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(columnDefinition = "CLOB")
	private String nr;

	/**
	 * 分数
	 */
	private Float score;

	/**
	 * 上级题目
	 */
	private Wjjgtm sjtm;

	/**
	 * 包含子题目
	 */
	@OneToMany(mappedBy = "sjtm", cascade = CascadeType.ALL)
	private Set<Wjjgtm> ztms = CollectUtils.newHashSet();
	/**
	 * 人工阅卷分数
	 */
	private Float rgyjfs;
	
	public Float getRgyjfs() {
		return rgyjfs;
	}

	public void setRgyjfs(Float rgyjfs) {
		this.rgyjfs = rgyjfs;
	}

	public Wjjg getWjjg() {
		return wjjg;
	}

	public void setWjjg(Wjjg wjjg) {
		this.wjjg = wjjg;
	}

	public TiMu getTm() {
		return tm;
	}

	public void setTm(TiMu tm) {
		this.tm = tm;
	}

	public Set<Tmxx> getTmxxs() {
		return tmxxs;
	}

	public void setTmxxs(Set<Tmxx> tmxxs) {
		this.tmxxs = tmxxs;
	}

	public String getNr() {
		return nr;
	}

	public void setNr(String nr) {
		this.nr = nr;
	}

	public Float getScore() {
		if(score == null){
			score = 0F;
		}
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public String getXxScore() {
		return xxScore;
	}

	public void setXxScore(String xxScore) {
		this.xxScore = xxScore;
	}

	public Float getTmScore() {
		return tmScore;
	}

	public void setTmScore(Float tmScore) {
		this.tmScore = tmScore;
	}

	public Float getXxScore(String xx) {
		if (xx != null) {
			xx = xx.trim();
		}
		if (StringUtils.isEmpty(xx) || StringUtils.isEmpty(xxScore)
				|| xx.length() != 1) {
			return 0F;
		}
		xx = xx.toUpperCase();
		char a = 'A';
		Integer index = xx.charAt(0) - a;
		String[] ss = xxScore.split("，");
		if (index < 0 || index >= ss.length + 1) {
			return 0F;
		}
		float max = 0;
		for (int i = 0; i < ss.length; i++) {
			String str = StringUtils.isNotEmpty(ss[i]) ? ss[i] : "0";
			if (max < Float.valueOf(str)) {
				max = Float.valueOf(str);
			}
		}
		try {
			Float score = Float.valueOf(ss[index]);
			if (tmScore != null) {
				if (max > tmScore) {
					score = score * tmScore / 100;
				}
			}
			return score;
		} catch (Exception e) {
		}
		return 0F;
	}

	public Wjjgtm getSjtm() {
		return sjtm;
	}

	public void setSjtm(Wjjgtm sjtm) {
		this.sjtm = sjtm;
	}

	public Set<Wjjgtm> getZtms() {
		return ztms;
	}

	public void setZtms(Set<Wjjgtm> ztms) {
		this.ztms = ztms;
	}

}
