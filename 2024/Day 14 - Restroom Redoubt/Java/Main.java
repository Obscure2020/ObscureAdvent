import java.io.*;
import java.util.*;

class Main {
    private static boolean anyAt(List<Robot> robots, int x, int y){
        for(Robot r : robots){
            if((r.getX() == x) && (r.getY() == y)){
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) throws Exception {
        ArrayList<Robot> robots = new ArrayList<>();
        Scanner scan = new Scanner(new File("input.txt"));
        while(scan.hasNext()){
            int[] line = Arrays.stream(scan.nextLine().strip().split("[^\\d-]+")).skip(1).mapToInt(Integer::parseInt).toArray();
            robots.add(new Robot(line[0], line[1], line[2], line[3], 101, 103));
        }
        scan.close();
        for(int i=1; i<=10000; i++){
            for(Robot r : robots) r.step();
            int consecutive = 0;
            boolean interesting = false;
            for(int y=0; y<103; y++){
                for(int x=0; x<101; x++){
                    boolean spot = anyAt(robots, x, y);
                    if(spot){
                        consecutive++;
                        if(consecutive == 10){
                            interesting = true;
                            break;
                        }
                    } else {
                        consecutive = 0;
                    }
                }
                if(interesting) break;
            }
            if(interesting){
                BitGrid frame = new BitGrid(101, 103);
                for(int y=0; y<103; y++){
                    for(int x=0; x<101; x++){
                        if(anyAt(robots, x, y)){
                            frame.setBit(x, y, true);
                        }
                    }
                }
                frame.debugFile(new File("Frame " + i + ".png"));
                System.out.println("Frame #" + i + " exported.");
            }
            if(i == 100){
                long quad1 = robots.stream().filter(Robot::quad1).count();
                long quad2 = robots.stream().filter(Robot::quad2).count();
                long quad3 = robots.stream().filter(Robot::quad3).count();
                long quad4 = robots.stream().filter(Robot::quad4).count();
                long part1 = quad1 * quad2 * quad3 * quad4;
                System.out.println("Part #1 - " + part1);
            }
        }
    }
}