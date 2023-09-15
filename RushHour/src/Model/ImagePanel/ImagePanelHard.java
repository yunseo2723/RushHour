package Model.ImagePanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Controller.Frame.*;
import View.etc.*;

public class ImagePanelHard extends JPanel {
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
    public JLabel clearLabel;

    public ImagePanelHard(CarMovementFrame parent) {
    	objects = new Rectangle[24];
        // 이미지 아이콘들을 불러옵니다.
        ImageIcon icon = new ImageIcon("Image/BackHard.png");
        ImageIcon[] imageIcons = {
        	new ImageIcon("Image/PoliceL.png"),
            new ImageIcon("Image/BlackL.png"),
            new ImageIcon("Image/BlackU.png"),
            new ImageIcon("Image/LimuZL.png"),
            new ImageIcon("Image/LimuZU.png"),
            new ImageIcon("Image/MintL.png"),
            new ImageIcon("Image/MintU.png"),
            new ImageIcon("Image/RedL.png"),
            new ImageIcon("Image/RedU.png"),
            new ImageIcon("Image/TaxiL.png"),
            new ImageIcon("Image/TaxiU.png"),
            new ImageIcon("Image/TruckL.png"),
            new ImageIcon("Image/TruckU.png"),
            new ImageIcon("Image/WhiteL.png"),
            new ImageIcon("Image/WhiteU.png")
        };

        // 이미지 아이콘을 이미지로 변환합니다.
        background = icon.getImage();
        images = new Image[imageIcons.length];
        for (int i = 0; i < imageIcons.length; i++) {
            images[i] = imageIcons[i].getImage();
        }

        // 초기 객체의 위치를 설정합니다.
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
    	
        objects[0] = new Rectangle(100, 500, 195, 95); // Car
        objects[1] = new Rectangle(500, 400, 195, 95); // BlackL
        objects[2] = new Rectangle(200, 600, 195, 95); // BlackL
        objects[3] = new Rectangle(500, 800, 195, 95); // BlackL
        objects[4] = new Rectangle(100, 200, 95, 195); // BlackU
        objects[5] = new Rectangle(400, 400, 95, 295); // LimuZU
        objects[6] = new Rectangle(600, 500, 95, 295); // LimuZU
        objects[7] = new Rectangle(200, 100, 195, 95); // MintL
        objects[8] = new Rectangle(600, 300, 195, 95); // MintL
        objects[9] = new Rectangle(200, 700, 195, 95); // MintL
        objects[10] = new Rectangle(600, 100, 95, 195); // RedU
    	objects[11] = new Rectangle(200, 800, 195, 95); // TaxiL
        objects[12] = new Rectangle(700, 800, 195, 95); // TaxiL
        objects[13] = new Rectangle(700, 500, 95, 195); // TaxiU
        objects[14] = new Rectangle(300, 400, 95, 195); // TaxiU
        objects[15] = new Rectangle(400, 100, 195, 95); // TruckL
        objects[16] = new Rectangle(400, 200, 195, 95); // TruckL
        objects[17] = new Rectangle(400, 300, 195, 95); // TruckL
        objects[18] = new Rectangle(800, 200, 95, 195); // TruckU
        objects[19] = new Rectangle(800, 600, 95, 195); // TruckU
        objects[20] = new Rectangle(100, 600, 95, 195); // TruckU
        objects[21] = new Rectangle(300, 200, 95, 195); // WhiteU
        objects[22] = new Rectangle(700, 100, 95, 195); // WhiteU
        objects[23] = new Rectangle(500, 500, 95, 195); // WhiteU
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // 그리기 전에 배경 이미지를 그립니다.
        g2d.drawImage(background, 0, 0, this);
        //맵 1
        // 객체들을 그립니다.
        g2d.drawImage(images[0], objects[0].x, objects[0].y, objects[0].width, objects[0].height, this); // Car
        g2d.drawImage(images[1], objects[1].x, objects[1].y, objects[1].width, objects[1].height, this); // BlackL
        g2d.drawImage(images[1], objects[2].x, objects[2].y, objects[2].width, objects[2].height,this); // BlackL
        g2d.drawImage(images[1], objects[3].x, objects[3].y, objects[3].width, objects[3].height,this); // BlackL
        g2d.drawImage(images[2], objects[4].x, objects[4].y, objects[4].width, objects[4].height,this); // BlackU
        g2d.drawImage(images[4], objects[5].x, objects[5].y, objects[5].width, objects[5].height,this); // LimuZU
        g2d.drawImage(images[4], objects[6].x, objects[6].y, objects[6].width, objects[6].height,this); // LimuZU
        g2d.drawImage(images[5], objects[7].x, objects[7].y, objects[7].width, objects[7].height,this); // MintL
        g2d.drawImage(images[5], objects[8].x, objects[8].y, objects[8].width, objects[8].height,this); // MintL
        g2d.drawImage(images[5], objects[9].x, objects[9].y, objects[9].width, objects[9].height,this); // MintL
        g2d.drawImage(images[8], objects[10].x, objects[10].y, objects[10].width, objects[10].height,this); // RedU
        g2d.drawImage(images[9], objects[11].x, objects[11].y, objects[11].width, objects[11].height,this); // TaxiL
        g2d.drawImage(images[9], objects[12].x, objects[12].y, objects[12].width, objects[12].height,this); // TaxiL
        g2d.drawImage(images[10], objects[13].x, objects[13].y, objects[13].width, objects[13].height,this); // TaxiU
        g2d.drawImage(images[10], objects[14].x, objects[14].y, objects[14].width, objects[14].height,this); // TaxiU
        g2d.drawImage(images[11], objects[15].x, objects[15].y, objects[15].width, objects[15].height,this); // TruckL
        g2d.drawImage(images[11], objects[16].x, objects[16].y, objects[16].width, objects[16].height,this); // TruckL
        g2d.drawImage(images[11], objects[17].x, objects[17].y, objects[17].width, objects[17].height,this); // TruckL
        g2d.drawImage(images[12], objects[18].x, objects[18].y, objects[18].width, objects[18].height,this); // TruckU
        g2d.drawImage(images[12], objects[19].x, objects[19].y, objects[19].width, objects[19].height,this); // TruckU
        g2d.drawImage(images[12], objects[20].x, objects[20].y, objects[20].width, objects[20].height,this); // TruckU
        g2d.drawImage(images[14], objects[21].x, objects[21].y, objects[21].width, objects[21].height,this); // WhiteU
        g2d.drawImage(images[14], objects[22].x, objects[22].y, objects[22].width, objects[22].height,this); // WhiteU
        g2d.drawImage(images[14], objects[23].x, objects[23].y, objects[23].width, objects[23].height,this); // WhiteU

        // 이동 횟수와 경과 시간을 업데이트합니다.
           moveCountLabel.setText("이동횟수: " + moveCount);
           elapsedTimeLabel.setText("경과시간: " + elapsedTime + "초");

           if (objects[0] != null && objects[0].x > 595) {
               timer.stop();
           }
           else
        	   timer.start();
    }
    
    public boolean isObjectOverlapping(Rectangle selectedObject, Rectangle newObjectPosition) {
        if (selectedObject == null || newObjectPosition == null) {
            return false;
        }

        // 겹치는 객체가 있는지 확인합니다.
        for (Rectangle object : objects) {
            if (object != selectedObject && newObjectPosition.intersects(object)) {
                return true;
            }
        }

        return false;
    }
    
    


    public Rectangle getObjectAtPoint(Point point) {
        // 주어진 좌표에 해당하는 객체를 반환합니다.
        for (Rectangle object : objects) {
            if (object.contains(point)) {
                return object;
            }
        }
        return null;
    }
    
    

}
