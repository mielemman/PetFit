package models.specific;

import models.Pet;

public class Bear extends Pet {

    public Bear(String name) {
        super(name, "     (()__(()\n" +
                    "     /       \\ \n" +
                    "    ( /    \\  \\\n" +
                    "     \\ o o    /\n" +
                    "     (_()_)__/ \\              \n" +
                    "    / _,==.____ \\\n" +
                    "   (   |--|      )\n" +
                    "   /\\_.|__|'-.__/\\_\n" +
                    "  / (        /     \\ \n" +
                    "  \\  \\      (      /\n" +
                    "   )  '._____)    /    \n" +
                    "(((____.--(((____/   ");


        this.hunger = 50;
        this.happiness = 50;
        this.health = 100;
    }

    @Override
    public void feed() {
        this.hunger = 100; 
        System.out.println(name + " has been fed and is now full.");
    }

    @Override
    public void play() {
        this.happiness = 100; 
        System.out.println(name + " is roaring and hunting in the forest!");
    }

    @Override
    public void petAction() {
        System.out.println(name + " is resting and enjoying the peace.");
    }
}
