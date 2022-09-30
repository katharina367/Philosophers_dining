	/** 
	* Komplette Datei wurde mit Ausnahme der Methode 'public void propertyChange 
	* (PropertyChangeEvent evt)' freundlicherweise von Prof. Lorenz im Programmierkurs Sommer 
	* 2022 bereitgestellt
	*/

import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

//Beobachtermuster
@SuppressWarnings("serial")
public class PhilosopherFrame extends JFrame implements PropertyChangeListener {
	// Dynamische Listen fuer Philosophen und Gabeln
	private ArrayList<Philosopher> philosophers;
	private ArrayList<Fork> forks;

	// Dynamsiche Listen fuer Textfelder und Label zur grafischen Anzeige
	// Zeigen an, welcher Philosoph welche Gabel hat
	private ArrayList<JTextField> hasFork;
	// Zeigen den Zustand der Philosophen an
	private ArrayList<JLabel> status;

	public PhilosopherFrame(int anzahl) {
		forks = new ArrayList<>();
		philosophers = new ArrayList<>();
		hasFork = new ArrayList<>();
		status = new ArrayList<>();

		for (int i = 0; i < anzahl; ++i) {
			forks.add(new Fork());
		}

		for (int i = 0; i < anzahl; ++i) {
			philosophers.add(new Philosopher(forks.get(i % anzahl), forks.get((i + 1) % anzahl)));
			// Fuer jeden Philosophen 2 Textfelder und ein Label zur Anzeige
			hasFork.add(new JTextField());
			hasFork.get(2 * i).setEditable(false);
			hasFork.add(new JTextField());
			hasFork.get(2 * i + 1).setEditable(false);
			status.add(new JLabel());
		}

		setLayout(new GridLayout(anzahl, 3));

		for (int i = 0; i < anzahl; ++i) {
			philosophers.get(i).addPropertyChangeListener(this);
			add(hasFork.get(2 * i));
			add(new JLabel("Philosopher " + (i + 1), JLabel.CENTER));
			add(hasFork.get(2 * i + 1));
			add(status.get(i));
		}
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	// Teilaufgabe f)
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch((Zustand) evt.getNewValue()) {
			case DENKEN:
				hasFork.get((int)evt.getSource() * 2).setText("");
				hasFork.get(((int)evt.getSource() * 2) + 1).setText("");
				status.get((int)evt.getSource()).setText("denkt");
				break;
			case HUNGER_HABEN:
				status.get((int)evt.getSource()).setText("hat Hunger");
				break;
			case HAT_LINKE_GABEL_AUFGENOMMEN:
				hasFork.get((int)evt.getSource() * 2).setText("X");
				status.get((int)evt.getSource()).setText("hat linke Gabel");
				break;
			case HAT_RECHTE_GABEL_AUFGENOMMEN:
				hasFork.get(((int)evt.getSource() * 2) + 1).setText("X");
				status.get((int)evt.getSource()).setText("hat rechte Gabel");
				break;
			case ESSEN:
				status.get((int)evt.getSource()).setText("isst");
				break;
			default:
				break;
		}
	}
}
