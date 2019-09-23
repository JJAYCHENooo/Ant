public class PlayRoom implements CreepingGameUpdateViewService, UserPanelSendParamService {
    private static CreepingGame creepingGame;

    private PlayRoomUpdateViewService playRoomUpdateViewService;

    private int lengthOfPole = 300;
    private int velocityOfAnts = 5;
    private int incTime = 1;
    private int[] antsPositions = {30, 80, 110, 160, 250};

    private int maxTime = 0;
    private int minTime = 0;
    private boolean isPlayed = false;

//    private int[] antsDirections = {-1, -1, -1, -1, -1};

//    public void setAntsDirections(int[] antsDirections) {
//        this.antsDirections = antsDirections;
//    }

    public void setPlayRoomUpdateViewService(PlayRoomUpdateViewService playRoomUpdateViewService) {
        this.playRoomUpdateViewService = playRoomUpdateViewService;
    }

    public static void main(String[] args) {
        UserPanel userPanel = new UserPanel();
        PlayRoom playRoom = new PlayRoom();

        playRoom.setPlayRoomUpdateViewService(userPanel);
        userPanel.setUserPanelSendParamService(playRoom);
    }

    @Override
    public void updateView(int[] antsPositions, int[] antsDirections, int timeCount) {
        playRoomUpdateViewService.updateView(antsPositions, antsDirections, timeCount);
    }

    @Override
    public void updateRecordTime(int time) {
        if (!isPlayed) {
            maxTime = time;
            minTime = time;
            isPlayed = true;
        } else {
            if (time > maxTime) maxTime = time;
            if (time < minTime) minTime = time;
        }
        playRoomUpdateViewService.updateRecord(maxTime, minTime);
    }


    @Override
    public void createGame(int[] antsDirections) {
        this.creepingGame = new CreepingGame(incTime, lengthOfPole, velocityOfAnts, antsPositions, antsDirections);
        this.creepingGame.playGame();
    }

    @Override
    public void resetGame() {
        this.creepingGame = null;
    }
}
