public class IntPoint {
    public final int x;
    public final int y;

    public IntPoint(int new_x, int new_y){
        x = new_x;
        y = new_y;
    }

    @Override
    public String toString(){
        return "(" + x + " " + y + ")";
    }

    public IntPoint minus(IntPoint other){
        return new IntPoint(x-other.x, y-other.y);
    }

    public IntPoint plus(IntPoint other){
        return new IntPoint(x+other.x, y+other.y);
    }

    public IntPoint times(int scale){
        return new IntPoint(x*scale, y*scale);
    }

    public boolean fitsBounds(int minX, int maxX, int minY, int maxY){
        if(x < minX) return false;
        if(x > maxX) return false;
        if(y < minY) return false;
        if(y > maxY) return false;
        return true;
    }
}