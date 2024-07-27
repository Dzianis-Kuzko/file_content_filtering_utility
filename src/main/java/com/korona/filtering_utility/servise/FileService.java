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

    private IFileWriterDao integerFileDao;
    private IFileWriterDao floatFileDao;
    private IFileWriterDao stringFileDao;
    private IFileReaderDao readFileDao;

    private String filePathForIntegers;
    private String filePathForFloats;
    private String filePathForStrings;

    public FileService() {
        this.readFileDao = new FileDao();
    }

    @Override
    public void filterData(List<String> inputFiles, boolean append) {

        for (String inputFile : inputFiles) {
            readFileDao.initializeReader(inputFile);
            String line;

            while ((line = readFileDao.readLine()) != null) {
                classifyAndWriteLine(line, append);
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

        filePathForIntegers = outputDir + prefix + DEFAULT_FILE_PATH_FOR_INTEGERS;
        filePathForFloats = outputDir + prefix + DEFAULT_FILE_PATH_FOR_FLOATS;
        filePathForStrings = outputDir + prefix + DEFAULT_FILE_PATH_FOR_STRINGS;

    }

    private void classifyAndWriteLine(String line, boolean append) {
        if (DataClassifier.isInteger(line)) {
            if (integerFileDao == null) {
                integerFileDao = new FileDao();
                integerFileDao.initializeWriter(filePathForIntegers, append);
            }
            integerFileDao.writeLine(line);

        } else if (DataClassifier.isFloat(line)) {
            if (floatFileDao == null) {
                floatFileDao = new FileDao();
                floatFileDao.initializeWriter(filePathForFloats, append);
            }

            floatFileDao.writeLine(line);
        } else {
            if (stringFileDao == null) {
                stringFileDao = new FileDao();
                stringFileDao.initializeWriter(filePathForStrings, append);
            }

            stringFileDao.writeLine(line);
        }
    }

    private void closeWriters() {
        if (integerFileDao != null) {
            integerFileDao.closeWriter();
        }

        if (floatFileDao != null) {
            floatFileDao.closeWriter();
        }

        if (stringFileDao != null) {
            stringFileDao.closeWriter();
        }
    }
}
