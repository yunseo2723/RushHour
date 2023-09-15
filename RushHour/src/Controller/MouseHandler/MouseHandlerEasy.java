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
    public ImagePanelNormal imagePanelNormal;		//다음단계
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
        // 초기 위치와 마우스의 위치를 저장
        initialPoint = e.getPoint();
        initialMouse = new Point(e.getX(), e.getY());

        // 초기 위치에 해당하는 객체를 가져옴
        selectedObject = imagePanelEasy.getObjectAtPoint(initialPoint);
        // 가져온 객체가 있을 경우, 해당 객체의 초기 위치를 저장
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

                // 클리어 음악 재생 시간(7초) 이후에 배경음악을 다시 재생
                Timer timer = new Timer(1000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // 배경음악 재생
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

        // 자동차 객체를 터치한 경우 이동횟수 증가
        if (selectedObject != null && selectedObject != imagePanelEasy.objects[0]) {
            imagePanelEasy.increaseMoveCount();
        }

        int targetX = 495; // 목표 X 좌표
        if (imagePanelEasy.objects[0] != null && imagePanelEasy.objects[0].x > targetX) {
            DialogEasy dialog2 = new DialogEasy((Frame) SwingUtilities.getWindowAncestor(imagePanelEasy), parent, imagePanelEasy, imagePanelNormal,backgroundMusic);
            dialog2.showDialog(backgroundMusic);
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (selectedObject != null) {
        	
            // 새로운 X, Y 좌표
            int newX = selectedObjectInitialPosition.x + e.getX() - initialMouse.x;
            int newY = selectedObjectInitialPosition.y + e.getY() - initialMouse.y;

            int width = selectedObject.width;
            int height = selectedObject.height;

            // 객체의 너비와 높이에 따라 이동을 조정
            if (width > height) {
                // 가로로 이동
                int deltaX = newX - selectedObject.x;
                newX = selectedObject.x + (deltaX / 100) * 100; // 한 번에 100씩 이동하도록 조정
                newY = selectedObject.y;
            } else {
                // 세로로 이동
                int deltaY = newY - selectedObject.y;
                newY = selectedObject.y + (deltaY / 100) * 100; // 한 번에 100씩 이동하도록 조정
                newX = selectedObject.x;
            }

            // 객체가 패널 내에 위치하도록 제한
            newX = Math.max(Math.min(newX, 700 - width), 300);
            newY = Math.max(Math.min(newY, 700 - height), 300);

            // 겹치는 객체 확인
            Rectangle newObjectPosition = new Rectangle(newX, newY, width, height);
            if (!imagePanelEasy.isObjectOverlapping(selectedObject, newObjectPosition)) {
                // 겹치지 않는 경우에만 선택된 객체의 위치를 업데이트
                selectedObject.setLocation(newX, newY);
            }

            imagePanelEasy.repaint();
        }
    }
}