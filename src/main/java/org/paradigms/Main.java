package org.paradigms;

import org.paradigms.view.ConsoleView;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
     //main should run the application
     ConsoleView consoleView = new ConsoleView();
     consoleView.displayMenu();
     consoleView.clearConsole();

     //exit app
     System.exit(0);
    }
}