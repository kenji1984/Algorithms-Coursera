package com.framework.db.api.interfaces;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface Graph<E> {
	
	/*
	 *  returns the total number of vertices
	 */
	int verticesCount();
	
	/*
	 *  returns the total number of edges
	 */
	int edgesCount();
	
	/*
	 * 	add a vertex to the graph
	 */
	void addVertex(E e);
	
	/*
	 *  add an edge between 2 vertices
	 */
	void addEdge(E from, E to);
	
	/*
	 *  returns all the edge end points with e as starting point
	 */
	Set<E> outwardEdges(E from);
	
	/*
	 *  returns all the edge points in the whole graph
	 */
	Collection<Set<E>> outwardEdges();
	
	/*
	 *  returns all the key value pairs of <starting vertex, end vertices> of the graph
	 */
	Map<E, Set<E>> edges();
	
	/*
	 * 	returns an Iterable of key in the natural order (order depends on the internal data structure used)
	 */
	Set<E> vertices();
	
}