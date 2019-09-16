import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;

public class CreepingGame {
    private Pole pole;
    private Ant[] ants;

    /* 每次执行下一步所执行的具体时长，例如 step = 1 则执行一次代表经历一秒钟。 */
    private int step;

    /* 目前游戏的时长 */
    private int currentTime = 0;

    private boolean finished = false;

    private UserInterface userInterface;


    public CreepingGame(int incTime, int lengthOfPole, int velocityOfAnts, int... antsPositions) {

        step = incTime;

        /* 创建木杆 */
        pole = new Pole(lengthOfPole);

        /* 创建蚂蚁 */
        int count = antsPositions.length;
        ants = new Ant[count];

        for (int i = 0; i < count; i++) {
            ants[i] = new Ant(antsPositions[i], velocityOfAnts);
        }
    }

    public void start() {
        Timer timer = new Timer(300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!finished) {
                    nextStep();
                }
            }
        });
        timer.start();

//        while (!finished) {
//            nextStep();
//
//            /* 一秒钟刷新一次 */
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException ex) {
//                Thread.currentThread().interrupt();
//            }
//        }
        // 在添加 Listener 后好像因为多线程，会把图形界面给睡死，出现问题。
    }

    /**
     * 从头至尾执行一次游戏
     */
    public void play() {
        userInterface = new UserInterface();
        userInterface.init(ants);

        userInterface.getStartButton().addActionListener(e -> start());
    }

    private void nextStep() {
        /* 更新时间 */
        currentTime += step;

        updateAnts();
        configureFinished();
        updateView();
    }

    /**
     * 更新下一步之后蚂蚁的位置和方向
     */
    private void updateAnts() {
        Ant[] sortedAnts = new Ant[ants.length];

        for (int i = 0; i < ants.length; i++) {
            ants[i].move(step);
            sortedAnts[i] = ants[i];
        }

        Arrays.sort(sortedAnts, Comparator.comparingInt(Ant::getPosition));
        int positions[] = new int[ants.length];
        int directions[] = new int[ants.length];
        for (int i = 0; i < ants.length; i++) {
            positions[i] = sortedAnts[i].getPosition();
            directions[i] = sortedAnts[i].getDirection();
        }

        for (int i = 0; i < ants.length; i++) {
            ants[i].setPosition(positions[i]);
            ants[i].setDirection(directions[i]);
        }
    }

    /**
     * 根据目前的游戏状态更新 UI
     */
    private void updateView() {
        userInterface.updateLabels(ants);
    }

    /**
     * 判断游戏是否结束，若结束，设置 finished 为 true.
     */
    private void configureFinished() {
        for (int i = 0; i < ants.length; i++) {
            int position = ants[i].getPosition();
            if (position > 0 && position < pole.getLength()) {
                finished = false;
                return;
            }
            if (i == ants.length - 1) {
                finished = true;
                return;
            }
        }
    }
}
