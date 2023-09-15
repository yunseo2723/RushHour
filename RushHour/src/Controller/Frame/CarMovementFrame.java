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
	
    // ������� ��ư Ŭ���� ���� �г��� ���Ƴ��� �پ��� �޴��� �� �� �ֵ���
    public static final int BEGINNING_PANEL = -1; // ���۸޴� �г�
    public static final int SELECT_PANEL = 0; // �ܰ輱�� ������
    public static final int IMAGE_PANEL_HARD = 1; // �̹��� �г�
    public static final int IMAGE_PANEL_EASY = 2; // �̹��� �г�
    public static final int IMAGE_PANEL_NORMAL = 3; // �̹��� �г�
    public static final int SETTING_PANEL = 4; // ���� �г�

    // swapPanel�Լ��� ���� �г��� ���Ƴ� �� �ֵ��� �ϱ� ����
    // ������ GameFrame�� �����ڰ� �ҷ��� ����
    public BeginningPanel beginningPanel; // ���� �޴� �г�
    public SelectPanel selectPanel; // �ܰ輱�� ������
    public ImagePanelHard imagePanelHard; // �̹��� �г�
    public ImagePanelEasy imagePanelEasy; // �̹��� �г�
    public ImagePanelNormal imagePanelNormal; // �̹��� �г�
    public DialogEasy dialogEasy;
    public Frame parentFrame;
    public BackgroundMusic backgroundMusic;
    public SettingPanel settingPanel;
    
    public CarMovementFrame(BackgroundMusic backgroundMusic) {
    	this.backgroundMusic = backgroundMusic;
        // "Rush Hour"��� ������ ���� JFrame �����츦 �����մϴ�.
        setTitle("���þƿ�");

        // �������� ���� �� ���ø����̼��� �����ϱ� ���� ���� ������ �����մϴ�.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // �������� ũ�⸦ 1015x1045 �ȼ��� �����մϴ�.
        setSize(1015, 1045);

        // �������� ũ�� ������ ��Ȱ��ȭ�մϴ�.
        setResizable(false);

        // ImagePanel ��ü�� �����մϴ�.
        imagePanelHard = new ImagePanelHard(this);
        imagePanelEasy = new ImagePanelEasy(this);
        imagePanelNormal = new ImagePanelNormal(this);
        parentFrame = new JFrame();
        dialogEasy = new DialogEasy(parentFrame, this, imagePanelEasy, imagePanelNormal, backgroundMusic);
        selectPanel = new SelectPanel(this, imagePanelHard, imagePanelEasy, imagePanelNormal);
        settingPanel = new SettingPanel(backgroundMusic.getClip(), this);
        
        // �������� ���̾ƿ��� null�� �����մϴ�.
        setLayout(null);

        // �޴�ȭ���� ����Ʈ������ �����մϴ�.
        beginningPanel = new BeginningPanel(this);
        setContentPane(beginningPanel);

        // MouseHandler ��ü�� �����ϰ�, ImagePanel�� ����մϴ�.
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
