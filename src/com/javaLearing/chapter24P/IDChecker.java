package com.javaLearing.chapter24P;

//import com.google.common.collect.Sets;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.*;

public class IDChecker {
    public static final int SIZE = 100_000;

    //生产者类
    static class MakeObjects implements Supplier<List<Integer>> {
        private Supplier<HasID> gen;

        MakeObjects(Supplier<HasID> gen) {
            this.gen = gen;
        }

        @Override
        public List<Integer> get() {
            //生成一定数量的HasID对象，然后转换获取其ID，变成一个List
            return Stream.generate(gen)
                    .limit(SIZE)
                    .map(HasID::getID)
                    .collect(Collectors.toList());
        }

    }


    public static void test(Supplier<HasID> gen) {
        CompletableFuture<List<Integer>>
                groupA = CompletableFuture.supplyAsync(new
                MakeObjects(gen)),
                groupB = CompletableFuture.supplyAsync(new
                        MakeObjects(gen));

        groupA.thenAcceptBoth(groupB, (a, b) -> {
            System.out.println(
                    
//                    Sets.intersection(
//                            Sets.newHashSet(a),
//                            Sets.newHashSet(b)).size()
                                                        );
        }).join();
    }
}
