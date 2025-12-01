import java.nio.file.*;
import java.util.*;
import java.util.regex.Pattern;

class Main {
    private static final String l1 = "L1";
    private static final String r1 = "R1";

    private static int part1(List<String> turns){
        int dial = 50;
        int zeros = 0;
        for(String turn : turns){
            final int adjust = Integer.parseInt(turn.replace("L", "-").replace("R", ""));
            dial += adjust;
            while(dial < 0) dial += 100;
            while(dial > 99) dial -= 100;
            if(dial == 0) zeros++;
        }
        return zeros;
    }

    private static List<String> part2(List<String> turns){
        ArrayList<String> result = new ArrayList<>();
        for(String turn : turns){
            final int distance = Integer.parseInt(turn.substring(1));
            if(turn.charAt(0) == 'L'){
                for(int i=0; i<distance; i++){
                    result.add(l1);
                }
            } else {
                for(int i=0; i<distance; i++){
                    result.add(r1);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        List<String> turns = Files.newBufferedReader(Path.of("input.txt")).lines().map(String::strip).filter(s -> !s.isBlank()).toList();
        Pattern p = Pattern.compile("^[LR]\\d+$");
        for(String turn : turns){
            assert p.matcher(turn).matches();
        }
        System.out.println("Part #1 - " + part1(turns));
        System.out.println("Part #2 - " + part1(part2(turns)));
    }
}