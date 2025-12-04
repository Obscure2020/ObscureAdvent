import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.util.regex.Pattern;

class Main {
    private static BitGrid readFile(Path input) throws IOException {
        List<String> lines = Files.newBufferedReader(input).lines().map(String::strip).filter(s -> !s.isBlank()).toList();
        Pattern p = Pattern.compile("^[.@]+$");
        for(String line : lines) assert p.matcher(line).matches();
        int[] lengths = lines.stream().mapToInt(String::length).distinct().toArray();
        assert lengths.length == 1;
        BitGrid result = new BitGrid(lengths[0], lines.size());
        for(int y=0; y<lines.size(); y++){
            for(int x=0; x<lengths[0]; x++){
                if(lines.get(y).charAt(x) == '@'){
                    result.setBit(x, y, true);
                }
            }
        }
        return result;
    }

    private static int part1(BitGrid data){
        final int y_max = data.height - 1;
        final int x_max = data.width - 1;
        int result = 0;
        for(int y=0; y<=y_max; y++){
            for(int x=0; x<=x_max; x++){
                if(!data.getBit(x, y)) continue;
                int count = 0;
                if(y > 0){
                    if((x > 0) && data.getBit(x-1, y-1)) count++;
                    if(data.getBit(x, y-1)) count++;
                    if((x < x_max) && data.getBit(x+1, y-1)) count++;
                }
                if(y < y_max){
                    if((x > 0) && data.getBit(x-1, y+1)) count++;
                    if(data.getBit(x, y+1)) count++;
                    if((x < x_max) && data.getBit(x+1, y+1)) count++;
                }
                if((x > 0) && data.getBit(x-1, y)) count++;
                if((x < x_max) && data.getBit(x+1, y)) count++;
                if(count < 4) result++;
            }
        }
        return result;
    }

    private static int part2(BitGrid data_in){
        BitGrid data = new BitGrid(data_in);
        final int y_max = data.height - 1;
        final int x_max = data.width - 1;
        int prev_result = -1;
        int cur_result = 0;
        while(cur_result != prev_result){
            prev_result = cur_result;
            for(int y=0; y<=y_max; y++){
                for(int x=0; x<=x_max; x++){
                    if(!data.getBit(x, y)) continue;
                    int count = 0;
                    if(y > 0){
                        if((x > 0) && data.getBit(x-1, y-1)) count++;
                        if(data.getBit(x, y-1)) count++;
                        if((x < x_max) && data.getBit(x+1, y-1)) count++;
                    }
                    if(y < y_max){
                        if((x > 0) && data.getBit(x-1, y+1)) count++;
                        if(data.getBit(x, y+1)) count++;
                        if((x < x_max) && data.getBit(x+1, y+1)) count++;
                    }
                    if((x > 0) && data.getBit(x-1, y)) count++;
                    if((x < x_max) && data.getBit(x+1, y)) count++;
                    if(count < 4){
                        cur_result++;
                        data.setBit(x, y, false);
                    }
                }
            }
        }
        return cur_result;
    }

    public static void main(String[] args) throws Exception {
        BitGrid grid = readFile(Path.of("input.txt"));
        System.out.println("Part #1 - " + part1(grid));
        System.out.println("Part #2 - " + part2(grid));
    }
}