
import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class chatClient {

	public static void main(String argv[]) throws Exception {
		String request;
		String username = null;
		String return_value = "Fail";
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
                                                                             System.in));

		/*********** Send username and an available port reserved for chat ***************/
		ServerSocket chatServer = new ServerSocket(0);

		Socket clientSocket = new Socket("localhost", 49152);
		DataOutputStream outToServer = new DataOutputStream(
                                                            clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
                                                                               clientSocket.getInputStream()));

		int port = chatServer.getLocalPort();
		System.out.println("Pleas enter your username: ");
		username = inFromUser.readLine();
		outToServer.writeBytes("REG" + '\n' + username + '\n' + port + '\n');
		return_value = inFromServer.readLine();
		if (!return_value.equals("Success")) {
			System.out.println(return_value);
			System.out.println("Session closed");
			clientSocket.close();
			return ;
		} /*else if (return_value.equals("Success")) {
          // test registration
          // If we want to test other functionalities such as chat, we need to remove this block.
          System.out.println("Register Successfully");
          clientSocket.close();
          return;
          }*/
		System.out.println(return_value);

		System.out.println("Successfully logged in. ");
		TimeUnit.SECONDS.sleep(3);
		//Start receiving messages from other clients
		recieveChatMessage r = new recieveChatMessage(chatServer);
		r.start();

		//Start send heartbeat to server every 50 seconds
		liveThread heartbeat = new liveThread(username);
		heartbeat.start();

		while(true) {
			clientSocket = new Socket("localhost", 49152);
			outToServer = new DataOutputStream(
                                               clientSocket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(
                                                                    clientSocket.getInputStream()));

			System.out.println("Pleas enter one of following command code:GET,CLOSE,SEND");

			request = inFromUser.readLine();

			if(request.equals("SEND")){
				System.out.println("Please choose ip you want to send message:");
				String ip = inFromUser.readLine();

				System.out.println("Please input port number for that ip address");
				String chatPort = inFromUser.readLine();
				Socket chatSocket = new Socket(ip, Integer.parseInt(chatPort));

				DataOutputStream outToChatServer = new DataOutputStream(
                                                                        chatSocket.getOutputStream());

				System.out.println("Please input the message you want to send:");
				String message = inFromUser.readLine();

				outToChatServer.writeBytes(message);
				chatSocket.close();
				continue;
			}

			System.out.println("Snding request: "+request);
			outToServer.writeBytes(request+'\n');

			if( request.equals("CLOSE") ){
				outToServer.writeBytes(username + "\n");
				r.kill();
				heartbeat.kill();
				System.out.println("close successfully");
				break;
			}

			return_value = inFromServer.readLine();
			System.out.println(return_value);
			if(!return_value.equals("Success")){
				System.out.println(return_value);
				r.kill();
				heartbeat.kill();
				break;
			}

			else if( request.equals("GET") ){
				String size_of_hashmap = inFromServer.readLine();
				System.out.println("Client size: " + size_of_hashmap);
				for(int i = 0 ; i < Integer.parseInt(size_of_hashmap) ; i++){
					System.out.println(inFromServer.readLine());
				}
			}else if (request.equals("LIVE")) {

			}else {
				System.out.println("No such command code, please try again!");
			}
			clientSocket.close();
			TimeUnit.SECONDS.sleep(3);
		}
		/***** Close all port ******/
		//chatServer.close();
	}
}