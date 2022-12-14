import java.util.*;
import java.util.stream.*;
import java.io.*;
class Main{
  private static boolean checkRow(String row, int id){
    int k = row.codePointAt(id);
    return Long.min(IntStream.range(0,id).filter(i->row.codePointAt(i)>=k).count(), IntStream.range(id+1,row.length()).filter(i->row.codePointAt(i)>=k).count()) == 0;
  }
  private static boolean checkCol(ArrayList<String> rows, int row, int col){
    int k = rows.get(row).codePointAt(col);
    return Long.min(IntStream.range(0,row).filter(i->rows.get(i).codePointAt(col)>=k).count(), IntStream.range(row+1,rows.size()).filter(i->rows.get(i).codePointAt(col)>=k).count()) == 0;
  }
  private static int visible(ArrayList<String> rows, int row, int col){
    int k = rows.get(row).codePointAt(col), up=0, down=0, left=0, right=0;
    for(int i=row-1; i>=0; i--){
      up++;
      if(rows.get(i).codePointAt(col)>=k) break;
    }
    for(int i=row+1; i<rows.size(); i++){
      down++;
      if(rows.get(i).codePointAt(col)>=k) break;
    }
    for(int i=col-1; i>=0; i--){
      left++;
      if(rows.get(row).codePointAt(i)>=k) break;
    }
    for(int i=col+1; i<rows.get(row).length(); i++){
      right++;
      if(rows.get(row).codePointAt(i)>=k) break;
    }
    return up * down * left * right;
  }
  public static void main(String[] args) throws Exception{
    Scanner scan = new Scanner(new File("input.txt"));
    ArrayList<String> input = new ArrayList<>();
    while(scan.hasNext()) input.add(scan.nextLine());
    scan.close();
    int part1=input.size()*2+input.get(0).length()*2-4, part2=0;
    for(int row=1; row<input.size()-1; row++){
      for(int col=1; col<input.get(row).length()-1; col++){
        if(checkRow(input.get(row), col) || checkCol(input, row, col)) part1++;
        part2 = Integer.max(part2, visible(input, row, col));
      }
    }
    System.out.println("Part #1 - " + part1);
    System.out.println("Part #2 - " + part2);
  }
}