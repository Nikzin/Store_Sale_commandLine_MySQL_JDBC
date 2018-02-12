package com.gmail.nikolay.zinin;


import java.util.Scanner;

public  class Application {
    InputStateMachine inputStateMachine = new InputStateMachine();

    public void run() {
     

        Scanner scanner = new Scanner(System.in);
        while (true) {

            inputStateMachine.handleInput(scanner.nextLine());

        }
    }
}
