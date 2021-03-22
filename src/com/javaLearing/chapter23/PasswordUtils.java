package com.javaLearing.chapter23;

import java.util.List;

/**
 * 注解的元素在使用时表现为 名-值 对的形式，并且需要放置在 @UseCase 声明之后的括号内。
 * 在 encryptPassword() 方法的注解中，并没有给出 description 的值，所以在 @interface UseCase 的注解处理器分析处理这个类的时候会使用该元素的默认值。
 * 你应该能够想象到如何使用这套工具来“勾勒”出将要建造的系统，然后在建造的过程中逐渐实现系统的各项功能。
 */
public class PasswordUtils {
    @UseCase(id = 67, description = "Passwords must contain at least one numeric")
    public boolean validatePassword(String passwd) {
        return (passwd.matches("\\w*\\d\\w*"));
    }
    @UseCase(id = 48)
    public String encryptPassword(String passwd) {
        return new StringBuilder(passwd)
                .reverse().toString();
    }
    @UseCase(id = 49, description =
            "New passwords can't equal previously used ones")
    public boolean checkForNewPassword(
            List<String> prevPasswords, String passwd) {
        return !prevPasswords.contains(passwd);
    }
}
