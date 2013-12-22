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
	if(isSigninInfoValid()){
		$("#signinInfo").html();
		$.ajax({
			type:'post',
			data:{
				usernamesignin:$('#usernamesignin').val(),
				passwdsignin:$('#passwdsignin').val(),
			},
			url:'',
			success:function (a){
					
			},
			error:function (a){
				
			}
		});
		
		
	}else{
		$("#signinInfo").html("<font color=\"red\">" + "InValid SignIn Info" + "</font>");
	}
}


var request;
function signin(){
	
    if(isUserNameValid()&&isPassWdValid()){
        var nameObj=document.getElementsById("username");
        var name=nameObj[0].value;
        var pwObj=document.getElementsById("passwd");
        var pw=pwObj[0].value;
        var url = "login?name="+name+"&pw="+pw;        //OB login servlet 
        if(window.XMLHttpRequest) {  
            request = new XMLHttpRequest();  //IE7, Firefox, Opera 
        }else if(window.ActiveXObject) {  
            requset = new ActiveXObject("Microsoft.XMLHTTP");   //IE5,IE6
        }
        if(request!==null){  
            request.open("GET",url,true);
            request.onreadystatechange=loginback;
            request.send(null);
        }
    }
       
}

function loginback(){
        if(request.readyState===4){  
                if(request.status===200){  
                    var flag=request.responseText;
                    if(flag==="success"){
                        //go to personal page
                        location.href="personalpage.html";
                    }
                    else if(flag==="admin"){
                         location.href="adminpage.html";
                    }
                   else{
                       var info="<div class='notice error'><i class='icon-remove-sign '>\n\
                        </i>Incorrect username or password<a href='#close' class='icon-remove'></a></div>";
                       document.getElementById("logininfo").innerHTML=info;  
                   }
                }else{
                         var info="<div class='notice error'><i class='icon-remove-sign '>\n\
                            </i>Network error<a href='#close' class='icon-remove'></a></div>";
                        document.getElementById("logininfo").innerHTML=info;  
                }
        }
}
