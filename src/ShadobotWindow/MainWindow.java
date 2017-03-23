package ShadobotWindow;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class MainWindow
{
	public MainWindow()
	{
		JFrame window = new JFrame("Shadobot");
		window.setSize(400, 300);
		JLabel label = new JLabel("Test");
		window.add(label);
		//window.pack();
		window.setVisible(true);
	}
}
