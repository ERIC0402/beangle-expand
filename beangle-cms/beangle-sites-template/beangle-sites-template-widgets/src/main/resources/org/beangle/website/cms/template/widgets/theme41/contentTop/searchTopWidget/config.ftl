  <#include "/widgetComm.ftl"/>
  <table width="100%" class="list_box1">
   <tr>
        <td class="list_box1_title"><label>是否显示搜索</label>：</td>
        <td><input name="config.search" type="checkbox" <#if config.search??>checked</#if>/></td>
    </tr>
    
    <tr>
        <td class="list_box1_title"><label>是站群搜索搜索</label>：</td>
        <td><input name="config.allSiteSearch" type="checkbox" <#if config.allSiteSearch??>checked</#if>/></td>
    </tr>
    
    <tr>
        <td class="list_box1_title"><label>链接的显示数量</label><font color="red">*</font>：</td>
        <td><input name="config.num" type="text" value="${config.num!}"/></td>
    </tr>
    
    </table> 
    
    <h3>链接设置</h3>
    <hr/>
    <table align="center">
    <tr>
  		<td>链接编号</td>  
  		<td>链接名称</td>
  		<td>链接地址</td>
    </tr>
    <tr>
    	<td>1</td>
	    <td><input name="config.uname1" type="text" value="${config.uname1!}"/></td>
	    <td><input name="config.url1" type="text" value="${config.url1!}"/></td>
    </tr>
    <tr>
		<td>2</td>    
	  	<td><input name="config.uname2" type="text" value="${config.uname2!}"/></td>
	    <td><input name="config.url2" type="text" value="${config.url2!}"/></td>
    </tr>
    
     <tr>
		<td>3</td>    
	   	<td><input name="config.uname3" type="text" value="${config.uname3!}"/></td>
	    <td><input name="config.url3" type="text" value="${config.url3!}"/></td>
    </tr>
    
     <tr>
		<td>4</td>    
	  	<td><input name="config.uname4" type="text" value="${config.uname4!}"/></td>
	    <td><input name="config.url4" type="text" value="${config.url4!}"/></td>
    </tr>
    
     <tr>
		<td>5</td>    
	 	<td><input name="config.uname5" type="text" value="${config.uname5!}"/></td>
	    <td><input name="config.url5" type="text" value="${config.url5!}"/></td>
    </tr>
   
    </table>

    
    
    <script type="text/javascript">
    var a_fields = {
       'config.num':{'l':'最多显示5个链接','f':'unsigned','r':true}
    };
    var v = new validator(a_fields);
</script>