package com.gmail.nikolay.zinin;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Question5 {
    public void run() {

        System.out.println("TopProducts:");

        System.out.println("Type in start datum");
        Scanner scanner = new Scanner(System.in);
        String startDate = scanner.nextLine();
        System.out.println("Type in end datum");
        String endDate = scanner.nextLine();
        System.out.println("Type in amount of Topproducts");
        String amount = scanner.nextLine();

        String query;

        if ((startDate.length() == 0) || (endDate.length() == 0) || (amount.length() == 0)) {
            System.out.println("there is an empty parameter request, cannot continue with it so\n");
        } else {
            query = "CALL  TopProducts(?, ?, ?);";
            Connection con = null;
            PreparedStatement stm = null;
            ResultSet rs = null;

            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/webbutiken?useSSL=false", "root", "root");


                stm = con.prepareStatement(query);

                stm.setString(1, startDate);
                stm.setString(2, endDate);
                stm.setString(3, amount);
                Boolean correctParameters = true;
                try {
                    rs = stm.executeQuery();
                } catch (Exception e) {
                    System.out.println("Wrong format is used for one of the input parameters\n");
                    correctParameters = false;
                }

                if (correctParameters) {
                    System.out.println("Question 5 results:");

                    ArrayList<String> rowArray = new ArrayList<>();

                    String gap = "%1$-30s";

                    rowArray.add("Topproducts");
                    rowArray.add("amount sold");

                    new Format().line(rowArray);
                    new Format().row(rowArray, gap);
                    new Format().line(rowArray);

                    while (rs.next()) {
                        rowArray.clear();

                        rowArray.add(rs.getString(1));
                        rowArray.add(rs.getString(2));
                        new Format().row(rowArray, gap);
                    }
                    new Format().line(rowArray);
                    System.out.println("");
                }


            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stm != null) {
                        stm.close();
                    }
                    if (rs != null) {
                        rs.close();
                    }
                    if (con != null)
                        con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
