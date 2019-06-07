/*
 * Name: Christine Johnson
 * Date: 6/4/19
 * Program Overview: Create a window with a button, which when clicked will start the Kruskals program which prints its MST to output.txt
 * Comment: If the GUI does not work, running Kruskals on its own will send its output to the output.txt file as well
 */


package prg4;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class KruskalAlg extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public KruskalAlg(Composite parent, int style) {
		super(parent, style);
		
		Button btnStartKruskalsAlgorithm = new Button(this, SWT.NONE);
		btnStartKruskalsAlgorithm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				new Kruskals();
			}
		});
		btnStartKruskalsAlgorithm.setBounds(91, 109, 262, 87);
		btnStartKruskalsAlgorithm.setText("Start Kruskal's Algorithm");
		
		

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
