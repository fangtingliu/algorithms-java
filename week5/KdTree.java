import java.util.TreeSet;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
    private Node root;
    private int size;

    private static class Node implements Comparable<Node> {
        private Point2D point;
        private RectHV rect = null;
        private Node left = null;
        private Node right = null;
        private boolean x = true;

        public int compareTo(Node n) {
            if (x) {
                return Double.compare(point.x(), n.point.x());
            } else {
                return Double.compare(point.y(), n.point.y());
            }
        }
    }

    public KdTree() {
        root = new Node();
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        double x = p.x();
        if (size == 0) {
            root.point = p;
            root.rect = new RectHV(x, 0, x, 1);
            size++;
        } else {
            Node node = new Node();
            node.point = p;
            insert(root, node);
        }
    }

    private void insert(Node n, Node node) {
        double diff = n.compareTo(node);
        if (diff <= 0) {
            if (diff == 0 && n.point.equals(node.point)) return;
            if (n.right == null) {
                if (n.x) {
                    node.x = false;
                    node.rect = new RectHV(n.point.x(), node.point.y(), 1, node.point.y());
                } else {
                    node.rect = new RectHV(node.point.x(), n.point.y(), node.point.x(), 1);
                }
                n.right = node;
                size++;
            } else {
                insert(n.right, node);
            }
        } else if (diff > 0) {
            if (n.left == null) {
                if (n.x) {
                    node.x = false;
                    node.rect = new RectHV(node.point.x(), node.point.y(), n.point.x(), node.point.y());
                } else {
                    node.rect = new RectHV(node.point.x(), 0, node.point.x(),  n.point.y());
                }
                n.left = node;
                size++;
            } else {
                insert(n.left, node);
            }
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        Node node = new Node();
        node.point = p;
        return contains(root, node);
    }

    private boolean contains(Node n, Node node) {
        if (n.compareTo(node) <= 0) {
            if (node.right != null) {
                return contains(node.right, node);
            } else {
                return false;
            }
        } else if (n.compareTo(node) > 0) {
            if (node.left != null) {
                return contains(node.left, node);
            } else {
                return false;
            }
        } else {
            return n.point.equals(node.point);
        }
    }

    public void draw() {
        StdDraw.setXscale(0.0, 1.0);
        StdDraw.setYscale(0.0, 1.0);
        draw(root);
    }

    private void draw(Node n) {
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        n.point.draw();

        StdDraw.setPenRadius();
        if (n.x) {
            StdDraw.setPenColor(StdDraw.RED);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
        }
        n.rect.draw();

        if (n.left != null) {
            draw(n.left);
        }

        if (n.right != null) {
            draw(n.right);
        }

    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        TreeSet<Point2D> rangeSet = new TreeSet<Point2D>();
        range(root, rect, rangeSet);
//        System.out.println("rangeSet: " + rangeSet);
        return rangeSet;
    }

    private void range(Node n, RectHV rect, TreeSet<Point2D> rangeSet) {
        if (n == null) return;
//        System.out.println("rangeSet private intersect: " + rect.intersects(n.rect) + n.point + rect.contains(n.point));
        if (rect.intersects(n.rect)) {
            if (rect.contains(n.point)) {
                rangeSet.add(n.point);
            }
            range(n.left, rect, rangeSet);
            range(n.right, rect, rangeSet);
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        double dis = Double.POSITIVE_INFINITY;
        Point2D closest = null;
        nearest(root, p, dis, closest);
        return closest;
    }

    private double nearest(Node n, Point2D p, double dis, Point2D closest) {
        if (n == null) return Double.POSITIVE_INFINITY;
        double currDis = p.distanceTo(n.point);
        if (currDis < dis) {
            dis = currDis;
            closest = n.point;
        }

        double nearestChildDis = Double.POSITIVE_INFINITY;
        if ((n.x && p.x() < n.point.x() && n.left != null) || (!n.x && p.y() < n.point.y() && n.left != null)) {
            nearestChildDis = nearest(n.left, p, dis, closest);
            if (dis > nearestChildDis) {
                dis = nearestChildDis;
            }

            if (n.right != null && dis > n.rect.distanceTo(p)) {
                nearestChildDis = nearest(n.right, p, dis, closest);
            }
        } else if ((n.x && p.x() > n.point.x() && n.right != null) || (!n.x && p.y() > n.point.y() && n.right != null)) {
            nearestChildDis = nearest(n.right, p, dis, closest);
            if (dis > nearestChildDis) {
                dis = nearestChildDis;
            }

            if (n.left != null && dis > n.rect.distanceTo(p)) {
                nearestChildDis = nearest(n.right, p, dis, closest);
            }
        }

        if (dis > nearestChildDis) {
            dis = nearestChildDis;
        }

        return dis;
    }

    public static void main(String[] args) {

        String filename = args[0];
        In in = new In(filename);

        StdDraw.enableDoubleBuffering();

        // initialize the data structures with N points from standard input
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }

        double x0 = 0.0, y0 = 0.0;      // initial endpoint of rectangle
        double x1 = 0.0, y1 = 0.0;      // current location of mouse
        boolean isDragging = false;     // is the user dragging a rectangle

        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
//        brute.draw();
        kdtree.draw();
        StdDraw.show();

//        while (true || false) {
//
//            // user starts to drag a rectangle
//            if (StdDraw.mousePressed() && !isDragging) {
//                x0 = StdDraw.mouseX();
//                y0 = StdDraw.mouseY();
//                isDragging = true;
//                continue;
//            }
//
//            // user is dragging a rectangle
//            else if (StdDraw.mousePressed() && isDragging) {
//                x1 = StdDraw.mouseX();
//                y1 = StdDraw.mouseY();
//                continue;
//            }
//
//            // mouse no longer pressed
//            else if (!StdDraw.mousePressed() && isDragging) {
//                isDragging = false;
//            }
//
//
//            RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1),
//                    Math.max(x0, x1), Math.max(y0, y1));
//            // draw the points
//            StdDraw.clear();
//            StdDraw.setPenColor(StdDraw.BLACK);
//            StdDraw.setPenRadius(0.01);
////            brute.draw();
//
//            // draw the rectangle
//            StdDraw.setPenColor(StdDraw.BLACK);
//            StdDraw.setPenRadius();
//            rect.draw();
//
//            // draw the range search results for brute-force data structure in red
////            StdDraw.setPenRadius(0.03);
////            StdDraw.setPenColor(StdDraw.RED);
////            for (Point2D p : brute.range(rect))
////                p.draw();
//
//            // draw the range search results for kd-tree in blue
//            StdDraw.setPenRadius(.02);
//            StdDraw.setPenColor(StdDraw.BLUE);
//            for (Point2D p : kdtree.range(rect))
//                p.draw();
//
//            StdDraw.show();
//            StdDraw.pause(40);
//        }
    }
}