/* 
 * user log in 
 * link with ifttt.html
 */
/*
 * Modernizr 2.6.1 (Custom Build) | MIT & BSD Build:
 * http://modernizr.com/download/#-canvas-history-input-shiv-cssclasses
 */

var usernameNow;

var taskJSONInfo;

var taskName;

var THIS_TYPE;
var thisDetail;
var taskDatetime;
var srcEmailBox;
var srcEmailPassWd;
var listenWeiboID;
var listenWeiboPassWd;
var listenWeiboCheckCon;


var THAT_TYPE;
var thatDetail;
var dstEmailBox;
var updateWeiboID;
var updateWeiboPassWd;

var EmailSubject;
var taskContent;


$(document).ready(function(){
	
	
	usernameNow=$("#userInfoDropdownMenu").html();
	
//	alert("username:" + usernameNow);
	
	$("#createTaskStep1").hide();
	$("#createTaskStep2-datetime").hide();
	$("#createTaskStep2-email").hide();
	$("#createTaskStep2-weibo").hide();
	$("#createTaskStep3").hide();
	$("#createTaskStep4").hide();
	$("#createTaskStep5-email").hide();
	$("#createTaskStep5-weibo").hide();
	$("#createTaskStep6").hide();
	$("#createTaskStep7").hide();
});

/**
 * 检测邮箱是否合法
 * @param email
 * @param infotxt
 * @returns {Boolean}
 */
function isEmailValid(emailId) {
	var email = $("#"+emailId).val();
	apos = email.indexOf("@");
	dotpos = email.lastIndexOf(".");
	if (apos < 1 || dotpos - apos < 2) {
		alert("Illegal Email Address");
		return false;
	} else {
		return true;
	}
}

function isNotEmpty(inputtextId, infotxt) {
	var inputtext = $("#" + inputtextId).val();
	if (inputtext == null || inputtext == "") {
		alert(infotxt + " can't be empty!");
		return false;
	} else {
		
		return true;
	}
}


/**
 * 进行显示THIS的Trigger Type选择div
 */
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
	$("#createTaskStep7").hide();
	$("html,body").animate({scrollTop:($("#createTaskStep1").offset().top - 50)},"slow");
}

/**
 * 选择THIS的Trigger的种类Type
 * @param thistype 0表示定时任务 1表示监听邮箱 2表示监听微博
 */
function chooseTHIS(thistype) {
	THIS_TYPE = thistype;
	switch (thistype) {
	case 0:
		$("#createTaskStep2-datetime").slideDown("slow");
		$("#createTaskStep2-email").hide();
		$("#createTaskStep2-weibo").hide();
		$("html,body").animate({scrollTop:($("#createTaskStep2").offset().top - 50)},"slow");
		// 更新相应的Trigger图片
		$("img[name$='THIS_Task_img']").attr("src","images/datetime.png");
		break;
	case 1:
		$("#createTaskStep2-email").slideDown("slow");
		$("#createTaskStep2-datetime").hide();
		$("#createTaskStep2-weibo").hide();
		$("html,body").animate({scrollTop:($("#createTaskStep2").offset().top - 50)},"slow");
		// 更新相应的Trigger图片
		$("img[name$='THIS_Task_img']").attr("src","images/email.png");
		break;
	case 2:
		$("#createTaskStep2-weibo").slideDown("slow");
		$("#createTaskStep2-datetime").hide();
		$("#createTaskStep2-email").hide();
		$("html,body").animate({scrollTop:($("#createTaskStep2").offset().top - 50)},"slow");
		// 更新相应的Trigger图片
		$("img[name$='THIS_Task_img']").attr("src","images/weibo.png");
		break;
	default:
		break;
	}
	$("#createTaskStep3").hide();
	$("#createTaskStep4").hide();
	$("#createTaskStep5-email").hide();
	$("#createTaskStep5-weibo").hide();
	$("#createTaskStep6").hide();
	$("#createTaskStep7").hide();
}

/**
 * 确认Trigger的种类和相关信息
 */
function createTrigger() {
	switch (THIS_TYPE) {
	case 0:
		if(!isNotEmpty("datetimeText", "DateTime")){
			$("#datetimeText").focus();
			return;
		}else {
			taskDatetime=$("#datetimeText").val();
		}
		break;
	case 1:
		if(!isNotEmpty("emailRcvdBox", "Receive Email")){
			$("#emailRcvdBox").focus();
			return;
		}else if (!isNotEmpty("emailRcvdPassWd", "Email password")){
			$("#emailRcvdPassWd").focus();
			return;
		}else if (!isEmailValid("emailRcvdBox")){
			$("#emailRcvdBox").focus();
			return;
		}else {
			srcEmailBox=$("#emailRcvdBox").val();
			srcEmailPassWd=$("#emailRcvdPassWd").val();
		}
		break;
	case 2:
		if(!isNotEmpty("weiboRcvdIDID", "WeiboID")){
			$("#weiboRcvdIDID").focus();
			return;
		}else if (!isNotEmpty("weiboRcvdIDPassWd", "Weibo password")){
			$("#weiboRcvdIDPassWd").focus();
			return;
		}else if (!isNotEmpty("weiboRcvdCheckCon", "weibo Check Content")){
			$("#weiboRcvdCheckCon").focus();
			return;
		}else {
			listenWeiboID=$("#weiboRcvdIDID").val();
			listenWeiboPassWd=$("#weiboRcvdIDPassWd").val();
			listenWeiboCheckCon=$("#weiboRcvdCheckCon").val();
		}
		break;

	default:
		break;
	}
	$("#createTaskStep3").slideDown("slow");
	$("#createTaskStep4").hide();
	$("#createTaskStep5-email").hide();
	$("#createTaskStep5-weibo").hide();
	$("#createTaskStep6").hide();
	$("#createTaskStep7").hide();
	$("html,body").animate({scrollTop:($("#createTaskStep3").offset().top - 50)},"slow");
}

/**
 * 进行显示THAT的Action Type选择div
 */

function goToTHAT() {
	$("#createTaskStep4").slideDown("slow");
	$("#createTaskStep5-email").hide();
	$("#createTaskStep5-weibo").hide();
	$("#createTaskStep6").hide();
	$("#createTaskStep7").hide();
	$("html,body").animate({scrollTop:($("#createTaskStep4").offset().top - 50)},"slow");
}

/**
 * 选择THAT的Action的种类Type
 * @param thattype 3表示发邮件 2表示发微博
 */

function chooseTHAT(thattype) {
	THAT_TYPE = thattype;
	switch (thattype) {
	case 3:
		//根据Trigger的类型决定是否显示SendSrcMail的输入
		if(THIS_TYPE == 1){
			//如果Trigger是邮箱 则不显示 即用监听的邮箱发邮件
			$("#emailSendSrcBox").hide();
			$("#emailSendSrcPassWd").hide();
		}else{
			//如果Trigger不是邮箱 则显示
			$("#emailSendSrcBox").show();
			$("#emailSendSrcPassWd").show();
		}
			
		$("#createTaskStep5-email").slideDown("slow");
		$("#createTaskStep5-weibo").hide();
		$("html,body").animate({scrollTop:($("#createTaskStep5").offset().top - 50)},"slow");
		// 更新相应的Action图片
		$("img[name$='THAT_Task_img']").attr("src","images/email.png");
		break;
	case 4:
		$("#createTaskStep5-weibo").slideDown("slow");
		$("#createTaskStep5-email").hide();
		$("html,body").animate({scrollTop:($("#createTaskStep5").offset().top - 50)},"slow");
		// 更新相应的Action图片
		$("img[name$='THAT_Task_img']").attr("src","images/weibo.png");
		break;
	default:
		break;
	}
	$("#createTaskStep6").hide();
	$("#createTaskStep7").hide();
}

/*
 * 确认Action的种类和相关信息
 */
function createAction() {
	switch (THAT_TYPE) {
	case 3:
		//验证DstMail的信息
		if(!isNotEmpty("emailSendDstBox", "Send Dst Email")){
			$("#emailSendDstBox").focus();
			return;
		}else if (!isEmailValid("emailSendDstBox")){
			$("#emailSendDstBox").focus();
			return;
		}else{
			dstEmailBox=$("#emailSendDstBox").val();
		}
		
		//验证邮件标题和内容的信息
		if(!isNotEmpty("emailSendSubject", "Email Subject")){
			$("#emailSendSubject").focus();
			return;
		}else if(!isNotEmpty("emailSendContent", "Email Content")){
			$("#emailSendContent").focus();
			return;
		}else{
			EmailSubject=$("#emailSendSubject").val();
			taskContent=$("#emailSendContent").val();
		}
		
		//依据Trigger类型的不同 分别验证SendSrcMail的信息
		if(THIS_TYPE == 1){
			//如果Trigger是邮箱 则不显示 即用监听的邮箱发邮件
			if(!isNotEmpty("emailRcvdBox", "Send Src Email")){
				$("#emailRcvdBox").focus();
				return;
			}else if (!isNotEmpty("emailRcvdPassWd", "Email password")){
				$("#emailRcvdPassWd").focus();
				return;
			}else if (!isEmailValid("emailRcvdBox")){
				$("#emailRcvdBox").focus();
				return;
			}
		}else{
			//如果Trigger不是邮箱 则用新的发送邮箱
			if(!isNotEmpty("emailSendSrcBox", "Send Src send Email")){
				$("#emailSendSrcBox").focus();
				return;
			}else if (!isNotEmpty("emailSendSrcPassWd", "Email password")){
				$("#emailSendSrcPassWd").focus();
				return;
			}else if (!isEmailValid("emailSendSrcBox")){
				$("#emailSendSrcBox").focus();
				return;
			}else{
				srcEmailBox=$("#emailSendSrcBox").val();
				srcEmailPassWd=$("#emailSendSrcPassWd").val();
			}
		}
		
		break;
	case 4:
		if(!isNotEmpty("weiboSendID", "WeiboID")){
			$("#weiboRcvdIDID").focus();
			return;
		}else if (!isNotEmpty("weiboSendPassWd", "Weibo password")){
			$("#weiboRcvdIDPassWd").focus();
			return;
		}else if(!isNotEmpty("weiboSendContent", "Weibo Content")){
			$("#weiboSendContent").focus();
			return;
		}else{
			updateWeiboID=$("#weiboSendID").val();
			updateWeiboPassWd=$("#weiboSendPassWd").val();
			taskContent =$("#weiboSendContent").val();
		}
		break;

	default:
		break;
	}
	$("#createTaskStep6").slideDown("slow");
	$("#createTaskStep7").slideDown("slow");
	$("html,body").animate({scrollTop:($("#createTaskStep6").offset().top - 50)},"slow");
}

/**
 * 创建Task
 */
function createNewTask(){
	//检测任务名
	if (!isNotEmpty("taskNameText", "TaskName")){
		$("#taskNameText").focus();
		return;
	}else {
		taskName=$("#taskNameText").val();
	}
//	
//	//记录和Trigger有关的信息成为JSON形式
//	switch (THIS_TYPE) {
//	case 0:
//		thisDetail = '"taskDeadTime" :"' + taskDatetime + '"';
//		break;
//	case 1:
//		thisDetail = '"srcMailBox" :"' + srcEmailBox + '",' +
//						'"srcMailPassWd" :"' + srcEmailPassWd + '"';
//		break;
//	case 2:
//		thisDetail = '"listenWeiboID" :"' + listenWeiboID + '",' +
//						'"listenWeiboPassWd" :"' + listenWeiboPassWd + '",' +
//						'"weiboCheckCon" :"' + listenWeiboCheckCon + '"';
//		break;
//	default:
//		thisDetail = '';
//		break;
//	}
//	
//	//记录和Action有关的信息成为JSON形式
//	switch (THAT_TYPE) {
//	case 3:
//		if(THIS_TYPE == 1){
//			//如果Trigger是邮箱 则不显示 即用监听的邮箱发邮件
//			thatDetail = '"dstMailBox" :"' + dstEmailBox + '",' + 
//							'"mailSubject" :"' + EmailSubject + '",' +
//							'"content" :"' + taskContent + '"';
//		}else{
//			//如果Trigger不是邮箱 则用新的发送邮箱
//			thatDetail = '{"dstMailBox" :"' + dstEmailBox + '",' +
//							'"srcMailBox" :"' + srcEmailBox + '",' +
//							'"srcMailPassWd" :"' + srcEmailPassWd + '",' +
//							'"mailSubject" :"' + EmailSubject + '",' +
//							'"content" :"' + taskContent + '"';
//		}
//		break;
//	case 4:
//		thatDetail = '"updateWeiboId" :"' + updateWeiboID + '",' +
//						'"updateWeiboPassWd" :"' + updateWeiboPassWd + '",' +
//						'"content" :"' + taskContent + '"';
//		break;
//	default:
//		thatDetail = '';
//	break;
//	}
//	
//	//创建JSON
//	taskJSONInfo = '[{"taskName" :"' + taskName + '",' +
//						'"taskBuilder" :"' + usernameNow + '",' + 
//						'"taskTHISType" :"' + THIS_TYPE + '",' + 
//						'"taskTHATType" :"' + THAT_TYPE + '",' + 
//						thisDetail + ',' +
//						thatDetail + ',' +
//						'}]';
	
//	alert(taskJSONInfo);
	
	$.ajax({
		type:'post',
		data:{
			taskName:taskName,
			taskBuilder:usernameNow,
			taskTHISType:THIS_TYPE,
			taskTHATType:THAT_TYPE,
			
			taskDeadTime:taskDatetime,
			srcMailBox:srcEmailBox,
			srcMailPassWd:srcEmailPassWd,
			listenWeiboID:listenWeiboID,
			listenWeiboPassWd:listenWeiboPassWd,
			weiboCheckCon:listenWeiboCheckCon,
			
			dstMailBox:dstEmailBox,
			mailSubject:EmailSubject,
			updateWeiboID:updateWeiboID,
			updateWeiboPassWd:updateWeiboPassWd,
			content:taskContent,
		},
		url:"CreateNewTask",
		success:function (canCreateTask){
			alert(canCreateTask);
			if(canCreateTask == "true"){
				alert("Create Task successfully!");
				window.location.href="dashboard.jsp";
			}else {
				alert("Fail in create Task!");
			}
		},
		error:function (canCreateTask){
			alert("Error in create Task.");
		}
	});
		
}

