/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private ArrayList<LineSegment> segments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
            if (points == null)
                throw new IllegalArgumentException("argument to BruteCollinearPoints is null.");

        validateInput(points);

        Point[] slopeSortedPoints = points.clone();

        for (int i = 0; i < slopeSortedPoints.length - 3; i++) {
            Merge.sort(slopeSortedPoints);

            Arrays.sort(slopeSortedPoints, slopeSortedPoints[i].slopeOrder());

            for (int start = 0, first = 1, last = 2; last < slopeSortedPoints.length; last++) {
                while (last < slopeSortedPoints.length && collinear(slopeSortedPoints[start], slopeSortedPoints[first], slopeSortedPoints[last]))
                    last++;

                if (last - first >= 3 && slopeSortedPoints[start].compareTo(slopeSortedPoints[first]) < 0) {
                    addSegment(slopeSortedPoints[start], slopeSortedPoints[last - 1]);
                }

                first = last;
            }


        }

    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[numberOfSegments()]);
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

    private void addSegment(Point startingPoint, Point endingPoint) {
        segments.add(new LineSegment(startingPoint, endingPoint));
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
