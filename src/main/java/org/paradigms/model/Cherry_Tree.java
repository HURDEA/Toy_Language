package org.paradigms.model;

public class Cherry_Tree extends Base_Fruit_Tree {

    @Override
    public void describe() {
        System.out.println("This is a cherry tree.");
    }

    public Cherry_Tree() {
        super();
    }

    public Cherry_Tree(int age) {
        super(age);
    }
}
