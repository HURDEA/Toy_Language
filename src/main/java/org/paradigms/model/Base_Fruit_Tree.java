package org.paradigms.model;

public class Base_Fruit_Tree implements Fruit_Tree {
    //implementation:

    @Override
    public int getAge() {
            return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void grow() {
        age++;
    }

    @Override
    public void describe() {
        System.out.println("This is a fruit tree.");
    }

    private int age = 0;

    //doesn't this need a constructor?
    //answer:
    public Base_Fruit_Tree() {
        this.age = 0;
    }

    public Base_Fruit_Tree(int age) {
        this.age = age;
    }
}
