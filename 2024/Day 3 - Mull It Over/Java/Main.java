import java.io.*;
import java.nio.file.Files;
import java.util.*;

class Main {
    private static String[] findMatches(String input, String regex){
        return Arrays.stream(input.splitWithDelimiters(regex, 0)).filter(s -> s.matches(regex)).toList().toArray(new String[0]);
    }
    private static long evalMul(String input){
        //This method assumes that input already matches the syntax of a mul statement.
        final int comma = input.indexOf(',');
        assert comma >= 0;
        long left = Long.parseLong(input.substring(input.indexOf('(')+1, comma));
        long right = Long.parseLong(input.substring(comma+1, input.indexOf(')')));
        return left * right;
    }
    private static long evalPart2(String[] parts){
        //This method assumes that parts contains only the following three types of strings:
        //    * Strings that are exactly "do()"
        //    * Strings that are exactly "don't()"
        //    * Strings that match the syntax of a mul statement.
        long total = 0;
        boolean enabled = true;
        for(String s : parts){
            if(s.equals("do()")){
                enabled = true;
                continue;
            }
            if(s.equals("don't()")){
                enabled = false;
                continue;
            }
            if(enabled){
                total += evalMul(s);
            }
        }
        return total;
    }
    public static void main(String[] args) throws Exception{
        String input = Files.readString(new File("input.txt").toPath()).strip();
        String[] parts = findMatches(input, "mul\\([0-9]+,[0-9]+\\)");
        System.out.println("Part #1 - " + Arrays.stream(parts).mapToLong(Main::evalMul).sum());
        parts = findMatches(input, "(mul\\([0-9]+,[0-9]+\\))|(do\\(\\))|(don't\\(\\))");
        System.out.println("Part #2 - " + evalPart2(parts));
    }
}