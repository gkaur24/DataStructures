package apps;

import structures.*;

import java.util.ArrayList;

import apps.PartialTree.Arc;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
	
		/* COMPLETE THIS METHOD */
		
		PartialTreeList L = new PartialTreeList();
		
		
		for(int i=0; i < graph.vertices.length; i++){
			
			Vertex v = graph.vertices[i];
			PartialTree T = new PartialTree(v); 
			v.parent = T.getRoot();
			Vertex.Neighbor ptr = v.neighbors;
			while(ptr != null){
				T.getArcs().insert(new PartialTree.Arc(v, ptr.vertex,ptr.weight)); 
				ptr = ptr.next;
			}
			
				L.append(T);
		}	
		
		
		return L;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param graph Graph for which MST is to be found
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(Graph graph, PartialTreeList ptlist) {
		
		/* COMPLETE THIS METHOD */
		
		ArrayList<PartialTree.Arc> result = new ArrayList<PartialTree.Arc>();
		
		while(ptlist.size() > 1){

			PartialTree PTX = ptlist.remove();
			
			if(PTX == null){
				break;
			}
			MinHeap<PartialTree.Arc> PQX = PTX.getArcs();
			PartialTree.Arc a = PQX.deleteMin();
			Vertex v1 = PTX.getRoot();
			Vertex v2 = a.v2;
			
			if(v1==v2 || v1==v2.parent){
				a = PQX.deleteMin();
				v2 = a.v2.parent;
			}
			result.add(a);
			PartialTree PTY = ptlist.removeTreeContaining(v2);
			if(PTY == null){
				continue;
			}
			PTY.getRoot().parent = PTX.getRoot();
			PTY.merge(PTX);
			ptlist.append(PTY);
		
		}
		//PTX.merge(x);

		return result;
	}
}
