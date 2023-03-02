package com.jacobi.solve_service;

import com.jacobi.entity.Matrix;
import com.jacobi.entity.ResultSolve;
import com.jacobi.entity.SimpleItrMethodSystemParam;
import com.jacobi.entity.SystemParameters;
import com.jacobi.util.TextMessageUtil;

public class SimpleIterationMethodForLinearEqSystem implements SolveSystemOfEquation {

    public ResultSolve solveSystem(SystemParameters systemParameters) {

        if (!(systemParameters instanceof SimpleItrMethodSystemParam)) {
            return new ResultSolve(TextMessageUtil.INCORRECT_SYSTPARAMETER_CLASS);
        }
        SimpleItrMethodSystemParam sPar = (SimpleItrMethodSystemParam) systemParameters;
        Matrix matrix = sPar.geMatrix();
        if (matrix.getRows() != matrix.getColumns()) {
            return new ResultSolve(TextMessageUtil.MATRIX_NOT_SQUARE);
        }
        if (!checkDiagonalPredominance(matrix) && !tryToMakeDiagonalPredominance(sPar)) {
            return new ResultSolve(TextMessageUtil.MATRIX_NOT_DIAG_PREDOMINANCE);
        }

        int n = matrix.getRows();
        double[] solution = new double[n];
        double err = 0;
        double[] errorRates = new double[n];

        // iterate to solve system
        for (int k = 0; k < sPar.getMaxIter(); k++) {
            double[] newIterationSol = countNewX(matrix, sPar.getVector(), solution);
            err = 0;
            // count error
            for (int i = 0; i < n; i++) {
                err = Math.max(Math.abs(newIterationSol[i] - solution[i]), err);
                errorRates[i] = Math.abs(newIterationSol[i] - solution[i]);
            }
            if (err < sPar.getAccuracy()) {
                return new ResultSolve(newIterationSol, TextMessageUtil.SUCCESS, errorRates, k); // Convergence achieved, return solution
            }
            // update solution vector
            solution = newIterationSol;
        }
        if (err > sPar.getAccuracy()) {
            return new ResultSolve(TextMessageUtil.FAIL_JACOBI_METHOD);
        }
        return new ResultSolve(solution, TextMessageUtil.SUCCESS, errorRates, sPar.getMaxIter());
    }

    private boolean checkDiagonalPredominance(Matrix matrix) {
        for (int i = 0; i < matrix.getRows(); i++) {
            double sumOfEl = 0;
            for (int j = 0; j < matrix.getColumns(); j++) {
                if (i != j) {
                    sumOfEl += Math.abs(matrix.getElement(i, j));
                }
            }
            if (Math.abs(matrix.getElement(i, i)) <= sumOfEl) {
                return false;
            }
        }
        return true;
    }

    private double[] countNewX(Matrix matrix, double[] b, double[] solution) {
        double[] xNew = new double[matrix.getRows()];
        for (int i = 0; i < matrix.getRows(); i++) {
            double sum = 0;

            for (int j = 0; j < matrix.getRows(); j++) {
                if (i != j) {
                    sum += matrix.getElement(i, j) * solution[j];
                }
            }

            xNew[i] = (b[i] - sum) / matrix.getElement(i, i);
        }
        return xNew;
    }

    private boolean tryToMakeDiagonalPredominance(SimpleItrMethodSystemParam sPar) {
        Matrix matrix = sPar.geMatrix();
        double[] b = sPar.getVector();
        for (int i = 0; i < matrix.getRows(); i++) {
            double min = Double.MAX_VALUE;
            int k = -1;
            for (int j = i + 1; j < matrix.getRows(); j++) {
                double sumOfRow = sumOfRowCount(matrix, j, 0, matrix.getRows());
                double sumAfterElement = sumOfRowCount(matrix, j, j, matrix.getRows());
                if (Math.abs(matrix.getElement(j, i)) >= sumOfRow - Math.abs(matrix.getElement(j, i))
                    && sumAfterElement <= min) {
                        min = sumAfterElement;
                        k = j;
                }
            }
            if (k != -1) {
                double[] newRow = matrix.getRow(i);
                matrix.setRow(i, matrix.getRow(k));
                matrix.setRow(k, newRow);
                double newEl = b[i];
                b[i] = b[k];
                b[k] = newEl;
            }
        }
        sPar.setVector(b);
        return checkDiagonalPredominance(matrix);
    }

    private double sumOfRowCount(Matrix matrix, int row, int start, int end) {
        double sum = 0;
        for (int i = start; i < end; i++) {
            sum += Math.abs(matrix.getElement(row, i));
        }
        return sum;
    }
}
