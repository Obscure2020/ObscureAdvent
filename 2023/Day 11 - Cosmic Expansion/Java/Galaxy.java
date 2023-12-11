public class Galaxy {
    private long x;
    private long y;
    private long x_offset = 0;
    private long y_offset = 0;

    public Galaxy(long newX, long newY){
        x = newX;
        y = newY;
    }

    public Galaxy(Galaxy other){
        x = other.x;
        y = other.y;
    }

    public long getX(){
        return x;
    }

    public long getY(){
        return y;
    }

    public void bumpX(long amount){
        x_offset += amount;
    }

    public void bumpY(long amount){
        y_offset += amount;
    }

    public void applyBumps(){
        x += x_offset;
        x_offset = 0;
        y += y_offset;
        y_offset = 0;
    }

    public long dist(Galaxy other){
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }
}