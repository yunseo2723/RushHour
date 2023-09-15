package Controller.Frame;

import java.awt.Frame;
import javax.swing.*;
import Controller.MouseHandler.*;
import View.Dialog.*;
import View.Panel.*;
import View.etc.*;
import Model.ImagePanel.*;
//view
public class CarMovementFrame extends JFrame {
	private static final long serialVersionUID = 1L; 
	//The serializable class CarMovementFrame does not declare a static final serialVersionUID field of type long??
	
    // 사용자의 버튼 클릭에 따라 패널을 갈아끼며 다양한 메뉴를 볼 수 있도록
    public static final int BEGINNING_PANEL = -1; // 시작메뉴 패널
    public static final int SELECT_PANEL = 0; // 단계선택 페이지
    public static final int IMAGE_PANEL_HARD = 1; // 이미지 패널
    public static final int IMAGE_PANEL_EASY = 2; // 이미지 패널
    public static final int IMAGE_PANEL_NORMAL = 3; // 이미지 패널
    public static final int SETTING_PANEL = 4; // 설정 패널

    // swapPanel함수를 통해 패널을 갈아낄 수 있도록 하기 위함
    // 생성은 GameFrame의 생성자가 불려진 이후
    public BeginningPanel beginningPanel; // 시작 메뉴 패널
    public SelectPanel selectPanel; // 단계선택 페이지
    public ImagePanelHard imagePanelHard; // 이미지 패널
    public ImagePanelEasy imagePanelEasy; // 이미지 패널
    public ImagePanelNormal imagePanelNormal; // 이미지 패널
    public DialogEasy dialogEasy;
    public Frame parentFrame;
    public BackgroundMusic backgroundMusic;
    public SettingPanel settingPanel;
    
    public CarMovementFrame(BackgroundMusic backgroundMusic) {
    	this.backgroundMusic = backgroundMusic;
        // "Rush Hour"라는 제목을 가진 JFrame 윈도우를 생성합니다.
        setTitle("러시아워");

        // 프레임이 닫힐 때 애플리케이션을 종료하기 위해 종료 동작을 설정합니다.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 프레임의 크기를 1015x1045 픽셀로 설정합니다.
        setSize(1015, 1045);

        // 프레임의 크기 조정을 비활성화합니다.
        setResizable(false);

        // ImagePanel 객체를 생성합니다.
        imagePanelHard = new ImagePanelHard(this);
        imagePanelEasy = new ImagePanelEasy(this);
        imagePanelNormal = new ImagePanelNormal(this);
        parentFrame = new JFrame();
        dialogEasy = new DialogEasy(parentFrame, this, imagePanelEasy, imagePanelNormal, backgroundMusic);
        selectPanel = new SelectPanel(this, imagePanelHard, imagePanelEasy, imagePanelNormal);
        settingPanel = new SettingPanel(backgroundMusic.getClip(), this);
        
        // 프레임의 레이아웃을 null로 설정합니다.
        setLayout(null);

        // 메뉴화면을 컨텐트팬으로 지정합니다.
        beginningPanel = new BeginningPanel(this);
        setContentPane(beginningPanel);

        // MouseHandler 객체를 생성하고, ImagePanel에 등록합니다.
        MouseHandlerHard mouseHandler = new MouseHandlerHard(imagePanelNormal, imagePanelHard, this, backgroundMusic);
        imagePanelHard.addMouseListener(mouseHandler);
        imagePanelHard.addMouseMotionListener(mouseHandler);
        MouseHandlerEasy mouseHandler2 = new MouseHandlerEasy(imagePanelNormal, imagePanelEasy, this, backgroundMusic);
        imagePanelEasy.addMouseListener(mouseHandler2);
        imagePanelEasy.addMouseMotionListener(mouseHandler2);
        MouseHandlerNormal mouseHandler3 = new MouseHandlerNormal(imagePanelHard,imagePanelEasy, imagePanelNormal, this, backgroundMusic);
        imagePanelNormal.addMouseListener(mouseHandler3);
        imagePanelNormal.addMouseMotionListener(mouseHandler3);
    }

    public void run() {
        setVisible(true);
    }

    public void swapPanel(int panelID) {
        if (panelID == BEGINNING_PANEL) {
            setContentPane(beginningPanel);
        } else if (panelID == SELECT_PANEL) {
            setContentPane(selectPanel);
        } else if (panelID == IMAGE_PANEL_HARD) {
            setContentPane(imagePanelHard);
        } else if (panelID == IMAGE_PANEL_EASY) {
        	setContentPane(imagePanelEasy);
        } else if (panelID == IMAGE_PANEL_NORMAL) {
        	setContentPane(imagePanelNormal);
        } else if (panelID == SETTING_PANEL) {
        	setContentPane(settingPanel);
        }

        revalidate();
    }
}
