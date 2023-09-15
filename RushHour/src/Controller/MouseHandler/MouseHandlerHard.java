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

public class MouseHandlerHard extends MouseAdapter {
	public CarMovementFrame parent;
    public ImagePanelHard imagePanelHard;
    public ImagePanelNormal imagePanelNormal;
    public Rectangle selectedObject;
    public Point initialPoint;
    public Point initialMouse;
    public Point selectedObjectInitialPosition;
    public BackgroundMusic backgroundMusic;

    public MouseHandlerHard(ImagePanelNormal imagePanelNormal, ImagePanelHard imagePanelHard, CarMovementFrame parent, BackgroundMusic backgroundMusic) {
        this.imagePanelHard = imagePanelHard;
        this.imagePanelNormal = imagePanelNormal;
    	this.parent = parent;
    	this.backgroundMusic = backgroundMusic;
    }

    public void mousePressed(MouseEvent e) {
        // MouseHandler���� imagePanelEasy�� ���õ� �ڵ� �߰�
        // ���콺 ��ư�� ���� �� ȣ��Ǵ� �̺�Ʈ �ڵ鷯�Դϴ�.
        // �ʱ� ��ġ�� ���콺�� ��ġ�� �����մϴ�.
        initialPoint = e.getPoint();
        initialMouse = new Point(e.getX(), e.getY());

        // �ʱ� ��ġ�� �ش��ϴ� ��ü�� �����ɴϴ�.
        selectedObject = imagePanelHard.getObjectAtPoint(initialPoint);
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
        imagePanelHard.isDragging = false;

        // �ڵ��� ��ü�� ��ġ�� ��쿡�� �̵�Ƚ���� ������ŵ�ϴ�.
        if (selectedObject != null && selectedObject != imagePanelHard.objects[0]) {
            imagePanelHard.increaseMoveCount();
        }

        int targetX = 695; // ��ǥ X ��ǥ
        if (imagePanelHard.objects[0] != null && imagePanelHard.objects[0].x > targetX) {
            DialogHard dialog3 = new DialogHard((Frame) SwingUtilities.getWindowAncestor(imagePanelHard), parent, imagePanelHard, imagePanelNormal);
            dialog3.showDialog(backgroundMusic);
            imagePanelHard.timer.stop();
        }
    }

    public void mouseDragged(MouseEvent e) {
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
            newX = Math.max(Math.min(newX, 900 - width), 100);
            newY = Math.max(Math.min(newY, 900 - height), 100);

            // ��ġ�� ��ü�� Ȯ���մϴ�.
            Rectangle newObjectPosition = new Rectangle(newX, newY, width, height);
            if (!imagePanelHard.isObjectOverlapping(selectedObject, newObjectPosition)) {
                // ��ġ�� �ʴ� ��쿡�� ���õ� ��ü�� ��ġ�� ������Ʈ�մϴ�.
                selectedObject.setLocation(newX, newY);
            }

            // �г��� �ٽ� �׸��ϴ�.
            imagePanelHard.repaint();
        }
    }
}