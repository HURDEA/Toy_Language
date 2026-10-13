package org.paradigms.controller;

import org.paradigms.exceptions.ControllerException;
import org.paradigms.exceptions.RepositoryException;
import org.paradigms.model.Apple_Tree;
import org.paradigms.model.Cherry_Tree;
import org.paradigms.model.Fruit_Tree;
import org.paradigms.model.Pear_Tree;
import org.paradigms.repository.IRepository;

// Controller for Fruit_Tree operations
// Maintains a reference to the repository interface (IRepository)
// Implements add, remove, and the problem-solving filtering logic
public class Fruit_TreeController {

    private IRepository repository;

    public Fruit_TreeController(IRepository repository) {
        this.repository = repository;
    }

    public void addTree(Fruit_Tree tree) throws ControllerException, RepositoryException {
        if (tree == null) {
            throw new ControllerException("Tree cannot be null.");
        }
        if (tree.getAge() < 0) {
            throw new ControllerException("Tree age cannot be negative. Given: " + tree.getAge());
        }
        repository.add(tree);
    }

    public Fruit_Tree createTree(String type, int age) throws ControllerException {
        if (age < 0) {
            throw new ControllerException("Tree age cannot be negative. Given: " + age);
        }
        if (type == null || type.trim().isEmpty()) {
            throw new ControllerException("Tree type cannot be empty.");
        }

        String normalized = type.trim().toLowerCase();
        switch (normalized) {
            case "1":
            case "apple":
            case "apple_tree":
            case "apple tree":
                return new Apple_Tree(age);
            case "2":
            case "cherry":
            case "cherry_tree":
            case "cherry tree":
                return new Cherry_Tree(age);
            case "3":
            case "pear":
            case "pear_tree":
            case "pear tree":
                return new Pear_Tree(age);
            default:
                throw new ControllerException("Unknown tree type: '" + type + "'. Valid types: 1. Apple, 2. Cherry, 3. Pear.");
        }
    }

    public void addTree(String type, int age) throws ControllerException, RepositoryException {
        Fruit_Tree tree = createTree(type, age);
        addTree(tree);
    }

    public void removeTree(Fruit_Tree tree) throws ControllerException, RepositoryException {
        if (tree == null) {
            throw new ControllerException("Tree to remove cannot be null.");
        }
        repository.remove(tree);
    }

    public void removeTreeByIndex(int index) throws ControllerException, RepositoryException {
        repository.remove(index);
    }

    public void removeLastTree() throws ControllerException, RepositoryException {
        int size = repository.getSize();
        if (size == 0) {
            throw new ControllerException("Repository is empty, cannot remove tree.");
        }
        repository.remove(size - 1);
    }

    public Fruit_Tree[] getAllTrees() {
        return repository.getAll();
    }

    public int getTreeCount() {
        return repository.getSize();
    }

    // Problem functionality:
    // Filter all fruit trees older than 3 years (age > 3)
    public Fruit_Tree[] filterTreesOlderThanThreeYears() {
        return filterTreesOlderThan(3);
    }

    public Fruit_Tree[] filterTreesOlderThan(int minAge) {
        Fruit_Tree[] all = repository.getAll();
        int count = 0;
        for (Fruit_Tree tree : all) {
            if (tree.isOlderThanThreeYears()) {
                count++;
            }
        }

        Fruit_Tree[] result = new Fruit_Tree[count];
        int idx = 0;
        for (Fruit_Tree tree : all) {
            if (tree.isOlderThanThreeYears()) {
                result[idx++] = tree;
            }
        }
        return result;
    }

    public IRepository getRepository() {
        return repository;
    }

    public void setRepository(IRepository repository) {
        this.repository = repository;
    }
}
