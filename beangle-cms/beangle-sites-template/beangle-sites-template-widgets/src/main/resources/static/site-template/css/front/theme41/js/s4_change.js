// JavaScript Document
$(document).ready(function(){
$('.s4 dt:first').addClass('active');
$('.s4 dd:first').css('display','block');
autoroll();
hookThumb();
});
var i=-1; //第i+1个tab开始
var offset = 5500; //轮换时间
var timer = null;
function autoroll(){
n = $('.s4 dt').length-1;
i++;
if(i > n){
i = 0;
}
slide(i);
timer = window.setTimeout(autoroll, offset);
}
function slide(i){
$('.s4 dt').eq(i).addClass('active').siblings().removeClass('active');
$('.s4 dd').eq(i).css('display','block').siblings('.s4 dd').css('display','none');

}
//function hookThumb(){
//$('.s4 dt').hover(
//function () {
//if (timer) {
//clearTimeout(timer);
//i = $(this).prevAll().length;
//slide(i);
//}
//},
//function () {
//
//timer = window.setTimeout(autoroll, offset);
//this.blur();
//return false;
//}
//);
//}

function hookThumb() {

}

