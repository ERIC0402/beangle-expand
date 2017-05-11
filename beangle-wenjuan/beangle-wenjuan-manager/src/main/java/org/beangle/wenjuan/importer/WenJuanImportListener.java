package org.beangle.wenjuan.importer;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.beangle.ems.security.User;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.service.DictDataService;
import org.beangle.website.system.service.DictTreeService;
import org.beangle.wenjuan.service.TiKuService;

/**
 * 问卷导入超类
 * @author GZW
 *
 */
public class WenJuanImportListener extends ItemImporterListener {

	/**
	 * 字典接口
	 */
	protected DictDataService dictDataService;

	/**
	 * 字典树接口
	 */
	protected DictTreeService dictTreeService;

	/**
	 * 题库接口
	 */
	protected TiKuService tiKuService;

	/**
	 * 服务接口
	 */
	protected EntityDao entityDao;

	protected HttpSession session;

	// 新增数量
	protected int count = 0;

	// 更新数量
	protected int ucount = 0;
	
	protected User tjr;

	public int getUcount() {
		return ucount;
	}

	public void setUcount(int ucount) {
		this.ucount = ucount;
	}

	public HttpSession getSession() {
		return session;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public void setDictDataService(DictDataService dictDataService) {
		this.dictDataService = dictDataService;
	}

	public void setDictTreeService(DictTreeService dictTreeService) {
		this.dictTreeService = dictTreeService;
	}

	public void setTiKuService(TiKuService tiKuService) {
		this.tiKuService = tiKuService;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	public User getTjr() {
		return tjr;
	}

	public void setTjr(User tjr) {
		this.tjr = tjr;
	}

	protected DictData getDictData(Map<String, DictData> map, Long typeid,
			DictData ndd) {
		if (ndd == null || StringUtils.isEmpty(ndd.getName())) {
			return null;
		}
		DictData dd = map.get(ndd.getName());
		if (dd == null) {
			// if (typeid == null) {
			// return null;
			// }
			// dd = dictDataService.saveDictData(typeid, ndd.getName());
			// dd.setEnabled(true);
			// entityDao.saveOrUpdate(dd);
			// map.put(dd.getName(), dd);
		}
		return dd;
	}
	
}
