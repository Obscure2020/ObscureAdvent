import java.util.*;
import java.io.*;
class Main{
  private static boolean isDigit(char input){
    return ('0' <= input) && (input <= '9');
  }
  private static boolean isSymbol(char input){
    return !isDigit(input) && (input != '.');
  }
  private static char gridLookup(char[][] grid, int row, int col){
    if(row < 0) return '.';
    if(row >= grid.length) return '.';
    if(col < 0) return '.';
    if(col >= grid[row].length) return '.';
    return grid[row][col];
  }
  private static int[] extractNum(char[][] grid, int row, int col){
    if(!isDigit(gridLookup(grid, row, col))){
      throw new IllegalArgumentException("There is not a number at this location.");
    }
    ArrayList<Integer> colVals = new ArrayList<>();
    colVals.add(col);
    while(true){
      ArrayList<Integer> newVals = new ArrayList<>();
      for(int c : colVals){
        int leftLoc = c-1;
        char leftChar = gridLookup(grid, row, leftLoc);
        int rightLoc = c+1;
        char rightChar = gridLookup(grid, row, rightLoc);
        if(isDigit(leftChar) && !colVals.contains(leftLoc)) newVals.add(leftLoc);
        if(isDigit(rightChar) && !colVals.contains(rightLoc)) newVals.add(rightLoc);
      }
      if(newVals.isEmpty()) break;
      colVals.addAll(newVals);
    }
    Collections.sort(colVals);
    StringBuilder sb = new StringBuilder();
    for(int c : colVals) sb.append(gridLookup(grid, row, c));
    return new int[] {Integer.parseInt(sb.toString()), colVals.getFirst(), colVals.getLast()};
  }
  public static void main(String[] args) throws Exception{
    final char[][] grid;
    final int width, height;
    int partOne = 0, partTwo = 0;
    {
      ArrayList<String> input = new ArrayList<>();
      Scanner scan = new Scanner(new File("input.txt"));
      while(scan.hasNext()) input.add(scan.nextLine().trim()); //Trimming just in case
      scan.close();
      height = input.size();
      width = input.get(0).length();
      grid = new char[height][width];
      for(int row=0; row<height; row++){
        for(int col=0; col<width; col++){
          grid[row][col] = input.get(row).charAt(col);
        }
      }
    } //Get rid of scan and input
    StringBuilder sb = new StringBuilder();
    ArrayList<Integer> colVals = new ArrayList<>();
    for(int row=-1; row<=height; row++){ // Searching outside of the bounds by one row and column, as enabled by
      for(int col=-1; col<=width; col++){// gridLookup, fixed a bug that produced wrong answers. Huh!
        char c = gridLookup(grid, row, col);
        if(isDigit(c)){
          sb.append(c);
          colVals.add(col);
        } else if(!sb.isEmpty()){
          final int number = Integer.parseInt(sb.toString());
          {
            IntSummaryStatistics stats = colVals.stream().mapToInt(i->i).summaryStatistics();
            colVals.addFirst(stats.getMin() - 1);
            colVals.add(stats.getMax() + 1);
          } //Get rid of stats
          boolean symbol = false;
          for(int cv : colVals){
            symbol = symbol || isSymbol(gridLookup(grid, row-1, cv));
          }
          symbol = symbol || isSymbol(gridLookup(grid, row, colVals.getFirst()));
          symbol = symbol || isSymbol(gridLookup(grid, row, colVals.getLast()));
          for(int cv : colVals){
            symbol = symbol || isSymbol(gridLookup(grid, row+1, cv));
          }
          if(symbol) partOne += number;
          sb.setLength(0);
          colVals.clear();
        }
      }
    }
    for(int row=0; row<height; row++){
      for(int col=0; col<width; col++){
        if(grid[row][col] == '*'){
          ArrayList<Integer> found = new ArrayList<>();
          int[] lastSeen = {0, 0, 0}, foundInfo = {0, 0, 0};
          for(int i=col-1; i<=col+1; i++){
            if(isDigit(gridLookup(grid, row-1, i))){
              foundInfo = extractNum(grid, row-1, i);
              if(!Arrays.equals(lastSeen, foundInfo)) found.add(foundInfo[0]);
              lastSeen = foundInfo;
            }
          }
          lastSeen = new int[] {0, 0, 0};
          if(isDigit(gridLookup(grid, row, col-1))){
            foundInfo = extractNum(grid, row, col-1);
            if(!Arrays.equals(lastSeen, foundInfo)) found.add(foundInfo[0]);
            lastSeen = foundInfo;
          }
          if(isDigit(gridLookup(grid, row, col+1))){
            foundInfo = extractNum(grid, row, col+1);
            if(!Arrays.equals(lastSeen, foundInfo)) found.add(foundInfo[0]);
            lastSeen = foundInfo;
          }
          lastSeen = new int[] {0, 0, 0};
          for(int i=col-1; i<=col+1; i++){
            if(isDigit(gridLookup(grid, row+1, i))){
              foundInfo = extractNum(grid, row+1, i);
              if(!Arrays.equals(lastSeen, foundInfo)) found.add(foundInfo[0]);
              lastSeen = foundInfo;
            }
          }
          if(found.size() == 2){
            partTwo += found.get(0) * found.get(1);
          }
        }
      }
    }
    System.out.println("Part #1 - " + partOne);
    System.out.println("Part #2 - " + partTwo);
  }
}