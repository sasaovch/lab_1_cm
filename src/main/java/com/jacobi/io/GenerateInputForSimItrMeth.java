package com.jacobi.io;

import java.io.IOException;

import com.jacobi.entity.Matrix;
import com.jacobi.entity.SimpleItrMethodSystemParam;
import com.jacobi.entity.SystemParameters;

public class GenerateInputForSimItrMeth implements InputForSolveSystemInt {
    private IOStream stream;
    public GenerateInputForSimItrMeth(IOStream stream) {
        this.stream = stream;
    }

    private static final String ENTER_DIMENSION = "Enter dimension";
    private static final String ENTER_INTEGER = "Please enter integer";
    private static final String ENTER_DOUBLE = "Please enter double";
    private static final String ENTER_ACCURACY = "Enter accuracy";

    @Override
    public SystemParameters readInput() throws IOException {
        
        stream.writelnAndFlush(ENTER_DIMENSION);
        int dimension;
        dimension = parseIntFromInput();
        Matrix matrix = new Matrix(dimension, dimension);
        double[][] array = new double[dimension][dimension];
        double[] b = new double[dimension];
        for(int i = 0; i < dimension; i++) {
            for(int j = 0; j < dimension; j++) {
                if (i == j) {
                    array[i][j] = Math.random() * (dimension + 1);
                } else {
                    array[i][j] = Math.random();
                }
            }
            b[i] = Math.random() * 10;
        }
        matrix.setArray(array);
        stream.writelnAndFlush(ENTER_ACCURACY);
        double accuracy = parseDoubleFromInput();
        printMatrix(matrix, b);
        return new SimpleItrMethodSystemParam(matrix, b, accuracy, dimension);
    }

    private void printMatrix(Matrix matrix, double[] b) throws IOException {
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                stream.write(matrix.getElement(j, i) + " ");
                stream.flush();
            }
            stream.writelnAndFlush("\n");
        }
        for (int j = 0; j < matrix.getColumns(); j++) {
            stream.write(b[j] + " ");
            stream.flush();
        }
    }

    private int parseIntFromInput() throws IOException {
        Integer num = stream.readInteger();
        while(num == null) {
            stream.writelnAndFlush(ENTER_INTEGER);
            num = stream.readInteger();
        }
        return num;
    }

    private double parseDoubleFromInput() throws IOException {
        Double num = stream.readDouble();
        while(num == null) {
            stream.writelnAndFlush(ENTER_DOUBLE);
            num = stream.readDouble();
        }
        return num;
    }
}
