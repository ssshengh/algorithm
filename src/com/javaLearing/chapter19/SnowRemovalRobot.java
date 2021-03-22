package com.javaLearing.chapter19;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SnowRemovalRobot implements Robot{
    private String name;

    public SnowRemovalRobot(String name){
        this.name = name;
    }
    @Override
    public String name(){
        return name;
    }
    @Override
    public String model() {
        return "SnowBot Series 11";
    }

    private List<Operation> ops = Arrays.asList(
            new Operation(
                    ()-> name + "会扫雪",
                    ()-> System.out.println(name + "扫雪")
            ),
            new Operation(
                    () -> name + " can chip ice",
                    () -> System.out.println(name + " chipping ice")
            ),
            new Operation(
                    () -> name + " can clear the roof",
                    () -> System.out.println(
                            name + " clearing roof"))
    );
    public List<Operation> operations() {
        return ops;
    }

    public static void main(String[] args) {
        Robot.test(new SnowRemovalRobot("lalala"));
    }

}
