import java.io.*;
import java.util.*;

class Main {
    private static byte[] int_arr_to_byte_arr(int[] input){
        byte[] result = new byte[input.length];
        for(int i=0; i<input.length; i++){
            int item = input[i];
            assert item <= 127;
            assert item >= -128;
            result[i] = (byte) item;
        }
        return result;
    }
    private static byte[] simMachine(long reg_a, long reg_b, long reg_c, byte[] instrux){
        int counter = 0;
        ArrayList<Integer> output = new ArrayList<>();
        while(counter < instrux.length){
            final byte op = instrux[counter];
            assert op <= 7;
            assert op >= 0;
            final byte raw_input = instrux[counter+1];
            assert raw_input <= 7;
            assert raw_input >= 0;
            int jump_val = -1;
            switch(op){
                case 0: { // adv
                    long combo_input = raw_input;
                    if(raw_input == 4) combo_input = reg_a;
                    if(raw_input == 5) combo_input = reg_b;
                    if(raw_input == 6) combo_input = reg_c;
                    if(raw_input == 7) throw new AssertionError("Invalid program. Combo operand with value of 7.");
                    long denom = 1l << combo_input;
                    reg_a = reg_a / denom;
                    break;
                }
                case 1: { // bxl
                    reg_b = reg_b ^ ((long) raw_input);
                    break;
                }
                case 2: { // bst
                    long combo_input = raw_input;
                    if(raw_input == 4) combo_input = reg_a;
                    if(raw_input == 5) combo_input = reg_b;
                    if(raw_input == 6) combo_input = reg_c;
                    if(raw_input == 7) throw new AssertionError("Invalid program. Combo operand with value of 7.");
                    reg_b = combo_input & 7;
                    break;
                }
                case 3: { // jnz
                    if(reg_a == 0) break;
                    jump_val = raw_input;
                    break;
                }
                case 4: { // bxc
                    reg_b = reg_b ^ reg_c;
                    break;
                }
                case 5: { // out
                    long combo_input = raw_input;
                    if(raw_input == 4) combo_input = reg_a;
                    if(raw_input == 5) combo_input = reg_b;
                    if(raw_input == 6) combo_input = reg_c;
                    if(raw_input == 7) throw new AssertionError("Invalid program. Combo operand with value of 7.");
                    output.add((int) (combo_input & 7));
                    break;
                }
                case 6: { // bdv
                    long combo_input = raw_input;
                    if(raw_input == 4) combo_input = reg_a;
                    if(raw_input == 5) combo_input = reg_b;
                    if(raw_input == 6) combo_input = reg_c;
                    if(raw_input == 7) throw new AssertionError("Invalid program. Combo operand with value of 7.");
                    long denom = 1l << combo_input;
                    reg_b = reg_a / denom;
                    break;
                }
                case 7: { // cdv
                    long combo_input = raw_input;
                    if(raw_input == 4) combo_input = reg_a;
                    if(raw_input == 5) combo_input = reg_b;
                    if(raw_input == 6) combo_input = reg_c;
                    if(raw_input == 7) throw new AssertionError("Invalid program. Combo operand with value of 7.");
                    long denom = 1l << combo_input;
                    reg_c = reg_a / denom;
                    break;
                }
                default:
                    throw new AssertionError("How the FUCK did this happen?!?!?");
            }
            if(jump_val >= 0){
                counter = jump_val;
            } else {
                counter += 2;
            }
        }
        return int_arr_to_byte_arr(output.stream().mapToInt(i->i).toArray());
    }
    private static String textOutput(byte[] input){
        if(input.length == 0) return "No output.";
        StringBuilder sb = new StringBuilder();
        sb.append(input[0]);
        for(int i=1; i<input.length; i++){
            sb.append(',');
            sb.append(input[i]);
        }
        return sb.toString();
    }
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(new File("input.txt"));
        String line = scan.nextLine().strip();
        assert line.startsWith("Register A:");
        long reg_a = Long.parseLong(line.split("[^\\d-]+")[1]);
        line = scan.nextLine().strip();
        assert line.startsWith("Register B:");
        long reg_b = Long.parseLong(line.split("[^\\d-]+")[1]);
        line = scan.nextLine().strip();
        assert line.startsWith("Register C:");
        long reg_c = Long.parseLong(line.split("[^\\d-]+")[1]);
        line = scan.nextLine().strip();
        assert line.isEmpty();
        line = scan.nextLine().strip();
        assert line.startsWith("Program:");
        byte[] instrux = int_arr_to_byte_arr(Arrays.stream(line.split("[^\\d-]+")).skip(1).mapToInt(Integer::parseInt).toArray());
        scan.close();
        System.out.println("Part #1 - " + textOutput(simMachine(reg_a, reg_b, reg_c, instrux)));
        System.out.println();
        System.out.println("Caution: Part #2 is not guaranteed to work on all inputs.");
        System.out.println("It might only work on Obscure's input.");
        System.out.println();
        //The following are TreeSets because:
        //  (a) I needed to remove duplicates
        //  (b) I wanted least-to-greatest ordered traversal
        TreeSet<Long> prev = null;
        TreeSet<Long> cur = new TreeSet<>();
        for(long test_a=0; test_a<=511; test_a++){
            byte[] result = simMachine(test_a, reg_b, reg_c, instrux);
            if(result[0] == instrux[0]){
                cur.add(test_a);
            }
        }
        long upper_bound = 0;
        for(int round=1; round<instrux.length; round++){
            prev = cur;
            cur = new TreeSet<>();
            for(long test_a=0; test_a<=511; test_a++){
                long shift_a = test_a << (3 * round);
                for(long p : prev){
                    long or = shift_a | p;
                    byte[] result = simMachine(or, reg_b, reg_c, instrux);
                    boolean bad = false;
                    for(int i=0; i<=round; i++){
                        if(i >= result.length){
                            bad = true;
                            break;
                        }
                        if(result[i] != instrux[i]){
                            bad = true;
                            break;
                        }
                    }
                    if(bad) continue;
                    cur.add(or);
                }
            }
            if(cur.isEmpty()){
                System.out.println("Part #2 - Became stuck on round " + round + " with no results.");
                return;
            }
            for(long c : cur){
                if(Arrays.equals(simMachine(c, reg_b, reg_c, instrux), instrux)){
                    upper_bound = c;
                    break;
                }
            }
            if(upper_bound > 0) break;
        }
        if(upper_bound == 0){
            throw new AssertionError("Completed all digit-matching rounds, but found no PERFECT match.");
        }
        int shift = (64 - Long.numberOfLeadingZeros(upper_bound)) - 12;
        long part2 = upper_bound;
        for(int i=1; i<=4095; i++){
            long meddle = ((long) i) << shift;
            long test = upper_bound ^ meddle;
            if(Arrays.equals(simMachine(test, reg_b, reg_c, instrux), instrux) && (test < part2)){
                part2 = test;
            }
        }
        System.out.println("Part #2 - " + part2);
    }
}