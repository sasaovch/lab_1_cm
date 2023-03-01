package com.jacobi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.jacobi.entity.ResultSolve;
import com.jacobi.entity.SystemParameters;
import com.jacobi.io.FileInputForSimItrMeth;
import com.jacobi.io.GenerateInputForSimItrMeth;
import com.jacobi.io.IOStream;
import com.jacobi.io.UserInputForSimItrMeth;
import com.jacobi.solve_service.SimpleIterationMethodForLinearEqSystem;

public class App {
    private static final String INCORRECT_PARAMETERS = "Incorrect arguments. Please enter -[f,i,g] for file/input/generate";
    private static final String NO_PARAMETERS = "No arguments. Please enter -[f,i,g] for file/input/generate";
    private static final String FAIL_OPEN_FILE = "Can not open file with name";
    private static final String INCORRECT_DATA_IN_FILE = "Incorrect data in file";
    private static final String ANSWER_MESSAGE = "Answer is";
    private static final String ERROR_RATES_MESSAGE = "Error rates";
    private static final String ITER_NUMBER_MESS = "Number of iterations:";
    public static void main(String[] args) throws IOException {
        IOStream syOutputStream = new IOStream();
        if (args.length == 0) {
            syOutputStream.writelnAndFlush(NO_PARAMETERS);
        }
        String type = args[0];
        SystemParameters systemParameters = null;
        switch (type) {
            case "-f":
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(args[1]))) {
                    syOutputStream.setReader(bufferedReader);
                    systemParameters = new FileInputForSimItrMeth(syOutputStream).readInput();
                    if (systemParameters == null) {
                        syOutputStream.writelnAndFlush(INCORRECT_DATA_IN_FILE);
                        return;
                    }
                } catch (IOException e) {
                    syOutputStream.writelnAndFlush(FAIL_OPEN_FILE + " " + args[1]);
                }
                break;
            case "-i":
                systemParameters = new UserInputForSimItrMeth(syOutputStream).readInput();
                break;
            case "-g":
                systemParameters = new GenerateInputForSimItrMeth(syOutputStream).readInput();
                break;
            default:
                syOutputStream.writelnAndFlush(INCORRECT_PARAMETERS);
                return;
        }
        SimpleIterationMethodForLinearEqSystem solveSystem = new SimpleIterationMethodForLinearEqSystem();
        ResultSolve res = solveSystem.solveSystem(systemParameters);
        if (res.getArray() == null) {
            syOutputStream.writelnAndFlush(res.getMessage());
        } else {
            syOutputStream.writelnAndFlush(res.getMessage());
            syOutputStream.writelnAndFlush(ITER_NUMBER_MESS + " " + res.getIteration());
            syOutputStream.writelnAndFlush(ANSWER_MESSAGE);
            syOutputStream.writeArray(res.getArray());
            syOutputStream.flush();
            syOutputStream.writelnAndFlush(ERROR_RATES_MESSAGE);
            syOutputStream.writeArray(res.getErrorRates());
            syOutputStream.flush();
        }
    }
}
