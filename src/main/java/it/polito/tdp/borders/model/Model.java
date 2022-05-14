package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.borders.db.BordersDAO;



public class Model {

	private List<Country> arrayNazioni;
	private List<Border> confini;
	private Map<Integer, Country> mapNazioni;
	private BordersDAO dao;
	private Graph<Country, DefaultEdge> grafo;
	
	
	public Model() {
	arrayNazioni = new ArrayList<Country>();
	mapNazioni = new HashMap<Integer, Country>();
	confini = new ArrayList<Border>();
	dao = new BordersDAO();
	}
	
	public List<Country> getNazioni() {
		if(arrayNazioni.isEmpty()) {
		   arrayNazioni = dao.loadAllCountries();
		   for(Country c:arrayNazioni) {
			   mapNazioni.put(c.getKey(), c);
		   }
		   return arrayNazioni;
		}
		else return arrayNazioni;
	}
	
	public Map<Integer,Country> getMapNazioni(){
		if(mapNazioni.isEmpty()) {
			getNazioni();
			return mapNazioni;
		}
		else return mapNazioni;
	}
	
	public List<Border> getBorder(int anno){
		if(confini.isEmpty()) {
		return dao.getCountryPairs(anno);
		}
		else return this.confini;
	}
	
	public Graph<Country, DefaultEdge> creaGrafo(int anno){
		this.grafo = new SimpleGraph<Country,DefaultEdge>(DefaultEdge.class);
		for(Border b:getBorder(anno)) {
			this.grafo.addVertex(getMapNazioni().get(b.getB1()));
			this.grafo.addVertex(getMapNazioni().get(b.getB2()));
			this.grafo.addEdge(getMapNazioni().get(b.getB1()), getMapNazioni().get(b.getB2()));
		}
		this.confini.clear();
		return grafo;	
	}
	public Graph<Country, DefaultEdge> getGrafo() {
		if(this.grafo!=null)
			return this.grafo;
		else return null;
					
	}
	//metodo 1: libreria jgrapht
	public List<Country> visitaGrafo(Country partenza){
		List<Country> raggiungibili = new ArrayList<Country>();
		if(this.grafo.vertexSet().contains(partenza)) {
	    GraphIterator<Country,DefaultEdge> visita = new BreadthFirstIterator<Country,DefaultEdge>(this.grafo,partenza);
		while(visita.hasNext()) {
			raggiungibili.add(visita.next());
		}
		return raggiungibili;
		}
		else return null;
	}
	//metodo 2: algoritmo ricorsivo per visita in profondita
	private List<Country> raggiungibili;
	public List<Country> visitaGrafoRicorsiva(Country partenza){
	raggiungibili = new ArrayList<Country>();
	List<Country> parziale = new ArrayList();
	ricorsiva(parziale,partenza);
	raggiungibili = parziale;
	return raggiungibili;
	}
	
	public void ricorsiva(List<Country> parziale,Country c) {
		if(!parziale.contains(c))
			parziale.add(c);
	 for (DefaultEdge e: this.grafo.edgesOf(c))	{
		 if(!this.grafo.getEdgeSource(e).equals(c) && !parziale.contains(this.grafo.getEdgeSource(e))) {
			 parziale.add(this.grafo.getEdgeSource(e));
			 ricorsiva(parziale,this.grafo.getEdgeSource(e));
		 }
		 if(!this.grafo.getEdgeTarget(e).equals(c) && !parziale.contains(this.grafo.getEdgeTarget(e))) {
			 parziale.add(this.grafo.getEdgeTarget(e));
			 ricorsiva(parziale,this.grafo.getEdgeTarget(e));
		 } 
	 } 
}
	
	//metodo 3: algoritmo iterativo per la visita in ampiezza.
	public List<Country> visitaGrafoIterativa(Country partenza){
	List<Country> visitati = new LinkedList<Country>();
	List<Country> daVisitare = new LinkedList<Country>();
	daVisitare.add(partenza);
	while(daVisitare.size()!=0) {
		if(Graphs.neighborListOf(this.grafo, daVisitare.get(0))!=null && !visitati.contains(daVisitare.get(0))) {
		daVisitare.addAll(Graphs.neighborListOf(this.grafo, daVisitare.get(0)));
		visitati.add(daVisitare.get(0));
		}
		daVisitare.remove(0);
	}
		
/* Si inizia inserendo lo stato scelto nella
lista daVisitare. L’algoritmo continua fino a quando la lista dei 
nodi daVisitare non si svuota. Ad ogni passo si estrae un nodo dalla 
lista daVisitare e si inseriscono tutti i nodi vicini a quello estratto
(a meno di quelli già visitati) nella lista daVisitare. Infine, il nodo 
estratto viene inserito nella lista dei Visitati.*/
		
		
		return visitati;
	}



}
