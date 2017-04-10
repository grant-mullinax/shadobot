package shadobot.UI;

import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.Shadobot;
import shadobot.UI.ParameterInput.*;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IVoiceChannel;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public final class MainWindow
{
	public static IGuild selectedGuild;

	public static Boolean debug = true;

	private HashMap<String,IGuild> guildRegister = new HashMap<String, IGuild>();
	private HashMap<String,IRole> roleRegister = new HashMap<String, IRole>();
	private HashMap<String,IVoiceChannel> voiceChannelRegister = new HashMap<String, IVoiceChannel>();

	private JFrame window;

	private JMenuBar menuBar;

	private JMenu serverMenu;
	private ButtonGroup serverGroup;

	// left side of pane
	private JButton refreshButton;

	// right side of layout
	private JTextPane log;
	private JTextField commandline;
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
		
		/* left side
		serverSide = new JPanel();
		serverSide.setLayout(null);
		serverSide.setBounds(10, 10, 160, 415);
		serverSide.setBorder(BorderFactory.createEtchedBorder());*/

		/*muteButton = new JButton("Mute");
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
		*/


		/*roleSelectBox = new JComboBox();
		roleSelectBox.setBounds(10,20,140,30);
		window.add(roleSelectBox);*/
		
		//to-do: functionality
		/*refreshButton = new JButton("Refresh");
		refreshButton.setBounds(10,370,80,30);
		window.add(refreshButton);*/
		
		// right side todo convert to setbounds positioning for right side
		/*logSide = new JPanel();
		logSide.setBounds(170, 10, 600, 415);
		logSide.setBorder(BorderFactory.createEtchedBorder());*/
		
		log = new JTextPane();
		log.setEditable(false);
		doc = log.getStyledDocument();
		docStyle = new SimpleAttributeSet();
		//log.setBounds(10,10,755,470);

		scrollableLog = new JScrollPane(log);
		scrollableLog.setBounds(10,10,755,360);
		window.add(scrollableLog);

		commandline = new JTextField();
		commandline.setBounds(10,380,140,30);
		commandline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event)
			{
				logAdd("Executing "+commandline.getText()+" from command line");
				Command command = Shadobot.commandListener.getRegisteredCommand(commandline.getText());
				if (command!=null){
					logAdd("found "+commandline.getText());
					for (Method method:command.getClass().getMethods()) {
						if (method.getName().equals("execute")) {

							Annotation[][] paramAnnotations = method.getParameterAnnotations();
							Class[] parameterTypes = method.getParameterTypes();

							for (int i = 0; i < parameterTypes.length; i++) {
								if (typeToInputMap.get(parameterTypes[i])!=null) {
									try {
										ParameterInputElement element = (ParameterInputElement)typeToInputMap.get(parameterTypes[i])
												.getConstructors()[0].newInstance(200+(130*i),380,140,30, window, selectedGuild);
									} catch (IllegalAccessException e) {
										e.printStackTrace();
									} catch (InstantiationException e) {
										e.printStackTrace();
									} catch (InvocationTargetException e) {
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
		});
		window.add(commandline);

		
		/*clearLogButton = new JButton("Clear Log");
		clearLogButton.setPreferredSize(new Dimension(180, 50));
		clearLogButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0)
					{
						logClear();
					}
		});
		logSide.add(clearLogButton, BorderLayout.SOUTH);*/
		
		// total window
		/*window.add(logSide, BorderLayout.EAST);
		window.add(serverSide, BorderLayout.WEST);*/
		window.setPreferredSize(new Dimension(775, 475));
		
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

	/*public void mutePressed(Boolean toggle)
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
	}*/

	public void addServer(IGuild guild){
		JRadioButtonMenuItem serverMenuItem = new JRadioButtonMenuItem(guild.getName());
		if (guild.getName().equals("OFF MY STOOP")) {
			serverMenuItem.setSelected(true);
			setSelectedGuild(guild);
		}
		serverGroup.add(serverMenuItem);
		serverMenu.add(serverMenuItem);
		serverMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Shadobot.UI.setSelectedGuild(e.getActionCommand());
				Shadobot.UI.logAdd("selected "+e.getActionCommand());
			}
		});

		guildRegister.put(guild.getName(),guild);
	}

	public void setSelectedGuild(IGuild guild){
		selectedGuild = guild;
	}

	public void setSelectedGuild(String name){
		setSelectedGuild(guildRegister.get(name));
	}

	public static final HashMap<Class,Class> typeToInputMap = new HashMap<Class, Class>(){ //is it too jank?
		{
			put(IChannel.class, IChannelInput.class);
			put(IRole.class, IRoleInput.class);
			put(IVoiceChannel.class, IVoiceChannelInput.class);
			put(String.class, StringInput.class);
		}
	};
}
