package com.korona.filtering_utility;


import com.korona.filtering_utility.dao.FileDao;
import com.korona.filtering_utility.servise.FileService;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("in1.txt");
        arrayList.add("in2.txt");
        FileService fileService = new FileService(arrayList);
        fileService.filterData();

    }
}
