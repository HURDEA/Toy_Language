package org.paradigms.model;

public class Pear_Tree extends Base_Fruit_Tree {

    @Override
    public void describe() {
        System.out.println("This is a pear tree.");
    }

    //does this automatically use the base constructor?
    public Pear_Tree() {
        super();
    }

    public Pear_Tree(int age) {
        super(age);
    }
}
