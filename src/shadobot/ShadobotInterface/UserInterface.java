package shadobot.ShadobotInterface;

/**
 * Created by shado on 3/25/2017.
 */
public final class UserInterface {
    //jank, try to unjank

    public static MainWindow mainWindow;

    public UserInterface(MainWindow mainWindow){
        this.mainWindow = mainWindow;
    }

    public void setMainWindow(MainWindow mainWindow){
        this.mainWindow = mainWindow;
    }

    public static void logAdd(String s){
        mainWindow.logAdd(s);
    }
}
