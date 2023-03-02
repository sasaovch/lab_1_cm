package com.jacobi.entity;

public class Matrix {
    private final int rows;
    private final int columns;
    private double[][] array;

    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.array = new double[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public double[][] getArray() {
        return array;
    }

    public boolean setArray(double[][] matrix) {
        if (matrix.length == rows && matrix[0].length == columns) {
            array = matrix;
            return true;
        } else {
            return false;
        }
    }

    public void setElement(int row, int column, int value) {
        array[row][column] = value;
    }

    public void setRow(int rowNumber, double[] row) {
        array[rowNumber] = row;
    }

    public double[] getRow(int rowNumber) {
        return array[rowNumber];
    }

    public double getElement(int row, int column) {
        return array[row][column];
    }
}
