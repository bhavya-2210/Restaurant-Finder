import java.util.*;
import java.io.*;
public class kdtree {
    //
    class TreeNode {
        public int numl;
        public TreeNode parent;
        public TreeNode left;
        public TreeNode right;
        public int depth;
        public int xval;
        public int yval;
        public int xmin;
        public int xmax;
        public int ymin;
        public int ymax;
        public boolean isleft;

        public boolean inside(int x, int y) {
            if (xmin <= x && xmax >= x && ymin <= y && ymax >= y) {
                return true;
            } else {
                return false;
            }
        }
    }

    //
    public TreeNode rootnode;

    //
    static class Pair<A, B> {
        public A First;
        public B Second;

        public Pair() {
        }

        public Pair(A _first, B _second) {
            this.First = _first;
            this.Second = _second;
        }

        public A get_first() {
            return First;
        }

        public B get_second() {
            return Second;
        }
    }

    //
    public static void xsort(List<Pair<Integer, Integer>> arr) {
        int n = arr.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr.get(j).First > arr.get(j + 1).First) {
                    Pair<Integer, Integer> temp = arr.get(j);
                    arr.set(j, arr.get(j + 1));
                    arr.set(j + 1, temp);
                }
            }
        }
    }

    //
    public static void ysort(List<Pair<Integer, Integer>> arr) {
        int n = arr.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr.get(j).Second > arr.get(j + 1).Second) {
                    Pair<Integer, Integer> temp = arr.get(j);
                    arr.set(j, arr.get(j + 1));
                    arr.set(j + 1, temp);
                }
            }
        }
    }

    //
    public void Build(List<Pair<Integer, Integer>> l) {
        if (rootnode == null) {
            if (l.size() == 1) {
                TreeNode T = new TreeNode();
                T.numl = 1;
                T.parent = null;
                T.xval = l.get(0).First;
                T.yval = l.get(0).Second;
                T.depth = 0;
                T.xmax = 10000;
                T.xmin = -10000;
                T.ymax = 10000;
                T.ymin = -10000;
                T.left = null;
                T.right = null;
                rootnode = T;
            } else {
                xsort(l);
                int n = l.size();
                int i;
                Pair<Integer, Integer> rnode;
                if (n % 2 == 0) {
                    rnode = l.get(n / 2 - 1);
                    i = n / 2 - 1;
                } else {
                    rnode = l.get((n + 1) / 2 - 1);
                    i = (n + 1) / 2 - 1;
                }
                //
                TreeNode R1 = new TreeNode();
                R1.numl = n;
                R1.parent = null;
                R1.xval = rnode.First;
                R1.yval = rnode.Second;
                R1.depth = 0;
                R1.xmax = 10000;
                R1.xmin = -10000;
                R1.ymax = 10000;
                R1.ymin = -10000;
                rootnode = R1;
                //
                kdtree L = new kdtree();
                TreeNode L0 = new TreeNode();
                L0.parent = rootnode;
                L0.depth = 1;
                L0.isleft = true;
                L0.numl = i + 1;
                L.rootnode = L0;
                L.Build(l.subList(0, i + 1));
                //
                kdtree R = new kdtree();
                TreeNode R0 = new TreeNode();
                R0.parent = rootnode;
                R0.depth = 1;
                R0.isleft = false;
                R0.numl = n - i - 1;
                R.rootnode = R0;
                R.Build(l.subList(i + 1, n));
                //
                rootnode.left = L.rootnode;
                rootnode.right = R.rootnode;
            }
        } else {
            if (rootnode.depth % 2 != 0) {
                ysort(l);
                int n = l.size();
                int i;
                Pair<Integer, Integer> rnode;
                if (n % 2 == 0) {
                    rnode = l.get(n / 2 - 1);
                    i = n / 2 - 1;
                } else {
                    rnode = l.get((n + 1) / 2 - 1);
                    i = (n + 1) / 2 - 1;
                }
                //
                rootnode.xval = rnode.First;
                rootnode.yval = rnode.Second;
                rootnode.ymax = rootnode.parent.ymax;
                rootnode.ymin = rootnode.parent.ymin;
                if (rootnode.isleft == true) {
                    rootnode.xmax = rootnode.parent.xval;
                    rootnode.xmin = rootnode.parent.xmin;
                } else {
                    rootnode.xmax = rootnode.parent.xmax;
                    /////////////////////////////////////////////////////////////
                    rootnode.xmin = rootnode.parent.xval + 1;
                }
                if (l.size() == 1) {
                    rootnode.left = null;
                    rootnode.right = null;
                } else {
                    //
                    kdtree L = new kdtree();
                    TreeNode L1 = new TreeNode();
                    L1.parent = rootnode;
                    L1.depth = L1.parent.depth + 1;
                    L1.isleft = true;
                    L1.numl = i + 1;
                    L.rootnode = L1;
                    L.Build(l.subList(0, i + 1));
                    //
                    kdtree R = new kdtree();
                    TreeNode R1 = new TreeNode();
                    R1.parent = rootnode;
                    R1.depth = R1.parent.depth + 1;
                    R1.isleft = false;
                    R1.numl = n - i - 1;
                    R.rootnode = R1;
                    R.Build(l.subList(i + 1, n));
                    //
                    rootnode.left = L.rootnode;
                    rootnode.right = R.rootnode;
                }
                // System.out.println(rootnode.yval);
            } else {
                xsort(l);
                int n = l.size();
                int i;
                Pair<Integer, Integer> rnode;
                if (n % 2 == 0) {
                    rnode = l.get(n / 2 - 1);
                    i = n / 2 - 1;
                } else {
                    rnode = l.get((n + 1) / 2 - 1);
                    i = (n + 1) / 2 - 1;
                }
                //
                rootnode.xval = rnode.First;
                rootnode.yval = rnode.Second;
                rootnode.xmax = rootnode.parent.xmax;
                rootnode.xmin = rootnode.parent.xmin;
                if (rootnode.isleft == true) {
                    rootnode.ymax = rootnode.parent.yval;
                    rootnode.ymin = rootnode.parent.ymin;
                } else {
                    rootnode.ymax = rootnode.parent.ymax;
                    rootnode.ymin = rootnode.parent.yval + 1;
                }
                if (l.size() == 1) {
                    rootnode.left = null;
                    rootnode.right = null;
                } else {
                    //
                    kdtree L = new kdtree();
                    TreeNode L1 = new TreeNode();
                    L1.parent = rootnode;
                    L1.depth = L1.parent.depth + 1;
                    L1.isleft = true;
                    L1.numl = i + 1;
                    L.rootnode = L1;
                    L.Build(l.subList(0, i + 1));
                    //
                    kdtree R = new kdtree();
                    TreeNode R1 = new TreeNode();
                    R1.parent = rootnode;
                    R1.depth = R1.parent.depth + 1;
                    R1.isleft = false;
                    R1.numl = n - i - 1;
                    R.rootnode = R1;
                    R.Build(l.subList(i + 1, n));
                    //
                    rootnode.left = L.rootnode;
                    rootnode.right = R.rootnode;
                }
                /// System.out.println(rootnode.xval);
            }
        }
    }

    //
    public static boolean overlap(TreeNode T, Pair<Integer, Integer> Q) {
        if (T.xmax < Q.First - 100 || T.xmin > Q.First + 100 && T.ymax < Q.Second - 100 || T.ymin > Q.Second + 100) {
            return false;
        }
        return true;
    }
    public int nearme(Pair<Integer, Integer> Q, TreeNode N) {
        if (N.left == null && N.right == null) {
            if (N.xval >= Q.First - 100 && N.xval <= Q.First + 100 &&
                    N.yval >= Q.Second - 100 && N.yval <= Q.Second + 100) {
                return 1;
            } else {
                return 0;
            }
        } else if (overlap(N.left, Q) == false && overlap(N.right, Q) == false) {
            return 0;
        } else if (overlap(N.left, Q) == false && overlap(N.right, Q) == true) {
            return nearme(Q, N.right);
        } else if (overlap(N.left, Q) == true && overlap(N.right, Q) == false) {
            return nearme(Q, N.left);
        }
        return nearme(Q, N.left) + nearme(Q, N.right);
    }

    public static void main(String[] args) {
        // preparing the array containing restaurant data
        try {
            FileInputStream f = new FileInputStream("restaurants.txt");
            Scanner s = new Scanner(f);
            s.nextLine();
            List<Pair<Integer, Integer>> Arr = new ArrayList<Pair<Integer, Integer>>();
            while (s.hasNextLine()) {
                String a = s.nextLine();
                int i = a.indexOf(",");
                //int j = a.indexOf("\n");
                int f3 = Integer.parseInt(a.substring(0, i));
                int s1 = Integer.parseInt(a.substring(i + 1));
                Pair<Integer, Integer> P = new Pair<Integer, Integer>(f3, s1);
                Arr.add(P);
            }
            // prepared
            kdtree k = new kdtree();
            k.Build(Arr);
            // kd tree also made
            //k.display(k.rootnode);
            try {
                FileInputStream fs = new FileInputStream("queries.txt");
                Scanner S = new Scanner(fs);
                S.nextLine();
                try {
                    FileOutputStream f8 = new FileOutputStream("output.txt", false);
                    while (S.hasNextLine()) {
                        String a = S.nextLine();
                        int i = a.indexOf(",");
                        //int j = a.indexOf("\n");
                        int f2 = Integer.parseInt(a.substring(0, i));
                        int s2 = Integer.parseInt(a.substring(i + 1));
                        Pair<Integer, Integer> Z = new Pair<Integer, Integer>(f2, s2);
                        int n = k.nearme(Z, k.rootnode);
                        PrintStream p = new PrintStream(f8);
                        p.println(n);

                    }
                } catch (Exception e) {
                    System.out.println("Error");
                }
            } catch (Exception e) {
                System.out.println("Error");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}