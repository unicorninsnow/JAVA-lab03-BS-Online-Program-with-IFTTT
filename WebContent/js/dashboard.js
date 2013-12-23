/* 
 * user log in 
 * link with ifttt.html
 */
/*
 * Modernizr 2.6.1 (Custom Build) | MIT & BSD Build:
 * http://modernizr.com/download/#-canvas-history-input-shiv-cssclasses
 */
var usernameNow;



/*var allTaskListJsonText = '{"allTask" : ['+
'	{"taskBuildTime":"2013-12-10 16:09",'+
'		"updateWeiboId":"","dstMailBox":"",'+
'		"taskTHISType":"1",'+
'		"srcMailPassWd":"",'+
'			"taskName":"cfdsnvk",'+
'			"taskID":"1e88ad78-637b-46ba-8a51-271999a7025c",'+
'			"content":"nima","taskDeadTime":"2014-12-12",'+
'			"srcMailBox":"nima@qq.com",'+
'			"taskTHATType":"3",'+
'			"taskBuilder":"mzs",'+
'			"weiboCheckCon":"haha",'+
'			"mailSubject":"",'+
'			"updateWeiboPassWd":"123"},'+
'	{"taskBuildTime":"2013-12-10 16:09",'+
'		"updateWeiboId":"","dstMailBox":"",'+
'		"taskTHISType":"1",'+
'		"srcMailPassWd":"",'+
'			"taskName":"hrtfbdb",'+
'			"taskID":"1e88ad78-637b-46ba-8a51-271999a7025c",'+
'			"content":"nima","taskDeadTime":"2014-12-12",'+
'			"srcMailBox":"nima@qq.com",'+
'			"taskTHATType":"3",'+
'			"taskBuilder":"mzs",'+
'			"weiboCheckCon":"haha",'+
'			"mailSubject":"",'+
'			"updateWeiboPassWd":"123"},'+
'	{"taskBuildTime":"2013-12-10 16:09",'+
'		"updateWeiboId":"","dstMailBox":"",'+
'		"taskTHISType":"1",'+
'		"srcMailPassWd":"",'+
'			"taskName":"ymndfbg",'+
'			"taskID":"1e88ad78-637b-46ba-8a51-271999a7025c",'+
'			"content":"nima","taskDeadTime":"2014-12-12",'+
'			"srcMailBox":"nima@qq.com",'+
'			"taskTHATType":"3",'+
'			"taskBuilder":"mzs",'+
'			"weiboCheckCon":"haha",'+
'			"mailSubject":"",'+
'			"updateWeiboPassWd":"123"}'+
']}';*/
var allTaskListJson;

var allTaskListJsonText= [
{"taskBuildTime":"2013-12-10 16:09",
			"taskDeadTime":"2013-12-18 16:09",
			"updateWeiboId":"","dstMailBox":"",
			"taskTHISType":"0",
			"srcMailPassWd":"",
			"taskName":"betdf",
			"taskID":"1e88ad78-637b-46ba-8a51-271999a7025c",
			"content":"nima","taskDeadTime":"2014-12-12",
			"srcMailBox":"nima@qq.com",
			"taskTHATType":"3",
			"taskBuilder":"mzs",
			"weiboCheckCon":"haha",
			"mailSubject":"",
			"updateWeiboPassWd":"123"},
{"taskBuildTime":"2013-12-10 16:09",
			"updateWeiboId":"","dstMailBox":"",
			"taskTHISType":"1",
			"srcMailPassWd":"",
			"taskName":"rhgsf",
			"taskID":"1e88ad78-637b-46ba-8a51-271999a70243",
			"content":"nima","taskDeadTime":"2014-12-12",
			"srcMailBox":"nima@qq.com",
			"taskTHATType":"3",
			"taskBuilder":"mzs",
			"weiboCheckCon":"haha",
			"mailSubject":"",
			"updateWeiboPassWd":"123"},
{"taskBuildTime":"2013-12-10 16:09",
			"updateWeiboId":"","dstMailBox":"",
			"taskTHISType":"1",
			"srcMailPassWd":"",
			"taskName":"jythrbvcds",
			"taskID":"1e88ad78-637b-46ba-8a51-271999a70825",
			"content":"nima","taskDeadTime":"2014-12-12",
			"srcMailBox":"nima@qq.com",
			"taskTHATType":"4",
			"taskBuilder":"mzs",
			"weiboCheckCon":"haha",
			"mailSubject":"",
			"updateWeiboPassWd":"123"},
];


$(document).ready(function() {
	allTaskListJson = eval(allTaskListJsonText);
	$("#allTaskTable").tablesorter();
	updateAllTaskTable();

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
		url:'lookupTask',
		success:function (allTaskListJsonText){
			allTaskListJson = eval (allTaskListJsonText);
		},
		error:function (allTaskListJsonText){
			alert("Can't get Your task List.");
		}
	});
}

function checkTaskIsRunning(task_ID){
	$.ajax({
		type:'post',
		data:{
			taskID:task_ID,
		},
		url:'IsRunning',
		success:function (isRunning){
		},
		error:function (isRunning){
			alert("Error in get Task status");
		}
	});
	if(isRunning == true || isRunning == "true"){
		return true;
	}else {
		return false;
	}
}

function updateAllTaskTable() {
	if($("#allTaskTable_tbody tr").size() > 0){
		cleanTableBeforeUpdate("allTaskCheckBox");
	}
	var i=0;
	var taskID = 1;
	var taskTHISTypeName;
	var taskTHATTypeName;
	var thisContent;
	var thatContent;
	
	for(i=0;i<allTaskListJson.length;i++){
		//将Task的THIS和THAT的类型翻译 并填充有效THIS THAT信息
		switch (allTaskListJson[i].taskTHISType) {
		case "0":
			taskTHISTypeName = "DateTime";
			thisContent = "Trigger:<br/>&emsp;DateTime:&emsp;" + allTaskListJson[i].taskDeadTime;
			break;
		case "1":
			taskTHISTypeName = "Email";
			thisContent = "Trigger:<br/>&emsp;ListenEmailBox:&emsp;" + allTaskListJson[i].srcMailBox;
			break;
		case "2":
			taskTHISTypeName = "Weibo";
			thisContent = "Trigger:<br/>&emsp;ListenWeiboID:&emsp;" + allTaskListJson[i].listenWeiboID + "<br/>" +
					"&emsp;ListenWeiboCheckCon:&emsp;" + allTaskListJson[i].weiboCheckCon;
			break;

		default:
			taskTHISTypeName = "defaultTHIS";
			thisContent = "Trigger:<br/>&emsp;default";
			break;
		}
		switch (allTaskListJson[i].taskTHATType) {
		case "3":
			taskTHATTypeName = "Email";
			thatContent = "Action:<br/>&emsp;SendEmailBox:&emsp;" + allTaskListJson[i].srcMailBox + "<br/>" +
					"&emsp;DestinationEmailBox:&emsp;" + allTaskListJson[i].dstMailBox + "<br/>" +
					"&emsp;EmailSubject:&emsp;" + allTaskListJson[i].mailSubject + "<br/>" +
					"&emsp;EmailContent:&emsp;" + allTaskListJson[i].content;
			break;
		case "4":
			taskTHATTypeName = "Weibo";
			thatContent = "Action:<br/>&emsp;UpdateWeibo:&emsp;" + allTaskListJson[i].updateWeibo + "<br/>" +
					"&emsp;WeiboContent:&emsp;" + allTaskListJson[i].content;
			break;

		default:
			taskTHATTypeName = "defaultTHAT";
			thatContent = "Action:<br/>&emsp;default";
			break;
		}
		
		addTr("allTaskTable_tbody", i ,
				"<tr id='allTaskID_" + allTaskListJson[i].taskID + "' " + 
//					(true||checkTaskIsRunning(allTaskListJson[i].taskID)? "class='success'" :"")
					((i%2==0?true:false)? "class='success'" :"")
					+ ">" +
					"<td>"+ i + "</td>" +
					"<td style='text-align: left;'><div id='allTaskID_taskName_" + allTaskListJson[i].taskID +
						"' data-container='body' data-toggle='popover' data-container='body'" +
						"data-animation='true' data-placement='bottom' data-html='true'" +
						"data-delay='show:500,hide:100'>" + allTaskListJson[i].taskName + "</div></td>" +
					"<td>" + taskTHISTypeName + "</td>" +
					"<td>" + taskTHATTypeName + "</td>" +
					"<td>" +
						"<span class='glyphicon glyphicon-play' onclick='startTask(" + allTaskListJson[i].taskID + ")'></span> " +
						"<span class='glyphicon glyphicon-stop' onclick='stopTask(" + allTaskListJson[i].taskID + ")'></span> " +
						"<span class='glyphicon glyphicon-pencil' onclick='modifyTask(" + allTaskListJson[i].taskID + ")'></span> " +
						"<span class='glyphicon glyphicon-remove' onclick='deleteTask(" + allTaskListJson[i].taskID + ")'></span>" +
					"</td>" +
					"<td>" +
						"<input id='allTaskCkb" + allTaskListJson[i].taskID + "' name='allTaskCheckBox' type='checkbox'>" +
					"</td>" +
				"</tr>");
		

		
		
		
		$('#allTaskID_taskName_' + allTaskListJson[i].taskID).popover({
			content: allTaskListJson[i].taskName + "<br>&emsp;" +
				(true||checkTaskIsRunning(allTaskListJson[i].taskID)? "class='success'" :"") + "<br/>" + 
				thisContent + "<br/>" +
				thatContent});
			
		
		
		taskID++;
	}
	$("#allTaskTable").tablesorter();
	
	
}

function updateRunTaskTable() {
	if($("#runTaskTable_tbody tr").size() > 0){
		cleanTableBeforeUpdate("runTaskCheckBox");
	}
	var i=0;
	var taskID = 1;
	for(i=0;i<5;i++){
		
		addTr("runTaskTable_tbody", i ,"<tr>" +
				"<td>"+ taskID + "</td><td style='text-align: left;'><div id='runTaskName" + taskID +
				"' data-container='body' data-toggle='popover'>" + 
				"Tasks Test" + taskID +	"</div></td><td>" +
				"Datetime" + i+		"</td><td>" +
				"Weibo" + 			
				"</td><td><input id='allTaskCkb" + i + 
		"' name='runTaskCheckBox'  type='checkbox'></td><td>To Modify</td></tr>");
		
		$('#runTaskName' + taskID).popover({
			container:'body',
			animation:'true', 
			placement:'bottom',
			html:'true',
			container:"body", 
			delay:'show:500,hide:100',
			content:'Task Test' + taskID + '<br>datetime:&emsp;2013-12-23 12:23<br>Weibo:&emsp;unicorninsnow@live.cn<br>RUN'});
		
		taskID++;
	}
	
	 
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
