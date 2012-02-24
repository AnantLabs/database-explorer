/* ******************************************************************************
 * 	
 * 	Name	: DatabaseGraph.java
 * 	Type	: com.gs.dbex.design.model.DatabaseGraph
 * 
 * 	Created	: Jan 30, 2012
 * 	
 * 	Author	: Sabuj Das [ mailto::sabuj.das@gmail.com ]
 * 
 * -----------------------------------------------------------------------------*
 * 																				*
 * Copyright © Sabuj Das 2010 All Rights Reserved. 								*
 * <br/>No part of this document may be reproduced without written 				*
 * consent from the author.														*
 * 																				*
 ****************************************************************************** */

package com.gs.dbex.design.model;

import java.awt.Rectangle;
import java.lang.reflect.Array;
import java.util.List;

import com.gs.dbex.model.BaseDbModel;

/**
 * @author sabuj.das
 * @MailTo sabuj.das@gmail.com
 * 
 */
public class DatabaseGraph<T extends BaseDbModel> {

	public static final int DEFAULT_CAPACITY = 10;
	
	
	private Rectangle bound;
	private T[] vertices;
	private int numVertices;
    private List<List<T>> adjacencyList;
    
    /**
	 * 
	 */
	public DatabaseGraph() {
		// TODO Auto-generated constructor stub
	}
	
	public DatabaseGraph(List<T> data) {
		populateGraph(data);
	}

	public Rectangle getBound() {
		return bound;
	}

	public void setBound(Rectangle bound) {
		this.bound = bound;
	}

	public T[] getVertices() {
		return vertices;
	}

	public void setVertices(T[] vertices) {
		this.vertices = vertices;
	}

	public int getNumVertices() {
		return numVertices;
	}

	public void setNumVertices(int numVertices) {
		this.numVertices = numVertices;
	}

	public List<List<T>> getAdjacencyList() {
		return adjacencyList;
	}

	public void setAdjacencyList(List<List<T>> adjacencyList) {
		this.adjacencyList = adjacencyList;
	}
	
	
	protected void expandCapacity(int loadFactor) {
		if(null == vertices || vertices.length <= 0){
			return;
		}
        T[] largerVertices = (T[]) Array.newInstance(vertices[0].getClass(), vertices.length * loadFactor);//new T[vertices.length * loadFactor];
        for (int i = 0; i < numVertices; i++) {
            largerVertices[i] = vertices[i];
        }
        vertices = largerVertices;
    }
	
	/**
	 * @param data
	 */
	private void populateGraph(List<T> data) {
		
	}
	
	
}
