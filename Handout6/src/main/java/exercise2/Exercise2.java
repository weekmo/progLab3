package exercise2;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import org.jgrapht.EdgeFactory;
import org.jgrapht.VertexFactory;
import org.jgrapht.ext.ComponentNameProvider;
import org.jgrapht.ext.GmlExporter;
import org.jgrapht.ext.IntegerEdgeNameProvider;
import org.jgrapht.graph.SimpleGraph;

import com.github.javafaker.Faker;

@SuppressWarnings("deprecation")
public class Exercise2 {

	public Exercise2(int nodesNumber) {
		
		VertexFactory<Person> vertexFactory = new VertexFactory<Person>() {
			int n=0;
			public Person createVertex() {
				Faker faker = new Faker(new Locale("de"));
				n++;
				return new Person(n,
						faker.name().firstName());
			}
		};
		EdgeFactory<Person, SocialRelation> edgeFactory = new EdgeFactory<Person, SocialRelation>() {
			
			/**
			 * We just use built-in function from faker class for random selection on strong 
			 * and weak tie
			 */
			public SocialRelation createEdge(Person person1, Person person2) {
				return new SocialRelation(new Faker().bool().bool());
			}
		};
		SimpleGraph<Person, SocialRelation> socialNetwork = new SimpleGraph<Person, SocialRelation>(SocialRelation.class);
		OurGnpRandomGraphGenerator<Person, SocialRelation> randGraphGn = 
				new OurGnpRandomGraphGenerator<Person, SocialRelation>(nodesNumber,0.15);
		randGraphGn.generateGraph(socialNetwork, vertexFactory,edgeFactory,null);
		ComponentNameProvider<Person> nodeName = new ComponentNameProvider<Person>() {
			
			public String getName(Person component) {
				return component.getName();
			}
		};
		ComponentNameProvider<Person> nameName = new ComponentNameProvider<Person>() {
			
			public String getName(Person component) {
				return component.getId()+"";
			}
		};
		ComponentNameProvider<SocialRelation> connectionAtr = new ComponentNameProvider<SocialRelation>() {
			
			public String getName(SocialRelation component) {
				return component.isStrongTie() ? "strong":"weak";
			}
		};
		ComponentNameProvider<SocialRelation> connectionName = new IntegerEdgeNameProvider<SocialRelation>();
		
		GmlExporter<Person, SocialRelation> exporter = new GmlExporter<Person, SocialRelation>(nameName,nodeName,connectionName,connectionAtr);
		exporter.setParameter(GmlExporter.Parameter.EXPORT_EDGE_LABELS, true);
		exporter.setParameter(GmlExporter.Parameter.EXPORT_VERTEX_LABELS,true);
		try {
			exporter.exportGraph(socialNetwork, new FileWriter("data/SocialNetwork.gml"));
			System.out.println("GML file can be found in data folder as SocialNetwork.gml");
		}
		catch(IOException e) {e.printStackTrace();}
		try {
			Desktop.getDesktop().open(new File("data/SocialNetwork.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
