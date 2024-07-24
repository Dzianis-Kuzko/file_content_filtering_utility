package com.korona.filtering_utility.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class FileDao {
    private BufferedWriter writer;
    private BufferedReader reader;
    private Iterator<String> lineIterator;

    public FileDao() {
    }

    public void initializeReader(String filePath) {
        try {
            reader = new BufferedReader(new FileReader(filePath));
            lineIterator = reader.lines().iterator();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeWriter(String filePath) {
        try {
            writer = new BufferedWriter(new FileWriter(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readNextLine() {
        if (lineIterator != null && lineIterator.hasNext()) {
            return lineIterator.next();
        }
        return null; // Возвращаем null, если больше нет строк для чтения
    }

    public void writeLine(String line) {
        if (writer != null) {
            try {
                writer.write(line);
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeReader() {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeWriter() {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
