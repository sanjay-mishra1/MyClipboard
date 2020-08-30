    
document.addEventListener('DOMContentLoaded', function() {
    
    try {
      uid=  getUserID();
      if(uid==null||uid=='')
     { window.location.href = '/login.html'
      return;
    }
    var name;
    if(uid.length>1)
        name=uid.substring(0,1).toUpperCase()+uid.substring(1);
        else name=uid;
    document.getElementById('username').innerText=' '+name;
    document.getElementsByTagName('body')[0].style='visibility:visible';
       app = firebase.app();
     let  db=firebase.database();
       
       db.ref("Customers/"+uid+'/data').on('value', function(snapshot) {
         document.getElementById("page_title").innerHTML='Connected';
         document.getElementById("sub_title").innerHTML='Receiving data from computer';
         var val=snapshot.val();
         if(val!=null&&val!='')
         setData(val);
         else dataElement.style='visibility:hidden';
       })
     } catch (e) {
       console.error(e);
       document.getElementById("page_title").innerHTML='';
       document.getElementById('load').innerHTML = 'Error loading the data';
     }
   });
   var btFieldVisible;
   var dataFieldVisible;
   var dataElement=document.getElementById("data");
   var btElement=document.getElementById('btGroup');
   function setData(data){
    if(btFieldVisible==null||!btFieldVisible)
        btFieldVisible=isHidden(btElement);
    if(dataFieldVisible==null||!dataFieldVisible)
        dataFieldVisible=isHidden(dataElement);   
    if(!btFieldVisible)     
     btElement.style='visiblity:visible;display: flex;'
     dataElement.innerText=data;
     if(!dataFieldVisible)     
     dataElement.style='visiblity:visible;padding: 8px;box-shadow: 0 1px 3px rgb(0 0 0 / 58%), 0 1px 2px rgba(0,0,0,0.24);  margin-top: 10%;'

   }
   function isHidden(el) {
    var style = window.getComputedStyle(el);
    return (style.display === 'hidden')
}
   function copyToClipboard(){
     getClipboardData();
  
   }
 
   var tooltip = document.getElementById("myTooltip");
 async function getClipboardData(){
     try{
      
       var text=dataElement.innerText;
       console.log(text);
       var tempText;
       if(text.length>50)
         tempText= text.substring(0,50)+"...";  
       else
            tempText=text;
         tooltip.innerHTML = "Copied: " + tempText;
    s= await navigator.clipboard.writeText(text).then(function(data){
         
     });
     
     }catch(e){
         document.getElementById('load').innerHTML = "Error: "+e;
     }
   
   }
 
 
   function getUserID(){
     let uid=localStorage.getItem("uid");
     return uid;
   }
   var tooltip = document.getElementById("myTooltip");
   function outFunc() {
     tooltip.innerHTML = "Copy to clipboard";
   }
  

   function   logout(){
     localStorage.setItem("uid", "");
     window.location.href = '/login.html' 
   }
   var searchElement;
  function searchGoogle(){
    if(searchElement==null)
      { 
        var style = window.getComputedStyle(document.getElementById("searchText"));
        searchElement= (style.display === 'none')        
  }
       console.log("Search element ",searchElement);
    if(searchElement)   
      logout()
    else{  
    var query=dataElement.innerText;  
    var url='intent://www.google.com/search?q='+query+"#Intent;scheme=http;package=com.android.chrome;end";
    window.location.href = url;
    }
  }