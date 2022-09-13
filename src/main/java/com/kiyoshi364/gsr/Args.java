package com.kiyoshi364.gsr;

import java.util.Map;
import java.util.HashMap;

public final class Args {
    // TODO: support -- whatever
    private static void parseOne(
            String arg, String[] options, Map<Integer, String> map) {
        boolean used = false;
        for ( int i = 0; i < options.length; i++ ) {
            String opt = options[i];
            if ( arg.startsWith(opt) ) {
                int opt_len = opt.length();
                if ( arg.length() > opt_len
                        && arg.charAt(opt_len) == '=' ) {
                    String matched = arg.substring(opt.length()+1);
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
}
