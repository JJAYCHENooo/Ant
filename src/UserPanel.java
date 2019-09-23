import javax.swing.*;
import java.awt.*;

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

        /** 初始化蚂蚁相关标签 */
        for (int i = 0; i < 5; i++) {
            /** 蚂蚁图片 */
            ants[i] = new JLabel(new ImageIcon("./resources/antSLeft.png"));
            /** 蚂蚁 ID 标签 */
            antsIDLabels[i] = new JLabel(String.valueOf(i + 1));
            /** 蚂蚁改变方向按钮*/
            antsDirectionChangeButtons[i] = new JButton("Turn");
        }

        mainPanel.setLayout(null);

        /**设置蚂蚁初始参数
         * 将蚂蚁相关标签添加到 Panel 并显示
         */
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
        /** 设置默认朝向 */
        for(int i = 0; i < 5; i++) {
            setAntDirection(i, -1);
        }

        /** 将蚂蚁相关标签添加至 Panel 中 */
        for(int i = 0; i < 5; i++) {
            mainPanel.add(ants[i]);
            ants[i].setBounds(antsPositions[i] * scale, antPositionYAxis, 50, 30);
            mainPanel.add(antsIDLabels[i]);
            antsIDLabels[i].setBounds(antsPositions[i] * scale, antIDLabelYAxis, 30, 30);
            mainPanel.add(antsDirectionChangeButtons[i]);
            antsDirectionChangeButtons[i].setBounds(antsPositions[i] * scale, antsDirectionChangeButtonYAxis, 60, 30);
        }
    }

    /** 当用户单击 reset 按钮时被调用 */
    public void resetView() {
        setAntsAtStart();
        
        /** 更新当前时间标签 */
        labelTimeCount.setText("0");
    }


    /** ==================== PlayRoomUpdateViewService ====================== */

    /**
     * 接受 PlayRoom 的调用来更新蚂蚁相关标签。游戏每执行一步该函数就被调用一次。
     * @param antsPositions 蚂蚁位置数组
     * @param antsDirections 蚂蚁方向数组
     * @param timeCount 当前时间
     */
    @Override
    public void updateView(int[] antsPositions, int[] antsDirections, int timeCount) {
        /** 更新当前时间标签 */
        labelTimeCount.setText(String.valueOf(timeCount));

        /** 更新所有蚂蚁位置标签 */
        for(int i = 0; i < 5; i++) {
            ants[i].setLocation(antsPositions[i] * scale, antPositionYAxis);
            antsIDLabels[i].setLocation(antsPositions[i] * scale, antIDLabelYAxis);
            setAntDirection(i, antsDirections[i]);
        }
    }

    /**
     * 更新游戏的历史记录。游戏结束时会调用该函数。
     * @param maxTime 最新的 maxTime
     * @param minTime 最新的 minTime
     */
    @Override
    public void updateRecord(int maxTime, int minTime) {
        labelMaxTime.setText("maxTime: " + maxTime);
        labelMinTime.setText("minTime: " + minTime);

    }

    /** ===================================================================== */

}
