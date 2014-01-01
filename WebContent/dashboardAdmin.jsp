<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<% /*检测是否已登录*/ %>
<%@include file="isLogIn.jsp" %>
<%String userNameLogIn = (String)session.getAttribute("user"); %>
<!DOCTYPE HTML>
<html>
<head>
	<%@include file="PathInclude.jsp" %>
	<title>IFTTT - Dashboard</title>
	
	<link href="//cdnjs.bootcss.com/ajax/libs/twitter-bootstrap/2.2.2/css/bootstrap-responsive.min.css" rel="stylesheet">
	<link href="../bootstrap-3.0.0/examples/starter-template/starter-template.css" rel="stylesheet" media="screen">
	<link href="../css/dashboard.css" rel="stylesheet" media="screen">
	<link href="../bwsewell-tablecloth/assets/css/prettify.css" rel="stylesheet" media="screen">
	<link href="../bwsewell-tablecloth/assets/css/tablecloth.css" rel="stylesheet" media="screen">
	<link href="../bwsewell-tablecloth/assets/css/bootstrap-tables.css" rel="stylesheet" media="screen">
	
	<script type="text/javascript" src="../js/dashboard.js"></script>
	<script type="text/javascript" src="../bootstrap-3.0.0/js/popover.js"></script>
	<script type="text/javascript" src="../bootstrap-3.0.0/assets/js/application.js"></script>
	<script type="text/javascript" src="../bwsewell-tablecloth/assets/js/jquery.tablecloth.js"></script>
	<script type="text/javascript" src="../bwsewell-tablecloth/assets/js/jquery.tablesorter.js"></script>
	<script type="text/javascript" src="../bwsewell-tablecloth/assets/js/jquery.metadata.js"></script>

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
	                <li><a href="#">Separated link</a></li>
	                <li><a href="#">One more separated link</a></li>
	              </ul>
	            </li>
	          </ul>
	        </div><!--/.nav-collapse -->
	       </div>
		<div class="col-xs-4 col-sm-3 userInfoButton">
			<div class="dropdown userInfoButtonDropdownMenu">
				<a id="userInfoDropdownMenu" class="userInfoButtonDropdown" data-toggle="dropdown" data-target="#"
					href="/page.html"> <%=userNameLogIn%> <span class="caret"></span>
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
    
	<div class="dashboard_main">
		<div class="container">
			<!-- <div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar" role="navigation">
	          <div class="list-group">
	            <a href="#" class="list-group-item active">Link</a>
	            <a href="#" class="list-group-item">Link</a>
	           
	          </div>
	        </div> -->
	        
	        <div class="col-xs-14 col-sm-9">
				<div class="dashboard_main_title">
						<h1>My Task!</h1>
				</div>
				
				<div class="dashboard_nav_tabs">
					<ul class="nav nav-tabs" id="dashboard_nav_tabs_id">
						<li class="active"><a href="#allTask" data-toggle="tab"  onclick="updateAllTaskTable();">All Task</a></li>
						<li><a href="#runTask" data-toggle="tab" onclick="updateRunTaskTable();">Run Task</a></li>
						<li><a href="#messages" data-toggle="tab">Messages</a></li>
					</ul>
					<script>
						$('#dashboard_nav_tabs_id a').click(function (e) {
						  e.preventDefault();
						  $(this).tab('show');
						});
					</script>
				</div>
			</div>
			
			<div class="clearfix visible-xs"></div>
			<div class="col-xs-4 col-sm-3">
				
				<div class="dashboard_create_task_button_div" >
					<a type="button" class="btn btn-lg btn-success dashboard_create_task_button" href="NewTask.jsp">New Task!</a>
				</div>
				<div class="dashboard_tasknum">
					<span id="TaskNowNum">0</span> Tasks in Total<br>
					<span id="TaskRunNum">0</span> Tasks in Run&nbsp;<span style="display: none;"></span>
				</div>
			</div>
		</div>
	</div>
	
	<div class="dashboard_tab_div">
		<div class="container ">
			<div id="myTabContent" class="tab-content">
				<!-- 所有任务列表 -->
				<div class="tab-pane fade active in" id="allTask">
					<div class="panel panel-default" id="allTask_panel">
						<!-- Default panel contents -->
						<div class="panel-heading" style="font-size: 20px;">All Task Here! -- All tasks created are list here.</div>

						<!-- Table -->
						<div class="table-responsive">
							<table class="table"  id="allTaskTable"
								style="padding:0;border-spacing:0px 0px;word-wrap:break-word; table-layout:fixed;">
								<thead>
									<tr>
										<th width="50px" style="text-align: center;">#</th>
										<th>Task Name</th>
										<th width="100px" style="text-align: center;">Trigger</th>
										<th width="100px" style="text-align: center;">Action</th>
										<th width="100px" style="text-align: center;">
											
											<div onclick="deleteChosenTasks('allTaskCheckBox')">
											Delete
											</div>
											
										</th>
										<th width="80px" style="text-align: center;">Modify</th>
									</tr>
								</thead>
								<tbody id="allTaskTable_tbody" style="text-align: center;">
									<!-- <tr>
										<td>12</td><td style='text-align: left;'>
											<div id='allTaskName13' data-container='body' data-toggle='popover'>Tasks Test13
											</div>
										</td>
										<td>13</td>
										<td>Datetime</td>
										<td>
											<input id='allTaskCkb13' name='allTaskCheckBox'  type='checkbox'>
										</td>
										<td>To Modify</td>
									</tr>
									<tr>
										<td>13</td><td style='text-align: left;'>
											<div id='allTaskName12' data-container='body' data-toggle='popover'>Tasks Test12
											</div>
										</td>
										<td>12</td>
										<td>Datetime</td>
										<td>
											<input id='allTaskCkb12' name='allTaskCheckBox'  type='checkbox'>
										</td>
										<td>To Modify</td>
									</tr> -->
									
								</tbody>
								
							</table>
							<script>
								$("table").tablecloth();

								$("table").tablecloth({
									theme : "paper",
									bordered : false,
									condensed : true,
									striped : false,
									sortable : true,
									clean : true,
									cleanElements : "th td",
									customClass : "table-hover"
								});
							</script>
						</div>
					</div>
				</div>
				
				<!-- 正在运行的任务列表 -->
				<div class="tab-pane fade" id="runTask">
					<div class="panel panel-default" id="runTask_panel">
						<!-- Default panel contents -->
						<div class="panel-heading" style="font-size: 20px;">Run Task Here! -- Run tasks created are list here.</div>

						<!-- Table -->
						<div class="table-responsive">
							<table class="table"  id="runTaskTable"
								style="padding:0;border-spacing:0px 0px;word-wrap:break-word; table-layout:fixed;">
								<thead style="text-align: center;">
									<tr>
										<th width="50px" style="text-align: center;">#</th>
										<th class="allTask_Table_Name">Task Name</th>
										<th width="100px" style="text-align: center;">Trigger</th>
										<th width="100px" style="text-align: center;">Action</th>
										<th width="100px" style="text-align: center;">
											<!-- <div class="dropdown">
												<a id="dLabel" role="button" data-toggle="dropdown"
													data-target="#" href="/page.html"> Do <span
													class="caret"></span>
												</a>


												<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel" 
													onclick="deleteChosenTasks()">Delete</ul>
											</div>
											<script>
												$('.dropdown-toggle').dropdown();
											</script> -->
											
											<div onclick="deleteChosenTasks('runTaskCheckBox')">Stop</div>
											
										</th>
										<th width="80px" style="text-align: center;">Modify</th>
									</tr>
								</thead>
								<tbody id="runTaskTable_tbody" style="text-align: center;">
									
									
								</tbody>
								
							</table>
							<script>
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
							</script>
						</div>
					</div>
				</div>
				
				<!-- 消息列表 -->
				<div class="tab-pane fade" id="messages">
					<p>Etsy mixtape wayfarers, ethical wes anderson tofu before
						they sold out mcsweeney's organic lomo retro fanny pack lo-fi
						farm-to-table readymumptown. Pitchfork sustainable tofu synth
						chambray yr.</p>
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


</body>
</html>