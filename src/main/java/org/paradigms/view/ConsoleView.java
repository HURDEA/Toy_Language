package org.paradigms.view;

//this is the view for the console, it should handle displaying information to the user
//this should allow the user to interact with the application
public class ConsoleView {

    //Menu
    public void displayMenu() {
        //we should first clear the console because this menu will print until app exit
        clearConsole();

        System.out.println("\n\n\n___Lab02: Orchard___\n\n\n");
        System.out.println("1. Add Fruit Tree\n");
        System.out.println("2. Remove Fruit Tree\n");
        System.out.println("3. Display Fruit Trees\n");
        System.out.println("4.Given Task: Print Fruit Tree Older than 3 years\n");
        System.out.println("5. Exit\n");
    }

    public void clearConsole() {
        //clear console
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
