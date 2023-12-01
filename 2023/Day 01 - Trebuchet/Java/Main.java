import java.util.*;
import java.io.*;
class Main{
  private final static String[] keysOne = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
  private final static String[] keysTwo = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"}; //The description of the problem does not list "zero" as a textual option.
  private final static int[] values = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9};
  private static int calcValue(String input, String[] keys){
    int firstIndex = Integer.MAX_VALUE;
    int firstDigit = 0;
    int lastIndex = Integer.MIN_VALUE;
    int lastDigit = 0;
    for(int i=0; i<keys.length; i++){
      final int leftIndex = input.indexOf(keys[i]);
      if(leftIndex < 0) continue;
      final int rightIndex = input.lastIndexOf(keys[i]);
      if(leftIndex < firstIndex){
        firstIndex = leftIndex;
        firstDigit = values[i];
      }
      if(rightIndex > lastIndex){
        lastIndex = rightIndex;
        lastDigit = values[i];
      }
    }
    return firstDigit * 10 + lastDigit;
  }
  public static void main(String[] args) throws Exception{
    Scanner scan = new Scanner(new File("input.txt"));
    int partOne = 0;
    int partTwo = 0;
    while(scan.hasNext()){
      String s = scan.nextLine();
      partOne += calcValue(s, keysOne);
      partTwo += calcValue(s, keysTwo);
    }
    scan.close();
    System.out.println("Part #1 - " + partOne);
    System.out.println("Part #2 - " + partTwo);
  }
}