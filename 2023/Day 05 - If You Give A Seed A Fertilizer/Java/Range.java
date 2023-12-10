public class Range {
    private final long destStart;
    private final long sourceStart;
    private final long sourceEnd;

    public Range(long destRangeStart, long sourceRangeStart, long rangeLength){
        destStart = destRangeStart;
        sourceStart = sourceRangeStart;
        sourceEnd = sourceStart + (rangeLength - 1);
    }

    public boolean fitsMap(long input){
        return sourceStart <= input && input <= sourceEnd;
    }

    public long map(long input){
        return destStart + (input - sourceStart);
    }

    public String toString(){ //I added this for debugging purposes. Not useful for actual solution.
        StringBuilder sb = new StringBuilder("[");
        sb.append(sourceStart);
        sb.append('-');
        sb.append(sourceEnd);
        sb.append(" to ");
        sb.append(destStart);
        sb.append('-');
        sb.append(map(sourceEnd));
        sb.append(']');
        return sb.toString();
    }
}