import java.util.*;
import java.io.*;
class Main{
  private static String[] partTwoKeys = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
  private static int[] partTwoVals = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
  private static boolean isDigit(char input){
    return input >= '0' && input <= '9';
  }
  private static int partOneValue(String input){
    final int len = input.length();
    int firstDigit = 0;
    int lastDigit = 0;
    for(int i=0; i<len; i++){
      char c = input.charAt(i);
      if(isDigit(c)){
        firstDigit = Character.getNumericValue(c);
        break;
      }
    }
    for(int i=len-1; i>=0; i--){
      char c = input.charAt(i);
      if(isDigit(c)){
        lastDigit = Character.getNumericValue(c);
        break;
      }
    }
    return firstDigit * 10 + lastDigit;
  }
  private static int partTwoValue(String input){
    int firstIndex = Integer.MAX_VALUE;
    int firstDigit = 0;
    int lastIndex = Integer.MIN_VALUE;
    int lastDigit = 0;
    for(int i=0; i<partTwoKeys.length; i++){
      final int leftIndex = input.indexOf(partTwoKeys[i]);
      if(leftIndex < 0) continue;
      final int rightIndex = input.lastIndexOf(partTwoKeys[i]);
      if(leftIndex < firstIndex){
        firstIndex = leftIndex;
        firstDigit = partTwoVals[i];
      }
      if(rightIndex > lastIndex){
        lastIndex = rightIndex;
        lastDigit = partTwoVals[i];
      }
    }
    return firstDigit * 10 + lastDigit;
  }
  public static void main(String[] args) throws Exception{
    Scanner scan = new Scanner(new File("input.txt"));
    ArrayList<String> input = new ArrayList<>();
    while(scan.hasNext()){
      input.add(scan.nextLine());
    }
    scan.close();
    System.out.println("Part #1 - " + input.stream().mapToInt(Main::partOneValue).sum());
    System.out.println("Part #2 - " + input.stream().mapToInt(Main::partTwoValue).sum());
  }
}