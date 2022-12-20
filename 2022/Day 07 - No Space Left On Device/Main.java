import java.util.*;
import java.util.stream.*;
import java.io.*;
class Main{
  private static int d(int i){
    return (-i)-1;
  }
  private static int getSize(int index, ArrayList<String> names, ArrayList<Integer> size, ArrayList<ArrayList<Integer>> children){
    if(size.get(index) < 0){
      return children.get(index).stream().mapToInt(i->i).map(i->getSize(i,names,size,children)).sum();
    }
    return size.get(index);
  }
  public static void main(String[] args) throws Exception{
    ArrayList<String> names = new ArrayList<>();
    ArrayList<Integer> size = new ArrayList<>();
    ArrayList<ArrayList<Integer>> children = new ArrayList<>();
    int wd = 0;
    Scanner scan = new Scanner(new File("input.txt"));
    while(scan.hasNext()){
      String[] bits = scan.nextLine().split(" ");
      if(bits[0].equals("$")){
        if(bits[1].equals("cd")){
          if(names.isEmpty() && bits[2].equals("/")){
            names.add("/");
            size.add(d(0));
            children.add(new ArrayList<>());
          } else if(bits[2].equals("..")){
            wd = d(size.get(wd));
          } else {
            wd = children.get(wd).stream().mapToInt(i->i).filter(i->names.get(i).equals(bits[2])).sum();
          }
        }
      } else {
        if(bits[0].equals("dir")){
          children.get(wd).add(names.size());
          size.add(d(wd));
          children.add(new ArrayList<>());
        } else {
          children.get(wd).add(names.size());
          size.add(Integer.valueOf(bits[0]));
          children.add(null);
        }
        names.add(bits[1]);
      }
    }
    scan.close();
    int[] cache = IntStream.range(0,names.size()).filter(i->size.get(i)<0).map(i->getSize(i,names,size,children)).toArray();
    int unused = 70000000 - getSize(0,names,size,children);
    System.out.println("Part #1 - " + Arrays.stream(cache).filter(i->i<=100000).sum());
    System.out.println("Part #2 - " + Arrays.stream(cache).filter(i->i+unused>=30000000).min().getAsInt());
  }
}