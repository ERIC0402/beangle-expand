[#ftl]
      <div class="BlankLine1"></div>
      <div class="div_list_5">
        <h2 class="h2_style_5"> <span>推荐资源</span><a href="#">更多</a> </h2>
       <div class="BlankLine1"></div>
       <div style="padding:0px 10px">
        
        <ul class="ul_style_2_2">
	        [#list courseResourceList as resource]
	        	[#list resource.resourceFiles as resourceFile]
		          <li>
		            <div class="pic_box">
			            <a href="#">
			              <div class="file_ico file_ico_3"></div>
			              <img src="${base}/common/file.action?method=downFile&folder=${(resourceFile.filePath)!}">
			             </a>
		              </div>
		          	  <div class="text_box_1">
		              <a class="a_title_1" href="#">${(resource.zybt)!}</a> 
		            </div>
		            <font>3300 人阅读</font> 
		            </li>
	            [/#list]
	        [/#list]
        </ul>
        <div class="BlankLine1"></div>
        </div>
        
      </div>
