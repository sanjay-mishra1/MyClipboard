package app;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import error.FirebaseException;
import error.JacksonUtilityException;
import model.FirebaseResponse;
import service.Firebase;
import response.Response;;
public class Database {
    // get the base-url (ie: 'http://gamma.firebase.com/username')
		String firebase_baseUrl = "your-firebase-url";

		// get the api-key (ie: 'tR7u9Sqt39qQauLzXmRycXag18Z2')
        String firebase_apiKey = "your-firebase-apikey";
        Firebase firebase;
        FirebaseResponse response;
      public  Database() throws UnsupportedEncodingException, FirebaseException{
            initializeDB();
        }
        void initializeDB() throws FirebaseException, UnsupportedEncodingException{
            if( firebase_baseUrl == null || firebase_baseUrl.trim().isEmpty() ) {
                throw new IllegalArgumentException( "Program-argument 'baseUrl' not found but required" );
            }
            // create the firebase
		firebase = new Firebase( firebase_baseUrl );
		// "DELETE" (the fb4jDemo-root)
//		response = firebase.delete();
        }
    public void store(String path,String uid,String data) throws UnsupportedEncodingException, JacksonUtilityException, FirebaseException{
		// "PUT" (test-map into the fb4jDemo-root)
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		dataMap.put( "data", data );
		response = firebase.put( "Customers/"+path,dataMap );
//		System.out.println( "\n\nResult of PUT (for the test-PUT to fb4jDemo-root):\n" + response );
		System.out.println("\n");
		Clipboard clip=new Clipboard();
		clip.dbWriteResponse(response.getSuccess(),data);

    }
    public void read(String path) throws UnsupportedEncodingException, JacksonUtilityException, FirebaseException{
      	response = firebase.get(path);
//    	System.out.println( "\n\nResult of GET:\n" + response );
    	System.out.println("\n");
		Clipboard clip=new Clipboard();clip.dbReadResponse(response.getSuccess());

   }
   public void delete(String path) throws UnsupportedEncodingException, JacksonUtilityException, FirebaseException{
	   LinkedHashMap<String, Object> dataMap = new LinkedHashMap<String, Object>();
		response = firebase.delete( path);
//		System.out.println( "\n\nResult of DELETE (for the test-DELETE):\n" + response );
		response = firebase.get( path );
		System.out.println( "\n\nResult of GET (for the test-DELETE):\n" + response );
   
   }
   public void login(String name,String password) throws FirebaseException, UnsupportedEncodingException, JacksonUtilityException{
	   	System.out.println("Authentication started....");
	   	firebase = new Firebase("https://www.googleapis.com/identitytoolkit/v3/relyingparty", false);
		firebase.addQuery("key", firebase_apiKey);

		Map<String, Object> dataMap=new HashMap<String, Object>();
		dataMap.put("email", name+"@gmail.com");
		dataMap.put("password", password);
		dataMap.put("returnSecureToken", true);
		response = firebase.post("verifyPassword", dataMap);
//		System.out.println("\n\nResult of Signing Up:\n" + response);
		System.out.println("\n");
		Clipboard clip=new Clipboard();clip.loginResponse(response.getSuccess(),name);
   }

   public void signUp(String name,String password) throws FirebaseException, UnsupportedEncodingException, JacksonUtilityException{
	   System.out.println("creating account....");
	   firebase = new Firebase("https://www.googleapis.com/identitytoolkit/v3/relyingparty", false);
		firebase.addQuery("key", firebase_apiKey);

		Map<String, Object> dataMap=new HashMap<String, Object>();
		dataMap.put("email", name+"@gmail.com");
		dataMap.put("password", password);
		dataMap.put("returnSecureToken", true);
		response = firebase.post("signupNewUser", dataMap);
//		System.out.println("\n\nResult of Signing Up:\n" + response);
//		System.out.println("\n");
		if(!response.getSuccess())
			name="signup";
		Clipboard clip=new Clipboard();clip.loginResponse(response.getSuccess(),name);
   }
        
}