package com.example.smartpantrymanager;

public class Recipe {
    public long id;
    public String name, description, instructions;

    public Recipe(long id, String name, String description, String instructions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.instructions = instructions;
    }
}