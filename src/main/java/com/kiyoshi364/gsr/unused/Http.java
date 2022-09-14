package com.kiyoshi364.gsr;

import static com.kiyoshi364.gsr.Parser.Ctx;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Http {
    public enum Method {
        GET, POST, HEAD, OPTIONS, PUT, DELETE, TRACE;
    }

    public static final class Request {
        public final Map<String, String> headers;
        public final List<String> rawHeaders;
        public Method method;
        public String path;
        public String httpVersion;

        public Request() {
            this.headers = new HashMap<>();
            this.rawHeaders = new ArrayList<>();
            this.method = null;
            this.path = null;
            this.httpVersion = null;
        }

        public void firstHeader(String firstLine) {
            parseMethod(firstLine);
        }
    }

    public static Parser.After<Method> parseMethod(String s) {
        return parseMethod(s, 0);
    }

    public static Parser.After<Method> parseMethod(
            String s, int start) {
        return parseMethod(new Ctx(s, start));
    }
    public static Parser.After<Method> parseMethod(
            Ctx ctx) {
        String s = ctx.str;
        int start = ctx.next;
        if ( s.startsWith("GET", start) ) {
            return new Parser.After(Method.GET, s, start + 3);
        } else if ( s.startsWith("POST", start) ) {
            return new Parser.After(Method.POST, s, start + 4);
        } else if ( s.startsWith("HEAD", start) ) {
            return new Parser.After(Method.HEAD, s, start + 4);
        } else if ( s.startsWith("OPTIONS", start) ) {
            return new Parser.After(Method.OPTIONS, s, start + 7);
        } else if ( s.startsWith("PUT", start) ) {
            return new Parser.After(Method.PUT, s, start + 3);
        } else if ( s.startsWith("DELETE", start) ) {
            return new Parser.After(Method.DELETE, s, start + 6);
        } else if ( s.startsWith("TRACE", start) ) {
            return new Parser.After(Method.TRACE, s, start + 5);
        }
        return null;
    }
}
