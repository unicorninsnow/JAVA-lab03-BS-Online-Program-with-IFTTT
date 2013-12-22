<!DOCTYPE HTML>
<html>
<head>
<!-- <%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%> -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>IFTTT - Sign Up</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="../bootstrap-3.0.0/dist/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="../css/signup/signup.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="../js/signup.js"></script>
<script src="http://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	      <script src="bootstrap-3.0.0/assets/js/html5shiv.js"></script>
	      <script src="bootstrap-3.0.0/assets/js/respond.min.js"></script>
	    <![endif]-->
</head>
<body>
	<div class="container">
	
	</div>
	<div class="container">
		<form class="form-signup" role="form">
			<h2 class="form-signup-heading">Sign Up to Join Us NOW!</h2>
			<input type="text" id="usernamesignup" class="form-control" 
				placeholder="Username"	
				onblur="isUsernameValid(this)"> 
			<input type="text" id="emailsignup" class="form-control" 
				placeholder="Email address"	required="" autofocus=""
				onblur="isEmailValid(this)"> 
			<input type="password" id="passwdsignup" class="form-control" 
				placeholder="Password" 
				onblur="isPasswdValid(this)"> 
			<label class="checkbox"> 
			<input type="checkbox" value="remember-me">
				Remember me
			</label>
			<br></br>
			<p id="signupInfo"><font color="red"></font></p>
			<button class="btn btn-lg btn-primary btn-block" type="submit" onclick="signup()">Sign
				in</button>
		</form>
		<!-- <form action="SignUp.jsp" method="post"
			class="zu-side-login-box" id="sign-form-1" autocomplete="off"
			novalidate="novalidate">
			<input type="hidden" name="_xsrf"
				value="4c2e03de1e2144258079209adbb59aa3">


			<div class="name input-wrapper js-weibo-bind-name-transfer">
				<input type="text" class="username" name="username"	placeholder="Username" original-title="">
			</div>
			<div class="email input-wrapper">
				<input required="" type="email" name="email" placeholder="Email">
			</div>
			<div class="input-wrapper">
				<input required="" type="password" minlength="6" maxlength="128" name="password" placeholder="Password">
			</div>
			<div class="captcha-holder"></div>
			<div class="failure" id="summary" style="display: none;">
				<ul></ul>
			</div>
			<div class="button-wrapper command">
				<button class="sign-button" type="submit">Join!</button>
			</div>

		</form> -->
	</div>
</body>
</html>