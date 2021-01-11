package com.example.minimo2_dsa.Models;

public class User {

    private String login;
    private int followers;
    private int following;
    private String avatar_url;
    private String repos_url;

    public String getLogin() {
        return login;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getRepos_url() {
        return repos_url;
    }
}
