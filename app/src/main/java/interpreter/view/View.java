package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.repository.Repository;

import java.util.Scanner;

public class View {
    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n Toy Interpreter Menu ");
            System.out.println("1. Example 1: " + Examples.EX1);
            System.out.println("2. Example 2: " + Examples.EX2);
            System.out.println("3. Example 3: " + Examples.EX3);
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            if (!scanner.hasNextLine()) {
                break;
            }
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            switch (line) {
                case "1" -> runProgram(Examples.EX1);
                case "2" -> runProgram(Examples.EX2);
                case "3" -> runProgram(Examples.EX3);
                case "0" -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option. Please choose 1, 2, 3, or 0.");
            }
        }
    }

    private void runProgram(IStmt program) {
        try {
            PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), program);
            Controller controller = new Controller(new Repository(state), true);
            controller.allStep();
            System.out.println("Execution result: " + state.getOut());
        } catch (MyException e) {
            System.err.println("Execution error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}
