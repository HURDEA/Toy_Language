package org.paradigms.repository;

import org.paradigms.model.Fruit_Tree;

//implement fixed-sized array of Fruit_Trees
public class Fruit_TreeRepository {
    private Fruit_Tree[] fruitTrees;

    public Fruit_TreeRepository(int capacity) {
        fruitTrees = new Fruit_Tree[capacity];
    }

    //Can this currently hold Apple_Tree?
    //Answer: Yes, it can hold Apple_Tree because Apple_Tree is a subclass of Fruit_Tree.
}
