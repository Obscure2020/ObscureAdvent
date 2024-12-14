public class Robot {
    private int x;
    private int y;
    private final int vel_x;
    private final int vel_y;
    private final int bounds_width;
    private final int bounds_height;
    private final int half_width;
    private final int half_height;

    public Robot(int start_x, int start_y, int vel_x, int vel_y, int bounds_width, int bounds_height){
        x = start_x;
        y = start_y;
        this.vel_x = vel_x;
        this.vel_y = vel_y;
        this.bounds_width = bounds_width;
        this.bounds_height = bounds_height;
        half_width = bounds_width / 2;
        half_height = bounds_height / 2;
    }

    public void step(){
        x += vel_x;
        if(x >= bounds_width) x -= bounds_width;
        if(x < 0) x += bounds_width;
        y += vel_y;
        if(y >= bounds_height) y -= bounds_height;
        if(y < 0) y += bounds_height;
    }

    public boolean quad1(){
        return (x < half_width) && (y < half_height);
    }

    public boolean quad2(){
        return (x > half_width) && (y < half_height);
    }

    public boolean quad3(){
        return (x < half_width) && (y > half_height);
    }

    public boolean quad4(){
        return (x > half_width) && (y > half_height);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}