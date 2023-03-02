package com.jacobi.entity;

public class ResultSolve {
    private double[] array;
    private String message;
    private double[] errorRates;
    private int iteration;

    public ResultSolve(){}
    public ResultSolve(String message) {
        this.message = message;
    }

    public ResultSolve(double[] array, String message) {
        this.array = array;
        this.message = message;
    }

    public ResultSolve(double[] array, String message, double[] errorRates, int iter) {
        this.array = array;
        this.message = message;
        this.errorRates = errorRates;
        this.iteration = iter;
    }

    public String getMessage() {
        return message;
    }

    public double[] getArray() {
        return array;
    }

    public double[] getErrorRates() {
        return errorRates;
    }
    
    public int getIteration() {
        return iteration;
    }
}
