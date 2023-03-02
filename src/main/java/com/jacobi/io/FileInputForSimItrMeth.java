package com.jacobi.io;

import java.io.IOException;

import com.jacobi.entity.Matrix;
import com.jacobi.entity.SimpleItrMethodSystemParam;
import com.jacobi.entity.SystemParameters;
import com.jacobi.util.TextMessageUtil;

public class FileInputForSimItrMeth implements InputForSolveSystemInt {
    private IOStream stream;
    public FileInputForSimItrMeth(IOStream stream) {
        this.stream = stream;
    }

    @Override
    public SystemParameters readInput() throws IOException {
        String[] strRow;
        Integer dimension = stream.readInteger();
        if (dimension == null) {
            return null;
        }
        Matrix matrix = new Matrix(dimension, dimension);
        double[] b = new double[dimension];

        for (int i = 0; i < dimension; i++) {
            double[] row = new double[dimension];
            strRow = stream.readLine().split(TextMessageUtil.WHITESPACE);
            for (int j = 0; j < dimension; j++) {
                Double elDouble = stream.parseDouble(strRow[j]);
                if (elDouble == null) {
                    return null;
                }
                row[j] = elDouble;
            }
            matrix.setRow(i, row);
            b[i] = stream.parseDouble(strRow[dimension]);
        }
    
        Double acDouble = stream.readDouble();
        if (acDouble == null) {
            return null;
        }
        int maxIter = stream.readInteger();
        return new SimpleItrMethodSystemParam(matrix, b, acDouble, maxIter);
    }
}
