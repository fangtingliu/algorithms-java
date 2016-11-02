import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class FastCollinearPoints {
    private int counts = 0;
    private List<LineSegment> lineSegments = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        int n = points.length;

        Point[] copy = new Point[n];
        Point[] sorted = new Point[n];
        for (int i = 0; i < n; i++) {
            copy[i] = points[i];
            sorted[i] = points[i];
        }

        Arrays.sort(sorted);

        ArrayList<Point> temp = new ArrayList<Point>();
        for (int i = 0; i < n; i++) {
            Point p = sorted[i];
            if (p == null) throw new NullPointerException();
            if (i > 0 && p.compareTo(sorted[i - 1]) == 0) throw new IllegalArgumentException();

            Arrays.sort(copy, p.slopeOrder());

            double currSlop = p.slopeTo(copy[0]);
            int currC = 0;
            temp.add(p);
            for (int j = 1; j < n; j++) {
                Point currP = copy[j];
                if (Double.compare(currSlop, p.slopeTo(currP)) == 0) {
                    temp.add(currP);
                    currC++;
                } else {
                    if (currC > 2) {
                        Collections.sort(temp);
                        LineSegment lineSegment =  new LineSegment(temp.get(0), temp.get(currC));
                        if (temp.get(0).compareTo(p) >= 0) {
                            counts++;
                            lineSegments.add(lineSegment);
                        }
                    }
                    temp.clear();
                    currSlop = p.slopeTo(currP);
                    temp.add(p);
                    temp.add(currP);
                    currC = 1;
                }
            }
            if (temp.size() > 3) {
                Collections.sort(temp);
                LineSegment lineSegment =  new LineSegment(temp.get(0), temp.get(currC));
                if (temp.get(0).compareTo(p) >= 0) {
                    counts++;
                    lineSegments.add(lineSegment);
                }
                temp.clear();
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}