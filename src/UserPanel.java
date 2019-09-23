import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserPanel implements PlayRoomUpdateViewService {
    private UserPanelSendParamService userPanelSendParamService;

    public void setUserPanelSendParamService(UserPanelSendParamService userPanelSendParamService) {
        this.userPanelSendParamService = userPanelSendParamService;
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

    }

    @Override
    public void updateRecord(int maxTime, int minTime) {

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
