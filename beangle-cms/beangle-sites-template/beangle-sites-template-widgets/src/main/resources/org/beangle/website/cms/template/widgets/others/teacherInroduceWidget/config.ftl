<#include "/widgetComm.ftl"/>
<table width="100%" class="list_box1">
    <tr>
        <td class="list_box1_title"><label>标题</label>：</td>
        <td><input name="config.title" type="text" value="${config.title!}" maxlength=30 style="width:160px;"/></td>
    </tr>
    <tr>
        <td class="list_box1_title"><label>关联栏目</label>：</td>
        <td>
            <@f.columnSelect name="config.columnId" data=columns?sort_by('orders') value=((config.columnId!('0')))/>
        </td>
    </tr>
    <tr>
        <td class="list_box1_title" name="configTd"><label>显示教师</label>：</td>
        <td>
            <input type="hidden" name="config.ids" id="configIds" value='${config.ids!}'/>
            <select id="teachers" multiple style="min-width: 200px;" size="10">
                <#list teachers! as v>
                <option value="${v.id}">${v.xm!}(${v.gh!})</option>    
                </#list>
            </select>
            <div>
                <input type="button" value="选择教师" onclick="$fu.findTeachers('configIds', 'teachers');"/>
                <input type="button" value="编辑选中教师" onclick="$fu.editTeacher('teachers');"/>
                <input type="button" value="删除选中教师" onclick="removeTeachers()"/>
            </div>
        </td>
    </tr>
    <tr>
        <td class="list_box1_title" name="configTd"><label>显示属性</label>：</td>
        <td>
            <#macro checkbox title='' name=''>
    <li>
        <input class="no" type="text" name="config.fileds.${name}.no" maxlength="2" size="1" value="${config['fileds.' + name + '.no']!0}" readonly/>
        <input type="checkbox" name="config.fileds.${name}" id="config.fileds.${name}" <#if config['fileds.' + name]??>checked</#if>/>
               <label for="config.fileds.${name}">${title}</label>
    </li>
    </#macro>
    <ul id="sortable">
        <li class="notmove">
            <input class="no" type="text" maxlength="2" size="1" value="1" readonly/>
            <input type="checkbox" checked readonly disabled/>
            <label>姓名 技术职务</label>
        </li>
        <@checkbox title='性别' name='xb'/>
        <@checkbox title='单位' name='dw'/>
        <@checkbox title='出生日期' name='csrq'/>
        <@checkbox title='民族' name='mz'/>
        <@checkbox title='政治面貌' name='zzmm'/>
        <@checkbox title='来院年月' name='lyny'/>
        <@checkbox title='办公室地址' name='bgsdz'/>
        <@checkbox title='移动电话' name='yddh'/>
        <@checkbox title='电子邮箱' name='dzyx'/>
        <@checkbox title='研究方向' name='yjfx'/>
        <@checkbox title='荣誉称号' name='rych'/>
    </ul>
</td>
</tr>
</table>

<script type="text/javascript">    
    var a_fields = {
    };
    var v = new validator(a_fields);
    $(function (){
        $("#sortable" ).sortable({
            items: "li:not(.notmove)",
            update : function (){
                var i = 0;
                $("#sortable li").each(function (){
                    $(this).find(".no").val(++i);
                })
            }
        });
        sortTable();
    })
    
    function sortTable(){
        var list = $("#sortable li");
        for(var i = 0; i < list.length; i++){
            var list = $("#sortable li");            
            var li1 = $(list.get(0));
            var id1 = li1.find(".no").val() * 1;
            for(var j = 1; j < list.length - i; j++){
                var li2 = $(list.get(j));
                var id2 = li2.find(".no").val() * 1;
                if(id1 > id2){
                    li1.remove();
                    li2.after(li1);
                }else{
                    li1 = li2;
                    id1 = id2;
                }
            }
        }
    }
    
    function removeTeachers(){
        var select = document.getElementById("teachers");
        removeSelectedOption(select)
        $name("config.ids").val(getAllOptionValue(select));
    }
</script>
