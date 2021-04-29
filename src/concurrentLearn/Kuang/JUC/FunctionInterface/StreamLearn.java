package concurrentLearn.Kuang.JUC.FunctionInterface;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class StreamLearn {
    public static void main(String[] args) {
        Stream.generate(new User("ss", 22))
                .limit(5)
                .map(T->{
                    System.out.println("额外的东西");
                    return T+"!!!";
                }).forEach(User::print);


        System.out.println("======================");
        User u1 = User.getUser("sisi", 22);
        User u2 = User.getUser("wage", 23);
        User u3 = User.getUser("aa", 23);
        User u4 = User.getUser("laoshi", 37);
        List<User> users = Arrays.asList(u1, u2, u3, u4);
        users.stream().filter(t->{
            return t.getAge()<30;
        }).forEach(User::printBy);

    }
}

class User implements Supplier<String>{
    private static final int id = 0;
    private final String name;
    private final int age;

    public static void print(String s){
        System.out.println(s);
        System.out.println("元素结束");
    }
    public void printBy(){
        System.out.println(this.get());
    }

    public User(String name, int age){this.name = name; this.age = age;}

    private static int count = 0;
    public static User getUser(String ss, int aa){
        count++;
        return new User(ss, aa);
    }
    public int getAge(){
        return this.age;
    }
    @Override
    public String get() {
        return id+":"+this.name+"的年龄是："+this.age+"岁";
    }
}