/* 
 * user log in 
 * link with ifttt.html
 */
/*
 * Modernizr 2.6.1 (Custom Build) | MIT & BSD Build:
 * http://modernizr.com/download/#-canvas-history-input-shiv-cssclasses
 */


var THIS_TYPE;
var THIS_datetime;
var THIS_email;
var THIS_weibo;
var THAT_TYPE;
var THAT_email_Dst_Box;
var THAT_email_Src_Box;
var THAT_email_Src_PWd;


$(document).ready(function(){
	$("#createTaskStep1").hide();
	$("#createTaskStep2-datetime").hide();
	$("#createTaskStep2-email").hide();
	$("#createTaskStep2-weibo").hide();
	$("#createTaskStep3").hide();
	$("#createTaskStep4").hide();
	$("#createTaskStep5-email").hide();
	$("#createTaskStep5-weibo").hide();
	$("#createTaskStep6").hide();
});

function goToTHIS() {
	$("#createTaskStep1").slideDown("slow");
	$("#createTaskStep2-datetime").hide();
	$("#createTaskStep2-email").hide();
	$("#createTaskStep2-weibo").hide();
	$("#createTaskStep3").hide();
	$("#createTaskStep4").hide();
	$("#createTaskStep5-email").hide();
	$("#createTaskStep5-weibo").hide();
	$("#createTaskStep6").hide();
	$("html,body").animate({scrollTop:($("#createTaskStep1").offset().top - 50)},"slow");
//	scrollTo(($("#createTaskStep1").offset().top),($("#createTaskStep1").offset().left));
//	$body.animate({scrollTop: $('#createTaskStep1').offset()}, 1000);
//	show_and_scroll_to("#createTaskStep1",function(){
//		dim_out("#createTaskStep0");
//		});

}

function chooseTHIS(thistype) {
	THIS_TYPE = thistype;
	switch (thistype) {
	case 1:
		$("#createTaskStep2-datetime").slideDown("slow");
		$("html,body").animate({scrollTop:($("#createTaskStep2").offset().top - 50)},"slow");
		$("#createTaskStep2-email").hide();
		$("#createTaskStep2-weibo").hide();//		$("#THIS_Task_img").attr("src","images/datetime.png");
		$("img[name$='THIS_Task_img']").attr("src","../images/datetime.png");
		break;
	case 2:
		$("#createTaskStep2-email").slideDown("slow");
		$("html,body").animate({scrollTop:($("#createTaskStep2").offset().top - 50)},"slow");
		$("#createTaskStep2-datetime").hide();
		$("#createTaskStep2-weibo").hide();
//		$("#THIS_Task_img").attr("src","images/email.png");
		$("img[name$='THIS_Task_img']").attr("src","../images/email.png");
		break;
	case 3:
		$("#createTaskStep2-weibo").slideDown("slow");
		$("html,body").animate({scrollTop:($("#createTaskStep2").offset().top - 50)},"slow");
		$("#createTaskStep2-datetime").hide();
		$("#createTaskStep2-email").hide();
//		$("#THIS_Task_img").attr("src","images/weibo.png");
		$("img[name$='THIS_Task_img']").attr("src","../images/weibo.png");
		break;

	default:
		break;
	}
	
	$("#createTaskStep3").hide();
	$("#createTaskStep4").hide();
	$("#createTaskStep5-email").hide();
	$("#createTaskStep5-weibo").hide();
	$("#createTaskStep6").hide();
}


function createTrigger() {
	switch (THIS_TYPE) {
	case 1:
		THIS_datetime=$("#datetimeText").val();
		break;
	case 2:
		THIS_email=$("#emailRcvdText").val();
		break;
	case 3:
		THIS_weibo=$("#weiboRcvdIDText").val();
		break;

	default:
		break;
	}
	$("#createTaskStep3").slideDown("slow");
	$("#createTaskStep4").hide();
	$("#createTaskStep5-email").hide();
	$("#createTaskStep5-weibo").hide();
	$("#createTaskStep6").hide();
	
	$("html,body").animate({scrollTop:($("#createTaskStep3").offset().top - 50)},"slow");
}

function goToTHAT() {
	$("#createTaskStep4").slideDown("slow");
	$("#createTaskStep5-email").hide();
	$("#createTaskStep5-weibo").hide();
	$("#createTaskStep6").hide();
	$("html,body").animate({scrollTop:($("#createTaskStep4").offset().top - 50)},"slow");
}

function chooseTHAT(thattype) {
	THAT_TYPE = thattype;
	switch (thattype) {
	case 1:
		$("#createTaskStep5-email").slideDown("slow");
		$("#createTaskStep5-weibo").hide();
		$("html,body").animate({scrollTop:($("#createTaskStep5").offset().top - 50)},"slow");
//		$("#THAT_Task_img").attr("src","images/email.png");
		$("img[name$='THAT_Task_img']").attr("src","../images/email.png");
		break;
	case 2:
		$("#createTaskStep5-weibo").slideDown("slow");
		$("#createTaskStep5-email").hide();
		$("html,body").animate({scrollTop:($("#createTaskStep5").offset().top - 50)},"slow");
//		$("#THAT_Task_img").attr("src","images/weibo.png");
		$("img[name$='THAT_Task_img']").attr("src","../images/weibo.png");
		break;

	default:
		break;
	}
	
	$("#createTaskStep6").hide();

}

function createAction() {
	switch (THAT_TYPE) {
	case 1:
		THAT_email_Dst_Box=$("#emailSendDstText").val();
		THAT_email_Src_Box=$("#emailSendSrcText").val();
		THAT_email_Src_PWd=$("#emailSendSrcPasswdText").val();
		break;
	case 2:
		THAT_weibo_ID=$("#weiboSendIDText").val();
		THAT_weibo_PWd=$("#weiboSendPassWd").val();
		break;

	default:
		break;
	}
	$("#createTaskStep6").slideDown("slow");
	$("html,body").animate({scrollTop:($("#createTaskStep6").offset().top - 50)},"slow");
}


function createNewTask(){
	if(isSignupInfoValid()){
		$("#signupInfo").html();
		$.ajax({
			type:'post',
			data:{
				usernamesignup:$('#usernamesignup').val(),
				emailsignup:$('#emailsignup').val(),
				passwdsignup:$('#passwdsignup').val(),
			},
		});
		
	}else{
		$("#signupInfo").html("<font color=\"red\">" + "InValid SignIn Info" + "</font>");
	}
}


/*function show_and_scroll_to(a,b,c)
{
	var d=$.browser.msie?$(window).height():window.innerHeight,
			e=$(a),
			f=300,
			g=600,
			h=navigator.userAgent.toLowerCase().search("wosbrowser")==-1;
	e.fadeIn(f,"swing",function(){
		var f=!1,i=e.offset().top,j=4,k=d-e.height();
		k<0&&(k=0),$("#create_cover").height(k);
		var l=function(){
			if(f)return;
			f=!0,$(".step_parent").each(function(){
				var b=$(this);
				parent_id="#"+b.attr("id");
				if(parent_id==a)return!1;
				b.find(".btn-restart").show(),
				b.find(".btn-back").hide();}),
			set_keyboard_init_focus(a),
			b&&(c?setTimeout(b,c):b());
		};
		h?$("html,body").animate({scrollTop:i+j},g,"swing",l):($(window).scrollTop(i+j),l());}
	);}*/
