package shadobot;

import shadobot.MiscListeners.UIListeners.GuildSelectionListener;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IGuild;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public final class MainWindow
{
	public static IGuild selectedGuild;

	private HashMap<String,IGuild> guildRegister = new HashMap<String, IGuild>();

	private JFrame window;

	private JMenuBar menuBar;

	private JMenu serverMenu;
	private ButtonGroup serverGroup;

	private GuildSelectionListener guildSelectionListener = new GuildSelectionListener(); //todo maybe handle it like
	// the mute button

	// left side of pane
	private JPanel serverSide;
	private JButton muteButton;
	private JComboBox roleSelectBox;
	private JButton refreshButton;

	// right side of layout
	private JPanel logSide;
	private JButton clearLogButton;
	private JTextPane log;
	private JScrollPane scrollableLog;

	private StyledDocument doc;
	private SimpleAttributeSet docStyle;
	
	public void init(IDiscordClient client)
	{
		window = new JFrame("Shadobot");
		window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		window.setLayout(null);
		window.setLocation(300,100);

		//Create the menu bar.
		menuBar = new JMenuBar();

		serverMenu = new JMenu("Server");
		serverMenu.setMnemonic(KeyEvent.VK_A);
		serverMenu.getAccessibleContext().setAccessibleDescription(
				"The only menu in this program that has menu items");
		menuBar.add(serverMenu);

		serverGroup = new ButtonGroup();

		/*for (IGuild guild: client.getGuilds()) {
			JRadioButtonMenuItem serverMenuItem = new JRadioButtonMenuItem(guild.getName());
			serverMenuItem.setSelected(true);
			serverMenuItem.setMnemonic(KeyEvent.VK_R);
			serverGroup.add(serverMenuItem);
			serverMenu.add(serverMenuItem);
		}*/

		window.setJMenuBar(menuBar);
		
		// left side
		serverSide = new JPanel();
		serverSide.setBounds(10, 10, 235, 280);
		
		muteButton = new JButton("Mute");
		muteButton.setPreferredSize(new Dimension(80, 40));
		muteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				mutePressed();
			}
		});
		serverSide.add(muteButton, BorderLayout.SOUTH);

		roleSelectBox = new JComboBox();
		roleSelectBox.setPreferredSize(new Dimension(80, 40));
		serverSide.add(roleSelectBox, BorderLayout.SOUTH);
		
		//to-do: functionality
		refreshButton = new JButton("Refresh");
		refreshButton.setPreferredSize(new Dimension(80, 50));
		serverSide.add(refreshButton,  BorderLayout.SOUTH);
		
		// right side
		logSide = new JPanel();
		logSide.setBounds(255, 10, 600, 425);
		
		log = new JTextPane();
		log.setEditable(false);
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
		
		window.setResizable(false);
		
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
	
	public void mutePressed()
	{
		muteButton.setText(muteButton.getText() == "Mute" ? "Unmute" : "Mute");
	}

	public void addServer(IGuild guild){
		JRadioButtonMenuItem serverMenuItem = new JRadioButtonMenuItem(guild.getName());
		if (guild.getName().equals("OFF MY STOOP")) serverMenuItem.setSelected(true);
		serverGroup.add(serverMenuItem);
		serverMenu.add(serverMenuItem);
		serverMenuItem.addActionListener(guildSelectionListener);

		guildRegister.put(guild.getName(),guild);
	}

	public void setSelectedGuild(IGuild guild){
		selectedGuild = guild;
		String[] roleNames = new String[guild.getRoles().size()];
		for (int i = 0; i < guild.getRoles().size(); i++) {
			roleNames[i] = guild.getRoles().get(i).getName();
		}
		DefaultComboBoxModel model = new DefaultComboBoxModel(roleNames);
		roleSelectBox.setModel(model);
	}

	public void setSelectedGuild(String name){
		setSelectedGuild(guildRegister.get(name));
	}
	
}
