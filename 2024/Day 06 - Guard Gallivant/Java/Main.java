import java.util.*;
import java.io.*;

class Main {
    private static Pair<BitGrid, IntPoint> readFile(File input) throws FileNotFoundException{
        ArrayList<String> lines = new ArrayList<>();
        Scanner scan = new Scanner(input);
        while(scan.hasNext()){
            String line = scan.nextLine().strip();
            assert !line.isEmpty();
            lines.add(line);
        }
        scan.close();
        assert lines.stream().distinct().count() == 1;
        final int width = lines.getFirst().length();
        final int height = lines.size();
        BitGrid grid = new BitGrid(width, height);
        IntPoint guard = new IntPoint(0, 0);
        for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){
                switch(lines.get(y).charAt(x)){
                    case '#' -> grid.setBit(x, y, true);
                    case '^' -> guard = new IntPoint(x, y);
                }
            }
        }
        return new Pair<>(grid, guard);
    }
    private static long part1(BitGrid map, IntPoint start){
        int dir = 0;
        int x = start.x;
        int y = start.y;
        BitGrid progress = new BitGrid(map.width, map.height);
        progress.setBit(x, y, true);
        while(true){
            int newX = x;
            int newY = y;
            switch(dir){
                case 0 -> newY--;
                case 1 -> newX++;
                case 2 -> newY++;
                case 3 -> newX--;
                default -> throw new AssertionError("This should not be possible.");
            }
            if(newY < 0) break;
            if(newY >= map.height) break;
            if(newX < 0) break;
            if(newX >= map.width) break;
            if(map.getBit(newX, newY)){
                dir = (dir + 1) & 3;
            } else {
                x = newX;
                y = newY;
                progress.setBit(x, y, true);
            }
        }
        return progress.bitCount();
    }
    private static boolean findLoop(BitGrid map, IntPoint start){
        int dir = 0;
        int x = start.x;
        int y = start.y;
        byte[][] progress = new byte[map.height][map.width];
        progress[y][x] |= 1 << dir;
        while(true){
            int newX = x;
            int newY = y;
            switch(dir){
                case 0 -> newY--;
                case 1 -> newX++;
                case 2 -> newY++;
                case 3 -> newX--;
                default -> throw new AssertionError("This should not be possible.");
            }
            if(newY < 0) break;
            if(newY >= map.height) break;
            if(newX < 0) break;
            if(newX >= map.width) break;
            if(map.getBit(newX, newY)){
                dir = (dir + 1) & 3;
                progress[y][x] |= 1 << dir;
            } else {
                x = newX;
                y = newY;
                if((progress[y][x] & (1 << dir)) != 0) return true;
                progress[y][x] |= 1 << dir;
            }
        }
        return false;
    }
    private static long part2(BitGrid map, IntPoint start){
        long result = 0;
        for(int y=0; y<map.height; y++){
            for(int x=0; x<map.width; x++){
                if((y == start.y) && (x == start.x)) continue;
                BitGrid newMap = new BitGrid(map);
                newMap.setBit(x, y, true);
                if(findLoop(newMap, start)) result++;
            }
        }
        return result;
    }
    public static void main(String[] args) throws Exception {
        Pair<BitGrid, IntPoint> input = readFile(new File("input.txt"));
        System.out.println("Part #1 - " + part1(input.first, input.second));
        System.out.println("Part #2 - " + part2(input.first, input.second));
    }
}