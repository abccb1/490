
import java.io.*;
import java.net.*;

public class chatClient {

	public static void main(String argv[]) throws Exception {
		String request;
		String username;
		String return_value = "Fail";
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
				System.in));

		/*********** Start a connection with chat server ************/
		Socket clientSocket = new Socket("localhost", 49152);
		DataOutputStream outToServer = new DataOutputStream(
				clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));

		/*********** Send username and an available port reserved for chat ***************/
		ServerSocket chatServer = new ServerSocket(0);
		int port = chatServer.getLocalPort();
		//while (!return_value.equals("Success")) {
		
			System.out.println("Pleas enter your username: ");
			username = inFromUser.readLine();
			outToServer.writeBytes("REG" + '\n' + username + '\n' + port + '\n');
			return_value = inFromServer.readLine();
			System.out.println(return_value);
		
		
	
	}
}
