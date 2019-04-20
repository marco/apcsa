import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class SAPTest {

  private SAP sapDigraph1;
  private SAP sapDigraphAmbiguous;
  private SAP sapDigraphWordNet;

  @Before
  public void setup() {
    In in = new In("wordnet-test-files/digraph1.txt");
    Digraph G = new Digraph(in);
    sapDigraph1 = new SAP(G);

    In inAmbiguous = new In("wordnet-test-files/digraph-ambiguous-ancestor.txt");
    Digraph ambiguousGraph = new Digraph(inAmbiguous);
    sapDigraphAmbiguous = new SAP(ambiguousGraph);

    In inWordNet = new In("wordnet-test-files/digraph-wordnet.txt");
    Digraph graphWordNet = new Digraph(inWordNet);
    sapDigraphWordNet = new SAP(graphWordNet);
  }

  @Test
  public void testSAPImmutable() {
      In in = new In("wordnet-test-files/digraph1.txt");
      Digraph G = new Digraph(in);
      sapDigraph1 = new SAP(G);

      // If it's mutable, then the second length will be 2.
      assertEquals(4, sapDigraph1.length(3, 11));
      G.addEdge(3, 5);
      G.addEdge(5, 11);
      assertEquals(4, sapDigraph1.length(3, 11));
  }

  @Test
  public void testLengthIntInt() {
    assertEquals(4, sapDigraph1.length(3, 11));
  }

  @Test
  public void testLengthIntIntAmbiguous() {
    assertEquals(1, sapDigraphAmbiguous.length(1, 2));
    assertEquals(2, sapDigraphAmbiguous.length(4, 6));
  }

  @Test
  public void testLengthIntIntWordNet() {
    assertEquals(2, sapDigraphWordNet.length(5, 10));
    assertEquals(2, sapDigraphWordNet.length(4, 11));
  }

  @Test
  public void testLengthIntIntWordnet() {
    assertEquals(38003, sapDigraphWordNet.ancestor(1645, 3122));
    assertEquals(60600, sapDigraphWordNet.ancestor(5967, 812));
  }

  @Test
  public void testAncestorIntInt() {
    assertEquals(1, sapDigraph1.ancestor(3, 11));
  }

  @Test
  public void testAncestorIntIntAmbiguous() {
    assertEquals(5, sapDigraphAmbiguous.ancestor(5, 6));
    assertEquals(2, sapDigraphAmbiguous.ancestor(4, 1));
  }

  @Test
  public void testAncestorIntIntWordnet() {
    assertEquals(38003, sapDigraphWordNet.ancestor(1645, 3122));
    assertEquals(60600, sapDigraphWordNet.ancestor(5967, 812));
  }

  @Test
  public void testLengthIterableIterable() {
    ArrayList<Integer> verticesV = new ArrayList<Integer>();
    verticesV.add(7);
    verticesV.add(4);
    verticesV.add(9);
    ArrayList<Integer> verticesW = new ArrayList<Integer>();
    verticesW.add(11);
    verticesW.add(2);
    assertEquals(3,sapDigraph1.length(verticesV, verticesW));
  }

  @Test
  public void testLengthIterableWordNet() {
    ArrayList<Integer> verticesA = new ArrayList<Integer>();
    verticesA.add(4810);
    verticesA.add(1211);
    verticesA.add(8301);
    ArrayList<Integer> verticesB = new ArrayList<Integer>();
    verticesB.add(6788);
    verticesB.add(3912);
    verticesB.add(904);
    assertEquals(7, sapDigraphWordNet.length(verticesA, verticesB));
  }

  @Test
  public void testLengthIterableAmbiguousNet() {
    ArrayList<Integer> verticesA = new ArrayList<Integer>();
    verticesA.add(1);
    verticesA.add(2);
    verticesA.add(3);
    ArrayList<Integer> verticesB = new ArrayList<Integer>();
    verticesB.add(5);
    verticesB.add(6);
    verticesB.add(7);
    assertEquals(2, sapDigraphAmbiguous.length(verticesA, verticesB));
  }

  @Test
  public void testAncestorIterableIterable() {
    ArrayList<Integer> verticesV = new ArrayList<Integer>();
    verticesV.add(7);
    verticesV.add(4);
    verticesV.add(9);
    ArrayList<Integer> verticesW = new ArrayList<Integer>();
    verticesW.add(11);
    verticesW.add(2);
    assertEquals(5, sapDigraph1.ancestor(verticesV, verticesW));
  }

  @Test
  public void testAncestorIterableAmbiguous() {
    ArrayList<Integer> verticesA = new ArrayList<Integer>();
    verticesA.add(1);
    verticesA.add(2);
    verticesA.add(3);
    ArrayList<Integer> verticesB = new ArrayList<Integer>();
    verticesB.add(5);
    verticesB.add(6);
    verticesB.add(7);
    assertEquals(2, sapDigraphAmbiguous.length(verticesA, verticesB));
  }

  @Test
  public void testAncestorIterableWordNet() {
    ArrayList<Integer> verticesA = new ArrayList<Integer>();
    verticesA.add(4810);
    verticesA.add(1211);
    verticesA.add(8301);
    ArrayList<Integer> verticesB = new ArrayList<Integer>();
    verticesB.add(6788);
    verticesB.add(3912);
    verticesB.add(904);
    assertEquals(7, sapDigraphWordNet.length(verticesA, verticesB));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testThrowNegativeVertexAncestor() {
      sapDigraphWordNet.ancestor(10, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testThrowNegativeVertexListAncestor() {
      ArrayList<Integer> verticesA = new ArrayList<Integer>();
      verticesA.add(4810);
      verticesA.add(1211);
      verticesA.add(8301);
      ArrayList<Integer> verticesB = new ArrayList<Integer>();
      verticesB.add(-1);
      verticesB.add(3912);
      verticesB.add(904);
      sapDigraphWordNet.ancestor(verticesA, verticesB);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testThrowNullInVertexListAncestor() {
      ArrayList<Integer> verticesA = new ArrayList<Integer>();
      verticesA.add(4810);
      verticesA.add(1211);
      verticesA.add(8301);
      ArrayList<Integer> verticesB = new ArrayList<Integer>();
      verticesB.add(null);
      verticesB.add(3912);
      verticesB.add(904);
      sapDigraphWordNet.ancestor(verticesA, verticesB);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testThrowNullVertexListAncestor() {
      ArrayList<Integer> verticesA = new ArrayList<Integer>();
      verticesA.add(4810);
      verticesA.add(1211);
      verticesA.add(8301);
      sapDigraphWordNet.ancestor(verticesA, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testThrowNegativeVertexLength() {
      sapDigraphWordNet.length(10, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testThrowNegativeVertexListLength() {
      ArrayList<Integer> verticesA = new ArrayList<Integer>();
      verticesA.add(4810);
      verticesA.add(1211);
      verticesA.add(8301);
      ArrayList<Integer> verticesB = new ArrayList<Integer>();
      verticesB.add(-1);
      verticesB.add(3912);
      verticesB.add(904);
      sapDigraphWordNet.length(verticesA, verticesB);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testThrowNullInVertexListLength() {
      ArrayList<Integer> verticesA = new ArrayList<Integer>();
      verticesA.add(4810);
      verticesA.add(1211);
      verticesA.add(8301);
      ArrayList<Integer> verticesB = new ArrayList<Integer>();
      verticesB.add(null);
      verticesB.add(3912);
      verticesB.add(904);
      sapDigraphWordNet.length(verticesA, verticesB);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testThrowNullVertexListLength() {
      ArrayList<Integer> verticesA = new ArrayList<Integer>();
      verticesA.add(4810);
      verticesA.add(1211);
      verticesA.add(8301);
      sapDigraphWordNet.length(verticesA, null);
  }
}
