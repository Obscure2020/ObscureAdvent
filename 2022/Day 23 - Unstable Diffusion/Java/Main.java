import java.util.*;
import java.util.stream.*;
import java.io.*;
class Main{
  private static boolean needsMoving(IntPoint e, ArrayList<IntPoint> elves){
    for(int x=-1; x<=1; x++){
      for(int y=-1; y<=1; y++){
        if(x==0 && y==0) continue;
        IntPoint n = new IntPoint(e.x+x, e.y+y);
        if(elves.contains(n)) return true;
      }
    }
    return false;
  }
  private static boolean needsMoving(ArrayList<IntPoint> elves){
    return elves.stream().parallel().anyMatch(e->needsMoving(e,elves));
  }
  private static IntPoint propose(IntPoint e, ArrayList<IntPoint> elves, int offset){
    IntPoint[] checks = new IntPoint[0];
    for(int zz=offset; zz<4+offset; zz++){
      int m = zz % 4;
      if(m == 0){ //North
        checks = IntStream.rangeClosed(-1,1).mapToObj(i->new IntPoint(e.x+i, e.y-1)).toList().toArray(checks);
        if(Arrays.stream(checks).noneMatch(elves::contains)) return new IntPoint(e.x, e.y-1);
      }
      if(m == 1){ //South
        checks = IntStream.rangeClosed(-1,1).mapToObj(i->new IntPoint(e.x+i, e.y+1)).toList().toArray(checks);
        if(Arrays.stream(checks).noneMatch(elves::contains)) return new IntPoint(e.x, e.y+1);
      }
      if(m == 2){ //West
        checks = IntStream.rangeClosed(-1,1).mapToObj(i->new IntPoint(e.x-1, e.y+i)).toList().toArray(checks);
        if(Arrays.stream(checks).noneMatch(elves::contains)) return new IntPoint(e.x-1, e.y);
      }
      if(m == 3){ //East
        checks = IntStream.rangeClosed(-1,1).mapToObj(i->new IntPoint(e.x+1, e.y+i)).toList().toArray(checks);
        if(Arrays.stream(checks).noneMatch(elves::contains)) return new IntPoint(e.x+1, e.y);
      }
    }
    return new IntPoint(e);
  }
  public static void main(String[] args) throws Exception{
    ArrayList<IntPoint> elves = new ArrayList<>();
    Scanner scan = new Scanner(new File("input.txt"));
    for(int i=0; scan.hasNext(); i++){
      final int ii = i;
      String k = scan.nextLine();
      IntStream.range(0,k.length()).filter(n->k.charAt(n)=='#').mapToObj(n->new IntPoint(n,ii)).forEachOrdered(elves::add);
    }
    scan.close();
    int offset=0, zz=1;
    for(; needsMoving(elves); zz++){
      int[] needs = IntStream.range(0,elves.size()).parallel().filter(i->needsMoving(elves.get(i),elves)).toArray();
      ArrayList<IntPoint> wants = new ArrayList<>(needs.length);
      final int offCopy = offset;
      Arrays.stream(needs).mapToObj(i->propose(elves.get(i),elves,offCopy)).forEachOrdered(wants::add);
      int[] freqs = wants.stream().mapToInt(e->Collections.frequency(wants,e)).toArray();
      for(int i=0; i<needs.length; i++) if(freqs[i] == 1) elves.set(needs[i], wants.get(i));
      offset = (offset + 1) % 4;
      if(zz == 10){
        int minX = elves.stream().mapToInt(e->e.x).min().getAsInt();
        int minY = elves.stream().mapToInt(e->e.y).min().getAsInt();
        int maxX = elves.stream().mapToInt(e->e.x).max().getAsInt();
        int maxY = elves.stream().mapToInt(e->e.y).max().getAsInt();
        int freeCount = 0;
        for(int y=minY; y<=maxY; y++){
          for(int x=minX; x<=maxX; x++){
            IntPoint n = new IntPoint(x,y);
            if(!elves.contains(n)) freeCount++;
          }
        }
        System.out.println("Part #1 - " + freeCount);
      }
      if(zz % 20 == 0) System.out.print(" " + zz + "...\r");
    }
    System.out.println("Part #2 - " + zz);
  }
}