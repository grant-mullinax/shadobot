package shadobot.UI.ParameterInput;

/**
 * Created by shado on 4/9/2017.
 */
public abstract class ParameterInputElement {

    //todo try and have this contain the setbounds and add
    /*public ParameterInputElement(JFrame frame, int offset, IGuild guild){
        interfaceElement.setBounds(10+offset*150,60,140,30);
        frame.add(interfaceElement);
    }*/

    public abstract Object getValue();
}
