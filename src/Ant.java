public class Ant {

    private static int nextID = 1;

    private static int idFactory() {
        return nextID++;
    }

    /**
     * Identify each ant.
     */
    private int id;

    /**
     * direction: -1 represents moving to left, 1 represents moving to right.
     */
    private int direction = -1;
    private int velocity;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getDirection() {
        return this.direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public Ant(int Position, int velocity) {
        this.id = idFactory();
        this.position = Position;
        this.velocity = velocity;
    }

    public void changeDirection() {
        this.direction = -this.direction;
    }

    public void move(int time) {
        this.position += direction * velocity * time;
    }

    public void moveTo(int position) {
        this.position = position;
    }



}
