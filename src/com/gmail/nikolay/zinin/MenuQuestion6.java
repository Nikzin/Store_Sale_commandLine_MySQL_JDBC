package com.gmail.nikolay.zinin;

import java.sql.*;
import java.util.ArrayList;

public class MenuQuestion6 implements MenuInterface {

    private MenuInterface currentMenu;

    public MenuQuestion6() {
      //  this.currentMenu = currentMenu;
        System.out.println("test2");
    }


    private String[] parametersIn = new String[3];
    private String[] questions = {"customer", "produkt", "order"};

    private int count = 0;
    private ArrayList<String> data = new ArrayList<>();
    private ArrayList<String> answersValues = new ArrayList<>();
    private String currentAnswerValue;
    private String currentAnswer;
    boolean printtable = true;

    @Override
    public void question() {
        System.out.println("count is"+ count);
        count++;


        String query = "";
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        switch (count) {
            case 1:
                query = "SELECT * FROM kund;";
                break;
            case 2:
                query = "SELECT * FROM produkt;";
                break;
            case 3:
                query = "SELECT * FROM orders;";
                break;
        }
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/webbutiken?useSSL=false", "root", "root");
            stm = con.prepareStatement(query);
            rs = stm.executeQuery();
            if (printtable) {
                new Format().printTable(rs);
            }
            System.out.println("To choose a " + questions[count - 1] + " type in a number");
            if (count==3) System.out.println("or type \"0\" to creare a new order" );
            rs = stm.executeQuery();
            data.clear();
            while (rs.next()) {
                data.add(rs.getString(1));
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

    @Override
    public void handleAnswer(InputStateMachine menuSetter, String answer){
//this.currentMenu= new MenuQuestion6();
       // setCurrentMenu.setCurrentMenu(currentMenu);
        System.out.println("in q6");
        currentAnswer=answer;
        if (validateAnswerQ6(answer)) {
            String query = "";
            Connection con = null;
            PreparedStatement stm = null;
            ResultSet rs = null;

            if (currentAnswer==null){answersValues.add(currentAnswer);}
            else {
            currentAnswerValue = data.get(Integer.parseInt(currentAnswer)-1);
            answersValues.add(currentAnswerValue);}


            if (count == 3) { this.currentMenu=new MainMenu();
                menuSetter.setCurrentMenu(currentMenu);
               // this.currentMenu=new MainMenu ();
              //  application.setCurrentMenu(new MainMenu(this.currentMenu));
             //   System.out.println(handleAnswer);
                try {
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/webbutiken?useSSL=false", "root", "root");

                    try {
                        query = "CALL LäggTillKundvagn (?,?,?);";
                        //query= "CALL LäggTillKundvagn ('1209036225','AA0003', 'SK0004' );";

                        stm = con.prepareStatement(query);
                        stm.setString(1, answersValues.get(0));
                        stm.setString(2, answersValues.get(1));
                        stm.setString(3, answersValues.get(2));
                        rs = stm.executeQuery();
                        System.out.println("custumer " + answersValues.get(0) + " product " + answersValues.get(1) + " order " + answersValues.get(2) + " are used for calling a procedure");
                    } catch (Exception e) {
                        System.out.println("Error: some of the parameters you typed is not in range, so: no action with those is possible");
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

                //return new MainMenu();
            }
        } else {
            count--;
            printtable = false;
        }
    //return new MenuQuestion6();
      // return this;
       // return null;
    }

    private Boolean validateAnswerQ6(String answer) {
       if (count==3 && answer.equals("0")) {currentAnswer=null;}
       else {
           try {
               currentAnswerValue = data.get(Integer.parseInt(answer)-1);
               printtable = true;
           } catch (Exception e) {
               System.out.println("you wrote a wrong number or not a number, try again");
               return false;
           }
       }


        return true;
    }
}

