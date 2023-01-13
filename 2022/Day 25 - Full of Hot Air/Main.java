import java.util.*;
import java.io.*;
class Main{
  private static long snafuToDecimal(String s){
    long pow = 1, total = 0;
    for(int i=0; i<s.length(); i++){
      char c = s.charAt(s.length()-i-1);
      total += ("=-012".indexOf(c)-2) * pow;
      pow *= 5;
    }
    return total;
  }
  private static String decimalToSnafu(long input){
    StringBuilder sb = new StringBuilder();
    while(input > 0){
      int mod = (int) (input % 5);
      sb.append("=-012".charAt((mod+2)%5));
      input += 2; //This tripped me up for a long time.
      input /= 5;
    }
    if(sb.isEmpty()) sb.append('0');
    return sb.reverse().toString();
  }
  public static void main(String[] args) throws Exception{
    ArrayList<String> input = new ArrayList<>();
    Scanner scan = new Scanner(new File("input.txt"));
    while(scan.hasNext()) input.add(scan.nextLine());
    scan.close();
    String part1 = decimalToSnafu(input.stream().parallel().mapToLong(Main::snafuToDecimal).sum());
    System.out.println("Answer: " + part1);
  }
}