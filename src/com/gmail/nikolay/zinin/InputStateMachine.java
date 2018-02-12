package com.gmail.nikolay.zinin;

    public class InputStateMachine  {

    private MenuInterface currentMenu;

        public InputStateMachine() {
        currentMenu = new MainMenu();
        currentMenu.question();
    }

    public void handleInput(String input) {

        System.out.println("handleInput " + currentMenu.getClass());

      currentMenu.handleAnswer(this, input);
      currentMenu.question();

    }
        public void setCurrentMenu (MenuInterface currentMenu) {
        this.currentMenu=currentMenu;
    }
}
