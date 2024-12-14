package models.specific;

import models.Pet;

public class Dragon extends Pet {

    public Dragon(String name) {
        super(name, 
                    "                \\||/\n" +
                    "                |  @___oo\n" +
                    "      /\\  /\\   / (__,,,,|\n" +
                    "     )   /^\\/   _)\n" +
                    "     )   _ /  / _)\n" +
                    " /\\  )/\\/ ||  | )_)\n" +
                    "<  >      |(,,) )__)\n" +
                    " ||      /    \\)___)\\\n" +
                    " | \\____(      )___) )___\n" +
                    "  \\______(_______;;; __;;;");

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
        System.out.println(name + " is breathing fire and flying around!");
    }

    @Override
    public void petAction() {
        System.out.println(name + " is resting in its cave.");
    }
}
