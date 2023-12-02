public class IntPoint {
  public final int x;
  public final int y;

  public IntPoint(){
    x = 0;
    y = 0;
  }

  public IntPoint(int nx, int ny){
    x = nx;
    y = ny;
  }

  public IntPoint(IntPoint other){
    x = other.x;
    y = other.y;
  }

  public String toString(){
    StringBuilder sb = new StringBuilder();
    sb.append('{');
    sb.append(x);
    sb.append(',');
    sb.append(y);
    sb.append('}');
    return sb.toString();
  }

  public boolean equals(Object other){
    if(other instanceof IntPoint){
      IntPoint o = (IntPoint) other;
      return x==o.x && y==o.y;
    }
    return false;
  }
}
