package com.javaLearing.chapter22;

public enum OzWitch {
    // Instances must be defined first, before methods:
    WEST("Miss Gulch, aka the Wicked Witch of the West"),
    NORTH("Glinda, the Good Witch of the North"),
    EAST("Wicked Witch of the East, wearer of the Ruby " +
            "Slippers, crushed by Dorothy's house"),
    SOUTH("Good by inference, but missing");//这个的原理是，默认调用了构造函数

    private String description;
    OzWitch(String s) {
        this.description = s;
    }
    public String getDescription() { return description; }

    public static void main(String[] args) {
        for (OzWitch o:OzWitch.values())
            System.out.println(o+ " : " + o.getDescription());
    }
}
