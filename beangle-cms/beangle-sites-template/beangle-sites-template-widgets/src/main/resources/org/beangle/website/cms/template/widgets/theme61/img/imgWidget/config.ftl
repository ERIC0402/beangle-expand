<table width="100%" class="list_box1">    
    <tr>
        <td class="list_box1_title"><label>切换时间</label><font color="red">*</font>：</td>
        <td><input name="config.time" type="text" value="${config.time!10}" maxlength=30 /></td>
    </tr>
    <tr>
        <td class="list_box1_title" width="5%" style="text-align:center">排序</td>
        <td class="list_box1_title" width="95%" style="text-align:center">内容</td>
    </tr>
    <#list 1..5 as v>    
    <tr>
        <td>
            <input size="4" maxlength="1" type="text" name="config.order${v}" value="${config['order' + v]!v}"/>
        </td>
        <td>
            <input type="hidden" name="config.img${v}" value="${config['img' + v]!'css/front/theme61/style_01/banner_pic.jpg'}"/>
            <img id="configImg${v}" width="400" src="${base}/${config['img' + v]!}" <#if !config['img' + v]??>style='display:none'</#if>/>
                 <div style="margin:3px 0;">
                <input id="file_upload${v}" name="file_upload${v}" type="file" />
            </div>
            <div>标题：<input type="text" name="config.title${v}" value="${config['title' + v]!}" maxlength="255" size="100"/></div>
            <div>链接：<input type="text" name="config.address${v}" value="${config['address' + v]!}" maxlength="255" size="100"/></div>
        </td>
    </tr>
    </#list>
</table>

<script type="text/javascript">    
    //    <#list 1..5 as v>
    $(function (){
        uploadimg({
            dir:"img/template/widget/${1}",
            ofile:"${config['img' + v]!}",
            upBtn:"file_upload${v}",
            imgNmae:"config.img${v}",
            showImg:"configImg${v}",
            width:960
        });
    });
    //    </#list>
</script>
