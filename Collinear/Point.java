import java.util.Comparator;

public class Point implements Comparable<Point> {
    
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();
    
    private final int x;
    private final int y;
    
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }    
    
    public void draw() {        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0); 
        StdDraw.setPenRadius(0.001);
        StdDraw.setPenColor(StdDraw.BLACK);
        
        StdDraw.point(this.x, this.y);
    }
 
    public void drawTo(Point that) {        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0); 
        StdDraw.setPenRadius(0.001);
        StdDraw.setPenColor(StdDraw.BLUE);
        
        StdDraw.line(this.x, this.y, that.x, that.y);  
    }
    
    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return 1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return 1;
        return 0;
    }
    
    public double slopeTo(Point that) {
        if (that == null) throw new NullPointerException();
        
        int deltaX = that.x - this.x;
        int deltaY = that.y - this.y;
        if (deltaX == 0 && deltaY == 0) return (double) Double.NEGATIVE_INFINITY;
        if (deltaX == 0) return (double) Double.POSITIVE_INFINITY;
        if (deltaY == 0) return (double) +0;
        return (double) deltaY / deltaX;
    }
    
    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point a, Point b) {
            return ((Double) slopeTo(a)).compareTo((Double) slopeTo(b));
        }
    }
    
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
    
    public static void main(String[] args) {
        Point p = new Point(310, 203);
        Point q = new Point(310, 178);
        System.out.println(p.slopeTo(q));
    }
}