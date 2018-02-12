package com.gmail.nikolay.zinin;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class Format {
    public void row(ArrayList<String> headArray, String gap) {
        String row = "";
        for (String s : headArray) {
            row += String.format(gap, s);
        }
        System.out.println(row);
    }
    public void rowNr(ArrayList<String> headArray, String gap) {
        String row = "";
        System.out.print("   ");
        for (String s : headArray) {
            row += String.format(gap, s);
        }
        System.out.println(row);
    }


    public void line(ArrayList<String> headArray) {
        String line = "--------------------";
        for (int i = 1; i < headArray.size(); i++) {
            line += line;
        }
        System.out.println(line);
    }

    public void printTable(ResultSet rs) {

        ResultSetMetaData rsmd = null;
        try {
            rsmd = rs.getMetaData();
            int columnCount = 0;
            columnCount = rsmd.getColumnCount();
            ArrayList<String> rowArray = new ArrayList<>();

            String gap = "%1$-30s";

            for (int i = 1; i <= columnCount; i++) {

                String name = rsmd.getColumnName(i);
                rowArray.add(name);
            }
            line(rowArray);

            rowNr(rowArray, gap);

            line(rowArray);
            int count=1;
            while (rs.next()) {
                rowArray.clear();

                for (int i = 1; i <= columnCount; i++) {
                    rowArray.add(rs.getString(i));
                }

                System.out.print(count++ +". ");
                new Format().row(rowArray, gap);
            }

            new Format().line(rowArray);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}