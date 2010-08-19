package com.gs.dbex.application.comps;

import java.util.Vector;

/**
 * 
 * @author sabuj.das
 *
 */

public class NamedVector extends Vector {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1325220182794276097L;
	private String name;

	public NamedVector(String name) {
		this.name = name;
	}

	public NamedVector(String name, Object elements[]) {
		this.name = name;
		for (int i = 0, n = elements.length; i < n; i++) {
			add(elements[i]);
		}
	}

	public String toString() {
		return "[" + name + "]";
	}
}
