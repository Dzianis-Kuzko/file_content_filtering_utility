package com.korona.filtering_utility.servise;

import com.korona.filtering_utility.dao.FileDao;
import com.korona.filtering_utility.servise.util.DataClassifier;

import java.util.List;

public class FileService {
    private final static String DEFAULT_FILE_PATH_FOR_INTEGERS = "integer.txt";
    private final static String DEFAULT_FILE_PATH_FOR_FLOATS = "floats.txt";
    private final static String DEFAULT_FILE_PATH_FOR_STRINGS = "strings.txt";
    private List<String> inputFiles;
    private String filePathForIntegers;
    private String filePathForFloats;
    private String filePathForStrings;


    public void filterData(List<String> inputFiles, String outputDir, String prefix, boolean append) {
        setFilePaths(outputDir, prefix);


        FileDao fileDaoForWriteInteger = new FileDao();
        fileDaoForWriteInteger.initializeWriter(filePathForIntegers);
        FileDao fileDaoForWriterFloat = new FileDao();
        fileDaoForWriterFloat.initializeWriter(filePathForFloats);
        FileDao fileDaoForWriterString = new FileDao();
        fileDaoForWriterString.initializeWriter(filePathForStrings);

        for (String inputFile : inputFiles) {
            FileDao fileDaoForRead = new FileDao();
            fileDaoForRead.initializeReader(inputFile);

            String line;

            while ((line = fileDaoForRead.readNextLine()) != null) {
                if (DataClassifier.isInteger(line) == true) {
                    fileDaoForWriteInteger.writeLine(line);

                } else if (DataClassifier.isFloat(line) == true) {
                    fileDaoForWriterFloat.writeLine(line);

                } else {
                    fileDaoForWriterString.writeLine(line);
                }
            }

            fileDaoForRead.closeWriter();

        }

        fileDaoForWriteInteger.closeWriter();
        fileDaoForWriterString.closeWriter();
        fileDaoForWriterFloat.closeWriter();

    }

    private void setFilePaths(String outputDir, String prefix) {
        if (outputDir == null) {
            outputDir = "";
        }
        if (prefix == null) {
            prefix = "";
        }

        filePathForIntegers = outputDir + "/" + prefix + DEFAULT_FILE_PATH_FOR_INTEGERS;
        filePathForFloats = outputDir + "/" + prefix + DEFAULT_FILE_PATH_FOR_FLOATS;
        filePathForStrings = outputDir + "/" + prefix + DEFAULT_FILE_PATH_FOR_STRINGS;

    }
}
