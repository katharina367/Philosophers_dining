
public class Fork {
	public boolean status;
	
	public Fork () {
		status = true;
	}
	
	public boolean forkStatus() {
		return status;
	}
	
	
	public synchronized void grab() {
		while (!forkStatus()) {
			try {
				wait();
			} catch (Exception e) {}
		}
		status = false;
	}
	
	public synchronized void drop() {
		status = true;
		notifyAll();
	}
}
