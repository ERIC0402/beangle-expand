package org.beangle.wenjuan.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.beangle.ems.security.User;
import org.beangle.wenjuan.model.WenJuan;
import org.beangle.wenjuan.model.Wjjg;
import org.beangle.wenjuan.model.Wjjgtm;
import org.beangle.wenjuan.model.Wjtm;

public interface WjjgService {

	/**
	 * 根据唯一标识、问卷、用户、获得问卷结果
	 * @param wybs
	 * @param wenJuan
	 * @param currentUser
	 * @return
	 */
	public Wjjg getWjjg(String wybs, WenJuan wenJuan, User currentUser);

	/**
	 * 根据用户ID、问卷ID和唯一标识查找问卷结果
	 * @param wybs
	 * @return
	 */
	public Wjjg getWjjgByUserAndWjAndWybs(Long userId, Long wjId, String wybs);
	
	/**
	 * 根据用户ID查找问卷结果
	 * @param userId
	 * @return
	 */
	public List<?> findWjjgsByUser(Long userId);

	/**
	 * 保存答题结果
	 * @param request
	 * @param wjjg
	 */
	public void putDa(HttpServletRequest request, Wjjg wjjg);

	/**
	 * 计算分数
	 * @param wj
	 */
	public void calScore(Wjjg wjjg);
	
	/**
	 * 根据唯一标识查找问卷结果
	 * @param wybs 唯一标示
	 * @return
	 */
	public List<Wjjg> findWjjgsByWybs(String wybs);
	
	
	public Wjjgtm getWjjgtm(Wjjg wjjg, Wjtm tm);
}
