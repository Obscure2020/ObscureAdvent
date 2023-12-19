public class Pair {
    final public String left;
    final public String right;

    public Pair(String l, String r){
        left = l;
        right = r;
    }

    public String toString(){ // Useful for debugging and testing, not for the actual solution.
        StringBuilder sb = new StringBuilder("[");
        sb.append(left);
        sb.append(", ");
        sb.append(right);
        sb.append(']');
        return sb.toString();
    }
}