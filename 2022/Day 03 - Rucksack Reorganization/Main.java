import java.util.*;
import java.io.*;
class Main{
    private static int priority(char c){
        int result = (int)(c ^ ' ') - 'A';
        return result>=32 ? result-5 : result+1;
    }
    public static void main(String[] args) throws Exception{
        int prioritySum = 0;
        int tripletSum = 0;
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        ArrayList<String> list = new ArrayList<>(3);
        Scanner scan = new Scanner(new File("input.txt"));
        while(scan.hasNext()){
            String k = scan.nextLine();
            int sl = k.length()/2;
            for(int i=0; i<sl; i++){
                int last = k.lastIndexOf(k.charAt(i));
                if(last >= sl){
                    prioritySum += priority(k.charAt(i));
                    break;
                }
            }
            list.add(k);
            if(list.size()==3){
                for(int i=0; i<alphabet.length(); i++){
                    if(list.get(0).indexOf(alphabet.charAt(i)) >= 0){
                        if(list.get(1).indexOf(alphabet.charAt(i)) >= 0){
                            if(list.get(2).indexOf(alphabet.charAt(i)) >= 0){
                                tripletSum += priority(alphabet.charAt(i));
                                break;
                            }
                        }
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