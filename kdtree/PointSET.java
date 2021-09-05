/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {

    private final TreeSet<Point2D> points;

    public PointSET() {
        points = new TreeSet<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException(
                    "Argument cannot be null to insert");
        }

        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException(
                    "Argument cannot be null to contains");
        }

        return points.contains(p);
    }

    public void draw() {
        points.forEach(Point2D::draw);
    }

    public Iterable<Point2D> range(RectHV rect) {

        if (rect == null) {
            throw new IllegalArgumentException(
                    "Rectange cannot be null in range.");
        }

        List<Point2D> containedPoints = new ArrayList<Point2D>();

        for (Point2D point : points) {
            if (rect.contains(point))
                containedPoints.add(point);
        }

        return containedPoints;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("The point passed into nearest cannot be null.");
        }

        if (points.isEmpty()) {
            return null;
        }

        double minDistance = Double.POSITIVE_INFINITY;
        Point2D nearestPoint = null;

        for (Point2D point : points) {
            if (p.distanceSquaredTo(point) < minDistance) {
                minDistance = p.distanceSquaredTo(point);
                nearestPoint = point;
            }
        }

        return nearestPoint;
    }

    public static void main(String[] args) {
        PointSET bruteSet = new PointSET();

        while (!StdIn.isEmpty()) {
            double pointx = StdIn.readDouble();
            double pointy = StdIn.readDouble();
            Point2D newPoint = new Point2D(pointx, pointy);

            bruteSet.insert(newPoint);
        }

        StdOut.println(bruteSet.isEmpty());
        StdOut.println(bruteSet.contains(new Point2D(0.5, 0.5)));
        StdOut.println(bruteSet.size());
        StdOut.println(bruteSet.nearest(new Point2D(0.5, 0.5)));
        StdOut.println(bruteSet.range(new RectHV(0.2, 0, 0.7, 0.63)));
    }
}
