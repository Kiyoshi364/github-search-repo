package com.kiyoshi364.gsr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.StringBuilder;
import java.net.URL;
import java.net.MalformedURLException;
import javax.net.ssl.HttpsURLConnection;

public final class Web {
    public static final class Response {
        public final int rcode;
        public final String rmsg;
        public final String response;
        public Response(int rcode, String rmsg, String response) {
            this.rcode = rcode;
            this.rmsg = rmsg;
            this.response = response;
        }
    }

    public static final class NotOkException extends IOException {
        public final Response response;

        public NotOkException(Response response) {
            super("Response was not OK");
            this.response = response;
        }
    }

    public static Response makeRequest(String link, String token) {
        try {
            URL url = new URL(link);
            HttpsURLConnection conn
                = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty(
                    "Accept", "application/vnd.github+json");
            if ( token != null ) {
                conn.setRequestProperty(
                        "Authorization", "Bearer " + token);
            }
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setInstanceFollowRedirects(false);

            conn.connect();
            int rcode = conn.getResponseCode();
            String rmsg = conn.getResponseMessage();

            InputStream in = null;
            if ( rcode == 200 ) {
                in = conn.getInputStream();
            } else {
                in = conn.getErrorStream();
            }
            BufferedReader buffer
                = new BufferedReader(new InputStreamReader(in));
            StringBuilder b = new StringBuilder();
            char[] buff = new char[1024];
            int read = 0;
            while ( (read = buffer.read(buff,0,buff.length)) != -1 ) {
                b.append(buff, 0, read);
            }
            buffer.close();
            conn.disconnect();
            return new Response(rcode, rmsg, b.toString());
        } catch (MalformedURLException e) {
            System.out.println(e);
            System.exit(1);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
        return null;
    }

    public static Response fakeRequest(String link, String token) {
        StringBuilder b = new StringBuilder();
        b.append("{");
        b.append("\n  \"total_count\": 1,");
        b.append("\n  \"incomplete_results\": false,");
        b.append("\n  \"items\": [");
        b.append("\n    {");
        b.append("\n      \"id\": 287130193,");
        b.append("\n      \"node_id\": \"MDEwOlJlcG9zaXRvcnkyODcxMzAxOTM=\",");
        b.append("\n      \"name\": \"crlfuzz\",");
        b.append("\n      \"full_name\": \"dwisiswant0/crlfuzz\",");
        b.append("\n      \"private\": false,");
        b.append("\n      \"owner\": {");
        b.append("\n        \"login\": \"dwisiswant0\",");
        b.append("\n        \"id\": 25837540,");
        b.append("\n        \"node_id\": \"MDQ6VXNlcjI1ODM3NTQw\",");
        b.append("\n        \"avatar_url\": \"https://avatars.githubusercontent.com/u/25837540?v=4\",");
        b.append("\n        \"gravatar_id\": \"\",");
        b.append("\n        \"url\": \"https://api.github.com/users/dwisiswant0\",");
        b.append("\n        \"html_url\": \"https://github.com/dwisiswant0\",");
        b.append("\n        \"followers_url\": \"https://api.github.com/users/dwisiswant0/followers\",");
        b.append("\n        \"following_url\": \"https://api.github.com/users/dwisiswant0/following{/other_user}\",");
        b.append("\n        \"gists_url\": \"https://api.github.com/users/dwisiswant0/gists{/gist_id}\",");
        b.append("\n        \"starred_url\": \"https://api.github.com/users/dwisiswant0/starred{/owner}{/repo}\",");
        b.append("\n        \"subscriptions_url\": \"https://api.github.com/users/dwisiswant0/subscriptions\",");
        b.append("\n        \"organizations_url\": \"https://api.github.com/users/dwisiswant0/orgs\",");
        b.append("\n        \"repos_url\": \"https://api.github.com/users/dwisiswant0/repos\",");
        b.append("\n        \"events_url\": \"https://api.github.com/users/dwisiswant0/events{/privacy}\",");
        b.append("\n        \"received_events_url\": \"https://api.github.com/users/dwisiswant0/received_events\",");
        b.append("\n        \"type\": \"User\",");
        b.append("\n        \"site_admin\": false");
        b.append("\n      },");
        b.append("\n      \"html_url\": \"https://github.com/dwisiswant0/crlfuzz\",");
        b.append("\n      \"description\": \"A fast tool to scan CRLF vulnerability written in Go\",");
        b.append("\n      \"fork\": false,");
        b.append("\n      \"url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz\",");
        b.append("\n      \"forks_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/forks\",");
        b.append("\n      \"keys_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/keys{/key_id}\",");
        b.append("\n      \"collaborators_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/collaborators{/collaborator}\",");
        b.append("\n      \"teams_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/teams\",");
        b.append("\n      \"hooks_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/hooks\",");
        b.append("\n      \"issue_events_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/issues/events{/number}\",");
        b.append("\n      \"events_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/events\",");
        b.append("\n      \"assignees_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/assignees{/user}\",");
        b.append("\n      \"branches_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/branches{/branch}\",");
        b.append("\n      \"tags_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/tags\",");
        b.append("\n      \"blobs_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/git/blobs{/sha}\",");
        b.append("\n      \"git_tags_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/git/tags{/sha}\",");
        b.append("\n      \"git_refs_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/git/refs{/sha}\",");
        b.append("\n      \"trees_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/git/trees{/sha}\",");
        b.append("\n      \"statuses_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/statuses/{sha}\",");
        b.append("\n      \"languages_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/languages\",");
        b.append("\n      \"stargazers_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/stargazers\",");
        b.append("\n      \"contributors_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/contributors\",");
        b.append("\n      \"subscribers_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/subscribers\",");
        b.append("\n      \"subscription_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/subscription\",");
        b.append("\n      \"commits_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/commits{/sha}\",");
        b.append("\n      \"git_commits_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/git/commits{/sha}\",");
        b.append("\n      \"comments_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/comments{/number}\",");
        b.append("\n      \"issue_comment_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/issues/comments{/number}\",");
        b.append("\n      \"contents_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/contents/{+path}\",");
        b.append("\n      \"compare_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/compare/{base}...{head}\",");
        b.append("\n      \"merges_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/merges\",");
        b.append("\n      \"archive_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/{archive_format}{/ref}\",");
        b.append("\n      \"downloads_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/downloads\",");
        b.append("\n      \"issues_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/issues{/number}\",");
        b.append("\n      \"pulls_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/pulls{/number}\",");
        b.append("\n      \"milestones_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/milestones{/number}\",");
        b.append("\n      \"notifications_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/notifications{?since,all,participating}\",");
        b.append("\n      \"labels_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/labels{/name}\",");
        b.append("\n      \"releases_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/releases{/id}\",");
        b.append("\n      \"deployments_url\": \"https://api.github.com/repos/dwisiswant0/crlfuzz/deployments\",");
        b.append("\n      \"created_at\": \"2020-08-12T22:47:35Z\",");
        b.append("\n      \"updated_at\": \"2022-09-08T16:09:33Z\",");
        b.append("\n      \"pushed_at\": \"2022-08-19T12:02:38Z\",");
        b.append("\n      \"git_url\": \"git://github.com/dwisiswant0/crlfuzz.git\",");
        b.append("\n      \"ssh_url\": \"git@github.com:dwisiswant0/crlfuzz.git\",");
        b.append("\n      \"clone_url\": \"https://github.com/dwisiswant0/crlfuzz.git\",");
        b.append("\n      \"svn_url\": \"https://github.com/dwisiswant0/crlfuzz\",");
        b.append("\n      \"homepage\": \"\",");
        b.append("\n      \"size\": 3763,");
        b.append("\n      \"stargazers_count\": 803,");
        b.append("\n      \"watchers_count\": 803,");
        b.append("\n      \"language\": \"Shell\",");
        b.append("\n      \"has_issues\": true,");
        b.append("\n      \"has_projects\": true,");
        b.append("\n      \"has_downloads\": true,");
        b.append("\n      \"has_wiki\": true,");
        b.append("\n      \"has_pages\": false,");
        b.append("\n      \"forks_count\": 102,");
        b.append("\n      \"mirror_url\": null,");
        b.append("\n      \"archived\": false,");
        b.append("\n      \"disabled\": false,");
        b.append("\n      \"open_issues_count\": 2,");
        b.append("\n      \"license\": {");
        b.append("\n        \"key\": \"mit\",");
        b.append("\n        \"name\": \"MIT License\",");
        b.append("\n        \"spdx_id\": \"MIT\",");
        b.append("\n        \"url\": \"https://api.github.com/licenses/mit\",");
        b.append("\n        \"node_id\": \"MDc6TGljZW5zZTEz\"");
        b.append("\n      },");
        b.append("\n      \"allow_forking\": true,");
        b.append("\n      \"is_template\": false,");
        b.append("\n      \"web_commit_signoff_required\": false,");
        b.append("\n      \"topics\": [");
        b.append("\n        \"crlf-injection\",");
        b.append("\n        \"go\",");
        b.append("\n        \"golang\",");
        b.append("\n        \"vulnerability-scanner\",");
        b.append("\n        \"vulnerability-scanning\"");
        b.append("\n      ],");
        b.append("\n      \"visibility\": \"public\",");
        b.append("\n      \"forks\": 102,");
        b.append("\n      \"open_issues\": 2,");
        b.append("\n      \"watchers\": 803,");
        b.append("\n      \"default_branch\": \"master\",");
        b.append("\n      \"score\": 1.0");
        b.append("\n    },");
        b.append("\n  ]");
        b.append("\n}");
        return new Response(200, "OK", b.toString());
    }
}
