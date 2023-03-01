package com.jacobi.entity;

public class SimpleItrMethodSystemParam implements SystemParameters {
    private Matrix matrix;
    private double[] vector;
    private double accuracy;
    private int maxIter;

    public SimpleItrMethodSystemParam() {
    }

    public SimpleItrMethodSystemParam(Matrix matrix, double[] vector, double accuracy, int maxIter) {
        this.matrix = matrix;
        this.vector = vector;
        this.accuracy = accuracy;
        this.maxIter = maxIter;
    }

    public Matrix geMatrix() {
        return matrix;
    }
    public double[] getVector() {
        return vector;
    }
    public double getAccuracy() {
        return accuracy;
    }
    public int getMaxIter() {
        return maxIter;
    }
    public void setVector(double[] vec) {
        this.vector = vec;
    }
}
