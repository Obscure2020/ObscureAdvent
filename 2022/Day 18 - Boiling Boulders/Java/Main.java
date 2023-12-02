import java.util.*;
import java.util.stream.*;
import java.io.*;
class Main{
  private static String fab(int x, int y, int z){
    StringBuilder sb = new StringBuilder();
    sb.append(x);
    sb.append(',');
    sb.append(y);
    sb.append(',');
    sb.append(z);
    return sb.toString();
  }
  public static void main(String[] args) throws Exception{
    Scanner scan = new Scanner(new File("input.txt"));
    ArrayList<String> cubes = new ArrayList<>();
    while(scan.hasNext()) cubes.add(scan.nextLine());
    scan.close();
    Collections.sort(cubes);
    long part1 = 0;
    int minX=Integer.MAX_VALUE, maxX=Integer.MIN_VALUE, minY=Integer.MAX_VALUE, maxY=Integer.MIN_VALUE, minZ=Integer.MAX_VALUE, maxZ=Integer.MIN_VALUE;
    for(String k : cubes){
      int[] coords = Arrays.stream(k.split(",")).mapToInt(Integer::valueOf).toArray();
      minX = Integer.min(coords[0],minX);
      maxX = Integer.max(coords[0],maxX);
      minY = Integer.min(coords[1],minY);
      maxY = Integer.max(coords[1],maxY);
      minZ = Integer.min(coords[2],minZ);
      maxZ = Integer.max(coords[2],maxZ);
      ArrayList<String> siblings = new ArrayList<>(6);
      for(int x=-1; x<=1; x++){
        for(int y=-1; y<=1; y++){
          for(int z=-1; z<=1; z++){
            if(IntStream.of(x,y,z).map(Math::abs).sum()==1) siblings.add(fab(x+coords[0],y+coords[1],z+coords[2]));
          }
        }
      }
      part1 += siblings.stream().filter(s->Collections.binarySearch(cubes,s)<0).count();
    }
    System.out.println("Part #1 - " + part1);
    ArrayList<String> air = new ArrayList<>();
    for(int x=minX; x<=maxX; x++){
      for(int y=minY; y<=maxY; y++){
        for(int z=minZ; z<=maxZ; z++){
          String k = fab(x,y,z);
          if(Collections.binarySearch(cubes,k)<0) air.add(k);
        }
      }
    }
    HashSet<String> outer = new HashSet<>();
    outer.add(fab(minX-1,minY-1,minZ-1));
    ArrayList<String> bubbles = new ArrayList<>();
    while(air.size() > 0){
      String k = air.get(0);
      ArrayList<String> known = new ArrayList<>();
      HashSet<String> found = new HashSet<>();
      known.add(k);
      while(true){
        for(String s : known){
          int[] coords = Arrays.stream(s.split(",")).mapToInt(Integer::valueOf).toArray();
          for(int x=-1; x<=1; x++){
            for(int y=-1; y<=1; y++){
              for(int z=-1; z<=1; z++){
                if(IntStream.of(x,y,z).map(Math::abs).sum()==1){
                  String bud = fab(x+coords[0],y+coords[1],z+coords[2]);
                  if(!known.contains(bud) && Collections.binarySearch(cubes,bud)<0) found.add(bud);
                }
              }
            }
          }
        }
        for(String baby : found) known.add(baby);
        if(found.stream().anyMatch(outer::contains)){
          for(String bad : known){
            air.remove(bad);
            outer.add(bad);
          }
          break;
        }
        if(found.size() == 0){
          for(String baby : known){
            bubbles.add(baby);
            air.remove(baby);
          }
          break;
        }
        found.clear();
      }
    }
    long part2 = 0;
    for(String k : bubbles){
      int[] coords = Arrays.stream(k.split(",")).mapToInt(Integer::valueOf).toArray();
      ArrayList<String> siblings = new ArrayList<>(6);
      for(int x=-1; x<=1; x++){
        for(int y=-1; y<=1; y++){
          for(int z=-1; z<=1; z++){
            if(IntStream.of(x,y,z).map(Math::abs).sum()==1) siblings.add(fab(x+coords[0],y+coords[1],z+coords[2]));
          }
        }
      }
      part2 += siblings.stream().filter(s->Collections.binarySearch(cubes,s)>=0).count();
    }
    part2 = part1 - part2;
    System.out.println("Part #2 - " + part2);
  }
}