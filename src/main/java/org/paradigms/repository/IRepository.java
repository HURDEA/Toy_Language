package org.paradigms.repository;

import org.paradigms.exceptions.RepositoryException;
import org.paradigms.model.Fruit_Tree;

public interface IRepository {
    void add(Fruit_Tree tree) throws RepositoryException;
    void remove(Fruit_Tree tree) throws RepositoryException;
    void remove(int index) throws RepositoryException;
    Fruit_Tree get(int index) throws RepositoryException;
    Fruit_Tree[] getAll();
    int getSize();
    int getCapacity();
}
