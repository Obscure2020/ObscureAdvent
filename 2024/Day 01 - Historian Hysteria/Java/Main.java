import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws Exception{
        Scanner scan = new Scanner(new File("input.txt"));
        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();
        while(scan.hasNext()){
            String[] line = scan.nextLine().strip().split("\\s+");
            left.add(Integer.parseInt(line[0]));
            right.add(Integer.parseInt(line[1]));
        }
        scan.close();
        Collections.sort(left);
        Collections.sort(right);
        long part1 = 0;
        for(int i=0; i<left.size(); i++){
            part1 += Math.abs(left.get(i) - right.get(i));
        }
        long part2 = 0;
        for(int k : left){
            part2 += Collections.frequency(right, k) * k;
        }
        System.out.println("Part #1 - " + part1);
        System.out.println("Part #2 - " + part2);
    }
}