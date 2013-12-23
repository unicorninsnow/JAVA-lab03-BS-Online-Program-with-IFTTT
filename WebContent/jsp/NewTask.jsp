<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<% /*检测是否已登录*/ %>
<%@include file="isLogIn.jsp" %>
<%String userNameLogIn = (String)session.getAttribute("user"); %>
<!DOCTYPE HTML>
<html>
<head>
	<%@include file="PathInclude.jsp" %>
	<title>IFTTT - CreateNewTask</title>

	<link href="//cdnjs.bootcss.com/ajax/libs/twitter-bootstrap/2.2.2/css/bootstrap-responsive.min.css" rel="stylesheet">
	<link href="//cdnjs.bootcss.com/ajax/libs/prettify/r224/prettify.css" rel="stylesheet">
	<link href="../bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
	<link href="../bootstrap-3.0.0/examples/starter-template/starter-template.css" rel="stylesheet" media="screen">
	<link href="../css/NewTask.css" rel="stylesheet" media="screen">
	<script type="text/javascript" src="../js/NewTask.js"></script>
	<script src="../bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.min.js"></script>

</head>
<body>
	 <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
      	<div class="col-xs-14 col-md-9">
	        <div class="navbar-header">
	          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
	            <span class="sr-only">Toggle navigation</span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	          </button>
	          <a class="navbar-brand" href="dashboard.jsp">IFTTT</a>
	        </div>
	        <div class="navbar-collapse collapse">
	          <ul class="nav navbar-nav">
	            <li class="active"><a href="#">Home</a></li>
	            <li><a href="#about">About</a></li>
	            <li><a href="#contact">Contact</a></li>
	            <li class="dropdown">
	              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
	              <ul class="dropdown-menu">
	                <li><a href="#">Action</a></li>
	                <li><a href="#">Another action</a></li>
	                <li><a href="#">Something else here</a></li>
	                <li class="divider"></li>
	                <li class="dropdown-header">Nav header</li>
	                <li><a  href="#">Separated link</a></li>
	                <li><a href="#">One more separated link</a></li>
	              </ul>
	            </li>
	          </ul>
	        </div><!--/.nav-collapse -->
	       </div>
		<div class="col-xs-4 col-sm-3 userInfoButton">
			<div class="dropdown userInfoButtonDropdownMenu">
				<a id="userInfoDropdownMenu" class="userInfoButtonDropdown" data-toggle="dropdown" data-target="#"
					href="/page.html"><%=userNameLogIn%>
				</a>
				<ul class="dropdown-menu" role="menu" aria-labelledby="userInfoDropdownMenu">
					<li role="presentation"><a role="menuitem" tabindex="-1"
						href="#">Action</a></li>
					<li role="presentation"><a role="menuitem" tabindex="-1"
						href="#">Another action</a></li>
					<li role="presentation"><a role="menuitem" tabindex="-1"
						href="#">Something else here</a></li>
					<li role="presentation" class="divider"></li>
					<li role="presentation"><a role="menuitem" tabindex="-1"
						href="#">Separated link</a></li>
					
				</ul>


			</div>
			
			<!-- 
			<div class="dropdown userInfoButtonDropdownMenu">
				<button class="btn dropdown-toggle sr-only" type="button"
					id="dropdownMenu1" data-toggle="dropdown">
					Dropdown <span class="caret"></span>
				</button>
				<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
				</ul>
			</div> -->



			<script>
				$('.dropdown-toggle').dropdown();
			</script>
		</div>
      </div>
	</div>
    
	<div class="create-Task-info">
		<div class="container create-Task-info_title">
			<h1>New Task!</h1>
		</div>
		<br/>
		<hr/>
	</div>

	<div class="create-Task">
	
		<!-- createTask Step0 显示 提示选择信息 -->
		<div class="create-Task-Step0" id="createTaskStep0">
			if<span style="display: none"></span><a href="#" 
				onclick="goToTHIS();">THIS</a>then<span style="display: none"></span>THAT<span
				style="display: none"></span>
		</div>

		<!-- createTask Step1  选择THIS Trigger -->
		<!-- 0表示定时  1表示email  2表示weibo -->
		<div class="create-Task-Step1" id="createTaskStep1">
			<hr/>
			<span>Choose THIS Task:</span>
			<div>
				<a class="THIS_img" id="THIS_Task_datetime" onclick="chooseTHIS(0)">
					<img src="../images/datetime.png">
				</a>
				<a class="THIS_img" id="THIS_Task_email" onclick="chooseTHIS(1)">
					<img src="../images/email.png">
				</a>
				<a class="THIS_img" id="THIS_Task_weibo" onclick="chooseTHIS(2)">
					<img src="../images/weibo.png">
				</a>
			</div>
		</div>
		
		
		<!-- createTask Step2  进行Trigger详细信息的填写 -->
		<div class="create-Task-Step2" id="createTaskStep2">
		
			<!-- 当Trigger为定时任务时 显示的Trigger详细输入体 -->
			<div class="create-Task-Step2-datetime" id="createTaskStep2-datetime">
				<hr/>
				<div class="container">
					Date and Time to do Task:
				</div>
	
				<div class="container">
					<div class="input-group date form_datetime col-md-4 col-md-offset-4" id="datetimepicker"
						style="padding: 5px;" data-link-field="dtp_input">
						<input class="form-control" id="datetimeText" size="16" type="text" value="" readonly="" > 
						<span class="input-group-addon"><i class="glyphicon glyphicon-remove"></i></span> 
						<span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
					</div>
					<input type="hidden" id="dtp_input" value="">
					<script type="text/javascript" src="bootstrap-datetimepicker-master/js/locales/bootstrap-datetimepicker.fr.js" charset="UTF-8"></script>
					<script type="text/javascript">
					   /*  $('.form_datetime').datetimepicker({
					        //language:  'fr',
					        weekStart: 1,
					        todayBtn:  1,
							autoclose: 1,
							todayHighlight: 1,
							startView: 2,
							forceParse: 0,
					        showMeridian: 1
					    }); */
						$(".form_datetime").datetimepicker({
							format : "yyyy-mm-dd hh:ii",
							autoclose : true,
							todayBtn : true,
							startDate : "1900-01-01 10:00",
							minuteStep : 5
						});
					</script>
					<div class="col-md-4 col-md-offset-4" style="padding-top: 5px;text-align:center;">
						<button type="button" class="btn btn-lg btn-success button-task-confirm" 
							onclick="createTrigger()">Create Trigger</button>
					</div>	
						
				</div>
			</div>
			
			<!-- 当Trigger为监听邮箱时 显示的Trigger详细输入体 -->
			<!-- 此处如果Action也采用邮件则默认用监听的邮箱发邮件 即不显示发时的源邮箱 -->
			<div class="create-Task-Step2-email" id="createTaskStep2-email">
				<hr/>
				<div class="container">
					Email to trigger when Receiving:
				</div>
				<div class="container">
					<form class="form-horizontal">
						<input class="input-xlarge" id="emailRcvdBox" size="16" type="text" placeholder="RcvdMailBox"><br/>
						<input class="input-xlarge" id="emailRcvdPassWd" size="16" type="password" placeholder="RcvdMailPassword"> 
						
					</form>
					<div class="col-md-4 col-md-offset-4" style="padding-top: 5px;text-align:center;">
						<button type="button" class="btn btn-lg btn-success button-task-confirm" 
							onclick="createTrigger()">Create Trigger</button>
					</div>
				</div>
			</div>
			
			<!-- 当Trigger为监听微博时 显示的Trigger详细输入体 -->
			<div class="create-Task-Step2-weibo" id="createTaskStep2-weibo">
				<hr/>
				<div class="container">
					Weibo to trigger when Receiving:
				</div>
				<div class="container">
					<form class="form-horizontal">
						<input class="input-xlarge" id="weiboRcvdIDID" size="16" type="text" placeholder="RcvdWeiboID"><br/>
						<input class="input-xlarge" id="weiboRcvdIDPassWd" size="16" type="password" placeholder="RcvdWeiboPassword"><br/>
						<input class="input-xlarge" id="weiboRcvdCheckCon" size="16" type="password" placeholder="RcvdWeiboCheckContent"> 
						
					</form>
					<div class="col-md-4 col-md-offset-4" style="padding-top: 5px;text-align:center;">
						<button type="button" class="btn btn-lg btn-success button-task-confirm" 
							onclick="createTrigger()">Create Trigger</button>
					</div>	
				</div>
			</div>
		</div>
		
		
		<!-- createTask Step3 显示已选好的THIS Trigger  并提示用户前往选择THAT的div -->
		<div class="create-Task-Step3" id="createTaskStep3">
			<hr/>
			if<span style="display: none"></span><a class="THIS_img" id="THIS_Task">
					<img name="THIS_Task_img" src="">
				</a>then<span style="display: none"></span><a href="#"
				onclick="goToTHAT();">THAT</a>
		</div>
		
		
		<!-- createTask Step4 选择THAT Action -->
		<div class="create-Task-Step4" id="createTaskStep4">
			<hr/>
			<span>Choose THAT Task:</span>
			<div>
				<a class="THIS_img" id="THAT_Task_email" onclick="chooseTHAT(3)">
					<img src="../images/email.png">
				</a>
				<a class="THIS_img" id="THAT_Task_weibo" onclick="chooseTHAT(4)">
					<img src="../images/weibo.png">
				</a>
			</div>
		</div>
		
		
		<!-- createTask Step5  进行Action详细信息的填写 -->
		<div class="create-Task-Step5" id="createTaskStep5">
		
			<!-- 当Action为发邮件时 显示的Action详细输入体 -->
			<div class="create-Task-Step5-email" id="createTaskStep5-email">
				<hr/>
				<div class="container">
					Send Email:
				</div>
				<div class="container">
					<form class="form-horizontal">
						<div style="display: block; margin:0px auto;vertical-align: middle;">
							<input class="input-xlarge " 
								id="emailSendDstBox" size="16" type="text" placeholder="SendDstMailBox" ></input>
						</div>
						<div style="display: block; margin:0px auto;vertical-align: middle;">
							<input class="input-xlarge " style="display: block;"
								id="emailSendSrcBox" size="16" type="text" placeholder="SendSrcMailBox"></input>
						</div>	
						<div style="display: block; margin:0px auto;vertical-align: middle;">
							<input class="input-xlarge " style="display: block;"
								id="emailSendSrcPassWd" size="16" type="password" placeholder="SendSrcMailPassword" ></input>
						</div>
						<div style="display: block; margin:0px auto;vertical-align: middle;">
							<input class="input-xlarge " style="display: block;"
								id="emailSendSubject" size="16" type="text" placeholder="SendSrcMailSubject" ></input>
						</div>
						
						<textarea id="emailSendContent" rows="4" cols="20" placeholder="SendWeiboPassword"></textarea>
					</form>
					<div class="col-md-4 col-md-offset-4" style="padding-top: 5px;text-align:center;">
						<button type="button" class="btn btn-lg btn-success button-task-confirm" 
							onclick="createAction()">Create Action</button>
					</div>	
				</div>
			</div>
			
			<!-- 当Action为发微博时 显示的Action详细输入体 -->
			<div class="create-Task-Step5-weibo" id="createTaskStep5-weibo">
				<hr/>
				<div class="container">
					Update Weibo:
				</div>
				<div class="container">
					<form class="form-horizontal">
						<input class="input-xlarge" id="weiboSendID" size="16" type="text" placeholder="SendWeiboID"><br/>
						<input class="input-xlarge" id="weiboSendPassWd" size="16" type="password" placeholder="SendWeiboPassword"> <br/>
						<textarea id="weiboSendContent" rows="4" cols="20" placeholder="SendWeiboPassword"></textarea>
					</form>
					<div class="col-md-4 col-md-offset-4" style="padding-top: 5px;text-align:center;">
						<button type="button" class="btn btn-lg btn-success button-task-confirm" 
							onclick="createAction()">Create Action</button>
					</div>
				</div>
			</div>
		</div>
		
		
		<!-- createTask Step6   显示已选好的Trigger 和 Action -->
		<div class="create-Task-Step6" id="createTaskStep6">
			<hr/>
			if<span style="display: none"></span><a class="THIS_img" id="THIS_Task">
					<img name="THIS_Task_img" src="">
				</a>then<span style="display: none"></span><a class="THAT_img" id="THAT_Task">
					<img name="THAT_Task_img" src=""></a>
		</div>
		
		<div class="create-Task-Step7" id="createTaskStep7">
			<hr/>
			<div class="container" style="text-align: center;font-size: 40px;">
				Give the Task a Name:<br/>
				<input class="input-xlarge" id="taskNameText" size="16" type="text" placeholder="TaskName" style="font-size: 30px;">
				<div class="col-md-4 col-md-offset-4" style="padding-top: 5px;text-align:center;">
					<button type="button" class="btn btn-lg btn-success button-task-confirm" 
						onclick="createNewTask()">Create Task</button>
				</div>
			</div>
		</div>
		
	</div>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>


</body>
</html>