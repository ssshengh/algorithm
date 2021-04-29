package com.javaLearing.chapter23;

@DBTable(name = "MEMBER")
public class Member {
    @SQLString(30) String firstName;//注意这种初始化会自动匹配对应的类型
    @SQLString(50) String lastName;

    /**
     * 默认值的语法虽然很灵巧，但是它很快就变的复杂起来。
     * 以 reference 字段的注解为例，上面拥有 @SQLString 注解，但是这个字段也将成为表的主键，因此在嵌入的 @Constraint 注解中设定 primaryKey 元素的值。
     * 这时事情就变的复杂了。你不得不为这个嵌入的注解使用很长的键—值对的形式，来指定元素名称和 @interface 的名称。
     * 同时，由于有特殊命名的 value 也不是唯一需要赋值的元素，因此不能再使用快捷方式特性。如你所见，最终结果不算清晰易懂。
     */
    @SQLInteger Integer age;
    @SQLString(value = 30,
            constrains = @Constraints(primaryKey = true)) String reference;
    static int memberCount;
    public String getReference() { return reference; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    @Override
    public String toString() { return reference; }
    public Integer getAge() { return age; }
}
