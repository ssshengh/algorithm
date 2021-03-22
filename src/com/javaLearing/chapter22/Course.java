package com.javaLearing.chapter22;

public enum Course {
    APPETIZER(Food.Appetizer.class),
    MAINCOURSE(Food.MainCourse.class),
    DESSERT(Food.Dessert.class),
    COFFEE(Food.Coffee.class);

    private Food[] values;
    Course(Class<? extends Food> kind) {
        this.values = kind.getEnumConstants();//把得到的枚举的所有内容复制到一个对象数组内部
    }

    public Food randomSelection() {
        return Enums.random(values);
    }
}

class Meal {
    public static void main(String[] args) {
        for(int i = 0; i < 5; i++) {
            for(Course course : Course.values()) {
                //可以看到，values的每一个内容是一个枚举集合
                //course为每一次提取出来的Course的对象，这个对象因为是一个enum，所以有一个值
                //而这里嵌套了，这个值是一个enum集合
                Food food = course.randomSelection();
                System.out.println(food);
            }
            System.out.println("***");
        }
    }
}