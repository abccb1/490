import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class checkTime extends Thread {
	LinkedHashMap<String, users> userMap;
	private boolean ret_val;
	private Iterator<users> it;

	public checkTime(LinkedHashMap<String, users> userMap) {
		this.userMap = userMap;
	}

	public void run() {
		while (true) {

			try {
				if (userMap.size() > 0)
					sleep(60000 / userMap.size());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			it = userMap.values().iterator();

			while (it.hasNext()) {
				users currentUser = it.next();
				ret_val = currentUser.period();
				if (!ret_val) {
					it.remove();
					System.out.println("Remove user: " + currentUser.getName());
				}
			}

		}
	}

}
