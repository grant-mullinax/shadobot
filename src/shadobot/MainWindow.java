package shadobot;

import shadobot.MiscListeners.UIListeners.GuildSelectionListener;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public final class MainWindow
{
	public static IGuild selectedGuild;

	private HashMap<String,IGuild> guildRegister = new HashMap<String, IGuild>();
	private HashMap<String,IRole> roleRegister = new HashMap<String, IRole>();
	private HashMap<String,IVoiceChannel> voiceChannelRegister = new HashMap<String, IVoiceChannel>();

	private JFrame window;

	private JMenuBar menuBar;

	private JMenu serverMenu;
	private ButtonGroup serverGroup;

	private GuildSelectionListener guildSelectionListener = new GuildSelectionListener(); //todo maybe handle it like
	// the mute button

	// left side of pane
	private JPanel serverSide;
	private JButton muteButton;
	private JButton unMuteButton;
	private JComboBox roleSelectBox;
	private JComboBox voiceChannelSelectBox;
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

		window.setJMenuBar(menuBar);
		
		// left side
		serverSide = new JPanel();
		serverSide.setLayout(null);
		serverSide.setBounds(10, 10, 255, 415);
		serverSide.setBorder(BorderFactory.createEtchedBorder());

		//todo position these more intuitively
		muteButton = new JButton("Mute");
		muteButton.setBounds(10,20,80,30);
		muteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				mutePressed(true);
			}
		});
		serverSide.add(muteButton);

		unMuteButton = new JButton("Unmute");
		unMuteButton.setBounds(10,60,80,30);
		unMuteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				mutePressed(false);
			}
		});
		serverSide.add(unMuteButton);

		roleSelectBox = new JComboBox();
		roleSelectBox.setBounds(100,20,140,30);
		serverSide.add(roleSelectBox);

		voiceChannelSelectBox = new JComboBox();
		voiceChannelSelectBox.setBounds(100,60,140,30);
		serverSide.add(voiceChannelSelectBox);
		
		//to-do: functionality
		refreshButton = new JButton("Refresh");
		refreshButton.setBounds(10,370,80,30);
		serverSide.add(refreshButton);
		
		// right side todo convert to setbounds positioning for right side
		logSide = new JPanel();
		logSide.setBounds(280, 10, 600, 415);
		//logSide.setBorder(BorderFactory.createEtchedBorder());
		
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
	
	public void mutePressed(Boolean toggle)
	{

		for (IUser user: voiceChannelRegister.get((String) voiceChannelSelectBox.getSelectedItem()).getConnectedUsers
				()){
			if (!user.getRolesForGuild(selectedGuild).contains(roleRegister.get(roleSelectBox.getSelectedItem()))){
				try {
					selectedGuild.setMuteUser(user, toggle);
				} catch (RateLimitException e) {
					System.err.print("Sending messages too quickly!");
					e.printStackTrace();
					try {
						TimeUnit.SECONDS.sleep((long)1);
					}catch (InterruptedException e2){}

					try {
						selectedGuild.setMuteUser(user, toggle);
					} catch (RateLimitException e2) {
					} catch (DiscordException e2) {
					} catch (MissingPermissionsException e2) {}

				} catch (DiscordException e) {
					System.err.print(e.getErrorMessage());
					e.printStackTrace();
				} catch (MissingPermissionsException e) {}
			try {
				TimeUnit.SECONDS.sleep((long)0.2);
			}catch (InterruptedException e){}
			}
		}
	}

	public void addServer(IGuild guild){
		JRadioButtonMenuItem serverMenuItem = new JRadioButtonMenuItem(guild.getName());
		if (guild.getName().equals("OFF MY STOOP")) {
			serverMenuItem.setSelected(true);
			setSelectedGuild(guild);
		}
		serverGroup.add(serverMenuItem);
		serverMenu.add(serverMenuItem);
		serverMenuItem.addActionListener(guildSelectionListener);

		guildRegister.put(guild.getName(),guild);
	}

	public void setSelectedGuild(IGuild guild){ //todo handle these with their own combobox extention
		selectedGuild = guild;

		roleRegister = new HashMap<String, IRole>();
		String[] roleNames = new String[guild.getRoles().size()];
		for (int i = 0; i < guild.getRoles().size(); i++) {
			IRole role = guild.getRoles().get(i);
			roleNames[i] = role.getName();
			roleRegister.put(role.getName(),role);
		}
		DefaultComboBoxModel roleBoxModel = new DefaultComboBoxModel(roleNames);
		roleSelectBox.setModel(roleBoxModel);

		voiceChannelRegister = new HashMap<String, IVoiceChannel>();
		String[] channelNames = new String[guild.getVoiceChannels().size()];
		for (int i = 0; i < guild.getVoiceChannels().size(); i++) {
			IVoiceChannel voiceChannel = guild.getVoiceChannels().get(i);
			channelNames[i] = voiceChannel.getName();
			voiceChannelRegister.put(voiceChannel.getName(),voiceChannel);
			if (voiceChannel.getName().equals("speaker")) voiceChannelSelectBox.setSelectedIndex(i);
		}
		DefaultComboBoxModel voiceChannelBoxModel = new DefaultComboBoxModel(channelNames);
		voiceChannelSelectBox.setModel(voiceChannelBoxModel);
	}

	public void setSelectedGuild(String name){
		setSelectedGuild(guildRegister.get(name));
	}
	
}
