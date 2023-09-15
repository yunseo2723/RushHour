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
        // MouseHandler에서 imagePanelEasy와 관련된 코드 추가
        // 마우스 버튼이 눌릴 때 호출되는 이벤트 핸들러입니다.
        // 초기 위치와 마우스의 위치를 저장합니다.
        initialPoint = e.getPoint();
        initialMouse = new Point(e.getX(), e.getY());

        // 초기 위치에 해당하는 객체를 가져옵니다.
        selectedObject = imagePanelHard.getObjectAtPoint(initialPoint);
        // 가져온 객체가 있을 경우, 해당 객체의 초기 위치를 저장합니다.
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
        imagePanelHard.isDragging = false;

        // 자동차 객체를 터치한 경우에만 이동횟수를 증가시킵니다.
        if (selectedObject != null && selectedObject != imagePanelHard.objects[0]) {
            imagePanelHard.increaseMoveCount();
        }

        int targetX = 695; // 목표 X 좌표
        if (imagePanelHard.objects[0] != null && imagePanelHard.objects[0].x > targetX) {
            DialogHard dialog3 = new DialogHard((Frame) SwingUtilities.getWindowAncestor(imagePanelHard), parent, imagePanelHard, imagePanelNormal);
            dialog3.showDialog(backgroundMusic);
            imagePanelHard.timer.stop();
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (selectedObject != null) {
        	
            // 새로운 X, Y 좌표 계산합니다.
            int newX = selectedObjectInitialPosition.x + e.getX() - initialMouse.x;
            int newY = selectedObjectInitialPosition.y + e.getY() - initialMouse.y;

            int width = selectedObject.width;
            int height = selectedObject.height;

            // 객체의 너비와 높이에 따라 이동을 조정합니다.
            if (width > height) {
                // 가로로 이동합니다.
                int deltaX = newX - selectedObject.x;
                newX = selectedObject.x + (deltaX / 100) * 100; // 한 번에 100씩 이동하도록 조정
                newY = selectedObject.y;
            } else {
                // 세로로 이동합니다.
                int deltaY = newY - selectedObject.y;
                newY = selectedObject.y + (deltaY / 100) * 100; // 한 번에 100씩 이동하도록 조정
                newX = selectedObject.x;
            }

            // 객체가 패널 내에 위치하도록 제한합니다.
            newX = Math.max(Math.min(newX, 900 - width), 100);
            newY = Math.max(Math.min(newY, 900 - height), 100);

            // 겹치는 객체를 확인합니다.
            Rectangle newObjectPosition = new Rectangle(newX, newY, width, height);
            if (!imagePanelHard.isObjectOverlapping(selectedObject, newObjectPosition)) {
                // 겹치지 않는 경우에만 선택된 객체의 위치를 업데이트합니다.
                selectedObject.setLocation(newX, newY);
            }

            // 패널을 다시 그립니다.
            imagePanelHard.repaint();
        }
    }
}