package org.paradigms.model;

public class Apple_Tree implements Fruit_Tree {
    private int age;

    public Apple_Tree() {
        this.age = 0;
    }

    public Apple_Tree(int age) {
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
        return "Apple Tree";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apple_Tree that = (Apple_Tree) o;
        return age == that.age;
    }

    @Override
    public String toString() {
        return "Apple Tree [Age: " + age + " years]";
    }
}
