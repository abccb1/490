import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;

public interface remoteUser extends Remote{
	public LinkedHashMap<String, users> get(String name) throws RemoteException;
	public String reg(String username, String port) throws RemoteException;
	public void live(String name) throws RemoteException;
	public void close(String name) throws RemoteException;
}
