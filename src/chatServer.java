import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.io.*;
import java.net.*;
import java.util.*;


public class chatServer {


    static LinkedHashMap<String, users> userMap = new LinkedHashMap<String, users>();
    public static void main(String args[]) throws IOException{
	System.out.println("Server ready\n");
	ExecutorService tpes =
            Executors.newFixedThreadPool(5);
	checkTime periodThread = new checkTime(userMap);
	
	periodThread.start();
	ServerSocket welcomeSocket = new ServerSocket(49152);
	
	while (true) {
	    /*********** Start a connection and put it into a new thread *************/
	    Socket connectionSocket = welcomeSocket.accept();
	    handleRequest thread = new handleRequest(connectionSocket,userMap);
	    tpes.execute(thread);

	}
    }
}
