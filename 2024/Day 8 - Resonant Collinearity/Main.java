import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws Exception {
        // Data
        int mapWidth = 0;
        int mapHeight = 0;
        HashMap<Character,ArrayList<IntPoint>> stations = new HashMap<>();

        // Load
        Scanner scan = new Scanner(new File("input.txt"));
        while(scan.hasNext()){
            String line = scan.nextLine().strip();
            mapWidth = Math.max(mapWidth, line.length());
            mapHeight++;
            for(int i=0; i<line.length(); i++){
                char c = line.charAt(i);
                if(c == '.') continue;
                ArrayList<IntPoint> antennas = stations.getOrDefault(c, null);
                if(antennas == null){
                    antennas = new ArrayList<>();
                    antennas.add(new IntPoint(i, mapHeight-1));
                    stations.put(c, antennas);
                } else {
                    antennas.add(new IntPoint(i, mapHeight-1));
                }
            }
        }
        scan.close();

        //Part 1 process
        BitGrid antinodes = new BitGrid(mapWidth, mapHeight);
        for(Map.Entry<Character,ArrayList<IntPoint>> station : stations.entrySet()){
            ArrayList<IntPoint> antennas = station.getValue();
            for(int i=0; i<antennas.size()-1; i++){
                IntPoint first = antennas.get(i);
                for(int j=i+1; j<antennas.size(); j++){
                    IntPoint second = antennas.get(j);
                    IntPoint diff = second.minus(first);
                    IntPoint anti_one = first.minus(diff);
                    IntPoint anti_two = second.plus(diff);
                    if(anti_one.fitsBounds(0, mapWidth-1, 0, mapHeight-1)){
                        antinodes.setBit(anti_one.x, anti_one.y, true);
                    }
                    if(anti_two.fitsBounds(0, mapWidth-1, 0, mapHeight-1)){
                        antinodes.setBit(anti_two.x, anti_two.y, true);
                    }
                }
            }
        }
        System.out.println("Part #1 - " + antinodes.bitCount());

        //Part 2 process
        antinodes = new BitGrid(mapWidth, mapHeight);
        int maxMult = Math.max(mapWidth, mapHeight) + 1;
        for(Map.Entry<Character,ArrayList<IntPoint>> station : stations.entrySet()){
            ArrayList<IntPoint> antennas = station.getValue();
            for(int i=0; i<antennas.size()-1; i++){
                IntPoint first = antennas.get(i);
                for(int j=i+1; j<antennas.size(); j++){
                    IntPoint second = antennas.get(j);
                    IntPoint diff = second.minus(first);
                    for(int mult=1; mult<=maxMult; mult++){
                        IntPoint scaled_diff = diff.times(mult);
                        IntPoint anti_one = first.plus(scaled_diff);
                        IntPoint anti_two = second.minus(scaled_diff);
                        if(anti_one.fitsBounds(0, mapWidth-1, 0, mapHeight-1)){
                            antinodes.setBit(anti_one.x, anti_one.y, true);
                        }
                        if(anti_two.fitsBounds(0, mapWidth-1, 0, mapHeight-1)){
                            antinodes.setBit(anti_two.x, anti_two.y, true);
                        }
                    }
                }
            }
        }
        System.out.println("Part #2 - " + antinodes.bitCount());
    }
}