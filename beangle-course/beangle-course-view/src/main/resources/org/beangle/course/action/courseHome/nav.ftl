[#ftl]
<div id="nav">
  <div class="nav_div">
    <ul class="nav_ul">
      <li>
      	[@b.a href="!${(homeColumn.url)!}?courseId=${course.id!}" title="${(homeColumn.name)!}" id="${(homeColumn.id)!}" class="nav_a_now" target="BodyBg"]${(homeColumn.name)!}[/@]
       </li>
      [#list courseColumns?sort_by("code") as courseColumn]
	      <li>
	      	[@b.a href="#" id="${(courseColumn.id)!}" target="BodyBg"]${(courseColumn.name)!}[/@]
	      </li>
      [/#list]
    </ul>
    <a href="#" class="closed_top_ico"></a> <a href="#" class="expand_top_ico"></a> </div>
</div>
<script type="text/javascript">
	  jQuery(document).ready(function(){
	   $("#${(homeColumn.id)!}").click( function() {
	     	 [#list courseColumns?sort_by("code") as other]
	     		 $('#${(other.id)!}').removeClass('nav_a_now'); 
	     	 [/#list]
		     $('#${(homeColumn.id)!}').addClass('nav_a_now'); 
		});
	  [#list courseColumns?sort_by("code") as courseColumn]
	     $("#${(courseColumn.id)!}").click( function() {
	     	 $('#${(homeColumn.id)!}').removeClass('nav_a_now'); 
	     	 [#list courseColumns?sort_by("code") as other]
	     		 $('#${(other.id)!}').removeClass('nav_a_now'); 
	     	 [/#list]
		     $('#${(courseColumn.id)!}').addClass('nav_a_now'); 
		  });
      [/#list]
    })
</script>