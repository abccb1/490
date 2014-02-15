import java.io.*;
import java.net.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class chatClient {
	private chatClient() {
	}

	public static void main(String argv[]) throws Exception {
		String host = (argv.length < 1) ? null : argv[0];
		LinkedHashMap<String, users> userMap = null;
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
				System.in));
		ServerSocket chatServer = new ServerSocket(0);
		int port = chatServer.getLocalPort();
		String return_value = "Fail";
		String username = null;
		String request;

		Registry registry = LocateRegistry.getRegistry(host);
		remoteUser users = (remoteUser) registry.lookup("Server");

		while (!return_value.equals("Success")) {
			System.out.println("Pleas enter your username: ");
			username = inFromUser.readLine();
			return_value = users.reg(username, Integer.toString(port));
			System.out.println(return_value);
		}

        recieveChatMessage r = new recieveChatMessage(chatServer);
        r.start();

//        liveThread heartbeat = new liveThread(users,username);
//        heartbeat.start();
		
		while (true) {
			System.out
					.println("Pleas enter one of following command code:GET,CLOSE,SEND");
			request = inFromUser.readLine();
			if (request.equals("GET")) {
				userMap = users.get(username);
				if(userMap.size() == 0){
					System.out.println("Your account is expired, please register again");
					break;
				}
				Iterator<users> it = userMap.values().iterator();
				while (it.hasNext()) {
					users currentUser = it.next();
					currentUser.print();
				}
			} else if (request.equals("CLOSE")) {
				users.close(username);
				System.exit(0);
			} else if (request.equals("SEND")) {
				System.out
						.println("Please choose name you want to send message:");
				String reciever = inFromUser.readLine();
				users currentUser = userMap.get(reciever);
				Socket chatSocket = new Socket(currentUser.getIp(),
						Integer.parseInt(currentUser.getPort()));
				DataOutputStream outToChatServer = new DataOutputStream(
						chatSocket.getOutputStream());

				System.out
						.println("Please input the message you want to send:");
				String message = inFromUser.readLine();

				outToChatServer.writeBytes(message);
				chatSocket.close();
				continue;

			}
		}
	}
}
