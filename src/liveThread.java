package com.teamhashtable.chat;

import java.io.DataOutputStream;
import java.io.IOException;

public class liveThread extends Thread{
	private DataOutputStream outToServer;
	long lastTime;
	boolean isLiving;

	public liveThread(DataOutputStream outToServer) {
		this.outToServer = outToServer;
		lastTime = System.nanoTime();
		isLiving = true;
	}

	public void run() {
		try {
			while (isLiving) {
				long duration = System.nanoTime() - lastTime;
				double seconds = (double) duration / 1000000000.0;
				if (seconds > 50) {
					outToServer.writeBytes("LIVE\n");
					lastTime = System.nanoTime();
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
