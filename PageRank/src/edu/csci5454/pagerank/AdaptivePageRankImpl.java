package edu.csci5454.pagerank;
import java.util.HashMap;
import java.util.Map;

import edu.csci5454.graph.Graph;

public class AdaptivePageRankImpl {

	
	
		static double V = 5;
		static double dampingFactor = 1;
		static double convergenceThresold = 0.001;
		static double maxIterations = 1000;
		
		public static void main(String[] args) {
			Graph graph = new Graph();
			double oldPagerank[] = new double[(int)V+1];
			double newPagerank[] = new double[(int)V+1];
			Map<Integer, Integer> converged = new HashMap<>();
			graph.createGraph((int)V);
			
			//Initialize page rank of all pages
			for(int i=1; i<= V; i++){
				oldPagerank[i] = (1/V);
			}
			int steps =0;
			long startTime = System.nanoTime();
			while(steps < maxIterations && converged.size() < V){
				newPagerank = calculateAdaptivePagerank(graph, oldPagerank, converged);
				for(int i=1; i<= V; i++){
					if(!converged.containsKey(i)){
						if(Math.abs(oldPagerank[i] - newPagerank[i]) <= convergenceThresold){
							converged.put(i, i);
						}
					}
					System.out.print(newPagerank[i] + " ");
				}
				oldPagerank = newPagerank;
				System.out.println();
				steps++;
			}
			long endTime = System.nanoTime();
			System.out.println("Time taken for basic Page rank: " + (endTime - startTime));
			
		}

		private static double[] calculateAdaptivePagerank(Graph graph, double[] pagerank, Map<Integer, Integer> converged) {
			double totalIncomingPagerank = 0;
			double  newPageRank[] = new double[pagerank.length];
			int incomingnode = 0;
			for(int page=1; page <= V ; page++){
				if(!converged.containsKey(page)){
					for(int j=0; j < graph.getIncomingAdj()[page].size(); j++){
						 incomingnode = (int) graph.getIncomingAdj()[page].get(j);
						 totalIncomingPagerank  +=  (pagerank[incomingnode])/(double)(graph.getOutgoingAdj()[incomingnode].size());			
					}
					newPageRank[page] = ((1-dampingFactor) + dampingFactor*totalIncomingPagerank);
					totalIncomingPagerank = 0;
				}else{
					newPageRank[page] = pagerank[page];
				}
			}
			return newPageRank;
		}
		
	}


