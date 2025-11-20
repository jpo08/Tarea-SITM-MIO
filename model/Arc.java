public class Arc {
    public int from;
    public int to;

    public Arc(int from, int to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return from + " -> " + to;
    }
}