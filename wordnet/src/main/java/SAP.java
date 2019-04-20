import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.DepthFirstOrder;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Topological;

/**
 * A class for identifying the shortest ancestral path
 * between two elements of a digraph. The shortest ancestral path is the
 * sum of the distances between a common ancestor and two vertices.
 *
 * @author skunkmb
 */
public class SAP {
    /**
     * The digraph to find paths in.
     */
    private Digraph graph;

    /**
     * A topologically-sorted list of vertices within the digraph.
     * This is used for finding the ancestor of two digraph vertices.
     */
    private Iterable<Integer> orderedGraphVertices;

    /**
     * Constructs an `SAP`.
     *
     * @param graph The digraph to be used for finding ancestors and
     * paths. This is a digraph, but not necessarily a "DAG" (directional
     *
     */
    public SAP(Digraph graph) {
        this.graph = new Digraph(graph);
        Topological topologicalSorting = new Topological(this.graph);

        if (topologicalSorting.hasOrder()) {
            orderedGraphVertices = topologicalSorting.order();
        } else {
            orderedGraphVertices = new DepthFirstOrder(this.graph).reversePost();
        }
    }

    /**
     * Finds the length of the shortest ancestral path between two
     * vertices. The shortest ancestral path is the sum of the distances
     * between a common ancestor and two vertices.
     *
     * @param vertexA The first vertex to find length for.
     * @param vertexB The second vertex to find length for.
     * @return The shortest ancestral path between the vertices.
     */
    public int length(int vertexA, int vertexB) {
        AncestorResult ancestorResult = findAncestor(new BreadthFirstDirectedPaths(graph, vertexA), new BreadthFirstDirectedPaths(graph, vertexB));

        if (ancestorResult.ancestor == -1) {
            return -1;
        }

        return ancestorResult.distanceSum;
    }

    /**
     * Finds the common ancestor between two BFD search paths, each of which
     * should be constructed with a separate vertex and the same digraph.
     *
     * @param searchA The first BFD seach path.
     * @param searchB The second BFD seach path.
     * @return An `AncestorResult` representing the common ancestor.
     */
    private AncestorResult findAncestor(BreadthFirstDirectedPaths searchA, BreadthFirstDirectedPaths searchB) {
        int bestAncestor = -1;
        int bestDistanceSum = Integer.MAX_VALUE;

        for (Integer possibleAncestor : orderedGraphVertices) {
            int distA = searchA.distTo(possibleAncestor);
            int distB = searchB.distTo(possibleAncestor);

            if (distA == Integer.MAX_VALUE || distB == Integer.MAX_VALUE) {
                continue;
            }

            if (distA + distB < bestDistanceSum) {
                bestDistanceSum = distA + distB;
                bestAncestor = possibleAncestor;
            }
        }

        return new AncestorResult(bestAncestor, bestDistanceSum);
    }

    /**
     * Finds the common ancestor between two vertices on the digraph.
     *
     * @param vertexA The first vertex.
     * @param vertexB The second vertex.
     * @return The common ancestor vertex.
     */
    public int ancestor(int vertexA, int vertexB) {
        return findAncestor(new BreadthFirstDirectedPaths(graph, vertexA), new BreadthFirstDirectedPaths(graph, vertexB)).ancestor;
    }

    /**
     * Validates an array of vertices for the digraph, or throws an
     * error if any is invalid.
     *
     * @param vertices The vertices to validate.
     */
    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("The vertices cannot be null.");
        }

        for (Integer vertex : vertices) {
            if (vertex == null) {
                throw new IllegalArgumentException("Vertices cannot be null.");
            }
        }
    }

    /**
     * Finds the length of the shortest ancestral path between any two
     * vertices in two arrays. The shortest ancestral path is the sum
     * of the distances between a common ancestor and two vertices.
     *
     * @param verticesA The first array of vertices to find length for.
     * @param verticesB The second array of vertices to find length for.
     * @return The shortest ancestral path between the closest vertices.
     */
    public int length(Iterable<Integer> verticesA, Iterable<Integer> verticesB) {
        validateVertices(verticesA);
        validateVertices(verticesB);

        AncestorResult ancestorResult = findAncestor(new BreadthFirstDirectedPaths(graph, verticesA), new BreadthFirstDirectedPaths(graph, verticesB));

        if (ancestorResult.ancestor == -1) {
            return -1;
        }

        return ancestorResult.distanceSum;
    }

    /**
     * Finds the closest common ancestor between any two
     * vertices in two arrays.
     *
     * @param verticesA The first array of vertices to find length for.
     * @param verticesB The second array of vertices to find length for.
     * @return The closest common ancestor between any two vertices.
     */
    public int ancestor(Iterable<Integer> verticesA, Iterable<Integer> verticesB) {
        validateVertices(verticesA);
        validateVertices(verticesB);

        return findAncestor(new BreadthFirstDirectedPaths(graph, verticesA), new BreadthFirstDirectedPaths(graph, verticesB)).ancestor;
    }

    /**
     * A class representing the result of an ancestor search.
     *
     * @author skunkmb
     */
    private class AncestorResult {
        /**
         * The resulting ancestor vertex.
         */
        private Integer ancestor;

        /**
         * The resulting shortest ancestral path length from the
         * ancestor and the target vertices.
         */
        private int distanceSum;

        /**
         * Constructs an `AncestorResult`.
         *
         * @param ancestor The found ancestor vertex.
         * @param distanceSum The found shortest ancestral path length.
         */
        private AncestorResult(Integer ancestor, int distanceSum) {
            this.ancestor = ancestor;
            this.distanceSum = distanceSum;
        }
    }
}