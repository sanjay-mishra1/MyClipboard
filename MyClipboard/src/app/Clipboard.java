package app;

import java.awt.datatransfer.*;
import java.io.UnsupportedEncodingException;
import java.awt.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import error.FirebaseException;
import error.JacksonUtilityException;
import logger.Logger;
import response.Response;
    public class Clipboard extends Response{
    	private static final long READ_INTERVAL = 4;
		String clipboard="";
    	String uid="";
    static	Database tempDb;
    static	Database db;
	protected static final Logger LOGGER = Logger.getRootLogger();

         public static void main(String args[])throws Exception
         {	
            initiateView();
        }
         
    private static void initiateView() throws UnsupportedEncodingException {
    	Scanner sc=new Scanner(System.in);
 		System.out.println("Select Option:\n1.Login\n2.Create Account\n3.Stop");
    	switch(sc.next()){
    	case "1":login(sc);break;
    	case "2":signup(sc);break;
    	case "3":LOGGER.info("Ending execution...");break;
    	default: LOGGER.info("Invalid option");initiateView();
    	}
		}

	private static void signup(Scanner sc) throws UnsupportedEncodingException {
		clearScreen();
		LOGGER.info("Signup user\n");
		try{ System.out.println("Enter name");
			 String name=sc.next();
			 System.out.println("Enter Password");
			 String pass=sc.next();
			 if(!checkCredentials(name, pass))
			 {
			 initiateView();
			 return;
			 }
        	tempDb = new Database();
        	tempDb.signUp(name.toLowerCase().trim(), pass);
		} catch (FirebaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (JacksonUtilityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	private static void login(Scanner sc) throws UnsupportedEncodingException {
		clearScreen();
		LOGGER.info("login user\n");
		try{ System.out.println("Enter name");
		 String name=sc.next();
		 System.out.println("Enter Password");
		 String pass=sc.next();
		 if(!checkCredentials(name, pass))
			 {
			 initiateView();
			 return;
			 }
		 tempDb = new Database();
		 tempDb.login(name.toLowerCase().trim(), pass);
	} catch (FirebaseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}catch (JacksonUtilityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}			
	}
	public static boolean checkCredentials(String name,String password){
		if(name.isEmpty())
			{
			clearScreen();
			LOGGER.info("Name cannot be empty");
			return false;
			}
		if(password.isEmpty())
		{clearScreen();LOGGER.info("Password cannot be empty");
		return false;
		}
		if(password.length()<6)
		{clearScreen();LOGGER.info("length of password should be greater than 6");
		return false;
		}
		if(name.contains(" ")||name.contains(".")||name.contains(",")||name.contains("+"))
		{clearScreen();LOGGER.info("Name should not contains any spaces, special character");
		return false;
		}
		
		return true;
		
	}
	public static void clearScreen(){
		try{
			new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
		}catch(Exception e){}
	}
	@Override
    public void loginResponse(boolean isSuccess,String data){
    	if(isSuccess&& !data.isEmpty())
    		{try {clearScreen();
    			System.out.println("Detecting clipboard...");
    			uid=data;
				db=new Database();
				try {
					clip();
				} catch (JacksonUtilityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FirebaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		}
    	else 
    		{
    			clearScreen();
    			if(data.contains("signup"))
    				LOGGER.info("An error occurred. Looks like an account with same name already exist.");
    			else
    				LOGGER.info("Username or password incorrect");
    			try {
					initiateView();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			
    		}
    }
    @Override
    public void dbWriteResponse(boolean isSuccess,String data){
    	if(isSuccess)
        	LOGGER.info("Database write successfull  data <"+data+">");
        	else 
        		LOGGER.info("Database write error occurred");  
    	}
    @Override
    public void dbReadResponse(boolean isSuccess){
    	if(isSuccess)
        	LOGGER.info("Database read successfull");
       	else 
       		LOGGER.info("Database read error occurred"); 
    	}
    public  void  clip() throws JacksonUtilityException, FirebaseException{
        try {
        	String tempClip=(String) Toolkit.getDefaultToolkit().getSystemClipboard()
                .getData(DataFlavor.stringFlavor);
        	if(!clipboard.equals(tempClip))
        		{
        		db.store(uid,uid, tempClip);
        		clipboard=tempClip;
        		}
        	
            TimeUnit.SECONDS.sleep(READ_INTERVAL);
            mySeparateTask();
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }catch (Exception e) {
           LOGGER.info("Unsupportted file. Only support text in clipboard");
           //e.printStackTrace();
           try {
			TimeUnit.SECONDS.sleep(READ_INTERVAL);
			mySeparateTask();
		} catch (InterruptedException e1) {	
		}
           
        }
    }

    private  void mySeparateTask() {
        try {
			clip();
		} catch (JacksonUtilityException | FirebaseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("\n\nAN error occurred. May be because of no internet connection");
			try {
				TimeUnit.SECONDS.sleep(READ_INTERVAL);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
			mySeparateTask();
		}
    }
    }