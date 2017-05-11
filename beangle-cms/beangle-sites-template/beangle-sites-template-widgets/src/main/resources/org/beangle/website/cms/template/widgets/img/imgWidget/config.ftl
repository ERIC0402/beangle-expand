<input type="hidden" name="updateSameWidget" value="true"/>
<table width="100%" class="list_box1">
<#--    <tr>
        <td class="list_box1_title" name="configTd"><label>是否显示上方空行</label>：</td>
        <td>
            <input type="checkbox" name="config.topBlank" <#if config.topBlank??>checked</#if>/>
        </td>
    </tr>
<tr>-->  
        <td class="list_box1_title" name="configTd"><label>是否显示底线</label>：</td>
        <td>
            <input type="checkbox" name="config.bottomLine" <#if config.bottomLine??>checked</#if>/>
        </td>
    </tr>  
    <tr>
        <td class="list_box1_title" name="configTd"><label>图片切换时间（秒）</label>：</td>
        <td>
            <input size="64" maxlength="256" name="config.time" value="${config['time']!10}">
        </td>
    </tr>
    <#list 1..5 as v>
    <tr>
        <td class="list_box1_title" name="configTd"><label>图片${v}</label>：</td>
        <td>
            <input type="hidden" name="config.img${v}" value="${config['img' + v]!}"/>
            <img id="configImg${v}" width="400" src="${base}/${config['img' + v]!}" <#if !config['img' + v]?? || config['img' + v] == ''>style='display:none'</#if>/>
            <div style="margin:3px 0;">
                <input id="file_upload${v}" name="file_upload${v}" type="file" />
                <input type="button" value="删除" onclick="deleteImg('${v}')"/>
            </div>
        </td>
    </tr>
    <tr>
        <td class="list_box1_title" name="configTd"><label>链接${v}</label>：</td>
        <td>
            <input size="64" maxlength="256" name="config.url${v}" value="${config['url' + v]!}">
        </td>
    </tr>
    </#list>
</table>

<script type="text/javascript">    
    $(function (){
        //        <#list 1..5 as v>
        uploadimg({
            dir:"img/sites/img",
            ofile:"${config.img!}",
            upBtn:"file_upload${v}",
            imgNmae:"config.img${v}",
            showImg:"configImg${v}",
            width:960
        });
        //        </#list>
    });
    
    function deleteImg(id){
        $("#configImg" + id).hide();
        $name("config.img" + id).val("");
    }
</script>
