$(document).ready(function(){
  $('.slider').slick({
    autoplay : true,
		dots: true,
		speed : 800 /* 이미지가 슬라이딩시 걸리는 시간 */,
		infinite: true,
		autoplaySpeed: 800 /* 이미지가 다른 이미지로 넘어 갈때의 텀 */,
		arrows: true,
		slidesToShow: 1,
		slidesToScroll: 1,
    fade: true,
    pauseOnHover:false
  });

  $(".header .bottom_menu").hover(function(){
    $(".header .sub_menu").addClass("on");    
  },function(){    
  })
  $(".header .sub_menu").mouseleave(function(){
    $(".header .sub_menu").removeClass("on");
  })



});


function getContextRootPath(){
  const html = document.querySelector("html");
  console.log("html:"+html);
  if(!html || !html.getAttribute("data-contextpath"))
    return "/";
  console.log("contextRoot:",html.getAttribute("data-contextpath"));
  return html.getAttribute("data-contextpath");
}