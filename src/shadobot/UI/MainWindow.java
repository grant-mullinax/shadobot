package shadobot.UI;

import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.Shadobot;
import shadobot.UI.UIComponents.ParameterInput.*;
import sx.blah.discord.handle.obj.*;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public final class MainWindow {
	private Shadobot shadobot;

	private Command targetCommand;
	private Method targetMethod;
	private ArrayList<JComponent>  parameterInputComponents = new ArrayList<JComponent>();

	private JFrame window;

	private JMenuBar menuBar;

	private JMenu serverMenu;
	private JMenu channelListenMenu;
	private ButtonGroup serverGroup;
	private ButtonGroup channelListenGroup;

	public static IGuild selectedGuild;

	private HashMap<String,IGuild> guildRegister = new HashMap<String, IGuild>(); //todo maybe i can remove the need for this using objects
	private HashMap<IChannel,JCheckBoxMenuItem> channelRegister = new HashMap<IChannel,JCheckBoxMenuItem>();

	private JTextField commandline;
	private JButton executeButton;

	private JTextPane log;
	private JScrollPane scrollableLog;

	private StyledDocument doc;
	private SimpleAttributeSet docStyle;
	
	public MainWindow() {

		window = new JFrame("Shadobot");
		window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		window.setLayout(null);

		//Create the menu bar.
		menuBar = new JMenuBar();

		serverMenu = new JMenu("Server");
		menuBar.add(serverMenu);

		channelListenMenu = new JMenu("Channel Listen");
		menuBar.add(channelListenMenu);

		serverGroup = new ButtonGroup();
		//channelListenGroup = new ButtonGroup();

		window.setJMenuBar(menuBar);
		
		log = new JTextPane();
		log.setEditable(false);
		doc = log.getStyledDocument();
		docStyle = new SimpleAttributeSet();

		scrollableLog = new JScrollPane(log);
		scrollableLog.setBounds(10,10,755,360);
		window.add(scrollableLog);

		commandline = new JTextField();
		commandline.setBounds(10,380,140,30);
		commandline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Command command = Shadobot.getCommandListener().getRegisteredCommand(Shadobot.getCommandListener().getPrefix()+commandline.getText()
						.toLowerCase());
				if (command!=null){
					for (Method method:command.getClass().getMethods()) {
						if (method.getName().equals("execute")) {
							for (JComponent component:parameterInputComponents) window.remove(component);
							targetCommand = command;
							targetMethod = method;

							parameterInputComponents = new ArrayList<JComponent>();
							Class[] parameterTypes = method.getParameterTypes();

							//int componentOffset = 160;
							for (int i = 0; i < parameterTypes.length; i++) {
								if (typeToInputMap.get(parameterTypes[i])!=null) {
									try {
										JComponent element = (JComponent)typeToInputMap.get(parameterTypes[i]).getConstructor(IGuild.class)
												.newInstance(selectedGuild);
										element.setBounds(160+(i*110),380,100,30);
										//componentOffset+=300+10;
										window.add(element);
										parameterInputComponents.add(element);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
							window.validate();
							window.repaint();
						}
					}
				}
			}
		});
		window.add(commandline);

		executeButton = new JButton("Execute");
		executeButton.setBounds(680,380,80,30);
		executeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				executeCommand();
			}
		});
		window.add(executeButton);

		window.setPreferredSize(new Dimension(775, 475));
		window.setLocation(300,100);
		
		window.pack();
		
		window.setResizable(false);
		
		window.setVisible(true);
	}
	
	public void logAdd(String s) {
		try {
			doc.insertString(doc.getLength(), s + "\n", docStyle);

			final JScrollBar verticalScrollBar = scrollableLog.getVerticalScrollBar();
			verticalScrollBar.setValue(verticalScrollBar.getMaximum()+16);
		} catch (BadLocationException e) {
			System.out.println(e);
		}
	}

	public void executeCommand(){
		Class[] parameterTypes = targetMethod.getParameterTypes();
		Object[] params = new Object[parameterTypes.length];

		int consoleSuppliedParams = 0;
		for (int i = 0; i < parameterTypes.length; i++) {
			if (parameterTypes[i].equals(IGuild.class)) {
				params[i] = selectedGuild;
			}else {
				params[i] = ((ParameterInputComponent) parameterInputComponents.get(consoleSuppliedParams)).getValue();
				consoleSuppliedParams++;
			}
		}

		try {
			Shadobot.UI.logAdd("Executing "+targetCommand.getClass().getSimpleName()+" from console");
			targetMethod.invoke(targetCommand, params);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public void addGuild(IGuild guild){
		JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(guild.getName());
		if (guild.getName().equals("test zone")) {
			menuItem.setSelected(true);
			setSelectedGuild(guild);
		}
		serverGroup.add(menuItem);
		serverMenu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSelectedGuild(e.getActionCommand());
			}
		});

		guildRegister.put(guild.getName(),guild);
	}

	public void setSelectedGuild(IGuild guild){
		selectedGuild = guild;
		Shadobot.UI.logAdd("selected "+guild.getName());

		commandline.setText("");
		for (JComponent component:parameterInputComponents) window.remove(component);
		parameterInputComponents = new ArrayList<JComponent>();
	}

	public void setSelectedGuild(String name){
		setSelectedGuild(guildRegister.get(name));
	}

	public void addConnectedChannels(IGuild guild){
		//channelRegister = new HashMap<String, IChannel>();
		//channelListenMenu.removeAll();

		channelListenMenu.add(new JMenuItem(guild.getName()));
		channelListenMenu.addSeparator();

		for (IChannel channel : guild.getChannels()) {
			JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(channel.getName());

			channelListenMenu.add(menuItem);

			channelRegister.put(channel,menuItem);
		}
		channelListenMenu.add(new JMenuItem(""));
	}

	public boolean isListeningToChannel(IChannel channel){
		return channelRegister.get(channel).getState();
	}

	public static final HashMap<Class,Class> typeToInputMap = new HashMap<Class, Class>(){ //is it too jank?
		{
			put(IChannel.class, IChannelInput.class);
			put(IRole.class, IRoleInput.class);
			put(IVoiceChannel.class, IVoiceChannelInput.class);
			put(String.class, StringInput.class);
			put(IUser.class, IUserInput.class);
		}
	};
}
