package View.etc;
import java.awt.event.*;
import javax.swing.*;
import Controller.Frame.*;

public class BackButton extends JButton {
	private static final long serialVersionUID = 1L; 
	
    public CarMovementFrame parent;

    public BackButton(CarMovementFrame parent) {
    	super("뒤로가기");
    	this.parent = parent;
        
    	addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.swapPanel(CarMovementFrame.SELECT_PANEL);
                
            }
        });
    }
}
