package com.jacobi.io;

import java.io.IOException;

import com.jacobi.entity.Matrix;
import com.jacobi.entity.SimpleItrMethodSystemParam;
import com.jacobi.entity.SystemParameters;
import com.jacobi.util.TextMessageUtil;

public class GenerateInputForSimItrMeth implements InputForSolveSystemInt {
    private IOStream stream;

    public GenerateInputForSimItrMeth(IOStream stream) {
        this.stream = stream;
    }

    @Override
    public SystemParameters readInput() throws IOException {
        
        stream.writelnAndFlush(TextMessageUtil.ENTER_DIMENSION);
        int dimension;
        dimension = parseIntFromInput();
        Matrix matrix = new Matrix(dimension, dimension);
        double[][] array = new double[dimension][dimension];
        double[] b = new double[dimension];
        for(int i = 0; i < dimension; i++) {
            double absSumRow = 0;
            for(int j = 0; j < dimension; j++) {
                if (i != j) {
                    array[i][j] = Math.random();
                }
                absSumRow += Math.abs(array[i][j]);
            }
            array[i][i] = absSumRow + Math.random();
            b[i] = Math.random() * 10;
        }
        matrix.setArray(array);
        stream.writelnAndFlush(TextMessageUtil.ENTER_ACCURACY);
        double accuracy = parseDoubleFromInput();
        stream.writelnAndFlush(TextMessageUtil.ENTER_MAX_ITERATIONS);
        int maxIter = parseIntFromInput();
        printMatrix(matrix, b);
        return new SimpleItrMethodSystemParam(matrix, b, accuracy, maxIter);
    }

    private void printMatrix(Matrix matrix, double[] b) throws IOException {
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                stream.write(matrix.getElement(j, i) + TextMessageUtil.WHITESPACE);
                stream.flush();
            }
            stream.writelnAndFlush(TextMessageUtil.NEW_LINE);
        }
        for (int j = 0; j < matrix.getColumns(); j++) {
            stream.write(b[j] + TextMessageUtil.WHITESPACE);
            stream.flush();
        }
        stream.writelnAndFlush(TextMessageUtil.NEW_LINE);
    }

    private int parseIntFromInput() throws IOException {
        Integer num = stream.readInteger();
        while(num == null) {
            stream.writelnAndFlush(TextMessageUtil.ENTER_INTEGER);
            num = stream.readInteger();
        }
        return num;
    }

    private double parseDoubleFromInput() throws IOException {
        Double num = stream.readDouble();
        while(num == null) {
            stream.writelnAndFlush(TextMessageUtil.ENTER_DOUBLE);
            num = stream.readDouble();
        }
        return num;
    }
}
