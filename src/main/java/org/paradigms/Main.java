package org.paradigms;

import org.paradigms.controller.Fruit_TreeController;
import org.paradigms.exceptions.AppException;
import org.paradigms.exceptions.ControllerException;
import org.paradigms.exceptions.RepositoryException;
import org.paradigms.model.Apple_Tree;
import org.paradigms.model.Cherry_Tree;
import org.paradigms.model.Fruit_Tree;
import org.paradigms.model.Pear_Tree;
import org.paradigms.repository.Fruit_TreeRepository;
import org.paradigms.repository.IRepository;
import org.paradigms.view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   UBB Computer Science - Lab02: Orchard (MVC)    ");
        System.out.println("   Task: Display all fruit trees older than 3 yrs ");
        System.out.println("==================================================\n");

        // 1.2. Repository: in-memory repository with a fixed-size array
        IRepository repository = new Fruit_TreeRepository(10);

        // 1.3. Controller: maintains reference to repository interface
        Fruit_TreeController controller = new Fruit_TreeController(repository);

        // 1.4. Hardcoded test examples (required for maximum grade)
        System.out.println(">>> 1. Populating repository with hardcoded test examples...");
        try {
            controller.addTree(new Apple_Tree(4));   // age 4 (> 3) -> should match
            controller.addTree(new Pear_Tree(2));    // age 2 (<= 3) -> no match
            controller.addTree(new Cherry_Tree(5));  // age 5 (> 3) -> should match
            controller.addTree(new Apple_Tree(1));   // age 1 (<= 3) -> no match
            controller.addTree(new Cherry_Tree(3));  // age 3 (<= 3) -> no match
            controller.addTree(new Pear_Tree(6));    // age 6 (> 3) -> should match
            System.out.println("[Success] 6 test trees successfully added to the repository.\n");
        } catch (AppException e) {
            System.out.println("[Error] Could not initialize hardcoded examples: " + e.getMessage());
        }

        // Display all trees currently stored
        System.out.println(">>> 2. All fruit trees currently in the repository:");
        Fruit_Tree[] allTrees = controller.getAllTrees();
        for (int i = 0; i < allTrees.length; i++) {
            System.out.println("   [" + i + "] " + allTrees[i]);
        }
        System.out.println("   Total count: " + allTrees.length + "\n");

        // 1.5. Demonstrate exception handling
        System.out.println(">>> 3. Testing Exception Handling (Requirement 1.5)...");
        try {
            System.out.print("   Attempting to add tree with negative age (-5): ");
            controller.addTree(new Apple_Tree(-5));
        } catch (ControllerException | RepositoryException e) {
            System.out.println("CAUGHT EXPECTED EXCEPTION -> " + e.getMessage());
        }

        try {
            System.out.print("   Attempting to remove tree at invalid index (99): ");
            controller.removeTreeByIndex(99);
        } catch (ControllerException | RepositoryException e) {
            System.out.println("CAUGHT EXPECTED EXCEPTION -> " + e.getMessage());
        }
        System.out.println();

        // Solve problem: Filter and display all fruit trees older than 3 years
        System.out.println(">>> 4. Executing Problem Requirement: Filter trees older than 3 years:");
        Fruit_Tree[] olderTrees = controller.filterTreesOlderThanThreeYears();
        for (int i = 0; i < olderTrees.length; i++) {
            System.out.println("   * " + olderTrees[i]);
        }
        System.out.println("   Total matching trees: " + olderTrees.length + "\n");

        // 1.4. View: Interactive text interface
        // Check if demo-only flag was passed
        if (args.length > 0 && (args[0].equalsIgnoreCase("--demo") || args[0].equalsIgnoreCase("--non-interactive"))) {
            System.out.println("Non-interactive mode requested. Exiting successfully.");
            return;
        }

        System.out.println(">>> 5. Starting Interactive Console View (Press Ctrl+C or option 5 to exit)...");
        ConsoleView view = new ConsoleView(controller);
        view.run();
    }
}
