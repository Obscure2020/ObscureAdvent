import java.util.*;
import java.util.stream.*;
import java.io.*;
class Main{
  public static void main(String[] args) throws Exception{
    Scanner scan = new Scanner(new File("input.txt"));
    ArrayList<Integer> list = new ArrayList<>();
    int x = 1;
    while(scan.hasNext()){
      String s = scan.nextLine();
      list.add(x);
      if(s.indexOf("noop")<0){
        list.add(x);
        x += Integer.valueOf(s.substring(5));
      }
    }
    scan.close();
    System.out.println("Part #1 - " + IntStream.range(0,6).map(i->40*i+20).map(i->list.get(i-1)*i).sum());
    System.out.println("Part #2:");
    for(int i=0; i<240; i++){
      int p = i%40;
      System.out.print(Math.abs(list.get(i)-p)<=1 ? '#' : '.');
      if(p == 39) System.out.println();
    }
  }
}