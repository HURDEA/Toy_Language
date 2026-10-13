package org.paradigms.model;

public class Cherry_Tree implements Fruit_Tree {
    private int age;

    public Cherry_Tree() {
        this.age = 0;
    }

    public Cherry_Tree(int age) {
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
        return "Cherry Tree";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cherry_Tree that = (Cherry_Tree) o;
        return age == that.age;
    }

    @Override
    public String toString() {
        return "Cherry Tree [Age: " + age + " years]";
    }
}
