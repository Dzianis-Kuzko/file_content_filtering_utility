package com.korona.filtering_utility.controller;

import com.korona.filtering_utility.servise.FileService;
import com.korona.filtering_utility.servise.api.IFileService;

import java.util.ArrayList;
import java.util.List;

public class ConsoleController {
    private final IFileService fileService;
    private String outputDir;
    private String prefix;
    private boolean append;
    private List<String> inputFiles = new ArrayList<>();

    public ConsoleController() {
        fileService = new FileService();
    }

    public void execute(String[] args) {
        processArguments(args);

        fileService.setFilePaths(outputDir, prefix);

        fileService.filterData(inputFiles, append);

    }

    private void processArguments(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    i++;
                    outputDir = args[i] + "/";
                    break;
                case "-p":
                    i++;
                    prefix = args[i];
                    break;
                case "-a":
                    append = true;
                    break;
                default:
                    inputFiles.add(args[i]);
                    break;
            }
        }
    }
}
