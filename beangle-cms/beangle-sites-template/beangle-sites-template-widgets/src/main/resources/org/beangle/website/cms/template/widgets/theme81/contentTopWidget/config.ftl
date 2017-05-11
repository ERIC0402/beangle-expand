<#include "/widgetComm.ftl"/>

<table width="100%" class="list_box1">
    <tr>
        <td class="list_box1_title"><label>标题</label>：</td>
        <td><input name="config.title" type="text" value="${config.title!}" maxlength=30 style="width:160px;"/></td>
    </tr>
    <tr>
        <td class="list_box1_title"><label>选择栏目</label>：</td>
        <td>
            <@f.columnSelect name="config.columnId" data=columnList?sort_by('orders') value=((config.columnId!('0')))/>
        </td>
    </tr>
    <tr>
        <td class="list_box1_title"><label>显示信息数量</label><font color="red">*</font>：</td>
        <td><input name="config.count" type="text" value="${config.count!3}" maxlength=2 style="width:160px;"/></td>
    </tr>
    <tr>
        <td class="list_box1_title" name="configTd"><label>显示图片</label>：</td>
        <td>
            <input type="hidden" name="config.img" value="${config.img!'css/front/theme81/style_comm/index3_07.jpg'}"/>
            <img id="configImg" width="200" src="${base}/${config.img!'css/front/theme1/style_01/top_bg.jpg'}" <#if !config.img??>style='display:none'</#if>/>
                 <div style="margin:3px 0;">
                <input id="file_upload" name="file_upload" type="file" />
            </div>
        </td>
    </tr>
</table>
<script type="text/javascript">
    var a_fields = {
        'config.count':{'l':'最多输入2位正整数','f':'unsigned'}
    };
    var v = new validator(a_fields);
    $(function (){
        selectToText($name("config.columnId")[0],  $name("config.title")[0], ")");
    });
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