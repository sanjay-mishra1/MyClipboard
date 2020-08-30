package logger;

public class Logger {
	
	String space="    ";
	public Logger(){
		
	}
	public static Logger getRootLogger(){
		return new Logger();
	}
	public void info(String msg){
		System.out.println("i"+space+msg);
	}
	public void error(String msg){
		System.out.println("e"+space+msg);

	}public void warn(String msg){
		System.out.println("w"+space+msg);
		}
}
