import java.io.*;
import java.util.*;

class Main {
    private static boolean checkIncreasing(List<Integer> report){
        int prev = Integer.MIN_VALUE;
        for(int cur : report){
            if(cur < prev) return false;
            prev = cur;
        }
        return true;
    }
    private static boolean checkDecreasing(List<Integer> report){
        int prev = Integer.MAX_VALUE;
        for(int cur : report){
            if(cur > prev) return false;
            prev = cur;
        }
        return true;
    }
    private static boolean checkMinGaps(List<Integer> report){
        int prev = report.getFirst() - 1;
        for(int cur : report){
            int diff = Math.abs(prev - cur);
            if(diff < 1 || diff > 3) return false;
            prev = cur;
        }
        return true;
    }
    private static boolean checkSafe(List<Integer> report){
        return (checkIncreasing(report) || checkDecreasing(report)) && checkMinGaps(report);
    }
    private static boolean checkDampenedSafe(List<Integer> report){
        if(checkSafe(report)) return true;
        for(int i=0; i<report.size(); i++){
            ArrayList<Integer> test = new ArrayList<>(report);
            test.remove(i);
            if(checkSafe(test)) return true;
        }
        return false;
    }
    public static void main(String[] args) throws Exception{
        Scanner scan = new Scanner(new File("input.txt"));
        ArrayList<List<Integer>> reports = new ArrayList<>();
        while(scan.hasNext()){
            reports.add(Arrays.stream(scan.nextLine().strip().split("\\s+")).map(Integer::valueOf).toList());
        }
        scan.close();
        reports.trimToSize(); //I dunno why I included this. For fun, I guess.
        System.out.println("Part #1 - " + reports.stream().unordered().parallel().filter(Main::checkSafe).count());
        System.out.println("Part #2 - " + reports.stream().unordered().parallel().filter(Main::checkDampenedSafe).count());
    }
}