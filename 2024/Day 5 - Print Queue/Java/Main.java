import java.io.*;
import java.util.*;

class Main {
    private static boolean matchesRule(List<Integer> rule, List<Integer> update){
        assert rule.size() == 2;
        int indexA = update.indexOf(rule.get(0));
        int indexB = update.indexOf(rule.get(1));
        if((indexA < 0) || (indexB < 0)) return true;
        if(indexA > indexB) return false;
        return true;
    }
    private static boolean matchesRules(List<List<Integer>> rules, List<Integer> update){
        for(List<Integer> rule : rules){
            if(!matchesRule(rule, update)) return false;
        }
        return true;
    }
    private static int middleValue(List<Integer> input){
        assert (input.size() & 1) == 1; //input is of odd length
        return input.get(input.size() / 2);
    }
    private static void coerceRule(List<Integer> rule, List<Integer> update){
        assert rule.size() == 2;
        int indexA = update.indexOf(rule.get(0));
        int indexB = update.indexOf(rule.get(1));
        if((indexA < 0) || (indexB < 0)) return;
        if(indexA > indexB){
            update.set(indexA, rule.get(1));
            update.set(indexB, rule.get(0));
        }
    }
    private static void coerceRules(List<List<Integer>> rules, List<Integer> update){
        for(List<Integer> rule : rules){
            coerceRule(rule, update);
        }
    }
    public static void main(String[] args) throws Exception{
        List<List<Integer>> rules = new ArrayList<>();
        List<List<Integer>> updates = new ArrayList<>();
        Scanner scan = new Scanner(new File("input.txt"));
        while(true){
            String line = scan.nextLine().strip();
            if(line.isEmpty()) break;
            rules.add(Arrays.stream(line.split("\\|")).map(Integer::valueOf).toList());
        }
        assert scan.hasNext();
        while(scan.hasNext()){
            String line = scan.nextLine().strip();
            updates.add(Arrays.stream(line.split(",")).map(Integer::valueOf).toList());
        }
        scan.close();
        List<List<Integer>> goodUpdates = new ArrayList<>();
        List<List<Integer>> badUpdates = new ArrayList<>();
        for(List<Integer> update : updates){
            if(matchesRules(rules, update)){
                goodUpdates.add(update);
            } else {
                badUpdates.add(update);
            }
        }
        System.out.println("Part #1 - " + goodUpdates.stream().mapToInt(Main::middleValue).sum());
        for(int i=0; i<badUpdates.size(); i++){
            ArrayList<Integer> replace = new ArrayList<>(badUpdates.get(i));
            while(true){
                //This has to be looped because one round of rule coercions
                //may create new rule violations. We keep coercing the list
                //until no changes are detected from the previous round.
                ArrayList<Integer> copy = new ArrayList<>(replace);
                coerceRules(rules, copy);
                if(copy.equals(replace)) break;
                replace = copy;
            }
            badUpdates.set(i, replace);
        }
        System.out.println("Part #2 - " + badUpdates.stream().mapToInt(Main::middleValue).sum());
    }
}