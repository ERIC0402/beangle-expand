package org.beangle.wenjuan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.beangle.model.pojo.LongIdObject;


/**
 * 问卷题目（固定问卷、AB卷、评教问卷）
 * @author Donhko
 *
 */
@Entity(name="org.beangle.wenjuan.model.Wjtm")
public class Wjtm extends LongIdObject implements Comparable<Wjtm>{
	
	/**
	 * 所属问卷
	 */
	private WenJuan sswj;
	
	/**
	 * 所属题目
	 */
	private TiMu sstm;
	
	/**
	 * 排序
	 */
	private Integer px = 0;

	/**
	 * 题目分数
	 */
	private Float score;
	
	/**
	 * 所有选项分数（如100,80）注：如果选项最大分大于题目分数则使用百分制、否则为实际分数
	 */
	@Size(max=300)
	@Column(length=300)
	private String xxScore;
	/**
	 * 必答题
	 */
	private Boolean bdt = Boolean.FALSE;
	/**
	 * 排序选项
	 */
	private Boolean sortxx = Boolean.FALSE;
	
	private Tmxx gltmxx;

	public WenJuan getSswj() {
		return sswj;
	}

	public void setSswj(WenJuan sswj) {
		this.sswj = sswj;
	}

	public TiMu getSstm() {
		return sstm;
	}

	public void setSstm(TiMu sstm) {
		this.sstm = sstm;
	}

	public Integer getPx() {
		return px;
	}

	public void setPx(int px) {
		this.px = px;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public String getXxScore() {
		return xxScore;
	}

	public Float getXxScore(String xx) {
		if(xx != null){
			xx = xx.trim();
		}
		if(StringUtils.isEmpty(xx) || StringUtils.isEmpty(xxScore) || xx.length() != 1){
			return 0F;
		}
		xx = xx.toUpperCase();
		char a = 'A';
		Integer index = xx.charAt(0) - a;
		String[] ss = xxScore.split("，");
		if(index < 0 || index >= ss.length +1){
			return 0F;
		}
		try {
			Float score = Float.valueOf(ss[index]);
			if(this.score != null){
				float max = 0F;
				for(String s : ss){
					try {
						float f = Float.valueOf(s);
						if(f > max){
							max = f;
						}
					} catch (Exception e) {
					}
				}
				if(max > this.score && max > 0){
					score = score / max * this.score;
				}
			}
			return score;
		} catch (Exception e) {
		}
		return 0F;
	}

	public void setXxScore(String xxScore) {
		this.xxScore = xxScore;
	}

	public int compareTo(Wjtm o) {
		if(this.px == null){
			return -1;
		}
		return this.px.compareTo(o.px);
	}

	public Boolean getBdt() {
		if(bdt == null){
			bdt = false;
		}
		return bdt;
	}

	public void setBdt(Boolean bdt) {
		this.bdt = bdt;
	}

	public void setPx(Integer px) {
		this.px = px;
	}

	public Tmxx getGltmxx() {
		return gltmxx;
	}

	public void setGltmxx(Tmxx gltmxx) {
		this.gltmxx = gltmxx;
	}

	public Boolean getSortxx() {
		return sortxx;
	}

	public void setSortxx(Boolean sortxx) {
		this.sortxx = sortxx;
	}
	
}
