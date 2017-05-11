package org.beangle.website.cms.action;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.cms.model.Annex;
import org.beangle.website.common.util.ImageSizer;
import org.beangle.website.common.util.ReturnValue;
/**
 * 附件管理
 * @author GOKU
 *
 */
public class AnnexAction extends CommonAction {
	
	@Override
	protected String getEntityName() {
		return Annex.class.getName();
	}
	
	@Override
	protected void indexSetting() {
		put("selectId",get("selectId"));
		put("time",System.currentTimeMillis());
	}
	
	@Override
	protected QueryBuilder<Annex> getQueryBuilder() {
		put("selectId",get("selectId"));
		
		if(StringUtils.isNotEmpty(get("selectId"))){
			put("isSelect",1);
		}
		OqlBuilder<Annex> oql = OqlBuilder.from(Annex.class,"annex");
		populateConditions(oql);
		if(!isAllSitesAdmin()){
			oql.where("annex.user.id=:userId",getUserId());
		}
		oql.orderBy(getOrderString("annex.time desc"));
		oql.limit(getPageLimit()); 
		return oql;
	}
	
	@Override
	protected void editSetting(Entity<?> entity) {
		Annex annex = (Annex) entity;
		put("annex",annex);
		put("currentDate",new Date());
	}
	
	@Override
	protected String saveAndForward(Entity<?> entity) {
		Annex annex = (Annex) entity;
		annex.setTime(new Date());
		annex.setUser(getCurrentUser());
		String oldFileName = get("oldFileName");
		String tempFileName = annex.getFilePath();
		annex.setFilePath(moveAndRemoveAnnex(tempFileName, oldFileName));
		entityDao.saveOrUpdate(annex);
		return redirect("search","info.save.success");
	}
	
	public String remove(){
		Long[] annexIds = getEntityIds();
		boolean hasError = false;  //删除时是否有异常
		boolean hasSuccess = false; //删除时是否成功删除至少一条记录
		for(int i = 0;i < annexIds.length;i++){
			Annex annex = entityDao.get(Annex.class, annexIds[i]);
			try {
				entityDao.remove(annex);
				hasSuccess = true;
				//删除文件
				delete(annex.getFilePath());
			} catch (Exception e) {
				hasError = true;
			}
		}
		if(hasError && hasSuccess){
			return redirect("search","删除成功，但已被使用的附件不做处理！");
		}
		if(hasError){
			return redirect("search","已被使用的附件不可删除！");
		}
		return redirect("search","删除成功！");
	}
	
	/**
	 * 保存文件
	 * @return
	 */
	protected ReturnValue saveFile() {
		ReturnValue value = new ReturnValue();
        String pagePara = "fileData";
        java.io.File[] files = (java.io.File[]) this.getParams().get(pagePara);
        String[] fileNames = (String[]) this.getParams().get(pagePara + "FileName");
        if (files == null || fileNames == null) {
            return value;
        }
        
        //是否压缩图片，如果为false可支持非图片格式保存
        Boolean zipImg = getBool("zipImg");
        if (zipImg != null && zipImg) {
            Integer width = getInteger("width");
            if (width == null) {
                width = 600;
            }
            ImageSizer.saveImg(files[0].getAbsolutePath(), files[0].getAbsolutePath(), width, 800);
        }
        //保存文件
        String folder = get("folder");
        if (StringUtils.isEmpty(folder)) {
            folder = "files/";
        }
        if(folder.startsWith("/")){
        	folder = folder.substring(1);
        }
        if(folder.startsWith("website/cms/")){
        	folder = folder.substring(12);
        }
        if(!folder.endsWith("/")){
        	folder = folder + "/";
        }
        
        StringBuilder sb = new StringBuilder( TEMP_DIR +folder);
        sb.append(UUID.randomUUID().toString());
        sb.append(fileNames[0].substring(fileNames[0].lastIndexOf(".")).toLowerCase());
        
        File newFile = new File(getBaseDir() + sb.toString());
        if (!newFile.getParentFile().exists()) {
            newFile.getParentFile().mkdirs();
        }
        files[0].renameTo(newFile);
        Annex annex = new Annex();
        annex.setFileName(fileNames[0]);
        annex.setFileSize(newFile.length());
        annex.setOpen(false);
        annex.setTime(new Date());
        annex.setUser(getCurrentUser());
        annex.setFilePath(moveAndRemoveAnnex(sb.toString(), ""));
        entityDao.saveOrUpdate(annex);
        
        value.setFileName(annex.getFileName());
        value.setId(annex.getId());
		return value;
	}

	/**
	 * 上传图片返回值
	 * @throws IOException
	 */
    public void uploadContentFile() throws IOException {
    	ReturnValue value = saveFile();
        JSONObject data = new JSONObject();
        data.put("id", value.getId());
        data.put("name", value.getFileName());
        writeResponseMessage(data.toString());
    }
    
}
