import java.util.*;
import java.io.*;
class Main{
  private static long[] stripLine(Scanner scan){
    return Arrays.stream(scan.nextLine().split("[:]")[1].trim().split(" +")).mapToLong(Long::parseLong).toArray();
  }
  public static void main(String[] args) throws Exception{
    final long[] times, distances;
    {
      Scanner scan = new Scanner(new File("input.txt"));
      long[] preTimes = stripLine(scan);
      long[] preDistances = stripLine(scan);
      scan.close();
      if(preTimes.length != preDistances.length){
        throw new Exception("Input lists not read correctly. Unequal lengths?");
      }
      int len = preTimes.length + 1;
      long[] postTimes = Arrays.copyOf(preTimes, len);
      long[] postDistances = Arrays.copyOf(preDistances, len);
      postTimes[len - 1] = Long.parseLong(Arrays.stream(preTimes).mapToObj(Long::toString).reduce(String::concat).get());
      postDistances[len - 1] = Long.parseLong(Arrays.stream(preDistances).mapToObj(Long::toString).reduce(String::concat).get());
      times = postTimes;
      distances = postDistances;
    } // Get rid of temp objects and variables
    int[] results = new int[times.length];
    for(int i=0; i<times.length; i++){
      final long limit = times[i];
      final long record = distances[i];
      int winners = 0;
      for(long hold=1; hold<limit; hold++){
        final long travel = (limit-hold)*hold;
        if(travel > record) winners++;
      }
      results[i] = winners;
    }
    System.out.println("Part #1 - " + Arrays.stream(results).limit(results.length - 1).reduce(1, (a,b)->a*b));
    System.out.println("Part #2 - " + results[results.length - 1]);
  }
}