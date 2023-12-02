import java.util.*;
import java.io.*;
class Main{
  public static void main(String[] args) throws Exception{
    int prioritySum=0, tripletSum=0;
    String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    ArrayList<String> list = new ArrayList<>(3);
    Scanner scan = new Scanner(new File("input.txt"));
    while(scan.hasNext()){
      String k = scan.nextLine();
      int sl = k.length()/2;
      for(int i=0; i<sl; i++){
        int last = k.lastIndexOf(k.charAt(i));
        if(last >= sl){
          prioritySum += alphabet.indexOf(k.charAt(i))+1;
          break;
        }
      }
      list.add(k);
      if(list.size()==3){
        for(int i=0; i<alphabet.length(); i++){
          final char c = alphabet.charAt(i);
          if(list.stream().mapToInt(z->z.indexOf(c)).noneMatch(l->l<0)){
            tripletSum += i+1;
            break;
          }
        }
        list.clear();
      }
    }
    scan.close();
    System.out.println("Part #1 - " + prioritySum);
    System.out.println("Part #2 - " + tripletSum);
  }
}