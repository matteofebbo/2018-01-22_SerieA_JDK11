package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {
	
	private SerieADAO dao;
	private Map<Integer,Integer> classifiche;
	private Graph<Stagione, DefaultWeightedEdge> grafo;
	private List<Stagione> best;
	
	public Model() {
		dao= new SerieADAO();
	}

	public List<Team> getSquadre() {
	
		return dao.listTeams();
	}

	public Map<Integer,Integer> getClassifiche(Team team) {
		classifiche= new HashMap<>();
		classifiche= dao.getClassifiche(team);
		return classifiche;
	}

	public void creaGrafo(Team team) {
		
		this.grafo= new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		List<Stagione> stagioni= new ArrayList<>();
		for(Integer s: classifiche.keySet()) {
			Stagione stagione= new Stagione(s,team,classifiche.get(s));
			stagioni.add(stagione);
		}
		
		// vertici
		Graphs.addAllVertices(grafo, stagioni);
		
		// archi
		
		for(Stagione v:grafo.vertexSet()) {
			for(Stagione s: stagioni) {
				if(!s.equals(v) && !this.grafo.containsEdge(this.grafo.getEdge(s, v)) &&  !this.grafo.containsEdge(this.grafo.getEdge(v,s))) {
					int puntiS=s.getPunti();
					int puntiV=v.getPunti();
					int diff=puntiS-puntiV;
					if(diff>0) {
						Graphs.addEdge(grafo, v, s, diff);
						
					}
					else if(diff<0) {
						Graphs.addEdge(grafo, s,v, diff);
						
					}
				}
			}
		}
		
		System.out.println("Grafo creato\n");
		System.out.println("numero vertici: "+grafo.vertexSet().size());
		System.out.println("numero archi: "+grafo.edgeSet().size());
		
		int best=-100000000;
		Stagione oro=null;
		
		for(Stagione s: grafo.vertexSet()) {
			int grado = grafo.inDegreeOf(s)-grafo.outDegreeOf(s);
			if(grado>best) {
				best=grado;
				oro= new Stagione(s.getStagione(),s.getTeam(),s.getPunti());
			}
		}
		
		System.out.println("stagione migliore :\n");
		System.out.println("stagione: "+oro.getStagione()+" punti: "+oro.getPunti()+" grado: "+best);
	}

	public List<Stagione> getCamminoVirtuoso() {
		
		
		best= new ArrayList<>();
		
		for(Stagione s:grafo.vertexSet()) {
			List<Stagione> parziale= new ArrayList<>();
			parziale.add(s);
			ricorsione(parziale,s);
		}
		
		return best;
	}

	private void ricorsione(List<Stagione> parziale,Stagione s) {
		
		
		
		Stagione successiva=this.stagioneNext(s,parziale);
		
		if(successiva!=null && successiva.getPunti()-s.getPunti()>0) {
			System.out.println(successiva.getStagione());
			System.out.println("cacacaccacaca");
			parziale.add(successiva);
			ricorsione(parziale,successiva);
			
		}
		else {
			if(parziale.size()>best.size()) {
				best= new ArrayList<>(parziale);
			}
		}
		
	}

	private Stagione stagioneNext(Stagione s,List<Stagione> parziale) {
		int best=10000;
		Stagione next=null;
		for(Stagione st: grafo.vertexSet()) {
			if(st.getStagione()-s.getStagione()< best && !parziale.contains(st) && st.getStagione()>s.getStagione()) {
				best=st.getStagione()-s.getStagione();
				next=st;
			}
		}
		return next;
	}

}
