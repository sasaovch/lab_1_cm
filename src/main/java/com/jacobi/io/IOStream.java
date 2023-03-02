package com.jacobi.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.jacobi.util.TextMessageUtil;

public class IOStream {
    private BufferedReader reader;
    private BufferedWriter writer;

    public IOStream(){
        reader = new BufferedReader(new InputStreamReader(System.in));
        writer = new BufferedWriter(new OutputStreamWriter(System.out));
    }

    public void writeln(String str) throws IOException {
        writer.write(str);
        writer.newLine();
    }

    public void write(String str) throws IOException {
        writer.write(str);
    }

    public void writeArray(double[] array) throws IOException {
        for (int i = 0; i < array.length; i++) {
            write(array[i] + TextMessageUtil.NEW_LINE);
        }
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

    public void setReader(BufferedReader rd) {
        reader = rd;
    }

    public void setWriter(BufferedWriter wr) {
        writer = wr;
    }

    public void flush() throws IOException {
        writer.flush();
    }

    public void writelnAndFlush(String str) throws IOException {
        writeln(str);
        flush();
    }

    public Double readDouble() {
        try {
            return parseDouble(readLine());
        } catch (IOException e) {
            return null;
        }
    }

    public Integer readInteger() {
        try {
            return Integer.parseInt(readLine());
        } catch (NumberFormatException | IOException e) {
            return null;
        }
    }

    public Double parseDouble(String str) {
        try {
            if (str.contains(",")) {
                return Double.parseDouble(str.replace(',', '.'));
            }
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
