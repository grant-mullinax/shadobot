package shadobot.MiscListeners.UIListeners;

import shadobot.Shadobot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by shado on 3/27/2017.
 */
public class GuildSelectionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        Shadobot.UI.setSelectedGuild(e.getActionCommand());
        Shadobot.UI.logAdd("selected "+e.getActionCommand());
    }
}
