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

public class MouseHandlerEasy extends MouseAdapter {
	public CarMovementFrame parent;
    public ImagePanelNormal imagePanelNormal;		//�����ܰ�
    public ImagePanelEasy imagePanelEasy;
    public Rectangle selectedObject;
    public Point initialPoint;
    public Point initialMouse;
    public Point selectedObjectInitialPosition;
    public BackgroundMusic backgroundMusic;

    public MouseHandlerEasy(ImagePanelNormal imagePanelNormal, ImagePanelEasy imagePanelEasy, CarMovementFrame parent,BackgroundMusic backgroundMusic ) {
    	this.imagePanelNormal = imagePanelNormal;
    	this.imagePanelEasy = imagePanelEasy;
    	this.parent = parent;
    	this.backgroundMusic = backgroundMusic;
    }

    public void mousePressed(MouseEvent e) {
        // �ʱ� ��ġ�� ���콺�� ��ġ�� ����
        initialPoint = e.getPoint();
        initialMouse = new Point(e.getX(), e.getY());

        // �ʱ� ��ġ�� �ش��ϴ� ��ü�� ������
        selectedObject = imagePanelEasy.getObjectAtPoint(initialPoint);
        // ������ ��ü�� ���� ���, �ش� ��ü�� �ʱ� ��ġ�� ����
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
        imagePanelEasy.isDragging = false;

        // �ڵ��� ��ü�� ��ġ�� ��� �̵�Ƚ�� ����
        if (selectedObject != null && selectedObject != imagePanelEasy.objects[0]) {
            imagePanelEasy.increaseMoveCount();
        }

        int targetX = 495; // ��ǥ X ��ǥ
        if (imagePanelEasy.objects[0] != null && imagePanelEasy.objects[0].x > targetX) {
            DialogEasy dialog2 = new DialogEasy((Frame) SwingUtilities.getWindowAncestor(imagePanelEasy), parent, imagePanelEasy, imagePanelNormal,backgroundMusic);
            dialog2.showDialog(backgroundMusic);
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (selectedObject != null) {
        	
            // ���ο� X, Y ��ǥ
            int newX = selectedObjectInitialPosition.x + e.getX() - initialMouse.x;
            int newY = selectedObjectInitialPosition.y + e.getY() - initialMouse.y;

            int width = selectedObject.width;
            int height = selectedObject.height;

            // ��ü�� �ʺ�� ���̿� ���� �̵��� ����
            if (width > height) {
                // ���η� �̵�
                int deltaX = newX - selectedObject.x;
                newX = selectedObject.x + (deltaX / 100) * 100; // �� ���� 100�� �̵��ϵ��� ����
                newY = selectedObject.y;
            } else {
                // ���η� �̵�
                int deltaY = newY - selectedObject.y;
                newY = selectedObject.y + (deltaY / 100) * 100; // �� ���� 100�� �̵��ϵ��� ����
                newX = selectedObject.x;
            }

            // ��ü�� �г� ���� ��ġ�ϵ��� ����
            newX = Math.max(Math.min(newX, 700 - width), 300);
            newY = Math.max(Math.min(newY, 700 - height), 300);

            // ��ġ�� ��ü Ȯ��
            Rectangle newObjectPosition = new Rectangle(newX, newY, width, height);
            if (!imagePanelEasy.isObjectOverlapping(selectedObject, newObjectPosition)) {
                // ��ġ�� �ʴ� ��쿡�� ���õ� ��ü�� ��ġ�� ������Ʈ
                selectedObject.setLocation(newX, newY);
            }

            imagePanelEasy.repaint();
        }
    }
}