package org.paradigms.model;

public class Pear_Tree implements Fruit_Tree {
    private int age;

    public Pear_Tree() {
        this.age = 0;
    }

    public Pear_Tree(int age) {
        this.age = age;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public boolean isOlderThanThreeYears() {
        return age > 3;
    }

    @Override
    public String getTreeType() {
        return "Pear Tree";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pear_Tree that = (Pear_Tree) o;
        return age == that.age;
    }

    @Override
    public String toString() {
        return "Pear Tree [Age: " + age + " years]";
    }
}
