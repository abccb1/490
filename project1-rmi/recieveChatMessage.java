import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class recieveChatMessage extends Thread {
	ServerSocket chatServer;
	boolean isLiving;
	public recieveChatMessage(ServerSocket chatServer) {
		this.chatServer = chatServer;
		isLiving = true;
	}

	public void run() {
		while (isLiving) {
			try {
				Socket connectionSocket = chatServer.accept();
				BufferedReader inFromClient = new BufferedReader(
						new InputStreamReader(connectionSocket.getInputStream()));
				String message = inFromClient.readLine();
				System.out.println("Recieve:" + message);
				connectionSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void kill(){
		isLiving = false;
	}
}
