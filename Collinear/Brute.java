public class Brute {
    
    // only sort 4 items so insertion sort is used
    private static void sort(Point[] p) {
        for (int i = 0; i < p.length; i++) {
            for (int j = i; j > 0; j--) {
                if (less(p[j], p[j-1])) swap(p, j, j-1);
                else break;
            }
        }
    }
    
    private static void swap(Point[] p, int i, int j) {
        Point temp = p[i];
        p[i] = p[j];
        p[j] = temp;
    }
    
    private static boolean less(Point a, Point b) {
        return a.compareTo(b) < 0;
    }
    
    /**
     *  sort the segment by natural order
     *  Draw a line from first point to last point
     *  Print out the point from first to last
     */
    private static void drawSegment(Point[] p) {
        sort(p);
        int segLength = p.length;
        p[0].drawTo(p[segLength - 1]);
        StdOut.print(p[0]);
        for (int i = 1; i < segLength; i++) {
            StdOut.print(" -> " + p[i]);
        }
        StdOut.println();
    }
    
    public static void main(String[] args) {
        String fileName = args[0];
        In in = new In(fileName);
        int N = in.readInt();
        Point[] points = new Point[N];
        
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }
        
        for (int j = 0; j < N - 3; j++) {
            
            for (int k = j+1; k < N - 2; k++) {
                Double slope = points[j].slopeTo(points[k]);
                    
                for (int l = k+1; l < N - 1; l++) {
                    Double slope2 = points[j].slopeTo(points[l]);
                    
                    // skip scanning for m if j > k > l are not collinear
                    if (slope.compareTo(slope2) != 0) continue; 
                    for (int m = l+1; m < N; m++) {
                        Double slope3 = points[j].slopeTo(points[m]);
                        
                        if (slope.compareTo(slope3) == 0) {
                            Point[] seg = {points[j], points[k], points[l], points[m]};
                            drawSegment(seg);
                        }
                    } 
                }
            }
        }
        StdDraw.show(0);
    }
}