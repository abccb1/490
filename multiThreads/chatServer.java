import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class chatServer {
	static LinkedHashMap<String, users> userMap = new LinkedHashMap<String, users>();
	public static void main(String args[]) throws IOException{

		checkTime periodThread = new checkTime(userMap);
		periodThread.start();
		ServerSocket welcomeSocket = new ServerSocket(49152);
		ExecutorService tpes =
		    Executors.newFixedThreadPool(5);
		while (true) {
			/*********** Start a connection and put it into a new thread *************/
			Socket connectionSocket = welcomeSocket.accept();
			handleRequest thread = new handleRequest(connectionSocket,userMap);
			//thread.start();
			tpes.execute(thread);
		}
	}
}
