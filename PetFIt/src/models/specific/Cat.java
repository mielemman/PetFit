package models.specific;

import models.Pet;

public class Cat extends Pet {

    public Cat(String name) {
        super(name, "      |\\      _,,,---,,_\n" +
        "ZZZzz /,`.-'`'    -.  ;-;;,_\n" +
        "     |,4-  ) )-,_. ,\\ (  `'-'\n" +
        "    '---''(_/--'  `-\\_)\n");

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
        System.out.println(name + " is playing with a toy!");
    }

    @Override
    public void petAction() {
        System.out.println(name + " is napping peacefully.");
    }
}
