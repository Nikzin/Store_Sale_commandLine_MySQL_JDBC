package com.gmail.nikolay.zinin;


import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Question3 {
    public void run() {

        System.out.println("Search a product: type in what do you want to find:");
        Scanner scanner = new Scanner(System.in);
        String searchWord = scanner.nextLine();

        String query;

        if (searchWord.length() == 0) {
            System.out.println("there is an empty request\n");
        } else {
            query = "SELECT produkt.Namn as produkt FROM produkt\n" +
                    "WHERE produkt.Namn LIKE ?";


            Connection con = null;
            PreparedStatement stm = null;
            ResultSet rs = null;

            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/webbutiken?useSSL=false", "root", "root");

                stm = con.prepareStatement(query);
                searchWord = "%" + searchWord + "%";
                stm.setString(1, searchWord);

                rs = stm.executeQuery();

                System.out.println("Question 3 result:\n");
                if (!rs.next()) {
                    System.out.println("no data found\n");}
                else {

                ArrayList<String> rowArray = new ArrayList<>();

                String gap = "%1$-30s";

                rowArray.add("produkt");

                new Format().line(rowArray);
                new Format().row(rowArray, gap);
                new Format().line(rowArray);

                    rs.previous();
    while (rs.next()) {

                    rowArray.clear();

                    rowArray.add(rs.getString("produkt"));
                    new Format().row(rowArray, gap);
                }
                new Format().line(rowArray);
                    System.out.println("");}



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
