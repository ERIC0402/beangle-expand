<#include "/org/beangle/website/cms/template/template/simpleHead.ftl"/>
<#include "/org/beangle/website/cms/template/template/toolBar.ftl"/>
<#macro form title="" back=true title2="" backurl="">
<script language="JavaScript" type="text/javascript" src="${base}/comm/contentFileUpload.js"></script>
<script language="JavaScript" type="text/javascript" src="${base}/scripts/My97DatePicker/WdatePicker.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${base}/scripts/Validator2.js"></script>
<script type="text/javascript" src="${base}/scripts/beangle/form.js"></script>
<body>
    <#if title2 != "">
    <@tooBar title2=title2 nomsg=true/>
    </#if>
    <@tooBar title=title isform=true/>
    <#nested>
    <script type="text/javascript"  language="javascript" >
        //    <#if back>
//        <#if backurl == "">
         addBackOrClose("<@text name='action.back'/>");
//        <#else>
        addItem("返回", "location.href='${backurl}'");
        //</#if>
        //</#if>
    </script>
</body>
<#include "/org/beangle/website/cms/template/template/foot.ftl"/>
</#macro>
