package View.Panel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import Controller.Frame.*;

public class SettingPanel extends JPanel {
	private static final long serialVersionUID = 1L; 
	
    public Clip music;
    public JSlider volumeSlider;
    public CarMovementFrame parent;
    public ImageIcon bgImageicon = new ImageIcon("Image/StartBack2.png");
    public Image backgroundPanelImage = bgImageicon.getImage();

    public SettingPanel(Clip music, CarMovementFrame parent) {
    	this.music=music;
    	this.parent=parent;
        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        volumeSlider.setMajorTickSpacing(10);
        volumeSlider.setPaintTicks(true);
        volumeSlider.addChangeListener(e -> adjustVolume());

        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(new JLabel("배경음악 볼륨:"));
        add(volumeSlider);
        
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

    private void adjustVolume() {
        if (music != null) {
            int volume = volumeSlider.getValue();
            float gain = (float) volume / 100f;

            try {
                FloatControl gainControl = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(20f * (float) Math.log10(gain));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // 그래픽 컴포넌트 설정
        // 배경 이미지
        g.drawImage(backgroundPanelImage, 0, 0, bgImageicon.getIconWidth(), bgImageicon.getIconHeight(), null);
    }
    
}
