import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;


public class FastCollinearPoints {
    private LineSegment[] segments;
    private int numOfSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Argument is null.");
        }

        for (int i = 0; i < points.length; i += 1) {
            if (points[i] == null) {
                throw new IllegalArgumentException("Null point in the array.");
            }
        }

        if (checkDuplicates(points)) {
            throw new IllegalArgumentException("Duplicate points in the array.");
        }

        Point[] pointsCopy = new Point[points.length - 1];
        Point[] refPointCopy = new Point[points.length];
        Point min;
        Point max;

        System.arraycopy(points, 0, refPointCopy, 0, points.length);

        Arrays.sort(refPointCopy);

        List<LineSegment> segmentList = new ArrayList<>();
        List<Point> maxList = new ArrayList<>();
        List<Point> minList = new ArrayList<>();


        for (int i = 0; i < pointsCopy.length; i += 1) {
            Point refPoint = refPointCopy[i];
            System.arraycopy(refPointCopy, 0, pointsCopy, 0, i);
            System.arraycopy(refPointCopy, i + 1, pointsCopy, i, pointsCopy.length - i);
            Arrays.sort(pointsCopy, refPoint.slopeOrder());
            int j = 0;
            while (j < pointsCopy.length - 1) {
                int count = 1;
                int element = 1;
                Point A = pointsCopy[j];
                double slopeRA = refPoint.slopeTo(A);
                Point B = pointsCopy[j + 1];
                while (slopeRA == refPoint.slopeTo(B)) {
                    element += 1;
                    count += 1;

                    if (j + count > pointsCopy.length - 1) {
                        break;
                    }
                    B = pointsCopy[j + count];

                }
                if (element >= 3) {
                    Arrays.sort(pointsCopy, j, j + element - 1);
                    if (refPoint.compareTo(pointsCopy[j]) <= 0) {
                        min = refPoint;
                        max = pointsCopy[j + element - 1];
                    }
                    else if(refPoint.compareTo(pointsCopy[j + element - 1]) >= 0) {
                        max = refPoint;
                        min = pointsCopy[j];
                    }
                    else {
                        max = pointsCopy[j + element - 1];
                        min = pointsCopy[j];
                    }
                    j = j + count - 1;
                    if(!hasSubsegment(maxList,minList, max, min)) {
                        maxList.add(max);
                        minList.add(min);
                        numOfSegments += 1;
                    }

                }
                else {
                    j = j + count;
                }

            }

        }
        for (int i = 0; i < maxList.size(); i += 1) {
            segmentList.add(new LineSegment(minList.get(i), maxList.get(i)));
        }
        segments = new LineSegment[segmentList.size()];
        segments = segmentList.toArray(segments);

    }

    private boolean hasSubsegment(List<Point> max, List<Point> min, Point a, Point b) {
        if(max.isEmpty() && min.isEmpty()) {
            return false;
        }
        for (int i = 0; i < max.size(); i += 1) {
            Point start = min.get(i);
            Point end = max.get(i);
            if (start.compareTo(b) == 0 && end.compareTo(a) == 0) {
                return true;
            }
        }

        return false;
    }


    private boolean checkDuplicates(Point[] points) {
        for (int i = 0; i < points.length - 1; i += 1) {
            for (int j = i + 1; j < points.length; j += 1) {
                if (points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY) {
                    return true;
                }
            }

        }
        return false;
    }

    // the number of line segments
    public int numberOfSegments() {
        return numOfSegments;
    }
    // the line segments
    public LineSegment[] segments() {
        return segments.clone();
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }

}
