package org.paradigms.model;

//a type of Fruit_Tree
public class Apple_Tree extends Base_Fruit_Tree {

    //I should override describe()
    @Override
    public void describe() {
        System.out.println("This is an apple tree.");
    }

    public Apple_Tree() {
        super();
    }

    public Apple_Tree(int age) {
        super(age);
    }

}
