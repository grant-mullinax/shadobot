package shadobot.ShadobotWindow;

import com.sun.istack.internal.Nullable;

/**
 * Created by shado on 3/25/2017.
 */
public class LogElement {
    private String text;
    private LogElement next;

    public LogElement(String text, @Nullable LogElement next){
        this.text = text;
        this.next = next;
    }

    public LogElement getText(){
        return next;
    }

    public LogElement getNext(){
        return next;
    }

    public LogElement push(){
        return next;
    }
}
