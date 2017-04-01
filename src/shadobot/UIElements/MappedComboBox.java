package shadobot.UIElements;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;

/**
 * its typed as in it has a type, not that you type.
 */
public class MappedComboBox<E> extends JComboBox{
    HashMap<String,E> register;

    public void register(List<E> list){
        for(E item: list){
            //register.put(item.getClass().getMethods(),item);
        }
    }
}
