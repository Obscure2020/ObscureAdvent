import java.util.*;
import java.util.stream.*;
import java.io.*;
class Main{
  public static void main(String[] args) throws Exception{
    final int[] nums;
    {
      Scanner scan = new Scanner(new File("input.txt"));
      ArrayList<String> lines = new ArrayList<>();
      while(scan.hasNext()) lines.add(scan.nextLine());
      scan.close();
      nums = lines.stream().mapToInt(Integer::valueOf).toArray();
    }
    LinkedList<Integer> mix = new LinkedList<>();
    IntStream.range(0,nums.length).forEach(mix::add);
    for(int i=0; i<nums.length; i++){
      Integer ii = i;
      int before = mix.indexOf(ii);
      mix.remove(ii);
      int temp = (before+nums[i])%mix.size();
      mix.add(temp<0 ? mix.size()+temp : temp, ii);
    }
    final int start1 = mix.indexOf(IntStream.range(0,nums.length).filter(i->nums[i]==0).findFirst().getAsInt());
    System.out.println("Part #1 - " + IntStream.of(1000,2000,3000).map(i->nums[mix.get((start1+i)%mix.size())]).sum());
    final long[] decrypt = Arrays.stream(nums).mapToLong(i->811589153*(long)i).toArray();
    mix.clear();
    IntStream.range(0,decrypt.length).forEach(mix::add);
    for(int z=0; z<10; z++){
      for(int i=0; i<decrypt.length; i++){
        Integer ii = i;
        int before = mix.indexOf(ii);
        mix.remove(ii);
        long temp = ((long)before+decrypt[i]) % (long)mix.size();
        int temp2 = (int) temp;
        mix.add(temp2<0 ? mix.size()+temp2 : temp2, ii);
      }
    }
    final int start2 = mix.indexOf(IntStream.range(0,decrypt.length).filter(i->decrypt[i]==0).findFirst().getAsInt());
    System.out.println("Part #2 - " + IntStream.of(1000,2000,3000).map(i->mix.get((start2+(int)i)%mix.size())).mapToLong(i->decrypt[i]).sum());
  }
}