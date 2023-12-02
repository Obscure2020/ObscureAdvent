import java.util.*;
import java.io.*;
class Main{
  public static void main(String[] args) throws Exception{
    Scanner scan = new Scanner(new File("input.txt"));
    final int maxRed = 12, maxGreen = 13, maxBlue = 14;
    int partOne = 0, partTwo = 0;
    while(scan.hasNext()){
      String[] halves = scan.nextLine().split("[:]");
      final int game = Integer.parseInt(halves[0].substring(5));
      String[] parts = halves[1].trim().split("[;]");
      boolean good = true;
      int maxFoundRed = 0, maxFoundGreen = 0, maxFoundBlue = 0;
      for(String s : parts){
        String pad = " " + s + " ";
        int match = pad.indexOf("red"), begin = match, result = 0;
        if(match >= 0){
          begin -= 2;
          while(!(pad.charAt(begin)==' ')) begin--;
          result = Integer.parseInt(pad.substring(begin, match).trim());
          maxFoundRed = Math.max(maxFoundRed, result);
        }
        good = good && (result <= maxRed);
        match = pad.indexOf("green"); begin = match; result = 0;
        if(match >= 0){
          begin -= 2;
          while(!(pad.charAt(begin)==' ')) begin--;
          result = Integer.parseInt(pad.substring(begin, match).trim());
          maxFoundGreen = Math.max(maxFoundGreen, result);
        }
        good = good && (result <= maxGreen);
        match = pad.indexOf("blue"); begin = match; result = 0;
        if(match >= 0){
          begin -= 2;
          while(!(pad.charAt(begin)==' ')) begin--;
          result = Integer.parseInt(pad.substring(begin, match).trim());
          maxFoundBlue = Math.max(maxFoundBlue, result);
        }
        good = good && (result <= maxBlue);
      }
      if(good) partOne += game;
      partTwo += maxFoundRed * maxFoundGreen * maxFoundBlue;
    }
    scan.close();
    System.out.println("Part #1 - " + partOne);
    System.out.println("Part #2 - " + partTwo);
  }
}