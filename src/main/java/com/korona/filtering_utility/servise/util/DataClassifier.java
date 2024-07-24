package com.korona.filtering_utility.servise;

public class DataClassifier {
    public boolean isInteger(String line) {
        try {
            Integer.parseInt(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
