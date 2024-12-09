import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.IntStream;

class Main {
    private static long part1(ArrayList<Integer> blocks){
        for(int i=0; i<blocks.size(); i++){
            Integer b = blocks.get(i);
            if(b != null) continue;
            boolean giveUp = true;
            for(int x=i; x<blocks.size(); x++){
                if(blocks.get(x) != null){
                    giveUp = false;
                    break;
                }
            }
            if(giveUp) break;
            while(blocks.getLast() == null){
                blocks.removeLast();
            }
            blocks.set(i, blocks.removeLast());
        }
        while(blocks.getLast() == null){
            blocks.removeLast();
        }
        long result = 0;
        for(int i=1; i<blocks.size(); i++){
            long mult = i;
            long cell = blocks.get(i);
            result += mult * cell;
        }
        return result;
    }
    private static int locateGap(ArrayList<Integer> blocks, int len, int rightBound){
        assert len > 0;
        for(int i=0; i<rightBound; i++){
            if(blocks.get(i) != null) continue;
            boolean found = true;
            for(int c=1; c<len; c++){
                if(blocks.get(i+c) != null){
                    found = false;
                    break;
                }
            }
            if(found) return i;
        }
        return -1; //No gap of length "len" found.
    }
    private static long part2(ArrayList<Integer> blocks){
        int maxVal = blocks.stream().filter(i -> i!=null).mapToInt(i -> i).max().getAsInt();
        for(int b=maxVal; b>=1; b--){
            final int bb = b;
            int blockStart = IntStream.range(0, blocks.size()).filter(i -> blocks.get(i)!=null).filter(i -> blocks.get(i)==bb).findFirst().getAsInt();
            int blockLen = (int) blocks.stream().skip(blockStart).limit(10).filter(i -> i!=null).filter(i -> i==bb).count();
            int gapLoc = locateGap(blocks, blockLen, blockStart);
            if(gapLoc < 0) continue;
            for(int i=0; i<blockLen; i++){
                blocks.set(gapLoc+i, bb);
                blocks.set(blockStart+i, null);
            }
        }
        long result = 0;
        for(int i=1; i<blocks.size(); i++){
            Integer cellObj = blocks.get(i);
            if(cellObj == null) continue;
            long mult = i;
            long cell = cellObj;
            result += mult * cell;
        }
        return result;
    }
    public static void main(String[] args) throws Exception {
        String input = Files.readString(new File("input.txt").toPath()).strip();
        ArrayList<Integer> blocks = new ArrayList<>();
        boolean blank = false;
        int nextBlock = -1;
        for(int i=0; i<input.length(); i++){
            int reps = Character.getNumericValue(input.charAt(i));
            Integer val = blank ? null : ++nextBlock;
            for(int x=0; x<reps; x++){
                blocks.add(val);
            }
            blank = !blank;
        }
        System.out.println("Part #1 - " + part1(new ArrayList<>(blocks)));
        System.out.println("Part #2 - " + part2(new ArrayList<>(blocks)));
    }
}