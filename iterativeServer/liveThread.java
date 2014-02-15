
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class liveThread extends Thread{
	long lastTime;
	boolean isLiving;
	String userName;
	
	public liveThread(String userName) {
		this.userName = userName;
		lastTime = System.nanoTime();
		isLiving = true;
	}

	public void run() {
		try {
			while (isLiving) {
				long duration = System.nanoTime() - lastTime;
				double seconds = (double) duration / 1000000000.0;
				if (seconds > 50) {
					Socket clientSocket = new Socket("localhost", 49152);
					DataOutputStream out = new DataOutputStream(
							clientSocket.getOutputStream());
					//System.out.println("beating");
					out.writeBytes("LIVE\n");
					out.writeBytes(userName + "\n");
					lastTime = System.nanoTime();
					clientSocket.close();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void kill() {
		isLiving = false;
	}
}
