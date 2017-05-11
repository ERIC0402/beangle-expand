[#ftl]
[@b.head/]
[@b.toolbar title="题目导入"]bar.addBack();[/@]
[@b.form name="tiMuImportForm" action="!importData" id="tiMuImportForm" theme="list" enctype="multipart/form-data"]
<table>
		<tr>
      <td>文件目录:<input type="file" id="importFile" name="importFile" label="文件目录"/>
 
      [@b.submit value="提交" onsubmit="validateExtendName"/]
      
      </td>
    </tr>
     <tr>
       <td> 
          <font size="2"> 请选择.xls文件进行上传&nbsp;&nbsp;&nbsp;&nbsp;
          <a title="请点击下载" href="${base}/common/file!downFile.action?folder=static/wenjuan/data/timu.xls">【题目导入模板.xls】</a>
          <br>上传文件中的所有信息均要采用文本格式。对于日期和数字等信息也是一样。
          </font>
          <br>导入速度：约200行/分钟
       </td>
     </tr>
</table>
[/@]
<script>
  function validateExtendName(form){
  	var value=form.importFile.value;
  	var index=value.indexOf(".xls");
  	var index2=value.indexOf(".xlsx");
	if((index<1) ||(index<(value.length-4))){
		if((index2<(value.length-5))){
			alert("请选择扩展名为.xls的文件");
			return false;
		}
	}
	return true;
	
  }
   
</script>
[@b.foot/]