<table width="100%" class="list_box1">
	    
    <tr>
        <td class="list_box1_title" name="configTd"><label>图片</label>：</td>
        <td>
            <input type="hidden" name="config.img" value="${config.img!'css/front/theme61/style_01/banner_pic.jpg'}"/>
            <img id="configImg" width="400" src="${base}/${config.img!}" <#if !config.img??>style='display:none'</#if>/>
                 <div style="margin:3px 0;">
                <input id="file_upload" name="file_upload" type="file" />
            </div>
        </td>
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
