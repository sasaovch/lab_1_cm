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
import com.jacobi.util.TextMessageUtil;

public class App {
    public static void main(String[] args) throws IOException {
        IOStream syOutputStream = new IOStream();
        if (args.length == 0) {
            syOutputStream.writelnAndFlush(TextMessageUtil.NO_PARAMETERS);
            return;
        }
        String type = args[0];
        SystemParameters systemParameters = null;
        switch (type) {
            case TextMessageUtil.F_FLAG:
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(args[1]))) {
                    syOutputStream.setReader(bufferedReader);
                    systemParameters = new FileInputForSimItrMeth(syOutputStream).readInput();
                    if (systemParameters == null) {
                        syOutputStream.writelnAndFlush(TextMessageUtil.INCORRECT_DATA_IN_FILE);
                        return;
                    }
                } catch (IOException e) {
                    syOutputStream.writelnAndFlush(TextMessageUtil.FAIL_OPEN_FILE + TextMessageUtil.WHITESPACE + args[1]);
                }
                break;
            case TextMessageUtil.I_FLAG:
                systemParameters = new UserInputForSimItrMeth(syOutputStream).readInput();
                break;
            case TextMessageUtil.G_FLAG:
                systemParameters = new GenerateInputForSimItrMeth(syOutputStream).readInput();
                break;
            default:
                syOutputStream.writelnAndFlush(TextMessageUtil.INCORRECT_PARAMETERS);
                return;
        }
        long startTime = System.currentTimeMillis();
        SimpleIterationMethodForLinearEqSystem solveSystem = new SimpleIterationMethodForLinearEqSystem();
        ResultSolve res = solveSystem.solveSystem(systemParameters);
        if (res.getArray() == null) {
            syOutputStream.writelnAndFlush(res.getMessage());
        } else {
            syOutputStream.writelnAndFlush(res.getMessage());
            syOutputStream.writelnAndFlush(TextMessageUtil.ITER_NUMBER_MESS + TextMessageUtil.WHITESPACE + res.getIteration());
            syOutputStream.writelnAndFlush(TextMessageUtil.ANSWER_MESSAGE);
            syOutputStream.writeArray(res.getArray());
            syOutputStream.flush();
            syOutputStream.writelnAndFlush(TextMessageUtil.ERROR_RATES_MESSAGE);
            syOutputStream.writeArray(res.getErrorRates());
            syOutputStream.flush();
        }
        long endTime = System.currentTimeMillis();
        syOutputStream.writelnAndFlush(TextMessageUtil.TIME_MESSAGE + TextMessageUtil.WHITESPACE + (endTime - startTime));
    }
}
