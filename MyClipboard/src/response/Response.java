package response;

public abstract class Response {
	public Response(){
		
	}
	public  void loginResponse(boolean isSuccess,String data){};
	public  void dbWriteResponse(boolean isSuccess,String data){};
	public  void dbReadResponse(boolean isSuccess){};
}
