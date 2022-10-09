package edu.uchicago.gerber._02arrays;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class P7_5 {

    String path;
    ArrayList<String> rows;

    public P7_5(String path) {
        this.path = path;
        this.rows = new ArrayList<>();
        this.open();
    }

    public void open() {
        try {
            File f = new File(this.path);
            Scanner s = new Scanner(f);

            while (s.hasNextLine()) {
                this.rows.add(s.nextLine());
            }

            s.close();
        } catch (FileNotFoundException e) {
            System.out.println("The CSV file does not exist.");
            System.exit(1);
        }
    }

    public int numberOfRows() {
        return this.rows.size();
    }

    public int numberOfFields(int row) {
        String str = this.rows.get(row);

        int fieldcount = 0;
        boolean inside_quotation = false;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '"') {
                inside_quotation = !inside_quotation;
            } else if (str.charAt(i) == ',' && !inside_quotation){
                fieldcount++;
            }
        }

        return fieldcount+1;
    }

    String field(int row, int column) {
        String str = this.rows.get(row);

        //Debug
//        System.out.println("HERE!");

        int column_index = 0;
        boolean inside_quotation = false;
        int i = 0;
        for (; i < str.length(); i++) {
            if (column_index == column) {
                break;
            }
            if (str.charAt(i) == '"') {
                inside_quotation = !inside_quotation;
            } else if (str.charAt(i) == ',' && !inside_quotation){
                column_index++;
            }
        }

        int start = i;

        for(; i < str.length(); i++) {
            if (str.charAt(i) == '"') {
                inside_quotation = !inside_quotation;
            } else if (str.charAt(i) == ',' && !inside_quotation){
                break;
            }
        }

        String pre_str = str.substring(start, i);

        //Debug
//        System.out.println(pre_str);

        if (pre_str.length() > 0) {
            if (pre_str.charAt(0) == '"') {

                pre_str = pre_str.substring(1, pre_str.length() - 1);

                //Debug
//                System.out.println(pre_str);

                for (int j = 0; j < pre_str.length(); j++) {
                    if (pre_str.charAt(j) == '"') {
                        pre_str = pre_str.substring(0,j) + pre_str.substring(j+1);
                    }
                }
            }
        }

        return pre_str;
    }

    public static void main(String[] args) {
        //Debug
//        P7_5 CSVReader = new P7_5("SampleCSVFile_119kb.csv");
//        System.out.println(CSVReader.numberOfRows());
//        System.out.println(CSVReader.numberOfFields(8));
//        System.out.println(CSVReader.field(38,3));
    }


}
