package com.javaLearing.chapter19;

// typeinfo/Staff.java

import java.util.*;

public class Staff extends ArrayList<Position> {
    //注意这个add，因为继承与Array，是直接加到了列表里，很微妙
    public void add(String title, UseOfOptional person) {
        add(new Position(title, person));
    }

    public void add(String... titles) {
        for (String title : titles)
            add(new Position(title));
    }

    public Staff(String... titles) {
        add(titles);
    }

    public Boolean positionAvailable(String title) {
        System.out.println("This 是这个玩意儿");
        System.out.println(this);//this是一个列表，继承于ArrayList，add添加了Position对象，因此可以直接this用来做为for in迭代

        for (Position position : this)
            if (position.getTitle().equals(title) &&
                    position.getPerson().empty)
                return true;
        return false;
    }

    public void fillPosition(String title, UseOfOptional hire) {
        System.out.println("This 是这个玩意儿");
        System.out.println(this);

        for (Position position : this)
            if (position.getTitle().equals(title) &&
                    position.getPerson().empty) {
                position.setPerson(hire);
                return;
            }
        throw new RuntimeException(
                "Position " + title + " not available");
    }

    //在有些地方你可能还是要测试引用是不是 Optional，这跟检查是否为 null 没什么不同。
    // 但是在其它地方（例如本例中的 toString() 转换），你就不必执行额外的测试了，而可以直接假设所有对象都是有效的。
    public static void main(String[] args) {
        Staff staff = new Staff("President", "CTO",
                "Marketing Manager", "Product Manager",
                "Project Lead", "Software Engineer",
                "Software Engineer", "Software Engineer",
                "Software Engineer", "Test Engineer",
                "Technical Writer");
        staff.fillPosition("President",
                new UseOfOptional("Me", "Last", "The Top, Lonely At"));
        staff.fillPosition("Project Lead",
                new UseOfOptional("Janet", "Planner", "The Burbs"));
        if (staff.positionAvailable("Software Engineer"))
            staff.fillPosition("Software Engineer",
                    new UseOfOptional(
                            "Bob", "Coder", "Bright Light City"));
        System.out.println(staff);
    }
}
