import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class BruteCollinearPoints {
    private LineSegment[] segments;
    private int count;


    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Argument is null.");
        }

        if (checkDuplicates(points)) {
            throw new IllegalArgumentException("Duplicate points in the array.");
        }

        for (int i = 0; i < points.length; i += 1) {
            if (points[i] == null) {
                throw new IllegalArgumentException("Null point in the array.");
            }
        }

        Point[] pointsCopy = new Point[points.length];
        System.arraycopy(points, 0, pointsCopy, 0, points.length);
        Arrays.sort(pointsCopy);
        List<LineSegment> segmentList = new ArrayList<>();


        for (int i = 0; i < pointsCopy.length; i += 1) {

            for (int j = i + 1; j < pointsCopy.length; j += 1) {
                double slopeIJ = pointsCopy[i].slopeTo(pointsCopy[j]);

                for (int m = j + 1; m < pointsCopy.length; m += 1) {
                    double slopeIM = pointsCopy[i].slopeTo(pointsCopy[m]);
                    if (slopeIJ != slopeIM) {
                        continue;
                    }

                    for (int n = m + 1; n < pointsCopy.length; n += 1) {
                        double slopeIN = pointsCopy[i].slopeTo(pointsCopy[n]);
                        if (slopeIJ == slopeIN) {
                                segmentList.add(new LineSegment(pointsCopy[i],pointsCopy[n]));
                                count += 1;
                        }
                    }
                }
            }
        }

        segments = new LineSegment[segmentList.size()];
        segments = segmentList.toArray(segments);
    }


    private Point getMaxPoint(Point a, Point b, Point c, Point d) {
        Point max = a;
        if(max.compareTo(b) < 0){
            max = b;
        }
        if(max.compareTo(c) < 0){
            max = c;
        }
        if(max.compareTo(d) < 0){
            max = d;
        }
        return max;
    }

    private Point getMinPoint(Point a, Point b, Point c, Point d) {
        Point min = a;
        if(min.compareTo(b) > 0){
            min = b;
        }
        if(min.compareTo(c) > 0){
            min = c;
        }
        if(min.compareTo(d) > 0){
            min = d;
        }
        return min;
    }

    private boolean checkDuplicates(Point[] points) {
        for (int i = 0; i < points.length - 1; i += 1) {
            if (points[i].slopeTo(points[i + 1]) == Double.NEGATIVE_INFINITY) {
                return true;
            }
        }
        return false;
    }

    // the number of line segments
    public int numberOfSegments() {
        return count;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }

}


