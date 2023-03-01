package com.jacobi.io;

import java.io.IOException;

import com.jacobi.entity.SystemParameters;

interface InputForSolveSystemInt {
    SystemParameters readInput() throws IOException;
}
