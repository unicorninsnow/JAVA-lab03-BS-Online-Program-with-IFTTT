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
                <li><a href="#">Separated link</a></li>
                <li><a href="#">One more separated link</a></li>
              </ul>
            </li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>
    
	<div class="create-Task-info">
		<div class="container create-Task-info_title">
			<h1>New Task!</h1>
		</div>
		<br>
		<hr>
	</div>

	<div class="create-Task">
		<div class="create-Task-Step0" id="createTaskStep0">
			if<span style="display: none"></span><a href="#"
				onclick="goToTHIS();">THIS</a>then<span style="display: none"></span>THAT<span
				style="display: none"></span>
		</div>

		<!-- éæ©THISçç±»å -->
		<div class="create-Task-Step1" id="createTaskStep1">
			<hr>
			<span>Choose THIS Task:</span>
			<div>
				<a class="THIS_img" id="THIS_Task_datetime" onclick="chooseTHIS(1)">
					<img src="../images/datetime.png">
				</a>
				<a class="THIS_img" id="THIS_Task_email" onclick="chooseTHIS(2)">
					<img src="../images/email.png">
				</a>
				<a class="THIS_img" id="THIS_Task_weibo" onclick="chooseTHIS(3)">
					<img src="../images/weibo.png">
				</a>
					
				
			</div>
		</div>
		
		
		<div class="create-Task-Step2" id="createTaskStep2">
		
			<!-- å¨THISä¸ºå®æ¶datetimeæ¶è¿è¡çæä½ -->
			<div class="create-Task-Step2-datetime" id="createTaskStep2-datetime">
				<hr>
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
			
			
			<!-- å¨THISä¸ºæ¶å°emailæ¶è¿è¡çæä½ -->
			<div class="create-Task-Step2-email" id="createTaskStep2-email">
				<hr>
				<div class="container">
					Email to trigger when Receiving:
				</div>
				<div class="container">
					<form class="form-horizontal">
						<input class="input-xlarge" id="emailRcvdText" size="16" type="text" placeholder="RcvdMailBox"> 
						
					</form>
					<div class="col-md-4 col-md-offset-4" style="padding-top: 5px;text-align:center;">
						<button type="button" class="btn btn-lg btn-success button-task-confirm" 
							onclick="createTrigger()">Create Trigger</button>
					</div>
				</div>
			</div>
			
			<!-- å¨THISä¸ºæ¶å°weiboæ¶è¿è¡çæä½ -->
			<div class="create-Task-Step2-weibo" id="createTaskStep2-weibo">
				<hr>
				<div class="container">
					Weibo to trigger when Receiving:
				</div>
				<div class="container">
					<form class="form-horizontal">
						<input class="input-xlarge" id="weiboRcvdIDText" size="16" type="text" placeholder="RcvdWeiboID"> 
						
					</form>
					<div class="col-md-4 col-md-offset-4" style="padding-top: 5px;text-align:center;">
						<button type="button" class="btn btn-lg btn-success button-task-confirm" 
							onclick="createTrigger()">Create Trigger</button>
					</div>	
				</div>
			</div>
		</div>
		
		<!-- æ¾ç¤ºTHISçéæ© åå¤éTHAT -->
		<div class="create-Task-Step3" id="createTaskStep3">
			<hr>
			if<span style="display: none"></span><a class="THIS_img" id="THIS_Task">
					<img name="THIS_Task_img" src="">
				</a>then<span style="display: none"></span></span><a href="#"
				onclick="goToTHAT();">THAT</a>
		</div>
		
		<!-- éæ©THATçç±»å -->
		<div class="create-Task-Step4" id="createTaskStep4">
			<hr>
			<span>Choose THAT Task:</span>
			<div>
				<a class="THIS_img" id="THAT_Task_email" onclick="chooseTHAT(1)">
					<img src="../images/email.png">
				</a>
				<a class="THIS_img" id="THAT_Task_weibo" onclick="chooseTHAT(2)">
					<img src="../images/weibo.png">
				</a>
			</div>
		</div>
		
		
		<div class="create-Task-Step5" id="createTaskStep5">
				<br>
				<hr>
			<!-- å¨THATä¸ºåéemailæ¶è¿è¡çæä½ -->
			<div class="create-Task-Step5-email" id="createTaskStep5-email">
				<div class="container">
					Email to trigger when Receiving:
				</div>
				<div class="container">
					<form class="form-horizontal">
						<input class="input-xlarge" id="emailSendDstText" size="16" type="text" placeholder="SendDstMailBox"></br>
						<input class="input-xlarge" id="emailSendSrcText" size="16" type="text" placeholder="SendSrcMailBox"></br>
						<input class="input-xlarge" id="emailSendSrcPasswdText" size="16" type="password" placeholder="SendSrcMailPassword"> 
						
					</form>
					<div class="col-md-4 col-md-offset-4" style="padding-top: 5px;text-align:center;">
						<button type="button" class="btn btn-lg btn-success button-task-confirm" 
							onclick="createAction()">Create Action</button>
					</div>	
				</div>
			</div>
			
			<!-- å¨THATä¸ºåéweiboæ¶è¿è¡çæä½ -->
			<div class="create-Task-Step5-weibo" id="createTaskStep5-weibo">
				<div class="container">
					Email to trigger when Receiving:
				</div>
				<div class="container">
					<form class="form-horizontal">
						<input class="input-xlarge" id="weiboSendIDText" size="16" type="text" placeholder="SendWeiboID"></br>
						<input class="input-xlarge" id="weiboSendPassWd" size="16" type="password" placeholder="SendWeiboPassword"> 
						
					</form>
					<div class="col-md-4 col-md-offset-4" style="padding-top: 5px;text-align:center;">
						<button type="button" class="btn btn-lg btn-success button-task-confirm" 
							onclick="createAction()">Create Action</button>
					</div>
				</div>
			</div>
		</div>
		
		
		<!-- æ¾ç¤ºæç»çTask -->
		<div class="create-Task-Step6" id="createTaskStep6">
			<hr>
			if<span style="display: none"></span><a class="THIS_img" id="THIS_Task">
					<img name="THIS_Task_img" src="">
				</a>then<span style="display: none"></span><a class="THAT_img" id="THAT_Task">
					<img name="THAT_Task_img" src=""></a>
		</div>
		
		<div class="create-Task-Step7" id="createTaskStep7">
			<hr>
			<div class="container">
				<div class="col-md-4 col-md-offset-4" style="padding-top: 5px;text-align:center;">
					<button type="button" class="btn btn-lg btn-success button-task-confirm" 
						onclick="createTask()">Create Task</button>
				</div>
			</div>
		</div>
		
	</div>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>


</body>
</html>