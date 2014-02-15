import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class handleRequest extends Thread {
	private String username;
	private String port;
	private String ip;
	private String request;

	LinkedHashMap<String, users> userMap;
	Socket connectionSocket;

	public handleRequest(Socket connectionSocket,
			     LinkedHashMap<String, users> userMap) {
		this.userMap = userMap;
		this.connectionSocket = connectionSocket;

	}

	public void run() {
	    System.out.println("Start handling requests\n");
		try {

			BufferedReader inFromClient = new BufferedReader(
					new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(
					connectionSocket.getOutputStream());

			/********** Read request *********************/

			users tempUser = null;
			/********** Process Reg request: *************/
			while (tempUser == null) {
				request = inFromClient.readLine();
				if (request.equals("REG")) {
					/*********** Receive usernames and available port on clients **************/
					username = inFromClient.readLine();
					port = inFromClient.readLine();
					ip = connectionSocket.getInetAddress().toString()
							.substring(1);

					/***********
					 * Store user information into a linked hash map if not
					 * already exist
					 **********/

					tempUser = new users(username, ip, port);
					tempUser.print();
					if (!userMap.containsKey(tempUser.getName())) {
						userMap.put(tempUser.getName(), tempUser);
						outToClient.writeBytes("Success\n");
					} else {
						System.out.println("Name already exist");
						tempUser = null;
						outToClient.writeBytes("Failed\n");
					}
				}
			}
			

			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}
