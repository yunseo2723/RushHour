package Model.ImagePanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Controller.Frame.*;
import View.etc.*;

public class ImagePanelEasy extends JPanel {
	private static final long serialVersionUID = 1L; 
	
	public CarMovementFrame parent;
    public Image background;
    public Image[] images;
    public Rectangle[] objects;
    public boolean isDragging;
    public int moveCount = 0;
    public Timer timer;
    public int elapsedTime = 0;
    public JLabel moveCountLabel;
    public JLabel elapsedTimeLabel;

    public ImagePanelEasy(CarMovementFrame parent) {
    	
    	objects = new Rectangle[6];

        ImageIcon icon = new ImageIcon("Image/BackEasy.png");
        ImageIcon[] imageIcons = {
        	new ImageIcon("Image/PoliceL.png"),
            new ImageIcon("Image/BlackL.png"),
            new ImageIcon("Image/MintL.png"),
            new ImageIcon("Image/RedL.png"),
            new ImageIcon("Image/TaxiU.png"),
            new ImageIcon("Image/WhiteU.png")
        };


        background = icon.getImage();
        images = new Image[imageIcons.length];
        for (int i = 0; i < imageIcons.length; i++) {
            images[i] = imageIcons[i].getImage();
        }

        // 자동차 위치 설정
        placeObjectsWithoutOverlap();

        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                elapsedTime++;
                repaint();
            }
        });
        timer.start();
        
        this.parent = parent;
        BackButton back = new BackButton(parent);
        add(back);
        back.setPreferredSize(new Dimension(200,50));
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 50));
        
        ResetButton btn = new ResetButton(this);
        add(btn);
        btn.setPreferredSize(new Dimension(200,50));
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        
        moveCountLabel = new JLabel("이동횟수: " + moveCount);
        moveCountLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 25));
        moveCountLabel.setForeground(Color.BLACK);
        add(moveCountLabel);
        
        elapsedTimeLabel = new JLabel("경과 시간: " + elapsedTime + "초");
        elapsedTimeLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 25));
        elapsedTimeLabel.setForeground(Color.BLACK);
        add(elapsedTimeLabel);
        
    }
    
    
    public void increaseMoveCount() {
        moveCount++;
        moveCountLabel.setText("이동횟수: " + moveCount);
    }
    
    public void placeObjectsWithoutOverlap() {
    	
        objects[0] = new Rectangle(300, 500, 195, 95); // Car
        objects[1] = new Rectangle(300, 400, 195, 95); // BlackL
        objects[2] = new Rectangle(500, 300, 195, 95); // MintL
        objects[3] = new Rectangle(400, 600, 195, 95); // RedL
        objects[4] = new Rectangle(500, 400, 95, 195); // TaxiU
        objects[5] = new Rectangle(600, 500, 95, 195); // WhiteU
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;


        g2d.drawImage(background, 0, 0, this);

        g2d.drawImage(images[0], objects[0].x, objects[0].y, objects[0].width, objects[0].height, this); // Car
        g2d.drawImage(images[1], objects[1].x, objects[1].y, objects[1].width, objects[1].height, this); // BlackL
        g2d.drawImage(images[2], objects[2].x, objects[2].y, objects[2].width, objects[2].height,this); // MintL
        g2d.drawImage(images[3], objects[3].x, objects[3].y, objects[3].width, objects[3].height,this); // RedL
        g2d.drawImage(images[4], objects[4].x, objects[4].y, objects[4].width, objects[4].height,this); // TaxiU
        g2d.drawImage(images[5], objects[5].x, objects[5].y, objects[5].width, objects[5].height,this); // WhiteU
        // 이동 횟수와 경과 시간을 업데이트
           moveCountLabel.setText("이동횟수: " + moveCount);
           elapsedTimeLabel.setText("경과시간: " + elapsedTime + "초");

           if (objects[0] != null && objects[0].x > 495) {
               timer.stop();
           }
           else
        	   timer.start();
    }
    
    public boolean isObjectOverlapping(Rectangle selectedObject, Rectangle newObjectPosition) {
        if (selectedObject == null || newObjectPosition == null) {
            return false;
        }

        // 겹치는 객체가 있는지 확인
        for (Rectangle object : objects) {
            if (object != selectedObject && newObjectPosition.intersects(object)) {
                return true;
            }
        }

        return false;
    }
    
    public Rectangle getObjectAtPoint(Point point) {
        // 주어진 좌표에 해당하는 객체를 반환
        for (Rectangle object : objects) {
            if (object.contains(point)) {
                return object;
            }
        }
        return null;
    }
}