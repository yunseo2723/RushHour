package Controller.MouseHandler;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
import Controller.Frame.*;
import View.Dialog.*;
import View.etc.*;
import Model.ImagePanel.*;

public class MouseHandlerNormal extends MouseAdapter {
	public CarMovementFrame parent;
    public ImagePanelHard imagePanelHard;		//�����ܰ�
    public ImagePanelEasy imagePanelEasy;
    public ImagePanelNormal imagePanelNormal;
    public Rectangle selectedObject;
    public Point initialPoint;
    public Point initialMouse;
    public Point selectedObjectInitialPosition;
    public BackgroundMusic backgroundMusic;

    public MouseHandlerNormal(ImagePanelHard imagePanelHard, ImagePanelEasy imagePanelEasy, ImagePanelNormal imagePanelNormal, CarMovementFrame parent, BackgroundMusic backgroundMusic) {
    	this.parent = parent;
        this.imagePanelHard = imagePanelHard;
        this.imagePanelEasy = imagePanelEasy;
        this.imagePanelNormal = imagePanelNormal;
        this.backgroundMusic =backgroundMusic;
    }

    public void mousePressed(MouseEvent e) {
        // MouseHandler���� imagePanelEasy�� ���õ� �ڵ� �߰�
        // ���콺 ��ư�� ���� �� ȣ��Ǵ� �̺�Ʈ �ڵ鷯�Դϴ�.
        // �ʱ� ��ġ�� ���콺�� ��ġ�� �����մϴ�.
        initialPoint = e.getPoint();
        initialMouse = new Point(e.getX(), e.getY());

        // �ʱ� ��ġ�� �ش��ϴ� ��ü�� �����ɴϴ�.
        selectedObject = imagePanelNormal.getObjectAtPoint(initialPoint);
        // ������ ��ü�� ���� ���, �ش� ��ü�� �ʱ� ��ġ�� �����մϴ�.
        if (selectedObject != null) {
            selectedObjectInitialPosition = new Point(selectedObject.x, selectedObject.y);
        }
    }
    
    public void mouseReleased(MouseEvent e) {
    	if (selectedObject != null) {
        	
        	try {
                File effectSoundFile = new File("Music/CarEffect.wav");
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(effectSoundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();

                // Ŭ���� ���� ��� �ð�(7��) ���Ŀ� ��������� �ٽ� ���
                Timer timer = new Timer(1000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // ������� ���
                    	clip.stop();
                    }
                });
                timer.setRepeats(false);
                timer.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
    	}
        imagePanelNormal.isDragging = false;
        if (selectedObject != null && selectedObject != imagePanelNormal.objects[0]) {
            imagePanelNormal.increaseMoveCount();
        }
        int targetX = 595; // ��ǥ X ��ǥ
        if (imagePanelNormal.objects[0] != null && imagePanelNormal.objects[0].x > targetX) {
            DialogNormal dialog3 = new DialogNormal((Frame) SwingUtilities.getWindowAncestor(imagePanelNormal),parent, imagePanelHard, imagePanelEasy, imagePanelNormal);
            dialog3.showDialog(backgroundMusic);
        }
    }

    public void mouseDragged(MouseEvent e) {

        // ���õ� ��ü�� ���� ��쿡�� ó���մϴ�.
        if (selectedObject != null) {
        	
            // ���ο� X, Y ��ǥ ����մϴ�.
            int newX = selectedObjectInitialPosition.x + e.getX() - initialMouse.x;
            int newY = selectedObjectInitialPosition.y + e.getY() - initialMouse.y;

            int width = selectedObject.width;
            int height = selectedObject.height;

            // ��ü�� �ʺ�� ���̿� ���� �̵��� �����մϴ�.
            if (width > height) {
                // ���η� �̵��մϴ�.
                int deltaX = newX - selectedObject.x;
                newX = selectedObject.x + (deltaX / 100) * 100; // �� ���� 100�� �̵��ϵ��� ����
                newY = selectedObject.y;
            } else {
                // ���η� �̵��մϴ�.
                int deltaY = newY - selectedObject.y;
                newY = selectedObject.y + (deltaY / 100) * 100; // �� ���� 100�� �̵��ϵ��� ����
                newX = selectedObject.x;
            }

            // ��ü�� �г� ���� ��ġ�ϵ��� �����մϴ�.
            newX = Math.max(Math.min(newX, 800 - width), 200);
            newY = Math.max(Math.min(newY, 800 - height), 200);

            // ��ġ�� ��ü�� Ȯ���մϴ�.
            Rectangle newObjectPosition = new Rectangle(newX, newY, width, height);
            if (!imagePanelNormal.isObjectOverlapping(selectedObject, newObjectPosition)) {
                // ��ġ�� �ʴ� ��쿡�� ���õ� ��ü�� ��ġ�� ������Ʈ�մϴ�.
                selectedObject.setLocation(newX, newY);
            }

            // �г��� �ٽ� �׸��ϴ�.
            imagePanelNormal.repaint();
        }
    }
}