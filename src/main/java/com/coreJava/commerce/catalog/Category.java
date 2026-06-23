package com.coreJava.commerce.catalog;

public class Category {
    private Long id;
    private String name;

    public String getName() {
        return name;
    }

    public void rename(String name) {

        if (name.isEmpty()){ // isBlanktan farkı ne ?
            throw new IllegalArgumentException();

        }
        this.name = name;
    }



    public Category(Long id, String name){

        if (id <=0){
            throw new IllegalArgumentException();

        }

        if (name.isEmpty()){
            throw new IllegalArgumentException();

        }
        this.id = id;
        this.name = name;

    }

    public Long getId() {
        return id;
    }
}
