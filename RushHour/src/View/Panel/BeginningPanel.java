package View.Panel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Controller.Frame.*;

public class BeginningPanel extends JPanel {
	private static final long serialVersionUID = 1L; 
	
    // GameFrame�� ���� ������ ���� ���۷���
    public CarMovementFrame parent;
    // ��� �̹���
    public ImageIcon bgImageicon = new ImageIcon("Image/StartBack.png");
    public Image backgroundPanelImage = bgImageicon.getImage();
    public BeginningPanel(CarMovementFrame parent) {
        this.parent = parent; // ������ ���۷��� ���� => GameFrame�� �ۼ��� �Լ��� �̿��ϱ� ����
        setLayout(null);
        
        
            
            JButton startButton = new JButton(new ImageIcon("Image/Start.png"));
            startButton.setBorderPainted(false);
            startButton.setFocusPainted(false);
            startButton.setSize(600, 110);
            startButton.setLocation(200, 420);
            startButton.addMouseListener(new MouseAdapter() {
            	public void mouseClicked(MouseEvent e) {
                    parent.swapPanel(CarMovementFrame.SELECT_PANEL);
            		startButton.setIcon(new ImageIcon("Image/Start.png"));
            	}
                public void mouseEntered(MouseEvent e) {
                    startButton.setIcon(new ImageIcon("Image/StartFocus.png"));
                }
                public void mouseExited(MouseEvent e) {
                    startButton.setIcon(new ImageIcon("Image/Start.png"));
                }
            });
            add(startButton);
            
            JButton settingButton = new JButton(new ImageIcon("Image/Setting.png"));
            settingButton.setBorderPainted(false);
            settingButton.setFocusPainted(false);
            settingButton.setSize(600, 110);
            settingButton.setLocation(200, 570);
            settingButton.addMouseListener(new MouseAdapter() {
            	public void mouseClicked(MouseEvent e) {
                    parent.swapPanel(CarMovementFrame.SETTING_PANEL);
                    settingButton.setIcon(new ImageIcon("Image/Setting.png"));
            	}
                public void mouseEntered(MouseEvent e) {
                	settingButton.setIcon(new ImageIcon("Image/SettingFocus.png"));
                }
                public void mouseExited(MouseEvent e) {
                	settingButton.setIcon(new ImageIcon("Image/Setting.png"));
                }
            });
            add(settingButton);
        
        JButton exitButton = new JButton(new ImageIcon("Image/Exit.png"));
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        exitButton.setSize(300, 110);
        exitButton.setLocation(350, 720);
        exitButton.addMouseListener(new MouseAdapter() {
        	public void mouseEntered(MouseEvent e) {
        		exitButton.setIcon(new ImageIcon("Image/ExitFocus.png"));
        	}
    
        	@Override
        	public void mouseExited(MouseEvent e) {
        		exitButton.setIcon(new ImageIcon("Image/Exit.png"));
        	}
        });
        exitButton.addActionListener(e -> {
        	System.exit(0);
        });
        add(exitButton);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // �׷��� ������Ʈ ����
        // ��� �̹���
        g.drawImage(backgroundPanelImage, 0, 0, bgImageicon.getIconWidth(), bgImageicon.getIconHeight(), null);
    }
}