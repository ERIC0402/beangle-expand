<table width="100%" class="list_box1">
	    <tr>
        <td class="list_box1_title"><label>是否显示搜索</label>：</td>
        <td><input name="config.search" type="checkbox" <#if config.search??>checked</#if>/></td>
    </tr>
    <tr>
        <td class="list_box1_title" name="configTd"><label>图片</label>：</td>
        <td>
            <input type="hidden" name="config.img" value="${config.img!'css/front/theme21/style_01/banner_pic.jpg'}"/>
            <img id="configImg" width="400" src="${base}/${config.img!}" <#if !config.img??>style='display:none'</#if>/>
                 <div style="margin:3px 0;">
                <input id="file_upload" name="file_upload" type="file" />
            </div>
        </td>
    </tr>
    <tr>
        <td class="list_box1_title"><label>更新其它页面相同组件</label>：</td>
        <td><input name="updateSameWidget" type="checkbox" checked/></td>
    </tr>
</table>

<script type="text/javascript">    
    $(function (){
        uploadimg({
            dir:"img/sites/img",
            ofile:"${config.img!}",
            upBtn:"file_upload",
            imgNmae:"config.img",
            showImg:"configImg",
            width:960
        });
    });
</script>
