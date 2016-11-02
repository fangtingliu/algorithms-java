import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class BruteCollinearPoints {
    private int counts = 0;
    private ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        int n = points.length;

        Point[] sorted = new Point[n];
        for (int i = 0; i < n; i++) {
            sorted[i] = points[i];
        }
        Arrays.sort(sorted);

        for (int i = 0; i < n; i++) {
            Point p = sorted[i];
            if (p == null) throw new NullPointerException();
            if (i > 0 && p.compareTo(sorted[i - 1]) == 0) throw new IllegalArgumentException();
        }

        boolean found = false;
        for (int i1 = 0; i1 < n; i1++) {
            Point p1 = sorted[i1];

            for (int i2 = i1 + 1; i2 < n; i2++) {
                found = false;

                Point p2 = sorted[i2];
                for (int i3 = i2 + 1; i3 < n; i3++) {
                    if (found) break;

                    Point p3 = sorted[i3];
                    for (int i4 = i3 + 1; i4 < n; i4++) {
                        Point p4 = sorted[i4];
                        if (p1 == null || p2 == null || p3 == null || p4 == null) throw new NullPointerException();
                        if (p1 == p2 || p1 == p3 || p1 == p4 || p2 == p3 || p2 == p4 || p3 == p4) {
                            throw new IllegalArgumentException();
                        }

                        double s1 = p1.slopeTo(p2);
                        double s2 = p1.slopeTo(p3);
                        double s3 = p1.slopeTo(p4);
                        if (Double.compare(s1, s2) == 0 && Double.compare(s1, s3) == 0) {
                            found = true;
                            counts++;
                            LineSegment lineSegment =  new LineSegment(p1, p4);
                            lineSegments.add(lineSegment);
                            break;
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return counts;
    }

    public LineSegment[] segments() {
        LineSegment[] lines = new LineSegment[counts];
        for (int i = 0; i < counts; i++) {
            lines[i] = lineSegments.get(i);
        }
        return lines;
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