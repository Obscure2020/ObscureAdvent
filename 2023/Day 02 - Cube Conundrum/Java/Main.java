import java.util.*;
import java.io.*;
class Main{
  public static void main(String[] args) throws Exception{
    Scanner scan = new Scanner(new File("input.txt"));
    final int maxRed = 12;
    final int maxGreen = 13;
    final int maxBlue = 14;
    int partOne = 0;
    int partTwo = 0;
    while(scan.hasNext()){
      String[] halves = scan.nextLine().split("[:]");
      final int game = Integer.parseInt(halves[0].substring(5));
      String[] parts = halves[1].trim().split("[;]");
      boolean goodGame = true;
      int maxFoundRed = 0;
      int maxFoundGreen = 0;
      int maxFoundBlue = 0;
      for(String s : parts){
        String pad = " " + s + " ";
        int match = pad.indexOf("red");
        int begin = match;
        int result = 0;
        if(match >= 0){
          begin -= 2;
          while(!(pad.charAt(begin)==' ')) begin--;
          result = Integer.parseInt(pad.substring(begin, match).trim());
          maxFoundRed = Math.max(maxFoundRed, result);
        }
        if(result > maxRed) goodGame = false;
        match = pad.indexOf("green");
        begin = match;
        result = 0;
        if(match >= 0){
          begin -= 2;
          while(!(pad.charAt(begin)==' ')) begin--;
          result = Integer.parseInt(pad.substring(begin, match).trim());
          maxFoundGreen = Math.max(maxFoundGreen, result);
        }
        if(result > maxGreen) goodGame = false;
        match = pad.indexOf("blue");
        begin = match;
        result = 0;
        if(match >= 0){
          begin -= 2;
          while(!(pad.charAt(begin)==' ')) begin--;
          result = Integer.parseInt(pad.substring(begin, match).trim());
          maxFoundBlue = Math.max(maxFoundBlue, result);
        }
        if(result > maxBlue) goodGame = false;
      }
      if(goodGame) partOne += game;
      partTwo += maxFoundRed * maxFoundGreen * maxFoundBlue;
    }
    scan.close();
    System.out.println("Part #1 - " + partOne);
    System.out.println("Part #2 - " + partTwo);
  }
}