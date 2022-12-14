import java.util.*;
import java.io.*;
class Main{
  private static void record(ArrayList<String> store, int x, int y){
    StringBuilder sb = new StringBuilder();
    sb.append(x);
    sb.append(' ');
    sb.append(y);
    store.add(sb.toString());
  }
  private static void simulate(int num, String[] input, String splash){
    ArrayList<String> history = new ArrayList<>();
    int[] ropeX = new int[num];
    int[] ropeY = new int[num];
    for(int i=0; i<num; i++){
      ropeX[i] = 0;
      ropeY[i] = 0;
    }
    record(history, ropeX[num-1], ropeY[num-1]);
    for(String s : input){
      char dir = s.charAt(0);
      int reps = Integer.valueOf(s.substring(2));
      for(int i=0; i<reps; i++){
        switch(dir){
          case 'U':
            ropeY[0]++;
            break;
          case 'D':
            ropeY[0]--;
            break;
          case 'R':
            ropeX[0]++;
            break;
          case 'L':
            ropeX[0]--;
            break;
        }
        for(int b=1; b<num; b++){
          switch(ropeX[b-1]-ropeX[b]){
            case -2:
              ropeX[b]--;
              if(ropeY[b-1] > ropeY[b]) ropeY[b]++;
              if(ropeY[b-1] < ropeY[b]) ropeY[b]--;
              break;
            case 2:
              ropeX[b]++;
              if(ropeY[b-1] > ropeY[b]) ropeY[b]++;
              if(ropeY[b-1] < ropeY[b]) ropeY[b]--;
              break;
          }
          switch(ropeY[b-1]-ropeY[b]){
            case -2:
              ropeY[b]--;
              if(ropeX[b-1] > ropeX[b]) ropeX[b]++;
              if(ropeX[b-1] < ropeX[b]) ropeX[b]--;
              break;
            case 2:
              ropeY[b]++;
              if(ropeX[b-1] > ropeX[b]) ropeX[b]++;
              if(ropeX[b-1] < ropeX[b]) ropeX[b]--;
              break;
          }
        }
        record(history, ropeX[num-1], ropeY[num-1]);
      }
    }
    System.out.println(splash + history.stream().distinct().count());
  }
  private static String[] read(Scanner scan){
    ArrayList<String> al = new ArrayList<>();
    while(scan.hasNext()) al.add(scan.nextLine());
    return al.toArray(new String[0]);
  }
  public static void main(String[] args) throws Exception{
    Scanner scan = new Scanner(new File("input.txt"));
    String[] input = read(scan);
    scan.close();
    simulate(2, input, "Part #1 - ");
    simulate(10, input, "Part #2 - ");
  }
}