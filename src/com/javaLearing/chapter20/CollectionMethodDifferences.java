package com.javaLearing.chapter20;


import java.util.*;
import java.lang.reflect.Method;
import java.util.stream.Collectors;

public class CollectionMethodDifferences {
    //获取类的所有方法的名字，存为一个TreeSet
    static Set<String> methodSet(Class<?> type){
        return Arrays.stream(type.getMethods())//RTTI
                .map(Method::getName)//反射
                .collect(Collectors.toCollection(TreeSet::new));
    }

    static void interfaces(Class<?> type){
        System.out.println("该类的接口为：");
        System.out.print("Interfaces in " +
                type.getSimpleName() + ": ");
        System.out.println(
                Arrays.stream(type.getInterfaces()).map(Class::getSimpleName).collect(Collectors.toList())
        );
    }

    static Set<String> object = methodSet(Object.class);

    static {
        object.add("clone");
    }

    static void
    difference(Class<?> superset, Class<?> subset) {
        System.out.println("注意看差异：");
        System.out.print(superset.getSimpleName() +
                " extends " + subset.getSimpleName() +
                ", adds: ");
        Set<String> comp = Sets.difference(
                methodSet(superset), methodSet(subset));
        comp.removeAll(object); // Ignore 'Object' methods
        System.out.println(comp);
        interfaces(superset);
    }

    public static void main(String[] args) {
        System.out.println("Collection: " +
                methodSet(Collection.class));
        interfaces(Collection.class);
        difference(Set.class, Collection.class);
        difference(HashSet.class, Set.class);
        difference(LinkedHashSet.class, HashSet.class);
        difference(TreeSet.class, Set.class);
        difference(List.class, Collection.class);
        difference(ArrayList.class, List.class);
        difference(LinkedList.class, List.class);
        difference(Queue.class, Collection.class);
        difference(PriorityQueue.class, Queue.class);
        System.out.println("Map: " + methodSet(Map.class));
        difference(HashMap.class, Map.class);
        difference(LinkedHashMap.class, HashMap.class);
        difference(SortedMap.class, Map.class);
        difference(TreeMap.class, Map.class);
    }
}
