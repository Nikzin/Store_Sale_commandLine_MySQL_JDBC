package com.gmail.nikolay.zinin;


import java.sql.*;
import java.util.ArrayList;

public class Question1 {
    public  void run() {
        ResultSet rs = null;
        Statement stm = null;

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/webbutiken?useSSL=false","java", "java");)
        {
            stm = con.createStatement();

            rs = stm.executeQuery("SELECT produkt.Namn as produkter, kategori.Namn as kategori FROM produkt\n" +
                    "INNER JOIN produktkategori ON produkt.Kod = produktkategori.Produktkod\n" +
                    "INNER JOIN kategori ON produktkategori.Kategorikod = kategori.Kod\n" +
                    "ORDER BY kategori.Namn;");

            System.out.println("Question 1 result:");

            ArrayList<String> rowArray= new ArrayList<>();

            String gap = "%1$-30s";
            rowArray.add("produkter");
            rowArray.add("kategori");

            new Format().line(rowArray);
            new Format().row(rowArray, gap);
            new Format().line(rowArray);

            while (rs.next()) {
                rowArray.clear();
                rowArray.add(rs.getString("produkter"));
                rowArray.add(rs.getString("kategori"));
                new Format().row(rowArray, gap);
            }
            new Format().line(rowArray);
            System.out.println("");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(rs != null)
                    rs.close();
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
}
