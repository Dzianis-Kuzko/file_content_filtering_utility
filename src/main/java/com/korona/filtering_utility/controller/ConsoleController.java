package com.korona.filtering_utility.controller;

import com.korona.filtering_utility.exeption.FileDaoException;
import com.korona.filtering_utility.servise.api.IFileService;
import com.korona.filtering_utility.view.api.IView;

import java.util.ArrayList;
import java.util.List;

public class ConsoleController {
    private final IFileService fileService;
    private final IView consoleView;
    private String outputDir;
    private String prefix;
    private boolean append;
    private List<String> inputFiles = new ArrayList<>();

    public ConsoleController(IFileService fileService, IView consoleView) {
        this.fileService = fileService;
        this.consoleView = consoleView;
    }

    public void execute(String[] args) {
       try {

           processArguments(args);

           fileService.setFilePaths(outputDir, prefix);

           fileService.filterData(inputFiles, append);

       }catch (FileDaoException e){
           handleException(e);
       }

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
    private void handleException(Exception e) {
        consoleView.displayError(e.getMessage(), e);
    }
}
