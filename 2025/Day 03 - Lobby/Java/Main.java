import java.nio.file.*;
import java.util.*;
import java.util.regex.Pattern;

class Main {
    private static long highestJoltage(final String bank_str, final int quota){
        assert quota > 0;
        final int len = bank_str.length();
        assert len >= quota;
        int[] bank = new int[len];
        for(int i=0; i<len; i++){
            bank[i] = ((int) bank_str.charAt(i)) - ((int) '0');
        }
        StringBuilder sb = new StringBuilder(quota);
        int prevIndex = -1;
        for(int digit=0; digit<quota; digit++){
            final int leftBound = prevIndex + 1;
            final int rightBound = (len - quota) + digit;
            int highestFound = 0;
            int index = -1;
            for(int i=leftBound; i<=rightBound; i++){
                final int cell = bank[i];
                if(cell > highestFound){
                    highestFound = cell;
                    index = i;
                }
            }
            sb.append(highestFound);
            prevIndex = index;
        }
        return Long.parseLong(sb.toString());
    }

    public static void main(String[] args) throws Exception {
        List<String> banks = Files.newBufferedReader(Path.of("input.txt")).lines().map(String::strip).filter(s -> !s.isBlank()).toList();
        Pattern p = Pattern.compile("^\\d{12,}$");
        for(String bank : banks) assert p.matcher(bank).matches();
        System.out.println("Part #1 - " + banks.stream().parallel().mapToLong(s -> highestJoltage(s, 2)).sum());
        System.out.println("Part #2 - " + banks.stream().parallel().mapToLong(s -> highestJoltage(s, 12)).sum());
    }
}