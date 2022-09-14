package com.kiyoshi364.gsr;

public final class Parser {
    public static final class Ctx {
        public final String str;
        public final int next;

        public Ctx(String str, int next) {
            this.str = str;
            this.next = next;
        }
    }
    public static final class After<T> {
        public final T parsed;
        public final Ctx ctx;

        public After(T parsed, String str, int next) {
            this(parsed, new Ctx(str, next));
        }

        public After(T parsed, Ctx ctx) {
            this.parsed = parsed;
            this.ctx = ctx;
        }
    }
}
