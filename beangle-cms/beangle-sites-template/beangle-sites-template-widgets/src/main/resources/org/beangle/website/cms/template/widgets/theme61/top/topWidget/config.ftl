<input type="hidden" name="updateSameWidget" value="true"/>
<table width="100%" class="list_box1">
    <tr>
        <td class="list_box1_title"><label>是否显示搜索</label>：</td>
        <td><input name="config.search" type="checkbox" <#if config.search??>checked</#if>/></td>
    </tr>
    <tr>
        <td class="list_box1_title"><label>Contact US链接</label>：</td>
        <td><input name="config.link1" value="${config.link1!'#'}"/></td>
    </tr>
    <tr>
        <td class="list_box1_title"><label>中文版链接</label>：</td>
        <td><input name="config.link2" value="${config.link2!'#'}"/></td>
    </tr>
    <tr>
        <td class="list_box1_title" name="configTd"><label>网站Logo</label>：</td>
        <td>
            <input type="hidden" name="config.img" value="${config.img!'css/front/theme1/style_01/top_bg.jpg'}"/>
            <img id="configImg" width="400" src="${base}/${config.img!'css/front/theme1/style_01/top_bg.jpg'}" <#if !config.img??>style='display:none'</#if>/>
                 <div style="margin:3px 0;">
                <input id="file_upload" name="file_upload" type="file" />
            </div>
        </td>
    </tr>
</table>

<script type="text/javascript">    
    $(function (){
        uploadimg({
            dir:"img/sites/top",
            ofile:"${config.img!}",
            upBtn:"file_upload",
            imgNmae:"config.img",
            showImg:"configImg",
            width:1000
        });
    });
</script>
