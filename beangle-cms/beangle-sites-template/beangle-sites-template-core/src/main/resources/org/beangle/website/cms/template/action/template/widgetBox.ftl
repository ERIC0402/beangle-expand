<#import "widget.ftl" as w/>
<link href="${base}/static/site-template/js/colorbox/colorbox.css" rel="stylesheet" type="text/css" /> 
<script type="text/javascript" src="${base}/static/site-template/js/colorbox/jquery.colorbox.js"></script>
<script type="text/javascript">
    $(function (){
        //组件Tab
        $('#widgetBoxTabs').tabs();
        //组件缩略图放大
        //$("a[rel='imgBig']").colorbox();
        //筛选组件
        $("#widgetTypeSelect").change(function (){
            var typeId = this.value;
            if(typeId == ""){//显示全部
                $("#widgetsDiv div[typeId]").slideDown("fast");
            }else{//显示某个分类
                $("#widgetsDiv div[typeId='"+typeId+"']").slideDown("fast");
                $("#widgetsDiv div[typeId!='"+typeId+"']").slideUp("fast");
            }
        });
        //组件拖拽
        $(".widgetBoxTabs .widget").draggable({ 
            appendTo: 'body' ,
            connectToSortable: '.layout,#layoutRoot',
            zIndex: 9999,
            cursor: "move",
            opacity: 0.7,
            revert: "invalid",
            helper: "clone" ,
            cursorAt: { left: -20 , top: 50}
        });
    })
</script>

<div>
    <div id="widgetBoxTabs" class="widgetBoxTabs">
        <ul>
            <li><a href="#widgetBoxTabs1">全部</a></li>
<!--            <li><a href="#widgetBoxTabs2">已使用</a></li>-->
        </ul>
        <div id="widgetBoxTabs1" class="widgetBoxTabs1">
            <p>
                <select id="widgetTypeSelect">
                    <option value="">全部</option>
                    <#list types as v>
                    <option value="${v.id}">${v.name}</option>
                    </#list>
                </select>
            </p>
            <div id="widgetsDiv">
                <#list widgets as v>
                <div typeId="${v.type.id}" class="widget" data='{"widgetId":${v.id}}'>
                    <@w.img widget=v width=160/>
                    <p>${v.name!}</p>
                </div>
                </#list>
            </div>
        </div>

<!--        <div id="widgetBoxTabs2" class="widgetBoxTabs1">
            <include "uniteWidgetConfig.ftl"/>
        </div>-->
    </div>
</div>