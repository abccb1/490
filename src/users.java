public class users {
	private String name;
	private String ip;
	private String port;
	private boolean status;
	private long checkedTime;

	public users(String name, String ip, String port) {
		this.name = name;
		this.ip = ip;
		this.port = port;
		status = true;
		checkedTime = System.nanoTime();
	}

	public void changeStatus(boolean status) {
		this.status = status;
	}

	public void print() {
		System.out.println(name + " " + ip + " " + port);
	}

	public String getUserInformation(){
		return name + " " + ip + " " + port+'\n';
	}

	public boolean check(){
		return status;
	}

	public void liveness() {
		checkedTime = System.nanoTime();
	}

	public boolean period(){
		long duration = System.nanoTime() - checkedTime;
		double seconds = (double)duration / 1000000000.0;
		if(seconds > 60 ){
			status = false;
		}
		return status;
	}

	public String getPort() {
		return port;
	}

	public String getIp() {
		return ip;
	}

	public String getName() {
		return name;
	}

}
