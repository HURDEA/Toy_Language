package org.paradigms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.paradigms.controller.Fruit_TreeController;
import org.paradigms.exceptions.AppException;
import org.paradigms.exceptions.ControllerException;
import org.paradigms.model.Apple_Tree;
import org.paradigms.model.Cherry_Tree;
import org.paradigms.model.Fruit_Tree;
import org.paradigms.model.Pear_Tree;
import org.paradigms.repository.Fruit_TreeRepository;
import org.paradigms.repository.IRepository;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    private Fruit_TreeController controller;
    private IRepository repository;

    @BeforeEach
    void setUp() {
        repository = new Fruit_TreeRepository(10);
        controller = new Fruit_TreeController(repository);
    }

    @Test
    void testAddTreeAndGetAll() throws AppException {
        controller.addTree(new Apple_Tree(4));
        controller.addTree("cherry", 5);
        controller.addTree("3", 2); // '3' corresponds to Pear Tree

        assertEquals(3, controller.getTreeCount());
        Fruit_Tree[] trees = controller.getAllTrees();
        assertEquals(3, trees.length);
        assertEquals("Apple Tree", trees[0].getTreeType());
        assertEquals("Cherry Tree", trees[1].getTreeType());
        assertEquals("Pear Tree", trees[2].getTreeType());
    }

    @Test
    void testAddTreeValidationExceptions() {
        assertThrows(ControllerException.class, () -> controller.addTree((Fruit_Tree) null));
        assertThrows(ControllerException.class, () -> controller.addTree(new Apple_Tree(-1)));
        assertThrows(ControllerException.class, () -> controller.addTree("apple", -2));
        assertThrows(ControllerException.class, () -> controller.addTree("", 3));
        assertThrows(ControllerException.class, () -> controller.addTree(null, 3));
        assertThrows(ControllerException.class, () -> controller.addTree("unknown_fruit", 3));
    }

    @Test
    void testCreateTreeTypes() throws ControllerException {
        Fruit_Tree apple = controller.createTree("1", 4);
        Fruit_Tree cherry = controller.createTree("cherry_tree", 5);
        Fruit_Tree pear = controller.createTree("Pear Tree", 2);

        assertTrue(apple instanceof Apple_Tree);
        assertTrue(cherry instanceof Cherry_Tree);
        assertTrue(pear instanceof Pear_Tree);
    }

    @Test
    void testFilterTreesOlderThanThreeYears() throws AppException {
        // Add trees with different ages: 1, 2, 3, 4, 5, 6
        controller.addTree(new Apple_Tree(1));
        controller.addTree(new Pear_Tree(2));
        controller.addTree(new Cherry_Tree(3)); // Boundary: age 3 should NOT match (> 3)
        controller.addTree(new Apple_Tree(4));  // Should match
        controller.addTree(new Pear_Tree(5));   // Should match
        controller.addTree(new Cherry_Tree(6)); // Should match

        assertEquals(6, controller.getTreeCount());

        Fruit_Tree[] filtered = controller.filterTreesOlderThanThreeYears();
        assertEquals(3, filtered.length);

        for (Fruit_Tree tree : filtered) {
            assertTrue(tree.getAge() > 3, "Filtered tree must have age > 3: " + tree);
        }

        assertEquals(4, filtered[0].getAge());
        assertEquals(5, filtered[1].getAge());
        assertEquals(6, filtered[2].getAge());
    }

    @Test
    void testFilterWhenNoTreesMatch() throws AppException {
        controller.addTree(new Apple_Tree(1));
        controller.addTree(new Pear_Tree(2));
        controller.addTree(new Cherry_Tree(3));

        Fruit_Tree[] filtered = controller.filterTreesOlderThanThreeYears();
        assertEquals(0, filtered.length);
    }

    @Test
    void testRemoveTree() throws AppException {
        Apple_Tree apple = new Apple_Tree(4);
        Pear_Tree pear = new Pear_Tree(2);
        controller.addTree(apple);
        controller.addTree(pear);

        assertEquals(2, controller.getTreeCount());
        controller.removeTree(apple);

        assertEquals(1, controller.getTreeCount());
        assertEquals(pear, controller.getAllTrees()[0]);

        controller.removeTreeByIndex(0);
        assertEquals(0, controller.getTreeCount());
    }

    @Test
    void testRemoveNullOrInvalidTree() {
        assertThrows(ControllerException.class, () -> controller.removeTree(null));
    }

    @Test
    void testRemoveLastTree() throws AppException {
        controller.addTree(new Apple_Tree(1));
        controller.addTree(new Pear_Tree(2));

        controller.removeLastTree();
        assertEquals(1, controller.getTreeCount());

        controller.removeLastTree();
        assertEquals(0, controller.getTreeCount());

        assertThrows(ControllerException.class, () -> controller.removeLastTree());
    }

    @Test
    void testGetAndSetRepository() {
        assertEquals(repository, controller.getRepository());
        IRepository newRepo = new Fruit_TreeRepository(5);
        controller.setRepository(newRepo);
        assertEquals(newRepo, controller.getRepository());
    }
}
