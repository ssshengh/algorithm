package com.javaLearing.chapter19;

// typeinfo/Person.java
// Using Optional with regular classes

import java.util.*;

class UseOfOptional {
    public final Optional<String> first;
    public final Optional<String> last;
    public final Optional<String> address;
    //只能通过构造器赋值，然后就不能修改它，只能读，不能写
    // etc.
    public final Boolean empty;

    UseOfOptional(String first, String last, String address) {
        this.first = Optional.ofNullable(first);
        this.last = Optional.ofNullable(last);
        this.address = Optional.ofNullable(address);
        empty = !this.first.isPresent()
                && !this.last.isPresent()
                && !this.address.isPresent();
    }

    UseOfOptional(String first, String last) {
        this(first, last, null);
    }

    UseOfOptional(String last) {
        this(null, last, null);
    }

    UseOfOptional() {
        this(null, null, null);
    }

    @Override
    public String toString() {
        if (empty)
            return "<Empty>";
        return (first.orElse("") +
                " " + last.orElse("") +
                " " + address.orElse("")).trim();
    }

    public static void main(String[] args) {
        System.out.println(new UseOfOptional());
        System.out.println(new UseOfOptional("Smith"));
        System.out.println(new UseOfOptional("Bob", "Smith"));
        System.out.println(new UseOfOptional("Bob", "Smith",
                "11 Degree Lane, Frostbite Falls, MN"));
    }
}


//现在假设你已经因你惊人的理念而获得了一大笔风险投资，现在你要招兵买马了，但是在虚位以待时，你可以将 Person Optional 对象放在每个 Position 上
class EmptyTitleException extends RuntimeException {
}

class Position {
    private String title;
    private UseOfOptional person;
    //请注意，title 和 person 都是普通字段，不受 Optional 的保护。
    // 但是，修改这些字段的唯一途径是调用 setTitle() 和 setPerson() 方法，这两个都借助 Optional 对字段进行了严格的限制。


    //把每个人放在一个合适的工作岗位上
    Position(String jobTitle, UseOfOptional employee) {
        setTitle(jobTitle);
        setPerson(employee);
    }
    //不放人，只安排工作
    Position(String jobTitle) {
        this(jobTitle, null);
    }

    public String getTitle() {
        return title;
    }

    /**
     * 但其实还有更好的做法，函数式编程一大优势就是可以让我们重用经过验证的功能（即便是个很小的功能），以减少自己手动编写代码可能产生的一些小错误。
     * 所以在这里，我们用 ofNullable() 把 newTitle 转换一个 Optional（如果传入的值为 null，ofNullable() 返回的将是 Optional.empty()）。
     * 紧接着我们调用了 orElseThrow() 方法，所以如果 newTitle 的值是 null，你将会得到一个异常。
     * 这里我们并没有把 title 保存成 Optional，但通过应用 Optional 的功能，我们仍然如愿以偿地对这个字段施加了约束。
     * */
    //只能通过这个方法修改title字段
    public void setTitle(String newTitle) {
        // Throws EmptyTitleException if newTitle is null:
        title = Optional.ofNullable(newTitle)
                .orElseThrow(EmptyTitleException::new);
    }

    public UseOfOptional getPerson() {
        return person;
    }
    //只能通过这个修改Person字段
    public void setPerson(UseOfOptional newPerson) {
        //一种思路是是否等于null的if判断，但是显然有更好办法：流式编程重用验证过的小功能
        // Uses empty Person if newPerson is null:
        person = Optional.ofNullable(newPerson)
                .orElse(new UseOfOptional());//如果为空，创建一个新的UseOfOptional的person
    }

    @Override
    public String toString() {
        return "Position: " + title +
                ", Employee: " + person;
    }

    public static void main(String[] args) {
        System.out.println(new Position("CEO"));
        System.out.println(new Position("Programmer",
                new UseOfOptional("Arthur", "Fonzarelli")));
        try {
            new Position(null);
        } catch (Exception e) {
            System.out.println("caught " + e);
        }
    }
}
