import java.util.*;
import java.util.stream.*;
import java.io.*;
class Main{
  private static long get(String s, HashMap<String,String> map){
    String[] b = map.get(s).split(" ");
    if(b.length==1) return Long.valueOf(b[0]);
    long left = get(b[0], map);
    long right = get(b[2], map);
    long result = 0;
    switch(b[1].charAt(0)){
      case '+':
        result = left + right;
        break;
      case '-':
        result = left - right;
        break;
      case '*':
        result = left * right;
        break;
      case '/':
        result = left / right;
        break;
    }
    return result;
  }
  private static HashSet<String> mark(String s, HashMap<String,String> map){
    HashSet<String> bad = new HashSet<>();
    bad.add(s);
    int track = 0;
    while(bad.size() > track){
      track = bad.size();
      for(String k : map.keySet()){
        String[] b = map.get(k).split(" ");
        if(b.length==1) continue;
        if(bad.contains(b[0]) || bad.contains(b[2])) bad.add(k);
      }
    }
    return bad;
  }
  private static void failCase(int i){
    System.out.println("Sorry, didn't account for this. Error type " + i + ".");
    System.out.println("Let me know if this text ever appears.");
    System.out.println(" - Joey (Obscure)");
    System.exit(0);
  }
  private static long needs(String start, String end, long must, HashMap<String,String> map, HashSet<String> bad){
    String[] b = map.get(start).split(" ");
    if(bad.contains(b[0]) && bad.contains(b[2])) failCase(2);
    long result = 0;
    if(bad.contains(b[0])){ //Left is unknown
      long right = get(b[2],map);
      long left = 0;
      switch(b[1].charAt(0)){
        case '+':
          left = must - right;
          break;
        case '-':
          left = must + right;
          break;
        case '*':
          left = must / right;
          break;
        case '/':
          left = must * right;
          break;
      }
      if(b[0].equals(end)) return left;
      result = needs(b[0], end, left, map, bad);
    } else { //Right is unknown
      long left = get(b[0],map);
      long right = 0;
      switch(b[1].charAt(0)){
        case '+':
          right = must - left;
          break;
        case '-':
          right = left - must;
          break;
        case '*':
          right = must / left;
          break;
        case '/':
          right = left / must;
          break;
      }
      if(b[2].equals(end)) return right;
      result = needs(b[2], end, right, map, bad);
    }
    return result;
  }
  public static void main(String[] args) throws Exception{
    HashMap<String,String> map = new HashMap<>();
    Scanner scan = new Scanner(new File("input.txt"));
    while(scan.hasNext()){
      String[] b = scan.nextLine().split(": ");
      map.put(b[0], b[1]);
    }
    scan.close();
    System.out.println("Part #1 - " + get("root", map));
    HashSet<String> bad = mark("humn", map);
    String good = "", start = "";
    {
      String[] starts = IntStream.of(0,2).mapToObj(i->map.get("root").split(" ")[i]).toList().toArray(new String[0]);
      if(Arrays.stream(starts).allMatch(bad::contains)) failCase(1);
      int g = IntStream.of(0,1).filter(i->!bad.contains(starts[i])).findFirst().getAsInt();
      good = starts[g];
      start = starts[1-g];
    }
    long known = get(good, map);
    System.out.println("Part #2 - " + needs(start, "humn", known, map, bad));
  }
}