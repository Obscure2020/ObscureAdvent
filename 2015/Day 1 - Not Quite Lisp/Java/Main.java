import java.io.*;
import java.nio.file.Files;

class Main{
    public static void main(String[] args) throws Exception{
        String input = Files.readString(new File("input.txt").toPath()).strip();
        int count = 0;
        int basement = -1;
        for(int i=0; i<input.length(); i++){
            if(input.charAt(i) == '('){
                count++;
            } else {
                count--;
            }
            if(basement < 0 && count < 0){
                basement = i + 1;
            }
        }
        System.out.println("Part #1 - " + count);
        System.out.println("Part #2 - " + basement);
    }
}