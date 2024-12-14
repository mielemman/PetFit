package models.specific;

import models.Pet;

public class Rabbit extends Pet {

    public Rabbit(String name) {
        super(name, "              ,-\"\"-.\n" +
                    "             /  _  _\\\n" +
                    "            /  /_; ;_\\\n" +
                    "           / ,-.o) (o|\n" +
                    "          / (  =.`-o' )=\n" +
                    "         (  \\`-. __J.'\n" +
                    "          )  )/   (\n" +
                    "         /_,'/   , \\\n" +
                    "            /     \\ \\\n" +
                    "        ,  /    -,/\\ \\ \n" +
                    "      ,{ \\/       `\\`\"'\n" +
                    "      { (        __/__\n" +
                    "   `{ /\\_________)))\n" +
                    "        `");
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
        System.out.println(name + " is hopping around happily!");
    }

    @Override
    public void petAction() {
        System.out.println(name + " is resting and relaxing.");
    }
}
