import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;

public class CreepingGame {
    private Pole pole;
    private Ant[] ants;

    /* 每次执行下一步所执行的具体时长，例如 incTime = 1 则执行一次代表经历一秒钟。 */
    private int incTime;

    /* 目前游戏的时长 */
    private int timeCount = 0;

    /* 标识游戏是否结束（是否所有蚂蚁都离开木杆） */
    private boolean isEnded = false;

    /* 负责传递游戏数据给 Controller(creepingGameDelegate) */
    private CreepingGameUpdateViewService creepingGameUpdateViewService;

    /**
     * 在初始化 CreepingGame 的时候调用
     * @param creepingGameUpdateViewService
     */
    public void setCreepingGameUpdateViewService(CreepingGameUpdateViewService creepingGameUpdateViewService) {
        this.creepingGameUpdateViewService = creepingGameUpdateViewService;
    }

    /**
     * 给定参数创建游戏
     *
     * @param incTime        每次执行下一步所执行的具体时长，例如 incTime = 1 则执行一次代表经历一秒钟。
     * @param lengthOfPole   木杆长度
     * @param velocityOfAnts 蚂蚁的速度（所有蚂蚁都相同）
     * @param antsPositions  蚂蚁的位置数组
     * @param antsDirections 蚂蚁的方向数组
     */
    public CreepingGame(int incTime, int lengthOfPole, int velocityOfAnts, int[] antsPositions, int[] antsDirections) {

        this.incTime = incTime;

        /* 创建木杆 */
        pole = new Pole(lengthOfPole);

        /* 创建蚂蚁 */
        int count = antsPositions.length;
        ants = new Ant[count];

        for (int i = 0; i < count; i++) {
            ants[i] = new Ant(antsPositions[i], velocityOfAnts, antsDirections[i]);
        }
    }

    /**
     * 开始游戏，由 PlayRoom 调用
     */
    public void playGame() {
        Timer timer = new Timer(300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isEnded) {
                    NextStep();
                }
            }
        });
        timer.start();
    }

//    /**
//     * 从头至尾执行一次游戏
//     */
//    public void Play() {
//        userInterface = new UserInterface();
//        userInterface.init(ants);
//
//        userInterface.getStartButton().addActionListener(e -> Start());
//    }

    private void NextStep() {
        /* 更新时间 */
        timeCount += incTime;

        UpdateAnts();
        UpdateView();
        CheckIsEnded();
    }

    /**
     * 更新蚂蚁的位置和方向
     */
    private void UpdateAnts() {
        Ant[] sortedAnts = new Ant[ants.length];

        for (int i = 0; i < ants.length; i++) {
            ants[i].move(incTime);
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
     * 传递更新后的游戏数据，让 CreepingGameDelegate 更新 UI 信息
     */
    private void UpdateView() {
        creepingGameUpdateViewService.updateView(getAntsPositions(), getAntsDirections(), timeCount);
        if(isEnded){
            creepingGameUpdateViewService.updateRecordTime(timeCount);
            creepingGameUpdateViewService.gameEndedMessage();
        }
    }

    private int[] getAntsPositions() {
        int[] antsPositions = new int[ants.length];
        for (int i = 0; i < ants.length; i++) {
            antsPositions[i] = ants[i].getPosition();
        }
        return antsPositions;
    }

    private int[] getAntsDirections() {
        int[] antsDirections = new int[ants.length];
        for (int i = 0; i < ants.length; i++) {
            antsDirections[i] = ants[i].getDirection();
        }
        return antsDirections;
    }

    /**
     * 判断游戏是否结束，若结束，设置 isEnded 为 true.
     */
    private void CheckIsEnded() {
        for (int i = 0; i < ants.length; i++) {
            int position = ants[i].getPosition();
            if (position > 0 && position < pole.getLength()) {
                isEnded = false;
                return;
            }
            if (i == ants.length - 1) {
                isEnded = true;
                return;
            }
        }
    }
}
