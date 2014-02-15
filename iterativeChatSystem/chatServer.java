import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class chatServer {
	static LinkedHashMap<String, User> userMap = new LinkedHashMap<String, User>();
	public static void main(String args[]) throws IOException{
        System.out.println("Listening 49152...");
        ServerSocket welcomeSocket = new ServerSocket(49152);
		while (true) {
            String request;
            String username;
            String ip;
            String port;
			/*********** Start a connection and put it into a new thread *************/
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(
                new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(
                connectionSocket.getOutputStream());
	        /************** read request info *****************/
	        System.out.println("waiting for request");
	        request = inFromClient.readLine();
	        System.out.println("requset " + request);
	        if (request.equals("REG")) {
	            username = inFromClient.readLine();
	            port = inFromClient.readLine();
	            ip = connectionSocket.getInetAddress().toString().substring(1);
	            if (!userMap.containsKey(username)) {
	                User tmpUser = new User(username, ip, port);
	                userMap.put(tmpUser.getName(), tmpUser);
	                outToClient.writeBytes("Success\n");
	                connectionSocket.close();
	                continue;
	            } else {
	                outToClient.writeBytes("Name exists, please pick another name\n");
	                connectionSocket.close();
	                continue;
	            }
	        } else if (request.equals("GET")) {
	        	outToClient.writeBytes("Success\n");
	            String mapSize = Integer.toString(userMap.size());
	            outToClient.writeBytes(mapSize + "\n");
	            Iterator<User> it = userMap.values().iterator();
	            while (it.hasNext()) {
	            	User curUser = it.next();
	             	outToClient.writeBytes(curUser.getUserInformation());
	            }
	        } else if (request.equals("CLOSE")) {
	           	username = inFromClient.readLine();
	           	if (userMap.containsKey(username))
	           		userMap.remove(username);
	           	outToClient.writeBytes("CLOSE\n");
	        } else if (request.equals("LIVE")) {
	           	username = inFromClient.readLine();
	           	System.out.println("living");
	           	if (userMap.containsKey(username))
	           		userMap.get(username).liveness();
	        } else {
	        	outToClient.writeBytes("Success\n");
	        	outToClient.writeBytes("none\n");
	        }
	        checkTime();
	        connectionSocket.close();
	        //welcomeSocket.close();
		}
    }
	
	public static void checkTime() {
		//System.out.println("checking time");
		boolean ret_val;
		Iterator<User> it;
		it = userMap.values().iterator();
		while ( it.hasNext() ) {
            User currentUser = it.next();
            ret_val = currentUser.period();
            if(!ret_val) {
                userMap.remove(currentUser.getName());
            }
		}
	}
}
