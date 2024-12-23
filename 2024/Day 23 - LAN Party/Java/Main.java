import java.io.*;
import java.util.*;

class Main {
    private static Map<String, List<String>> readFile(File input) throws FileNotFoundException {
        HashMap<String, ArrayList<String>> conn = new HashMap<>();
        Scanner scan = new Scanner(input);
        while(scan.hasNext()){
            String line = scan.nextLine().strip();
            int dash = line.indexOf("-");
            assert dash > 0; //line must contain a dash, cannot start with a dash
            assert dash < (line.length() - 1); //lust cannot end with a dash
            String left = line.substring(0, dash);
            String right = line.substring(dash + 1);
            //Left store
            ArrayList<String> list = conn.getOrDefault(left, null);
            if(list == null){
                list = new ArrayList<>();
                list.add(right);
                conn.put(left, list);
            } else {
                list.add(right);
            }
            //Right store
            list = conn.getOrDefault(right, null);
            if(list == null){
                list = new ArrayList<>();
                list.add(left);
                conn.put(right, list);
            } else {
                list.add(left);
            }
        }
        scan.close();
        HashMap<String, List<String>> locked_conn = new HashMap<>();
        for(Map.Entry<String, ArrayList<String>> e : conn.entrySet()){
            String key = e.getKey();
            e.getValue().trimToSize();
            List<String> val = Collections.unmodifiableList(e.getValue());
            locked_conn.put(key, val);
        }
        return Collections.unmodifiableMap(locked_conn);
    }
    private static List<List<String>> findTriplets(Map<String, List<String>> conn){
        HashSet<List<String>> found = new HashSet<>();
        ArrayList<String> keys = new ArrayList<>(conn.keySet());
        for(int x=0; x<keys.size()-2; x++){
            for(int y=x+1; y<keys.size()-1; y++){
                for(int z=y+1; z<keys.size(); z++){
                    String kx = keys.get(x);
                    String ky = keys.get(y);
                    String kz = keys.get(z);
                    if(!conn.get(kx).contains(ky)) continue;
                    if(!conn.get(kx).contains(kz)) continue;
                    if(!conn.get(ky).contains(kz)) continue;
                    ArrayList<String> triplet = new ArrayList<>();
                    triplet.add(kx);
                    triplet.add(ky);
                    triplet.add(kz);
                    Collections.sort(triplet);
                    triplet.trimToSize();
                    found.add(Collections.unmodifiableList(triplet));
                }
            }
        }
        ArrayList<List<String>> results = new ArrayList<>(found);
        Collections.sort(results, Comparator.comparing(List::toString));
        results.trimToSize();
        return Collections.unmodifiableList(results);
    }
    private static boolean anyItemStartsWithT(List<String> input){
        for(String s : input){
            if(s.startsWith("t")) return true;
        }
        return false;
    }
    private static List<List<String>> bumpGroups(List<List<String>> prevGroups, Map<String, List<String>> conn){
        HashSet<List<String>> groups = new HashSet<>();
        for(List<String> oldGroup : prevGroups){
            for(Map.Entry<String, List<String>> entry : conn.entrySet()){
                String id = entry.getKey();
                List<String> folks = entry.getValue();
                if(oldGroup.contains(id)) continue;
                boolean notNeighbor = false;
                for(String guy : oldGroup){
                    if(!folks.contains(guy)){
                        notNeighbor = true;
                        break;
                    }
                }
                if(notNeighbor) continue;
                ArrayList<String> newGroup = new ArrayList<>(oldGroup);
                newGroup.add(id);
                Collections.sort(newGroup);
                newGroup.trimToSize();
                groups.add(Collections.unmodifiableList(newGroup));
            }
        }
        ArrayList<List<String>> results = new ArrayList<>(groups);
        Collections.sort(results, Comparator.comparing(List::toString));
        results.trimToSize();
        return Collections.unmodifiableList(results);
    }
    private static List<String> largestGroup(List<List<String>> triplets, Map<String, List<String>> conn){
        List<List<String>> prev = triplets;
        assert !prev.isEmpty();
        while(true){
            List<List<String>> cur = bumpGroups(prev, conn);
            if(cur.isEmpty()){
                return prev.getFirst();
            } else {
                prev = cur;
            }
        }
    }
    private static String formatPassword(List<String> group){
        StringBuilder sb = new StringBuilder(group.getFirst());
        for(int i=1; i<group.size(); i++){
            sb.append(',');
            sb.append(group.get(i));
        }
        return sb.toString();
    }
    public static void main(String[] args) throws Exception {
        final Map<String, List<String>> conn = readFile(new File("input.txt"));
        List<List<String>> triplets = findTriplets(conn);
        System.out.println("Part #1 - " + triplets.stream().filter(Main::anyItemStartsWithT).count());
        System.out.println("Part #2 - " + formatPassword(largestGroup(triplets, conn)));
    }
}