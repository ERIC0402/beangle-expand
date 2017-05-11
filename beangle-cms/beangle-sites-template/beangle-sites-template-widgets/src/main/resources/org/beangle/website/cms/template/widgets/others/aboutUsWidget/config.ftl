<table width="100%" class="list_box1">
	<tr>
        <td class="list_box1_title" name="configTd"><label>标题</label>：</td>
        <td>
            <input type="text" name="config.title" value="${config.title!}" maxlength=8/>
        </td>
    </tr>
    <tr>
        <td class="list_box1_title" name="configTd"><label>标题一</label>：</td>
        <td>
            <input type="text" name="config.title1" value="${config.title1!}" maxlength=100/>
        </td>
    </tr>
    <tr>
        <td class="list_box1_title" name="configTd"><label>内容一</label>：</td>
        <td>
            <input type="text" name="config.content1" value="${config.content1!}" maxlength=100/>
        </td>
    </tr>
    <tr>
        <td class="list_box1_title" name="configTd"><label>标题二</label>：</td>
        <td>
            <input type="text" name="config.title2" value="${config.title2!}" maxlength=100/>
        </td>
    </tr>
    <tr>
        <td class="list_box1_title" name="configTd"><label>内容二</label>：</td>
        <td>
            <input type="text" name="config.content2" value="${config.content2!}" maxlength=100/>
        </td>
    </tr>
    <tr>
        <td class="list_box1_title" name="configTd"><label>标题三</label>：</td>
        <td>
            <input type="text" name="config.title3" value="${config.title3!}" maxlength=100/>
        </td>
    </tr>
    <tr>
        <td class="list_box1_title" name="configTd"><label>内容三</label>：</td>
        <td>
            <input type="text" name="config.content3" value="${config.content3!}" maxlength=100/>
        </td>
    </tr>
    <tr>
        <td class="list_box1_title" name="configTd"><label>标题四</label>：</td>
        <td>
            <input type="text" name="config.title4" value="${config.title4!}" maxlength=100/>
        </td>
    </tr>
    <tr>
        <td class="list_box1_title" name="configTd"><label>内容四</label>：</td>
        <td>
            <input type="text" name="config.content4" value="${config.content4!}" maxlength=100/>
        </td>
    </tr>
    <tr>
        <td class="list_box1_title" name="configTd"><label>图片</label>：</td>
        <td>
            <input type="hidden" name="config.img" value="${config.img!'css/front/theme1/style_01/banner_pic.jpg'}"/>
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
    
    var a_fields = {
    	'config.title':{'l':'最多输入8个字符'},
        'config.title1':{'l':'最多输入100个字符'},
        'config.content1':{'l':'最多输入100个字符'},
        'config.title2':{'l':'最多输入100个字符'},
        'config.content2':{'l':'最多输入100个字符'},
        'config.title3':{'l':'最多输入100个字符'},
        'config.content3':{'l':'最多输入100个字符'},
        'config.title4':{'l':'最多输入100个字符'},
        'config.content4':{'l':'最多输入100个字符'}
    };
    var v = new validator(a_fields);
</script>
