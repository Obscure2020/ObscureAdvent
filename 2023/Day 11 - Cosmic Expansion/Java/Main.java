import java.util.*;
import java.io.*;
class Main{
  private static ArrayList<Galaxy> bumpGaps(ArrayList<Galaxy> originals, long bump){
    ArrayList<Galaxy> galaxies = new ArrayList<>();
    for(Galaxy g : originals) galaxies.add(new Galaxy(g));
    long maxX = galaxies.stream().mapToLong(Galaxy::getX).max().getAsLong();
    long maxY = galaxies.stream().mapToLong(Galaxy::getY).max().getAsLong();
    for(long x=0; x<maxX; x++){
      boolean hit = false;
      for(Galaxy g : galaxies){
        if(g.getX() == x){
          hit = true;
          break;
        }
      }
      if(!hit){
        for(Galaxy g : galaxies){
          if(g.getX() > x) g.bumpX(bump);
        }
      }
    }
    for(long y=0; y<maxY; y++){
      boolean hit = false;
      for(Galaxy g : galaxies){
        if(g.getY() == y){
          hit = true;
          break;
        }
      }
      if(!hit){
        for(Galaxy g : galaxies){
          if(g.getY() > y) g.bumpY(bump);
        }
      }
    }
    for(Galaxy g : galaxies) g.applyBumps();
    return galaxies;
  }
  public static void main(String[] args) throws Exception{
    ArrayList<Galaxy> galaxies = new ArrayList<>();
    Scanner scan = new Scanner(new File("input.txt"));
    int y = 0;
    while(scan.hasNext()){
      String line = scan.nextLine().trim();
      for(int x=0; x<line.length(); x++){
        if(line.charAt(x) == '#') galaxies.add(new Galaxy(x, y));
      }
      y++;
    }
    scan.close();
    ArrayList<Galaxy> results = bumpGaps(galaxies, 1);
    long result = 0;
    for(int one=0; one<results.size()-1; one++){
      for(int two=one+1; two<results.size(); two++){
        result += results.get(one).dist(results.get(two));
      }
    }
    System.out.println("Part #1 - " + result);
    results = bumpGaps(galaxies, 999999);
    result = 0;
    for(int one=0; one<results.size()-1; one++){
      for(int two=one+1; two<results.size(); two++){
        result += results.get(one).dist(results.get(two));
      }
    }
    System.out.println("Part #2 - " + result);
  }
}