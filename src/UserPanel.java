import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserPanel implements PlayRoomUpdateViewService {
    private UserPanelSendParamService userPanelSendParamService;

    public void setUserPanelSendParamService(UserPanelSendParamService userPanelSendParamService) {
        this.userPanelSendParamService = userPanelSendParamService;
    }

    /** UI 窗口大小参数 */
    private int width = 900;
    private int height = 500;
    private int scale = width / 300;

    /** 游戏记录标签*/
    private JLabel labelMaxTime = new JLabel("maxTime:");
    private JLabel labelMinTime = new JLabel("minTime:");

    /** 游戏当前时长标签 */
    private JLabel labelTimeCount = new JLabel("0");

    /** 游戏控制标签 */
    private JButton startButton = new JButton("Start");
    private JButton resetButton = new JButton("Reset");
    private JButton autoPlayButton = new JButton("AutoPlay");


    /** 蚂蚁和 ID 标签 */
    private JLabel[] ants = new JLabel[5];
    private JLabel[] antsIDLabels = new JLabel[5];

    /** 蚂蚁和 ID 标签在 Y-Axis 的位置*/
    private int antPositionYAxis = 200;
    private int antIDLabelYAxis = 150;

    /** 蚂蚁方向控制按钮 */
    private JButton[] antsDirectionChangeButtons = new JButton[5];

    /** 蚂蚁方向控制按钮在 Y-Axis 的位置*/
    private int antsDirectionChangeButtonYAxis = 280;

    /** 蚂蚁方向数组 */
    private int[] antsDirections = new int[]{-1, -1, -1, -1, -1};

    /** 蚂蚁位置数组 */
    private int[] antsPositions = new int[]{30, 80, 110, 160, 250};

    //UI布局
    private JPanel mainPanel = new JPanel();
    private JPanel panelMenu = new JPanel();
    private JPanel panelRecord = new JPanel();
    private JPanel panelCommand = new JPanel();
    private JFrame frame;

    public UserPanel() {
        frame = new JFrame("Ant Game");

        frame.setBounds(400, 400, width, height);
        frame.setIconImage(new ImageIcon("./resources/ant.png").getImage());

        //布局
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(panelMenu, BorderLayout.NORTH);
        panelMenu.add(panelRecord, BorderLayout.WEST);
        panelMenu.add(panelCommand, BorderLayout.EAST);
        panelCommand.add(resetButton);
        panelCommand.add(startButton);
        panelCommand.add(autoPlayButton);
        panelRecord.add(labelMaxTime, BorderLayout.NORTH);
        panelRecord.add(labelMinTime, BorderLayout.SOUTH);
        panelMenu.add(labelTimeCount, BorderLayout.CENTER);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /** 显示蚂蚁 */
        for (int i = 0; i < 5; i++) {
            /** 蚂蚁图片 */
            ants[i] = new JLabel(new ImageIcon("./resources/antSLeft.png"));
            /** 蚂蚁 ID 标签 */
            antsIDLabels[i] = new JLabel(String.valueOf(i + 1));
            /** 蚂蚁改变方向按钮*/
            antsDirectionChangeButtons[i] = new JButton("Turn");
        }

        mainPanel.setLayout(null);
        setAntsAtStart();

        /** 为开始按钮添加响应事件 */
        startButton.addActionListener(e -> userPanelSendParamService.createGame(antsDirections));

        /** 为蚂蚁的转向按钮添加响应事件 */
        for(int i = 0; i < 5; i++) {
            final int finalI = i;
            antsDirectionChangeButtons[i].addActionListener(e -> setAntDirection(finalI, -antsDirections[finalI]));
        }

        /** 为 reset 按钮添加响应事件 */
        resetButton.addActionListener(e -> {
            userPanelSendParamService.resetGame();
            resetView();
        });
    }

    private void setAntDirection(int antIndex, int antDirection) {
        if(antDirection == antsDirections[antIndex]) return;
        if(antDirection == 1) {
            ants[antIndex].setIcon(new ImageIcon("./resources/antSRight.png"));
        }
        else ants[antIndex].setIcon(new ImageIcon("./resources/antSLeft.png"));
        antsDirections[antIndex] = antDirection;
    }

    private void setAntsAtStart() {
        for(int i = 0; i < 5; i++) {
            setAntDirection(i, -1);

        }

        for(int i = 0; i < 5; i++) {
            mainPanel.add(ants[i]);
            ants[i].setBounds(antsPositions[i] * scale, antPositionYAxis, 50, 30);
            mainPanel.add(antsIDLabels[i]);
            antsIDLabels[i].setBounds(antsPositions[i] * scale, antIDLabelYAxis, 30, 30);
            mainPanel.add(antsDirectionChangeButtons[i]);
            antsDirectionChangeButtons[i].setBounds(antsPositions[i] * scale, antsDirectionChangeButtonYAxis, 60, 30);

        }
    }



//    private int width = 900;
//    private int height = 300;
//
//    private int scale = width / 300; // 木杆长度为300厘米，计算一个缩放的比例
//
//    private JFrame frame;
//    private JPanel panel;
//
//    private AntIDLabel[] antIDLabels;
//    private Button startButton;
//    private Button resetButton;
//
//    public Button getStartButton() {
//        return startButton;
//    }

    @Override
    public void updateView(int[] antsPositions, int[] antsDirections, int timeCount) {
        labelTimeCount.setText(String.valueOf(timeCount));
        for(int i = 0; i < 5; i++) {
            ants[i].setLocation(antsPositions[i] * scale, antPositionYAxis);
            antsIDLabels[i].setLocation(antsPositions[i] * scale, antIDLabelYAxis);
            setAntDirection(i, antsDirections[i]);
        }
    }

    @Override
    public void updateRecord(int maxTime, int minTime) {
        labelMaxTime.setText("maxTime: " + maxTime);
        labelMinTime.setText("minTime: " + minTime);

    }

    @Override
    public void resetView() {
        setAntsAtStart();
    }

//    private class AntIDLabel {
//        public Label antLabel;
//        public Label idLabel;
//        public Button reverseButton;
//    }


//    public void init(Ant[] ants) {
//        frame = new JFrame("Game Panel");
//        frame.setSize(width, height);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        panel = new JPanel();
//        panel.setLayout(null);
//        frame.add(panel);
//
//        startButton = new Button("Start!");
//        startButton.setBounds(800, 20, 70, 30);
//        startButton.addActionListener(e -> HideReverseButtons());
//        panel.add(startButton);
//
//        antIDLabels = new AntIDLabel[ants.length];
//        for (int i = 0; i < ants.length; i++) {
//            if (antIDLabels[i] == null) {
//                antIDLabels[i] = new AntIDLabel();
//                antIDLabels[i].antLabel = new Label("Ant", Label.CENTER);
//                antIDLabels[i].idLabel = new Label(String.valueOf(ants[i].getId()), Label.CENTER);
//                antIDLabels[i].reverseButton = new Button("Reverse");
//                int finalI = i;
//                antIDLabels[i].reverseButton.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        ants[finalI].changeDirection();
//                    }
//                });
//            }
//        }
//
//        UpdateLabels(ants);
//        ShowReverseButtons(ants);
//    }
//
//    public void UpdateLabels(Ant[] ants) {
//        for (int i = 0; i < ants.length; i++) {
//            antIDLabels[i].antLabel.setBounds(scale * ants[i].getPosition(), height / 2, 25, 30);
//            panel.add(antIDLabels[i].antLabel);
//
//            antIDLabels[i].idLabel.setBounds(scale * ants[i].getPosition(), height / 2 - 25, 15, 30);
//            panel.add(antIDLabels[i].idLabel);
//        }
//
//        frame.setVisible(true);
//    }
//
//    public void ShowReverseButtons(Ant[] ants) {
//        for (int i = 0; i < ants.length; i++) {
//            antIDLabels[i].reverseButton.setBounds(scale * ants[i].getPosition(), height / 2 + 30, 80, 30);
//            panel.add(antIDLabels[i].reverseButton);
//        }
//
//        frame.setVisible(true);
//    }
//
//    public void HideReverseButtons(){
//        for (int i = 0; i < antIDLabels.length; i++) {
//            panel.remove(antIDLabels[i].reverseButton);
//        }
//    }
}
