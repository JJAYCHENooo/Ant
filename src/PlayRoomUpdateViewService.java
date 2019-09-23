public interface PlayRoomUpdateViewService {
    void updateView(int[] antsPositions, int[] antsDirections, int timeCount);

    void updateRecord(int maxTime, int minTime);
}
