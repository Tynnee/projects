import java.util.NoSuchElementException;
public class MyTrie<Value>
{
    private static final int R = 256;
    private Node root = new Node();
    
    private static class Node
    {
        private Object val;
        private Node[] next = new Node[R];
        private int N;
    }

    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c], key, d+1);
    }

    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        if (x == null) x = new Node();
        if (key.length() == d) {
            if (x.val == null) x.N++;
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, val, d+1);
        int sz = 0;
        for (char m = 0; m<R; m++) sz += size(x.next[m]);
        x.N = sz;
        if (x.val != null) x.N++;
        return x;
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x ==  null) return 0;
        return x.N;
    }

    public void delete(String key) {
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) {
            x.val = null;
        } else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d+1);
        }
        if (x.val != null) return x;
        for (char c = 0; c<R; c++) {
            if (x.next[c] != null) return x;
        }
        return null;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public String longestPrefixOf(String s) {
        int length = search(root, s, 0, 0);
        return s.substring(0, length);
    }

    private int search(Node x, String s, int d, int length) {
        if (x == null) return length;
        if (x.val != null) length = d;
        if (d == s.length()) return length;
        char c = s.charAt(d);
        return search(x.next[c], s, d+1, length);
    }

    public Iterable<String> keysWithPrefix(String pre) {
        Queue<String> q = new Queue<String>();
        collect(get(root, pre, 0), pre, q);
        return q;
    }

    private void collect(Node x, String pre, Queue<String> q) {
        if (x == null) return;
        if (x.val != null) q.enqueue(pre);
        for (char c = 0; c<R; c++) {
            collect(x.next[c], pre+c, q);
        }
    }

    private void collect(Node x, String pre, String pat, Queue<String> q) {
        int d = pre.length();
        if (x == null) return;
        if (d == pat.length() && x.val != null) q.enqueue(pre);
        if (d == pat.length()) return;
        char next = pat.charAt(d);
        for (char c = 0; c<R; c++) {
            if (next == c || next == '.') collect(x.next[c], pre+c, pat, q);
        }
    }

    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    public Iterable<String> keysThatMatch(String pat) {
        Queue<String> q = new Queue<String>();
        collect(root, "", pat, q);
        return q;
    }

    public String floor(String key) {
        int M = key.length();
        StringBuilder outer = new StringBuilder();
        StringBuilder inner = new StringBuilder();
        Node x = root;
        for (int i = 0; i<M; i++) {
            for (char c = key.charAt(i); c >= 0; c--) {
                if (x.next[c] != null) {
                    inner.append(c);
                    x = x.next[c];
                    if (x.val != null) {
                        outer.append(inner);
                        inner = new StringBuilder();
                    }
                    break;
                }
                if (c == 0) return new String(outer);
            }
        }
        return new String(outer);
    }
                

    public String min() {
        if (isEmpty()) throw new NoSuchElementException();
        Node x = root;
        StringBuilder sb = new StringBuilder();
        while (true) {
            for (char c = 0; c<R; c++) {
                if (x.next[c] != null) {
                    sb.append(String.valueOf(c));
                    x = x.next[c];
                    break;
                }
                if (c == R-1) return new String(sb);
            }
        }
    }

    public String max() {
        if (isEmpty()) throw new NoSuchElementException();
        Node x = root;
        StringBuilder sb = new StringBuilder();
        while (true) {
            for (char c = R-1; c>=0; c--) {
                if (x.next[c] != null) {
                    sb.append(String.valueOf(c));
                    x = x.next[c];
                    break;
                }
                if (c == 0) return new String(sb);
            }
        }
    }
        

    public static void main(String[] args) {
        MyTrie<Integer> t = new MyTrie<Integer>();
        String[] a = (new In(args[0])).readAll().split("\\s+");
        int i = 0;
        for (String s : a) {
            t.put(s, i++);
            StdOut.println(t.size());
        }
    }
}
