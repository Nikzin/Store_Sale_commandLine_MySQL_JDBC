package com.gmail.nikolay.zinin;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Question2 {
    public void run() {

        System.out.println("Type in personnumer or press Enter to see all the customers");
        Scanner scanner = new Scanner(System.in);
        String custumernummer = scanner.nextLine();
        String query;

        if (custumernummer.length() != 0) {
            query = "SELECT kund.Fornamn, kund.Efternamn, sum((Antal*Pris)) AS Total_ordervärdet From kund\n" +
                    "INNER JOIN orders on kund.Personnummer = orders.KundNr\n" +
                    "INNER JOIN orderprodukt ON orders.OrderNr = orderprodukt.OrderNr\n" +
                    "INNER JOIN produkt ON orderprodukt.Produktskod = produkt.Kod \n" +
                    "WHERE kund.Personnummer= ?";
        } else {
            query = "SELECT kund.Fornamn, kund.Efternamn, sum((Antal*Pris)) AS Total_ordervärdet From kund\n" +
                    "INNER JOIN orders on kund.Personnummer = orders.KundNr\n" +
                    "INNER JOIN orderprodukt ON orders.OrderNr = orderprodukt.OrderNr\n" +
                    "INNER JOIN produkt ON orderprodukt.Produktskod = produkt.Kod \n" +
                    " GROUP BY kund.Efternamn";
        }

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/webbutiken?useSSL=false", "root", "root");

            stm = con.prepareStatement(query);

            if (custumernummer.length() != 0) {
                stm.setString(1, custumernummer);
            }

            rs = stm.executeQuery();

            System.out.println("Question 2 result:");
            rs.next();
            if (rs.getString(1) == null) {
                System.out.println("No custumers with personnummer " + custumernummer + " are found\n");
            } else {
                rs.previous();
                ArrayList<String> rowArray = new ArrayList<>();

                String gap = "%1$-30s";

                rowArray.add("fornamn");
                rowArray.add("efternamn");
                rowArray.add("Total_ordervärdet");
                new Format().line(rowArray);
                new Format().row(rowArray, gap);
                new Format().line(rowArray);

                while (rs.next()) {
                    rowArray.clear();
                    rowArray.add(rs.getString("fornamn"));
                    rowArray.add(rs.getString("efternamn"));
                    rowArray.add(rs.getString("Total_ordervärdet"));
                    new Format().row(rowArray, gap);
                }
                new Format().line(rowArray);
                System.out.println("");

            }
        }
             catch(SQLException e){
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
    }
}





