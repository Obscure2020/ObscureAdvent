import java.io.*;
import java.util.*;

class Main {
    private static List<String> pieces = new ArrayList<>();
    private static List<String> targets = new ArrayList<>();
    private static HashMap<String,Long> ways = new HashMap<>();

    private static void readFile(File input) throws FileNotFoundException {
        ArrayList<String> targets_local = new ArrayList<>();
        Scanner scan = new Scanner(input);
        String line = scan.nextLine().strip();
        assert !line.isEmpty();
        pieces = Collections.unmodifiableList(Arrays.asList(line.split("[, ]+")));
        line = scan.nextLine().strip();
        assert line.isEmpty();
        assert scan.hasNext();
        while(scan.hasNext()){
            line = scan.nextLine().strip();
            assert !line.isEmpty();
            targets_local.add(line);
        }
        scan.close();
        targets_local.trimToSize();
        targets = Collections.unmodifiableList(targets_local);
    }
    private static long waysPossible(String target){
        if(target.isEmpty()) return 1;
        long lookup = ways.getOrDefault(target, -1l);
        if(lookup >= 0) return lookup;
        ArrayList<String> bits = new ArrayList<>();
        for(String piece : pieces){
            if(target.startsWith(piece)){
                bits.add(target.substring(piece.length()));
            }
        }
        long result = 0;
        for(String bit : bits){
            result += waysPossible(bit);
        }
        ways.put(target, result);
        return result;
    }
    public static void main(String[] args) throws Exception {
        readFile(new File("input.txt"));
        long[] results = targets.stream().mapToLong(Main::waysPossible).toArray();
        long part1 = 0;
        long part2 = 0;
        for(long l : results){
            part2 += l;
            if(l > 0) part1++;
        }
        System.out.println("Part #1 - " + part1);
        System.out.println("Part #2 - " + part2);
    }
}