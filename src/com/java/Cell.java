package com.java;

// Define Cell for Grid
public class Cell {

	// Coordinates of the Cell
	public int i, j;

	// Parent Cell for path
	public Cell parent;

	// Heuristic Cost of Current Cell
	public int heuristicCost;

	// Final Cost, G+H with
	public int finalCost;
	// G(n) the cost of the path from start Node to n
	// H(n) the heuristicCost, estimates the cost of the cheapest path from n to
	// the Destination

	public boolean solution; // If cell is part of the Solution path

	public Cell(int i, int j) {
		this.i = i;
		this.j = j;
	}

	@Override
	public String toString() {
		return "[ " + i + "," + j + " ]";
	}

}
