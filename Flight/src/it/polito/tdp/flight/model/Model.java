package it.polito.tdp.flight.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flight.db.FlightDAO;


public class Model {

	FlightDAO fdao = null;
	List<Border> airports;
	List<Airline> airlines;
	List<Route> routes;

	AirlineIdMap airlineIdMap;
	AirportIdMap airportIdMap;
	RouteIdMap routeIdMap;

	SimpleDirectedWeightedGraph<Border, DefaultWeightedEdge> grafo;


	public Model() {
		fdao = new FlightDAO();

		airlineIdMap = new AirlineIdMap();
		airportIdMap = new AirportIdMap();
		routeIdMap = new RouteIdMap();

		airlines = fdao.getAllAirlines(airlineIdMap);
		System.out.println(airlines.size());

		airports = fdao.getAllAirports(airportIdMap);
		System.out.println(airports.size());

		routes = fdao.getAllRoutes(airlineIdMap, airportIdMap, routeIdMap);
		System.out.println(routes.size());
	}

	public List<Border> getAirports() {
		if (this.airports == null) {
			return new ArrayList<Border>();
		}
		return this.airports;
	}

	public void createGraph() {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, this.airports);
		
		for (Route r : routes) {
			Border sourceAirport = r.getSourceAirport();
			Border destinationAirport = r.getDestinationAirport();
			
			if (!sourceAirport.equals(destinationAirport)) {
				double weight = LatLngTool.distance(new LatLng(sourceAirport.getLatitude(), 
						sourceAirport.getLongitude()), new LatLng(destinationAirport.getLatitude(), 
								destinationAirport.getLongitude()), LengthUnit.KILOMETER);
				Graphs.addEdge(grafo, sourceAirport, destinationAirport, weight);
			}
		}
		
		System.out.println(grafo.vertexSet().size());
		System.out.println(grafo.edgeSet().size());
	}
	
	public void printStats() {
		if (grafo != null) {
			this.createGraph();
		}
		
		ConnectivityInspector<Border, DefaultWeightedEdge> ci = new ConnectivityInspector(grafo);
		System.out.println(ci.connectedSets().size());
		
		
	}
	
	public Set<Border> getBiggestSCC() {
		
		ConnectivityInspector<Border, DefaultWeightedEdge> ci = new ConnectivityInspector(grafo);
		
		Set<Border> bestSet = null;
		int bestSize = 0;
		
		for (Set<Border> s : ci.connectedSets()) {
			if (s.size() > bestSize) {
				bestSet = new HashSet<Border>(s);
				bestSize = s.size();
			}	
		}
		
		return bestSet;
	}

	public List<Border> getShortestPath(int id1, int id2) {
		
		Border source = airportIdMap.get(id1);
		Border destination = airportIdMap.get(id2);
		
		System.out.println(source);
		System.out.println(destination);
		
		if (source == null || destination == null) {
			throw new RuntimeException("Gli areoporti selezionati non sono presenti in memoria");
		}

		ShortestPathAlgorithm<Border,DefaultWeightedEdge> spa = new DijkstraShortestPath<Border, DefaultWeightedEdge>(grafo);
		double weight = spa.getPathWeight(source, destination);
		System.out.println(weight);
		
		GraphPath<Border,DefaultWeightedEdge> gp = spa.getPath(source, destination);
		
		return gp.getVertexList();
	}

}
