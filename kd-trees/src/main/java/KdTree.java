/******************************************************************************
 *  Name:    J.D. DeVaughn-Brown
 *  NetID:   jddevaug
 *  Precept: P05
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 *
 *  Compilation:  javac-algs4 KdTree.java
 *  Execution:    java-algs4 KdTree
 *  Dependencies: Point2D.java RectHV.java
 *
 *  Description: Represents a set of points in the unit square
 *  (all points have x- and y-coordinates between 0 and 1)
 *  using a 2d-tree to support efficient range search
 *  (find all of the points contained in a query rectangle)
 *  and nearest-neighbor search (find a closest point to a query point).
 ******************************************************************************/

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

/**
 * A tree representing a set of points, which supports insertion and
 * searching.
 *
 * @author skunkmb
 */
public class KdTree {
    private static final double PEN_SIZE_POINT = 0.02;
    private static final double PEN_SIZE_LINE = 0.005;

    /**
     * A node containing a point, its axis rectangle, and its children.
     *
     * @author skunkmb
     */
    private class PointNode {
        /**
         * The point that this node represents.
         */
        private Point2D point;

        /**
         * The axis rectangle used to represent this point's half-section.
         */
        private RectHV axisRectangle;

        /**
         * The left child node, or `null` if there is none.
         */
        private PointNode leftPointNode;

        /**
         * The right child node, or `null` if there is none.
         */
        private PointNode rightPointNode;

        /**
         * Whether or not the children of this node are x-sorted. If
         * they aren't x-sorted, then they are y-sorted by definition.
         */
        private boolean childrenAreXSorted;

        /**
         * Constructs a `PointNode`.
         *
         * @param point The point that this node represents.
         * @param leftPointNode The left child node.
         * @param rightPointNode The right child node.
         * @param childrenAreXSorted Whether or not the children are
         * x-sorted.
         * @param axisRectangle The axis rectangle of this point's
         * half-section.
         */
        private PointNode(
            Point2D point,
            PointNode leftPointNode,
            PointNode rightPointNode,
            boolean childrenAreXSorted,
            RectHV axisRectangle
        ) {
            this.point = point;
            this.leftPointNode = leftPointNode;
            this.rightPointNode = rightPointNode;
            this.childrenAreXSorted = childrenAreXSorted;
            this.axisRectangle = axisRectangle;
        }
    }

    /**
     * The root node of this tree, or `null` if the tree is empty.
     */
    private PointNode rootNode;

    /**
     * The current number of nodes in this tree.
     */
    private int nodeCount = 0;

    /**
     * Constructs an empty `KdTree`.
     */
    public KdTree() {
        rootNode = null;
    }

    /**
     * Returns whether or not the tree is empty.
     *
     * @return Whether or not the tree is empty.
     */
    public boolean isEmpty() {
        return rootNode == null;
    }

    /**
     * Returns the number of points in this tree.
     *
     * @return The size of this tree.
     */
    public int size() {
        return nodeCount;
    }

    /**
     * Inserts a point into the tree, or throws an exception if the
     * point is `null`. If the point is already used in the tree, it
     * will not be added.
     *
     * @param pointToAdd The point to add.
     */
    public void insert(Point2D pointToAdd) {
        if (pointToAdd == null) {
            throw new IllegalArgumentException("The inserted point cannot be null.");
        }

        if (contains(pointToAdd)) {
            return;
        }

        nodeCount++;

        if (rootNode == null) {
            rootNode = new PointNode(pointToAdd, null, null, true, new RectHV(0, 0, 1, 1));
        } else {
            addAsNodeChild(rootNode, pointToAdd);
        }
    }

    /**
     * Adds a point as a child or subchild of a parent node.
     *
     * @param parent The parent node to add to.
     * @param pointToAdd The point to add.
     */
    private void addAsNodeChild(PointNode parent, Point2D pointToAdd) {
        if (getPointIsLeft(parent, pointToAdd)) {
            addNodeChildLeft(parent, pointToAdd);
        } else {
            addNodeChildRight(parent, pointToAdd);
        }
    }

    /**
     * Adds a point as the left child of a parent node. If it cannot be
     * added, it is added as a child or subchild of the left node.
     *
     * @param parent The parent node to add to.
     * @param pointToAdd The point to add.
     */
    private void addNodeChildLeft(PointNode parent, Point2D pointToAdd) {
        if (parent.leftPointNode == null) {
            RectHV newRect;
            RectHV parRect = parent.axisRectangle;

            if (parent.childrenAreXSorted) {
                newRect = new RectHV(parRect.xmin(), parRect.ymin(), parent.point.x(), parRect.ymax());
            } else {
                newRect = new RectHV(parRect.xmin(), parRect.ymin(), parRect.xmax(), parent.point.y());
            }

            parent.leftPointNode = new PointNode(pointToAdd, null, null, !parent.childrenAreXSorted, newRect);
        } else {
            addAsNodeChild(parent.leftPointNode, pointToAdd);
        }
    }

    /**
     * Adds a point as the right child of a parent node. If it cannot be
     * added, it is added as a child or subchild of the right node.
     *
     * @param parent The parent node to add to.
     * @param pointToAdd The point to add.
     */
    private void addNodeChildRight(PointNode parent, Point2D pointToAdd) {
        if (parent.rightPointNode == null) {
            RectHV newRect;
            RectHV parRect = parent.axisRectangle;

            if (parent.childrenAreXSorted) {
                newRect = new RectHV(parent.point.x(), parRect.ymin(), parRect.xmax(), parRect.ymax());
            } else {
                newRect = new RectHV(parRect.xmin(), parent.point.y(), parRect.xmax(), parRect.ymax());
            }

            parent.rightPointNode = new PointNode(pointToAdd, null, null, !parent.childrenAreXSorted, newRect);
        } else {
            addAsNodeChild(parent.rightPointNode, pointToAdd);
        }
    }

    /**
     * Returns whether or not a point should go left of a parent on the
     * tree. A node should go left if its x-value is less than the
     * parent's x-value when x-sorted, or if its y-value is less
     * when y-sorted.
     *
     * @param parent The parent of the child.
     * @param child The node to check.
     * @return Whether or not the child should be a left child. If it
     * shouldn't be, then it should be a right child by definition.
     */
    private boolean getPointIsLeft(PointNode parent, Point2D child) {
        if (parent.childrenAreXSorted) {
            return child.x() < parent.point.x();
        }

        return child.y() < parent.point.y();
    }

    /**
     * Returns whether or not a point is contained in the tree.
     *
     * @param pointToCheck The point to check.
     * @return Whether or not the point is contained.
     */
    public boolean contains(Point2D pointToCheck) {
        if (pointToCheck == null) {
            throw new IllegalArgumentException("The point to search for cannot be null.");
        }

        if (rootNode == null) {
            return false;
        }

        return checkNodeHasChild(rootNode, pointToCheck);
    }

    /**
     * Checks whether or not a node is equal to a point or has it as a
     * child or subchild.
     *
     * @param parent The parent node to check from.
     * @param pointToCheck The point to check.
     * @return Whether or not the parent is equal to the point or has
     * it as a child or subchild.
     */
    private boolean checkNodeHasChild(PointNode parent, Point2D pointToCheck) {
        if (parent.point.equals(pointToCheck)) {
            return true;
        }

        if (getPointIsLeft(parent, pointToCheck)) {
            if (parent.leftPointNode == null) {
                return false;
            }

            return checkNodeHasChild(parent.leftPointNode, pointToCheck);
        } else {
            if (parent.rightPointNode == null) {
                return false;
            }

            return checkNodeHasChild(parent.rightPointNode, pointToCheck);
        }
    }

    /**
     * Returns an `Iterable` of points on the tree that are bounded
     * inside of a given rectangle.
     *
     * @param rectangle The bounding rectangle to check.
     * @return The points inside of this rectangle.
     */
    public Iterable<Point2D> range(RectHV rectangle) {
        if (rectangle == null) {
            throw new IllegalArgumentException("The rectangle cannot be null.");
        }

        if (rootNode == null) {
            return new ArrayList<Point2D>();
        }

        List<Point2D> currentList = new ArrayList<Point2D>();
        getRangeFromNode(currentList, rectangle, rootNode);

        return currentList;
    }

    /**
     * Adds to a list of points that descend from a parent node within
     * a bounded inside of a given rectangle.
     *
     * @param currentList The list to add to.
     * @param rectangle The bounding rectangle to check.
     * @param startNode The parent node to search.
     */
    private void getRangeFromNode(List<Point2D> currentList, RectHV rectangle, PointNode startNode) {
        if (rectangle.contains(startNode.point)) {
            currentList.add(startNode.point);
        }

        if (startNode.leftPointNode != null && rectangle.intersects(startNode.leftPointNode.axisRectangle)) {
            getRangeFromNode(currentList, rectangle, startNode.leftPointNode);
        }

        if (startNode.rightPointNode != null && rectangle.intersects(startNode.rightPointNode.axisRectangle)) {
            getRangeFromNode(currentList, rectangle, startNode.rightPointNode);
        }
    }

    /**
     * Returns the nearest point in the tree to a given point.
     *
     * @param point The point to search for.
     * @return The nearest point.
     */
    public Point2D nearest(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException("The point to search for cannot be null.");
        }

        if (rootNode == null) {
            return null;
        }

        return getBestFromChampion(point, rootNode, rootNode, Double.POSITIVE_INFINITY).point;
    }

    /**
     * Returns the nearest point to a target point given an existing
     * "champion" point so far.
     *
     * @param target The point to search for.
     * @param start The start node to search from.
     * @param champion The champion node so far. This node has been the
     * closest to the target so far.
     * @param championDistance The champion distance so far.
     * @return The closest point, or the champion if no better
     * alternative has been found.
     */
    private PointNode getBestFromChampion(
        Point2D target,
        PointNode start,
        PointNode champion,
        double championDistance
    ) {
        double startRectDistance = start.axisRectangle.distanceSquaredTo(target);

        // If this starting node is better than the current champion,
        // restart with the current node as the champion.
        if (startRectDistance < championDistance) {
            double startDistance = start.point.distanceSquaredTo(target);

            if (startDistance < championDistance) {
                return getBestFromChampion(target, start, start, startDistance);
            }
        }

        boolean canGoLeft = false;
        boolean canGoRight = false;

        // First, check if we can go left and store this for later.
        if (
            start.leftPointNode != null
                && start.leftPointNode.axisRectangle.distanceSquaredTo(target) < championDistance) {
            canGoLeft = true;
        }

        // Check if we can go right and store this for later.
        if (
            start.rightPointNode != null
                && start.rightPointNode.axisRectangle.distanceSquaredTo(target) < championDistance) {
            canGoRight = true;
        }

        // If it can go either way, then check both of the children,
        // starting with the closer child.
        if (canGoLeft && canGoRight) {
            double leftDistance = start.leftPointNode.axisRectangle.distanceSquaredTo(target);
            double rightDistance = start.rightPointNode.axisRectangle.distanceSquaredTo(target);

            if (leftDistance < rightDistance) {
                PointNode leftBest = getBestFromChampion(
                    target,
                    start.leftPointNode,
                    champion,
                    championDistance
                );
                return getBestFromChampion(
                    target,
                    start.rightPointNode,
                    leftBest,
                    leftBest.point.distanceSquaredTo(target)
                );
            } else {
                PointNode rightBest = getBestFromChampion(
                    target,
                    start.rightPointNode,
                    champion,
                    championDistance
                );
                return getBestFromChampion(
                    target,
                    start.leftPointNode,
                    rightBest,
                    rightBest.point.distanceSquaredTo(target)
                );
            }
        }

        // If it can only go left, just return with left as the start.
        if (canGoLeft) {
            return getBestFromChampion(target, start.leftPointNode, champion, championDistance);
        }

        // If it can only go right, just return with right as the start.
        if (canGoRight) {
            return getBestFromChampion(target, start.rightPointNode, champion, championDistance);
        }

        // If all else fails, return the champion.
        return champion;
    }

    /**
     * Draws this tree to `StdDraw`.
     */
    public void draw() {
        if (rootNode != null) {
            drawPointAndChildren(rootNode);
        }
    }

    /**
     * Draws a point and its children and subchildren to `StdDraw`.
     *
     * @param point The point to draw.
     */
    private void drawPointAndChildren(PointNode point) {
        if (point.childrenAreXSorted) {
            StdDraw.setPenRadius(PEN_SIZE_LINE);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(point.point.x(), point.axisRectangle.ymin(), point.point.x(), point.axisRectangle.ymax());
        } else {
            StdDraw.setPenRadius(PEN_SIZE_LINE);
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(point.axisRectangle.xmin(), point.point.y(), point.axisRectangle.xmax(), point.point.y());
        }

        if (point.leftPointNode != null) {
            drawPointAndChildren(point.leftPointNode);
        }

        if (point.rightPointNode != null) {
            drawPointAndChildren(point.rightPointNode);
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(PEN_SIZE_POINT);
        point.point.draw();
    }

    /**
     * Returns a level-order iterable for the points in this tree. This
     * method is based on https://piazza.com/class/jjo56in6m8qyn?cid=127.
     *
     * @return The level-order iterable.
     */
    Iterable<Point2D> levelOrder() {
        Queue<Point2D> points = new Queue<Point2D>();
        Queue<PointNode> queue = new Queue<PointNode>();
        queue.enqueue(rootNode);

        while (!queue.isEmpty()) {
            PointNode nextPoint = queue.dequeue();

            if (nextPoint == null) {
                continue;
            }

            points.enqueue(nextPoint.point);

            if (nextPoint.leftPointNode != null) {
                queue.enqueue(nextPoint.leftPointNode);
            }

            if (nextPoint.rightPointNode != null) {
                queue.enqueue(nextPoint.rightPointNode);
            }
        }

        return points;
    }
}
