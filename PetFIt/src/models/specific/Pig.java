package models.specific;

import models.Pet;

public class Pig extends Pet {

    public Pig(String name) {
        super(name, "     __   __\n" +
                    "     \\/---\\/ \n" +
                    "      ). .(\n" +
                    "     ( (\\\") )\n" +
                    "      )   (\n" +
                    "     /     \\ hjw\n" +
                    "    (       )`97\n" +
                    "   ( \\ /-\\ / )\n" +
                    "    w'W   W'w");
        this.hunger = 50;
        this.happiness = 50;
        this.health = 100;
    }

    @Override
    public void feed() {
        this.hunger = 100; // Full after feeding
        System.out.println(name + " has been fed and is now full.");
    }

    @Override
    public void play() {
        this.happiness = 100; // Happy after play
        System.out.println(name + " is happily rolling in the mud!");
    }

    @Override
    public void petAction() {
        System.out.println(name + " is snuggling in the straw.");
    }
}
