<#include "/widgetComm.ftl"/>
<table width="100%" class="list_box1">
    <tr>
        <td class="list_box1_title"><label>标题</label>：</td>
        <td><input name="config.title" type="text" value="${config.title!}" maxlength=30/></td>
    </tr>
    <tr>
        <td class="list_box1_title"><label>选择栏目</label>：</td>
        <td><@f.columnSelect name="config.columnId" data=columnList?sort_by('orders') value=((config.columnId!('0')))/></td>
    </tr>
    <tr>
        <td class="list_box1_title"><label>切换时间</label><font color="red">*</font>：</td>
        <td><input name="config.time" type="text" value="${config.time!10}" maxlength=30 /></td>
    </tr>
    <tr>
        <td class="list_box1_title" name="configTd"><label>显示教师</label>：</td>
        <td>
            <input type="hidden" name="config.ids" id="configIds" value='${config.ids!}'/>
            <select id="englishs" multiple style="min-width: 200px;" size="10">
                <#list englishs! as v>
                <option value="${v.id}">${v.name!}(${v.no!})</option>    
                </#list>
            </select>
            <div>
                <input type="button" value="选择教师" onclick="$fu.findEnglish('configIds', 'englishs');"/>
                <input type="button" value="删除选中教师" onclick="removeTeachers()"/>
            </div>
        </td>
    </tr>
    <tr>
        <td class="list_box1_title" name="configTd"><label>显示属性</label>：</td>
        <td>
            <#macro checkbox title='' name=''>
    <li>
        <input class="no" type="text" name="config.fileds.${name}.no" maxlength="2" size="1" value="${config['fileds.' + name + '.no']!2}" readonly/>
        <input type="checkbox" name="config.fileds.${name}" id="config.fileds.${name}" <#if config['fileds.' + name]??>checked</#if>/>
               <label for="config.fileds.${name}">${title}</label>
    </li>
    </#macro>
    <ul id="sortable">
        <li class="notmove">
            <input class="no" type="text" maxlength="2" size="1" value="1" readonly/>
            <input type="checkbox" checked readonly disabled/>
            <label>Name Title</label>
        </li>
        <@checkbox title='Faculty' name='faculty'/>
        <@checkbox title='Office' name='office'/>
        <@checkbox title='Office Phone' name='officePhone'/>
        <@checkbox title='Website' name='website'/>
        <@checkbox title='Email' name='email'/>
        <@checkbox title='Department' name='department'/>
        <@checkbox title='Research Field' name='researchField'/>
    </ul>
</td>
</tr>
</table>

<script type="text/javascript">    
    var a_fields = {
        "config.time":{f:"integer", r:true}
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
        var select = document.getElementById("englishs");
        removeSelectedOption(select)
        $name("config.ids").val(getAllOptionValue(select));
    }
</script>
