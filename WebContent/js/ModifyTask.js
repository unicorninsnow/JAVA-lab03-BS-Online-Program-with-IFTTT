/* 
 * user log in 
 * link with ifttt.html
 */
/*
 * Modernizr 2.6.1 (Custom Build) | MIT & BSD Build:
 * http://modernizr.com/download/#-canvas-history-input-shiv-cssclasses
 */

var usernameNow;
var taskIDNow;

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
	
	var sch = window.location.search;
	sch = sch.substring(1);
	taskIDNow = sch.substring(sch.indexOf("=") + 1);
	
	getTheTaskInfoJSON(taskIDNow);
	initializeTaskInfo();
	initializeTaskToModify();
	
});

/**
 * 根据传入的taskID来获取当前任务的JSON
 * @param task_ID
 */
function getTheTaskInfoJSON(task_ID) {
	alert(usernameNow + " wants to modify task:" + task_ID);
	taskIDNow = task_ID;
	$.ajax({
		type:'post',
		data:{
			taskID:task_ID,
		},
		async:false,
		url:'GetTaskDetails',
		success:function (thisTaskJsonText){
			taskJSONInfo = eval (thisTaskJsonText)[0];
		},
		error:function (thisTaskJsonText){
			alert("Can't get Your task List.");
		}
	});
}

function initializeTaskInfo() {
	taskName = taskJSONInfo.taskName;
	THIS_TYPE = Number(taskJSONInfo.taskTHISType);
	THAT_TYPE = Number(taskJSONInfo.taskTHATType);
	
	switch (THIS_TYPE) {
	case 0:
		taskDatetime = taskJSONInfo.taskDeadTime;
		break;
	case 1:
		srcEmailBox = taskJSONInfo.srcMailBox;
		srcEmailPassWd = taskJSONInfo.srcMailPassWd;
		break;
	case 2:
		listenWeiboID = taskJSONInfo.listenWeiboID;
		listenWeiboPassWd = taskJSONInfo.listenWeiboPassWd;
		listenWeiboCheckCon = taskJSONInfo.weiboCheckCon;
		break;
	default:
		thisDetail = '';
		break;
	}
	
	switch (THAT_TYPE) {
	case 3:
		if(THIS_TYPE == 1){
			//如果Trigger是邮箱 则不显示 即用监听的邮箱发邮件
			dstEmailBox = taskJSONInfo.dstMailBox;
			EmailSubject = taskJSONInfo.mailSubject;
			taskContent = taskJSONInfo.content;
		}else{
			//如果Trigger不是邮箱 则用新的发送邮箱
			dstEmailBox = taskJSONInfo.dstMailBox;
			srcEmailBox = taskJSONInfo.srcMailBox;
			srcEmailPassWd = taskJSONInfo.srcMailPassWd;
			EmailSubject = taskJSONInfo.mailSubject;
			taskContent = taskJSONInfo.content;
		}
		break;
	case 4:
		updateWeiboID = taskJSONInfo.updateWeiboId;
		updateWeiboPassWd = taskJSONInfo.updateWeiboPassWd;
		taskContent = taskJSONInfo.content;
		break;
	default:
		thatDetail = '';
	break;
	}
	
}

/**
 * 根据已从后台JSON得到当前任务信息 初始化显示页面
 */
function initializeTaskToModify(){
	$("#taskNameText").val(taskName);
	
	//根据THIS_TYPE 来进行内容初始化
	switch (THIS_TYPE) {
	case 0:
		$("#modifyTaskStep2-datetime").slideDown("slow");
		$("#modifyTaskStep2-email").hide();
		$("#modifyTaskStep2-weibo").hide();
		// 更新相应的Trigger图片
		$("img[name$='THIS_Task_img']").attr("src","images/datetime.png");
		//填充内容
		$("#datetimeText").val(taskDatetime);
		break;
	case 1:
		$("#modifyTaskStep2-email").slideDown("slow");
		$("#modifyTaskStep2-datetime").hide();
		$("#modifyTaskStep2-weibo").hide();
		// 更新相应的Trigger图片
		$("img[name$='THIS_Task_img']").attr("src","images/email.png");
		
		//填充内容
		$("#emailRcvdBox").val(srcEmailBox);
		$("#emailRcvdPassWd").val(srcEmailPassWd);
		break;
	case 2:
		$("#modifyTaskStep2-weibo").slideDown("slow");
		$("#modifyTaskStep2-datetime").hide();
		$("#modifyTaskStep2-email").hide();
		// 更新相应的Trigger图片
		$("img[name$='THIS_Task_img']").attr("src","images/weibo.png");
		
		//填充内容
		$("#weiboRcvdIDID").val(listenWeiboID);
		$("#weiboRcvdIDPassWd").val(listenWeiboPassWd);
		$("#weiboRcvdCheckCon").val(listenWeiboCheckCon);
		break;
	default:
		break;
	}
	
	//根据THAT_TYPE 来进行内容初始化
	switch (THAT_TYPE) {
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
			
			//填充内容
			$("#emailSendSrcBox").val(srcEmailBox);
			$("#emailSendSrcPassWd").val(srcEmailPassWd);
		}
			
		$("#modifyTaskStep5-email").slideDown("slow");
		$("#modifyTaskStep5-weibo").hide();
		// 更新相应的Action图片
		$("img[name$='THAT_Task_img']").attr("src","images/email.png");
		
		//填充内容
		$("#emailSendDstBox").val(dstEmailBox);
		$("#emailSendSubject").val(EmailSubject);
		$("#emailSendContent").val(taskContent);
		break;
	case 4:
		$("#modifyTaskStep5-weibo").slideDown("slow");
		$("#modifyTaskStep5-email").hide();
		// 更新相应的Action图片
		$("img[name$='THAT_Task_img']").attr("src","images/weibo.png");
		
		//填充内容
		$("#weiboSendID").val(updateWeiboID);
		$("#weiboSendPassWd").val(updateWeiboPassWd);
		$("#weiboSendContent").val(taskContent);
		break;
	default:
		break;
	}
	
	//滚动到最后提交Task处
	$("html,body").animate({scrollTop:($("#modifyTaskStep6").offset().top - 50)},"slow");
	
	
}

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
	$("#modifyTaskStep1").slideDown("slow");
	$("#modifyTaskStep2-datetime").hide();
	$("#modifyTaskStep2-email").hide();
	$("#modifyTaskStep2-weibo").hide();
	$("#modifyTaskStep3").hide();
	$("#modifyTaskStep4").hide();
	$("#modifyTaskStep5-email").hide();
	$("#modifyTaskStep5-weibo").hide();
	$("#modifyTaskStep6").hide();
	$("#modifyTaskStep7").hide();
	$("html,body").animate({scrollTop:($("#modifyTaskStep1").offset().top - 50)},"slow");
}

/**
 * 选择THIS的Trigger的种类Type
 * @param thistype 0表示定时任务 1表示监听邮箱 2表示监听微博
 */
function chooseTHIS(thistype) {
	THIS_TYPE = thistype;
	switch (thistype) {
	case 0:
		$("#modifyTaskStep2-datetime").slideDown("slow");
		$("#modifyTaskStep2-email").hide();
		$("#modifyTaskStep2-weibo").hide();
		$("html,body").animate({scrollTop:($("#modifyTaskStep2").offset().top - 50)},"slow");
		// 更新相应的Trigger图片
		$("img[name$='THIS_Task_img']").attr("src","images/datetime.png");
		break;
	case 1:
		$("#modifyTaskStep2-email").slideDown("slow");
		$("#modifyTaskStep2-datetime").hide();
		$("#modifyTaskStep2-weibo").hide();
		$("html,body").animate({scrollTop:($("#modifyTaskStep2").offset().top - 50)},"slow");
		// 更新相应的Trigger图片
		$("img[name$='THIS_Task_img']").attr("src","images/email.png");
		break;
	case 2:
		$("#modifyTaskStep2-weibo").slideDown("slow");
		$("#modifyTaskStep2-datetime").hide();
		$("#modifyTaskStep2-email").hide();
		$("html,body").animate({scrollTop:($("#modifyTaskStep2").offset().top - 50)},"slow");
		// 更新相应的Trigger图片
		$("img[name$='THIS_Task_img']").attr("src","images/weibo.png");
		break;
	default:
		break;
	}
	$("#modifyTaskStep3").hide();
	$("#modifyTaskStep4").hide();
	$("#modifyTaskStep5-email").hide();
	$("#modifyTaskStep5-weibo").hide();
	$("#modifyTaskStep6").hide();
	$("#modifyTaskStep7").hide();
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
	$("#modifyTaskStep3").slideDown("slow");
	$("#modifyTaskStep4").hide();
	$("#modifyTaskStep5-email").hide();
	$("#modifyTaskStep5-weibo").hide();
	$("#modifyTaskStep6").hide();
	$("#modifyTaskStep7").hide();
	$("html,body").animate({scrollTop:($("#modifyTaskStep3").offset().top - 50)},"slow");
}

/**
 * 进行显示THAT的Action Type选择div
 */

function goToTHAT() {
	$("#modifyTaskStep4").slideDown("slow");
	$("#modifyTaskStep5-email").hide();
	$("#modifyTaskStep5-weibo").hide();
	$("#modifyTaskStep6").hide();
	$("#modifyTaskStep7").hide();
	$("html,body").animate({scrollTop:($("#modifyTaskStep4").offset().top - 50)},"slow");
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
			
		$("#modifyTaskStep5-email").slideDown("slow");
		$("#modifyTaskStep5-weibo").hide();
		$("html,body").animate({scrollTop:($("#modifyTaskStep5").offset().top - 50)},"slow");
		// 更新相应的Action图片
		$("img[name$='THAT_Task_img']").attr("src","images/email.png");
		break;
	case 4:
		$("#modifyTaskStep5-weibo").slideDown("slow");
		$("#modifyTaskStep5-email").hide();
		$("html,body").animate({scrollTop:($("#modifyTaskStep5").offset().top - 50)},"slow");
		// 更新相应的Action图片
		$("img[name$='THAT_Task_img']").attr("src","images/weibo.png");
		break;
	default:
		break;
	}
	$("#modifyTaskStep6").hide();
	$("#modifyTaskStep7").hide();
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
	$("#modifyTaskStep6").slideDown("slow");
	$("#modifyTaskStep7").slideDown("slow");
	$("html,body").animate({scrollTop:($("#modifyTaskStep6").offset().top - 50)},"slow");
}

/**
 * 创建Task
 * JSON通新建任务 但没有Builder
 */
function modifyTask(){
	//检测任务名
	if (!isNotEmpty("taskNameText", "TaskName")){
		$("#taskNameText").focus();
		return;
	}else {
		taskName=$("#taskNameText").val();
	}
	
	$.ajax({
		type:'post',
		data:{
			taskID:taskIDNow,
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
		url:"ModifyTask",
		success:function (canModifyTask){
			if(canModifyTask=="true"){
				alert("Modify Task successfully!");
				window.location.href="dashboard.jsp";
			}else{
				alert("Can't modify" + canModifyTask);
			}
		},
		error:function (canModifyTask){
			alert("Error in modify Task. error code:" + canModifyTask);
		}
	});
		
}

