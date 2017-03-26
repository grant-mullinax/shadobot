package ShadobotWindow;

import shadobot.Shadobot;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.Javacord;
import de.btobastian.javacord.entities.VoiceChannel;
import de.btobastian.javacord.entities.permissions.Permissions;
import de.btobastian.javacord.entities.permissions.Role;

import javax.swing.JButton;
import javax.swing.JScrollPane;

//TO-DO: SCROLLABLE TEXT PANE

public class MainWindow
{
	private JFrame window;

	// left side of pane
	private JPanel serverSide;
	private JButton muteButton;
	private JButton refreshButton;
	// right side of layout
	private JPanel logSide;
	private JButton clearLogButton;
	private JTextPane log;
	private StyledDocument doc;
	private SimpleAttributeSet docStyle;
	private JScrollPane logScroll;
	
	public MainWindow()
	{
		window = new JFrame("Shadobot");
		window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		window.setLayout(null);
		
		// left side
		serverSide = new JPanel();
		serverSide.setBounds(10, 10, 235, 280);
		
		muteButton = new JButton("Mute");
		muteButton.setPreferredSize(new Dimension(100, 50));
		muteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				toggleMute();
			}
		});
		serverSide.add(muteButton, BorderLayout.SOUTH);
		
		//to-do: functionality
		refreshButton = new JButton("Refresh");
		refreshButton.setPreferredSize(new Dimension(80, 50));
		refreshButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				refreshUserTable();
			}
		});
		serverSide.add(refreshButton,  BorderLayout.SOUTH);
		
		// right side
		logSide = new JPanel();
		logSide.setBounds(255, 10, 230, 280);
		
		log = new JTextPane();
		log.setEditable(false);
		doc = log.getStyledDocument();
		docStyle = new SimpleAttributeSet();
		log.setPreferredSize(new Dimension(215, 180));
		logScroll = new JScrollPane(log);
		
		logSide.add(logScroll, BorderLayout.NORTH);
		
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
		window.setPreferredSize(new Dimension(500, 300));
		
		window.pack();
		
		window.setResizable(false);
		
		window.setVisible(true);
	}
	
	public void logAdd(String s)
	{
		try
		{
			doc.insertString(doc.getLength(), s + "\n", docStyle);
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
	
	public void toggleMute()
	{
		muteButton.setText(muteButton.getText() == "Mute" ? "Unmute" : "Mute");
	}
	
	public void refreshUserTable()
	{
		DiscordAPI api = Shadobot.getAPI();
		
		for(VoiceChannel vc : api.getVoiceChannels())
		{
			if(vc.getName().equals("General"))
			{
				for(Role r : vc.getServer().getRoles())
				{
					logAdd(r.toString());
					logAdd(vc.getOverwrittenPermissions(r).toString());
				}
			}
		}
	}
}
