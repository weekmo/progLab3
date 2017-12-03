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

@SuppressWarnings("deprecation")
public class Executor {
	
	
	//3531190 3539452
	public static void main(String[] args) {
		Options options = new Options();
		CommandLineParser parser=new DefaultParser();
		HelpFormatter formatter=new HelpFormatter();
		CommandLine cmd;
		
		Option inputfile = new Option("fi","inputfile",true,"Input file name that contains PMC IDs!");
		inputfile.setRequired(true);
		options.addOption(inputfile);
		
		Option outputfile = new Option("fo","outputfile",true,"Output file name for graph!");
		outputfile.setRequired(true);
		options.addOption(outputfile);
		
		Option threshold = new Option("t","threshold",true,"Output file name for graph!");
		options.addOption(threshold);
		
		try {
			cmd= parser.parse(options, args);
		}catch(ParseException e) {
			e.printStackTrace();
			formatter.printHelp("Exercise1-Similarity Graph", options);
			System.exit(1);
			return;
		}
		String filein=cmd.getOptionValue("inputfile");
		String fileOut=cmd.getOptionValue("outputfile");
		double thresholdValue=Double.parseDouble(cmd.getOptionValue("threshold","0.25"));
		
		//System.out.println(filein +" - "+ fileOut);
		
		ArrayList<Document> documents=new ArrayList<Document>();
		CloseableHttpClient httpClient = HttpClients.custom()
				.setDefaultRequestConfig(RequestConfig.custom()
						.setCookieSpec(CookieSpecs.STANDARD)
						.build()).build();
		HttpResponse response;
		URI uri;
		HttpGet httpGet;
		ArrayList<String> ids=getIDs(filein);
		//Document doc;
		//int counter=0;
		for(String id:ids) {
			uri = getApiUri(id);
			httpGet = new HttpGet(uri);
			
			try {
				response = httpClient.execute(httpGet);
				JSONObject obj=new JSONObject(EntityUtils.toString(response.getEntity()));
				if(!obj.getJSONObject("result").getJSONObject(id).has("title")) {
					System.out.println("Document not found: "+id);
					continue;
				}
				//counter++;
				//writeJSONtoFile(obj.toString(),"document"+counter+".json");
				String title=obj.getJSONObject("result").getJSONObject(id).getString("title");
				JSONArray authors=obj.getJSONObject("result").getJSONObject(id).getJSONArray("authors");
				int pubYear=Integer.parseInt(
						obj.getJSONObject("result").getJSONObject(id).getString("pubdate").split(" ")[0]);
				documents.add(new Document(Integer.parseInt(id),title,pubYear,authors));
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		SimpleWeightedGraph<Document, DefaultWeightedEdge> weightedGraph=
				new SimpleWeightedGraph<Document, DefaultWeightedEdge>(DefaultWeightedEdge.class);

		SimpleGraph<Document, DefaultEdge> unWeightedGraph=
				new SimpleGraph<Document, DefaultEdge>(DefaultEdge.class);
		
		for(Document doc:documents) {
			weightedGraph.addVertex(doc);
			unWeightedGraph.addVertex(doc);
		}
		for(Document doc1:documents) {
			for(Document doc2:documents) {
				if(!doc1.equals(doc2)) {
					weightedGraph.addEdge(doc1, doc2);
					if(getJaccardDistance(doc1, doc2)>=thresholdValue) {
						unWeightedGraph.addEdge(doc1, doc2);
					}
				}	
			}
		}
		for(DefaultWeightedEdge e:weightedGraph.edgeSet()) {
			double weight = getJaccardDistance(weightedGraph.getEdgeSource(e),
					weightedGraph.getEdgeTarget(e));
			weightedGraph.setEdgeWeight(e, weight);
		}
		
		Collection<Set<Document>> largestClique=getLargestClique(unWeightedGraph);
		for(Set<Document> set:largestClique) {
			for(Document doc:set) {
				System.out.println("PMC ID: "+doc.getPmcID());
				System.out.println("Title: "+doc.getTitle()+"\n");
			}
		}
		System.out.println(getMaxYear(largestClique));
		System.out.println(getMinYear(largestClique));
		System.out.println(getAverageYear(largestClique));
		ComponentNameProvider<Document> DocumentName=new ComponentNameProvider<Document>() {
			
			public String getName(Document component) {
				// TODO Auto-generated method stub
				return component.getTitle();
			}
		};
		
		ComponentNameProvider<Document> DocumentID = new ComponentNameProvider<Document>() {
			
			public String getName(Document component) {
				// TODO Auto-generated method stub
				return component.getPmcID()+"";
			}
		};
		
		ComponentNameProvider<DefaultWeightedEdge> edge1Label = new ComponentNameProvider<DefaultWeightedEdge>() {
			
			public String getName(DefaultWeightedEdge component) {
				// TODO Auto-generated method stub
				return component.toString();
			}
		};
		ComponentNameProvider<DefaultWeightedEdge> edge1Id=new IntegerEdgeNameProvider<DefaultWeightedEdge>();
		ComponentNameProvider<DefaultEdge> edge2ID= new IntegerEdgeNameProvider<DefaultEdge>();
		ComponentNameProvider<DefaultEdge> edge2Label=new ComponentNameProvider<DefaultEdge>() {
			
			public String getName(DefaultEdge component) {
				return component.toString();
			}
		};
		GmlExporter<Document, DefaultWeightedEdge> weightedGraphExporter = 
				new GmlExporter<Document, DefaultWeightedEdge>(DocumentID,DocumentName,edge1Id,edge1Label);
		weightedGraphExporter.setParameter(GmlExporter.Parameter.EXPORT_EDGE_LABELS, true);
		weightedGraphExporter.setParameter(GmlExporter.Parameter.EXPORT_VERTEX_LABELS,true);
		weightedGraphExporter.setParameter(GmlExporter.Parameter.EXPORT_EDGE_WEIGHTS,true);
		
		GmlExporter<Document, DefaultEdge> unWeightedGraphExporter =
				new GmlExporter<Document, DefaultEdge>(DocumentID, DocumentName, edge2ID, edge2Label);
		unWeightedGraphExporter.setParameter(GmlExporter.Parameter.EXPORT_EDGE_LABELS, true);
		unWeightedGraphExporter.setParameter(GmlExporter.Parameter.EXPORT_VERTEX_LABELS,true);
		try {
			weightedGraphExporter.exportGraph(weightedGraph, new FileWriter("data/"+fileOut));
			unWeightedGraphExporter.exportGraph(unWeightedGraph, new FileWriter("data/un"+fileOut));
		}
		catch(IOException e) {e.printStackTrace();}
	}
	
	static URI getApiUri(String id) {
		URI apiUri=null;
		try {
			apiUri = new URIBuilder()
					.setScheme("https")
					.setHost("eutils.ncbi.nlm.nih.gov")
					.setPath("/entrez/eutils/esummary.fcgi")
					.setParameter("db", "pmc")
					.setParameter("id", id)
					.setParameter("retmode", "json")
					.build();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		return apiUri;
	}
	
	static ArrayList<String> getIDs(String fileName) {
		ArrayList<String> ids=new ArrayList<String>();
		BufferedReader reader=null;
		try {
			reader = new BufferedReader(new FileReader("data/"+fileName));
			String line;
			while((line=reader.readLine())!=null) {
				ids.add(line);
			}
			reader.close();
			return ids;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ids;
	}
	
	static double getJaccardDistance(Document doc1,Document doc2) {
		int cut=0;
		for(String author:doc1.getAuthors()) {
			if(doc2.getAuthors().contains(author)) {
				cut++;
			}
		}
		int cup = doc1.getAuthors().size()+doc2.getAuthors().size();
		return (double)cut/(double)(cup-cut);
	}
	
	static Collection<Set<Document>> getLargestClique(SimpleGraph<Document, DefaultEdge> unWeightedGraph) {
		return new BronKerboschCliqueFinder<Document, DefaultEdge>(unWeightedGraph).getBiggestMaximalCliques();
	}
	static int getMaxYear(Collection<Set<Document>> largestClique) {
		int max=0;
		for(Set<Document> set:largestClique) {
        	for(Document doc:set) {
        		if(doc.getYear()>max)
        			max=doc.getYear();
        	}
        }
		return max;
	}
	static int getMinYear(Collection<Set<Document>> largestClique) {
		int min=Integer.MAX_VALUE;
		for(Set<Document> set:largestClique) {
        	for(Document doc:set) {
        		if(doc.getYear()<min)
        			min=doc.getYear();
        	}
        }
		return min;
	}
	static double getAverageYear(Collection<Set<Document>> largestClique) {
		int counter=0,sumYears=0;
		for(Set<Document> set:largestClique) {
        	for(Document doc:set) {
        		sumYears+=doc.getYear();
        		counter++;
        	}
        }
		return sumYears/counter;
	}

	
	static void writeJSONtoFile(String text,String fileName) {
		try {
			FileWriter fileWriter=new FileWriter("data/"+fileName);
			fileWriter.write(text);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
