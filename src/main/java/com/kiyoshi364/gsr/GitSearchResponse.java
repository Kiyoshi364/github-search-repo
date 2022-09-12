package com.kiyoshi364.gsr;

import com.google.gson.Gson;
import java.util.LinkedList;

// Github API docs:
// https://docs.github.com/en/rest/search#search-repositories
public final class GitSearchResponse {
    public final GSRJson response;
    public final int pageSize;
    public final int pageIndex;
    public final int lastPage;

    public final class GSRJson {
        public final int total_count;
        public final boolean incomplete_results;
        public final LinkedList<Repo> items;

        public GSRJson( int total_count,
                boolean incomplete_results, LinkedList<Repo> items) {
            this.total_count = total_count;
            this.incomplete_results = incomplete_results;
            this.items = items;
        }
    }

    public static class Repo {
        public final String name;
        public final String description;
        public final SimpleUser owner;
        public final String language;
        public final int stargazers_count;
        public final int forks_count;
        public final String updated_at;

        public Repo(String name, String description,
                SimpleUser owner, String language,
                int stargazers_count, int forks_count,
                String updated_at) {
            this.name = name;
            this.description = description;
            this.owner = owner;
            this.language = language;
            this.stargazers_count = stargazers_count;
            this.forks_count = forks_count;
            this.updated_at = updated_at;
        }
    }

    public static class SimpleUser {
        public final String name;
        // Unused fields:
        // public final String email;
        // public final String login;
        // public final int id;
        // public final String node_id;
        // public final String avatar_url;
        // public final String gravatar_url;
        // public final String url;
        // public final String html_url;
        // public final String followers_url;
        // public final String following_url;
        // public final String gists_url;
        // public final String starred_url;
        // public final String subscriptions_url;
        // public final String organizations_url;
        // public final String repos_url;
        // public final String events_url;
        // public final String received_events_url;
        // public final String type;
        // public final boolean site_admin;
        // public final String starred_at;

        public SimpleUser(String name) {
            this.name = name;
        }
    }

    public GitSearchResponse(
            GSRJson response, int per_page, int pageNumber) {
        this.response = response;
        this.pageSize = per_page;
        this.pageIndex = pageNumber - 1;
        // Rounding up
        this.lastPage
            = (response.total_count + per_page - 1) / per_page;
    }

    public static GitSearchResponse fromJson(
            String json, int per_page, int pageNumber) {
        Gson gson = new Gson();
        return new GitSearchResponse(
                gson.fromJson(json, GSRJson.class),
                per_page, pageNumber);
    }

    public static String prettyRepo(Repo r) {
        final StringBuilder b = new StringBuilder();
        b.append(" * Name: ");
        b.append(r.name);
        b.append("\n * Description: ");
        b.append(r.description);
        b.append("\n * Author: ");
        b.append(r.owner.name);
        b.append("\n * Language: ");
        b.append(r.language);
        b.append("\n * Stars: ");
        b.append(r.stargazers_count);
        b.append("\n * Forks: ");
        b.append(r.forks_count);
        b.append("\n * Updated at: ");
        b.append(r.updated_at);
        return b.toString();
    }

    public String toString() {
        final StringBuilder b = new StringBuilder();
        b.append("total count: ");
        b.append(this.response.total_count);
        b.append("\nincomplete results: ");
        b.append(this.response.incomplete_results);
        b.append("\npage: ");
        b.append(this.pageIndex + 1);
        b.append(" / ");
        b.append(this.lastPage);
        b.append("\nitems:");
        int index = this.pageIndex * this.pageSize;
        for ( Repo r : response.items ) {
            if ( r == null ) {
                break;
            }
            index += 1;
            b.append("\n>>> Repo number: ");
            b.append(index);
            b.append("\n");
            b.append(prettyRepo(r));
        }
        return b.toString();
    }
}
