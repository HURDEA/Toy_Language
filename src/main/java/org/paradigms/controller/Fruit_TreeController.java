package org.paradigms.controller;

import org.paradigms.repository.Fruit_TreeRepository;

//this is the controller for Fruit_Tree
//it should handle requests related to Fruit_Tree
public class Fruit_TreeController {

    private Fruit_TreeRepository fruitTreeRepository;

    public Fruit_TreeController(Fruit_TreeRepository fruitTreeRepository) {
        this.fruitTreeRepository = fruitTreeRepository;
    }
}
