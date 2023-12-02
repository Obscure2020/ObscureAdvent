import java.util.*;
import java.util.stream.*;
import java.io.*;
class Main{
  private static void preStack(ArrayList<ArrayDeque<String>> st, Scanner scan){
    ArrayDeque<String> strings = new ArrayDeque<>();
    while(true){
      String k = scan.nextLine();
      if(k.indexOf('[') < 0){
        int[] spots = IntStream.rangeClosed(0, k.length()-1).filter(i -> k.charAt(i)!=' ').toArray();
        Arrays.stream(spots).forEach(i->st.add(new ArrayDeque<>()));
        while(strings.size()>0){
          String line = strings.pop();
          for(int i=0; i<spots.length; i++) if(spots[i]<line.length() && line.charAt(spots[i])!=' ') st.get(i).push(line.substring(spots[i],spots[i]+1));
        }
        break;
      }
      strings.push(k);
    }
  }
  private static void p(String s, ArrayList<ArrayDeque<String>> a){
    Stream.concat(Stream.of(s), a.stream().map(d->d.peek())).forEach(System.out::print);
    System.out.println();
  }
  public static void main(String[] args) throws Exception{
    ArrayList<ArrayDeque<String>> st = new ArrayList<>();
    ArrayList<ArrayDeque<String>> st2 = new ArrayList<>();
    Scanner scan = new Scanner(new File("input.txt"));
    preStack(st, scan);
    scan.nextLine();
    Scanner scan2 = new Scanner(new File("input.txt"));
    preStack(st2, scan2);
    scan2.close();
    while(scan.hasNext()){
      String k = scan.nextLine();
      int m1=k.indexOf(' ', 5), m2=k.indexOf(' ', 9)+1, m3=k.indexOf(' ', m2), m4=k.indexOf(' ', m3+1)+1,num=Integer.valueOf(k.substring(5, m1)),source=Integer.valueOf(k.substring(m2, m3))-1,target=Integer.valueOf(k.substring(m4))-1;
      for(int i=0; i<num; i++) st.get(target).push(st.get(source).pop());
      ArrayDeque<String> tempAD = new ArrayDeque<>();
      for(int i=0; i<num; i++) tempAD.push(st2.get(source).pop());
      while(tempAD.size()>0) st2.get(target).push(tempAD.pop());
    }
    scan.close();
    p("Part #1 - ", st);
    p("Part #2 - ", st2);
  }
}