import java.io.*;
import java.net.*;
import java.util.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

public class chatServer implements remoteUser {
	static public LinkedHashMap<String, users> userMap = new LinkedHashMap<String, users>();
	private users tempUser = null;
	private static int port = 64535;

	public chatServer() {
	}

	public static void main(String args[]) throws IOException {

		checkTime periodThread = new checkTime(userMap);

		chatServer obj = new chatServer();
		remoteUser users = (remoteUser) UnicastRemoteObject
				.exportObject(obj, 0);
		LocateRegistry.createRegistry(port);
		Registry registry = LocateRegistry.getRegistry();

		try {
			registry.bind("Server", users);
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println("Server ready");
		periodThread.start();

	}

	public String reg(String username, String port) {
		try {
			String ip = RemoteServer.getClientHost();
			tempUser = new users(username, ip, port);
			tempUser.print();
			if (!userMap.containsKey(tempUser.getName())) {
				userMap.put(tempUser.getName(), tempUser);
				return ("Success");
			} else {
				System.out.println("Name already exist");
				tempUser = null;
				return ("Failed");
			}
		} catch (ServerNotActiveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ("Failed");
	}

	public LinkedHashMap<String, users> get(String name) {
		System.out.println("GET request from: "+name);
		if(userMap.containsKey(name))
			return userMap;
		else
			return new LinkedHashMap<String, users>();
	}

	public void live(String name) {
		users currentUser = userMap.get(name);
		currentUser.liveness();
	}

	public void close(String name) {
		System.out.println("CLOSE request from: "+name);
		userMap.remove(name);
	}

}
