<!DOCTYPE HTML>
<html>
<head>
<!-- <%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%> -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- link rel="stylesheet" type="text/css" href="iftttStyle.css">-->

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" href="../bootstrap-3.0.0/assets/ico/favicon.png">
<title>IFTTT - Change Your Life</title>

<!-- Bootstrap -->
<link href="bootstrap-3.0.0/dist/css/bootstrap.css" rel="stylesheet"
	media="screen">
<link href="bootstrap-3.0.0/examples/signin/signin.css" rel="stylesheet"
	media="screen">
<link href="css/jumbotron/jumbotron.css"
	rel="stylesheet" media="screen">
<script type="text/javascript" src="js/signin.js"></script>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	      <script src="bootstrap-3.0.0/assets/js/html5shiv.js"></script>
	      <script src="bootstrap-3.0.0/assets/js/respond.min.js"></script>
	    <![endif]-->

</head>

<body style="">

	<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">IFTTT</a>
			</div>
			<div class="navbar-collapse collapse">
				<div class="navbar-form navbar-right" role="form">
				
					<p id="signinInfo" class="form-group"><font color="red"></font></p>
					<div class="form-group">
						<input type="text" id="usernamesignin" placeholder="Username" class="form-control" 
							onblur="isUsernameValid(this)">
					</div>
					<div class="form-group">
						<input type="password" id="passwdsignin"  placeholder="Password" class="form-control"
							onblur="isPasswdValid(this)">
					</div>
					<button  class="btn btn-success" onclick="signin()">Sign in</button>
					
					
				
				</div>
			</div>
			<!--/.navbar-collapse -->
		</div>
	</div>
<br/>
	<!-- Main jumbotron for a primary marketing message or call to action -->
	<div class="container theme-showcase">
		<div class="jumbotron">
			<div class="container">
				<h1>IFTTT</h1>
				<p>Change Your Life!</p>
				<p>
					<a href="SignUp.jsp" class="btn btn-primary btn-lg" role="button">
						Join Us NOW! »
					</a>
				</p>
			</div>
		</div>
	</div>

	<div class="container">
		<!-- <form class="form-signin" role="form">
			<h2 class="form-signin-heading">Please Sign In</h2>
			<input type="text" class="form-control" placeholder="Email address"
				required="" autofocus=""> <input type="password"
				class="form-control" placeholder="Password" required=""> <label
				class="checkbox"> <input type="checkbox" value="remember-me">
				Remember me
			</label>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign
				in</button>
		</form> -->
	
		<!-- Example row of columns -->
		<!-- <div class="row">
			<div class="col-md-4">
				<h2>Heading</h2>
				<p>Donec id elit non mi porta gravida at eget metus. Fusce
					dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh,
					ut fermentum massa justo sit amet risus. Etiam porta sem malesuada
					magna mollis euismod. Donec sed odio dui.</p>
				<p>
					<a class="btn btn-default" href="#" role="button">View details
						»</a>
				</p>
			</div>
			<div class="col-md-4">
				<h2>Heading</h2>
				<p>Donec id elit non mi porta gravida at eget metus. Fusce
					dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh,
					ut fermentum massa justo sit amet risus. Etiam porta sem malesuada
					magna mollis euismod. Donec sed odio dui.</p>
				<p>
					<a class="btn btn-default" href="#" role="button">View details
						»</a>
				</p>
			</div>
			<div class="col-md-4">
				<h2>Heading</h2>
				<p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in,
					egestas eget quam. Vestibulum id ligula porta felis euismod semper.
					Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum
					nibh, ut fermentum massa justo sit amet risus.</p>
				<p>
					<a class="btn btn-default" href="#" role="button">View details
						»</a>
				</p>
			</div> -->
		</div>

		<hr>

		<footer>
			<p>© Company 2013</p>
		</footer>
	<!-- /container -->


	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="http://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>
	<script src="../../dist/js/bootstrap.min.js"></script>


	<div>
		<object id="ClCache" click="sendMsg" host="" width="0" height="0"></object>
	</div>
	
	
	
	
	
	
	
	
    
<!-- 	<div class="bs-header" id="content">
		<div class='navbar navbar-inverse'>
			<div class="container">
				<header>
					<h1 class="homepage-title">
						IFTTT <br>Change Your Life!<br>
					</h1>
					<div class='nav-collapse' style="height: auto;">
						<ul class="nav">
							<li class="active"><a href="#">Home</a></li>
							<li><a href="#">Page One</a></li>
							<li><a href="#">Page Two</a></li>
						</ul>
					</div>
			</div>
			<h2>Join us NOW!</h2>
			</header>
		</div>
	</div>

	<div class="container">

		<form class="form-signin" role="form">
			<h2 class="form-signin-heading">Please Sign In</h2>
			<input type="text" class="form-control" placeholder="Email address"
				required="" autofocus=""> <input type="password"
				class="form-control" placeholder="Password" required=""> <label
				class="checkbox"> <input type="checkbox" value="remember-me">
				Remember me
			</label>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign
				in</button>
		</form>

	</div>

	<div>
		<object id="ClCache" click="sendMsg" host="" width="0" height="0"></object>
	</div> -->


	<!-- <div class="view view-signup selected" id="signup"
		style="display: block;">
		<div class="container">
			<form action="SignIn.jsp" method="post" class="zu-side-login-box"
				id="sign-form-1" autocomplete="off" novalidate="novalidate">
				<input type="hidden" name="_xsrf"
					value="4c2e03de1e2144258079209adbb59aa3">
				<div class="title clearfix">
					<a class="js-signin signin-switch with-icon" href="#signin">登录<i
						class="icon-sign icon-sign-arrow"></i></a> 
					<a class="with-icon return"><i
						class="icon-sign icon-sign-arrow-l"></i><span
						class="js-title-label">注册帐号</span></a>
						
				</div>
						
				<a href="SignUp.html"> SignUp NOW!</a>
				<div class="name input-wrapper js-weibo-bind-name-transfer">
					<input type="text" class="username" name="username"
						placeholder="Username" original-title="">

				</div>
				<div class="email input-wrapper">
					<input required="" type="email" name="email" placeholder="Email">
				</div>
				<div class="input-wrapper">
					<input required="" type="password" minlength="6" maxlength="128"
						name="password" placeholder="Password">
				</div>
				<div class="captcha-holder"></div>
				<div class="failure" id="summary" style="display: none;">
					<ul></ul>
				</div>
				<div class="button-wrapper command">
					<button class="sign-button" type="submit">Sign In!</button>
				</div>

			</form>
		</div>
	</div> -->


</body>
</html>