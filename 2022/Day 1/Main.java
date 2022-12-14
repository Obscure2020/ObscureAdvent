import java.util.*;
import java.io.*;

class Main{

    public static void main(String[] args) throws Exception{
        //Read input from file
        Scanner scan = new Scanner(new File("input.txt"));
        ArrayList<String> input = new ArrayList<>();
        while(scan.hasNext()) input.add(scan.nextLine());
        scan.close();

        //Tabulate elves
        ArrayList<Integer> running = new ArrayList<>();
        ArrayList<Integer> elves = new ArrayList<>();
        for(String k : input){
            if(k.isEmpty()){
                elves.add(running.stream().mapToInt(i -> i).sum());
                running.clear();
            } else {
                running.add(Integer.valueOf(k));
            }
        }
        if(!running.isEmpty()){
            elves.add(running.stream().mapToInt(i -> i).sum());
            running.clear();
        }

        //Print Answers
        Collections.sort(elves);
        Collections.reverse(elves);
        int part1 = elves.get(0);
        int part2 = elves.get(0) + elves.get(1) + elves.get(2);
        System.out.println("Part 1 Answer = " + part1);
        System.out.println("Part 2 Answer = " + part2);
    }

}