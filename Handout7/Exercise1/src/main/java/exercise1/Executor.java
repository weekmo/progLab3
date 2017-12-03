package exercise1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.alg.BronKerboschCliqueFinder;
import org.jgrapht.ext.ComponentNameProvider;
import org.jgrapht.ext.GmlExporter;
import org.jgrapht.ext.IntegerEdgeNameProvider;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Executor class has main function to start the application<br/>
 * it contain solution for Handout7-exercise1
 * 
 * @author laplace
 *
 */
@SuppressWarnings("deprecation")
public class Executor {

	// 3531190 3539452
	public static void main(String[] args) {

		// Create and initialise objects
		Options options = new Options();
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;
		/*
		 * There are three command line parameters: 1- inputfile: has to follow by file
		 * name that includes PMC IDs. 2- outputfile: has to follow by GML file name to
		 * export Graph. 3- threshold: should be followed by optional double value (0.5
		 * by default) that indicate the threshold for graph weight, the graph will add
		 * edge between two nodes if the weight is above the threshold.
		 */
		Option inputfile = new Option("fi", "inputfile", true, "Input file name that contains PMC IDs!");
		inputfile.setRequired(true);
		options.addOption(inputfile);

		Option outputfile = new Option("fo", "outputfile", true, "Output file name for graph!");
		outputfile.setRequired(true);
		options.addOption(outputfile);

		Option threshold = new Option("t", "threshold", true, "Output file name for graph!");
		options.addOption(threshold);

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			formatter.printHelp("Exercise1-Similarity Graph", options);
			System.exit(1);
			return;
		}

		// Read values from command line
		String filein = cmd.getOptionValue("inputfile");
		String fileOut = cmd.getOptionValue("outputfile");
		double thresholdValue = Double.parseDouble(cmd.getOptionValue("threshold", "0.25"));

		ArrayList<Document> documents = new ArrayList<Document>();
		CloseableHttpClient httpClient = HttpClients.custom()
				.setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()).build();
		HttpResponse response;
		URI uri;
		HttpGet httpGet;

		// Get IDs from file by calling custom function
		ArrayList<String> ids = getIDs(filein);

		/*
		 * Loop through all IDs and call API to get documents create array list of
		 * Document, if API give back document, it will be added to document's array
		 * list
		 */
		System.out.println("Please wait, getting documents from API! .....");
		for (String id : ids) {
			uri = getApiUri(id);
			httpGet = new HttpGet(uri);

			try {
				response = httpClient.execute(httpGet);
				JSONObject obj = new JSONObject(EntityUtils.toString(response.getEntity()));
				if (!obj.getJSONObject("result").getJSONObject(id).has("title")) {
					//System.out.println("Document not found: " + id);
					continue;
				}
				String title = obj.getJSONObject("result").getJSONObject(id).getString("title");
				JSONArray authors = obj.getJSONObject("result").getJSONObject(id).getJSONArray("authors");
				int pubYear = Integer
						.parseInt(obj.getJSONObject("result").getJSONObject(id).getString("pubdate").split(" ")[0]);
				documents.add(new Document(Integer.parseInt(id), title, pubYear, authors));
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(documents.size()<ids.size()) {
			System.err.println("Documents: Total "+ids.size()+
					", Found "+documents.size()+
					", Not found "+(ids.size()-documents.size())+"!\n");
		}
		// create two graphs, SimpleWeightedGraph and SimpleGraph
		SimpleWeightedGraph<Document, DefaultWeightedEdge> weightedGraph = new SimpleWeightedGraph<Document, DefaultWeightedEdge>(
				DefaultWeightedEdge.class);

		SimpleGraph<Document, DefaultEdge> unWeightedGraph = new SimpleGraph<Document, DefaultEdge>(DefaultEdge.class);

		// Read documents from Documents array list and them to graphs
		for (Document doc : documents) {
			weightedGraph.addVertex(doc);
			unWeightedGraph.addVertex(doc);
		}
		/*
		 * Add edges to weightedGraph and for unWeightedGraph if the weight is above
		 * threshold
		 */
		for (Document doc1 : documents) {
			for (Document doc2 : documents) {
				if (!doc1.equals(doc2)) {
					weightedGraph.addEdge(doc1, doc2);
					if (getJaccardDistance(doc1, doc2) > thresholdValue) {
						unWeightedGraph.addEdge(doc1, doc2);
					}
				}
			}
		}

		// Add weight to weightedGraph
		for (DefaultWeightedEdge e : weightedGraph.edgeSet()) {
			double weight = getJaccardDistance(weightedGraph.getEdgeSource(e), weightedGraph.getEdgeTarget(e));
			weightedGraph.setEdgeWeight(e, weight);
		}

		// Get largest clique and print its nodes
		Collection<Set<Document>> largestClique = new BronKerboschCliqueFinder<Document, DefaultEdge>(unWeightedGraph)
				.getBiggestMaximalCliques();
		for (Set<Document> set : largestClique) {
			for (Document doc : set) {
				System.out.println("PMC ID: " + doc.getPmcID());
				System.out.println("Title: " + doc.getTitle());
				System.out.println("Publication Year: " + doc.getYear() + "\n");
			}
			System.out.println("--- End of line ---");
		}

		// Print maximal, minimal and average year
		System.out.println(getMaxYear(largestClique));
		System.out.println(getMinYear(largestClique));
		System.out.println(getAverageYear(largestClique));

		// Create Components name and ID providers
		ComponentNameProvider<Document> DocumentName = new ComponentNameProvider<Document>() {

			public String getName(Document component) {
				// TODO Auto-generated method stub
				return component.getTitle();
			}
		};

		ComponentNameProvider<Document> DocumentID = new ComponentNameProvider<Document>() {

			public String getName(Document component) {
				// TODO Auto-generated method stub
				return component.getPmcID() + "";
			}
		};

		ComponentNameProvider<DefaultWeightedEdge> edge1Label = new ComponentNameProvider<DefaultWeightedEdge>() {

			public String getName(DefaultWeightedEdge component) {
				// TODO Auto-generated method stub
				return component.toString();
			}
		};
		ComponentNameProvider<DefaultWeightedEdge> edge1Id = new IntegerEdgeNameProvider<DefaultWeightedEdge>();
		ComponentNameProvider<DefaultEdge> edge2ID = new IntegerEdgeNameProvider<DefaultEdge>();
		ComponentNameProvider<DefaultEdge> edge2Label = new ComponentNameProvider<DefaultEdge>() {

			public String getName(DefaultEdge component) {
				return component.toString();
			}
		};

		// Create and setup graphs exporters
		GmlExporter<Document, DefaultWeightedEdge> weightedGraphExporter = new GmlExporter<Document, DefaultWeightedEdge>(
				DocumentID, DocumentName, edge1Id, edge1Label);
		weightedGraphExporter.setParameter(GmlExporter.Parameter.EXPORT_EDGE_LABELS, true);
		weightedGraphExporter.setParameter(GmlExporter.Parameter.EXPORT_VERTEX_LABELS, true);
		weightedGraphExporter.setParameter(GmlExporter.Parameter.EXPORT_EDGE_WEIGHTS, true);

		GmlExporter<Document, DefaultEdge> unWeightedGraphExporter = new GmlExporter<Document, DefaultEdge>(DocumentID,
				DocumentName, edge2ID, edge2Label);
		unWeightedGraphExporter.setParameter(GmlExporter.Parameter.EXPORT_EDGE_LABELS, true);
		unWeightedGraphExporter.setParameter(GmlExporter.Parameter.EXPORT_VERTEX_LABELS, true);

		// Export graphs
		try {
			weightedGraphExporter.exportGraph(weightedGraph, new FileWriter("data/" + fileOut));
			unWeightedGraphExporter.exportGraph(unWeightedGraph, new FileWriter("data/un" + fileOut));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function to build a URI
	 * 
	 * @param id
	 *            PMC ID
	 * @return URI
	 */
	static URI getApiUri(String id) {
		URI apiUri = null;
		try {
			apiUri = new URIBuilder().setScheme("https").setHost("eutils.ncbi.nlm.nih.gov")
					.setPath("/entrez/eutils/esummary.fcgi").setParameter("db", "pmc").setParameter("id", id)
					.setParameter("retmode", "json").build();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		return apiUri;
	}

	/**
	 * Function to read PMC IDs from file
	 * 
	 * @param fileName
	 *            File contain PMC IDs, it has to be in data folder
	 * @return ArrayList<String> IDs
	 */
	static ArrayList<String> getIDs(String fileName) {
		ArrayList<String> ids = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("data/" + fileName));
			String line;
			while ((line = reader.readLine()) != null) {
				ids.add(line);
			}
			reader.close();
			return ids;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ids;
	}

	/**
	 * Function to calculate Jaccard distance between documents
	 * 
	 * @param doc1
	 * @param doc2
	 * @return JaccardDistance
	 */
	static double getJaccardDistance(Document doc1, Document doc2) {
		int cut = 0;
		for (String author : doc1.getAuthors()) {
			if (doc2.getAuthors().contains(author)) {
				cut++;
			}
		}
		int cup = doc1.getAuthors().size() + doc2.getAuthors().size();
		return (double) cut / (double) (cup - cut);
	}

	/**
	 * Get maximal year among Documents
	 * 
	 * @param largestClique
	 * @return double
	 */
	static int getMaxYear(Collection<Set<Document>> largestClique) {
		int max = 0;
		for (Set<Document> set : largestClique) {
			for (Document doc : set) {
				if (doc.getYear() > max)
					max = doc.getYear();
			}
		}
		return max;
	}

	/**
	 * Get minimal year among Documents
	 * 
	 * @param largestClique
	 * @return double
	 */
	static int getMinYear(Collection<Set<Document>> largestClique) {
		int min = Integer.MAX_VALUE;
		for (Set<Document> set : largestClique) {
			for (Document doc : set) {
				if (doc.getYear() < min)
					min = doc.getYear();
			}
		}
		return min;
	}

	/**
	 * Get average year among Documents
	 * 
	 * @param largestClique
	 * @return double
	 */
	static double getAverageYear(Collection<Set<Document>> largestClique) {
		int counter = 0, sumYears = 0;
		for (Set<Document> set : largestClique) {
			for (Document doc : set) {
				sumYears += doc.getYear();
				counter++;
			}
		}
		return sumYears / counter;
	}
}
