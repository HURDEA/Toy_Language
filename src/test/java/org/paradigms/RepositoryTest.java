package org.paradigms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.paradigms.exceptions.RepositoryException;
import org.paradigms.model.Apple_Tree;
import org.paradigms.model.Cherry_Tree;
import org.paradigms.model.Fruit_Tree;
import org.paradigms.model.Pear_Tree;
import org.paradigms.repository.Fruit_TreeRepository;
import org.paradigms.repository.IRepository;

import static org.junit.jupiter.api.Assertions.*;

public class RepositoryTest {

    private IRepository repository;

    @BeforeEach
    void setUp() {
        repository = new Fruit_TreeRepository(3);
    }

    @Test
    void testInitialStateAndCapacity() {
        assertEquals(0, repository.getSize());
        assertEquals(3, repository.getCapacity());

        // Negative or zero capacity defaults to 10
        IRepository defaultRepo = new Fruit_TreeRepository(0);
        assertEquals(10, defaultRepo.getCapacity());
    }

    @Test
    void testAddAndGet() throws RepositoryException {
        Fruit_Tree apple = new Apple_Tree(2);
        repository.add(apple);

        assertEquals(1, repository.getSize());
        assertEquals(apple, repository.get(0));

        Fruit_Tree[] all = repository.getAll();
        assertEquals(1, all.length);
        assertEquals(apple, all[0]);
    }

    @Test
    void testAddNullThrowsException() {
        assertThrows(RepositoryException.class, () -> repository.add(null));
    }

    @Test
    void testAddBeyondCapacityThrowsException() throws RepositoryException {
        repository.add(new Apple_Tree(1));
        repository.add(new Pear_Tree(2));
        repository.add(new Cherry_Tree(3));
        assertEquals(3, repository.getSize());

        assertThrows(RepositoryException.class, () -> repository.add(new Apple_Tree(4)));
    }

    @Test
    void testRemoveByEntity() throws RepositoryException {
        Fruit_Tree apple = new Apple_Tree(1);
        Fruit_Tree pear = new Pear_Tree(2);
        Fruit_Tree cherry = new Cherry_Tree(3);

        repository.add(apple);
        repository.add(pear);
        repository.add(cherry);

        repository.remove(pear);
        assertEquals(2, repository.getSize());

        Fruit_Tree[] all = repository.getAll();
        assertEquals(apple, all[0]);
        assertEquals(cherry, all[1]);
    }

    @Test
    void testRemoveNonExistentEntityThrowsException() throws RepositoryException {
        repository.add(new Apple_Tree(1));
        assertThrows(RepositoryException.class, () -> repository.remove(new Pear_Tree(9)));
        assertThrows(RepositoryException.class, () -> repository.remove((Fruit_Tree) null));
    }

    @Test
    void testRemoveByIndex() throws RepositoryException {
        Fruit_Tree apple = new Apple_Tree(1);
        Fruit_Tree pear = new Pear_Tree(2);

        repository.add(apple);
        repository.add(pear);

        repository.remove(0);
        assertEquals(1, repository.getSize());
        assertEquals(pear, repository.get(0));
    }

    @Test
    void testRemoveInvalidIndexThrowsException() {
        assertThrows(RepositoryException.class, () -> repository.remove(0));
        assertThrows(RepositoryException.class, () -> repository.remove(-1));
    }

    @Test
    void testGetInvalidIndexThrowsException() {
        assertThrows(RepositoryException.class, () -> repository.get(0));
        assertThrows(RepositoryException.class, () -> repository.get(-1));
    }
}
