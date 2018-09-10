package com.java;

public class AStarMain {

	public static void main(String[] args) {

		System.out.println("A Star Algorithm \n");

		AStarAlgorithm aStar = new AStarAlgorithm(5, 5, 0, 0, 4, 4,
				new int[][] { { 1, 0 }, { 1, 1 }, { 1, 3 }, { 3, 4 }, { 4, 0 }, { 4, 1 }, { 4, 3 }

				});

		System.out.println("----- Test Map ------\n");

		aStar.display();
		aStar.process();

		System.out.println("----- Test Map Solution ------");

		aStar.displaySolution();

	}

}
