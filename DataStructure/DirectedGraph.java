package com.framework.db.api.imp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.framework.db.api.interfaces.Graph;


public class DirectedGraph<E extends Comparable<E>> implements Graph<E> {
	
	Map<E, Node> vertices = new HashMap<E, Node>();
	private int vertexCount;
	private int edgeCount;
	
	@Override
	public int verticesCount() {
		return vertexCount;
	}

	@Override
	public int edgesCount() {
		return edgeCount;
	}
	
	@Override
	public void addVertex(E e) {
		if (e == null) {
			throw new NullPointerException("key must not be null.");
		}
		if (!vertices.containsKey(e)) {
			Node newNode = new Node(e);
			vertices.put(e, newNode);
			++vertexCount;
		}
	}
	
	@Override
	public void addEdge(E from, E to) {
		addVertex(from);
		addVertex(to);

		Node fromVertex = vertices.get(from);
		Node toVertex = vertices.get(to);
		fromVertex.addNeighbor(toVertex);
		++edgeCount;
	}
	
	@Override
	public Set<E> outwardEdges(E from) {
		Node origin = vertices.get(from);
		return origin.neighbors.keySet();
	}

	@Override
	public Collection<Set<E>> outwardEdges() {
		Collection<Set<E>> collection = new ArrayList<Set<E>>();
		for (E vertex : vertices.keySet()) {
			collection.add(outwardEdges(vertex));
		}
		return collection;
	}

	@Override
	public Map<E, Set<E>> edges() {
		Map<E, Set<E>> edges = new HashMap<E, Set<E>>();
		for (E vertex : vertices.keySet()) {
			edges.put(vertex, outwardEdges(vertex));
		}
		return edges;
	}

	@Override
	public Set<E> vertices() {
		return vertices.keySet();
	}
	
	/*
	 *  returns all the vertices in topological order of dependency
	 *  use Depth-First-Search algorithm
	 */
	public Iterable<E> topologicalVertices() {
		List<E> topologicalList = new ArrayList<E>();
		for (E vertex : vertices.keySet()) {
			Node vertexNode = vertices.get(vertex);
			if (vertexNode.isUntouched()) { 
				vertexNode.visited();
				topologicalOrder(vertex, topologicalList);
			}
		}
		unvisitAllNodes();
		return topologicalList;
	}
	
	private void unvisitAllNodes() {
		for (Node vertexNode : vertices.values()) {
			vertexNode.untouched();
		}
	}
	
	private void topologicalOrder(E vertex, List<E> topoList) {
		Set<E> neighbors = vertices.get(vertex).neighbors.keySet();
		for (E neighbor : neighbors) {
			Node neighborNode = vertices.get(neighbor);
			if (neighborNode.isUntouched()) {
				neighborNode.visited();
				topologicalOrder(neighbor, topoList);
			} else if (neighborNode.isVisited()) {
				throw new RuntimeException("Dependency graph cannot be a cyclic graph. Error occur at Node " + neighbor.toString());
			}
		}
		vertices.get(vertex).processed();
		topoList.add(vertex);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (E vertex : vertices()) {
			sb.append(vertex.toString() + ":\t ");
			for (E edge : outwardEdges(vertex)) {
				sb.append(edge.toString() + " -> ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	

	private class Node {
		private static final byte UNVISITED = 0;
		private static final byte PROCESSING = 1;
		private static final byte DONE = 2;
		
		private E data;
		private Map<E, Node> neighbors;
		private int status;
		
		public Node(E data) {
			this.data = data;
			neighbors = new HashMap<E, Node>();
		}
		
		public void addNeighbor(Node neighbor) {
			if (!neighbors.containsKey(neighbor.data)) {
				neighbors.put(neighbor.data, neighbor);
			}
		}

		public void processed() {
			this.status = DONE;
		}

		public void visited() {
			this.status = PROCESSING;
		}

		public void untouched() {
			this.status = UNVISITED;
		}
		
		public boolean isProcessed() {
			return status == DONE;
		}
		
		public boolean isUntouched() {
			return status == UNVISITED;
		}
		
		public boolean isVisited() {
			return status == PROCESSING;
		}
	}
	
}