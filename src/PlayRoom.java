public class PlayRoom {
    private static CreepingGame creepingGame;

    public static void main(String[] args) {
        int lengthOfPole = 300; // 300 cm
        int velocityOfAnts = 5; // 5 cm/s
        int incTime = 1;

        creepingGame = new CreepingGame(incTime, lengthOfPole, velocityOfAnts, 30, 80, 110, 160, 250);

        creepingGame.play();
    }
}
