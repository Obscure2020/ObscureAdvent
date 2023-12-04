import java.util.*;
import java.io.*;
class Main{
  public static void main(String[] args) throws Exception{
    int partOne = 0, lastId = 0;
    ArrayList<Integer> partTwo = new ArrayList<>();
    Scanner scan = new Scanner(new File("input.txt"));
    while(scan.hasNext()){
      String[] pieces = scan.nextLine().split("[:|]");
      final int id = Integer.parseInt(pieces[0].substring(4).trim());
      lastId = id;
      partTwo.add(id);
      final int freq = Collections.frequency(partTwo, id);
      final int[] winners = Arrays.stream(pieces[1].trim().split(" +")).mapToInt(Integer::parseInt).sorted().toArray();
      final int matches = (int) Arrays.stream(pieces[2].trim().split(" +")).mapToInt(Integer::parseInt).filter(i -> Arrays.binarySearch(winners, i) >= 0).count();
      partOne += matches==0 ? 0 : 1 << (matches-1);
      for(int i=id+1; i<=id+matches; i++){
        for(int j=0; j<freq; j++){
          partTwo.add(i);
        }
      }
    }
    scan.close();
    System.out.println("Part #1 - " + partOne);
    final int ll = lastId;
    System.out.println("Part #2 - " + partTwo.stream().filter(i -> i<=ll).count());
  }
}