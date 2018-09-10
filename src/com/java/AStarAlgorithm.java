package com.java;

import java.util.PriorityQueue;

public class AStarAlgorithm {

	// Cost of Diagnoal and Vertical/Horizonatal 
	public static final int DIAGNOAL_COST = 14;
	public static final int V_H_COST = 10;
    
	// Cells of our grid 
	private Cell[][] grid;

	// Define PriorityQueue for Open Cells, the set of nodes to be evaluated 
	private PriorityQueue<Cell> openCells;
   
	// The set of nodes already evaluated 
	private boolean[][] closedCell;
   
	// Start Cell
	private int startI, startJ;

	// End Cell
	private int endI, endJ;

	
	public AStarAlgorithm(int width, int height, int si, int sj, int ei, int ej, int[][] blocks) {
		grid = new Cell[width][height];
		closedCell = new boolean[width][height];
		
		//We put Cells with Lowest cost in first
		openCells = new PriorityQueue<Cell>((Cell c1, Cell c2) -> {

			return c1.finalCost < c2.finalCost ? -1 : c1.finalCost > c2.finalCost ? 1 : 0;
		});

		startCell(si, sj);
		endCell(ei, ej);
        
		// init heuristic and cells
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = new Cell(i, j);
				grid[i][j].heuristicCost = Math.abs(i - endI) + Math.abs(j - endJ);
				grid[i][j].solution=false;

			}
		}

		grid[startI][startJ].finalCost = 0;
        
		// we put Non-Walkable( ~ ) on the path
		for (int i = 0; i < blocks.length; i++) {
			addBlockOnCell(blocks[i][0], blocks[i][1]);
		}
	}

	public void addBlockOnCell(int i, int j) {
		grid[i][j] = null;
	}

	public void startCell(int i, int j) {
		startI = i;
		startJ = j;
	}

	public void endCell(int i, int j) {
		endI = i;
		endJ = j;
	}

	public void updateCostIfNeeded(Cell current, Cell t, int cost) {
		if (t == null || closedCell[t.i][t.j])
			return;

		int tFinalCost = t.heuristicCost + cost;
		boolean isOpen = openCells.contains(t);

		if (!isOpen || tFinalCost < t.finalCost) {
			t.finalCost = tFinalCost;
			t.parent = current;

			if (!isOpen) {
				openCells.add(t);
			}
		}

	}

	public void process() {
		
		// add the start location to open list
		openCells.add(grid[startI][startJ]);
		Cell current;

		while (true) {
			current = openCells.poll();

			if (current == null)
				break;

			closedCell[current.i][current.j] = true;

			if (current.equals(grid[endI][endJ]))
				return;

			Cell t;

			if (current.i - 1 >= 0) {
				t = grid[current.i - 1][current.j];
				updateCostIfNeeded(current, t, current.finalCost + V_H_COST);

				if (current.j - 1 >= 0) {
					t = grid[current.i - 1][current.j - 1];
					updateCostIfNeeded(current, t, current.finalCost + DIAGNOAL_COST);
				}

				if (current.j + 1 < grid[0].length) {
					t = grid[current.i - 1][current.j + 1];
					updateCostIfNeeded(current, t, current.finalCost + DIAGNOAL_COST);
				}

			}

			if (current.j - 1 >= 0) {
				t = grid[current.i][current.j - 1];
				updateCostIfNeeded(current, t, current.finalCost + V_H_COST);
			}

			if (current.j + 1 < grid[0].length) {
				t = grid[current.i][current.j + 1];
				updateCostIfNeeded(current, t, current.finalCost + V_H_COST);
			}

			if (current.i + 1 < grid.length) {
				t = grid[current.i + 1][current.j];
				updateCostIfNeeded(current, t, current.finalCost + V_H_COST);

				if (current.j - 1 >= 0) {
					t = grid[current.i + 1][current.j - 1];
					updateCostIfNeeded(current, t, current.finalCost + DIAGNOAL_COST);
				}

				if (current.j + 1 < grid[0].length) {
					t = grid[current.i + 1][current.j + 1];
					updateCostIfNeeded(current, t, current.finalCost + DIAGNOAL_COST);
				}

			}

		}

	}

	public void display() {
		System.out.println("Grid :");

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (i == startI && j == startJ) {
					System.out.print("@ "); // Source Cell
				} else if (i == endI && j == endJ) {
					System.out.print("X "); // Destination Cell
				} else if (grid[i][j] != null) {
					System.out.printf("%-3d ", 0); // Walkable (.,@,X,*,^)Cell
				} else {

					System.out.print("~ "); // Non Walkable ( ~ ) Cell 
				}

			}

			System.out.println();

		}
		System.out.println();
	}

	

	public void displaySolution() {
		if (closedCell[endI][endJ]) {

			Cell current = grid[endI][endJ];

			grid[current.i][current.j].solution = true;

			while (current.parent != null) {

				grid[current.parent.i][current.parent.j].solution = true;
				current = current.parent;
			}

			System.out.println("\n");

			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					if (i == startI && j == startJ) {
						System.out.print("# "); // Best Path - Source
					} else if (i == endI && j == endJ) {
						System.out.print("# "); // Destination
					} else if (grid[i][j] != null) {
						System.out.printf("%-3s ", grid[i][j].solution ? "#" : "0");
					} else {

						System.out.print("~ "); // Non Walkable Cell
					}

				}

				System.out.println();

			}
			System.out.println();

		} else {

			System.out.println("---- No Possible Path ----");
		}
	}

}