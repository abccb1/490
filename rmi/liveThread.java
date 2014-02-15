import java.io.DataOutputStream;
import java.io.IOException;

public class liveThread extends Thread{
	remoteUser users;
	private long lastTime;
	public boolean isLiving;
	String name;
	public liveThread(remoteUser users,String name) {
		this.users = users;
		this.name = name;
		lastTime = System.nanoTime();
		isLiving = true;
	}

	public void run() {
		try {
			while (isLiving) {
				sleep(50000);
				long duration = System.nanoTime() - lastTime;
				double seconds = (double) duration / 1000000000.0;
				if (seconds > 50) {
					users.live(name);
					lastTime = System.nanoTime();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void kill(){
		isLiving = false;
	}
}
