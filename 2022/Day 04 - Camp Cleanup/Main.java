import java.util.*;
import java.io.*;
import java.math.BigInteger;
class Main{
  private static BigInteger makeRange(int start, int end){
    return BigInteger.ZERO.setBit(end).subtract(BigInteger.ONE).xor(BigInteger.ZERO.setBit(start-1).subtract(BigInteger.ONE));
  }
  public static void main(String[] args) throws Exception{
    int fullOverlaps = 0;
    int anyOverlaps = 0;
    Scanner scan = new Scanner(new File("input.txt"));
    while(scan.hasNext()){
      String k = scan.nextLine();
      int firstHyphen = k.indexOf('-');
      int secondHyphen = k.lastIndexOf('-');
      int comma = k.indexOf(',');
      int start1 = Integer.valueOf(k.substring(0,firstHyphen));
      int end1 = Integer.valueOf(k.substring(firstHyphen+1,comma));
      int start2 = Integer.valueOf(k.substring(comma+1,secondHyphen));
      int end2 = Integer.valueOf(k.substring(secondHyphen+1));
      BigInteger first = makeRange(start1, end1);
      BigInteger second = makeRange(start2, end2);
      if(first.and(second).compareTo(first) == 0) fullOverlaps++;
      else if(first.and(second).compareTo(second) == 0) fullOverlaps++;
      if(first.not().or(second).not().bitCount() < first.bitCount()) anyOverlaps++;
    }
    scan.close();
    System.out.println("Part #1 - " + fullOverlaps);
    System.out.println("Part #2 - " + anyOverlaps);
  }
}