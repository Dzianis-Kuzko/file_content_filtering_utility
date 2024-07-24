package com.korona.filtering_utility.servise;

import com.korona.filtering_utility.dao.FileDao;
import com.korona.filtering_utility.servise.util.DataClassifier;

import java.util.List;

public class FileService {
    private List<String> inputFiles;

    public FileService(List<String> inputFiles) {
        this.inputFiles = inputFiles;
    }

    public void filterData(){

        FileDao fileDaoForWriteInteger = new FileDao();
        fileDaoForWriteInteger.initializeWriter("./integer.txt");
        FileDao fileDaoForWriterFloat = new FileDao();
        fileDaoForWriterFloat.initializeWriter("./float.txt");
        FileDao fileDaoForWriterString = new FileDao();
        fileDaoForWriterString.initializeWriter("./string.txt");

       for (String inputFile: inputFiles){
           FileDao fileDaoForRead = new FileDao();
           fileDaoForRead.initializeReader(inputFile);

           String line;

           while ((line=fileDaoForRead.readNextLine()) !=null){
               if(DataClassifier.isInteger(line)== true){
                   fileDaoForWriteInteger.writeLine(line);

               } else if (DataClassifier.isFloat(line) == true) {
                   fileDaoForWriterFloat.writeLine(line);

               }else {
                   fileDaoForWriterString.writeLine(line);
               }
           }

           fileDaoForRead.closeWriter();

       }

       fileDaoForWriteInteger.closeWriter();
       fileDaoForWriterString.closeWriter();
       fileDaoForWriterFloat.closeWriter();

    }
}
