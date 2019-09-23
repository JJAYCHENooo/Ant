public interface CreepingGameUpdateViewService {
    void updateView(int[] antsPositions, int[] antsDirections, int timeCount);
    void updateRecordTime(int time);
    void gameEndedMessage();
}
