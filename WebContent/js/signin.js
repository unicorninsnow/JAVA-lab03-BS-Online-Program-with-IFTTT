/* 
 * user log in 
 * link with ifttt.html
 */

var usernameInfoValid = false;
var passwdInfoValid = false;

function isUsernameEmpty(username, infotxt) {
	if (username == null || username == "") {
		$("#signinInfo").html("<font color=\"red\">" + infotxt + "</font>");
		return false;
	} else {
		$("#signinInfo").html("");
		return true;
	}

}

function isUsernameConsistofWord(username, infotxt){
	var reg = /^(?!_)(?!.*?_$)[a-zA-Z0-9_]+$/; // name consists of a-z A-Z _
	if (!reg.test(username)) {
		$("#signinInfo").html("<font color=\"red\">" + infotxt + "</font>");
		return false;
	} else {
		$("#signinInfo").html("");
		return true;
	}
	
}

function isUsernameValid(thisform){
	with (thisform) {
		var username = $("#usernamesignin").val();
		if (isUsernameEmpty(username, "Username must be filled out!") == false) {
			$("#usernamesignin").focus();
			usernameInfoValid = false;
			return false;
		}
		if (isUsernameConsistofWord(username, "Username must be consist of word and num!") == false) {
			$("#usernamesignin").focus();
			usernameInfoValid = false;
			return false;
		}
		
		return true;
	}
}


function isPasswdEmpty(passwd, infotxt) {
	if (passwd == null || passwd == "") {
		$("#signinInfo").html("<font color=\"red\">" + infotxt + "</font>");
		return false;
	} else {
		$("#signinInfo").html("");
		return true;
	}
	
}

function isPasswdConsistofWord(passwd, infotxt){
	var reg = /^[a-zA-Z0-9]{6,}$/; // name consists of a-z A-Z _
	if (!reg.test(passwd)) {
		$("#signinInfo").html("<font color=\"red\">" + infotxt + "</font>");
		return false;
	} else {
		$("#signinInfo").html("");
		return true;
	}
	
}

function isPasswdValid(thisform){
	with (thisform) {
		var passwd = $("#passwdsignin").val();
		if (isUsernameEmpty(passwd, "Password must be filled out!") == false) {
			$("#passwdsignin").focus();
			passwdInfoValid = false;
			return false;
		}
		if (isUsernameConsistofWord(passwd, "Password valid!") == false) {
			$("#passwdsignin").focus();
			passwdInfoValid = false;
			return false;
		}
		
		return true;
	}
}


function isSigninInfoValid(){
	return (usernameInfoValid && passwdInfoValid);
}


function signin(){
//	if(isSigninInfoValid()){
		$("#signinInfo").html();
		$.ajax({
			type:'post',
			data:{
				name:$('#usernamesignin').val(),
				passWd:$('#passwdsignin').val(),
			},
			url:'LoginAuthe',
			success:function (isLogIn){
				if(isLogIn == "true"){
					alert("SignIn successfully.");
					 window.location.href="dashboard.jsp";
				}else{
					alert("can't signin!");
				}
			},
			error:function (a){
				alert("network error occur!")
			}
		});
		
		
//	}else{
//		$("#signinInfo").html("<font color=\"red\">" + "InValid SignIn Info" + "</font>");
//	}
}
