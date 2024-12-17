import java.io.*;
import java.util.*;

class Cleanup {
    private static List<File> getYears(File root){
        assert root.exists();
        assert root.isDirectory();
        return Arrays.stream(root.listFiles())
            .filter(File::isDirectory)
            .filter(f -> !f.getName().startsWith("."))
            .filter(f -> !f.getName().startsWith("Incomplete"))
            .toList();
    }
    private static String[] getFilters(File list) throws FileNotFoundException {
        assert list.exists();
        assert list.isFile();
        ArrayList<String> results = new ArrayList<>();
        Scanner scan = new Scanner(list);
        while(scan.hasNext()){
            String line = scan.nextLine().strip();
            if(line.startsWith("*.")) results.add(line.substring(1));
        }
        scan.close();
        return results.toArray(new String[0]);
    }
    public static void main(String[] args) throws Exception {
        String[] filters = getFilters(new File(".gitignore"));
        ArrayDeque<File> files = new ArrayDeque<>(getYears(new File(".")));
        while(!files.isEmpty()){
            File f = files.removeFirst();
            if(f.isDirectory()){
                File[] children = f.listFiles();
                for(File c : children) files.addLast(c);
            } else if(f.isFile()){
                for(String filter : filters){
                    if(f.getName().endsWith(filter)){
                        String path = f.getCanonicalPath();
                        f.delete();
                        System.out.println("Deleted file - " + path);
                    }
                }
            }
        }
    }
}