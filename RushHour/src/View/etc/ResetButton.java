package View.etc;
import java.awt.event.*;
import javax.swing.*;
import Controller.Frame.*;
import View.Panel.*;
import Model.ImagePanel.*;

public class ResetButton extends JButton {
	private static final long serialVersionUID = 1L; 
	
	public CarMovementFrame carMovementFrame;
	public SelectPanel selectPanel;
    public ImagePanelHard imagePanelHard;
    public ImagePanelEasy imagePanelEasy;
    public ImagePanelNormal imagePanelNormal;

    public ResetButton(ImagePanelHard imagePanelHard) {
        super("재시도");
        this.imagePanelHard = imagePanelHard;
        
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                imagePanelHard.placeObjectsWithoutOverlap();
                imagePanelHard.repaint();
                imagePanelHard.moveCount=0;
                imagePanelHard.elapsedTime=0;
            }
        });
    }
    
    public ResetButton(ImagePanelEasy imagePanelEasy) {
        super("재시도");
        this.imagePanelEasy = imagePanelEasy;
        
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                imagePanelEasy.placeObjectsWithoutOverlap();
                imagePanelEasy.repaint();
                imagePanelEasy.moveCount=0;
                imagePanelEasy.elapsedTime=0;
            }
        });
    }
    
    public ResetButton(ImagePanelNormal imagePanelNormal) {
        super("재시도");
        this.imagePanelNormal = imagePanelNormal;
        
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                imagePanelNormal.placeObjectsWithoutOverlap();
                imagePanelNormal.repaint();
                imagePanelNormal.moveCount=0;
                imagePanelNormal.elapsedTime=0;
            }
        });
    }
}