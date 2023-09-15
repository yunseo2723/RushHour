package View.Panel;
import java.awt.*;
import java.awt.event.*;
import Controller.Frame.*;
import Model.ImagePanel.*;
import javax.swing.*;

public class SelectPanel extends JPanel {
	private static final long serialVersionUID = 1L; 
	
    // GameFrame�� ���� ������ ���� ���۷���
    public CarMovementFrame parent;
    public ImagePanelHard imagePanelHard;
    public ImagePanelEasy imagePanelEasy;
    public ImagePanelNormal imagePanelNormal;

    // ��� �̹���
    public ImageIcon bgImageicon = new ImageIcon("Image/StartBack2.png");
    public Image backgroundPanelImage = bgImageicon.getImage();

    public SelectPanel(CarMovementFrame parent, ImagePanelHard imagePanelHard, ImagePanelEasy imagePanelEasy, ImagePanelNormal imagePanelNormal) {
    	

    	this.parent = parent;
        this.imagePanelHard = imagePanelHard;
        this.imagePanelEasy = imagePanelEasy;
        this.imagePanelNormal = imagePanelNormal;
        // ���ϴ� ��ġ�� ��ư�� ���̱� ���� ��ġ������ ����
        setLayout(null);

        // 4���� ��ư�� �ް� �ִ� panel�� ����
        // 1. ���� ���� ��ư
        JButton easyButton = new JButton(new ImageIcon("Image/Easy.png"));
        easyButton.setSize(300, 110);
        easyButton.setLocation(350, 110);
        easyButton.setBorderPainted(false);
        easyButton.setFocusPainted(false);
        easyButton.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                parent.swapPanel(CarMovementFrame.IMAGE_PANEL_EASY);
        		easyButton.setIcon(new ImageIcon("Image/Easy.png"));
        	}
        	public void mouseEntered(MouseEvent e) {
        		easyButton.setIcon(new ImageIcon("Image/EasyFocus.png"));
        	}
        	public void mouseExited(MouseEvent e) {
        		easyButton.setIcon(new ImageIcon("Image/Easy.png"));
        	}
        });
        easyButton.addActionListener(e -> {
            imagePanelEasy.placeObjectsWithoutOverlap();
            imagePanelEasy.moveCount = 0; // �̵� Ƚ�� �ʱ�ȭ
            imagePanelEasy.elapsedTime = 0; // Ÿ�̸� �ʱ�ȭ
            imagePanelEasy.repaint();
        });
        add(easyButton);

        JButton normalButton = new JButton(new ImageIcon("Image/Normal.png"));
        normalButton.setSize(450, 110);
        normalButton.setLocation(275, 330);
        normalButton.setBorderPainted(false);
        normalButton.setFocusPainted(false);
        normalButton.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                parent.swapPanel(CarMovementFrame.IMAGE_PANEL_NORMAL);
        		normalButton.setIcon(new ImageIcon("Image/Normal.png"));
        	}
        	public void mouseEntered(MouseEvent e) {
        		normalButton.setIcon(new ImageIcon("Image/NormalFocus.png"));
        	}
        	public void mouseExited(MouseEvent e) {
        		normalButton.setIcon(new ImageIcon("Image/Normal.png"));
        	}
        });
        normalButton.addActionListener(e -> {
            imagePanelNormal.placeObjectsWithoutOverlap();
            imagePanelNormal.moveCount = 0; // �̵� Ƚ�� �ʱ�ȭ
            imagePanelNormal.elapsedTime = 0; // Ÿ�̸� �ʱ�ȭ
            imagePanelNormal.repaint();
        });
        add(normalButton);

        JButton hardButton = new JButton(new ImageIcon("Image/Hard.png"));
        hardButton.setSize(300, 110);
        hardButton.setLocation(350, 550);
        hardButton.setBorderPainted(false);
        hardButton.setFocusPainted(false);
        hardButton.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                parent.swapPanel(CarMovementFrame.IMAGE_PANEL_HARD);
        		hardButton.setIcon(new ImageIcon("Image/Hard.png"));
        	}
        	public void mouseEntered(MouseEvent e) {
        		hardButton.setIcon(new ImageIcon("Image/HardFocus.png"));
        	}
        	public void mouseExited(MouseEvent e) {
        		hardButton.setIcon(new ImageIcon("Image/Hard.png"));
        	}
        });
        hardButton.addActionListener(e -> {
            imagePanelHard.placeObjectsWithoutOverlap();
            imagePanelHard.moveCount = 0; // �̵� Ƚ�� �ʱ�ȭ
            imagePanelHard.elapsedTime = 0; // Ÿ�̸� �ʱ�ȭ
            imagePanelHard.repaint();
        });
        add(hardButton);
        
        JButton back = new JButton(new ImageIcon("Image/Back.png"));
        
        back.setSize(300, 110);
        back.setLocation(350, 770);
        back.setBorderPainted(false);
        back.setFocusPainted(false);
        
        back.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                parent.swapPanel(CarMovementFrame.BEGINNING_PANEL);
        		back.setIcon(new ImageIcon("Image/Back.png"));
        	}
        	public void mouseEntered(MouseEvent e) {
        		back.setIcon(new ImageIcon("Image/BackFocus.png"));
        	}
        	public void mouseExited(MouseEvent e) {
        		back.setIcon(new ImageIcon("Image/Back.png"));
        	}
        });
        add(back);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // �׷��� ������Ʈ ����
        // ��� �̹���
        g.drawImage(backgroundPanelImage, 0, 0, bgImageicon.getIconWidth(), bgImageicon.getIconHeight(), null);
    }
}