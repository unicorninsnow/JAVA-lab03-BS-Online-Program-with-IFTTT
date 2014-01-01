/* 
 * user log in 
 * link with ifttt.html
 */
/*
 * Modernizr 2.6.1 (Custom Build) | MIT & BSD Build:
 * http://modernizr.com/download/#-canvas-history-input-shiv-cssclasses
 */
var usernameNow;

var allTaskListJson;

var taskAllNum = 0;
var taskinRunNum = 0;
var taskinRunListBaseOnAllTask = new Array();

/*var allTaskListJsonText= [
{"taskID":"1e88ad78-637b-46ba-8a51-271999a7025c",
			"taskName":"MyTask testtest",
			"taskBuilder":"Unicorninsnow",
			"taskBuildTime":"2013-12-10 16:09",
			"taskTHISType":"0",
			"taskTHATType":"4",
			"taskDeadTime":"2013-12-18 16:09",
			"srcMailBox":"testSrcMail@gmail.com",
			"srcMailPassWd":"mailpw",
			"weiboCheckCon":"haha",
			"dstMailBox":"dstMailBox@live.cn",
			"mailSubject":"testmailSubject",
			"listenWeiboID":"testlistenWeiboID",
			"updateWeiboPassWd":"lWeibopw",
			"updateWeiboId":"testupdateWeiboId",
			"updateWeiboPassWd":"uWeibopw",
			"content":"It's test content"},
{"taskID":"1e88ad78-637b-46ba-8a51-271999a7wvds",
			"taskName":"MyTask anothertest",
			"taskBuilder":"Unicorninsnow",
			"taskBuildTime":"2013-12-11 16:09",
			"taskTHISType":"1",
			"taskTHATType":"4",
			"taskDeadTime":"2013-12-18 16:09",
			"srcMailBox":"testSrcMail@gmail.com",
			"srcMailPassWd":"mailpw",
			"weiboCheckCon":"haha",
			"dstMailBox":"dstMailBox@live.cn",
			"mailSubject":"testmailSubject",
			"listenWeiboID":"testlistenWeiboID",
			"updateWeiboPassWd":"lWeibopw",
			"updateWeiboId":"testupdateWeiboId",
			"updateWeiboPassWd":"uWeibopw",
			"content":"It's test content"},
{"taskID":"1e88ad78-637b-46ba-8a51-27nhfgbfdfdv",
			"taskName":"MyTask again",
			"taskBuilder":"Unicorninsnow",
			"taskBuildTime":"2013-12-12 16:09",
			"taskTHISType":"2",
			"taskTHATType":"3",
			"taskDeadTime":"2013-12-18 16:09",
			"srcMailBox":"testSrcMail@gmail.com",
			"srcMailPassWd":"mailpw",
			"weiboCheckCon":"haha",
			"dstMailBox":"dstMailBox@live.cn",
			"mailSubject":"Subject",
			"listenWeiboID":"testlistenWeiboID",
			"updateWeiboPassWd":"lWeibopw",
			"updateWeiboId":"testupdateWeiboId",
			"updateWeiboPassWd":"uWeibopw",
			"content":"It's test content"},
];*/



$(document).ready(function() {
	usernameNow=$("#userInfoDropdownMenu").html();
	
	getAllTaskList();
//	allTaskListJson = eval(allTaskListJsonText);
//	alert("in the ready.");
//	alert("in ready alltasknum from json:" + allTaskListJson.length);
//	alert("in ready alltasknum from json:");
	updateAllTaskTable();
	
	$("#allTaskTable").tablesorter();
	
	$("table").tablecloth();
	
	$("table").tablecloth({
		  theme: "paper",
		  bordered: false,
		  condensed: true,
		  striped: false,
		  sortable: true,
		  clean: false,
		  cleanElements: "th td",
		  customClass: "table-hover"
		});

});

function isLogIn() {
	/*	$.ajax({
		type:'post',
		data:{
		},
		url:'servletname',
		success:function (a){
				
			if(a != "true"){
				location.
			}else{
				
			}
		},
		error:function (a){
			
		}
	}*/
	/**/
}
	

function getAllTaskList() {
	$.ajax({
		type:'post',
		data:{
			name:usernameNow,
		},
		url:'LookupTask',
		async:false,
		success:function (allTaskListJsonText){
			
			allTaskListJson = eval (allTaskListJsonText);
			return;
		},
		error:function (allTaskListJsonText){
			alert("Can't get Your task List.");
		}
	});
}

function checkTaskIsRunning(task_ID){
	var returnrun = false;
	$.ajax({
		type:'post',
		data:{
			taskID:task_ID,
		},
		url:'IsRunning',
		async:false,
		success:function (isRunning){
//			return Boolean(isRunning);
			if(isRunning ==="true"){
//				alert(task_ID + " in check run is not run." + isRunning);
				returnrun = true;
			}
			else{
//				alert(task_ID + " in check run is run." + isRunning);
				returnrun = false;
			}
		},
		error:function (isRunning){
			alert("Error in get Task status");
//			return false;
		}
	});
	return returnrun;
}

function updateAllTaskTable() {
	if($("#allTaskTable_tbody tr").size() > 0){
		cleanTableBeforeUpdate("allTaskCheckBox");
	}
	var i=0;
	var taskTHISTypeName;
	var taskTHATTypeName;
	var thisContent;
	var thatContent;
	var taskDetail;
	
	taskinRunNum = 0;
	
	taskAllNum = allTaskListJson.length;
	alert("alltask list json length from serlvet:" + taskAllNum);
	$("#TaskAllNum").html(taskAllNum);
	
	for(i=0;i<allTaskListJson.length;i++){
		var isTheTaskRun = checkTaskIsRunning(allTaskListJson[i].taskID);
		alert(allTaskListJson[i].taskID + " in update status: " + isTheTaskRun);
//		if(isTheTaskRun) alert("run in update" + allTaskListJson[i].taskID);
//		else alert("not run in update" + allTaskListJson[i].taskID);
//		var isTheTaskRun = (i%2==0?true:false);
		
		//将Task的THIS和THAT的类型翻译 并填充有效THIS THAT信息
		switch (Number(allTaskListJson[i].taskTHISType)) {
		case 0:
			taskTHISTypeName = "DateTime";
			thisContent = "Trigger:<br/>&emsp;DateTime:&emsp;" + allTaskListJson[i].taskDeadTime;
			break;
		case 1:
			taskTHISTypeName = "Email";
			thisContent = "Trigger:<br/>&emsp;ListenEmailBox:&emsp;" + allTaskListJson[i].srcMailBox;
			break;
		case 2:
			taskTHISTypeName = "Weibo";
			thisContent = "Trigger:<br/>&emsp;ListenWeiboID:&emsp;" + allTaskListJson[i].listenWeiboID + "<br/>" +
					"&emsp;ListenWeiboCheckCon:&emsp;" + allTaskListJson[i].weiboCheckCon;
			break;

		default:
			taskTHISTypeName = "defaultTHIS";
			thisContent = "Trigger:<br/>&emsp;default";
			break;
		}
		switch (Number(allTaskListJson[i].taskTHATType)) {
		case 3:
			taskTHATTypeName = "Email";
			thatContent = "Action:<br/>&emsp;SendEmailBox:&emsp;" + allTaskListJson[i].srcMailBox + "<br/>" +
					"&emsp;DestinationEmailBox:&emsp;" + allTaskListJson[i].dstMailBox + "<br/>" +
					"&emsp;EmailSubject:&emsp;" + allTaskListJson[i].mailSubject + "<br/>" +
					"&emsp;EmailContent:&emsp;" + allTaskListJson[i].content;
			break;
		case 4:
			taskTHATTypeName = "Weibo";
			thatContent = "Action:<br/>&emsp;UpdateWeibo:&emsp;" + allTaskListJson[i].updateWeiboId + "<br/>" +
					"&emsp;WeiboContent:&emsp;" + allTaskListJson[i].content;
			break;

		default:
			taskTHATTypeName = "defaultTHAT";
			thatContent = "Action:<br/>&emsp;default";
			break;
		}
		
		
		taskDetail = allTaskListJson[i].taskName + "<br>&emsp;" +
						(isTheTaskRun? "Run" :"Stop") + "<br/>" + 
						thisContent + "<br/>" +
						thatContent;
		
		addTr("allTaskTable_tbody", i ,
				"<tr style='text-align: center;' id='allTaskID_" + allTaskListJson[i].taskID + "' " + 
					(isTheTaskRun? "class='success'" :"")
					+ ">" +
					"<td style='text-align: center;'>"+ i + "</td>" +
					"<td style='text-align: center;'>" +
					"<input id='allTaskCkb" + allTaskListJson[i].taskID + "' name='allTaskCheckBox' type='checkbox'>" +
					"</td>" +
					"<td style='text-align: left;'><div id='allTaskID_taskName_" + allTaskListJson[i].taskID +
						"' data-container='body' data-toggle='popover' data-container='body'" +
						"data-animation='true' data-placement='bottom' data-html='true'" +
						"data-delay='show:500,hide:100'" +
//						" data-content='" + taskDetail + "'" +
								">" + 
						allTaskListJson[i].taskName + "</div></td>" +
					"<td style='text-align: center;'>" + taskTHISTypeName + "</td>" +
					"<td style='text-align: center;'>" + taskTHATTypeName + "</td>" +
					"<td style='text-align: center;'>" +
						"<span class='glyphicon glyphicon-play' style='color: green;' onclick='startTask(\"" + allTaskListJson[i].taskID + "\")'></span> " +
						"<span class='glyphicon glyphicon-stop' style='color: gray;' onclick='stopTask(\"" + allTaskListJson[i].taskID + "\")'></span> " +
						"<a class='glyphicon glyphicon-pencil' href='ModifyTask.jsp?task_ID=" + allTaskListJson[i].taskID + "'></a> " +
						"<span class='glyphicon glyphicon-remove' style='color: red;' onclick='deleteTask(\"" + allTaskListJson[i].taskID + "\")'></span>" +
					"</td>" +
				"</tr>");
		
		$('#allTaskID_taskName_' + allTaskListJson[i].taskID).popover({
			content: taskDetail});
		
//		$('#allTaskID_taskName_' + allTaskListJson[i].taskID).popover("shown");
		
		
		if(isTheTaskRun){
			taskinRunListBaseOnAllTask[taskinRunNum] = allTaskListJson[i];
			taskinRunNum++;
		}
		
		
	}
	$("#TaskRunNum").html(taskinRunNum);
	
	
	$("#allTaskTable").tablesorter();
	
	
}

function updateRunTaskTable() {
	if($("#runTaskTable_tbody tr").size() > 0){
		cleanTableBeforeUpdate("runTaskCheckBox");
	}
	var i=0;
	var taskTHISTypeName;
	var taskTHATTypeName;
	var thisContent;
	var thatContent;
	var taskDetail;

	for(i=0;i<taskinRunListBaseOnAllTask.length;i++){
		
		//将Task的THIS和THAT的类型翻译 并填充有效THIS THAT信息
		switch (Number(taskinRunListBaseOnAllTask[i].taskTHISType)) {
		case 0:
			taskTHISTypeName = "DateTime";
			thisContent = "Trigger:<br/>&emsp;DateTime:&emsp;" + taskinRunListBaseOnAllTask[i].taskDeadTime;
			break;
		case 1:
			taskTHISTypeName = "Email";
			thisContent = "Trigger:<br/>&emsp;ListenEmailBox:&emsp;" + taskinRunListBaseOnAllTask[i].srcMailBox;
			break;
		case 2:
			taskTHISTypeName = "Weibo";
			thisContent = "Trigger:<br/>&emsp;ListenWeiboID:&emsp;" + taskinRunListBaseOnAllTask[i].listenWeiboID + "<br/>" +
					"&emsp;ListenWeiboCheckCon:&emsp;" + taskinRunListBaseOnAllTask[i].weiboCheckCon;
			break;

		default:
			taskTHISTypeName = "defaultTHIS";
			thisContent = "Trigger:<br/>&emsp;default";
			break;
		}
		switch (Number(taskinRunListBaseOnAllTask[i].taskTHATType)) {
		case 3:
			taskTHATTypeName = "Email";
			thatContent = "Action:<br/>&emsp;SendEmailBox:&emsp;" + taskinRunListBaseOnAllTask[i].srcMailBox + "<br/>" +
					"&emsp;DestinationEmailBox:&emsp;" + taskinRunListBaseOnAllTask[i].dstMailBox + "<br/>" +
					"&emsp;EmailSubject:&emsp;" + taskinRunListBaseOnAllTask[i].mailSubject + "<br/>" +
					"&emsp;EmailContent:&emsp;" + taskinRunListBaseOnAllTask[i].content;
			break;
		case 4:
			taskTHATTypeName = "Weibo";
			thatContent = "Action:<br/>&emsp;UpdateWeibo:&emsp;" + taskinRunListBaseOnAllTask[i].updateWeiboId + "<br/>" +
					"&emsp;WeiboContent:&emsp;" + taskinRunListBaseOnAllTask[i].content;
			break;

		default:
			taskTHATTypeName = "defaultTHAT";
			thatContent = "Action:<br/>&emsp;default";
			break;
		}
		
		taskDetail = taskinRunListBaseOnAllTask[i].taskName + "<br>&emsp;" +
						"Run" + "<br/>" + 
						thisContent + "<br/>" +
						thatContent;	
		
		addTr("runTaskTable_tbody", i ,
				"<tr style='text-align: center;' id='runTaskID_" + taskinRunListBaseOnAllTask[i].taskID + "'class='success'>" +
					"<td style='text-align: center;'>"+ i + "</td>" +
					"<td style='text-align: center;'>" +
					"<input id='runTaskCkb" + taskinRunListBaseOnAllTask[i].taskID + "' name='runTaskCheckBox' type='checkbox'>" +
					"</td>" +
					"<td style='text-align: left;'><div id='runTaskID_taskName_" + taskinRunListBaseOnAllTask[i].taskID +
						"' data-container='body' data-toggle='popover' data-container='body'" +
						"data-animation='true' data-placement='bottom' data-html='true'" +
						"data-delay='show:500,hide:100'>" + taskinRunListBaseOnAllTask[i].taskName + "</div></td>" +
					"<td style='text-align: center;'>" + taskTHISTypeName + "</td>" +
					"<td style='text-align: center;'>" + taskTHATTypeName + "</td>" +
					"<td style='text-align: center;'>" +
						"<span class='glyphicon glyphicon-play' style='color: green;' onclick='startTask(" + taskinRunListBaseOnAllTask[i].taskID + ")'></span> " +
						"<span class='glyphicon glyphicon-stop' onclick='stopTask(" + taskinRunListBaseOnAllTask[i].taskID + ")'></span> " +
						"<a class='glyphicon glyphicon-pencil' href='ModifyTask.jsp?task_ID=" + taskinRunListBaseOnAllTask[i].taskID + "'></a> " +
						"<span class='glyphicon glyphicon-remove' style='color: red;' onclick='deleteTask(" + taskinRunListBaseOnAllTask[i].taskID + ")'></span>" +
					"</td>" +
				"</tr>");
		
		$('#runTaskID_taskName_' + taskinRunListBaseOnAllTask[i].taskID).popover({
			content: taskDetail});
		
	}
	$("#runTaskTable").tablesorter();
	
	
}



/**
 * 为table指定行添加一行
 *
 * tab 表id
 * row 行数，如：0->第0行 1->第1行 -2->倒数第2行 -1->最后1行
 * trHtml 添加行的html代码
 *
 */
function addTr(tab, row, trHtml){
   //获取table最后一行 $("#tab tr:last")
   //获取table第一行 $("#tab tr").eq(0)
   //获取table倒数第二行 $("#tab tr").eq(-2)
	var $tr;
	if(row == 0){
		$tr = $("#"+tab);
		$tr.append(trHtml);
		return;
	}else if(row > 0){
		$tr = $("#"+tab+" tr").eq(row-1);
	}else{
		$tr = $("#"+tab+" tr").eq(row);		
	}
	if($tr.size()==0){
		alert("指定的table id或行数不存在！");
		return;
	}
	$tr.after(trHtml);
	return;
}


function cleanTableBeforeUpdate(ckb){

    //全部删除
    var ckbs=$("input[name="+ckb+"]:checkbox");
    if(ckbs.size()==0){
       alert("要删除指定行，需选中要删除的行！");
       return;
    }
    ckbs.each(function(){
    	$(this).parent().parent().remove();
    });
 }

function deleteChosenTasks(ckb){
	//获取选中的复选框，然后循环遍历删除
    var ckbs=$("input[name="+ckb+"]:checked");
    if(ckbs.size()==0){
       alert("要删除指定行，需选中要删除的行！");
       return;
    }
    ckbs.each(function(){
    	$(this).parent().parent().remove();
    	//然后删数据库
    	
    	
    });
}


/**
 * 全选
 * 
 * allCkb 全选复选框的id
 * items 复选框的name
 */
function allCheck(allCkb, items){
 $("#"+allCkb).click(function(){
    $('[name='+items+']:checkbox').attr("checked", this.checked );
 });
}


function getJSONIdByTaskID(task_ID) {
	var i = 0;
	for(i = 0;i < taskAllNum; i++){
		if(allTaskListJson[i].taskID == task_ID) return i;
	}
	return -1;
}

function getTaskThisContent(jsonI){
	var thisContent;
	switch (Number(allTaskListJson[jsonI].taskTHISType)) {
	case 0:
		taskTHISTypeName = "DateTime";
		thisContent = "Trigger:<br/>&emsp;DateTime:&emsp;" + allTaskListJson[jsonI].taskDeadTime;
		break;
	case 1:
		taskTHISTypeName = "Email";
		thisContent = "Trigger:<br/>&emsp;ListenEmailBox:&emsp;" + allTaskListJson[jsonI].srcMailBox;
		break;
	case 2:
		taskTHISTypeName = "Weibo";
		thisContent = "Trigger:<br/>&emsp;ListenWeiboID:&emsp;" + allTaskListJson[jsonI].listenWeiboID + "<br/>" +
				"&emsp;ListenWeiboCheckCon:&emsp;" + allTaskListJson[jsonI].weiboCheckCon;
		break;

	default:
		taskTHISTypeName = "defaultTHIS";
		thisContent = "Trigger:<br/>&emsp;default";
		break;
	}
	return thisContent;
}

function getTaskThatContent(jsonI){
	var thatContent;
	switch (Number(allTaskListJson[jsonI].taskTHATType)) {
	case 3:
		taskTHATTypeName = "Email";
		thatContent = "Action:<br/>&emsp;SendEmailBox:&emsp;" + allTaskListJson[jsonI].srcMailBox + "<br/>" +
				"&emsp;DestinationEmailBox:&emsp;" + allTaskListJson[jsonI].dstMailBox + "<br/>" +
				"&emsp;EmailSubject:&emsp;" + allTaskListJson[jsonI].mailSubject + "<br/>" +
				"&emsp;EmailContent:&emsp;" + allTaskListJson[jsonI].content;
		break;
	case 4:
		taskTHATTypeName = "Weibo";
		thatContent = "Action:<br/>&emsp;UpdateWeibo:&emsp;" + allTaskListJson[jsonI].updateWeiboId + "<br/>" +
				"&emsp;WeiboContent:&emsp;" + allTaskListJson[jsonI].content;
		break;

	default:
		taskTHATTypeName = "defaultTHAT";
		thatContent = "Action:<br/>&emsp;default";
		break;
	}
	return thatContent;
}

/**
 * 开始某个任务
 */
function startTask(task_ID) {
	$.ajax({
		type:'post',
		data:{
			name:usernameNow,
			taskID:task_ID,
		},
		url:'StartTask',
//		async:false,
		success:function (isFinished){
			updateAllTaskTable();
			updateRunTaskTable();
		
		},
		error:function (isFinished){
			alert("Error in start Task");
		}
	});
	$("#allTaskID_" + task_ID).attr("class","success");
	
//	var allTaskJSONId = getJSONIdByTaskID(task_ID);
//	var thisContentToChange = getTaskThisContent(allTaskJSONId);
//	var thatContentToChange = getTaskThatContent(allTaskJSONId);
//	var taskDetailToChange = allTaskListJson[allTaskJSONId].taskName + "<br>&emsp;Run<br/>" + 
//					thisContentToChange + "<br/>" +
//					thatContentToChange;
////	alert(taskDetailToChange);
////	$('#allTaskID_taskName_' + task_ID).popover("destory");
//	$('#allTaskID_taskName_' + task_ID).popover({content: taskDetailToChange});

}

/**
 * 结束某个任务
 */
function stopTask(task_ID) {
	$.ajax({
		type:'post',
		data:{
			name:usernameNow,
			taskID:task_ID,
		},
		url:'StopTask',
		success:function (isFinished){
			updateAllTaskTable();
			updateRunTaskTable();
		
		},
		error:function (isFinished){
			alert("Error in stop Task");
		}
	});
//	$("#allTaskID_" + task_ID).attr("class","");
//	var allTaskJSONId = getJSONIdByTaskID(task_ID);
//	var thisContentToChange = getTaskThisContent(allTaskJSONId);
//	var thatContentToChange = getTaskThatContent(allTaskJSONId);
//	var taskDetailToChange = allTaskListJson[allTaskJSONId].taskName + "<br>&emsp;Stop<br/>" + 
//					thisContentToChange + "<br/>" +
//					thatContentToChange;
////	alert(taskDetailToChange);
////	$('#allTaskID_taskName_' + task_ID).popover("destory");
//	$('#allTaskID_taskName_' + task_ID).popover({content: taskDetailToChange});
//	$("#runTaskID_" + task_ID).remove();
}

/**
 * 删除某个任务
 */
function deleteTask(task_ID) {
	$.ajax({
		type:'post',
		data:{
			taskID:task_ID,
		},
		url:'DeleteTask',
		success:function (isFinished){
			updateAllTaskTable();
			updateRunTaskTable();
		},
		error:function (isFinished){
			alert("Error in stop Task");
		}
	});
//	$("#allTaskID_" + task_ID).remove();
//	$("#runTaskID_" + task_ID).remove();
}
