package com.korona.filtering_utility.servise;

import com.korona.filtering_utility.dao.FileDao;
import com.korona.filtering_utility.dao.api.IFileReaderDao;
import com.korona.filtering_utility.dao.api.IFileWriterDao;
import com.korona.filtering_utility.servise.api.IFileService;
import com.korona.filtering_utility.servise.util.DataClassifier;

import java.util.List;

public class FileService implements IFileService {
    private static final String DEFAULT_FILE_PATH_FOR_INTEGERS = "integer.txt";
    private static final String DEFAULT_FILE_PATH_FOR_FLOATS = "floats.txt";
    private static final String DEFAULT_FILE_PATH_FOR_STRINGS = "strings.txt";

    private final IFileWriterDao integerFileDao;
    private final IFileWriterDao floatFileDao;
    private final IFileWriterDao stringFileDao;
    private final IFileReaderDao readFileDao;

    private String filePathForIntegers;
    private String filePathForFloats;
    private String filePathForStrings;

    public FileService() {
        this.integerFileDao = new FileDao();
        this.floatFileDao = new FileDao();
        this.stringFileDao = new FileDao();
        this.readFileDao = new FileDao();
    }

    @Override
    public void filterData(List<String> inputFiles, boolean append) {
        initializeWriters(append);

        for (String inputFile : inputFiles) {
            readFileDao.initializeReader(inputFile);
            String line;

            while ((line = readFileDao.readLine()) != null) {
                classifyAndWriteLine(line);
            }

            readFileDao.closeReader();
        }

        closeWriters();
    }

    @Override
    public void setFilePaths(String outputDir, String prefix) {
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

    private void classifyAndWriteLine(String line) {
        if (DataClassifier.isInteger(line)) {
            integerFileDao.writeLine(line);
        } else if (DataClassifier.isFloat(line)) {
            floatFileDao.writeLine(line);
        } else {
            stringFileDao.writeLine(line);
        }
    }

    private void initializeWriters(boolean append) {
        integerFileDao.initializeWriter(filePathForIntegers, append);
        floatFileDao.initializeWriter(filePathForFloats, append);
        stringFileDao.initializeWriter(filePathForStrings, append);
    }

    private void closeWriters() {
        integerFileDao.closeWriter();
        floatFileDao.closeWriter();
        stringFileDao.closeWriter();
    }
}
