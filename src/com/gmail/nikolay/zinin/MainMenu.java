package com.gmail.nikolay.zinin;



public class MainMenu implements MenuInterface {

    private boolean showMainChoice=true;
    private MenuInterface currentMenu;

    @Override
    public void question() {

        if (showMainChoice){
        System.out.println("Which question do you want to execute?");
        System.out.println("Question 1: type \"1\"");
        System.out.println("Question 2: type \"2\"");
        System.out.println("Question 3: type \"3\"");
        System.out.println("Question 4: type \"4\"");
        System.out.println("Question 5: type \"5\"");
        System.out.println("Question 6: type \"6\"");
        System.out.println("To exit   : type \"quit\"");
   }
        else {System.out.println("Enter question nummer or type quit");}

    }

    @Override
    public void handleAnswer(InputStateMachine MenuSetter, String answer) {

        showMainChoice=false;
        switch (answer) {
            case "1": showMainChoice=true; new Question1().run();
                 break;
            case "2": showMainChoice=true;  new Question2().run();
               break;
            case "3": showMainChoice=true;  new Question3().run();
                break;
            case "4": showMainChoice=true;  new Question4().run();
               break;
            case "5": showMainChoice=true;  new Question5().run();
                break;
            case "6": showMainChoice=true;    System.out.println("menu is 6");
           // System.out.println(currentMenu.getClass());
            //return  new MenuQuestion6();
                currentMenu= new MenuQuestion6();
                MenuSetter.setCurrentMenu(currentMenu);
                System.out.println("MMInput " + this.currentMenu.getClass());

                break;
            case "quit":System.exit(0);
         default:{showMainChoice=false;} break;

        }

//return currentMenu;//check it!
    }
}
