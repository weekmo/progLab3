package exercise2;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.VertexFactory;
import org.jgrapht.generate.CompleteBipartiteGraphGenerator;
import org.jgrapht.generate.GnpRandomGraphGenerator;
import org.jgrapht.graph.SimpleGraph;

public class Exercise2 {

	public Exercise2() {
		VertexFactory<String> vertexFactory = new VertexFactory<String>() {
			int n=0;
			
			public String createVertex() {
				String vertex = String.valueOf(n);
				n++;
				return vertex;
			}
		};
		SimpleGraph<String, DefaultEdge> exampleGraph = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
		//CompleteBipartiteGraphGenerator<String, DefaultEdge> graphGen = new CompleteBipartiteGraphGenerator<String, DefaultEdge>(5, 3);
		GnpRandomGraphGenerator<String, DefaultEdge> randGraphGn=new GnpRandomGraphGenerator<String, DefaultEdge>(5, 0.5);
		randGraphGn.generateGraph(exampleGraph, vertexFactory, null);
		//graphGen.generateGraph(exampleGraph, vertexFactory, null);
		System.out.println(exampleGraph.vertexSet().size());
		for(String node:exampleGraph.vertexSet()) {
			System.out.println(node);
		}
	}

}
