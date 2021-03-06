public class IgnoreSubarraysQuicksort
{
    public static void main(String[] args) {
        final int M = 30;
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        for (int m = 0; m<M; m++) {
            Stopwatch s = new Stopwatch();
            for (int t = 0; t<T; t++) {
                Double[] a = new Double[N];
                for (int i = 0; i<N; i++) {
                    a[i] = StdRandom.uniform();
                }
                
                sort(a, 50);
                assert(isSorted(a));
            }
        StdOut.println(m + " " + s.elapsedTime());
        }
    }

    public static void sort(Double[] a, int m) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length-1, m);
        insertionSort(a);
    }

    private static void sort(Double[] a, int lo, int hi, int m) {
        if (hi <= lo) return;
        if (hi - lo <= m) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j-1, m);
        sort(a, j+1, hi, m);
    }
    
    private static void insertionSort(Double[] a) {
        for (int i = 1; i<a.length; i++) {
            for (int j = i; j>0 && a[j-1] > a[j]; j--) exch(a, j-1, j);
        }
    }

    private static boolean isSorted(Double[] a) {
        for (int i = 1; i<a.length; i++) {
            if (a[i-1] > a[i]) return false;
        }
        return true;
    }

    private static void exch(Double[] a, int i, int j) {
        Double t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static int partition(Double[] a, int lo, int hi) {
        int i = lo;
        int j = hi+1;
        Double v = a[lo];
        while (true) {
            while (v > a[++i]) if (i == hi) break;
            while (v < a[--j]) if (j == lo) break;
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }
}
