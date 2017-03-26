package shadobot.ShadobotInterface;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//TO-DO: SCROLLABLE TEXT PANE

public final class MainWindow
{
	private JFrame window;

	// left side of pane
	private JButton muteButton;
	private JPanel serverSide;

	// right side of layout
	private JPanel logSide;
	private JButton clearLogButton;
	private JTextPane log;
	private JScrollPane scrollableLog;

	private StyledDocument doc;
	private SimpleAttributeSet docStyle;
	
	public MainWindow()
	{
		window = new JFrame("Shadobot");
		window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		window.setLayout(null);
		
		// left side
		serverSide = new JPanel();
		serverSide.setBounds(10, 10, 235, 280);
		
		muteButton = new JButton("Mute");
		muteButton.setBounds(10, 10, 180, 50);
		serverSide.add(muteButton, BorderLayout.SOUTH);
		
		// right side
		logSide = new JPanel();
		logSide.setBounds(255, 10, 600, 425);
		
		log = new JTextPane();
		doc = log.getStyledDocument();
		docStyle = new SimpleAttributeSet();
		log.setPreferredSize(new Dimension(550, 350));

		scrollableLog = new JScrollPane(log);
		logSide.add(scrollableLog, BorderLayout.NORTH);
		
		clearLogButton = new JButton("Clear Log");
		clearLogButton.setPreferredSize(new Dimension(180, 50));
		clearLogButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0)
					{
						logClear();
					}
				});
		logSide.add(clearLogButton, BorderLayout.SOUTH);
		
		// total window
		window.add(logSide, BorderLayout.EAST);
		window.add(serverSide, BorderLayout.WEST);
		window.setPreferredSize(new Dimension(900, 490));
		
		window.pack();
		
		window.setVisible(true);
	}
	
	public void logAdd(String s)
	{
		try
		{
			doc.insertString(doc.getLength(), s + "\n", docStyle);

			final JScrollBar verticalScrollBar = scrollableLog.getVerticalScrollBar();
			verticalScrollBar.setValue(verticalScrollBar.getMaximum()+16);
		} 
		catch (BadLocationException e)
		{
			System.out.println(e);
		}
	}

	public void logClear()
	{
		log.setText("");
	}
}
