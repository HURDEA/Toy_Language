package org.paradigms;

import org.junit.jupiter.api.Test;
import org.paradigms.model.Apple_Tree;
import org.paradigms.model.Cherry_Tree;
import org.paradigms.model.Fruit_Tree;
import org.paradigms.model.Pear_Tree;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {

    @Test
    void testFruitTreesCreationAndProperties() {
        Fruit_Tree apple = new Apple_Tree(4);
        Fruit_Tree pear = new Pear_Tree(2);
        Fruit_Tree cherry = new Cherry_Tree(5);

        assertEquals(4, apple.getAge());
        assertEquals(2, pear.getAge());
        assertEquals(5, cherry.getAge());

        assertEquals("Apple Tree", apple.getTreeType());
        assertEquals("Pear Tree", pear.getTreeType());
        assertEquals("Cherry Tree", cherry.getTreeType());

        assertTrue(apple.isOlderThanThreeYears());
        assertFalse(pear.isOlderThanThreeYears());
        assertTrue(cherry.isOlderThanThreeYears());
    }

    @Test
    void testDefaultConstructors() {
        Fruit_Tree apple = new Apple_Tree();
        Fruit_Tree pear = new Pear_Tree();
        Fruit_Tree cherry = new Cherry_Tree();

        assertEquals(0, apple.getAge());
        assertEquals(0, pear.getAge());
        assertEquals(0, cherry.getAge());
        assertFalse(apple.isOlderThanThreeYears());
    }

    @Test
    void testBoundaryAges() {
        Fruit_Tree youngTree = new Apple_Tree(0);
        Fruit_Tree threeYearTree = new Cherry_Tree(3);
        Fruit_Tree fourYearTree = new Pear_Tree(4);

        assertFalse(youngTree.isOlderThanThreeYears());
        assertFalse(threeYearTree.isOlderThanThreeYears(), "3 years old is not strictly older than 3 years");
        assertTrue(fourYearTree.isOlderThanThreeYears());
    }

    @Test
    void testEqualsAndToString() {
        Fruit_Tree apple1 = new Apple_Tree(3);
        Fruit_Tree apple2 = new Apple_Tree(3);
        Fruit_Tree apple3 = new Apple_Tree(5);
        Fruit_Tree pear = new Pear_Tree(3);

        assertEquals(apple1, apple2);
        assertNotEquals(apple1, apple3);
        assertNotEquals(apple1, pear);
        assertNotEquals(null, apple1);

        assertTrue(apple1.toString().contains("Apple Tree"));
        assertTrue(apple1.toString().contains("3"));
    }
}
