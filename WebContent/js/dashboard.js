/* 
 * user log in 
 * link with ifttt.html
 */
/*
 * Modernizr 2.6.1 (Custom Build) | MIT & BSD Build:
 * http://modernizr.com/download/#-canvas-history-input-shiv-cssclasses
 */
$(document).ready(function(){
	/**/
	updateAllTaskTable();
		
		
});

function getAllTaskList() {
}

function updateAllTaskTable() {
	if($("#allTaskTable_tbody tr").size() > 0){
		cleanTableBeforeUpdate("allTaskCheckBox");
	}
	var i=0;
	var taskID = 1;
	for(i=0;i<10;i++){
		
		addTr("allTaskTable_tbody", i ,"<tr>" +
						"<td>"+ taskID + "</td><td style='text-align: left;'><div id='allTaskName" + taskID +
						"' data-container='body' data-toggle='popover'>" + 
						"Tasks Test" + taskID +	"</div></td><td>" +
						"Datetime" + i+		"</td><td>" +
						"Weibo" + 			
						"</td><td><input id='allTaskCkb" + i + 
						"' name='allTaskCheckBox'  type='checkbox'></td><td>To Modify</td></tr>");
		
		$('#allTaskName' + taskID).popover({
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
