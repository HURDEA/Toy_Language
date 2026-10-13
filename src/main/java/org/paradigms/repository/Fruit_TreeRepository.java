package org.paradigms.repository;

import org.paradigms.exceptions.RepositoryException;
import org.paradigms.model.Fruit_Tree;

import java.util.Arrays;

// In-memory repository using a fixed-size array
public class Fruit_TreeRepository implements IRepository {
    private Fruit_Tree[] fruitTrees;
    private int size;

    public Fruit_TreeRepository(int capacity) {
        if (capacity <= 0) {
            capacity = 10;
        }
        this.fruitTrees = new Fruit_Tree[capacity];
        this.size = 0;
    }

    @Override
    public void add(Fruit_Tree tree) throws RepositoryException {
        if (tree == null) {
            throw new RepositoryException("Cannot add a null tree to the repository.");
        }
        if (size >= fruitTrees.length) {
            throw new RepositoryException("Repository is full! Maximum capacity: " + fruitTrees.length);
        }
        fruitTrees[size] = tree;
        size++;
    }

    @Override
    public void remove(Fruit_Tree tree) throws RepositoryException {
        if (tree == null) {
            throw new RepositoryException("Cannot remove a null tree.");
        }
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (fruitTrees[i].equals(tree)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new RepositoryException("Tree not found in repository: " + tree);
        }
        remove(index);
    }

    @Override
    public void remove(int index) throws RepositoryException {
        if (size == 0) {
            throw new RepositoryException("Repository is empty, cannot remove tree.");
        }
        if (index < 0 || index >= size) {
            throw new RepositoryException("Invalid index " + index + ". Valid range: 0 to " + (size - 1));
        }
        for (int i = index; i < size - 1; i++) {
            fruitTrees[i] = fruitTrees[i + 1];
        }
        fruitTrees[size - 1] = null;
        size--;
    }

    @Override
    public Fruit_Tree get(int index) throws RepositoryException {
        if (index < 0 || index >= size) {
            throw new RepositoryException("Invalid index " + index + ". Valid range: 0 to " + (size - 1));
        }
        return fruitTrees[index];
    }

    @Override
    public Fruit_Tree[] getAll() {
        Fruit_Tree[] current = new Fruit_Tree[size];
        System.arraycopy(fruitTrees, 0, current, 0, size);
        return current;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getCapacity() {
        return fruitTrees.length;
    }
}
