import java.util.*;
import java.io.*;
import java.math.BigInteger;
class Main{
    private static String replaceChars(String original, char fill, String targets){
        String work = original;
        for(int i=0; i<targets.length(); i++){
            char c = targets.charAt(i);
            work = work.replace(c, fill);
        }
        return work;
    }
    private static long walk(String walkID, String[] ends, HashMap<String,Pair> pairs, String directions){
        long cycles = 0;
        String[] endIDs = Arrays.copyOf(ends, ends.length);
        Arrays.sort(endIDs); //Must do sort locally so we're SURE it has been done, so we can use binarySearch()
        Pair walker = pairs.get(walkID);
        while(Arrays.binarySearch(endIDs, walkID) < 0){
            int dirIndex = (int) (cycles % directions.length());
            boolean goLeft = directions.charAt(dirIndex) == 'L';
            if(goLeft){
                walkID = walker.left;
            } else {
                walkID = walker.right;
            }
            walker = pairs.get(walkID);
            cycles++;
        }
        return cycles;
    }
    private static BigInteger lcm(BigInteger one, BigInteger two){
        BigInteger gcd = one.gcd(two);
        BigInteger prod = one.multiply(two).abs();
        return prod.divide(gcd);
    }
    private static BigInteger largeLCM(long[] inputs){
        BigInteger work = new BigInteger(Long.toString(inputs[0]));
        for(int i=1; i<inputs.length; i++){
            work = lcm(work, new BigInteger(Long.toString(inputs[i])));
        }
        return work;
    }
    public static void main(String[] args) throws Exception{
        Scanner scan = new Scanner(new File("input.txt"));
        String directions = scan.nextLine().trim();
        scan.nextLine(); //Skipping blank line in input.
        HashMap<String,Pair> pairs = new HashMap<>();
        while(scan.hasNext()){
            String[] pieces = replaceChars(scan.nextLine(), ' ', "=(,)").trim().split(" +");
            pairs.put(pieces[0], new Pair(pieces[1], pieces[2]));
        }
        scan.close();

        long savedTime = System.currentTimeMillis(); //Added timing logic to compete with friends.
        System.out.println("Part #1 - " + walk("AAA", new String[] {"ZZZ"}, pairs, directions));
        long duration = System.currentTimeMillis() - savedTime;
        System.out.println(duration + " ms");
        System.out.println();

        savedTime = System.currentTimeMillis();
        String[] walkIDs = pairs.keySet().stream().filter(s -> s.endsWith("A")).toList().toArray(new String[0]);
        String[] endIDs = pairs.keySet().stream().filter(s -> s.endsWith("Z")).toList().toArray(new String[0]);
        long[] results = Arrays.stream(walkIDs).parallel().mapToLong(s -> walk(s, endIDs, pairs, directions)).toArray();
        System.out.println("Part #2 - " + largeLCM(results));
        duration = System.currentTimeMillis() - savedTime;
        System.out.println(duration + " ms");
    }
}