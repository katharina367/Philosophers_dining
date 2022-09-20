import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.ThreadLocalRandom;

public class Philosopher extends Thread {
	public int id;
	static int number = 0;
	public Fork left;
	public Fork right;
	public Zustand state = Zustand.DENKEN;
	private PropertyChangeSupport supp = new PropertyChangeSupport(this);
	
	public Philosopher (Fork l, Fork r) {
		id = setID();
		left = l;
		right = r;
		start();
	}
	
	public int setID() {
		return number++;
	}
	
	public void run() {
		while(true) {
			try {
				sleep(ThreadLocalRandom.current().nextInt(1000,6000));
			} catch (InterruptedException e) {}
			StateChange();
			switch(state) {
				case HUNGER_HABEN:
					left.grab();
					break;
				case HAT_LINKE_GABEL_AUFGENOMMEN:
					right.grab();
					break;
				case DENKEN:
					left.drop();
					right.drop();
					break;
				default:
					break;
			}
		}
		
	}
	
	public void StateChange () {
		if (state == Zustand.ESSEN) {
			state = Zustand.DENKEN;
			supp.firePropertyChange(new PropertyChangeEvent(this.id, "state", Zustand.ESSEN, state));

		} else {
			state = Zustand.values()[state.ordinal() + 1];
			supp.firePropertyChange(new PropertyChangeEvent(this.id, "state", Zustand.values()[state.ordinal() - 1], state));

		}
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.supp.addPropertyChangeListener(listener);
		
	}
	
}
