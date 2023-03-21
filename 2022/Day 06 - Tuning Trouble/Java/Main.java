import java.util.*;
import java.io.*;
class Main{
  private static void search(String input, int len, String info){
    int ans = 0;
    for(int i=0; i+len<=input.length(); i++){
      String spot = input.substring(i,i+len);
      if(spot.codePoints().sorted().distinct().count() == len){
        ans = i+len;
        break;
      }
    }
    System.out.println(info + ans);
  }
  public static void main(String[] args) throws Exception{
    Scanner scan = new Scanner(new File("input.txt"));
    String input = scan.nextLine();
    scan.close();
    search(input, 4, "Part #1 - ");
    search(input, 14, "Part #2 - ");
  }
}