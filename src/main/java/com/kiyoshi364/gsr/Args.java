package com.kiyoshi364.gsr;

import java.util.HashMap;
import java.util.Map;

public final class Args {
    // TODO: support -- whatever
    private static void parseOne(
            String arg, String[] options, Map<Integer, String> map) {
        boolean used = false;
        for ( int i = 0; i < options.length; i++ ) {
            String opt = options[i];
            if ( arg.startsWith(opt) ) {
                int opt_len = opt.length();
                int arg_len = arg.length();
                boolean withValue = arg_len > opt_len
                        && arg.charAt(opt_len) == '=';
                boolean withoutValue = arg_len == opt_len;

                if ( withValue || withoutValue ) {
                    String matched = null;
                    if ( withValue ) {
                        matched = arg.substring(opt_len+1);
                    } else {
                        matched = null;
                    }
                    if ( map.containsKey(i) ) {
                        System.out.println(
                                "[WARNING] overwriting " + opt
                                + ": `" + map.get(opt)
                                + "' with `" + matched + "'");
                    }
                    map.put(i, matched);
                    used = true;
                    break;
                }
            }
        }
        if ( !used ) {
            System.out.println("[WARNING] Unused argument: " + arg);
        }
    }

    public static Map<Integer, String> parse(
            String[] args, String[] options) {
        Map map = new HashMap<Integer, String>();
        for ( String arg : args ) {
            parseOne(arg, options, map);
        }
        return map;
    }

    private static String[] helps = {
        "help", "/?", "-h", "--help",
    };
    private static boolean isHelp(String arg) {
        for ( String h : helps ) {
            if ( h.equals(arg) ) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasHelp(String[] args) {
        for ( String arg : args ) {
            if ( isHelp(arg) ) {
                return true;
            }
        }
        return false;
    }
}
