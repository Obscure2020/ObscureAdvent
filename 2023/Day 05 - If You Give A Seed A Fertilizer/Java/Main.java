import java.util.*;
import java.util.stream.LongStream;
import java.io.*;
class Main{
  private static boolean startsWithNumber(String input){
    char first = input.charAt(0);
    return '0' <= first && first <= '9';
  }
  private static long mapRepeatedly(long num, Range[]...ranges){
    long result = num;
    for(Range[] stage : ranges){
      for(Range r : stage){
        if(r.fitsMap(result)){
          result = r.map(result);
          break;
        }
      }
    }
    return result;
  }
  public static void main(String[] args) throws Exception{
    final long[] seeds;
    final Range[] seed_to_soil;
    final Range[] soil_to_fertilizer;
    final Range[] fertilizer_to_water;
    final Range[] water_to_light;
    final Range[] light_to_temperature;
    final Range[] temperature_to_humidity;
    final Range[] humidity_to_location;
    {
      HashMap<String, ArrayList<Range>> results = new HashMap<>();
      ArrayList<Range> current = null;
      long[] seedsTemp = new long[0];
      Scanner scan = new Scanner(new File("input.txt"));
      while(scan.hasNext()){
        String line = scan.nextLine().replace(':', ' ').trim();
        if(line.isEmpty()) continue;
        if(startsWithNumber(line)){
          long[] nums = Arrays.stream(line.split(" +")).mapToLong(Long::parseLong).toArray();
          Range r = new Range(nums[0], nums[1], nums[2]);
          current.add(r);
        } else {
          String title = line.split(" +")[0];
          if(title.equals("seeds")){
            seedsTemp = Arrays.stream(line.split(" +")).skip(1).mapToLong(Long::parseLong).toArray();
          } else {
            current = new ArrayList<>();
            results.put(title, current);
          }
        }
      }
      scan.close();
      seeds = seedsTemp;
      seed_to_soil = results.get("seed-to-soil").toArray(new Range[0]);
      soil_to_fertilizer = results.get("soil-to-fertilizer").toArray(new Range[0]);
      fertilizer_to_water = results.get("fertilizer-to-water").toArray(new Range[0]);
      water_to_light = results.get("water-to-light").toArray(new Range[0]);
      light_to_temperature = results.get("light-to-temperature").toArray(new Range[0]);
      temperature_to_humidity = results.get("temperature-to-humidity").toArray(new Range[0]);
      humidity_to_location = results.get("humidity-to-location").toArray(new Range[0]);
    } // Get rid of objects used for reading input
    long partOne = Arrays.stream(seeds).parallel().map(l -> mapRepeatedly(l, seed_to_soil, soil_to_fertilizer, fertilizer_to_water, water_to_light, light_to_temperature, temperature_to_humidity, humidity_to_location)).min().getAsLong();
    System.out.println("Part #1 - " + partOne);
    LongStream partTwoProc = LongStream.of(); //Creates a LongStream of zero elements, to be appended to below.
    for(int i=0; i<seeds.length; i+=2){
      partTwoProc = LongStream.concat(partTwoProc, LongStream.range(seeds[i], seeds[i]+seeds[i+1]));
    }
    long partTwo = partTwoProc.parallel().map(l -> mapRepeatedly(l, seed_to_soil, soil_to_fertilizer, fertilizer_to_water, water_to_light, light_to_temperature, temperature_to_humidity, humidity_to_location)).min().getAsLong();
    System.out.println("Part #2 - " + partTwo);
  }
}