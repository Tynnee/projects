public class MyEdge implements Comparable<MyEdge>
{
    private final int v;
    private final int w;
    private final double weight;

    public MyEdge(int v, int w, double weight) {
        this.v = v; this.w = w; this.weight = weight;
    }

    public double weight() {
        return weight;
    }

    public int either() {
        return v;
    }

    public int other(int vertex) {
        if (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new RuntimeException("Incosistent edge");
    }

    public int compareTo(MyEdge that) {
        if (weight > that.weight) return 1;
        else if (weight < that.weight) return -1;
        else return 0;
    }

    public String toString() {
        return String.format("%d-%d %0.5f", v, w, weight);
    }
}
