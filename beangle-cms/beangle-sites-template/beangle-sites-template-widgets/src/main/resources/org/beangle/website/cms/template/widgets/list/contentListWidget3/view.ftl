  <#include "/widgetComm.ftl"/>
  <div class="BlankLine2"></div>
  
  
 
      <div class="c_Left_Bottom_Left_border">
            <div class="list_box_2">
               <h3 class="title_2">
               
                <div class="div_2">
                
                <#if column??>
                <span class="span_2"><font class="span_1">&middot;</font>${column.name!}</span>
                <a class="title_2_a" href="<@url.column c=column />"><font>&rsaquo;更 多</a>
                <#else>
                <span class="span_2"><font class="span_1">&middot;</font>图片新闻</span>
                <a class="title_2_a" href="#">&rsaquo;更 多</a>
                 </#if>
                 
           
           
               </div>
               
              </h3>  
               <#if !config.Content??>
             <ul>
                <#if !contents??>
                <#list contents as item>
                <li> 
                    <font>&gt;</font>
                    <a href="<@url.content cc=item/>" target="_blank"> <@wv.contentTitle cc=item/> </a>

                    <#if config.showDate??><span> [${(item.publishDate?date)!}] </span></#if>

                </li>
                </#list>
                <#else>
                
	              <li> <font>&gt;</font><a href="#"> 上海交通大学—日本早稻 本早稻 本稻 本早稻</a><#if config.showDate??><span> [2011.4.1] 
	
					</span></#if> </li>
				  <li> <font>&gt;</font><a href="#"> 上海交通大学—日本早稻 本早稻 本早稻 本早稻 </a><#if config.showDate??><span> 
					
					[2011.4.1] 
					
					</span> </#if></li>
			     <li> <font>&gt;</font><a href="#"> 上海交通大学—日本早稻 本早稻 本早稻 本早稻 </a><#if config.showDate??><span> 
					
					[2011.4.1] 
					
					</span> </#if></li>
				<li> <font>&gt;</font><a href="#"> 上海交通大学—日本早稻 本早稻 本早稻 本早稻 本早稻 本早稻 
					
					</a><#if config.showDate??><span> [2011.4.1] </span> </#if></li>
				<li> <font>&gt;</font><a href="#"> 上海交通大学—日本早稻 本早稻 本早稻 本早稻 本早稻 本早稻 
					
					</a><#if config.showDate??><span> [2011.4.1] </span> </#if></li>
				<li> <font>&gt;</font><a href="#"> 上海交通大学—日本早稻 本早稻 本早稻 本早稻 本早稻 本早稻 
					
					</a><#if config.showDate??><span> [2011.4.1] </span> </#if></li>
				<li> <font>&gt;</font><a href="#"> 上海交通大学—日本早稻 本早稻 本早稻 本早稻 本早稻 本早稻 
					
					</a><#if config.showDate??><span> [2011.4.1] </span> </#if></li>
				<li> <font>&gt;</font><a href="#"> 上海交通大学—日本早稻 本早稻 本早稻 本早稻 本早稻 本早稻 
					
					</a><#if config.showDate??><span> [2011.4.1] </span> </#if></li>
				<li> <font>&gt;</font><a href="#"> 上海交通大学—日本早稻 本早稻 本早稻 本早稻 本早稻 本早稻 
					
					</a><#if config.showDate??><span> [2011.4.1] </span> </#if></li>
      </#if>
   </ul>
   </#if>
  </div>
</div>

  
  
  
  
  
  
  
 