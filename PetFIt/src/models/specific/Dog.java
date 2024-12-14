package models.specific;

import models.Pet;

public class Dog extends Pet {

    public Dog(String name) {
        super(name,  
        "       __\n" +
        "        /  \\\n" +
        "       / ..|\\\n" +
        "      (_\\  |_)\n" +
        "      /  \\@' \n" +
        "     /     \\\n" +
        "_   /  `   |\n" +
        "\\/  \\  | _\\\n" +
        " \\   /_ || \\\\\n" +
        "  \\____)|_) \\_)");

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
        System.out.println(name + " is running around and playing fetch!");
    }

    @Override
    public void petAction() {
        System.out.println(name + " is wagging its tail happily.");
    }
}
