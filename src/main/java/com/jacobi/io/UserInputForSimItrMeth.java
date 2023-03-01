package com.jacobi.io;

import java.io.IOException;

import com.jacobi.entity.Matrix;
import com.jacobi.entity.SimpleItrMethodSystemParam;
import com.jacobi.entity.SystemParameters;

public class UserInputForSimItrMeth implements InputForSolveSystemInt {
    private IOStream stream;
    public UserInputForSimItrMeth(IOStream stream) {
        this.stream = stream;
    }
    private static final String ENTER_DIMENSION = "Enter dimension";
    private static final String ENTER_INTEGER = "Please enter integer";
    private static final String ENTER_DOUBLE = "Please enter double";
    private static final String ENTER_ROWS = "Enter rows";
    private static final String ENTER_DOUBLE_ROW = "Please enter double row: x x x x";
    private static final String ENTER_ACCURACY = "Enter accuracy";
    private static final String NOT_ENOUGH_ELEMENTS = "Not enought elements";
    private static final String ENTER_MAX_ITERATIONS = "Enter maximum number of iterations";

    @Override
    public SystemParameters readInput() throws IOException {
        
        stream.writelnAndFlush(ENTER_DIMENSION);
        Integer dimension = parseIntFromInput();
        Matrix matrix = new Matrix(dimension, dimension);
        
        stream.writelnAndFlush(ENTER_ROWS);
        double[] b = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            double[] row = new double[dimension];
            boolean correctInput = false;
            while (!correctInput) {
                correctInput = true;
                String[] strs = stream.readLine().split(" ");
                try {
                    for (int j = 0; j < dimension; j++) {
                        Double el = stream.parseDouble(strs[j]);
                        correctInput = checkCorrectDoubleInput(el);
                        row[j] = el;
                    }
                    Double elVector = stream.parseDouble(strs[dimension]);
                    correctInput = checkCorrectDoubleInput(elVector);
                    b[i] = elVector;
                    matrix.setRow(i, row);
                } catch (ArrayIndexOutOfBoundsException e) {
                    stream.writelnAndFlush(NOT_ENOUGH_ELEMENTS);
                    correctInput = false;
                }
            }
        }

        stream.writelnAndFlush(ENTER_ACCURACY);
        double accuracy = parseDoubleFromInput();
        stream.writelnAndFlush(ENTER_MAX_ITERATIONS);
        int maxIter = parseIntFromInput();
        return new SimpleItrMethodSystemParam(matrix, b, accuracy, maxIter);
    }

    private boolean checkCorrectDoubleInput(Double num) throws IOException {
        if (num == null) {
            stream.writelnAndFlush(ENTER_DOUBLE_ROW);
            return false;
        }
        return true;
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