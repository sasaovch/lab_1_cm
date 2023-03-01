package com.jacobi.solve_service;

import com.jacobi.entity.ResultSolve;
import com.jacobi.entity.SystemParameters;

public interface SolveSystemOfEquation {
    ResultSolve solveSystem(SystemParameters systemParameters);
}
