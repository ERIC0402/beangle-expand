<#include "/widgetComm.ftl"/>
<#setting locale='en'> <div class="BlankLine1"></div>
<div class="box_1 box_1_p">
	<div class="box_1_left_shadow">
		<div class="list_box_1">
			<div class="p_1">
				<div>
					<h3 class="title_1"><font></font><span>${config.title!'Welcome to SELEE'}</span></h3>
					<#if column??>
					<#list contents as item>
					<div class="p_2">
						<#if item.content.titleImage??>
						<div class="pic_box_1"><img src="<@url.getTitleImage cc=item/>">
						</div>
						</#if>
						<div class="text_box_1">
							${(item.content.abstracts)!}
						</div>
					</div>
					</#list>
					<div class="more_1">
						<a href="<@url.column c=column/>">More &raquo;</a>
					</div>
					<#else>
					<div class="p_2">
						<div class="pic_box_1"><img src="${frontbase}/css/front/theme61/style_01/temp_31.jpg">
						</div>
						<div class="text_box_1">
							An imperial edict issued in 1896 by Emperor Guangxu, established Nanyang Public School in Shanghai. <p></p>
							The normal school, school of foreign studies, middle school and a high school were established. Sheng Xuanhuai, the person responsible for proposing the idea to the emperor, became the first president and is regarded as the founder of the university.
						</div>
					</div>
					<div class="more_1">
						<a href="#">More &raquo;</a>
					</div>
					</#if>
				</div>
			</div>
		</div>
		<div class="box_1_bottom_shadow"></div>
	</div>
</div>
