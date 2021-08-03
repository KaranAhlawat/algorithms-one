import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {

    private int numberOfSegments;
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] pointsOriginal) {
        if (pointsOriginal == null)
            throw new IllegalArgumentException("argument to BruteCollinearPoints is null.");

        validateInput(pointsOriginal);

        numberOfSegments = 0;
        lineSegments = new LineSegment[pointsOriginal.length];

        Point[] points = new Point[pointsOriginal.length];
        for (int i = 0; i < pointsOriginal.length; i++)
            points[i] = pointsOriginal[i];

        Arrays.sort(points);

        for (int i = 0; i < points.length - 3; i++) {
            for  (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    Point p = points[i], q = points[j], r = points[k];
                    if (collinear(p, q, r)) {
                        for (int t = k + 1; t < points.length; t++) {
                            Point s = points[t];
                            if (collinear(q, r, s)) {
                                Point[] newSeg = {p, q, r, s};
                                addSegment(newSeg);
                            }
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(lineSegments, numberOfSegments());
    }

    private void validateInput(Point[] points) {
        for (Point point : points) {
            if (point == null)
                throw new IllegalArgumentException("Point cannot be null.");
        }

        for (int i = 0; i < points.length; ++i) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("Duplicate points found.");
            }
        }
    }

    private void addSegment(Point[] collinearPoints) {
        Point startingPoint = collinearPoints[0];
        Point endingPoint = collinearPoints[collinearPoints.length - 1];

        lineSegments[numberOfSegments++] = new LineSegment(startingPoint, endingPoint);
    }

    private boolean collinear(Point p1, Point p2, Point p3) {
        return Double.compare(p1.slopeTo(p2), p1.slopeTo(p3)) == 0;
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
