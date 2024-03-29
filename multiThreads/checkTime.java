import java.util.Iterator;
import java.util.LinkedHashMap;


public class checkTime extends Thread {
	LinkedHashMap<String, users> userMap;
	private boolean ret_val;
	private Iterator<users> it;
	public checkTime(LinkedHashMap<String, users> userMap){
		this.userMap = userMap;
	}

	public void run() {
		while(true){
			it = userMap.values().iterator();
			while ( it.hasNext() ) {
                users currentUser = it.next();
                ret_val = currentUser.period();
                if(!ret_val) {
                    userMap.remove(currentUser.getName());
                }
			}
		}
	}
}
