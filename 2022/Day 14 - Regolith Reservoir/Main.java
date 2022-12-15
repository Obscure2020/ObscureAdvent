import java.util.*;
import java.io.*;
class Main{
  public static void main(String[] args) throws Exception{
    Scanner scan = new Scanner(new File("input.txt"));
    int width=0, height=0, minX=Integer.MAX_VALUE;
    while(scan.hasNext()){
      int[] bits = Arrays.stream(scan.nextLine().split("( -> )|,")).mapToInt(Integer::valueOf).toArray();
      for(int i=0; i<bits.length/2; i++){
        width = Integer.max(width, bits[i*2]);
        height = Integer.max(height, bits[i*2+1]);
        minX = Integer.min(minX, bits[i*2]);
      }
    }
    scan.close();
    height += 2;
    width -= minX;
    width += 3+height+height; //Memory inefficient but a safe bet.
    char[][] grid = new char[height][width];
    for(int r=0; r<height; r++) for(int c=0; c<width; c++) grid[r][c] = '.';
    scan = new Scanner(new File("input.txt"));
    while(scan.hasNext()){
      int[] bits = Arrays.stream(scan.nextLine().split("( -> )|,")).mapToInt(Integer::valueOf).toArray();
      for(int i=1; i<bits.length/2; i++){
        int x1 = bits[(i-1)*2];
        int y1 = bits[(i-1)*2+1];
        int x2 = bits[i*2];
        int y2 = bits[i*2+1];
        for(int r=Integer.min(y1,y2); r<=Integer.max(y1,y2); r++) for(int c=Integer.min(x1,x2); c<=Integer.max(x1,x2); c++) grid[r][c-minX+height+1] = '#';
      }
    }
    scan.close();
    int sandCount=0, sandRow=0, sandCol=500-minX+height+1;
    while(sandRow < height-1){
      int[] testDx = {0,-1,1};
      StringBuilder tests = new StringBuilder(3);
      for(int k : testDx) tests.append(grid[sandRow+1][sandCol+k]);
      int spot = tests.indexOf(".");
      if(spot < 0){
        grid[sandRow][sandCol] = 'o';
        sandCount++;
        sandRow = 0;
        sandCol = 500-minX+height+1;
      } else {
        sandRow++;
        sandCol += testDx[spot];
      }
    }
    System.out.println("Part #1 - " + sandCount);
    sandRow=0;
    sandCol=500-minX+height+1;
    while(grid[sandRow][sandCol] != 'o'){
      int[] testDx = {0,-1,1};
      StringBuilder tests = new StringBuilder(3);
      for(int k : testDx) tests.append(grid[sandRow+1][sandCol+k]);
      int spot = tests.indexOf(".");
      if(spot < 0){
        grid[sandRow][sandCol] = 'o';
        sandCount++;
        sandRow = 0;
        sandCol = 500-minX+height+1;
      } else {
        sandRow++;
        sandCol += testDx[spot];
      }
      if(sandRow == height-1){
        grid[sandRow][sandCol] = 'o';
        sandCount++;
        sandRow = 0;
        sandCol = 500-minX+height+1;
      }
    }
    System.out.println("Part #2 - " + sandCount);
  }
}