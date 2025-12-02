import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.LongStream;

class Main {
    private static LongStream buildStream(String[] ranges){
        ArrayList<LongStream> streams = new ArrayList<>();
        for(String c : ranges){
            final int mark = c.indexOf('-');
            final long left = Long.parseLong(c.substring(0,mark));
            final long right = Long.parseLong(c.substring(mark+1));
            streams.add(LongStream.rangeClosed(left, right));
        }
        return streams.stream().flatMapToLong(x -> x);
    }

    private static boolean invalid_part1(long id){
        final String str = Long.toString(id);
        if((str.length() & 1) == 1) return false;
        final int len = str.length() >> 1;
        final String left = str.substring(0, len);
        final String right = str.substring(len);
        return left.equals(right);
    }

    private static boolean chunksEqual(final String text, final int size){
        final int len = text.length();
        String[] chunks = new String[len/size];
        Arrays.fill(chunks, "");
        int index = 0;
        for(int i=0; i<len; i+=size){
            chunks[index] = text.substring(i, i+size);
            index++;
        }
        final int pair_limit = chunks.length - 1;
        for(int i=0; i<pair_limit; i++){
            if(!chunks[i].equals(chunks[i+1])) return false;
        }
        return true;
    }

    private static boolean invalid_part2(long id){
        final String str = Long.toString(id);
        final int len = str.length();
        final int limit = len >> 1;
        for(int i=1; i<=limit; i++){
            if(((len % i) == 0) && (chunksEqual(str, i))){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(new File("input.txt"));
        String[] chunks = scan.nextLine().strip().split(",");
        scan.close();
        Pattern p = Pattern.compile("^\\d+-\\d+$");
        for(String c : chunks){
            assert p.matcher(c).matches();
        }
        System.out.println("Part #1 - " + buildStream(chunks).parallel().filter(Main::invalid_part1).sum());
        System.out.println("Part #2 - " + buildStream(chunks).parallel().filter(Main::invalid_part2).sum());
    }
}