import java.util.*;
import java.io.*;
class Main{
  public static void main(String[] args) throws Exception{
    HashMap<String, Integer> firstScore = new HashMap<>();
    firstScore.put("A Y", 6+2);
    firstScore.put("A Z", 0+3);
    firstScore.put("A X", 3+1);
    firstScore.put("B X", 0+1);
    firstScore.put("B Y", 3+2);
    firstScore.put("B Z", 6+3);
    firstScore.put("C X", 6+1);
    firstScore.put("C Y", 0+2);
    firstScore.put("C Z", 3+3);
    HashMap<String, Integer> secondScore = new HashMap<>();
    secondScore.put("A X", 3+0);
    secondScore.put("A Y", 1+3);
    secondScore.put("A Z", 2+6);
    secondScore.put("B X", 1+0);
    secondScore.put("B Y", 2+3);
    secondScore.put("B Z", 3+6);
    secondScore.put("C X", 2+0);
    secondScore.put("C Y", 3+3);
    secondScore.put("C Z", 1+6);
    int score1=0, score2=0;
    Scanner scan = new Scanner(new File("input.txt"));
    while(scan.hasNext()){
      String k = scan.nextLine();
      score1 += firstScore.get(k);
      score2 += secondScore.get(k);
    }
    scan.close();
    System.out.println("Part #1 - " + score1);
    System.out.println("Part #2 - " + score2);
  }
}