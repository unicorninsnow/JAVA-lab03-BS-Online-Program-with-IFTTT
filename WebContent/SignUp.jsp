<!DOCTYPE HTML>
<html>
<head>
<!-- <%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%> -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>IFTTT - Sign Up</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="bootstrap-3.0.0/dist/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/signup/signup.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="js/signup.js"></script>
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
				placeholder="Username"	required="" autofocus=""
				onblur="isUsernameValid(this)"> 
			<input type="text" id="emailsignup" class="form-control" 
				placeholder="Email address"	required=""
				onblur="isEmailValid(this)"> 
			<input type="text" id="sexsignup" class="form-control" 
				placeholder="Sex"	required=""
				onblur="isSexValid(this)"> 
			<input type="text" id="birthdatesignup" class="form-control" 
				placeholder="BirthDate"	required=""
				onblur="isBirthDateValid(this)"> 
			<input type="password" id="passwdsignup" class="form-control" 
				placeholder="Password"  required=""
				onblur="isPasswdValid(this)"> 
			<label class="checkbox"> 
			<input type="checkbox" value="remember-me">
				Remember me
			</label>
			<br></br>
			<p id="signupInfo"><font color="red"></font></p>
			<button class="btn btn-lg btn-primary btn-block" type="submit" onclick="signup()">Join Us</button>
		</form>
		
	</div>
</body>
</html>