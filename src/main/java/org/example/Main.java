package org.example;

import java.io.*;
import java.util.ArrayList;

class Main {
    public static void main(String[] args) {
        ArrayList<String> filePath = new ArrayList<>();
        ArrayList<String> flags = new ArrayList<>();
        String prefix = "";
        String pathOut = "";

        int i = 0;
        while (i < args.length) {
            String arg = args[i];
            if (arg.endsWith(".txt")) {
                filePath.add(arg);
            } else if (arg.startsWith("-")) {
                flags.add(arg);
            } else if (arg.endsWith("-")) {
                prefix = arg;
            } else {
                pathOut = arg;
            }
            i++;
        }
        ClassifierData classifier = new ClassifierData();
        classifier.readAndSortData(filePath, flags, pathOut, prefix);
    }
}