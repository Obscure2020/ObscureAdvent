import java.util.*;
import java.io.*;

class Main {
    private static List<List<Long>> readFile(File in) throws FileNotFoundException {
        ArrayList<List<Long>> result = new ArrayList<>();
        Scanner scan = new Scanner(in);
        while(scan.hasNext()){
            String line = scan.nextLine().strip();
            if(line.isEmpty()) break;
            List<Long> nums = Arrays.stream(line.split("[^\\d]+")).map(Long::valueOf).toList();
            result.add(nums);
        }
        scan.close();
        result.trimToSize();
        return Collections.unmodifiableList(result);
    }
    private static boolean attemptRow_twoOps(long goal, List<Long> row, long ops){
        int op = (int) (ops % 2);
        long restOps = ops / 2;
        ArrayDeque<Long> work_row = new ArrayDeque<>(row);
        while(work_row.size() > 1){
            long first = work_row.removeFirst();
            if(first > goal) return false;
            long second = work_row.removeFirst();
            switch(op){
                case 1 -> {
                    work_row.addFirst(first * second);
                }
                case 0 -> {
                    work_row.addFirst(first + second);
                }
                default -> {
                    throw new AssertionError("This should definitely not be possible.");
                }
            }
            op = (int) (restOps % 2);
            restOps /= 2;
        }
        return work_row.getFirst() == goal;
    }
    private static boolean attemptRow_threeOps(long goal, List<Long> row, long ops){
        int op = (int) (ops % 3);
        long restOps = ops / 3;
        ArrayDeque<Long> work_row = new ArrayDeque<>(row);
        while(work_row.size() > 1){
            long first = work_row.removeFirst();
            if(first > goal) return false;
            long second = work_row.removeFirst();
            switch(op){
                case 2 -> {
                    String newNum = Long.toString(first) + Long.toString(second);
                    work_row.addFirst(Long.parseLong(newNum));
                }
                case 1 -> {
                    work_row.addFirst(first * second);
                }
                case 0 -> {
                    work_row.addFirst(first + second);
                }
                default -> {
                    throw new AssertionError("This should definitely not be possible.");
                }
            }
            op = (int) (restOps % 3);
            restOps /= 3;
        }
        return work_row.getFirst() == goal;
    }
    private static long twoPow(int power){
        return 1l << power;
    }
    private static long threePow(int power){
        long result = 1;
        for(int i=0; i<power; i++){
            result *= 3;
        }
        return result;
    }
    private static long part1(List<List<Long>> input){
        long result = 0;
        for(List<Long> row : input){
            long goal = row.getFirst();
            int len = row.size();
            long maxOps = twoPow(len - 2) - 1;
            for(long ops = 0; ops<=maxOps; ops++){
                if(attemptRow_twoOps(goal, row.subList(1, len), ops)){
                    result += goal;
                    break;
                }
            }
        }
        return result;
    }
    private static long part2(List<List<Long>> input){
        long result = 0;
        for(List<Long> row : input){
            long goal = row.getFirst();
            int len = row.size();
            long maxOps = threePow(len - 2) - 1;
            for(long ops = 0; ops<=maxOps; ops++){
                if(attemptRow_threeOps(goal, row.subList(1, len), ops)){
                    result += goal;
                    break;
                }
            }
        }
        return result;
    }
    public static void main(String[] args) throws Exception {
        List<List<Long>> input = readFile(new File("input.txt"));
        System.out.println("Part #1 - " + part1(input));
        System.out.println("Part #2 - " + part2(input));
    }
}