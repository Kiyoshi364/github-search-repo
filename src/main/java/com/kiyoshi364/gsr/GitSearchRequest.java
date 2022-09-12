package com.kiyoshi364.gsr;

import java.io.UnsupportedEncodingException;
import java.lang.IllegalArgumentException;
import java.lang.StringBuilder;
import java.net.URLEncoder;

// Github API docs:
// https://docs.github.com/en/rest/search#search-repositories
public final class GitSearchRequest {
    public static final String BASE_URL
        = "https://api.github.com/search/repositories";

    public String q;
    public Sort sort;
    public Order order;
    public Integer per_page;
    public Integer page;

    public enum Sort {
        STARS, FORKS, HELP_WANTED_ISSUES, UPDATED;

        public String toString() {
            String s = null;
            switch ( this ) {
                case STARS:
                    s = "stars";
                    break;
                case FORKS:
                    s = "forks";
                    break;
                case HELP_WANTED_ISSUES:
                    s = "help-wanted-issues";
                    break;
                case UPDATED:
                    s = "updated";
                    break;
            }
            return s;
        }
    };

    public enum Order {
        DESC, ASC;

        public String toString() {
            String s = null;
            switch ( this ) {
                case DESC:
                    s = "desc";
                    break;
                case ASC:
                    s = "asc";
                    break;
            }
            return s;
        }
    }

    public GitSearchRequest(String q, Sort sort, Order order,
            Integer per_page, Integer page) {
        this.q = q;
        this.sort = sort;
        this.order = order;
        this.per_page = per_page;
        this.page = page;
    }

    public GitSearchRequest(String q) {
        this(q, Sort.STARS, Order.DESC, null, null);
    }

    private boolean append(StringBuilder b, boolean isFirst,
            String param, String value) {
        if ( value == null ) return true;
        if ( isFirst ) {
            b.append("?");
        } else {
            b.append("&");
        }
        try {
            b.append(URLEncoder.encode(param, "UTF-8"));
            b.append("=");
            b.append(URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            System.out.println("Reached an unreachable Exception!");
            System.out.println(e);
            System.exit(1);
        }
        return false;
    }

    public String makeLink() throws IllegalArgumentException {
        StringBuilder b = new StringBuilder(BASE_URL);
        boolean isFirst = true;
        if ( this.q == null ) {
            throw new IllegalArgumentException("`q' field is null");
        } else if ( this.q.equals("") ) {
            throw new IllegalArgumentException("`q' field is empty");
        } else {
            isFirst = append(b, isFirst, "q", this.q);
        }
        if ( this.sort != null )
            isFirst = append(b, isFirst, "sort", this.sort.toString());
        if ( this.order != null )
            isFirst = append(b, isFirst, "order", this.order.toString());
        if ( this.per_page != null )
            isFirst = append(b, isFirst,
                    "per_page", this.per_page.toString());
        if ( this.page != null )
            isFirst = append(b, isFirst,
                    "page", this.page.toString());
        return b.toString();
    }
}
