package com.javaLearing.chapter12;

// collections/ListFeatures.java
//import typeinfo.pets.*;
import java.util.*;

public class ListFeatures {
//    public static void main(String[] args) {
//        Random rand = new Random(47);
//        List<Pet> pets = Pets.list(7);
//        System.out.println("1: " + pets);
//
//
//        Hamster h = new Hamster();
//        pets.add(h); // Automatically resizes，插入后自动调整大小
//        System.out.println("2: " + pets);
//        System.out.println("3: " + pets.contains(h));//contains() 方法确定对象是否在列表中
//
//
//        pets.remove(h); // Remove by object
//        Pet p = pets.get(2);
//        System.out.println(
//                "4: " +  p + " " + pets.indexOf(p));//如果有一个对象的引用，可以使用 indexOf() 在 List 中找到该对象所在位置的下标号
//
//        //当确定元素是否是属于某个 List ，寻找某个元素的索引，以及通过引用从 List 中删除元素时，
//        // 都会用到 equals() 方法（根类 Object 的一个方法）。
//        /**
//         * 每个 Pet 被定义为一个唯一的对象，所以即使列表中已经有两个 Cymrics ，
//         * 如果再创建一个新的 Cymric 对象并将其传递给 indexOf() 方法，结果仍为 -1 （表示未找到），
//         * 并且尝试调用 remove() 方法来删除这个对象将返回 false 。对于其他类， equals() 的定义可能有所不同。
//         * 例如，如果两个 String 的内容相同，则这两个 String 相等。
//         * 因此，为了防止出现意外，请务必注意 List 行为会根据 equals() 行为而发生变化。
//         * */
//        Pet cymric = new Cymric();
//        System.out.println("5: " + pets.indexOf(cymric));//找不到这个元素，因为list里有一个唯一的Cymric对象
//        System.out.println("6: " + pets.remove(cymric));
//
//        // Must be the exact object:删除与 List 中的对象完全匹配的对象是成功的
//        System.out.println("7: " + pets.remove(p));
//        System.out.println("8: " + pets);
//
//        /**
//         * 可以在 List 的中间插入一个元素，就像在第 9 行输出和它之前的代码那样。
//         * 但这会带来一个问题：对于 LinkedList ，在列表中间插入和删除都是廉价操作（在本例中，除了对列表中间进行的真正的随机访问），
//         * 但对于 ArrayList ，这可是代价高昂的操作。这是否意味着永远不应该在 ArrayList 的中间插入元素，并最好是转换为 LinkedList ？
//         * 不，它只是意味着你应该意识到这个问题，如果你开始在某个 ArrayList 中间执行很多插入操作，并且程序开始变慢，
//         * 那么你应该看看你的 List 实现有可能就是罪魁祸首（发现此类瓶颈的最佳方式是使用分析器 profiler）。
//         *
//         * 优化是一个很棘手的问题，最好的策略就是置之不顾，直到发现必须要去担心它了（尽管去理解这些问题总是一个很好的主意）。
//         * */
//        pets.add(3, new Mouse()); // Insert at an index插入一个元素，把后面的元素朝后挤
//        System.out.println("9: " + pets);
//
//        /**
//         * subList() 方法可以轻松地从更大的列表中创建切片，当将切片结果传递给原来这个较大的列表的 containsAll() 方法时，很自然地会得到 true。
//         * 请注意，顺序并不重要，在第 11、12 行输出中可以看到，在 sub 上调用直观命名的 Collections.sort() 和 Collections.shuffle() 方法，
//         * 不会影响 containsAll() 的结果。 subList() 所产生的列表的幕后支持就是原始列表。因此，对所返回列表的更改都将会反映在原始列表中，反之亦然。
//         * */
//        List<Pet> sub = pets.subList(1, 4);
//        System.out.println("subList: " + sub);
//        System.out.println("10: " + pets.containsAll(sub));
//
//
//        Collections.sort(sub); // In-place sort
//        System.out.println("sorted subList: " + sub);
//        // Order is not important in containsAll():
//        System.out.println("11: " + pets.containsAll(sub));
//        Collections.shuffle(sub, rand); // Mix it up
//        System.out.println("shuffled subList: " + sub);
//        System.out.println("12: " + pets.containsAll(sub));
//
//        //retainAll() 方法实际上是一个“集合交集”操作，在本例中，它保留了同时在 copy 和 sub 中的所有元素。
//        // 请再次注意，所产生的结果行为依赖于 equals() 方法。！！！！！！！
//        List<Pet> copy = new ArrayList<>(pets);
//        sub = Arrays.asList(pets.get(1), pets.get(4));
//        System.out.println("sub: " + sub);
//        copy.retainAll(sub);
//        System.out.println("13: " + copy);
//
//        //第 14 行输出展示了使用索引号来删除元素的结果，与通过对象引用来删除元素相比，它显得更加直观，因为在使用索引时，不必担心 equals() 的行为。
//        copy = new ArrayList<>(pets); // Get a fresh copy
//        copy.remove(2); // Remove by index
//        System.out.println("14: " + copy);
//
//        //removeAll() 方法也是基于 equals() 方法运行的。
//        copy.removeAll(sub); // Only removes exact objects
//        System.out.println("15: " + copy);
//
//        //set() 方法的命名显得很不合时宜，因为它与 Set 类存在潜在的冲突。
//        // 在这里使用“replace”可能更适合，因为它的功能是用第二个参数替换索引处的元素（第一个参数）。
//        copy.set(1, new Mouse()); // Replace an element
//        System.out.println("16: " + copy);
//
//        //对于 List ，有一个重载的 addAll() 方法可以将新列表插入到原始列表的中间位置，
//        // 而不是仅能用 Collection 的 addAll() 方法将其追加到列表的末尾。
//        copy.addAll(2, sub); // Insert a list in the middle
//        System.out.println("17: " + copy);
//
//        //第 18 - 20 行输出展示了 isEmpty() 和 clear() 方法的效果
//        System.out.println("18: " + pets.isEmpty());
//        pets.clear(); // Remove all elements
//        System.out.println("19: " + pets);
//        System.out.println("20: " + pets.isEmpty());
//
//        /**
//         * 第 22、23 行输出展示了如何使用 toArray() 方法将任意的 Collection 转换为数组。
//         * 这是一个重载方法，其无参版本返回一个 Object 数组，但是如果将目标类型的数组传递给这个重载版本，
//         * 那么它会生成一个指定类型的数组（假设它通过了类型检查）。如果参数数组太小而无法容纳 List 中的所有元素（就像本例一样），
//         * 则 toArray() 会创建一个具有合适尺寸的新数组。 Pet 对象有一个 id() 方法，可以在所产生的数组中的对象上调用这个方法。
//         * */
//        pets.addAll(Pets.list(4));
//        System.out.println("21: " + pets);
//        Object[] o = pets.toArray();
//        System.out.println("22: " + o[3]);
//        Pet[] pa = pets.toArray(new Pet[0]);
//        System.out.println("23: " + pa[3].id());
//    }
}
/* Output:
1: [Rat, Manx, Cymric, Mutt, Pug, Cymric, Pug]
2: [Rat, Manx, Cymric, Mutt, Pug, Cymric, Pug, Hamster]
3: true
4: Cymric 2
5: -1
6: false
7: true
8: [Rat, Manx, Mutt, Pug, Cymric, Pug]
9: [Rat, Manx, Mutt, Mouse, Pug, Cymric, Pug]
subList: [Manx, Mutt, Mouse]
10: true
sorted subList: [Manx, Mouse, Mutt]
11: true
shuffled subList: [Mouse, Manx, Mutt]
12: true
sub: [Mouse, Pug]
13: [Mouse, Pug]
14: [Rat, Mouse, Mutt, Pug, Cymric, Pug]
15: [Rat, Mutt, Cymric, Pug]
16: [Rat, Mouse, Cymric, Pug]
17: [Rat, Mouse, Mouse, Pug, Cymric, Pug]
18: false
19: []
20: true
21: [Manx, Cymric, Rat, EgyptianMau]
22: EgyptianMau
23: 14
*/

