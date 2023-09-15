package View.Dialog;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;
import Controller.Frame.*;
import Controller.Frame.Record;
import View.etc.*;
import Model.ImagePanel.*;

public class DialogEasy extends JDialog {
	private static final long serialVersionUID = 1L; 
	
    public CarMovementFrame parent;
    public ImagePanelEasy imagePanelEasy;
    public ImagePanelNormal imagePanelNormal;
    public String nickname;
    public List<Record> ranking;
    public BackgroundMusic backgroundMusic;

    public DialogEasy(Frame parentFrame, CarMovementFrame parent, ImagePanelEasy imagePanelEasy, ImagePanelNormal imagePanelNormal, BackgroundMusic backgroundMusic) {
        super(parentFrame, true);
        setUndecorated(true); // â�ݱ� ����
        setLayout(new BorderLayout());

        this.backgroundMusic = backgroundMusic;
        
        JLabel messageLabel = new JLabel("Clear");
        messageLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel additionalLabel = new JLabel();
        additionalLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel moveCountLabel = new JLabel("<html>�̵� Ƚ��: " + imagePanelEasy.moveCount + "ȸ<br>�ɸ� �ð�: " + imagePanelEasy.elapsedTime + "��</html>");
        moveCountLabel.setHorizontalAlignment(JLabel.CENTER);

        this.parent = parent;
        this.imagePanelEasy = imagePanelEasy;
        this.imagePanelNormal = imagePanelNormal;
        this.ranking = new ArrayList<>();
        loadRanking();
        
        JButton backButton = new JButton("�ڷΰ���");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.swapPanel(CarMovementFrame.SELECT_PANEL);
                dispose();
            }
        });
        
        JButton retryButton = new JButton("�ٽ��ϱ�");
        retryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.swapPanel(CarMovementFrame.IMAGE_PANEL_EASY);
                imagePanelEasy.placeObjectsWithoutOverlap();
                imagePanelEasy.repaint();
                imagePanelEasy.moveCount = 0;
                imagePanelEasy.elapsedTime = 0;
                dispose();
            }
        });

        JButton nextButton = new JButton("�����ܰ�");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.swapPanel(CarMovementFrame.IMAGE_PANEL_NORMAL);

                imagePanelNormal.placeObjectsWithoutOverlap();
                imagePanelNormal.repaint();
                imagePanelNormal.moveCount = 0;
                imagePanelNormal.elapsedTime = 0;
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(retryButton);
        buttonPanel.add(nextButton);

        JPanel centerPanel = new JPanel(new GridLayout(4, 1));
        centerPanel.add(messageLabel);
        centerPanel.add(additionalLabel);
        centerPanel.add(moveCountLabel);
        
        
        int moveCount = imagePanelEasy.moveCount;
        int elapsedTime = imagePanelEasy.elapsedTime;
        
        JTextField nicknameField = new JTextField();
        nicknameField.setPreferredSize(new Dimension(200, 25));
        centerPanel.add(nicknameField);

        JPanel nicknamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        nicknamePanel.add(new JLabel("�г���: "));
        nicknamePanel.add(nicknameField);
        
        JButton confirmButton = new JButton("Ȯ��");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nickname = nicknameField.getText();
                Record record = new Record(nickname, moveCount, elapsedTime);
                boolean isRecordInserted = false;

                for (int i = 0; i < ranking.size(); i++) {
                    Record r = ranking.get(i);
                    if (record.compareTo(r) < 0) {  // ���� ����� 10��� ���� ���
                        ranking.add(i, record);     // ���� ����� �ش� ��ġ�� ����
                        isRecordInserted = true;
                        break;
                    }
                }

                if (!isRecordInserted && ranking.size() < 10) {
                    ranking.add(record);  // ���� ����� 10��� ���� ��찡 ����, ��ŷ�� ���� ������ �ִ� ���
                }

                if (ranking.size() > 10) {
                    ranking.remove(10);
                }

                // ��ŷ �˾� ����
                StringBuilder rankingMessage = new StringBuilder();
                String title = "---- ���� ��ŷ ----";

                int padding = (50 - title.length()) / 2;
                String centeredTitle = String.format("%" + padding + "s%s%" + padding + "s", "", title, "");
                rankingMessage.append(centeredTitle).append("\n");
                
                for (int i = 0; i < ranking.size(); i++) {
                    Record r = ranking.get(i);
                    rankingMessage.append((i + 1) + ". " + r.getNickname() + " - �̵� Ƚ��: " + r.getMoveCount() + ", �ɸ� �ð�: " + r.getElapsedTime() + "��\n");
                }
                JOptionPane.showMessageDialog(parent, rankingMessage.toString(), "��ŷ", JOptionPane.INFORMATION_MESSAGE);
                saveRanking();
                confirmButton.setEnabled(false);
            }
        });
        nicknamePanel.add(confirmButton);
        centerPanel.add(nicknamePanel);

        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        ImageIcon star1Icon = new ImageIcon("Image/Star1.png");
        ImageIcon star2Icon = new ImageIcon("Image/Star2.png");
        ImageIcon star3Icon = new ImageIcon("Image/Star3.png");
        Image star1 = star1Icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        Image star2 = star2Icon.getImage().getScaledInstance(200, 120, Image.SCALE_SMOOTH);
        Image star3 = star3Icon.getImage().getScaledInstance(300, 120, Image.SCALE_SMOOTH);

        if (moveCount <= 5 && elapsedTime <= 10) {
            additionalLabel.setIcon(new ImageIcon(star3));
        } else if (moveCount <= 5 || elapsedTime <= 10) {
            additionalLabel.setIcon(new ImageIcon(star2));
        } else {
            additionalLabel.setIcon(new ImageIcon(star1));
        }

        setSize(500, 400);
        setLocationRelativeTo(parentFrame);
        setResizable(false);
    }
    
   

    public void showDialog(BackgroundMusic backgroundMusic) {
        playClearSoundAndPauseBackgroundMusic(backgroundMusic);
        
        Timer timer = new Timer(7000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // ���� �ð��� ���� �Ŀ� ������� ���
                backgroundMusic.play();
            }
        });
        timer.setRepeats(false);
        timer.start();
        
        setVisible(true);
    }

    public void playClearSoundAndPauseBackgroundMusic(BackgroundMusic backgroundMusic) {
        // ������� �Ͻ�����
        backgroundMusic.stop();

        // Ŭ���� ���� ���
        try {
            File clearSoundFile = new File("Music/Clear.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(clearSoundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

            // Ŭ���� ���� ��� �ð�(7��) ���Ŀ� ��������� �ٽ� ���
            Timer timer = new Timer(7000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // ������� ���
                	clip.stop();
                    backgroundMusic.play();
                }
            });
            timer.setRepeats(false);
            timer.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }
    
    public void saveRanking() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("rankingEasy.txt"))) {
            for (Record record : ranking) {
                writer.println(record.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadRanking() {
        ranking.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader("rankingEasy.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String nickname = parts[0];
                    int moveCount = Integer.parseInt(parts[1]);
                    int elapsedTime = Integer.parseInt(parts[2]);
                    Record record = new Record(nickname, moveCount, elapsedTime);
                    ranking.add(record);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ����� �̵� Ƚ���� �ɸ� �ð��� ���� ����
        Collections.sort(ranking, Comparator.comparingInt(Record::getMoveCount).thenComparingInt(Record::getElapsedTime));

        // �ִ� 10������� ����
        if (ranking.size() > 10) {
            ranking = ranking.subList(0, 10);
        }
    }
}
