import java.io.*;
import java.util.*;

class Main {
    private static char[][] translateGrid(List<List<Character>> input){
        final int height = input.size();
        final int width = input.stream().mapToInt(List::size).max().getAsInt();
        char[][] grid = new char[height][width];
        for(int row=0; row<height; row++){
            List<Character> rowList = input.get(row);
            for(int col=0; col<rowList.size(); col++){
                grid[row][col] = rowList.get(col);
            }
        }
        return grid;
    }
    private static ArrayList<Character> listChars(String input){
        final int len = input.length();
        ArrayList<Character> output = new ArrayList<>(len);
        for(int i=0; i<len; i++){
            output.add(input.charAt(i));
        }
        return output;
    }
    private static char[][] readGrid(File input) throws FileNotFoundException{
        List<List<Character>> grid = new ArrayList<>();
        Scanner scan = new Scanner(input);
        while(scan.hasNext()){
            grid.add(listChars(scan.nextLine().strip()));
        }
        scan.close();
        return translateGrid(grid);
    }
    private static boolean scanDown(char[][] grid, int row, int col, String str){
        //Bounds Checks
        if(row < 0) return false;
        if(col < 0) return false;
        if(row >= grid.length) return false;
        if(col >= grid[row].length) return false;
        int maxRow = grid.length - str.length();
        if(row > maxRow) return false;
        //Scan procedure
        for(int i=0; i<str.length(); i++){
            if(grid[row+i][col] != str.charAt(i)) return false;
        }
        return true;
    }
    private static boolean scanUp(char[][] grid, int row, int col, String str){
        //Bounds Checks
        if(row < 0) return false;
        if(col < 0) return false;
        if(row >= grid.length) return false;
        if(col >= grid[row].length) return false;
        int minRow = str.length() - 1;
        if(row < minRow) return false;
        //Scan procedure
        for(int i=0; i<str.length(); i++){
            if(grid[row-i][col] != str.charAt(i)) return false;
        }
        return true;
    }
    private static boolean scanLeft(char[][] grid, int row, int col, String str){
        //Bounds Checks
        if(row < 0) return false;
        if(col < 0) return false;
        if(row >= grid.length) return false;
        if(col >= grid[row].length) return false;
        int minCol = str.length() - 1;
        if(col < minCol) return false;
        //Scan procedure
        for(int i=0; i<str.length(); i++){
            if(grid[row][col-i] != str.charAt(i)) return false;
        }
        return true;
    }
    private static boolean scanRight(char[][] grid, int row, int col, String str){
        //Bounds Checks
        if(row < 0) return false;
        if(col < 0) return false;
        if(row >= grid.length) return false;
        if(col >= grid[row].length) return false;
        int maxCol = grid[row].length - str.length();
        if(col > maxCol) return false;
        //Scan procedure
        for(int i=0; i<str.length(); i++){
            if(grid[row][col+i] != str.charAt(i)) return false;
        }
        return true;
    }
    private static boolean scanUpLeft(char[][] grid, int row, int col, String str){
        //Bounds Checks
        if(row < 0) return false;
        if(col < 0) return false;
        if(row >= grid.length) return false;
        if(col >= grid[row].length) return false;
        int minRow = str.length() - 1;
        if(row < minRow) return false;
        int minCol = str.length() - 1;
        if(col < minCol) return false;
        //Scan procedure
        for(int i=0; i<str.length(); i++){
            if(grid[row-i][col-i] != str.charAt(i)) return false;
        }
        return true;
    }
    private static boolean scanUpRight(char[][] grid, int row, int col, String str){
        //Bounds Checks
        if(row < 0) return false;
        if(col < 0) return false;
        if(row >= grid.length) return false;
        if(col >= grid[row].length) return false;
        int minRow = str.length() - 1;
        if(row < minRow) return false;
        int maxCol = grid[row].length - str.length();
        if(col > maxCol) return false;
        //Scan procedure
        for(int i=0; i<str.length(); i++){
            if(grid[row-i][col+i] != str.charAt(i)) return false;
        }
        return true;
    }
    private static boolean scanDownLeft(char[][] grid, int row, int col, String str){
        //Bounds Checks
        if(row < 0) return false;
        if(col < 0) return false;
        if(row >= grid.length) return false;
        if(col >= grid[row].length) return false;
        int maxRow = grid.length - str.length();
        if(row > maxRow) return false;
        int minCol = str.length() - 1;
        if(col < minCol) return false;
        //Scan procedure
        for(int i=0; i<str.length(); i++){
            if(grid[row+i][col-i] != str.charAt(i)) return false;
        }
        return true;
    }
    private static boolean scanDownRight(char[][] grid, int row, int col, String str){
        //Bounds Checks
        if(row < 0) return false;
        if(col < 0) return false;
        if(row >= grid.length) return false;
        if(col >= grid[row].length) return false;
        int maxRow = grid.length - str.length();
        if(row > maxRow) return false;
        int maxCol = grid[row].length - str.length();
        if(col > maxCol) return false;
        //Scan procedure
        for(int i=0; i<str.length(); i++){
            if(grid[row+i][col+i] != str.charAt(i)) return false;
        }
        return true;
    }
    private static long scanGrid(char[][] grid, String str){
        final int height = grid.length;
        final int width = Arrays.stream(grid).mapToInt(row -> row.length).max().getAsInt();
        long total = 0;
        for(int row=0; row<height; row++){
            for(int col=0; col<width; col++){
                if(scanDown(grid, row, col, str)) total++;
                if(scanUp(grid, row, col, str)) total++;
                if(scanLeft(grid, row, col, str)) total++;
                if(scanRight(grid, row, col, str)) total++;
                if(scanUpLeft(grid, row, col, str)) total++;
                if(scanUpRight(grid, row, col, str)) total++;
                if(scanDownLeft(grid, row, col, str)) total++;
                if(scanDownRight(grid, row, col, str)) total++;
            }
        }
        return total;
    }
    private static long scanGridForGrid(char[][] grid, char[][] target, char wild){
        final int height = grid.length;
        final int width = Arrays.stream(grid).mapToInt(row -> row.length).max().getAsInt();
        final int target_height = target.length;
        final int target_width = Arrays.stream(target).mapToInt(row -> row.length).max().getAsInt();
        long total = 0;
        for(int row=0; row<height; row++){
            for(int col=0; col<width; col++){
                boolean match = true;
                for(int target_row=0; target_row<target_height; target_row++){
                    if(!match) break;
                    int calc_row = row + target_row;
                    if(calc_row >= height){
                        match = false;
                        break;
                    }
                    for(int target_col=0; target_col<target_width; target_col++){
                        int calc_col = col + target_col;
                        if(calc_col >= width){
                            match = false;
                            break;
                        }
                        if(target[target_row][target_col] == wild) continue;
                        if(grid[calc_row][calc_col] != target[target_row][target_col]){
                            match = false;
                            break;
                        }
                    }
                }
                if(match) total++;
            }
        }
        return total;
    }
    public static void main(String[] args) throws Exception{
        char[][] grid = readGrid(new File("input.txt"));
        System.out.println("Part #1 - " + scanGrid(grid, "XMAS"));
        char[][] x1 = {
            {'M', '.', 'S'},
            {'.', 'A', '.'},
            {'M', '.', 'S'}
        };
        char[][] x2 = {
            {'M', '.', 'M'},
            {'.', 'A', '.'},
            {'S', '.', 'S'}
        };
        char[][] x3 = {
            {'S', '.', 'M'},
            {'.', 'A', '.'},
            {'S', '.', 'M'}
        };
        char[][] x4 = {
            {'S', '.', 'S'},
            {'.', 'A', '.'},
            {'M', '.', 'M'}
        };
        long part2 = scanGridForGrid(grid, x1, '.');
        part2 += scanGridForGrid(grid, x2, '.');
        part2 += scanGridForGrid(grid, x3, '.');
        part2 += scanGridForGrid(grid, x4, '.');
        System.out.println("Part #2 - " + part2);
    }
}