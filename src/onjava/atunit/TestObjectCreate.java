package onjava.atunit;

// onjava/atunit/TestObjectCreate.java
// The @Unit @TestObjectCreate tag
import java.lang.annotation.*;
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestObjectCreate {}

