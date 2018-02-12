package com.gmail.nikolay.zinin;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Question4 {
    public void run() {





            Connection con = null;
            PreparedStatement stm = null;
            ResultSet rs = null;

            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/webbutiken?useSSL=false", "root", "root");
                String   query="SELECT MONTHNAME(orders.Datum) as Month, sum((Antal*Pris)) AS försäljning FROM orders\n" +
                        "INNER JOIN orderprodukt on orders.OrderNr = orderprodukt.OrderNr\n" +
                        "INNER JOIN produkt on orderprodukt.Produktskod = produkt.Kod\n" +
                        "GROUP BY MONTHNAME(orders.Datum)\n" +
                        "ORDER BY sum((Antal*Pris)) DESC;";
                stm = con.prepareStatement(query);
                rs = stm.executeQuery();
                System.out.println("Question 4 result:");
                ArrayList<String> rowArray = new ArrayList<>();

                String gap = "%1$-30s";

                rowArray.add("month");
                rowArray.add("försäljning");

                new Format().line(rowArray);
                new Format().row(rowArray, gap);
                new Format().line(rowArray);

                while (rs.next()) {
                    rowArray.clear();

                    rowArray.add(rs.getString("month"));
                    rowArray.add(rs.getString("försäljning"));
                    new Format().row(rowArray, gap);
                }
                new Format().line(rowArray);
                System.out.println("");

                System.out.println("Type in month you want to see a report:");
                Scanner scanner = new Scanner(System.in);
                String searchWord= scanner.nextLine();




                if (searchWord.length() == 0) {System.out.println("there is an empty request\n"); }

                else {
                    query="SELECT MONTHNAME(orders.Datum) as Month, sum((Antal*Pris)) AS försälning FROM orders\n" +
                            "INNER JOIN orderprodukt on orders.OrderNr = orderprodukt.OrderNr\n" +
                            "INNER JOIN produkt on orderprodukt.Produktskod = produkt.Kod\n" +
                            "WHERE MONTHNAME(orders.Datum) = ?;";

                    stm = con.prepareStatement(query);
                    stm.setString(1, searchWord);

                    rs = stm.executeQuery();
                    rs.next();
                    String reportMonth= rs.getString(2);
                    if (searchWord.length() == 0) {System.out.println("there is an empty request\n"); }
                    else if (reportMonth == null){
                        System.out.println("no sales for "+ searchWord+" or no such a month");}
                        else
                    {System.out.println("Report for: " + searchWord + " SEK: " + reportMonth);
                    }
                    System.out.println();
                }


            } catch(SQLException e){
                e.printStackTrace();
            } finally{
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
        }}
