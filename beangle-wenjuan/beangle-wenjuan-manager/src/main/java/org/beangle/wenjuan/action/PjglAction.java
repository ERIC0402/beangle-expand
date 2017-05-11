package org.beangle.wenjuan.action;

import org.beangle.wenjuan.model.WenJuanPj;
import org.beangle.wenjuan.utils.DictDataWJUtil;

/**
 * 题库管理类
 * @author GZW
 *
 */
public class PjglAction extends ZuJuanAction {
	
	@Override
	protected String getWjfl() {
		return DictDataWJUtil.WEN_JUAN_FL_PJ;
	}

	@Override
	protected String getName() {
		return WenJuanPj.class.getName();
	}
	
}
