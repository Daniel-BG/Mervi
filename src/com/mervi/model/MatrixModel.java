package com.mervi.model;

import java.util.Random;

/**
 * Model that stores a color matrix (red, green, blue) of double values
 * @author Daniel
 *
 */
public class MatrixModel {
	
	private int rows, cols;
	private double[][] red, green, blue;
	
	public MatrixModel(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		
		this.red = randomMatrix(rows, cols);
		this.green = randomMatrix(rows, cols);
		this.blue = randomMatrix(rows, cols);
	}
	
	
	private double[][] randomMatrix(int rows, int cols) {
		double[][] res = new double[rows][cols];
		Random r = new Random();
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				res[i][j] = r.nextDouble();
			}
		}
		
		return res;
	}
	

	public double[][] getRed() {
		return red;
	}

	public double[][] getGreen() {
		return green;
	}

	public double[][] getBlue() {
		return blue;
	}


	public float getRows() {
		return this.rows;
	}


	public float getCols() {
		return this.cols;
	}

}
