package com.user.management.client.misc;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Try<A, E> {

    private final A a;
    private final E e;

    private boolean success;

    private Try(A a, E e) {
        if (a == null) {
            this.a = null;
            this.e = e;

            success = false;
        } else if (e == null) {
            this.a = a;
            this.e = e;

            success = true;
        } else {
            throw new AssertionError();
        }
    }

    public Boolean isSuccess() {
        return success;
    }

    public Boolean isFailure() {
        return !success;
    }

    public A get() {
        return a;
    }

    public E getFailure() {
        return e;
    }

    public A onFinish(Function<A, A> onSuccess, Consumer<E> onFailure) {
        if (!success) {
            onFailure.accept(e);
        }

        return onSuccess.apply(a);
    }

    public static <A, E> Try<A, E> success(A a) {
        return new Try<A, E>(a, null);
    }

    public static <A, E> Try<A, E> failure(E e) {
        return new Try<A, E>(null, e);
    }

    public static <A, E> Try<A, E> execute(Supplier<Try<A, E>> f, Function<Exception, Try<A, E>> g) {
        try {
            return f.get();
        } catch (Exception ex) {
            return g.apply(ex);
        }
    }

    public static Unit unit() {
        return Unit.getInstance();
    }

    public static class Unit {
        private static Unit instance = null;

        private Unit() {
        }

        public static Unit getInstance() {
            if (instance == null) {
                instance = new Unit();
            }

            return instance;
        }
    }
}
