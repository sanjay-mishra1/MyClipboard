
let app;
//document.addEventListener('DOMContentLoaded', function() {
function login(){
  let app = firebase.app();   
    let email= checkEmail().trim();  
    let password= checkPwd();  

    if(email=='')
    {//round_error_noti("Empty field");
        showMessage("Enter user name");
    }else if(password==''){
      showMessage("Enter password");
    }else if(email.includes(' '))
    showMessage("Name sholud not contains any spaces or special characters");
    else{
      showMessage("Login started...please wait");
    app.auth().signInWithEmailAndPassword(email+'@gmail.com', password).then(function(user) {
        // showMessage("Login success");  
        localStorage.setItem("uid",email.trim().toLowerCase());  
        window.location.href = '/index.html'    
    //    localStorage.setItem("uid", uid);
      }).catch(function(error) {
        var errorCode = error.code;
        uid="";
        var errorMessage = error.message;
        showMessage(errorMessage);
              
      
      }); 
    }
}
//});    
function checkEmail(){
    var email=$("#inputUserEmail").val();
    
    return email;
}
function showMessage( msg){
  document.getElementById("message").innerHTML=msg;
}
function checkPwd(){
    var password=$("#inputPassword").val();
    
    return password;
}


