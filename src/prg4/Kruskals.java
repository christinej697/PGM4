/*
 * Name: Christine Johnson
 * Date: 6/4/19
 * Program Overview: Use Kruskal's Algorithm print to output.txt the MST for an inputed adjacency list
 * Comments: This program assumes that each vertex will have no more than 4 edges connected to it
 */

package prg4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Kruskals {
	
	public static Edge[] visited;                                 //an array to hold visited edges
	public static Vert[] visitedVerts;                          //an array to hold visited vertices
	public static ArrayList<Edge> edges = new ArrayList<Edge>();	//arraylist of all the edges in graph (contains their vertices and weights)
	
	Kruskals(){
		
	}
	
	public static void main(String[] args) {

		StringBuilder str = new StringBuilder();
		
		//store input file info as a string
		try{
			Scanner sc = new Scanner(new File("input.txt"));
			while (sc.hasNextLine()) {
				str.append(sc.next());
			}
			
			sc.close();
		}catch(FileNotFoundException exc) {
			System.out.println("File not found");
		}
		
		String[] arr = str.toString().split(":");     //Separate adjacency list string into node groups
		String[] newarr;                              //Used to access info in the node groups
		
		for (int i = 0; i<arr.length; i++) {
			newarr = arr[i].split(",");
			
			//Store all the edges of the adjacency list into the arraylist "edges"
			for(int j=0; j < newarr.length; j++) {
				//check if has 1 edge
				if (j==2) {
					edges.add(new Edge(new Vert(newarr[0],false),new Vert(newarr[1],false),Integer.parseInt(newarr[2])));
				}
				//check if has 2 edge
				if (j==4) {
					edges.add(new Edge(new Vert(newarr[0],false),new Vert(newarr[3],false),Integer.parseInt(newarr[4])));
				}
				//check if has 3 edge
				if (j==6) {
					edges.add(new Edge(new Vert(newarr[0],false),new Vert(newarr[5],false),Integer.parseInt(newarr[6])));
				}
				//check if has 4 edge
				if (j==8) {
					edges.add(new Edge(new Vert(newarr[0],false),new Vert(newarr[7],false),Integer.parseInt(newarr[8])));
				}
			}
		}
		
		visitedVerts = new Vert[arr.length*2];
		visited = new Edge[arr.length-1];
		
		//Add all the smallest edges to unvisited vertices to the visited edges array
		int iter = arr.length-1;
		int n = 0;
		//reset visited index iterator if there is an minimum edge that did not visit any new vertices, remove that edge
		for(int m = 0; m < iter; m++) {
			int min = addEdge(edges,visitedVerts);
			if(min<0) {
				m--;
				edges.remove(min);	
			}
			//if the min edge had unvisited vertices, store it for the MST and mark the vertex as visited
			else {
			visited[m] = edges.get(min);
			edges.get(min).vert1.visited = true;
			edges.get(min).vert2.visited = true;
			//check all repetitions of those vertices as visited
			for(int d = 0; d < edges.size(); d++) {
				if(edges.get(d).vert1.name.equals(edges.get(min).vert1.name)||edges.get(d).vert1.name.equals(edges.get(min).vert2.name)) {
					edges.get(d).vert1.visited = true;
				}
				else if(edges.get(d).vert2.name.equals(edges.get(min).vert1.name)||edges.get(d).vert2.name.equals(edges.get(min).vert2.name)){
					edges.get(d).vert2.visited = true;
				}
			}
			//add those vertices to the visited vertex list
			visitedVerts[n]=edges.get(min).vert1;
			visitedVerts[n+1]=edges.get(min).vert2;
			n+=2;
			edges.remove(findDup(edges,edges.get(min)));   //remove any duplicates of edge (ex, AB is the same as BA)
			edges.remove(min);								//remove the edge added from the edge list so can find other minimums
			}
		}
		
		try {
			PrintWriter outputFile = new PrintWriter(new FileWriter("output.txt"));
			outputFile.println("Edge    Weight");
			for(int k = 0; k < visited.length; k++) {
				outputFile.println(visited[k].vert1.name+ " - " + visited[k].vert2.name+ "     " +visited[k].w);
			}
			outputFile.close();
		}catch(IOException e) {
			System.out.println("Problem finding the file for output");
		}
		
		
	}
	
	//Find the smallest edge and check if it connects to any new vertices
	public static int addEdge(ArrayList<Edge> edges,Vert[] verts) {

		int minIndex = 0;                                    //store index of smallest edge
		for (int l =0; l < edges.size(); l++){
			if(edges.get(l).w < edges.get(minIndex).w) {
				
				for(int u=0; u < verts.length; u++) {
					//check if vert1 has already been visited, if so mark visited
					if(verts[u] != null && edges.get(l).vert1.name.equals(verts[u].name)) {
						edges.get(l).vert1.visited = true;
					}
					//check if vert2 has already been visited, if so mark visited
					if(verts[u] != null && edges.get(l).vert2.name.equals(verts[u].name)) {
						edges.get(l).vert2.visited = true;
					}
					//if both the vertices were visited, the edge can't be used, return -1
					if(edges.get(l).vert1.visited == true && edges.get(l).vert2.visited == true) {
						minIndex = -1;
					}
					//the edge can be added, set minIndex to its index
					else {
						minIndex = l;
					}
				}
			}
		}
		return minIndex;
	}
	
	//Find the duplicate pair of the minimum edge so it can be deleted (for example, BA is a duplicate of AB)
	public static int findDup(ArrayList<Edge> edge, Edge pair) {
		int dup = 0;
		for (int y = 0; y < edge.size(); y++) {
			if(edge.get(y).vert1.name.equals(pair.vert2.name) == true && edge.get(y).vert2.name.equals(pair.vert1.name) == true) {
				dup=y;
			}
		}
		return dup;
	}

	public static class Edge{
		private Vert vert1;
		private Vert vert2;
		private int w;         //wieght of edge
		
		Edge(Vert vert1, Vert vert2, int w) {
			this.vert1 = vert1;
			this.vert2 = vert2;
			this.w = w;
		}
	}
	public static class Vert{
		private String name;
		private boolean visited;
		
		Vert(String name, boolean visited){
			this.name = name;
			this.visited = visited;
		}
	}
}