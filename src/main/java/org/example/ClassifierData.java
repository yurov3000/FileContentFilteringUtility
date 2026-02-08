package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class ClassifierData {
    private ArrayList<Long> intArr = new ArrayList<Long>();
    private ArrayList<String> strArr = new ArrayList<String>();
    private ArrayList<Double> doubleArr = new ArrayList<Double>();
    private int countObjectsStr = 0;
    private int countObjectsInt = 0;
    private int countObjectsFloat = 0;
    private double sumD = 0;
    private long sumI = 0;

    public void readAndSortData(ArrayList<String> filePath, ArrayList<String> flags, String pathOut, String prefix) {
        for (String file : filePath) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    try {
                        long num = Long.parseLong(line);
                        intArr.add(num);
                        countObjectsInt++;
                        sumI += num;
                    } catch (NumberFormatException e1) {
                        try {
                            double num = Double.parseDouble(line);
                            doubleArr.add(num);
                            countObjectsFloat++;
                            sumD += num;
                        } catch (NumberFormatException e2) {
                            strArr.add(line);
                            countObjectsStr++;
                        }
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        saveData(flags, pathOut, prefix);
    }

    private void saveData(ArrayList<String> flags, String pathOut, String prefix) {
        StringBuilder path = new StringBuilder();
        boolean rewrite = false;
        for (String flag : flags) {
            if (flag.equals("-p")) {
                rewrite = true;
            }
            if (flag.equals("-a")) {
                path.insert(0, prefix);
            }
            if (flag.equals("-o")) {
                if (pathOut.equals("")) {
                    path.insert(0, pathOut);
                } else {
                    path.insert(0, pathOut + "\\");
                }

            }
            if (flag.equals("-s") || flag.equals("-f")) {
                printStat(flags);
            }
        }

        try (FileWriter writer = new FileWriter(path + "string.txt", rewrite)) {
            for (String str : strArr) {
                writer.write(str + "\n");
                writer.flush();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        try (FileWriter writer = new FileWriter(path + "integer.txt", rewrite)) {
            for (Long integer : intArr) {
                writer.write(integer + "\n");
                writer.flush();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        try (FileWriter writer = new FileWriter(path + "float.txt", rewrite)) {
            for (Double doubleNum : doubleArr) {
                writer.write(doubleNum + "\n");
                writer.flush();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void printStat(ArrayList<String> flags) {
        for (String flag : flags) {
            if (flag.equals("-s")) {
                System.out.println("\nКраткая статистика:");
                System.out.println("Количество элементов типа String: " + countObjectsStr);
                System.out.println("Количество элементов типа Int: " + countObjectsInt);
                System.out.println("Количество элементов типа Float: " + countObjectsFloat + "\n");
            }
            if (flag.equals("-f")) {
                System.out.println("\nПолная статистика:");
                System.out.println("Количество элементов типа String: " + countObjectsStr);
                if (!strArr.isEmpty()) {
                    String shortest = strArr.getFirst();
                    String longest = strArr.getFirst();

                    for (String s : strArr) {
                        if (s.length() < shortest.length()) {
                            shortest = s;
                        }
                        if (s.length() > longest.length()) {
                            longest = s;
                        }
                    }
                    System.out.println("Самая короткая строка в файле: " + shortest);
                    System.out.println("Самая длинная строка в файле: " + longest + "\n");
                }

                System.out.println("Количество элементов типа Int: " + countObjectsInt);
                if (!intArr.isEmpty()) {
                    System.out.println("Минимальное: " + Collections.min(intArr) + "\nМаксимальное: " + Collections.max(intArr));
                    System.out.println("Cумма всех элементов: " + sumI);
                    System.out.println("Среднее значение по всем данным: " + (sumI / intArr.size()) + "\n");
                }

                System.out.println("Количество элементов типа Float: " + countObjectsFloat);
                if (!doubleArr.isEmpty()) {
                    System.out.println("Минимальное: " + Collections.min(doubleArr) + "\nМаксимальное: " + Collections.max(doubleArr));
                    System.out.println("Cумма всех элементов: " + sumD);
                    System.out.println("Среднее значение по всем данным: " + (sumD / doubleArr.size()) + "\n");
                }

            }
        }
    }
}

