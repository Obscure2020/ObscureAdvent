import java.io.*;
import java.util.*;

class Main{
    public static void main(String[] args) throws Exception{
        Scanner scan = new Scanner(new File("input.txt"));
        long paper = 0;
        long ribbon = 0;
        while(scan.hasNext()){
            int[] line = Arrays.stream(scan.nextLine().strip().split("x")).mapToInt(Integer::parseInt).toArray();
            int sideA = line[0] * line[1];
            int sideB = line[0] * line[2];
            int sideC = line[1] * line[2];
            paper += (2 * sideA) + (2 * sideB) + (2 * sideC);
            paper += Math.min(Math.min(sideA, sideB), sideC);
            int perimA = line[0] + line[0] + line[1] + line[1];
            int perimB = line[0] + line[0] + line[2] + line[2];
            int perimC = line[1] + line[1] + line[2] + line[2];
            ribbon += Math.min(Math.min(perimA, perimB), perimC);
            ribbon += line[0] * line[1] * line[2];
        }
        scan.close();
        System.out.println("Part #1 - " + paper);
        System.out.println("Part #2 - " + ribbon);
    }
}