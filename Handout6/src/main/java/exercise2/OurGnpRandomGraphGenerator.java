package exercise2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.VertexFactory;
import org.jgrapht.generate.GnpRandomGraphGenerator;

/**
 * Class inherits from {@code GnpRandomGraphGenerator} to override
 * {@code generateGraph} function
 * 
 * @author Laplace
 *
 * @param <V>
 *            vertex
 * @param <E>
 *            edge
 */
public class OurGnpRandomGraphGenerator<V, E> extends GnpRandomGraphGenerator<V, E> {

	private int n;
	private double p;
	private Random rng;

	/**
	 * Class constructor takes:
	 * 
	 * @param n
	 *            vertex number
	 * @param p
	 *            probability
	 */
	public OurGnpRandomGraphGenerator(int n, double p) {
		super(n, p);
		this.n = n;
		this.p = p;
		this.rng = new Random();
	}

	/**
	 * Graph generator
	 * 
	 * @param target
	 *            Graph
	 * @param vertexFactory
	 * @param edgeFactory
	 * @param resultMap
	 */
	public void generateGraph(Graph<V, E> target, VertexFactory<V> vertexFactory, EdgeFactory<V, E> edgeFactory,
			Map<String, V> resultMap) {
		// special case
		if (n == 0)
			return;

		// create vertices
		int previousVertexSetSize = target.vertexSet().size();
		Map<Integer, V> vertices = new HashMap<Integer, V>(n);
		for (int i = 0; i < n; i++) {
			V v = vertexFactory.createVertex();
			target.addVertex(v);
			vertices.put(i, v);
		}

		if (target.vertexSet().size() != previousVertexSetSize + n) {
			throw new IllegalArgumentException("Vertex factory did not produce " + n + " distinct vertices.");
		}
		// create edges
		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				V s = vertices.get(i);
				V t = vertices.get(j);
				if (i == j)
					continue;
				// s->t
				if (rng.nextDouble() < p) {
					target.addEdge(s, t, edgeFactory.createEdge(s, t));
				}
			}
		}
	}
}
