public class PlayRoom implements CreepingGameDelegate {
    private static CreepingGame creepingGame;

    public static void main(String[] args) {
        int lengthOfPole = 300; // 300 cm
        int velocityOfAnts = 5; // 5 cm/s
        int incTime = 1;
        int[] antsPositions = {30, 80, 110, 160, 250};
        int[] antsDirections = {-1, -1, -1, -1, -1};

        creepingGame = new CreepingGame(incTime, lengthOfPole, velocityOfAnts, antsPositions, antsDirections);

        creepingGame.playGame();
    }

    @Override
    public void updateView(int[] antsPositions, int[] antsDirections) {

    }

    @Override
    public void updateRecordTime(int time) {

    }
}
