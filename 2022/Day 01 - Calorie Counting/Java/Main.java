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
    System.out.println("Part #1 - " + elves.get(0));
    System.out.println("Part #2 - " + elves.stream().limit(3).mapToInt(i->i).sum());
  }
}