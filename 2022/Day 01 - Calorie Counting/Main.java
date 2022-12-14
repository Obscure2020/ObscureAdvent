import java.util.*;
import java.io.*;
class Main{
  public static void main(String[] args) throws Exception{
    Scanner scan = new Scanner(new File("input.txt"));
    ArrayList<Integer> running = new ArrayList<>();
    ArrayList<Integer> elves = new ArrayList<>();
    while(scan.hasNext()){
      String k = scan.nextLine();
      if(k.isEmpty()){
        elves.add(running.stream().mapToInt(i->i).sum());
        running.clear();
      } else {
        running.add(Integer.valueOf(k));
      }
    }
    scan.close();
    if(!running.isEmpty()){
      elves.add(running.stream().mapToInt(i->i).sum());
      running.clear();
    }
    Collections.sort(elves);
    Collections.reverse(elves);
    int part1 = elves.get(0);
    int part2 = elves.get(0) + elves.get(1) + elves.get(2);
    System.out.println("Part #1 - " + part1);
    System.out.println("Part #2 - " + part2);
  }
}