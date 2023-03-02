package com.jacobi.io;

import java.io.IOException;

import com.jacobi.entity.Matrix;
import com.jacobi.entity.SimpleItrMethodSystemParam;
import com.jacobi.entity.SystemParameters;
import com.jacobi.util.TextMessageUtil;

public class UserInputForSimItrMeth implements InputForSolveSystemInt {
    private IOStream stream;

    public UserInputForSimItrMeth(IOStream stream) {
        this.stream = stream;
    }

    @Override
    public SystemParameters readInput() throws IOException {
        
        stream.writelnAndFlush(TextMessageUtil.ENTER_DIMENSION);
        Integer dimension = parseIntFromInput();
        Matrix matrix = new Matrix(dimension, dimension);
        
        stream.writelnAndFlush(TextMessageUtil.ENTER_ROWS);
        double[] b = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            double[] row = new double[dimension];
            boolean correctInput = false;
            while (!correctInput) {
                correctInput = true;
                String[] strs = stream.readLine().split(TextMessageUtil.WHITESPACE);
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
                    stream.writelnAndFlush(TextMessageUtil.NOT_ENOUGH_ELEMENTS);
                    correctInput = false;
                }
            }
        }

        stream.writelnAndFlush(TextMessageUtil.ENTER_ACCURACY);
        double accuracy = parseDoubleFromInput();
        stream.writelnAndFlush(TextMessageUtil.ENTER_MAX_ITERATIONS);
        int maxIter = parseIntFromInput();
        return new SimpleItrMethodSystemParam(matrix, b, accuracy, maxIter);
    }

    private boolean checkCorrectDoubleInput(Double num) throws IOException {
        if (num == null) {
            stream.writelnAndFlush(TextMessageUtil.ENTER_DOUBLE_ROW);
            return false;
        }
        return true;
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