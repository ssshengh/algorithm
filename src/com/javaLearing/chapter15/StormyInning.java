package com.javaLearing.chapter15;
//一个抽象类
class BaseballException extends Exception {}
class Foul extends BaseballException {}
class Strike extends BaseballException {}
abstract class Inning{
    Inning() throws BaseballException {}
    public void event() throws BaseballException {
// Doesn't actually have to throw anything
    }
    public abstract void atBat() throws Strike, Foul;
    public void walk() {} // Throws no checked exceptions
}
//在 Inning 类中，可以看到构造器和 event() 方法都声明将抛出异常，但实际上没有抛出。
// 这种方式使你能强制用户去捕获可能在覆盖后的 event() 版本中增加的异常，所以它很合理。这对于抽象方法同样成立，比如 atBat()。


//一个接口
class StormException extends Exception {}
class RainedOut extends StormException {}
class PopFoul extends Foul {}
interface Storm {
    void event() throws RainedOut;
    void rainHard() throws RainedOut;
    //接口 Storm 包含了一个在 Inning 中定义的方法 event() 和一个不在 Inning 中定义的方法 rainHard()。

    /**
     * 这两个方法都抛出新的异常 RainedOut，如果 StormyInning 类在扩展 Inning 类的同时又实现了 Storm 接口，
     * 那么 Storm 里的 event() 方法就不能改变在 Inning 中的 event() 方法的异常接口。！！！！！！！！！！！
     * 否则的话，在使用基类的时候就不能判断是否捕获了正确的异常，所以这也很合理。
     * 当然，如果接口里定义的方法不是来自于基类，比如 rainHard()，那么此方法抛出什么样的异常都没有问题。
     * */

}


//注意在继承的时候，基类的异常该如何处理
public class StormyInning extends Inning implements Storm {
    // OK to add new exceptions for constructors, but you
// must deal with the base constructor exceptions:
    public StormyInning()
            throws RainedOut, BaseballException {}
    public StormyInning(String s)
            throws BaseballException {}
    /**
     * 异常限制对构造器不起作用。你会发现 StormyInning 的构造器可以抛出任何异常，而不必理会基类构造器所抛出的异常。
     * 然而，因为基类构造器必须以这样或那样的方式被调用（这里默认构造器将自动被调用），派生类构造器的异常说明必须包含基类构造器的异常说明。
     * */


    /**派生类构造器不能捕获基类构造器抛出的异常。*/

    // Regular methods must conform to base class:
//- void walk() throws PopFoul {} //Compile error编译错误
    /**
     * StormyInning.walk() 不能通过编译是因为它抛出了一个 Inning.walk() 中没有声明的异常。
     * 如果编译器允许这么做的话，就可以编写调用Inning.walk()却不处理任何异常的代码。
     * 但是当使用 Inning派生类的对象时，就会抛出异常，从而导致程序出现问题。通过强制派生类遵守基类方法的异常说明，对象的可替换性得到了保证。
     * */



// Interface CANNOT add exceptions to existing
// methods from the base class:
//- public void event() throws RainedOut {}
    //public void event() throws RainedOut, BaseballException{}这种也不行，说是没有抛出RainedOut异常
// If the method doesn't already exist in the
// base class, the exception is OK:
    /**
     * 覆盖后的 event() 方法表明，派生类版的方法可以不抛出任何异常，即使基类版的方法抛出了异常。
     * 因为这样做不会破坏那些假定基类版的方法会抛出异常的代码。
     * 类似的情况出现在 atBat()上，它抛出的异常PopFoul是由基类版atBat()抛出的Foul 异常派生而来。
     * 如果你写的代码同 Inning 一起工作，并且调用了 atBat()的话，那么肯定能捕获 Foul 。
     * 又因为 PopFoul 是由 Foul派生而来，因此异常处理程序也能捕获 PopFoul。
     * */

    @Override
    public void rainHard() throws RainedOut {}
    // You can choose to not throw any exceptions,
// even if the base version does:
    @Override
    public void event() {}
    // Overridden methods can throw inherited exceptions:
    @Override
    public void atBat() throws PopFoul {}
    public static void main(String[] args) {
        /**
         * 最后一个有趣的地方在 main()。如果处理的刚好是 Stormylnning 对象的话，编译器只要求捕获这个类所抛出的异常。
         * 但是如果将它向上转型成基类型，那么编译器就会准确地要求捕获基类的异常。所有这些限制都是为了能产生更为健壮的异常处理代码。
         * */
        try {
            StormyInning si = new StormyInning();
            si.atBat();
        } catch(PopFoul e) {
            System.out.println("Pop foul");
        } catch(RainedOut e) {
            System.out.println("Rained out");
        } catch(BaseballException e) {
            System.out.println("Generic baseball exception");
        }
// Strike not thrown in derived version.

        try {
// What happens if you upcast?向上转型时，要求准确捕获基类所有异常
            Inning i = new StormyInning();
            i.atBat();
// You must catch the exceptions from the
// base-class version of the method:
        } catch(Strike e) {
            System.out.println("Strike");
        } catch(Foul e) {
            System.out.println("Foul");
        } catch(RainedOut e) {
            System.out.println("Rained out");
        } catch(BaseballException e) {
            System.out.println("Generic baseball exception");
        }
    }
}
