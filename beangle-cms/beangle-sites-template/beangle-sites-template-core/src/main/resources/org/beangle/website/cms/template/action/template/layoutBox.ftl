<script type="text/javascript">
    $(function (){
        $('#layoutBoxTabs').tabs();
        $(".layoutBox div").draggable({ 
            appendTo: 'body' ,
            connectToSortable: '.layout,#layoutRoot',
            zIndex: 9999,
            cursor: "move",
            opacity: 0.7,
            revert: "invalid",
            helper: "clone" ,
//            cursorAt: { right: -12 , top: 20}
            cursorAt: { left: 15 , top: 15}
        });
    })
</script>

<div id="layoutBoxTabs" class="layoutBoxTabs">
    <ul>
        <li><a href="#layouts">布局工具箱</a></li>
    </ul>
    <div class="layoutBox" id="layouts" style="list-style: none;">

        <div class="litem litem1" data='{"rows":"1"}'>
            <span>100%</span> 
        </div>
        <div class="litem litem2" data='{"rows":"2"}'>
            <span>100% * 2</span> 
        </div>
        <div class="litem litem3" data='{"rows":"3"}'>
            <span>100% * 3</span> 
        </div>
        <div class="litem litem4" data='{"cols":"25,75"}'>
            <span>25%+75%</span> 
        </div>
        <div class="litem litem5" data='{"cols":"50,50"}'>
            <span>50%+50%</span> 
        </div>
        <div class="litem litem6" data='{"cols":"75,25"}'>
            <span>75%+25%</span>
        </div>
        <div class="litem litem7" data='{"cols":"33,34,33"}'>
            <span>33%+34%+33%</span> 
        </div>
        <div class="litem litem8" data='{"cols":"25,50,25"}'>
            <span>25%+50%+25%</span> 
        </div>
        <div class="litem litem9" data='{"cols":"25,25,25,25"}'>
            <span>25%*4</span>
        </div>
        <div class="litem litem10" data='{"cols":"20,20,20,20,20"}'>
            <span>20%*5</span>
        </div>
    </div>
</div>