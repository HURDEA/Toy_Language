package org.paradigms.view;

import org.paradigms.controller.Fruit_TreeController;
import org.paradigms.exceptions.AppException;
import org.paradigms.model.Fruit_Tree;

import java.util.Scanner;

// Text console view for user interaction
public class ConsoleView {

    private final Fruit_TreeController controller;
    private final Scanner scanner;

    public ConsoleView(Fruit_TreeController controller) {
        this(controller, new Scanner(System.in));
    }

    public ConsoleView(Fruit_TreeController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    private String readCleanLine() {
        if (!scanner.hasNextLine()) {
            return null;
        }
        String line = scanner.nextLine();
        return line.replace("\uFEFF", "")
                   .replace("\u00EF\u00BB\u00BF", "")
                   .trim();
    }

    public void displayMenu() {
        System.out.println("\n==========================================");
        System.out.println("             Lab02: Orchard               ");
        System.out.println("==========================================");
        System.out.println("1. Add Fruit Tree");
        System.out.println("2. Remove Fruit Tree");
        System.out.println("3. Display All Fruit Trees");
        System.out.println("4. Filter & Display Fruit Trees Older than 3 Years");
        System.out.println("5. Exit");
        System.out.println("==========================================");
        System.out.print("Enter your choice (1-5): ");
    }

    public void run() {
        boolean running = true;
        while (running) {
            displayMenu();
            String line = readCleanLine();
            if (line == null) {
                System.out.println("\nInput stream ended. Exiting application.");
                break;
            }

            if (line.isEmpty()) {
                continue;
            }

            int choice;
            try {
                choice = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("[Error] Invalid choice! Please enter a number between 1 and 5.");
                continue;
            }

            switch (choice) {
                case 1:
                    handleAddTree();
                    break;
                case 2:
                    handleRemoveTree();
                    break;
                case 3:
                    handleDisplayAllTrees();
                    break;
                case 4:
                    handleFilterTreesOlderThan3();
                    break;
                case 5:
                    System.out.println("Exiting application. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("[Error] Invalid choice! Please choose an option between 1 and 5.");
            }
        }
    }

    private void handleAddTree() {
        System.out.println("\n--- Add Fruit Tree ---");
        System.out.println("Select tree type:");
        System.out.println("1. Apple Tree");
        System.out.println("2. Cherry Tree");
        System.out.println("3. Pear Tree");
        System.out.print("Choice (1-3): ");

        String typeChoice = readCleanLine();
        if (typeChoice == null || typeChoice.isEmpty()) return;

        System.out.print("Enter tree age (positive integer): ");
        String ageStr = readCleanLine();
        if (ageStr == null || ageStr.isEmpty()) return;

        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            System.out.println("[Error] Age must be a valid integer!");
            return;
        }

        try {
            controller.addTree(typeChoice, age);
            System.out.println("[Success] Fruit tree added successfully!");
        } catch (AppException e) {
            System.out.println("[Error] Failed to add tree: " + e.getMessage());
        }
    }

    private void handleRemoveTree() {
        System.out.println("\n--- Remove Fruit Tree ---");
        Fruit_Tree[] trees = controller.getAllTrees();
        if (trees.length == 0) {
            System.out.println("Repository is empty. Nothing to remove.");
            return;
        }

        System.out.println("Current trees in orchard:");
        for (int i = 0; i < trees.length; i++) {
            System.out.println("  [" + i + "] " + trees[i]);
        }

        System.out.print("Enter index of tree to remove (0 to " + (trees.length - 1) + "): ");
        String indexStr = readCleanLine();
        if (indexStr == null || indexStr.isEmpty()) return;

        int index;
        try {
            index = Integer.parseInt(indexStr);
        } catch (NumberFormatException e) {
            System.out.println("[Error] Index must be a valid integer!");
            return;
        }

        try {
            controller.removeTreeByIndex(index);
            System.out.println("[Success] Tree at index " + index + " removed successfully!");
        } catch (AppException e) {
            System.out.println("[Error] Failed to remove tree: " + e.getMessage());
        }
    }

    public void handleDisplayAllTrees() {
        System.out.println("\n--- All Fruit Trees in Orchard ---");
        Fruit_Tree[] trees = controller.getAllTrees();
        if (trees.length == 0) {
            System.out.println("No trees currently in the orchard.");
            return;
        }

        for (int i = 0; i < trees.length; i++) {
            System.out.println("  [" + i + "] " + trees[i]);
        }
        System.out.println("Total count: " + trees.length);
    }

    public void handleFilterTreesOlderThan3() {
        System.out.println("\n--- Given Task: Fruit Trees Older Than 3 Years ---");
        Fruit_Tree[] filtered = controller.filterTreesOlderThanThreeYears();
        if (filtered.length == 0) {
            System.out.println("No trees older than 3 years found in the orchard.");
            return;
        }

        for (int i = 0; i < filtered.length; i++) {
            System.out.println("  - " + filtered[i]);
        }
        System.out.println("Total matching: " + filtered.length);
    }

    public void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
